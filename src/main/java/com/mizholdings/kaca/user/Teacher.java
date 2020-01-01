package com.mizholdings.kaca.user;

import com.alibaba.fastjson.JSONObject;
import com.mizholdings.kaca.Global;
import com.mizholdings.kaca.agent.app.AuthAgent;
import com.mizholdings.kaca.user.serve.App;
import com.mizholdings.util.Common;
import com.mizholdings.util.SampleAssert;
import io.qameta.allure.Step;

import java.util.List;

public class Teacher {
    private KacaUser teacher;

    public Teacher() {
        teacher = Global.getTeacher();
        teacher.setUserType("1");
    }

    public Teacher(String account, String password) {
        teacher = KacaUser.kcsj(account, password);
    }

    public App getApp() {
        return teacher.getApp();
    }

    public static AuthAgent authAgent() {
        return new AuthAgent("kcsj");
    }

    public String getUserId() {
        return teacher.getId();
    }

    public String getNickname() {
        return teacher.getNickname();
    }

    public String getAccount() {
        return teacher.getAccount();
    }


}
