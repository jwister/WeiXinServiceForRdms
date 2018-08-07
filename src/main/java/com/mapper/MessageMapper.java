package com.mapper;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface MessageMapper {


    List<Map> getCrenttoken();

    //更新token
    void updateToken(Map hashMap);

    void updateProjectIdByUser(Map hashMap);

    void updateWorkLoadByUser(Map hashMap);

    void updateContentByUser(Map hashMap);

    void updateEnableByUser(Map hashMap);

    void updateSprByUser(Map hashMap);

    //获取当前用户日志配置
    Map getLogCfg(Map hashMap);

    //获取定时器所有需要发送日志的用户
    List<Map> getUserList();

    //根据用户更新日志发送标识
    void updateIsSendByUser(Map hashMap);

    //根据发送标识
    void updateIsSend(Map hashMap);
}
