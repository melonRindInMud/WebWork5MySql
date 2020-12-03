package com.guapi.webwork5.controller;

import com.guapi.webwork5.Data.LoginInfor;
import com.guapi.webwork5.Service.CheckLogin;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

// 登录及相关处理请求控制器
@Controller
public class LoginController {

    // 请求登录界面
    @RequestMapping("/login")
    public String login(LoginInfor user, Model model) {
        model.addAttribute("user", user); // 需要先向view模型发送infor数据， 如果已有则覆盖
        // 然后通过数据进行渲染，并且该数据可以进行提交
        return "login";
    }

    // 登录检测界面 (登录界面通过post方法发送)
    @SneakyThrows
    @PostMapping("/checklogin")
    public String checkLogin(LoginInfor user, Model model, HttpServletRequest request) {        // 该参数是从view模型中提取出来的
        if (true == CheckLogin.Check(user)) {                        // 通过校验
            user.setMessage("OK");
            request.getSession().setAttribute("login", "OK");   // 设置标识
            return "redirect:/main";                                // 重定向到main界面
        } else {                                                       // 没通过校验
            user.setMessage("用户名或密码错误");                       // 向模型中添加预装属性，然后返回该模型
            user.setPassword("");                                   // 缺点：地址并没有真正的重定向
            return login(user, model);                              // 优点：便于传递参数 （如果使用redirect:/login 则可以真正的重定向地址，但是传递参数比较麻烦）
        }
    }

    // 如果使用get方法直接请求 登录检测界面，进行重定向
    // 这种重定向的controller映射响应函数可以不返回view 而不返回， 但是不能返回其他非字符串类型
    // 如果登录了 就重定向到main界面，如果没登录就重定向去登录界面
    @SneakyThrows
    @GetMapping("/checklogin")
    public String redirectLogin(HttpServletRequest request) {
        if (null == request.getSession().getAttribute("login"))  // 没登录重定向到登录界面
            return "redirect:/login";
        else                                                       // 登录了重定向到主界面
            return "redirect:/main";
    }

    // 请求登出 返回 导航界面
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        if (null != session.getAttribute("login")) {
            session.removeAttribute("login");
            model.addAttribute("message", "您已成功登出");
        } else
            model.addAttribute("message", "您尚未登录");
        return "Navigation";
    }

}
