package webTest.test_paper;

import com.alibaba.fastjson.JSONObject;
import com.mizholdings.kaca.Global;
import com.mizholdings.kaca.GlobalEnum;
import com.mizholdings.kaca.user.Teacher;
import com.mizholdings.util.Common;
import com.mizholdings.util.SampleAssert;
import org.testng.annotations.Test;

import java.util.List;

public class paperTestCase {
    private Teacher teacher = new Teacher();

    @Test(description = "一键组卷")
    public void A_key_set_of_volumes_test() {
        JSONObject object = teacher.getApp().reportAgent().knowledge_list(GlobalEnum.SubjectId.MATH,
                Global.Value.classId);
        SampleAssert.assertResult0(object);
        String knowledgeId = Common.random(object.getJSONArray("data")).getString("knowledgeId");

        object = teacher.getApp().reportAgent().questions_knowledge(knowledgeId);
        SampleAssert.assertResult0(object);

        String questionIds = Common.joinJsonArray(object.getJSONArray("data"), "questionId", ",");

        object = teacher.getApp().exportAgent().makeIntelligentTestPdf(questionIds,
                Global.Value.classId, GlobalEnum.SubjectId.MATH);
        SampleAssert.assertResult0(object);
    }

    @Test(description = "换一题")
    public void change_question_test() {
        JSONObject object = teacher.getApp().reportAgent().knowledge_list(GlobalEnum.SubjectId.MATH,
                Global.Value.classId);
        SampleAssert.assertResult0(object);
        String knowledgeId = Common.random(object.getJSONArray("data")).getString("knowledgeId");

        object = teacher.getApp().reportAgent().questions_knowledge(knowledgeId);
        SampleAssert.assertResult0(object);
        List<String> list = Common.map(object.getJSONArray("data"), "questionId");

        object = teacher.getApp().reportAgent().questions_change(knowledgeId, list.get(0), String.join(",", list));
        SampleAssert.assertResult0(object);
    }

    @Test(description = "下载组卷")
    public void download_question_test() {
        JSONObject object = teacher.getApp().reportAgent().question_detail(Global.Value.classId, GlobalEnum.SubjectId.MATH);
        SampleAssert.assertResult0(object);
        List<String> list = Common.map(object.getJSONObject("data").getJSONArray("questionList"), "picId");

        object = teacher.getApp().exportAgent().makeTestPagePdf(String.join(",", list), Global.Value.classId, GlobalEnum.SubjectId.MATH);
        SampleAssert.assertResult0(object);
    }

    @Test(description = "下载原错题+优选错题")
    public void download_question_optimization_test() {
        JSONObject object = teacher.getApp().reportAgent().question_detail(Global.Value.classId, GlobalEnum.SubjectId.MATH);
        SampleAssert.assertResult0(object);
        List<String> list = Common.map(object.getJSONObject("data").getJSONArray("questionList"), "picId");

        object = teacher.getApp().exportAgent().makeTestPagePdf(String.join(",", list), Global.Value.classId,
                GlobalEnum.SubjectId.MATH, GlobalEnum.Is_need_question.NEED);
        SampleAssert.assertResult0(object);
    }


}
