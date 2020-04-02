package com.ycorn.handler;

import com.ycorn.entity.MethodInfo;
import com.ycorn.entity.ServerInfo;

/**
 * @Author: wujianmin
 * @Date: 2020/4/2 16:00
 * @Function:
 * @Version 1.0
 */
public interface RestHandler {

    Object invoiceRest(MethodInfo methodInfo);

    void init(ServerInfo serverInfo);
}
