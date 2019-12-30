package com.mizholdings.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mizholdings.kaca.GlobalEnum;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Common {
    private static Random random = new Random();
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String TEMP_STRING = "接口测试生成课";

    public static String getNowTime() {
        return format.format(new Date());
    }

    public static String getNowDay() {
        return DateUtil.today();
    }

    public static String getEndTime(int days) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, days);
        return format.format(c.getTime());
    }

    public static String getRandomNameCN(int len) {
        String ret = "";
        for (int i = 0; i < len; i++) {
            String str = null;
            int hightPos, lowPos; // 定义高低位
            Random random = new Random();
            hightPos = (176 + Math.abs(random.nextInt(39))); // 获取高位值
            lowPos = (161 + Math.abs(random.nextInt(93))); // 获取低位值
            byte[] b = new byte[2];
            b[0] = (new Integer(hightPos).byteValue());
            b[1] = (new Integer(lowPos).byteValue());
            try {
                str = new String(b, "GBK"); // 转成中文
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
            ret += str;
        }
        return ret;
    }

    public static String getMethodName() {
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        StackTraceElement e = stacktrace[2];
        return e.getMethodName();
    }

    public static String creatRandomString() {
        return TEMP_STRING + DateUtil.format(DateUtil.date(), "yyyy-MM-dd_HH:mm");
//        return UUID.randomUUID().toString().replace("-", "").substring(0, 4);
    }

    public static JSONObject filder(JSONArray array, String key, String tag) {
        List<Object> list = array.stream().filter(i -> {
            JSONObject o = (JSONObject) i;
            return key.equals(o.getString(tag));
        }).collect(Collectors.toList());

        if (list.size() > 0) {
            return (JSONObject) list.get(0);
        }
        return null;
    }

    public static String get(JSONObject object, String key) {
        return object.getString(key);
    }

    public static String randomJoinFromList(String format, List<String> list) {
        Collections.shuffle(list);
        return String.join(format, list.subList(0, list.size() / 3));
    }

    public static JSONObject random(JSONArray array) {
        return array.getJSONObject(random.nextInt(array.size()));
    }

    public static List<String> map(JSONArray array, String tag) {
        if (ObjectUtil.isNotNull(array)) {
            return array.stream().map(i -> {
                JSONObject o = (JSONObject) i;
                return o.getString(tag);
            }).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public static int range(int a, int b) {
        return a + random.nextInt(b - a);
    }


    public static JSONObject pageData_to_CommitDate(JSONObject object) {
        int pageId = object.getJSONObject("data").getIntValue("pageId");

        List<JSONObject> list = object.getJSONObject("data").getJSONArray("questions").stream().map(i -> {
            JSONObject o = (JSONObject) i;
            JSONObject n = new JSONObject();

            JSONArray a = new JSONArray();
            a.add(o.getJSONArray("areas").getJSONObject(0).getString("imgUrl"));
            System.out.println(a.toJSONString());

            n.put("page", pageId);
            n.put("num", o.getIntValue("num"));
            n.put("orderIndex", o.getIntValue("num"));
            n.put("x", o.getJSONArray("areas").getJSONObject(0).getIntValue("x"));
            n.put("y", o.getJSONArray("areas").getJSONObject(0).getIntValue("y"));
            n.put("width", o.getJSONArray("areas").getJSONObject(0).getIntValue("width"));
            n.put("height", o.getJSONArray("areas").getJSONObject(0).getIntValue("height"));
            n.put("questionUrls", a);
            n.put("collect", 1);
            n.put("isEditByUser", false);
            n.put("type", 0);
            n.put("mark", 1);
            n.put("choiceAnswer", "");

            return n;
        }).collect(Collectors.toList());

        JSONArray array = new JSONArray();
        array.addAll(list);

        JSONArray pages = new JSONArray();
        JSONObject page = new JSONObject();
        page.put("pageId", pageId);
        page.put("questions", array);
        pages.add(page);

        JSONObject data = new JSONObject();
//        data.put("childId", childId);
        data.put("subjectId", GlobalEnum.SubjectId.MATH.value());
        data.put("pages", pages);

        return data;
    }

}
