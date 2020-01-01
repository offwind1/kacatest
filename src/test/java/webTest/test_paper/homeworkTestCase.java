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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

public class homeworkTestCase {
    private Teacher teacher = new Teacher();

    private String homeworkId;
    private JSONObject data;

    //    @BeforeClass
    @Test(description = "查询作业成绩详情")
    public void homework_list_test() {
        JSONObject object = teacher.getApp().reportAgent().homework_list(Global.Value.classId, GlobalEnum.SubjectId.MATH);
        SampleAssert.assertResult0(object);
        homeworkId = Common.random(object.getJSONArray("data")).getString("homeworkId");

        data = teacher.getApp().reportAgent().score_detail_ps(homeworkId);
        SampleAssert.assertResult0(data);
    }


    @Test(description = "查询作业成绩错误率对比", dependsOnMethods = {"homework_list_test"})
    public void homework_list() {
        JSONObject object = teacher.getApp().reportAgent().score_detail_rate(homeworkId, Global.Value.classId, GlobalEnum.SubjectId.MATH);
        SampleAssert.assertResult0(object);
    }

    @Test(description = "批改作业", dependsOnMethods = {"homework_list_test"})
    public void test() {
        JSONArray array = data.getJSONObject("data").getJSONArray("questionScoreList");
        for (int i = 0; i < array.size(); i++) {
            JSONObject o = array.getJSONObject(i);
            List<String> list = Common.map(o.getJSONArray("userAnswerList"), "uqId");
            String uqIds = String.join(",", list);
            o = teacher.getApp().reportAgent().questions_correction(uqIds);
            SampleAssert.assertResult0(o);

            JSONArray a = o.getJSONObject("data").getJSONArray("homeworkCorrectionList");

            List<JSONObject> list1 = a.stream().map(j -> {
                JSONObject p = (JSONObject) j;
                p.put("teacherCollect", 1);
                return p;
            }).collect(Collectors.toList());

            o = teacher.getApp().reportAgent().correction(a.toJSONString());
            SampleAssert.assertResult0(o);
        }

    }

    @Test(description = "教师收藏", dependsOnMethods = {"homework_list_test"})
    public void teacher_collect_test() {
        String uqId = Common.random(data.getJSONObject("data").getJSONArray("questionScoreList")).getString("questionId");
        JSONObject object = teacher.getApp().schoolAgent().teacher_collect(uqId, GlobalEnum.Collect_type.NEW_COLLECT);
        SampleAssert.assertResult0(object);

        object = teacher.getApp().schoolAgent().teacher_collect(uqId, GlobalEnum.Collect_type.CANCEL_COLLECT);
        SampleAssert.assertResult0(object);
    }

}
