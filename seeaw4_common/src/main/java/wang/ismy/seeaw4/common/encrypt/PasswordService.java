package wang.ismy.seeaw4.common.encrypt;

/**
 * @author MY
 * @date 2019/12/26 14:27
 */
public class PasswordService {

    private static String password = System.getProperty("seeaw4.password") != null ? System.getProperty("seeaw4.password") : "password";

    public static void update(String pwd){
        password = pwd;
    }

    public static String get(){
        return password;
    }
}
