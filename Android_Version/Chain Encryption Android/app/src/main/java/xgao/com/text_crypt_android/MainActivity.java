package xgao.com.text_crypt_android;


import android.Manifest;
import android.app.AlertDialog;
import android.app.IntentService;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.provider.DocumentsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import xgao.com.text_crypt_android.File_Browser.FileBrowserActivity;
import xgao.com.text_crypt_android.File_Browser.fileBrowserHelper;
import xgao.com.text_crypt_android.logic.cryptoException;
import xgao.com.text_crypt_android.logic.intentCodes;
import xgao.com.text_crypt_android.logic.model;


public class MainActivity extends AppCompatActivity {

    public static final String ENCRYPT_MODE = "ENCRYPT";
    public static final String DECRYPT_MODE = "DECRYPT";

    private model model;
    private EditText inputPath;
    private EditText outputPath;
    private EditText password;
    private String encryptionMode;
    private boolean useBuiltInFileExplorer = true;
    private String uriFilePath;
    private boolean uriInput;
    private boolean previousUriParseFailed;





    private void savePreferences(){
        if(!findViewById(R.id.browseInput).isEnabled()){
            this.previousUriParseFailed = true;
        }
        SharedPreferences save = getSharedPreferences("entry_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = save.edit();
        editor.putString("inputPath", this.inputPath.getText().toString());
        editor.putString("outputPath", this.outputPath.getText().toString());
        editor.putString("encryptionMode", encryptionMode);
        editor.putBoolean("useBuiltInFileExplorer", useBuiltInFileExplorer);
        editor.putBoolean("uriInput", uriInput);
        editor.putBoolean("parseFailed", previousUriParseFailed);
        if(!uriInput){
            new File(uriFilePath).delete();
        }
        editor.commit();
    }

    private void restorePreferences(){
        SharedPreferences restore = getSharedPreferences("entry_data", Context.MODE_PRIVATE);
        inputPath.setText(restore.getString("inputPath", ""));
        outputPath.setText(restore.getString("outputPath", ""));
        encryptionMode = restore.getString("encryptionMode", ENCRYPT_MODE);
        useBuiltInFileExplorer = restore.getBoolean("useBuiltInFileExplorer", true);
        uriInput = restore.getBoolean("uriInput", false);
        previousUriParseFailed = restore.getBoolean("parseFailed", false);
        if(encryptionMode.equals(DECRYPT_MODE)){
            ((RadioButton) findViewById(R.id.decryptRadio)).setChecked(true);
        }else{
            ((RadioButton) findViewById(R.id.encryptRadio)).setChecked(true);
        }
        if(uriInput && !new File(uriFilePath).exists()) {
            this.uriInput = false;
            this.inputPath.setText("");
        }
        if(!uriInput && new File(uriFilePath).exists()){
            new File(uriFilePath).delete();
        }
        if(this.previousUriParseFailed){
            this.inputPath.setText("");
            this.previousUriParseFailed = false;
            displayAlert("Previous input file failed to load. You can either keep the app alive until it finishes loading or use built in browser to avoid parsing the input file.");
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //text button color tint, accent, background, ect can all be changed at once in the styles.xml
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.uriFilePath = Environment.getExternalStorageDirectory() + "/.TextCryptTemFile";
        inputPath = this.findViewById(R.id.inputPath);
        this.inputPath.setFocusable(false);
        outputPath = this.findViewById(R.id.outPutPath);
        password =  this.findViewById(R.id.passWordInput);
        model = new model();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        restorePreferences();
        // Gotta do this here too because of stupid bugs
        this.permissionChecking();
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
        this.savePreferences();
    }

    public void convertClicked(View view) throws IOException {
         File tempFile = new File(this.uriFilePath);
         if(this.password.getText().toString().equals("")){
             this.displayAlert("Password can not be empty, your key should have at least 8 characters or more if you are serious about security");
             return;
         }
         model.setKey(this.password.getText().toString());
         model.setOutputPath(new File(this.outputPath.getText().toString()));
         if (uriInput){
             model.setUriInput(true);
             model.setInputFile(tempFile);
         }else{
             model.setInputFile(new File(this.inputPath.getText().toString()));
         }
         String validity = model.isEverythingValid();

         if(!validity.equals(model.ALL_GOOD)){
             displayAlert(validity);
             return;
         }


            model.setEncryptionMode(encryptionMode);
            intentService.converting = true;
            Intent intent = new Intent(this, intentService.class);
            intent.putExtra("model", model);
            intent.putExtra("messenger", new Messenger(this.getConvertHandler()));
            findViewById(R.id.convertButton).setEnabled(false);
            ((Button) findViewById(R.id.convertButton)).setText("Converting");
            startService(intent);


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
            if(fileBrowserHelper.checkPathValid(this.outputPath.getText().toString())) {
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
            if(fileBrowserHelper.checkPathValid(this.outputPath.getText().toString())){
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
                        this.uriInput = false;
                        this.inputPath.setText(result.getStringExtra(FileBrowserActivity.RETURN_PATH));
                        if(fileBrowserHelper.checkPathValid(uriFilePath)){
                            new File(uriFilePath).delete();
                        }
                    }else {
                        this.inputPath.setText("Parsing input file...");
                        this.inputPath.setEnabled(false);
                        this.findViewById(R.id.convertButton).setEnabled(false);
                        ((Button) this.findViewById(R.id.convertButton)).setText("Please wait while input file loads");
                        findViewById(R.id.browseInput).setEnabled(false);
                        intentService.converting = false;
                        Intent intent = new Intent(this, intentService.class);
                        intent.putExtra(intentService.convertUri, result.getData().toString());
                        intent.putExtra("messenger", new Messenger(this.getUiHandler()));
                        startService(intent);
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
            case R.id.about_menu_item:
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (123) {
            case FileBrowserActivity.MY_PERMISSIONS_REQUEST_READ_AND_WRITE_SDK:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    return;
                }else{
                    this.displayAlert("You gotta give me access to your files bud");
                }
                break;
        }

    }

    private void permissionChecking(){
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},  123);
        }
        else{
          return;
        }
    }

