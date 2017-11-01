package xgao.com.text_crypt_android.File_Browser;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xgao on 11/1/17.
 */

public class fileExploererAdaptor extends ArrayAdapter<String> {

    private LayoutInflater mInflator;

    public fileExploererAdaptor(@NonNull Context context, @NonNull String[] objects) {
        super(context, 0, objects);
        mInflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
}
