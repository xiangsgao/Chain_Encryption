package xgao.com.text_crypt_android.File_Browser;


import android.app.Application;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.OpenableColumns;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.channels.FileChannel;


public class fileBrowserHelper {


    public static String getFileName(Uri uri, Context context) {
        Cursor returnCursor = context.getContentResolver().query(uri, null, null, null, null);
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        return returnCursor.getString(nameIndex);
    }


    public static void openFileIntent() {

    }

    public static boolean checkPathValid(String path) {
        return new File(path).exists();
    }


    public static File AndroidUriToTempFile(Uri uri, Context context) throws IOException {
        File result = new File(Environment.getExternalStorageDirectory().getPath() + "/TextCryptTemFile");
        if (result.exists()) {
            result.delete();
        }
        InputStream in = context.getContentResolver().openInputStream(uri);

        OutputStream out = new FileOutputStream(result);
        byte buf[] = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0)
            out.write(buf, 0, len);
        out.close();
        in.close();
        return result;
    }


    public static void openFile(Context context, File url) throws IOException {

        Intent myIntent = new Intent(Intent.ACTION_VIEW);

            String mime = URLConnection.guessContentTypeFromStream(new FileInputStream(url));
            if (mime == null) mime = URLConnection.guessContentTypeFromName(url.getName());
            myIntent.setDataAndType(Uri.fromFile(url), mime);


        try {
            context.startActivity(myIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
