package com.mizholdings.kaca.agent.app;

import com.alibaba.fastjson.JSONObject;
import com.mizholdings.util.*;
import io.qameta.allure.Step;

public class UserAgent extends MODBase<UserAgent> {

    public UserAgent(User executor) {
        super(executor);
    }


    public JSONObject userInfo(String value, String key) {
        return exec(Common.getMethodName(), Parameter.creat()
                .add(key, value));
    }
    
    @Step("2.1.3 用户信息账户密码")
    public JSONObject userInfo_account(String account) {
        return userInfo(account, "account");
    }

    @Step("2.1.1 用户信息")
    public JSONObject userInfo_userId(String userId) {
        return userInfo(userId, "userId");
    }

}