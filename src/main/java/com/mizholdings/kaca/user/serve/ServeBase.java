package com.mizholdings.kaca.user.serve;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import com.mizholdings.kaca.Global;
import com.mizholdings.util.User;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ServeBase {
    protected User user;
    private static final String DEFINE_AGENT_PACKAGE = Global.getProperties("define.agent.package");
    private final String package_path;
    private final Cache<String, Object> map = CacheUtil.newLFUCache(8);

    public ServeBase(User user) {
        this.user = user;
        String[] strings = getClass().getName().split("\\.");
        package_path = DEFINE_AGENT_PACKAGE + "." + strings[strings.length - 1].toLowerCase();
    }

    protected Object getAgent(String agentName) {
        if (map.containsKey(agentName)) {
            return map.get(agentName);
        }

        try {
            Class cl = Class.forName(package_path + "." + firstWordToUpperCase(agentName));
            Constructor constructor = cl.getDeclaredConstructor(User.class);
            Object value = constructor.newInstance(user);
            map.put(agentName, value);
            return value;
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        throw new RuntimeException(package_path + "." + agentName + "未找到");
    }


    private static String firstWordToUpperCase(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }



}
