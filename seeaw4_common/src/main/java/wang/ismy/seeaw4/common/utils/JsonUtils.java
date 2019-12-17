package wang.ismy.seeaw4.common.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author MY
 * @date 2019/12/17 10:22
 */
public class JsonUtils {
    private static final Gson GSON = new Gson();

    public static String toJson(Object object){
        return GSON.toJson(object);
    }

    public static <T>T fromJson(String json, Type type){
        return GSON.fromJson(json,type);
    }
}
