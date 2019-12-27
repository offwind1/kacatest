package com.mizholdings.kaca.agent.app;

import com.alibaba.fastjson.JSONObject;
import com.mizholdings.util.Common;
import com.mizholdings.util.MODBase;
import com.mizholdings.util.Parameter;
import com.mizholdings.util.User;
import io.qameta.allure.Step;

public class VipAgent extends MODBase<VipAgent> {

    public VipAgent(User executor) {
        super(executor);
    }

    @Step("13.1.1 会员信息")
    public JSONObject vipInfo(String userId) {
        return exec(Common.getMethodName(), Parameter.creat()
                .add("userId", userId));
    }


}