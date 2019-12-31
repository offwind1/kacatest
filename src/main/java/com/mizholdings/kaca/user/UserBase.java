package com.mizholdings.kaca.user;

import com.alibaba.fastjson.JSONObject;
import com.mizholdings.kaca.user.serve.App;
import com.mizholdings.kaca.user.serveInterface.appInterface;
import com.mizholdings.util.*;
import com.mizholdings.util.requests.Request;
import io.qameta.allure.Step;

public class UserBase extends User implements appInterface {
    protected JSONObject object;
    protected String account;
    protected String password;
    protected App app;

    public UserBase(String account, String password, String project) {
        this.account = account;
        this.password = password;
        init(login(project));
        app = new App(this);
    }

    public App getApp() {
        return app;
    }

    private void init(JSONObject object) {
        SampleAssert.assertEquals("登入成功！", object);
        this.object = object;
        token = object.getJSONObject("data").getString("token");
        id = object.getJSONObject("data").getString("id");
    }

    @Step("用户登陆(手机/邮箱/用户名) app")
    public JSONObject login(String project) {
        return Request.go("auth", "login", Parameter.creat()
                .add("account", account)
                .add("password", password)
                .getObjectMap(), project).json();
    }

    public String getNickname() {
        return this.object.getJSONObject("data").getString("nickname");
    }

//    public String getUserType() {
//        return this.object.getJSONObject("data").getString("userType");
//    }
}
