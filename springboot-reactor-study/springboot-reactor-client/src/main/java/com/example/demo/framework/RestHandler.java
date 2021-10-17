package com.example.demo.framework;

import com.example.demo.framework.bean.MethodInfo;
import com.example.demo.framework.bean.ServerInfo;

/**
 * rest请求调用handler
 */
public interface RestHandler {

    /**
     * 初始化服务器信息
     * @param serverInfo
     */
    void init(ServerInfo serverInfo);

    /**
     * 调用rest请求，返回接口
     * @param methodInfo
     */
    Object invokeRest(MethodInfo methodInfo);
}
