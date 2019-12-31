package com.mizholdings.kaca.agent.app;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mizholdings.kaca.GlobalEnum;
import com.mizholdings.util.Common;
import com.mizholdings.util.MODBase;
import com.mizholdings.util.Parameter;
import com.mizholdings.util.User;
import io.qameta.allure.Step;

import java.util.List;
import java.util.stream.Collectors;

public class WrongbookAgent extends MODBase<WrongbookAgent> {

    public WrongbookAgent(User executor) {
        super(executor);
    }

    @Step("11.1.2 查看错题列表")
    public JSONObject questions(String childId, GlobalEnum.SubjectId subjectId) {
        DateTime dateTime = DateUtil.offsetMonth(DateUtil.date(), -3);
        return exec(Common.getMethodName(), Parameter.creat()
                .add("childId", childId)
                .add("subjectId", subjectId.value())
                .add("startTime", DateUtil.parse(
                        String.format("%d-%d",
                                dateTime.year(),
                                dateTime.monthStartFromOne()),
                        "yyyy-MM")
                        .toString("yyyy-MM-dd"))
                .add("pageSize", "50")
                .add("page", "1")
        );
    }


    @Step("11.1.3 删除错题")
    public JSONObject delete(String childId, JSONObject object) {
        JSONObject data = new JSONObject();
        data.put("childId", childId);
        data.put("questions", object.getJSONObject("data").getJSONArray("list"));
        return delete(data);
    }

    @Step("11.1.3 删除错题")
    public JSONObject delete(JSONObject object) {
        return exec(Common.getMethodName(), object);
    }

    @Step("11.1.4 查看错题详情")
    public JSONObject detail(String wrongId) {
        return exec(Common.getMethodName(), Parameter.creat().add("wrongId", wrongId));
    }

    @Step("11.2.1上传页面")
    public JSONObject page(String url, String childId) {
        return exec(Common.getMethodName(), Parameter.creat()
                .add("url", url)
                .add("childId", childId)
        );
    }


    @Step("11.2.2 提交错题")
    public JSONObject commit(String childId, JSONObject jsonObject) {
        JSONObject object = pageData_to_CommitDate(jsonObject);
        object.put("childId", childId);
        return exec(Common.getMethodName(), object);
    }

    @Step("11.2.2 提交错题")
    public JSONObject commit(JSONObject object) {
        return exec(Common.getMethodName(), object);
    }


    @Step("11.2.2 提交错题")
    public JSONObject child_report(String childId) {
        return exec(Common.getMethodName(), Parameter.creat().add("childId", childId));
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
        data.put("subjectId", GlobalEnum.SubjectId.MATH.value());
        data.put("pages", pages);

        return data;
    }


}