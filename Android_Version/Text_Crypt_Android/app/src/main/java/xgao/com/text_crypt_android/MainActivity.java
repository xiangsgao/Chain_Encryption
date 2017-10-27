package xgao.com.text_crypt_android;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;


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

    }

    public void browseOutputClicked(View view){

    }

    public void onEncryptSelected(){
        this.encryptionMode = ENCRYPTMODE;
    }

    public void onDecrytptSelect(){
        this.encryptionMode = DECRYPTMODE;
    }


























}
