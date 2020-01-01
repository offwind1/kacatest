package com.mizholdings.kaca.agent.app;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mizholdings.kaca.Global;
import com.mizholdings.kaca.GlobalEnum;
import com.mizholdings.util.Common;
import com.mizholdings.util.MODBase;
import com.mizholdings.util.Parameter;
import com.mizholdings.util.User;
import io.qameta.allure.Step;

import static com.mizholdings.kaca.Global.Value.classId;

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

    @Step("18.2.9 查询报告学生统计")
    public JSONObject query_student(String classId, GlobalEnum.SubjectId subjectId) {
        return exec(Common.getMethodName(), Parameter.creat()
                .add("classId", classId)
                .add("subjectId", subjectId.value())
                .add("year", Common.getYear())
        );
    }

    @Step("18.2.11 查询校级报告")
    public JSONObject school_data_report(String schoolId, String classId, String timeStamp, GlobalEnum.SubjectId subjectId, GlobalEnum.Period_time periodTime) {
        return exec(Common.getMethodName(), Parameter.creat()
                .add("schoolId", schoolId)
                .add("classId", classId)
                .add("subjectId", subjectId.value())
                .add("timeStamp", timeStamp)
                .add("periodTime", periodTime.value())
        );
    }

    @Step("18.2.12 查询班级报告")
    public JSONObject class_data_report(String classId, String timeStamp, GlobalEnum.SubjectId subjectId, GlobalEnum.Period_time periodTime) {
        return exec(Common.getMethodName(), Parameter.creat()
                .add("classId", classId)
                .add("subjectId", subjectId.value())
                .add("timeStamp", timeStamp)
                .add("periodTime", periodTime.value())
        );
    }

    @Step("18.2.13 查询时间戳")
    public JSONObject time_stamp() {
        return exec(Common.getMethodName(), Parameter.creat()
                .add("year", Common.getYear())
        );
    }

    @Step("18.2.18 获取百度搜索链接")
    public JSONObject search_link(String picId) {
        return exec(Common.getMethodName(), Parameter.creat()
                .add("picId", picId)
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

    @Step("18.3.0 预备教学视频")
    public JSONObject video_prepare(String questionId, String schoolId, GlobalEnum.Video_type videoType) {
        return exec(Common.getMethodName(), Parameter.creat()
                .add("questionId", questionId)
                .add("schoolId", schoolId)
                .add("videoType", videoType.value())
        );
    }

    @Step("18.3.4 删除教学视频")
    public JSONObject delete_video(String videoId) {
        return exec(Common.getMethodName(), Parameter.creat()
                .add("videoId", videoId)
        );
    }

    @Step("18.3.6 获取收藏")
    public JSONObject correction(String uqId, String classId) {
        return exec(Common.getMethodName(), Parameter.creat()
                .add("uqId", uqId)
                .add("classId", classId)
        );
    }

    @Step("18.3.6 获取收藏")
    public JSONObject correction(String CorrectionMarkerList) {
        return exec(Common.getMethodName(), Parameter.creat().add("CorrectionMarkerList", CorrectionMarkerList));
    }

    @Step("18.3.5 上传教学视频")
    public JSONObject upload_video(String videoId, String url, String duration) {
        return exec(Common.getMethodName(), Parameter.creat()
                .add("videoId", videoId)
                .add("url", url)
                .add("duration", duration)
        );
    }

    public JSONObject upload_video(String videoId) {
        return upload_video(videoId, Global.Value.video_url, Global.Value.video_duration);
    }

    @Step("18.4.3 查询作业列表")
    public JSONObject homework_list(String classId, GlobalEnum.SubjectId subjectId) {
        return exec(Common.getMethodName(), Parameter.creat()
                .add("classId", classId)
                .add("subjectId", subjectId.value())
        );
    }


    @Step("18.4.2 查询作业成绩详情")
    public JSONObject score_detail_ps(String homeworkId) {
        return exec(Common.getMethodName(), Parameter.creat()
                .add("homeworkId", homeworkId)
        );
    }

    @Step("18.5.2 查询作业成绩错误率对比")
    public JSONObject score_detail_rate(String homeworkId, String classId, GlobalEnum.SubjectId subjectId) {
        return exec(Common.getMethodName(), Parameter.creat()
                .add("homeworkId", homeworkId)
                .add("classId", classId)
                .add("subjectId", subjectId.value())
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

    @Step("批卷")
    public JSONObject questions_correction(String uqIds) {
        return exec(Common.getMethodName(), Parameter.creat()
                .add("uqIds", uqIds)
        );
    }


}