package xgao.com.text_crypt_android;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import xgao.com.text_crypt_android.File_Browser.FileBrowserActivity;
import xgao.com.text_crypt_android.logic.cryptoException;
import xgao.com.text_crypt_android.logic.intentCodes;
import xgao.com.text_crypt_android.logic.model;

import static xgao.com.text_crypt_android.File_Browser.fileBrowserHelper.AndroidUriToTempFile;
import static xgao.com.text_crypt_android.File_Browser.fileBrowserHelper.checkPathValid;
import static xgao.com.text_crypt_android.File_Browser.fileBrowserHelper.getFileName;

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
    private boolean useBuiltInFileExploerer = true;
    private Uri pathUri = null;







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
        model = new model();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    public void convertClicked(View view) throws IOException {
        File tempFile = null;
        if(this.password.getText().toString().equals("")){
            this.displayAlert("Password can not be empty, use at least 8 characters if you are serious about security");
            return;
        }
         if(encryptionMode.equals(ENCRYPTMODE)&&!password.equals(passWordConfirmed)){
             this.displayAlert("Your passwords do not match" );
             return;
         }

         model.setKey(this.password.toString());
         model.setOutputPath(new File(this.outputPath.getText().toString()));
         if (this.pathUri != null){
             model.setUriInput(true);
             tempFile = AndroidUriToTempFile(this.pathUri,this);
             model.setInputFile(tempFile);
         }else{
             model.setInputFile(new File(this.inputPath.getText().toString()));
         }
         String validity = model.isEeverythingValid();

         if(!validity.equals(model.ALL_GOOD)){
             displayAlert(validity);
             return;
         }

         try{
             model.convert();
             this.displayAlert("Success! Tap on File Browser to open the destination file directory");
         }catch (cryptoException e){
             this.displayAlert(e.getMessage());
         }
        if (tempFile!=null){
             tempFile.delete();
        }
    }


    public void browseInputClicked(View view) {
    if(this.useBuiltInFileExploerer){
        Intent intent = new Intent(MainActivity.this, FileBrowserActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(FileBrowserActivity.BROWSER_MODE, intentCodes.REQUEST_FILE);
        bundle.putString(FileBrowserActivity.START_DIR,"/storage/");
        bundle.putBoolean(FileBrowserActivity.SHOW_HIDDEN, true);
        bundle.putBoolean(FileBrowserActivity.ONLY_DIRS, false);
        intent.putExtras(bundle);
        startActivityForResult(intent, intentCodes.REQUEST_FILE);
    }else {
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

    }

    public void openFileClicked(View view){
        // This lets an app to browse the file but doesn't return the file uri. The opening folder is defined by Uri
        // Environment will return the path to the default home directory
        if(this.useBuiltInFileExploerer){
            Intent intent = new Intent(MainActivity.this,FileBrowserActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt(FileBrowserActivity.BROWSER_MODE, intentCodes.REQUEST_FILE_BROWSER);
            if(checkPathValid(this.outputPath.getText().toString())) {
                bundle.putString(FileBrowserActivity.START_DIR, this.outputPath.getText().toString());
            }
            else{
                bundle.putString(FileBrowserActivity.START_DIR, "/storage");
            }
            bundle.putBoolean(FileBrowserActivity.SHOW_HIDDEN, true);
            bundle.putBoolean(FileBrowserActivity.ONLY_DIRS, false);
            intent.putExtras(bundle);
            startActivityForResult(intent, intentCodes.REQUEST_FILE_BROWSER);
        }else {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            if(checkPathValid(this.outputPath.getText().toString())){
                intent.setDataAndType(Uri.parse(this.outputPath.getText().toString()), "*/*");
            }else {
                intent.setDataAndType(Uri.parse(Environment.getExternalStorageDirectory().getPath()), "*/*");
            }
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if(resultCode != RESULT_OK) {
            return;
        }





            switch (requestCode) {
                case intentCodes.REQUEST_FILE:
                    if (result == null) {
                        break;
                    }
                    if(this.useBuiltInFileExploerer){
                        this.pathUri = null;
                        this.inputPath.setText(result.getStringExtra(FileBrowserActivity.RETURN_PATH));
                    }else {
                        this.inputPath.setText(getFileName(result.getData(), this));
                       pathUri = result.getData();
                    }
                    break;

                case intentCodes.REQUEST_DIRECTORY:
                    if (result == null) {
                        break;
                    }
                    if(this.useBuiltInFileExploerer) {
                        this.outputPath.setText(result.getStringExtra(FileBrowserActivity.RETURN_PATH));
                    }else{
                        this.outputPath.setText(result.getData().getPath());
                    }
                    break;

            }







    }


    public void browseOutputClicked(View view) {
        // This chooses the directory only and does not return uri like the Intent.GET_DOCUMENT_TREE but return the full file path if using es file manager
        if(this.useBuiltInFileExploerer){
            Intent intent = new Intent(MainActivity.this, FileBrowserActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt(FileBrowserActivity.BROWSER_MODE, intentCodes.REQUEST_DIRECTORY);
            bundle.putString(FileBrowserActivity.START_DIR,"/storage");
            bundle.putBoolean(FileBrowserActivity.SHOW_HIDDEN, true);
            bundle.putBoolean(FileBrowserActivity.ONLY_DIRS, true);
            intent.putExtras(bundle);
            startActivityForResult(intent, intentCodes.REQUEST_DIRECTORY);
        }else {
            Intent intent = new Intent();
            intent.setType(DocumentsContract.Document.MIME_TYPE_DIR);
            if (intent.resolveActivityInfo(getPackageManager(), 0) != null) {
                startActivityForResult(Intent.createChooser(intent, "Choose ES File Explorer to pick directory, download it from playstore if you don't have it installed already"), intentCodes.REQUEST_DIRECTORY);
            } else {
                Toast.makeText(this.getApplicationContext(), "Please install ES File Manager", Toast.LENGTH_SHORT).show();
            }
        }



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuToggle:
                this.useBuiltInFileExploerer = !item.isChecked();
                item.setChecked(!item.isChecked());
                return true;
                default:return false;
        }

    }

    public void onEncryptSelected(View view) {
        this.encryptionMode = ENCRYPTMODE;
        this.passWordConfirmed.setVisibility(View.VISIBLE);
    }

    public void onDecryptSelected(View view) {
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

    private void displayAlert(String alertMessage){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage(alertMessage);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

}



























