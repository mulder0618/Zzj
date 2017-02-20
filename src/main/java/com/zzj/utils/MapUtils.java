package com.zzj.utils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by mulder on 2017/2/20.
 */
public class MapUtils {

    /**
     * 取交集两个set
     * @param set1
     * @param set2
     * @return
     */
    public static Set intersection2(Set set1 ,Set set2){
        Set<String> result = new HashSet<String>();
        result.clear();
        result.addAll(set1);
        result.retainAll(set2);
        return result;
    }

    /**
     * 取交集三个set
     * @param set1
     * @param set2
     * @param set3
     * @return
     */
    public static Set intersection3(Set set1 ,Set set2,Set set3){
        Set<String> result = new HashSet<String>();
        result.clear();
        result.addAll(set1);
        result.retainAll(set2);
        result.retainAll(set3);
        return result;
    }


    /*public static void main(String[] args){
        Set<String> result = new HashSet<String>();
        Set<String> set1 = new HashSet<String>();
        set1.add("1");
        set1.add("2");
        set1.add("3");

        Set<String> set2 = new HashSet<String>();
        set2.add("2");
        set2.add("3");
        set2.add("6");

        Set<String> set3 = new HashSet<String>();
        set3.add("2");
        set3.add("9");
        set3.add("61");

        result.clear();
        result.addAll(set1);
        result.retainAll(set2);
        result.retainAll(set3);
        System.out.println(result);
    }*/
}
