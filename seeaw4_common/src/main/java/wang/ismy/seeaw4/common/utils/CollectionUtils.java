package wang.ismy.seeaw4.common.utils;

import java.util.Collection;
import java.util.Map;

/**
 * @author MY
 * @date 2019/12/16 21:57
 */
public class CollectionUtils {

    public static boolean isEmpty(Collection collection){
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(Map collection){
        return collection == null || collection.isEmpty();
    }
}
