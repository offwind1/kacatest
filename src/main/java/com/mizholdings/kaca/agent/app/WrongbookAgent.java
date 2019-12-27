package com.mizholdings.kaca.agent.app;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.mizholdings.kaca.GlobalEnum;
import com.mizholdings.util.Common;
import com.mizholdings.util.MODBase;
import com.mizholdings.util.Parameter;
import com.mizholdings.util.User;
import io.qameta.allure.Step;

public class WrongbookAgent extends MODBase<WrongbookAgent> {

    public WrongbookAgent(User executor) {
        super(executor);
    }

    @Step("11.1.2 查看错题列表")
    public JSONObject questions(String childId, GlobalEnum.SubjectId subjectId) {
        DateTime dateTime = DateUtil.offsetMonth(DateUtil.date(), -3);
        return exec(Common.getMethodName(), Parameter.creat()
                .add("childId", childId)
                .add("subjectId", subjectId.value())
                .add("startTime", DateUtil.parse(
                        String.format("%d-%d",
                                dateTime.year(),
                                dateTime.monthStartFromOne()),
                        "yyyy-MM")
                        .toString("yyyy-MM-dd"))
                .add("pageSize", "50")
                .add("page", "1")
        );
    }

    @Step("11.1.4 查看错题详情")
    public JSONObject detail(String wrongId) {
        return exec(Common.getMethodName(), Parameter.creat().add("wrongId", wrongId));
    }

    @Step("11.2.1上传页面")
    public JSONObject page(String url, String childId) {
        return exec(Common.getMethodName(), Parameter.creat()
                .add("url", url)
                .add("childId", childId)
        );
    }


    @Step("11.2.2 提交错题")
    public JSONObject commit(String childId, JSONObject jsonObject) {
        JSONObject object = Common.pageData_to_CommitDate(jsonObject);
        object.put("childId", childId);
        return exec(Common.getMethodName(), object);
    }

    @Step("11.2.2 提交错题")
    public JSONObject commit(JSONObject object) {
        return exec(Common.getMethodName(), object);
    }


}