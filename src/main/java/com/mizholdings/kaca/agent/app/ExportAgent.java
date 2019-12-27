package com.mizholdings.kaca.agent.app;

import com.alibaba.fastjson.JSONObject;
import com.mizholdings.kaca.GlobalEnum;
import com.mizholdings.util.Common;
import com.mizholdings.util.MODBase;
import com.mizholdings.util.Parameter;
import com.mizholdings.util.User;
import io.qameta.allure.Step;

public class ExportAgent extends MODBase<ExportAgent> {

    public ExportAgent(User executor) {
        super(executor);
    }

    @Step("12.2 一错一练选择错题导出")
    public JSONObject makeSelectWB(String uqIdsStr, String childId, GlobalEnum.Is_need_question practise) {
        return makeSelectWB(Parameter.creat()
                .add("uqIdsStr", uqIdsStr)
                .add("childId", childId)
                .add("practise", practise.value())
        );
    }

    public JSONObject makeSelectWB(Parameter parameter) {
        return exec(Common.getMethodName(), parameter
        );
    }


}