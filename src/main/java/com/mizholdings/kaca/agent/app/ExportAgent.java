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

    @Step("12.2 一错一练选择错题导出")
    public JSONObject makeKnowledgeWB(String knowledgeIdsStr, String childId) {
        return makeKnowledgeWB(Parameter.creat()
                .add("knowledgeIdsStr", knowledgeIdsStr)
                .add("childId", childId)
                .add("practise", "0")
                .add("beginDate", Common.getEndTime(-13))
                .add("endDate", Common.getNowDay())
        );
    }

    public JSONObject makeTestPagePdf(String uqIdsStr, String childId, GlobalEnum.SubjectId subjectId) {
        return makeTestPagePdf(uqIdsStr, childId, subjectId, GlobalEnum.Is_need_question.NEED_NOT);
    }

    @Step("12.7 查询组卷样式PDF")
    public JSONObject makeTestPagePdf(String uqIdsStr, String classId, GlobalEnum.SubjectId subjectId, GlobalEnum.Is_need_question practise) {
        return exec(Common.getMethodName(), Parameter.creat()
                .add("uqIdsStr", uqIdsStr)
                .add("classId", classId)
                .add("subjectId", subjectId.value())
                .add("practise", practise.value())
        );
    }

    @Step("12.9 智能组卷中下载试卷")
    public JSONObject makeIntelligentTestPdf(String questionIds, String classId, GlobalEnum.SubjectId subjectId) {
        return exec(Common.getMethodName(), Parameter.creat()
                .add("subjectId", subjectId.value())
                .add("questionIds", questionIds)
                .add("classId", classId)
                .add("isAnswer", "1")
        );
    }

    public JSONObject makeKnowledgeWB(Parameter parameter) {
        return exec(Common.getMethodName(), parameter);
    }


}