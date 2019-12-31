package com.mizholdings.kaca.agent.app;

import com.alibaba.fastjson.JSONObject;
import com.mizholdings.kaca.GlobalEnum;
import com.mizholdings.util.Common;
import com.mizholdings.util.MODBase;
import com.mizholdings.util.Parameter;
import com.mizholdings.util.User;
import io.qameta.allure.Step;

public class ReportAgent extends MODBase<ReportAgent> {

    public ReportAgent(User executor) {
        super(executor);
    }


    @Step("18.2.3 查询月份列表")
    public JSONObject month_list(GlobalEnum.SubjectId subjectId, String classId) {
        return exec(Common.getMethodName(), Parameter.creat()
                .add("subjectId", subjectId.value())
                .add("classId", classId)
                .add("year", Common.getYear())
        );
    }

    @Step("18.2.4 查询数据报告详情")
    public JSONObject question_detail(String classId, GlobalEnum.SubjectId subjectId) {
        return exec(Common.getMethodName(), Parameter.creat()
                .add("classId", classId)
                .add("subjectId", subjectId.value())
                .add("year", Common.getYear())
                .add("info", "0")
                .add("page", "1")
                .add("pageSize", "20")
        );
    }

    @Step("18.2.19 根据班级ID获取知识点列表")
    public JSONObject knowledge_list(GlobalEnum.SubjectId subjectId, String classId) {
        return exec(Common.getMethodName(), Parameter.creat()
                .add("subjectId", subjectId.value())
                .add("classId", classId)
                .add("year", Common.getYear())
                .add("type", "0")
        );
    }

    @Step("18.6.1 智能组卷")
    public JSONObject combined_paper(GlobalEnum.SubjectId subjectId, String classId) {
        return exec(Common.getMethodName(), Parameter.creat()
                .add("subjectId", subjectId.value())
                .add("classId", classId));
    }

    @Step("18.6.2 智能组卷换题")
    public JSONObject questions_change(String knowledgeId, String questionId, String questionIds) {
        return exec(Common.getMethodName(), Parameter.creat()
                .add("knowledgeId", knowledgeId)
                .add("questionId", questionId)
                .add("questionIds", questionIds)
        );
    }

    @Step("18.6.3 智能组卷根据知识点加题")
    public JSONObject questions_knowledge(String knowledgeId) {
        return exec(Common.getMethodName(), Parameter.creat()
                .add("knowledgeId", knowledgeId)
        );
    }


}