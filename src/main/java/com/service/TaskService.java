package com.service;

import com.dao.RdmsDao;
import com.mapper.MessageMapper;
import org.apache.http.client.CookieStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TaskService {
    @Autowired
    MessageMapper messageMapper;

    @Autowired
    RdmsDao rdmsDao;

    @Scheduled(cron = "0 55 23 ? * MON-FRI" )        //fixedDelay = 5000表示当前方法执行完毕5000ms后，Spring scheduling会再次调用该方法
    public void sendLogTimer() {
        List<Map> maps = messageMapper.getUserList();
        for (int i = 0; i < maps.size(); i++) {
            String userName = maps.get(i).get("username").toString();
            if (maps.get(i).get("enable").toString().equals("1")  && maps.get(i).get("issend").toString().equals("0")) {
                CookieStore cs = rdmsDao.Login(userName,userName);
                Date d = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                rdmsDao.WriteLog(cs, sdf.format(d), maps.get(i).get("projectid").toString(), maps.get(i).get("workload").toString(), maps.get(i).get("content").toString());
                System.out.println(userName + "———》》》日志已发送");
            }
//            Map upissend = new HashMap();
//            upissend.put("issend", "0");
//            upissend.put("username", userName);
//            messageMapper.updateIsSendByUser(upissend);
        }
    }

    @Scheduled(cron = "0 59 23 ? * *" )        //fixedDelay = 5000表示当前方法执行完毕5000ms后，Spring scheduling会再次调用该方法
    public void upDateSendStates() {
        List<Map> maps = messageMapper.getUserList();
        for (int i = 0; i < maps.size(); i++) {
            String userName = maps.get(i).get("username").toString();
            Map upissend = new HashMap();
            upissend.put("issend", "0");
            upissend.put("username", userName);
            messageMapper.updateIsSendByUser(upissend);
        }
    }
}
