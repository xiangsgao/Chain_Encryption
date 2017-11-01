package xgao.com.text_crypt_android.File_Browser;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import android.Manifest;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import xgao.com.text_crypt_android.R;
import xgao.com.text_crypt_android.logic.intentCodes;

/**
 Copyright (C) 2011 by Brad Greco <brad@bgreco.net>

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 THE SOFTWARE.
 /////////////////////////////////////////////////////////////////////////////////////

 Requesting for permission method is my own work. I added it to ensure compatibly with Android Marshmallow and up

 Created by GAO November 1st, 2017

 */
public class FileBrowserActivity extends ListActivity {

    public static final String START_DIR = "startDir";
    public static final String ONLY_DIRS = "onlyDirs";
    public static final String SHOW_HIDDEN = "showHidden";
    public static final String RETURN_PATH = "returnPath";
    private static final int  MY_PERMISSIONS_REQUEST_READ_AND_WRITE_SDK = 1555454;
    private File dir;
    private boolean showHidden = false;
    private boolean onlyDirs = true ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chooser_list);
        Bundle extras = getIntent().getExtras();
        dir = Environment.getExternalStorageDirectory();
        if (extras != null) {
            String preferredStartDir = extras.getString(START_DIR);
            showHidden = extras.getBoolean(SHOW_HIDDEN, false);
            onlyDirs = extras.getBoolean(ONLY_DIRS, true);
            if (preferredStartDir != null) {
                File startDir = new File(preferredStartDir);
                if (startDir.isDirectory()) {
                    dir = startDir;
                }
            }
        }
       this.persmissionChecking();



    }


    private void restOfSetUp(){

        setTitle(dir.getAbsolutePath());
        Button btnChoose = (Button) findViewById(R.id.btnChoose);
        String name = dir.getName();
        btnChoose.setText("Select " + "'/" + name + "'");
        btnChoose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                returnDir(dir.getAbsolutePath());
            }
        });
        if(!onlyDirs){
            btnChoose.setVisibility(View.GONE);
        }
        ListView lv = getListView();
        lv.setTextFilterEnabled(true);


        if(!dir.canRead()) {
            Context context = getApplicationContext();
            String msg = "Could not read folder contents.";
            Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        final ArrayList<File> files = filter(dir.listFiles(), onlyDirs, showHidden);
        String[] names = names(files);
        setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, names));

        lv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // This returns the file path
                if(!files.get(position).isDirectory()) {
                    returnDir(files.get(position).getAbsolutePath());
                    return;
                }
                String path = files.get(position).getAbsolutePath();
                Intent intent = new Intent(FileBrowserActivity.this, FileBrowserActivity.class);
                intent.putExtra(FileBrowserActivity.START_DIR, path);
                intent.putExtra(FileBrowserActivity.SHOW_HIDDEN, showHidden);
                intent.putExtra(FileBrowserActivity.ONLY_DIRS, onlyDirs);
                startActivityForResult(intent, intentCodes.REQUEST_DIRECTORY);



            }
        });
    }










    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == intentCodes.REQUEST_DIRECTORY && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            String path = (String) extras.get(FileBrowserActivity.RETURN_PATH);
            returnDir(path);
        }
        if(requestCode == intentCodes.REQUEST_FILE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            String path = (String) extras.get(FileBrowserActivity.RETURN_PATH);
            returnDir(path);
        }
    }


    public String[] names(ArrayList<File> files) {
        String[] names = new String[files.size()];
        int i = 0;
        for(File file: files) {
            names[i] = file.getName();
            i++;
        }
        return names;
    }




    public ArrayList<File> filter(File[] file_list, boolean onlyDirs, boolean showHidden) {
        ArrayList<File> files = new ArrayList<File>();
        for(File file: file_list) {
            if(onlyDirs && !file.isDirectory())
                continue;
            if(!showHidden && file.isHidden())
                continue;
            files.add(file);
        }
        Collections.sort(files);
        return files;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_AND_WRITE_SDK:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        this.restOfSetUp();
                }else{
                    Toast.makeText(this, "You need to give permission bud", Toast.LENGTH_LONG);
                }
                break;
        }

    }

    private void persmissionChecking(){
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions((ListActivity) this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},  MY_PERMISSIONS_REQUEST_READ_AND_WRITE_SDK);
        }
        else{
            this.restOfSetUp();
        }
    }




    private void returnDir(String path) {
        Intent result = new Intent();
        result.putExtra(RETURN_PATH, path);
        setResult(RESULT_OK, result);
        finish();
    }





























}
