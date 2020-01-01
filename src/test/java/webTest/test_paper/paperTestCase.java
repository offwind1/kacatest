package webTest.test_paper;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mizholdings.kaca.Global;
import com.mizholdings.kaca.GlobalEnum;
import com.mizholdings.kaca.user.Teacher;
import com.mizholdings.util.Common;
import com.mizholdings.util.SampleAssert;
import io.qameta.allure.Allure;
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
        JSONArray array = object.getJSONObject("data").getJSONArray("questionList");
        List<String> list = Common.map(array, "picId");

        object = teacher.getApp().exportAgent().makeTestPagePdf(String.join(",", list), Global.Value.classId, GlobalEnum.SubjectId.MATH);
        SampleAssert.assertResult0(object);


        // 出卷次数+1
        object = teacher.getApp().reportAgent().question_detail(Global.Value.classId, GlobalEnum.SubjectId.MATH);
        SampleAssert.assertResult0(object);

        for (int i = 0; i < array.size(); i++) {
            JSONObject a = array.getJSONObject(i);
            JSONObject b = object.getJSONObject("data").getJSONArray("questionList").getJSONObject(i);

            assert (a.getIntValue("num") + 1 == b.getIntValue("num"));
        }
    }

    @Test(description = "下载原错题+优选错题")
    public void download_question_optimization_test() {
        JSONObject object = teacher.getApp().reportAgent().question_detail(Global.Value.classId, GlobalEnum.SubjectId.MATH);
        SampleAssert.assertResult0(object);
        JSONArray array = object.getJSONObject("data").getJSONArray("questionList");
        List<String> list = Common.map(array, "picId");

        object = teacher.getApp().exportAgent().makeTestPagePdf(String.join(",", list), Global.Value.classId,
                GlobalEnum.SubjectId.MATH, GlobalEnum.Is_need_question.NEED);
        SampleAssert.assertResult0(object);

        // 出卷次数+1
        object = teacher.getApp().reportAgent().question_detail(Global.Value.classId, GlobalEnum.SubjectId.MATH);
        SampleAssert.assertResult0(object);

        for (int i = 0; i < array.size(); i++) {
            JSONObject a = array.getJSONObject(i);
            JSONObject b = object.getJSONObject("data").getJSONArray("questionList").getJSONObject(i);

            assert (a.getIntValue("num") + 1 == b.getIntValue("num"));
        }
    }

    @Test(description = "本地上传视频")
    public void uploaded_video_test() {
        JSONObject object = teacher.getApp().reportAgent().question_detail(Global.Value.classId, GlobalEnum.SubjectId.MATH);
        SampleAssert.assertResult0(object);
        String recommendId = Common.random(object.getJSONObject("data").getJSONArray("questionList")).getString("recommendId");

        //上传视频预备
        object = teacher.getApp().reportAgent().video_prepare(recommendId, Global.Value.schoolId, GlobalEnum.Video_type.CLASS);
        SampleAssert.assertResult0(object);
        String videoId = object.getString("data");

        //上传视频
        object = teacher.getApp().reportAgent().upload_video(videoId);
        SampleAssert.assertResult0(object);

        //检查视频是否上传
        object = teacher.getApp().reportAgent().question_detail(Global.Value.classId, GlobalEnum.SubjectId.MATH);
        SampleAssert.assertResult0(object);
        JSONObject c = Common.filder(object.getJSONObject("data").getJSONArray("questionList"), "recommendId", recommendId);
        if (ObjectUtil.isNotNull(c)) {
            if (ObjectUtil.isNotNull(c.getJSONObject("teachVideo"))) {
                SampleAssert.assertEquals(c.getJSONObject("teachVideo"), "videoId", videoId);
            } else {
                throw new RuntimeException("teachVideo 为空");
            }
        } else {
            throw new RuntimeException("未搜索出指定对象");
        }

        //删除视频
        object = teacher.getApp().reportAgent().delete_video(videoId);
        SampleAssert.assertResult0(object);

        //删除视频是否成功
        object = teacher.getApp().reportAgent().question_detail(Global.Value.classId, GlobalEnum.SubjectId.MATH);
        SampleAssert.assertResult0(object);
        c = Common.filder(object.getJSONObject("data").getJSONArray("questionList"), "recommendId", recommendId);

        if (ObjectUtil.isNotNull(c)) {
            if (ObjectUtil.isNotNull(c.getJSONObject("teachVideo"))) {
                throw new RuntimeException("视频未删除");
            }
        } else {
            throw new RuntimeException("未搜索出指定对象");
        }
    }

    @Test(description = "百度搜索题目")
    public void baidu_test() {
        JSONObject object = teacher.getApp().reportAgent().question_detail(Global.Value.classId, GlobalEnum.SubjectId.MATH);
        SampleAssert.assertResult0(object);
        JSONArray array = object.getJSONObject("data").getJSONArray("questionList");
        String picId = Common.random(array).getString("picId");

        object = teacher.getApp().reportAgent().search_link(picId);
        SampleAssert.assertResult0(object);
    }

    @Test(description = "题目匹配报错")
    public void pipei() {
        Allure.addAttachment("提示", "过程不可逆不能循环执行，暂时不进行测试");
    }

    @Test(description = "18.2.9 查询报告学生统计")
    public void query_student_test() {
        JSONObject object = teacher.getApp().reportAgent().query_student(Global.Value.classId, GlobalEnum.SubjectId.MATH);
    }


}
