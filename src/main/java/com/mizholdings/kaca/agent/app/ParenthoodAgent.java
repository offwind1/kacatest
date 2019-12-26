package com.mizholdings.kaca.agent.app;

import com.alibaba.fastjson.JSONObject;
import com.mizholdings.util.Common;
import com.mizholdings.util.MODBase;
import com.mizholdings.util.Parameter;
import com.mizholdings.util.User;
import io.qameta.allure.Step;

public class ParenthoodAgent extends MODBase<ParenthoodAgent> {

    public ParenthoodAgent(User executor) {
        super(executor);
    }

    @Step("3.1.1 子女列表")
    public JSONObject childs() {
        return exec(Common.getMethodName(), Parameter.creat());
    }

    @Step("3.1.4 解除亲子关系")
    public JSONObject deleteChild(String id) {
        return exec(Common.getMethodName(), Parameter.creat().add("childId", id));
    }

    @Step("3.1.2 添加子女")
    public JSONObject addChild(String name) {
        return exec(Common.getMethodName(), Parameter.creat().add("name", name));
    }

    @Step("3.3.1 当前子女")
    public JSONObject set_currentChild(String childId) {
        return exec(Common.getMethodName(), Parameter.creat().add("childId", childId));
    }

    @Step("3.3.2 获取当前子女")
    public JSONObject get_currentChild() {
        return exec(Common.getMethodName(), Parameter.creat());
    }

    @Step("3.2.1 亲子绑定码")
    public JSONObject bindCode(String childId) {
        return exec(Common.getMethodName(), Parameter.creat().add("childId", childId));
    }

    @Step("3.2.2 亲子绑定")
    public JSONObject bindChild(String code) {
        return exec(Common.getMethodName(), Parameter.creat().add("code", code));
    }

    @Step("3.2.2 亲子绑定")
    public JSONObject bindChild(String cAccount, String cPassword, String name) {
        return exec(Common.getMethodName(), Parameter.creat()
                .add("cAccount", cAccount)
                .add("cPassword", cPassword)
                .add("name", name)
        );
    }


}