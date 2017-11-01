package xgao.com.text_crypt_android;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import xgao.com.text_crypt_android.File_Browser.pathResolver;
import java.io.File;

import xgao.com.text_crypt_android.logic.intentCodes;
import xgao.com.text_crypt_android.logic.model;

public class MainActivity extends AppCompatActivity {

    public static final String ENCRYPTMODE = "ENCRYPT";
    public static final String DECRYPTMODE = "DECRYPT";

    private model model;
    private EditText inputPath;
    private EditText outputPath;
    private EditText password;
    private EditText passWordConfirmed;
    private String encryptionMode = ENCRYPTMODE;
    private ImageButton openFile;
    private TextView openFileLabel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //text button color tint, accent, background, ect can all be changed at once in the styles.xml
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputPath = (EditText) this.findViewById(R.id.inputPath);
        this.inputPath.setFocusable(false);
        outputPath = (EditText) this.findViewById(R.id.outPutPath);
        password = (EditText) this.findViewById(R.id.passWordInput);
        passWordConfirmed = (EditText) this.findViewById(R.id.passwordConfirmed);
        openFile = (ImageButton) this.findViewById(R.id.openFile);
        openFileLabel = (TextView) findViewById(R.id.openFileLabel);
        openFileLabel.setVisibility(View.GONE);
        openFile.setEnabled(false);
        openFile.setVisibility(View.GONE);
        model = new model();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }


    public void convertClicked(View view) {
            this.openFileLabel.setVisibility(View.VISIBLE);
            this.openFile.setVisibility(View.VISIBLE);
            this.openFile.setEnabled(true);
    }


    public void browseInputClicked(View view) {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        // use intent.setType("image/*"); to choose only image file
        // use this to choose all files
        intent.setType("*/*");
        // Do this if you need to be able to open the returned URI as a stream
        // intent.addCategory(Intent.CATEGORY_OPENABLE);
        if (intent.resolveActivityInfo(getPackageManager(), 0) != null) {
            startActivityForResult(Intent.createChooser(intent, "Choose any file manager to pick a file to convert"), intentCodes.REQUEST_FILE);
        } else {
            Toast.makeText(this.getApplicationContext(), "No file browser found", Toast.LENGTH_SHORT).show();
        }

    }

    public void openFileClicked(View view){
        // This lets an app to browse the file but doesn't return the file uri. The opening folder is defined by Uri
        // Environment will return the path to the default home directory
        Uri selectedUri = Uri.parse(Environment.getExternalStorageDirectory().getPath());
       // Uri selectedUri = Uri.fromFile(new File(String.valueOf(this.outputPath.getText())));
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(selectedUri,  "*/*");
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if(resultCode == RESULT_OK) {




            switch (requestCode) {
                case intentCodes.REQUEST_FILE:
                    if (result == null) {
                        break;
                    }
                    this.inputPath.setText(pathResolver.getFileName(result.getData(), this));
                    break;

                case intentCodes.REQUEST_DIRECTORY:
                    if (result == null) {
                        break;
                    }
                    break;
            }









        }
    }


    public void browseOutputClicked(View view) {
        // This chooses the directory only and does not return uri like the Intent.GET_DOCUMENT_TREE but return the full file path if using es file manager
        Intent intent = new Intent();
        intent.setType(DocumentsContract.Document.MIME_TYPE_DIR);
        if (intent.resolveActivityInfo(getPackageManager(), 0) != null) {
            startActivityForResult(Intent.createChooser(intent, "Choose ES File Explorer to pick directory, download it from playstore if you don't have it installed already"), intentCodes.REQUEST_DIRECTORY);
        } else {
            Toast.makeText(this.getApplicationContext(), "Please install ES File Manager", Toast.LENGTH_SHORT).show();
        }


    }

    public void onEncryptSelected(View view) {
        this.encryptionMode = ENCRYPTMODE;
        this.passWordConfirmed.setVisibility(View.VISIBLE);
    }

    public void onDecrytptSelect(View view) {
        this.encryptionMode = DECRYPTMODE;
        this.passWordConfirmed.setVisibility(View.GONE);
    }

    public void passwordCheckChanged(View view) {
        // this can also be done by using a checkchangedlistener on the checkbox but more step because you'll need to find view by id and shit
        CheckBox checkbox = (CheckBox) view;
        // need to invert the logic because button onclick is process first before changing the check status
        if (!checkbox.isChecked()) {
            this.password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            this.passWordConfirmed.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            this.password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            this.passWordConfirmed.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
    }

}



























