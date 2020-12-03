package com.guapi.webwork5.controller;

import com.guapi.webwork5.Data.ContactInfor;
import com.guapi.webwork5.Data.Table;
import com.guapi.webwork5.Dao.UserJpaRepository;
import com.guapi.webwork5.Service.TypeTransformer;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class WebController {

    @Autowired
    private UserJpaRepository userJpaRepository;

    // 访问主界面 如果还没有登录 将被返回到登录界面
    @SneakyThrows
    @GetMapping("/main")
    public String showMain(HttpServletRequest request, Model model) {
        Object flag = request.getSession().getAttribute("login");
        if (null != flag) {
            List<ContactInfor> info = userJpaRepository.findAll();           // 获取持久层的数据
            Table t = new Table(TypeTransformer.listToVector(info));  // 类型转换
            model.addAttribute("table", t);
            return "main";
        }
        else
            return "redirect:/login";
    }

    // 导航页
    @GetMapping("/*")
    public String showNav(Model model, HttpServletRequest request) {
        if (null == request.getSession().getAttribute("login"))
            model.addAttribute("message", "请先登录");
        else
            model.addAttribute("message", "欢迎回来");
        return "Navigation";
    }
}
