package xgao.com.text_crypt_android.logic;


import java.io.File;


/**
 * Created by xgao on 10/21/17.
 */

public class model {

    public static final String ALL_GOOD = "Everything is good";

    private File inputFile;
    private boolean uriInput = false;
    private boolean encryptMode = true;
    private File outputFile;
    private String key = "";
    private String encryptionMode;

    public model() {
    }

    public void convert() throws cryptoException{
            throw new cryptoException("Successes");
    }

    public void setOutputPath(File outputFile){
        this.outputFile = outputFile;
    }

    public void setKey(String key){
        this.key = key;
    }


    public void setInputFile(File input) {
        this.inputFile = input;
    }

    public void setEncryptionMode(String encryptionMode){
        this.encryptionMode = encryptionMode;
    }

    public String isEeverythingValid(){
        if(inputFile == null || outputFile == null) {
            return "Please select both an input file and an output destination";
        }

        if (!inputFile.exists() && !uriInput){
            return "Input file path is invalid or it no longer exists";
        }

        if (!outputFile.exists()){
            return "Output directory is invalid or it no longer exists";
        }


        return ALL_GOOD;
    }

    public void setUriInput(boolean uriInput){
        this.uriInput = uriInput;
    }


}
