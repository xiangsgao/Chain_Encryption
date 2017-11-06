package xgao.com.text_crypt_android;


import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.RadioButton;
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

    public static final String ENCRYPT_MODE = "ENCRYPT";
    public static final String DECRYPT_MODE = "DECRYPT";

    private model model;
    private EditText inputPath;
    private EditText outputPath;
    private EditText password;
    private String encryptionMode;
    private boolean useBuiltInFileExplorer = true;
    private Uri pathUri;





    private void savePerferences (){
        SharedPreferences save = getSharedPreferences("entry_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = save.edit();
        editor.putString("inputPath", this.inputPath.getText().toString());
        editor.putString("outputPath", this.outputPath.getText().toString());
        editor.putString("encryptionMode", encryptionMode);
        editor.putBoolean("useBuiltInFileExplorer", useBuiltInFileExplorer);
        if(pathUri!=null) {
            editor.putString("pathUri", pathUri.toString());
        }
        editor.commit();
    }

    private void restorePerferences(){
        SharedPreferences restore = getSharedPreferences("entry_data", Context.MODE_PRIVATE);
        inputPath.setText(restore.getString("inputPath", ""));
        outputPath.setText(restore.getString("outputPath", ""));
        encryptionMode = restore.getString("encryptionMode", ENCRYPT_MODE);
        useBuiltInFileExplorer = restore.getBoolean("useBuiltInFileExplorer", true);
        if(encryptionMode.equals(DECRYPT_MODE)){
            ((RadioButton) findViewById(R.id.decryptRadio)).setChecked(true);
        }else{
            ((RadioButton) findViewById(R.id.encryptRadio)).setChecked(true);
        }
            pathUri = Uri.parse(restore.getString("pathUri", ""));
        if(pathUri.toString().equals("")){
            pathUri = null;
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //text button color tint, accent, background, ect can all be changed at once in the styles.xml
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputPath = this.findViewById(R.id.inputPath);
        this.inputPath.setFocusable(false);
        outputPath = this.findViewById(R.id.outPutPath);
        password =  this.findViewById(R.id.passWordInput);
        model = new model();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        restorePerferences();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        menu.getItem(0).setChecked(useBuiltInFileExplorer);
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.savePerferences();
    }

    public void convertClicked(View view) throws IOException {
         File tempFile = null;
         if(this.password.getText().toString().equals("")){
             this.displayAlert("Password can not be empty, your key should have at least 8 characters or more if you are serious about security");
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
    if(this.useBuiltInFileExplorer){
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
        if(this.useBuiltInFileExplorer){
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
                    if(this.useBuiltInFileExplorer){
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
                    if(this.useBuiltInFileExplorer) {
                        this.outputPath.setText(result.getStringExtra(FileBrowserActivity.RETURN_PATH));
                    }else{
                        this.outputPath.setText(result.getData().getPath());
                    }
                    break;

            }







    }


    public void browseOutputClicked(View view) {
        // This chooses the directory only and does not return uri like the Intent.GET_DOCUMENT_TREE but return the full file path if using es file manager
        if(this.useBuiltInFileExplorer){
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
           try{
                startActivityForResult(Intent.createChooser(intent, "Choose ES File Explorer to pick directory, download it from playstore if you don't have it installed already"), intentCodes.REQUEST_DIRECTORY);
            } catch (ActivityNotFoundException e){
                Toast.makeText(this.getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuToggle:
                this.useBuiltInFileExplorer = !item.isChecked();
                item.setChecked(!item.isChecked());
                return true;
                default:return false;
        }

    }

    public void onEncryptSelected(View view) {
        this.encryptionMode = ENCRYPT_MODE;
    }

    public void onDecryptSelected(View view) {
        this.encryptionMode = DECRYPT_MODE;
    }

    public void passwordCheckChanged(View view) {
        // this can also be done by using a checkchangedlistener on the checkbox but more step because you'll need to find view by id and shit
        CheckBox checkbox = (CheckBox) view;
        // need to invert the logic because button onclick is process first before changing the check status
        if (!checkbox.isChecked()) {
            this.password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            this.password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
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



























