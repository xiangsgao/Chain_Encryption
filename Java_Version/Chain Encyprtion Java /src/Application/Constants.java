package Application;

import java.util.HashMap;
import java.util.Map;

public enum Constants {

    TITLE("Chain Encryption"),
    OK("OK"),
    MAIN_SCREEN_FORM("/Application/UI/Forms/main_screen.fxml"),
    CONVERT_BUTTON("convertButton"),
    POP_UP_FORM("/Application/UI/Forms/pop_up_Screen.fxml"),
    ALERT("ALERT"),
    CONVERTING("Converting....."),
    DECRYPTED("decrypted"),
    FILE_EXIST_ERROR_MSG("File with same name already exists, delete the old one first"),
    FILE_TOO_BIG("File is too big"),
    ENCRYPTED("encrypted"),
    BROWSE_TEXT("Click on browse to select a file"),
    ENTER_VALID_PATH_TEXT("Please enter a valid file path.\nUse the browse button to help you."),
    NO_INPUT_ERROR_MSG("User did not enter input"),
    EMPTY_KEY_ERROR_MSG("key can not be empty\nEnter keys with length of 8 or more if you are serious about security"),
    NULL_ERROR_MSG("Null on pop up browse path"),
    CONVERT("Convert");


    private String value;

    Constants(String value) {
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }

    private static final Map<String, Constants> lookup = new HashMap<>();

    static {
        for(Constants constants : Constants.values()){
            lookup.put(constants.getValue(), constants);
        }
    }

    public static Constants get(String value){
        return lookup.get(value);
    }


}
