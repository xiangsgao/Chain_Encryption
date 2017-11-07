package xgao.com.text_crypt_android.logic;


import android.util.Log;

import java.io.File;

import xgao.com.text_crypt_android.MainActivity;


/**
 * Created by xgao on 10/21/17.
 */

public class model {

    public static final String ALL_GOOD = "Everything is good";

    private File inputFile;
    private boolean uriInput = false;
    private String uriInputFileName = "TextCryptTemFile";
    private File outputFile;
    private String key = "";
        private String encryptionMode;

    public model() {
    }

    public void convert() throws cryptoException{
        if (encryptionMode.equals(MainActivity.ENCRYPT_MODE)) {
            this.encrypt();
        }

        else if(this.encryptionMode.equals(MainActivity.DECRYPT_MODE)){
            this.decrypt();
        }
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

    private void decrypt() throws cryptoException{
        File decryptedFile = new File(this.outputFile.getPath() +"/" +"decrypted_"+this.inputFile.getName());
        if(decryptedFile.exists()) {
            throw new cryptoException("File with same name already exists, delete the old one first");
        }
        if(uriInput){
            uriInputFileName.substring(10);
            String path = this.outputFile.getPath() + "/" + "decrypted_" + uriInputFileName;
            decryptedFile = new File(path);
        }
        cryptoUtils.decrypt(this.key, this.inputFile, decryptedFile);
    }

    private void encrypt() throws  cryptoException{
        File encryptedFile = new File(this.outputFile.getPath() + "/" + "encrypted_" + this.inputFile.getName());
        if(encryptedFile.exists()) {
                throw new cryptoException("File with same name already exists, delete the old one first");
        }
        if(uriInput){
           String path = this.outputFile.getPath() + "/" + "encrypted_" + uriInputFileName;
           encryptedFile = new File(path);
        }
        cryptoUtils.encrypt(this.key, this.inputFile, encryptedFile);

    }

    public void setUriInputFileName(String uriInputFileName){ this.uriInputFileName = uriInputFileName;}






}
