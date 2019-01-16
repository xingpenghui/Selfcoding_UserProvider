package com.feri.selfcoding_userprovider.druid;

import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 *@Author feri
 *@Date Created in 2019/1/15 13:44
 */
@WebServlet(urlPatterns = "/druid/*", initParams = {
        @WebInitParam(name = "loginUsername", value = "admin"), // 用户名
        @WebInitParam(name = "loginPassword", value = "qfjava"), // 密码
        @WebInitParam(name = "resetEnable", value = "true")})
public class DruidServlet extends StatViewServlet {
}
