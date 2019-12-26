package com.mizholdings.kaca.agent.app;

import com.alibaba.fastjson.JSONObject;
import com.mizholdings.util.*;
import io.qameta.allure.Step;

public class AuthAgent extends MODBase<AuthAgent> {

    public AuthAgent(User executor) {
        super(executor);
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