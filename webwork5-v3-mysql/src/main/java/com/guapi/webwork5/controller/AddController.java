package com.guapi.webwork5.controller;

import com.guapi.webwork5.Data.ContactInfor;
import com.guapi.webwork5.Data.Table;
import com.guapi.webwork5.Dao.UserJpaRepository;
import com.guapi.webwork5.Service.TableAlters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class AddController {

    // JPA
    @Autowired
    private UserJpaRepository userJpaRepository;

    // 访问添加页面 需要传递一个和前端表单交互数据的提交类
    // 必须要先登录
    @RequestMapping("/add")
    public String showAdd(ContactInfor cont, Model model, HttpServletRequest request) {
        if (null == request.getSession().getAttribute("login"))
            return "redirect:/login";
        else {
            model.addAttribute("cont", cont);
            return "add";
        }
    }

    // 处理添加的URL 如果不是通过post请求的说明是手动请求的，将跳转回去，否则进行处理
    @GetMapping("/checkadd")
    public String redirectAdd() {
        return "redirect:/add";  // 返回模型redirect:/....  springboot 会自动创建或获取相关Mapping 方法所需的参数
    }

    // 处理添加的URL 如果是通过POST提交的，则进行处理
    @PostMapping("/checkadd")
    public String checkAdd(ContactInfor cont, HttpServletRequest request, Model model) {
        // 现在改用数据库，直接添加就行，因为如果有重复的将不会添加 但是因为一些后续逻辑，所以还是需要先查找
        List<ContactInfor> list = userJpaRepository.findByContactname(cont.getContactname());
        if (0 == list.size()) {        //   没有重复的
            userJpaRepository.save(cont);
            return "redirect:/main";
        } else {                         //   重复
            cont.setMessage("联系人名称已存在");
            cont.setContactname("");
            return showAdd(cont, model, request);
        }
    }

    // ajax 检测电话号码 是否重复  有重复的返回1 没有重复的返回0
    @ResponseBody
    @PostMapping("/checktel")
    public int checkTel(@RequestParam(name="tel")String tel, HttpServletRequest request) {
        Table table = (Table)request.getSession().getAttribute("table");
        boolean result = TableAlters.hasTel(userJpaRepository.findAll(), tel);
        if (result)
            return 1;
        else
            return 0;
    }
}
