package com.mizholdings.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mizholdings.util.requests.Request;
import com.mizholdings.util.requests.RequestData;
import com.mizholdings.util.requests.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;

public class MODBase<T extends MODBase> {
    private User executor;
    private final static Log logger = LogFactory.getLog(MODBase.class);
    private String agentName;

    public MODBase() {
        String[] list = getClass().getName().replace("Agent", "").split("\\.");
        agentName = list[list.length - 1];
    }

    public MODBase(User executor) {
        this();
        this.executor = executor;
    }

    /**
     * 设置执行人
     *
     * @param user 执行人
     * @return 返回对象本身
     */
    public T setExecutor(User user) {
        executor = user;
        return (T) this;
    }

    public JSONObject exec(String funcName) {
        return exec(funcName, Parameter.creat());
    }

    public JSONObject exec(String funcName, Parameter parameter) {
        RequestData r = new RequestData();
        r.setBody(parameter.getObjectMap());

        if (null != executor) {
            r.setHeader("Authorization", ((User) executor).getToken());
            r.setHeader("userType", ((User) executor).getUserType());
        }
        return _exec(funcName, r);
    }

//    public JSONObject exec(String funcName, JSONObject object) {
//        if (null != executor) {
//            object.put("token", ((User) executor).getToken());
//            object.put("userType", ((User) executor).getUserType());
//        }
//        return _exec(funcName, object);
//    }

    public JSONObject exec(String funcName, JSON object) {
        RequestData r = new RequestData();
        r.setBody(object);

        if (null != executor) {
            r.setHeader("Authorization", ((User) executor).getToken());
            r.setHeader("userType", ((User) executor).getUserType());
        }
        return _exec(funcName, r);
    }


//    public JSONObject exec(String funcName, Map<String, Object> map) {
//        return _exec(funcName, map);
//    }

//    private JSONObject _exec(String funcName, Map<String, Object> map) {
//        Response response = Request.go(agentName, funcName, map);
//        if (response.state) {
//            logger.info(response.json().toJSONString());
//            return response.json();
//        }
//        logger.error(response.text);
//        throw new RuntimeException(response.text);
//    }

    private JSONObject _exec(String funcName, RequestData requestData) {
        Response response = Request.go(agentName, funcName, requestData);

        if (response.state) {
            logger.info(response.json().toJSONString());
            return response.json();
        }
        logger.error(response.text);
        throw new RuntimeException(response.text);
    }

}
