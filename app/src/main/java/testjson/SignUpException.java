package testjson;

/**
 * Created by Jinffee on 2017/8/17.
 */

public class SignUpException extends Exception {
    public static char USER_PHONENUM_SAME = 0;
    public static char USER_PASSWARD_TOLONG = 1;
    public static char CREATE_FIAL = 2;
    public static char WEB_;
    public SignUpException(char msg) {
        super();
    }
}
