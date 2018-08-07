package com.dao;

import com.controller.HomeController;
import com.mapper.MessageMapper;
import com.tools.HttpToolRequest;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MessageDao {
    // 天气预报推送
    @Autowired
    MessageMapper messageMapper;

    private final Logger logger = LoggerFactory.getLogger(MessageDao.class);

    @Value("${wx.Token}")
    String sToken;// 这个Token是随机生成，但是必须跟企业号上的相同
    @Value("${wx.CorpID}")
    String sCorpID;// 这里是你企业号的CorpID
    @Value("${wx.EncodingAESKey}")
    String sEncodingAESKey;// 这个EncodingAESKey是随机生成，但是必须跟企业号上的相同
    @Value("${wx.Qyapi}")
    String api;// 这个获取token地址
    @Value("${wx.Secret}")
    String corpsecret;// 这个获取token地址

    public void pushMessage(String userid, String content) {
        // 第一步，获取所有用户的详细信息
        String token = getToken();
        // 发送消息接口
        String sendmessageapi = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + token;
        String query = "{\"touser\" : \"" + userid
                + "\",\"msgtype\" : \"text\",\"agentid\" : 1000002,\"text\" : { \"content\" : \"" + content.toString()
                + "\"}}";
        String sms = HttpToolRequest.sendPost(sendmessageapi, query);
        logger.info(sms);
    }

    // 获取token
    public String getToken() {
        // getCfg();
        String curentToken = "";
        Date curentTime = null;
        try {
            List<Map> hs = messageMapper.getCrenttoken();
            curentToken = hs.get(0).get("curenttoken").toString();
            String time = hs.get(0).get("curenttime").toString();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            curentTime = sdf.parse(time);
            logger.info("上次时间："+sdf.parse(time));
            Date now = new Date();
            long interval = (now.getTime() - curentTime.getTime()) / 1000;
            logger.info("两个时间相差" + interval + "秒");// 会打印出相差3秒
            if (interval > 7100) {

                String apiUrl = api + "?corpid=" + sCorpID + "&corpsecret=" + corpsecret;
                logger.info("微信接口请求地址：" + apiUrl);
                String json = HttpToolRequest.sendPost(apiUrl, "");
                logger.info(json);
                JSONObject jsonObject = JSONObject.fromObject(json);
                curentToken = jsonObject.get("access_token").toString();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Map parmhs = new HashMap();
                parmhs.put("curenttoken", curentToken);
                parmhs.put("curenttime", formatter.format(new Date()));
                messageMapper.updateToken(parmhs);
                logger.info("最新token：" + curentToken);
            }
            logger.info("当前token：" + curentToken);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return curentToken;
    }


}
