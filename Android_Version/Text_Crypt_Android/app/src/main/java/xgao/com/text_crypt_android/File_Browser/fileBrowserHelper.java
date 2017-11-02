package xgao.com.text_crypt_android.File_Browser;


import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;

import java.io.File;


public class fileBrowserHelper {


    public static String getFileName(Uri uri, Context context){
        Cursor returnCursor = context.getContentResolver().query(uri, null, null, null, null);
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        return returnCursor.getString(nameIndex);
    }


    public static void openFileIntent(){

    }

    public static Uri returnUri(String path){
      return  Uri.fromFile(new File(String.valueOf(path)));
    }












}
