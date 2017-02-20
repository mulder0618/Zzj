package com.zzj.utils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by mulder on 2017/2/20.
 */
public class MapUtils {

    /**
     * 取交集
     * @param set1
     * @param set2
     * @return
     */
    public static Set intersection(Set set1 ,Set set2){
        Set<String> result = new HashSet<String>();
        result.clear();
        result.addAll(set1);
        result.retainAll(set2);
        return result;
    }

}
