package com.guapi.webwork5.listener;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@Component
public class SessionListener implements HttpSessionListener {

    @Override
    public synchronized void sessionCreated(HttpSessionEvent httpSessionEvent) {
        // 在这里填写当有session被创建时的动作与功能
        // 从 httpSessionEvent 中获取 新创建的session对象：
        // httpSessionEvent.getSession()
        //System.out.println("有新的session创建");
        HttpSession session = httpSessionEvent.getSession();
        session.setMaxInactiveInterval(25);   // 单位秒
    }

    // session 的销毁：并非是浏览器关闭后销毁，而是浏览器一段时间没有操作以后自动销毁（较长，可以理解成浏览器关闭以后）
    // 这个销毁间隔可以设置
    @Override
    public synchronized void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        //System.out.println("有session销毁");
    }
}