    private Handler getUiHandler(){
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Bundle reply = msg.getData();
                String uriString = reply.getString(intentService.convertUri);
                Uri uri = Uri.parse(uriString);
                MainActivity.this.inputPath.setEnabled(true);
                MainActivity.this.inputPath.setText(fileBrowserHelper.getFileName(uri, MainActivity.this));
                MainActivity.this.uriInput = true;
                MainActivity.this.findViewById(R.id.convertButton).setEnabled(true);
                ((Button) MainActivity.this.findViewById(R.id.convertButton)).setText("Convert");
                MainActivity.this.uriInput = true;
                findViewById(R.id.browseInput).setEnabled(true);
                model.setUriInputFileName(MainActivity.this.inputPath.getText().toString());
            }
        };
        return handler;
    }

    private Handler getConvertHandler(){
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Bundle reply = msg.getData();
                boolean success = reply.getBoolean("success");
                if (success){
                    displayAlert("Success! Tap on file browser to open converted file directory");
                }else{
                    String errorMessage = reply.getString("error");
                    displayAlert(errorMessage);
                }
                findViewById(R.id.convertButton).setEnabled(true);
                ((Button) findViewById(R.id.convertButton)).setText("Convert");
            }
        };
        return handler;
    }


    public static class intentService extends IntentService {
        private static final String convertUri = "convertUri";
        private static final String intentTAG = "com.xgao.intentService";
        private static boolean converting = false;

        public intentService() {
            super("parsing_uri_to_file_service");
        }

        @Override
        protected void onHandleIntent(@Nullable Intent intent) {
            if (!converting) {
                String uriString = intent.getStringExtra(convertUri);
                Uri uri = Uri.parse(uriString);
                Bundle bundle = intent.getExtras();
                bundle.putString(convertUri, uri.toString());
                Messenger messenger = (Messenger) bundle.get("messenger");
                Message msg = Message.obtain();
                msg.setData(bundle); //put the data here
                try {
                    fileBrowserHelper.AndroidUriToTempFile(uri, this);
                    messenger.send(msg);
                } catch (IOException | RemoteException e) {
                    Toast.makeText(this, "Can't parse the input file" + "\n" + e.getMessage(), Toast.LENGTH_LONG);
                }

            } else if (converting) {
                handleConvert(intent, this);
            }
        }


        private static void handleConvert(Intent intent, Context context) {
            Bundle bundle = intent.getExtras();
            Messenger messenger = (Messenger) bundle.get("messenger");
            Message msg = Message.obtain();
            xgao.com.text_crypt_android.logic.model model = intent.getParcelableExtra("model");
            try {
                model.convert();
                bundle.putBoolean("success", true);
                msg.setData(bundle);
                messenger.send(msg);
            } catch (cryptoException | RemoteException e) {
                bundle.putBoolean("success", false);
                bundle.putString("error", e.getMessage());
                msg.setData(bundle);
                try {
                    messenger.send(msg);
                } catch (RemoteException e1) {
                    Toast.makeText(context, "Can't send unsuccessful handler message", Toast.LENGTH_LONG);
                }
            }
        }

        }

    }



























