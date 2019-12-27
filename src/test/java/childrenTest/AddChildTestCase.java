package childrenTest;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import com.alibaba.fastjson.JSONObject;
import com.mizholdings.kaca.Global;
import com.mizholdings.kaca.user.KacaUser;
import com.mizholdings.kaca.user.Parent;
import com.mizholdings.util.Common;
import com.mizholdings.util.SampleAssert;
import io.qameta.allure.Step;
import org.jsoup.helper.DataUtil;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class AddChildTestCase {

    private Parent parent = new Parent();

    @BeforeClass
    public void beforeClass() {
        parent.clearChildren();
    }

    @Step("添加子女")
    public void addChild() {
        JSONObject object = parent.getApp().parenthoodAgent().addChild(Common.getRandomNameCN(3));
        SampleAssert.assertResult("0", object);
        SampleAssert.assertMsg("添加成功", object);
        vip_test(object.getJSONObject("data").getString("userId"));
    }

    @Step("vip测试")
    public void vip_test(String childId) {
        JSONObject object = parent.getApp().vipAgent().vipInfo(childId);
        SampleAssert.assertEquals(object, "member", "1");
        SampleAssert.assertEquals(object, "endTime", DateUtil.offsetDay(DateUtil.date(), 15).toString("yyyy-MM-dd"));
    }

    @Test(description = "添加子女")
    public void addChild_test() {
        addChild();
    }

    @Test(description = "最多只能添加4个子女", dependsOnMethods = {"addChild_test"})
    public void most_add_four_child_test() {
        for (int i = 0; i < 3; i++) {
            addChild();
        }

        JSONObject object = parent.getApp().parenthoodAgent().addChild(Common.getRandomNameCN(3));
        SampleAssert.assertMsg("子女数目超过限制", object);
        SampleAssert.assertResult("-1", object);
    }

    @Test(description = "切换子女", dependsOnMethods = {"most_add_four_child_test"})
    public void current_child_test() {
        List<String> ids = parent.getChildrenIds();
        JSONObject object = parent.getApp().parenthoodAgent().get_currentChild();
        SampleAssert.assertResult0(object);
        SampleAssert.assertEquals(object.getJSONObject("data"), "id", ids.get(0));

        object = parent.getApp().parenthoodAgent().set_currentChild(ids.get(ids.size() - 1));
        SampleAssert.assertResult0(object);

        object = parent.getApp().parenthoodAgent().get_currentChild();
        SampleAssert.assertResult0(object);
        SampleAssert.assertEquals(object.getJSONObject("data"), "id", ids.get(ids.size() - 1));
    }

    @Test(description = "解绑子女", dependsOnMethods = {"current_child_test"})
    public void delete_child_test() {
        List<String> ids = parent.getChildrenIds();
        JSONObject object = parent.getApp().parenthoodAgent().deleteChild(ids.get(0));
        SampleAssert.assertResult0(object);
    }

    @Test(description = "账号添加子女", dependsOnMethods = {"delete_child_test"})
    public void account_add_child_test() {
        JSONObject object = parent.getApp().parenthoodAgent().get_currentChild();
        SampleAssert.assertResult0(object);
        String account = object.getJSONObject("data").getString("account");
        String id = object.getJSONObject("data").getString("id");
        String name = object.getJSONObject("data").getString("name");

        object = parent.getApp().parenthoodAgent().deleteChild(id);
        SampleAssert.assertResult0(object);

        object = parent.getApp().userAgent().userInfo_account(account);
        SampleAssert.assertResult0(object);

        object = parent.getApp().schoolAgent().classes(id);
        SampleAssert.assertResult0(object);

        object = parent.getApp().parenthoodAgent().bindChild(account, "123456", name);
        SampleAssert.assertResult0(object);

        assert parent.getChildrenIds().contains(id);
    }

    @Test(description = "微信邀请家长绑定该子女", dependsOnMethods = {"account_add_child_test"})
    public void bind_test() {
        List<String> ids = parent.getChildrenIds();
        JSONObject object = parent.getApp().parenthoodAgent().bindCode(ids.get(0));
        SampleAssert.assertResult0(object);
        String code = object.getJSONObject("data").getString("code");

        object = parent.getApp().parenthoodAgent().deleteChild(ids.get(0));
        SampleAssert.assertResult0(object);

        object = parent.getApp().parenthoodAgent().bindChild(code);
        SampleAssert.assertResult0(object);

        assert parent.getChildrenIds().contains(ids.get(0));
    }

    @Test(description = "扫码加入班级", dependsOnMethods = {"bind_test"})
    public void scan_join_class() {
        List<String> ids = parent.getChildrenIds();
        JSONObject object = parent.getApp().schoolAgent().join("301", ids.get(0));
        SampleAssert.assertResult0(object);
    }
}
