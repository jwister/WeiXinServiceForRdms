package com.tools;

import java.util.HashMap;
import java.util.Map;



import net.sf.json.JSONObject;

public class TuLingRobot {

    static String url = "http://www.tuling123.com/openapi/api";
    static String key = "88076fc0d2fc4d91a895d706fcf5c94f";

    public static String getContent(String content, String UserId) {
        String text = "";
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("key", key);
        parameters.put("info", content);
        parameters.put("userid", UserId);
        //url = url + "?key=" + key + "&info=" + content + "&userid=" + UserId;
        String Res = HttpToolRequest.sendPost(url, parameters);
        JSONObject jsonObject = JSONObject.fromObject(Res);
        String code = jsonObject.get("code").toString();
        text = jsonObject.get("text").toString();

        System.out.println(code);
        System.out.println(text);
        return text;
    }
}
