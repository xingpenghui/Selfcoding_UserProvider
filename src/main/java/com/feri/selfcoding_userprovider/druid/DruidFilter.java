package com.feri.selfcoding_userprovider.druid;

import com.alibaba.druid.support.http.WebStatFilter;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
/**
 *@Author feri
 *@Date Created in 2019/1/15 13:43
 */
@WebFilter(filterName = "druidWebStatFilter", urlPatterns = "/*",
        initParams = {
                @WebInitParam(name = "exclusions", value = "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*")// 忽略资源
})
public class DruidFilter extends WebStatFilter {
}
