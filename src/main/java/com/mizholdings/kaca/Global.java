package com.mizholdings.kaca;

import com.mizholdings.kaca.user.KacaUser;
import com.mizholdings.kaca.user.UserBase;
import com.mizholdings.util.XmlTool.ElementMine;
import com.mizholdings.util.XmlTool.XmlTool;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.*;

public class Global {
    private final static Log logger = LogFactory.getLog(Global.class);
    private static Global global;
    public Properties properties;

    private UserBase userBase;
    private KacaUser parent;
    private KacaUser superAdmin;

    public static class Value {
        public final static String classId = getProperties("kaca.global.classId");
        public final static String schoolId = getProperties("kaca.global.schoolId");
        public final static String video_url = getProperties("kaca.global.video.url");
        public final static String video_duration = getProperties("kaca.global.video.duration");
    }

    private static Map<String, ElementMine> map = new HashMap<>();

    public static Global init() {
        if (global == null) {
            global = new Global();
        }
        return global;
    }

    static class Single {
        public final static ElementMine serve = XmlTool.readXML(String.format("serve/%s.xml", "app"));
    }

    public static ElementMine getServe() {
        return Single.serve;
    }

    private Global() {
        InputStream stream = Global.class.getClassLoader().getResourceAsStream("testConfig.properties");
        properties = new Properties();
        try {
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e);
        }
    }

    public static String getProperties(String key) {
        return init().properties.getProperty(key);
    }


    public static List<String> getUserIds(int num) {
        InputStream stream = Global.class.getClassLoader().getResourceAsStream("userIds");
        Reader reader = new InputStreamReader(stream);
        BufferedReader bufferedReader = new BufferedReader(reader);
        List<String> strings = new ArrayList<>();
        String line = null;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                strings.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.shuffle(strings);
        return strings.subList(0, num < strings.size() ? num : strings.size());
    }

    public static KacaUser getParent() {
        return init()._getParent();
    }

    public static KacaUser getTeacher() {
        return init()._getTeacher();
    }


    public KacaUser _getParent() {
        if (parent == null) {
            String account = properties.getProperty("kaca.parent.account");
            String password = properties.getProperty("kaca.parent.password");
            parent = KacaUser.kcct(account, password);
        }
        return parent;
    }

    public KacaUser _getTeacher() {
        if (parent == null) {
            String account = properties.getProperty("kaca.teacher.account");
            String password = properties.getProperty("kaca.teacher.password");
            parent = KacaUser.kcsj(account, password);
        }
        return parent;
    }

    public UserBase getUser() {
        if (userBase == null) {
            String account = properties.getProperty("me2.student.account");
            String password = properties.getProperty("me2.student.password");
            userBase = new UserBase(account, password, "app");
        }
        return userBase;
    }

    public String getAccount() {
        return String.format(properties.getProperty("me2.student.account.format"),
                new Random().nextInt(200) + 1);
    }

    public String getPassword() {
        return properties.getProperty("me2.student.password.format");
    }

    public static String getVideoPath() {
        return init().properties.getProperty("me2.videoPath");
    }

}
