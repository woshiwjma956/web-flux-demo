package com.ycorn.webflux.webflux;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @Author: wujianmin
 * @Date: 2020/3/31 11:22
 * @Function:
 * @Version 1.0
 */

@WebServlet(name = "syncServlet", urlPatterns = "/sync")
@Slf4j
public class syncServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 开启Servlet 异步化
        long start = System.currentTimeMillis();
        log.info("AsyncServlet start ...");
        doSomething();
        log.info("AsyncServlet end cost " + (System.currentTimeMillis() - start) + " ms");
    }

    private void doSomething() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
