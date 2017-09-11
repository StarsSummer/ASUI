package testjson;

/**
 * Created by Jinffee on 2017/8/17.
 */

public class HttpException extends Exception {
    public static char Type_Wrong = 0;
    public static char SERVER_WRONG_RETURN = 1;
    public static char CREATE_FIAL = 2;
    public static char WEB_;
    public HttpException(char msg) {
        super();
    }
}
