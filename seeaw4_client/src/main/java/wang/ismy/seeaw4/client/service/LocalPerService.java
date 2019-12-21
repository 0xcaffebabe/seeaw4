package wang.ismy.seeaw4.client.service;

import wang.ismy.seeaw4.client.client.LocalPer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author MY
 * @date 2019/12/21 16:44
 */
public class LocalPerService {

    private static final LocalPerService SERVICE = new LocalPerService();

    private Map<String,LocalPer> perMap = new ConcurrentHashMap<>();

    public void add(LocalPer localPer){
        perMap.put(localPer.getId(),localPer);
    }
    public LocalPer get(String id){
        return perMap.get(id);
    }

    private LocalPerService() { }

    public static LocalPerService getInstance(){
        return SERVICE;
    }
}
