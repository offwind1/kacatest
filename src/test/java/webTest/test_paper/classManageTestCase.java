package webTest.test_paper;

import com.alibaba.fastjson.JSONObject;
import com.mizholdings.kaca.Global;
import com.mizholdings.kaca.GlobalEnum;
import com.mizholdings.kaca.user.Teacher;
import com.mizholdings.util.Common;
import com.mizholdings.util.SampleAssert;
import org.testng.annotations.Test;

public class classManageTestCase {
    private Teacher teacher = new Teacher();

    @Test(description = "")
    public void class_test() {
        JSONObject object = teacher.getApp().schoolAgent().school_info_detail(Global.Value.schoolId);
        SampleAssert.assertResult0(object);

        object = teacher.getApp().schoolAgent().user_classes(Global.Value.schoolId);
        SampleAssert.assertResult0(object);

        object = teacher.getApp().schoolAgent().members(Global.Value.schoolId, GlobalEnum.MemberType.TEACHER);
        SampleAssert.assertResult0(object);

        object = teacher.getApp().schoolAgent().members(Global.Value.schoolId, GlobalEnum.MemberType.STUDENT);
        SampleAssert.assertResult0(object);
    }


}
