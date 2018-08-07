package com.controller;


import com.service.MessageService;
import com.weixin.AesException;
import com.weixin.WXBizMsgCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;


@RestController
@RequestMapping("/home")
public class HomeController {
    private static final long serialVersionUID = 1L;

    @Value("${wx.Token}")
    String sToken;// 这个Token是随机生成，但是必须跟企业号上的相同
    @Value("${wx.CorpID}")
    String sCorpID;// 这里是你企业号的CorpID
    @Value("${wx.EncodingAESKey}")
    String sEncodingAESKey;// 这个EncodingAESKey是随机生成，但是必须跟企业号上的相同

   private final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    MessageService ms;

    @GetMapping("/message")
    public String vailidate(@RequestParam(name = "msg_signature") String sVerifyMsgSig,
                            @RequestParam(name = "timestamp") String sVerifyTimeStamp,
                            @RequestParam(name = "nonce") String sVerifyNonce,
                            @RequestParam(name = "echostr") String sVerifyEchoStr) {
        String sEchoStr = ""; // 需要返回的明文
        logger.info("进来了");
        try {
            WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(sToken, sEncodingAESKey, sCorpID);
            sEchoStr = wxcpt.VerifyURL(sVerifyMsgSig, sVerifyTimeStamp, sVerifyNonce, sVerifyEchoStr);

        } catch (Exception e) {
            // 验证URL失败，错误原因请查看异常
            e.printStackTrace();
        }
        return sEchoStr;
    }

    @PostMapping("/message")
    public String Receiv(HttpServletRequest request) {

        String sReqMsgSig = request.getParameter("msg_signature");
        // 时间戳
        String sReqTimeStamp = request.getParameter("timestamp");
        // 随机数
        String sReqNonce = request.getParameter("nonce");
        // 获得post提交的数据
        String sEncryptMsg = "";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
            StringBuilder sbuff = new StringBuilder();
            String tmp = null;
            while ((tmp = br.readLine()) != null) {
                sbuff.append(tmp);
            }
            String sReqData = sbuff.toString();
            WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(sToken, sEncodingAESKey, sCorpID);
            String sMsg = wxcpt.DecryptMsg(sReqMsgSig, sReqTimeStamp, sReqNonce, sReqData);
            logger.info("after decrypt msg: " + sMsg);
            sEncryptMsg = ms.getCallBackString(sMsg, wxcpt, sReqTimeStamp, sReqNonce);

        } catch (Exception e) {
            // TODO
            // 解密失败，失败原因请查看异常
            e.printStackTrace();
        }
        return sEncryptMsg;
    }
}
