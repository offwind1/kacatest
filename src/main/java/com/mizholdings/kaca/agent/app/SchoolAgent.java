package com.mizholdings.kaca.agent.app;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.mizholdings.kaca.GlobalEnum;
import com.mizholdings.util.Common;
import com.mizholdings.util.MODBase;
import com.mizholdings.util.Parameter;
import com.mizholdings.util.User;
import io.qameta.allure.Step;
import org.jsoup.helper.DataUtil;

import javax.xml.crypto.Data;

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

    @Step("4.1.3 成员列表")
    public JSONObject members(String schoolId, String classId, GlobalEnum.MemberType memberType) {
        return exec(Common.getMethodName(), Parameter.creat()
                .add("schoolId", schoolId)
                .add("classId", classId)
                .add("type", memberType.value())
        );
    }

    @Step("4.1.3 成员列表")
    public JSONObject members(String schoolId, GlobalEnum.MemberType type) {
        return exec(Common.getMethodName(), Parameter.creat()
                .add("schoolId", schoolId)
                .add("page", "1")
                .add("pageSize", "99")
                .add("type", type.value())
        );
    }

    @Step("4.3.1教师星标收藏")
    public JSONObject teacher_collect(String uqId, GlobalEnum.Collect_type type) {
        return exec(Common.getMethodName(), Parameter.creat()
                .add("uqId", uqId)
                .add("type", type.value())
        );
    }

    @Step("4.4.1 学校可用学年")
    public JSONObject school_years(String schoolId) {
        return exec(Common.getMethodName(), Parameter.creat()
                .add("schoolId", schoolId)
        );
    }


    @Step("4.1.6 班级信息查询")
    public JSONObject classes(String userId) {
        return exec(Common.getMethodName(), Parameter.creat()
                .add("userId", userId));
    }


    @Step("4.5.2 班级学科列表")
    public JSONObject subject_class(String classId) {
        return exec(Common.getMethodName(), Parameter.creat()
                .add("classId", classId)
                .add("year", Common.getYear())
        );
    }

    @Step("4.5.4 任职为班主任的班级和学科列表（班主任和校管）")
    public JSONObject teaching_classes(String schoolId) {
        return exec(Common.getMethodName(), Parameter.creat()
                .add("schoolId", schoolId)
                .add("year", Common.getYear())
        );
    }


    @Step("4.6.2 学校信息返回")
    public JSONObject school_info_detail(String schoolId) {
        return exec(Common.getMethodName(), Parameter.creat()
                .add("schoolId", schoolId));
    }

    @Step("4.8.1 用户班级列表")
    public JSONObject user_classes(String schoolId) {
        return exec(Common.getMethodName(), Parameter.creat()
                .add("schoolId", schoolId)
                .add("year", Common.getYear())
        );
    }


}