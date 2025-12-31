package com.controller;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.annotation.IgnoreAuth;

/**
 * 首页控制器
 */
@Controller
public class IndexController {
    
    /**
     * 跳转到前台首页
     */
    @IgnoreAuth
    @RequestMapping("/")
    public void index(HttpServletResponse response) throws IOException {
        response.sendRedirect("/springbootct3p7/front/front/dist/index.html");
    }
    
    /**
     * 跳转到前台首页
     */
    @IgnoreAuth
    @RequestMapping("/index")
    public void indexPage(HttpServletResponse response) throws IOException {
        response.sendRedirect("/springbootct3p7/front/front/dist/index.html");
    }
    
    /**
     * 跳转到后台首页
     */
    @IgnoreAuth
    @RequestMapping("/admin")
    public void admin(HttpServletResponse response) throws IOException {
        response.sendRedirect("/springbootct3p7/admin/admin/dist/index.html");
    }
}
