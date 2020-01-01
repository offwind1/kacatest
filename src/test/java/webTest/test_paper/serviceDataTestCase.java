package webTest.test_paper;

import com.alibaba.fastjson.JSONObject;
import com.mizholdings.kaca.Global;
import com.mizholdings.kaca.GlobalEnum;
import com.mizholdings.kaca.user.Teacher;
import com.mizholdings.util.Common;
import com.mizholdings.util.SampleAssert;
import org.testng.annotations.Test;

public class serviceDataTestCase {
    private Teacher teacher = new Teacher();

    @Test(description = "查询校级报告")
    public void school_data_report_test() {
        JSONObject object = teacher.getApp().reportAgent().time_stamp();
        SampleAssert.assertResult0(object);
        String timeStamp = Common.random(object.getJSONArray("data")).getString("timeStamp");

        object = teacher.getApp().reportAgent().school_data_report(Global.Value.schoolId, Global.Value.classId,
                timeStamp, GlobalEnum.SubjectId.MATH, GlobalEnum.Period_time.YEAR);
        SampleAssert.assertResult0(object);

        object = teacher.getApp().reportAgent().school_data_report(Global.Value.schoolId, Global.Value.classId,
                timeStamp, GlobalEnum.SubjectId.MATH, GlobalEnum.Period_time.QUARTER);
        SampleAssert.assertResult0(object);

        object = teacher.getApp().reportAgent().school_data_report(Global.Value.schoolId, Global.Value.classId,
                timeStamp, GlobalEnum.SubjectId.MATH, GlobalEnum.Period_time.WEEK);
        SampleAssert.assertResult0(object);

        object = teacher.getApp().reportAgent().school_data_report(Global.Value.schoolId, Global.Value.classId,
                timeStamp, GlobalEnum.SubjectId.MATH, GlobalEnum.Period_time.MONTH);
        SampleAssert.assertResult0(object);

        //
        object = teacher.getApp().reportAgent().class_data_report(Global.Value.classId, timeStamp,
                GlobalEnum.SubjectId.MATH, GlobalEnum.Period_time.YEAR);
        SampleAssert.assertResult0(object);

        object = teacher.getApp().reportAgent().class_data_report(Global.Value.classId, timeStamp,
                GlobalEnum.SubjectId.MATH, GlobalEnum.Period_time.QUARTER);
        SampleAssert.assertResult0(object);

        object = teacher.getApp().reportAgent().class_data_report(Global.Value.classId, timeStamp,
                GlobalEnum.SubjectId.MATH, GlobalEnum.Period_time.MONTH);
        SampleAssert.assertResult0(object);

        object = teacher.getApp().reportAgent().class_data_report(Global.Value.classId, timeStamp,
                GlobalEnum.SubjectId.MATH, GlobalEnum.Period_time.WEEK);
        SampleAssert.assertResult0(object);
    }


}
