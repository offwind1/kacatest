package wrongbookTest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mizholdings.kaca.GlobalEnum;
import com.mizholdings.kaca.user.Parent;
import com.mizholdings.util.Common;
import com.mizholdings.util.SampleAssert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

public class WrongbookTestCase {
    private Parent parent = new Parent();
    private String childId;//= parent.current();

    @BeforeClass
    public void beforeClass() {
        parent.addChildrenToFour();
        childId = parent.current();
    }

    @Test(description = "上传图片+提交错题", priority = -1)
    public void update_test() {
        String url = "https://homework.mizholdings.com/kacha/xcx/page/4727019111827456.4644792604280832.1577416843855.jpg?imageMogr2/auto-orient";
        JSONObject object = parent.getApp().wrongbookAgent().page(url, childId);
        SampleAssert.assertResult0(object);

        object = parent.getApp().wrongbookAgent().commit(childId, object);
        SampleAssert.assertResult0(object);
    }

    @Test(description = "查看错题详情")
    public void wrong_detail_test() {
        JSONObject object = parent.getApp().wrongbookAgent().questions(childId, GlobalEnum.SubjectId.MATH);
        List<String> list = Common.map(object.getJSONObject("data").getJSONArray("list"), "wrongId");

        for (String wrongId : list) {
            object = parent.getApp().wrongbookAgent().detail(wrongId);
            SampleAssert.assertResult0(object);
        }
    }

    @Test(description = "打印错题")
    public void print_wrong_test_Need_not() {
        JSONObject object = parent.getApp().wrongbookAgent().questions(childId, GlobalEnum.SubjectId.MATH);
        List<String> list = Common.map(object.getJSONObject("data").getJSONArray("list"), "wrongId");
        String ques = String.join(",", list.subList(0, Math.min(list.size(), 4)));
        object = parent.getApp().exportAgent().makeSelectWB(ques, childId, GlobalEnum.Is_need_question.NEED_NOT);
        SampleAssert.assertResult0(object);
    }

    @Test(description = "打印错题+优选错题")
    public void print_wrong_test_need() {
        JSONObject object = parent.getApp().wrongbookAgent().questions(childId, GlobalEnum.SubjectId.MATH);
        List<String> list = Common.map(object.getJSONObject("data").getJSONArray("list"), "wrongId");
        String ques = String.join(",", list.subList(0, Math.min(list.size(), 4)));
        object = parent.getApp().exportAgent().makeSelectWB(ques, childId, GlobalEnum.Is_need_question.NEED);
        SampleAssert.assertResult0(object);
    }

    @Test(description = "作业报告+一错一练知识点错题导出")
    public void report_and_knowledge_export_test() {
        JSONObject object = parent.getApp().wrongbookAgent().child_report(childId);
        SampleAssert.assertResult0(object);
        if (!object.getJSONObject("data").containsKey("knowledgeWrongList")) {
            throw new RuntimeException("还未生成知识点");
        }

        String knowledgeIdsStr = Common.joinJsonArray(object.getJSONObject("data")
                .getJSONArray("knowledgeWrongList"), "knowledgeId", ",");

        object = parent.getApp().exportAgent().makeKnowledgeWB(knowledgeIdsStr, childId);
        SampleAssert.assertResult0(object);
    }


    @Test(description = "删除错题", priority = 1)
    public void delete_question_test() {
        JSONObject object = parent.getApp().wrongbookAgent().questions(childId, GlobalEnum.SubjectId.MATH);
        SampleAssert.assertResult0(object);

        object = parent.getApp().wrongbookAgent().delete(childId, object);
        SampleAssert.assertResult0(object);
    }

}
