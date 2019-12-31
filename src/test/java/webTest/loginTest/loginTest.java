package webTest.loginTest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mizholdings.kaca.Global;
import com.mizholdings.kaca.GlobalEnum;
import com.mizholdings.kaca.user.Parent;
import com.mizholdings.kaca.user.Teacher;
import com.mizholdings.util.SampleAssert;
import org.testng.annotations.Test;

public class loginTest {

    private Teacher teacher = new Teacher();

    @Test(description = "账号密码登录")
    public void login_test() {
        Teacher.authAgent().login("19900000003", "000003");
    }

    @Test(description = "用户信息查询")
    public void user_info_test() {
        JSONObject object = teacher.getApp().userAgent().userInfo();
        SampleAssert.assertResult0(object);
    }

    @Test(description = "学校可用学年")
    public void school_years_test() {
        JSONObject object = teacher.getApp().schoolAgent().school_years(Global.Value.schoolId);
        SampleAssert.assertResult0(object);
    }

    @Test(description = "智能组卷")
    public void combined_paper_test() {
        JSONObject object = teacher.getApp().reportAgent().combined_paper(GlobalEnum.SubjectId.MATH, Global.Value.classId);
        SampleAssert.assertResult0(object);
    }

    @Test(description = "用户班级列表")
    public void user_class_test() {
        JSONObject object = teacher.getApp().schoolAgent().user_classes(Global.Value.schoolId);
        SampleAssert.assertResult0(object);
    }

    @Test(description = "班级学科列表")
    public void subject_class_test() {
        JSONObject object = teacher.getApp().schoolAgent().subject_class(Global.Value.classId);
        SampleAssert.assertResult0(object);
    }

    @Test(description = "查询月份列表")
    public void month_list_test() {
        JSONObject object = teacher.getApp().reportAgent().month_list(GlobalEnum.SubjectId.MATH, Global.Value.classId);
        SampleAssert.assertResult0(object);
    }

    @Test(description = "根据班级ID获取知识点列表")
    public void knowledge_list_test() {
        JSONObject object = teacher.getApp().reportAgent().knowledge_list(GlobalEnum.SubjectId.MATH, Global.Value.classId);
        SampleAssert.assertResult0(object);
    }


}
