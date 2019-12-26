package com.mizholdings.util;

import java.util.HashMap;
import java.util.Map;

public class Parameter {
    private Map<String, String> map = new HashMap<>();
    private Map<String, Object> oap = new HashMap<>();

    public static Parameter creat() {
        return new Parameter();
    }

    public Parameter add(String key, String value) {
        map.put(key, value);
        oap.put(key, value);
        return this;
    }

    public String getString(String key) {
        if (map.containsKey(key)) {
            return map.get(key);
        }
        return "";
    }

    public String getString(String key, String define) {
        if (map.containsKey(key)) {
            return map.get(key);
        }
        return define;
    }

    public Object getObject(String key) {
        if (oap.containsKey(key)) {
            return oap.get(key);
        }
        return null;
    }

    public Object getObject(String key, Object define) {
        if (oap.containsKey(key)) {
            return oap.get(key);
        }
        return define;
    }


    public Map<String, String> getMap() {
        return map;
    }

    public Map<String, Object> getObjectMap() {
        return oap;
    }

}


