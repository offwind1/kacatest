package com.mizholdings.kaca.user;

import com.alibaba.fastjson.JSONObject;
import com.mizholdings.kaca.Global;
import com.mizholdings.kaca.user.serve.App;
import com.mizholdings.util.Common;
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


}
