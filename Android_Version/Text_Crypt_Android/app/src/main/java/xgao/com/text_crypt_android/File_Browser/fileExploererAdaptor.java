package xgao.com.text_crypt_android.File_Browser;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import xgao.com.text_crypt_android.R;


/**
 * Created by xgao on 11/1/17.
 */

public class fileExploererAdaptor extends ArrayAdapter<String> {

    private LayoutInflater mInflator;
    private ArrayList<File> file;

    public fileExploererAdaptor(@NonNull Context context, @NonNull ArrayList<File> file, @NonNull String[] objects) {
        super(context, 0, objects);
        mInflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.file = file;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        int type = this.getItemViewType(position);
        if(convertView == null){
            switch(type){
                case 0:
                    convertView = mInflator.inflate(R.layout.list_item,parent,false);
                    ((TextView)convertView.findViewById(R.id.folderView)).setText(this.getItem(position));
                    break;

                case 1:
                    convertView = mInflator.inflate(R.layout.list_file_item, parent,false);
                    ((TextView)convertView.findViewById(R.id.fileView)).setText(this.getItem(position));
                    break;

            }
        }
        return convertView;

    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (file.get(position).isDirectory()) {
            return 0;
        } else {
            return 1;
        }
    }
}
