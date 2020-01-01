package webTest.userTest;

import com.alibaba.fastjson.JSONObject;
import com.mizholdings.kaca.Global;
import com.mizholdings.kaca.user.Parent;
import com.mizholdings.kaca.user.Teacher;
import com.mizholdings.util.Parameter;
import com.mizholdings.util.SampleAssert;
import org.testng.annotations.Test;

public class userTestCase {

    private Teacher teacher = new Teacher();

    @Test(description = "任职为班主任的班级和学科列表")
    public void teaching_classes_test() {
        JSONObject object = teacher.getApp().schoolAgent().teaching_classes(Global.Value.schoolId);
        SampleAssert.assertResult0(object);
    }

    @Test(description = "修改个人信息")
    public void user_edit_test() {
        JSONObject object = new JSONObject();
        object.put("id", teacher.getUserId());
        object.put("name", teacher.getNickname());
        object.put("phone", teacher.getAccount());
        teacher.getApp().userAgent().edit_userInfo(object);
    }

}

