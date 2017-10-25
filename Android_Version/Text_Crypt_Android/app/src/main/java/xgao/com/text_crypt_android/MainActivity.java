package xgao.com.text_crypt_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import xgao.com.text_crypt_android.logic.model;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private model model;
    private Button convert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        model = new model();
    }
}
