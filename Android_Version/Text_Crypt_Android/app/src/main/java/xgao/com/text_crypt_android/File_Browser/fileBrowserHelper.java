package xgao.com.text_crypt_android.File_Browser;


import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.OpenableColumns;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class fileBrowserHelper {


    public static String getFileName(Uri uri, Context context){
        Cursor returnCursor = context.getContentResolver().query(uri, null, null, null, null);
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        return returnCursor.getString(nameIndex);
    }


    public static void openFileIntent(){

    }

    public static boolean checkPathValid(String path){
        return new File(path).exists();
    }



    public static File AndroidUriToTempFile(Uri uri, Context context)throws IOException{
        File result = new File(Environment.getExternalStorageDirectory().getPath()+"/TextCryptTemFile");
        if(result.exists()){
            result.delete();
        }
        InputStream in = context.getContentResolver().openInputStream(uri);

        OutputStream out = new FileOutputStream(result);
        byte buf[]=new byte[1024];
        int len;
        while((len=in.read(buf))>0)
            out.write(buf,0,len);
        out.close();
        in.close();
        return result;
    }








}
