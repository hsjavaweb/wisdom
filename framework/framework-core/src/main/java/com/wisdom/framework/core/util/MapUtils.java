package com.wisdom.framework.core.util;

import org.jplus.hyberbin.excel.utils.ObjectHelper;

import java.util.Map;

/**
 * @author hyberbin on 2017/9/26.
 */
public class MapUtils {

    public static Map filterEmptyItem(Map map){
        final Object[] keys = map.keySet().toArray();
        for(Object key:keys){
            if(ObjectHelper.isEmpty(map.get(key))){
                map.remove(key);
            }
        }
        return map;
    }
}
