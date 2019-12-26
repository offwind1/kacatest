package com.mizholdings.kaca.agent.app;

import com.alibaba.fastjson.JSONObject;
import com.mizholdings.util.Common;
import com.mizholdings.util.MODBase;
import com.mizholdings.util.Parameter;
import com.mizholdings.util.User;
import io.qameta.allure.Step;

public class SchoolAgent extends MODBase<SchoolAgent> {

    public SchoolAgent(User executor) {
        super(executor);
    }

    @Step("4.1.1 学生加入班级")
    public JSONObject join(String classId, String childId) {
        return exec(Common.getMethodName(), Parameter.creat()
                .add("classId", classId)
                .add("childId", childId));
    }

    @Step("4.1.6 班级信息查询")
    public JSONObject classes(String userId) {
        return exec(Common.getMethodName(), Parameter.creat()
                .add("userId", userId));
    }


}