package com.ycorn.proxy;

/**
 * @Author: wujianmin
 * @Date: 2020/4/2 15:51
 * @Function:
 * @Version 1.0
 */
public interface ProxyCreator {

    Object getProxy(Class<?> clz);

}
