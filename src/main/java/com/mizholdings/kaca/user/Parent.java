package com.mizholdings.kaca.user;

import com.alibaba.fastjson.JSONObject;
import com.mizholdings.kaca.Global;
import com.mizholdings.kaca.user.serve.App;
import com.mizholdings.util.Common;
import com.mizholdings.util.SampleAssert;
import io.qameta.allure.Step;

import java.util.List;

public class Parent {
    private KacaUser parent;

    public Parent() {
        parent = Global.getParent();
    }

    public Parent(String account, String password) {
        parent = KacaUser.kcct(account, password);
    }

    public App getApp() {
        return parent.getApp();
    }

    @Step("清空子女")
    public void clearChildren() {
        for (String id : getChildrenIds()) {
            parent.getApp().parenthoodAgent().deleteChild(id);
        }
    }

    @Step("获取当前全部子女的ids")
    public List<String> getChildrenIds() {
        JSONObject object = parent.getApp().parenthoodAgent().childs();
        return Common.map(object.getJSONArray("data"), "id");
    }

    @Step("加满子女")
    public void addChildrenToFour() {
        int now_count = getChildrenIds().size();
        for (; now_count < 4; now_count++) {
            JSONObject object = parent.getApp().parenthoodAgent().addChild(Common.getRandomNameCN(3));
            SampleAssert.assertResult("0", object);
        }
    }

    @Step("获取当前子女")
    public String current() {
        JSONObject object = parent.getApp().parenthoodAgent().get_currentChild();
        return object.getJSONObject("data").getString("id");
    }
}
