package com.service;

import com.dao.ConfigDao;
import com.dao.MessageDao;
import com.dao.RdmsDao;
import com.tools.TuLingRobot;
import com.weixin.WXBizMsgCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

@Service
public class MessageService {
    // 接收消息内容以及根据消息类型 选择对应的回复类型
    @Autowired
    MessageDao md;

    @Autowired
    ConfigDao configDao;

    @Autowired
    RdmsDao rdmsDao;


    public String getCallBackString(String sMsg, WXBizMsgCrypt wxcpt, String sReqTimeStamp, String sReqNonce) {
        String sEncryptMsg = "";
        // TODO: 解析出明文xml标签的内容进行处理
        // For example:
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            StringReader sr = new StringReader(sMsg);
            InputSource is = new InputSource(sr);
            Document document = db.parse(is);
            Element root = document.getDocumentElement();
            NodeList nodelist2 = root.getElementsByTagName("MsgType");
            String Msgtype = nodelist2.item(0).getTextContent();
            switch (Msgtype) {
                case "text":
                    sEncryptMsg = MessageForText(document, sReqTimeStamp, sReqNonce, wxcpt);
                    break;
                case "image":
                    sEncryptMsg = MessageForText(document, sReqTimeStamp, sReqNonce, wxcpt);
                    break;
                case "voice":
                    sEncryptMsg = MessageForText(document, sReqTimeStamp, sReqNonce, wxcpt);
                    break;
                case "video":
                    sEncryptMsg = MessageForText(document, sReqTimeStamp, sReqNonce, wxcpt);
                    break;
                case "location":
                    sEncryptMsg = MessageForText(document, sReqTimeStamp, sReqNonce, wxcpt);
                    break;
                case "link":
                    sEncryptMsg = MessageForText(document, sReqTimeStamp, sReqNonce, wxcpt);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 加密失败
        }
        return sEncryptMsg;
    }

    // 文本类型的消息回复
    public String MessageForText(Document document, String sReqTimeStamp, String sReqNonce, WXBizMsgCrypt wxcpt) {
        String sEncryptMsg = "";
        try {
            Element root = document.getDocumentElement();

            NodeList nodelist1 = root.getElementsByTagName("Content");
            String Content = nodelist1.item(0).getTextContent();

            NodeList nodelist2 = root.getElementsByTagName("ToUserName");
            String ToUserName = nodelist2.item(0).getTextContent();

            NodeList nodelist3 = root.getElementsByTagName("FromUserName");
            String FromUserName = nodelist3.item(0).getTextContent();
            if (!FromUserName.contains("liujiang")) {
                md.pushMessage("liujiang", FromUserName + ":" + Content);
            }

            System.out.println("Content：" + Content);

            System.out.println("ToUserName：" + ToUserName);
            System.out.println("FromUserName：" + FromUserName);
            String resContent = "此功能正在开发中。。";
            if (Content.contains("剪优")) {
                resContent = "剪优是世界上最帅的男人！你不用怀疑！";
            }else if (Content.contains("发射")) {
                resContent =  rdmsDao.sendLog(FromUserName);
            }  else if (Content.contains("编号")) {
                configDao.updateProjectIdByUser(FromUserName, Content);
                resContent = "更新成功！";
            } else if (Content.contains("工时")) {
                configDao.updateWorkLoadByUser(FromUserName, Content);
                resContent = "更新成功！";
            } else if (Content.contains("审批人")) {
                configDao.updateSprByUser(FromUserName, Content);
                resContent = "更新成功！";
            } else if (Content.contains("内容")) {
                configDao.updateContentByUser(FromUserName, Content);
                resContent = "更新成功！";
            } else if (Content.contains("关闭日志")) {
                configDao.updateEnableByUser(FromUserName, "0");
                resContent = "更新成功！";
            } else if (Content.contains("启用日志")) {
                configDao.updateEnableByUser(FromUserName, "1");
                resContent = "更新成功！";
            } else if (Content.contains("日志配置")) {
                resContent = configDao.getLogCfg(FromUserName);
            } else if (Content.contains("搜项目")) {
                resContent = rdmsDao.getProjectList(FromUserName,Content);
            }else if (Content.contains("搜审批")) {
                resContent = rdmsDao.getSprList(FromUserName,Content);
            } else if (Content.contains("日志")) {
                resContent = "日志指令\r\n";
                resContent+="'编号xxx':修改默认项目编号。\n";
                resContent+="'工时xx':修改默认工时。\n";
                resContent+="'内容xxxx':修改默认内容。\n";
                resContent+="'审批人xxxx':审批人+id。\n";
                resContent+="'关闭日志':关闭定时发送。\n";
                resContent+="'启用日志':启用定时发送。\n";
                resContent+="'日志配置':查看当前日志配置。\n";
                resContent+="'搜项目xxx':查看自己相关的项目id。\n";
                resContent+="'搜审批xxx':查看审批人id。\n";
                resContent+="'发射':立即发送日志。";
            } else {
                // 图灵机器人的回复
                resContent = TuLingRobot.getContent(Content, FromUserName);
            }
            System.out.println(resContent);
            String sRespData = "<xml><ToUserName><![CDATA[" + FromUserName + "]]></ToUserName><FromUserName><![CDATA["
                    + ToUserName + "]]></FromUserName><CreateTime>" + sReqTimeStamp
                    + "</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[" + resContent
                    + "]]></Content></xml>";

            sEncryptMsg = wxcpt.EncryptMsg(sRespData, sReqTimeStamp, sReqNonce);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("after encrypt sEncrytMsg: " + sEncryptMsg);
        return sEncryptMsg;
    }

    public String getAvContent() {
//        DBHelper db = DBHelper.getInstance();
        String content = "";
//        String getcountsql = "SELECT COUNT(id) as length, MAX(id) as max FROM av";
//        Map<String, Object> mpcount;
//        try {
//            mpcount = db.returnSimpleResult(getcountsql);
//            int len = Integer.parseInt(mpcount.get("length").toString());
//            int max = Integer.parseInt(mpcount.get("max").toString());
//            int start = max - len;
//            int ran = new Random().nextInt(len) + start;
//            //根据ID获取视频链接
//            String sql = "select id,title from av where id = '" + ran + "'";
//            Map<String, Object> mpurl = db.returnSimpleResult(sql);
//            String avid = mpurl.get("id").toString();
//            String avtitle = mpurl.get("title").toString();
//            content = "卧槽，就喜欢你这么不要脸的人，拿去撸吧，骚年！\n<a href=\"http://wx.ijianyou.cn/WeiXinMessageServ/play?id=" + avid + "\">" + avtitle + "</a>";
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } finally {
//            db.closeconnection();
//        }
        return content;
    }

    public String serchAvContent(String word) {
//        DBHelper db = DBHelper.getInstance();
//        String getcountsql = "SELECT *  FROM av where title like '%" + word + "%' ";
//        System.out.println(getcountsql);
//        List<Map<String, Object>> mpcount;
        StringBuilder sb = new StringBuilder();
        sb.append("搜索结果如下：\n");
//        try {
//            mpcount = db.returnMultipleResult(getcountsql);
//            for (int i = 0; i < mpcount.size(); i++) {
//                Map<String, Object> mp = mpcount.get(i);
//                String avid = mp.get("id").toString();
//                String avtitle = mp.get("title").toString();
//                sb.append(i + 1 + "：<a href=\"http://wx.ijianyou.cn/WeiXinMessageServ/play?id=" + avid + "\">" + avtitle + "</a>\n");
//            }
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } finally {
//            db.closeconnection();
//        }
        System.out.println(sb.toString());
        return sb.toString();
    }
}
