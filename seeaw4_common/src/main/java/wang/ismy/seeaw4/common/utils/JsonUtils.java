package wang.ismy.seeaw4.common.utils;

import com.google.gson.Gson;

/**
 * @author MY
 * @date 2019/12/17 10:22
 */
public class JsonUtils {
    private static final Gson GSON = new Gson();

    public static String toJson(Object object){
        return GSON.toJson(object);
    }
}
