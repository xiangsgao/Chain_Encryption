package xgao.com.text_crypt_android.File_Browser;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.ArrayList;



import java.io.File;


import xgao.com.text_crypt_android.R;


/**
 * Created by xgao on 11/1/17.
 */

public class fileExploererAdaptor extends ArrayAdapter<String> {

    private LayoutInflater mInflator;
    private ArrayList<File> file;
    RecyclerView.ViewHolder holder = null;
    public fileExploererAdaptor(@NonNull Context context, @NonNull ArrayList<File> file, @NonNull ArrayList<String> objects) {
        super(context, 0, objects);
        mInflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.file = file;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        int type = this.getItemViewType(position);
        // view holder inner class is extreamly important, without it and the following code, position are jumble up
        ViewHolder holder = null;
        if(convertView == null) {
            switch (type) {
                case 0:
                    holder = new ViewHolder();
                    convertView = mInflator.inflate(R.layout.list_item, parent, false);
                    holder.textView = convertView.findViewById(R.id.folderView);
                    break;

                case 1:
                    holder = new ViewHolder();
                    convertView = mInflator.inflate(R.layout.list_file_item, parent, false);
                    holder.textView = convertView.findViewById(R.id.fileView);
                    break;

            }
            convertView.setTag(holder);
    }
        else {
        holder = (ViewHolder)convertView.getTag();
    }
    holder.textView.setText(this.getItem(position));
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

    private class ViewHolder {
        public TextView textView;
    }

    public void delete(int position, Context context){
      boolean deleteSuccess =  file.get(position).delete();
      if(deleteSuccess) {
          file.remove(position);
          this.remove(this.getItem(position));
          this.notifyDataSetChanged();
      }
      else{
          ((FileBrowserActivity) context).displayAlert("Due to Android SDK limitation (Actually, it's because I am too lazy to rewrite my entire code for built in File Browser, please use third party file manager to delete files from your removable SD card");
      }
    }
}
