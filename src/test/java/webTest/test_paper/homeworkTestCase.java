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
import java.util.stream.Collectors;

public class homeworkTestCase {
    private Teacher teacher = new Teacher();

    @Test(description = "查询作业列表")
    public void homework_list() {
        JSONObject object = teacher.getApp().reportAgent().homework_list(Global.Value.classId, GlobalEnum.SubjectId.MATH);
        SampleAssert.assertResult0(object);
        String homeworkId = Common.random(object.getJSONArray("data")).getString("homeworkId");

        object = teacher.getApp().reportAgent().score_detail_ps(homeworkId);
        SampleAssert.assertResult0(object);
        test(object);

        object = teacher.getApp().reportAgent().score_detail_rate(homeworkId, Global.Value.classId, GlobalEnum.SubjectId.MATH);
        SampleAssert.assertResult0(object);
    }

    public void test(JSONObject object) {
        JSONArray array = object.getJSONObject("data").getJSONArray("questionScoreList");
        System.out.println(array);

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

    @Test
    public void ssss() {
//        teacher.getApp().schoolAgent().teacher_collect()

    }

}
