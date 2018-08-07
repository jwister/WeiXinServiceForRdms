package com.dao;

import com.mapper.MessageMapper;
import com.tools.HttpToolRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ConfigDao {
    @Autowired
    MessageMapper messageMapper;


    public void updateProjectIdByUser(String username, String content) {
        content = content.replace("编号", "");
        Map mp = new HashMap();
        mp.put("projectid", content);
        mp.put("username", username);
        messageMapper.updateProjectIdByUser(mp);
    }

    public void updateWorkLoadByUser(String username, String content) {
        content = content.replace("工时", "");
        Map mp = new HashMap();
        mp.put("workload", content);
        mp.put("username", username);
        messageMapper.updateWorkLoadByUser(mp);
    }

    public void updateContentByUser(String username, String content) {
        content = content.replace("内容", "");
        Map mp = new HashMap();
        mp.put("content", content);
        mp.put("username", username);
        messageMapper.updateContentByUser(mp);
    }

    public void updateEnableByUser(String username, String content) {
        Map mp = new HashMap();
        mp.put("enable", content);
        mp.put("username", username);
        messageMapper.updateEnableByUser(mp);
    }

    public void updateSprByUser(String username, String content) {
        content = content.replace("审批人", "");
        Map mp = new HashMap();
        mp.put("spr", content);
        mp.put("username", username);
        messageMapper.updateSprByUser(mp);
    }

    public String getLogCfg(String username) {
        Map mp = new HashMap();
        mp.put("username", username);
        Map res = messageMapper.getLogCfg(mp);
        String str = "";
        str += "项目编号：" + res.get("projectid").toString() + "\r\n";
        str += "工时：" + res.get("workload").toString() + "\r\n";
        str += "内容：" + res.get("content").toString() + "\r\n";
        str += "是否启用：" + res.get("enable").toString()+ "\r\n";
        str += "审批人：" + res.get("spr").toString();
        return str;
    }


}
