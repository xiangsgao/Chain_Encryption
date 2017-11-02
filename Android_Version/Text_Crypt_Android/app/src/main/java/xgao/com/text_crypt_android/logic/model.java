package xgao.com.text_crypt_android.logic;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;


/**
 * Created by xgao on 10/21/17.
 */

public class model implements Parcelable{


    private File input = null;
    private boolean encryptMode = true;
    private String outputPath = "";
    private String key = "";



    public model() {
    }



    public void setInputFile(String input){
        this.input = new File(input);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.input);
        dest.writeByte(this.encryptMode ? (byte) 1 : (byte) 0);
        dest.writeString(this.outputPath);
        dest.writeString(this.key);
    }


    protected model(Parcel in) {
        this.input = (File) in.readSerializable();
        this.encryptMode = in.readByte() != 0;
        this.outputPath = in.readString();
        this.key = in.readString();
    }

    public static final Creator<model> CREATOR = new Creator<model>() {
        @Override
        public model createFromParcel(Parcel source) {
            return new model(source);
        }

        @Override
        public model[] newArray(int size) {
            return new model[size];
        }
    };
}
