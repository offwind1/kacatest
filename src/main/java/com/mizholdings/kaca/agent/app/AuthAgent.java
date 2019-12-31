package com.mizholdings.kaca.agent.app;

import com.alibaba.fastjson.JSONObject;
import com.mizholdings.util.*;
import com.mizholdings.util.requests.Request;
import io.qameta.allure.Step;

public class AuthAgent extends MODBase<AuthAgent> {
    private String project;

    public AuthAgent(String project) {
        super();
        this.project = project;
    }

    @Step("1.1.1 密码登入")
    public JSONObject login(String account, String password) {
        return Request.go("auth", Common.getMethodName(), Parameter.creat()
                .add("account", account)
                .add("password", password)
                .getObjectMap(), project).json();
    }

    @Step("1.1.2 外部登入")
    public JSONObject outsideLogin(String code) {
        return Request.go("auth", Common.getMethodName(), Parameter.creat()
                .add("code", code)
                .getObjectMap(), project).json();
    }

    @Step("1.1.3 短信登入")
    public JSONObject randomLogin(String phone, String random) {
        return Request.go("auth", Common.getMethodName(), Parameter.creat()
                .add("phone", phone)
                .add("random", random)
                .getObjectMap(), project).json();
    }

    @Step("1.1.4 公众号code登入")
    public JSONObject webchatLogin(String code) {
        return Request.go("auth", Common.getMethodName(), Parameter.creat()
                .add("code", code)
                .getObjectMap(), project).json();
    }

    @Step("1.1.5公众号密码登入")
    public JSONObject loginForGZ(String account, String password) {
        return Request.go("auth", Common.getMethodName(), Parameter.creat()
                .add("account", account)
                .add("password", password)
                .getObjectMap(), project).json();
    }

    @Step("1.4.1 获取用户权限")
    public JSONObject info() {
        return exec(Common.getMethodName(), Parameter.creat());
    }

    @Step("1.4.2 指派用户权限")
    public JSONObject assign(Parameter parameter) {
        return exec(Common.getMethodName(), parameter);
    }

    @Step("1.4.3 移除用户权限")
    public JSONObject remove(Parameter parameter) {
        return exec(Common.getMethodName(), parameter);
    }
}