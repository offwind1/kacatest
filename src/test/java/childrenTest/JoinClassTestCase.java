package childrenTest;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.mizholdings.kaca.GlobalEnum;
import com.mizholdings.kaca.user.Parent;
import com.mizholdings.util.Common;
import com.mizholdings.util.SampleAssert;
import io.qameta.allure.Step;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class JoinClassTestCase {

    private Parent parent = new Parent();
    private String classId = "1296";
    private String schoolId = "9060";

    @BeforeClass
    public void beforeClass() {
        parent.addChildrenToFour();
    }

    @Test(description = "用户加入班级")
    public void join_class_test() {
        JSONObject object = parent.getApp().schoolAgent().school_info_detail(schoolId);
        String effEnd = object.getJSONObject("data").getString("effEnd");
        String vipTime = DateUtil.parse(effEnd, "yyyy/MM/dd").offset(DateField.DAY_OF_YEAR, 15).toString("yyyy-MM-dd");

        List<String> list = parent.getChildrenIds();
        parent.getApp().schoolAgent().join(classId, list.get(0));
        vip_test(list.get(0), vipTime);
    }

    @Step("vip测试")
    private void vip_test(String childId, String vipTime) {
        JSONObject object = parent.getApp().vipAgent().vipInfo(childId);
        SampleAssert.assertEquals(object, "member", "2");
        SampleAssert.assertEquals(object, "endTime", vipTime);
    }

    @Test(description = "加入班级中有重名的学校")
    public void join_class_repeat_test() {
        //获得班级中的用户
        JSONObject object = parent.getApp().schoolAgent().members(schoolId, classId, GlobalEnum.MemberType.STUDENT);
        SampleAssert.assertResult0(object);
        List<String> names = Common.map(object.getJSONArray("data"), "userName");
        List<String> ids = parent.getChildrenIds();

        object = parent.getApp().parenthoodAgent().deleteChild(ids.get(0));
        SampleAssert.assertResult0(object);

        object = parent.getApp().parenthoodAgent().addChild(names.get(0));
        SampleAssert.assertResult0(object);

        object = parent.getApp().schoolAgent().join(classId, object.getJSONObject("data").getString("userId"));
        SampleAssert.assertEquals(object, "msg", "班级存在同名学生,请确认后再尝试！");
        SampleAssert.assertEquals(object, "result", "40001");
    }
}
