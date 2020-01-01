package com.mizholdings.util.requests;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpRequest;
//import com.github.fge.jackson.JsonLoader;
//import com.github.fge.jackson.JsonNodeReader;
//import com.github.fge.jsonschema.core.exceptions.ProcessingException;
//import com.github.fge.jsonschema.core.report.ProcessingReport;
//import com.github.fge.jsonschema.main.JsonSchema;
//import com.github.fge.jsonschema.main.JsonSchemaFactory;
import cn.hutool.http.HttpUtil;
import com.mizholdings.kaca.Global;
import com.mizholdings.util.XmlTool.ElementMine;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.util.Map;

public class Request {
    private final static Log logger = LogFactory.getLog(Request.class);

    private static final String SCHEMA = "schema";

    public static Response go(String agent, String funcName, RequestData requestData, String project) {
        logger.info("\n");
        logger.info(agent + ":" + funcName);

        ElementMine elementMine = Global.getServe();
        String host = elementMine.getAttribute("host");

        ElementMine interfaces = elementMine.getElementsByTagNameAndValue("interfaces", agent);
        String interfaces_name = interfaces.getAttribute("name");
        String interfaces_value = interfaces.getAttribute("value");

        ElementMine in = interfaces.getElementsByTagNameAndValue("interface", funcName);
        String interfaceName = in.getAttribute("name");
        String interface_url = in.getAttribute("url");
        String interface_value = in.getAttribute("value");
        String method = in.getAttribute("method");

        String url = "";
        if (ObjectUtil.isNotNull(project)) {
            url = host + interface_url + "/" + project;
        } else {
            url = host + interface_url;
        }

        logger.info("interfaces_value:" + interfaces_value + " # interface_url:" + interface_url);
        Response response = doRequest(url, method, requestData, interfaces_name, interfaceName);
        logger.info("response:" + response.text);

        Allure.addAttachment("response", response.text);
        String schema_path = "schema\\" + interfaces_value + "\\" + interface_value + ".json";
        if (response.state && FileUtil.isFile(schema_path)) {
            try {
                JsonSchema(schema_path, response, requestData);
            } catch (com.mizholdings.util.requests.SchemaCheckException e) {
                throw new RuntimeException(e);
            }
        }
        return response;
    }

    //
    public static Response go(String agent, String funcName, Map<String, Object> map, String project) {
        RequestData data = new RequestData();
        data.setBody(map);
        return go(agent, funcName, data, project);
    }

    public static Response go(String agent, String funcName, RequestData map) {
        return go(agent, funcName, map, null);
    }


    public static void JsonSchema(String schema_path, Response response, RequestData requestData) {
        Map<String, Object> map = (Map<String, Object>) requestData.getBody();
        String schema_string = readFile(schema_path);
        String schema_json = formatString(schema_string, map);
        JsonSchema(readJsonString(schema_json), readJsonString(response.text));
    }

    public static String readFile(String path) {
        return FileUtil.readString(path, "UTF-8");
    }

    public static String formatString(String str, Map<String, Object> map) {
        for (String key : map.keySet()) {
            str = str.replace("{{" + key + "}}", String.valueOf(map.get(key)));
        }
        return str;
    }

    public static void JsonSchema(String schema_path, String json) {
        org.json.JSONObject Schema = readJsonFile(schema_path);
        org.json.JSONObject data = new org.json.JSONObject(json);
        JsonSchema(Schema, data);
    }

    public static void JsonSchema(org.json.JSONObject schema_json, org.json.JSONObject json) {
        org.everit.json.schema.Schema schema = SchemaLoader.load(schema_json);
        try {
            schema.validate(json);
        } catch (ValidationException e) {
            throw new SchemaCheckException(e.getMessage());
        }
    }

    public static org.json.JSONObject readJsonFile(String filePath) {
        InputStream inputStream = Request.class.getClassLoader().getResourceAsStream(filePath);
        org.json.JSONObject schema = new org.json.JSONObject(new JSONTokener(inputStream));
        return schema;
    }

    public static org.json.JSONObject readJsonString(String json_string) {
        return new org.json.JSONObject(json_string);
    }

    @Step("{model}:{name}")
    private static Response doRequest(String url, String method, RequestData requestData, String model, String name) {
        Object body = requestData.getBody();
        String response = "";

        logger.info(model + name);
        logger.info("URL:" + url);
        logger.info("Method:" + method);

        if ("POST".equals(method)) {
            HttpRequest httpRequest = HttpRequest.post(url);
            Map<String, String> map = requestData.getHeaders();

            for (String key : map.keySet()) {
                logger.info(key + ":" + map.get(key));
                httpRequest = httpRequest.header(key, map.get(key));
            }

            if (body instanceof com.alibaba.fastjson.JSON) {
                com.alibaba.fastjson.JSON o = (com.alibaba.fastjson.JSON) body;
                logger.info("body:" + o.toJSONString());
                response = httpRequest.body(o.toJSONString()).execute().body();
            } else if (body instanceof Map) {
                Map<String, Object> m = (Map<String, Object>) body;
                logger.info("form:" + m);
                response = httpRequest.form(m).execute().body();
            }
        } else if ("GET".equals(method)) {
            HttpRequest httpRequest = HttpRequest.get(url);
            Map<String, String> map = requestData.getHeaders();

            for (String key : map.keySet()) {
                logger.info(key + ":" + map.get(key));
                httpRequest = httpRequest.header(key, map.get(key));
            }

            Map<String, Object> m = (Map<String, Object>) body;
            logger.info("form:" + m);
            response = httpRequest.form(m).execute().body();
        }

        return new Response(response);
    }
}

class SchemaCheckException extends RuntimeException {
    public SchemaCheckException(String message) {
        super(message);
    }
}

