package com.dao;

import com.mapper.MessageMapper;
import com.tools.HttpClientTool;
import com.tools.HttpToolRequest;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class RdmsDao {

    @Autowired
    MessageMapper messageMapper;

    //获取登陆cookie
    public CookieStore Login(String userName, String passWord) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("languages", "chinese"));
        params.add(new BasicNameValuePair("account", userName));
        params.add(new BasicNameValuePair("password", passWord));
        String url = "http://rdmsmob.dscomm.com.cn:7779/actions/mobileAction!login.action?languages=chinese";
        CookieStore ss = null;
        try {
            ss = HttpClientTool.sendPost(url, params);
        } catch (Exception e) {
        }
        return ss;
    }

    //日志接口
    public String WriteLog(CookieStore h, String workjourDate, String projectId, String workload, String content) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("workjourDate", workjourDate));
        params.add(new BasicNameValuePair("projectId", projectId));
        params.add(new BasicNameValuePair("workload", workload));
        params.add(new BasicNameValuePair("content", content));

        String url = "http://rdmsmob.dscomm.com.cn:7779/actions/mobileAction!addWorkjour.action?languages=chinese";
        Object ss = null;
        try {
            ss = HttpClientTool.sendPost(url, params, h);
        } catch (Exception e) {
        }
        return ss.toString();
    }

    //获取项目列表
    public String getProjectList(String username, String keyWord) {
        keyWord = keyWord.replace("搜项目", "");

        CookieStore h = Login(username, username);
        System.out.println(h.toString());
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        String url = "http://rdmsmob.dscomm.com.cn:7779/actions/mobileAction!searchProject.action?languages=chinese&ajax=true&word=" + keyWord;
        String ss = null;
        try {
            ss = HttpClientTool.sendPost(url, params, h);
        } catch (Exception e) {

        }
        return ss.toString();
    }

    //主动发送日志
    public String sendLog(String username) {
        String ss = "";
        CookieStore cs = Login(username, username);
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map parm = new HashMap();
        parm.put("username", username);
        Map res = messageMapper.getLogCfg(parm);
        System.out.println(res.toString() + "----》获取用户信息！");
        if (res.get("issend").toString().equals("1")) {
            ss = "今日日志已经发送！请勿重复发送！";
        } else {
            WriteLog(cs, sdf.format(d), res.get("projectid").toString(), res.get("workload").toString(), res.get("content").toString());
            System.out.println(username + "----》手动发射日志完成！");
            ss = "日志发射完成！";
        }

        //发送完成更新发送标识为1
        Map sendparm = new HashMap();
        sendparm.put("username", username);
        sendparm.put("issend", "1");
        messageMapper.updateIsSendByUser(sendparm);
        return  ss;
    }

}
