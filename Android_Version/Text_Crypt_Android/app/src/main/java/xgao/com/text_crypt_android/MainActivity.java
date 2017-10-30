package xgao.com.text_crypt_android;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


import java.io.File;
import java.net.URI;

import xgao.com.text_crypt_android.logic.intentCodes;
import xgao.com.text_crypt_android.logic.model;

public class MainActivity extends AppCompatActivity {

    public static String ENCRYPTMODE = "ENCRYPT";
    public static String DECRYPTMODE = "DECRYPT";

    private model model;
    private CheckBox showPassword;
    private EditText inputPath;
    private EditText outputPath;
    private EditText password;
    private EditText passWordConfirmed;
    private String encryptionMode = ENCRYPTMODE;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showPassword = (CheckBox) this.findViewById(R.id.showPassword);
        inputPath = (EditText) this.findViewById(R.id.inputPath);
        outputPath = (EditText) this.findViewById(R.id.outPutPath);
        password = (EditText) this.findViewById(R.id.passWordInput);
        passWordConfirmed = (EditText) this.findViewById(R.id.passwordConfirmed);
        model = new model();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }





    public void convertClicked(View view){

    }



    public void browseInputClicked(View view){

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        // use intent.setType("image/*"); to choose only image file
        // use this to choose all files
        intent.setType("*/*");
        // Do this if you need to be able to open the returned URI as a stream
        // intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Select a file to convert"), intentCodes.REQUEST_FILE);

//        This lets an app to browse the file but doesn't return the file uri
//        Uri selectedUri = Uri.parse(Environment.getExternalStorageDirectory().getPath());
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setDataAndType(selectedUri,  "*/*");
//        if (intent.resolveActivityInfo(getPackageManager(), 0) != null)
//        {
//            startActivityForResult(intent, intentCodes.REQUEST_FILE);
//        }
//        else
//        {
//            Toast.makeText(this.getApplicationContext(), "No file browser found", Toast.LENGTH_SHORT).show();
//        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        switch (requestCode){

            case intentCodes.REQUEST_FILE :
                if(result == null){
                    break;
                }
            this.inputPath.setText(new File(result.getData().toString()).getPath());
            break;

            case intentCodes.REQUEST_DIRECTORY:
                if(result == null){
                    break;
                }
                this.outputPath.setText(new File(result.getData().toString()).getPath());
                break;

        }
    }





    public void browseOutputClicked(View view){
        // This chooses the directory only
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        startActivityForResult(Intent.createChooser(intent, "Select a directory to save"), intentCodes.REQUEST_DIRECTORY);

    }

    public void onEncryptSelected(){
        this.encryptionMode = ENCRYPTMODE;
    }

    public void onDecrytptSelect(){
        this.encryptionMode = DECRYPTMODE;
    }


























}
