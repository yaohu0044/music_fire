package com.musicfire.common.config;


import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.*;
import java.util.HashSet;

@WebListener
@Slf4j
public class SessionListener implements HttpSessionListener, HttpSessionAttributeListener {
     

        @Override
        public void attributeAdded(HttpSessionBindingEvent httpSessionBindingEvent) {
            log.info("--attributeAdded--");
            HttpSession session = httpSessionBindingEvent.getSession();
            log.info("key----:" + httpSessionBindingEvent.getName());
            log.info("value---:" + httpSessionBindingEvent.getValue());

        }

        @Override
        public void attributeRemoved(HttpSessionBindingEvent httpSessionBindingEvent) {
            log.info("--attributeRemoved--");
        }

        @Override
        public void attributeReplaced(HttpSessionBindingEvent httpSessionBindingEvent) {
            log.info("--attributeReplaced--");
        }

        @Override
        public void sessionCreated(HttpSessionEvent event) {
            log.info("---sessionCreated----");
            HttpSession session = event.getSession();
            ServletContext application = session.getServletContext();
            // 在application范围由一个HashSet集保存所有的session
            HashSet sessions = (HashSet) application.getAttribute("sessions");
            if (sessions == null) {
                sessions = new HashSet();
                application.setAttribute("sessions", sessions);
            }
            // 新创建的session均添加到HashSet集中
            sessions.add(session);
            // 可以在别处从application范围中取出sessions集合
            // 然后使用sessions.size()获取当前活动的session数，即为“在线人数”
        }

        @Override
        public void sessionDestroyed(HttpSessionEvent event) throws ClassCastException {
            log.info("---sessionDestroyed----");
            HttpSession session = event.getSession();
            log.info("deletedSessionId: " + session.getId());
            log.info("getCreationTime: " + session.getCreationTime());
            log.info("getLastAccessedTime: " +session.getLastAccessedTime());
            ServletContext application = session.getServletContext();
            HashSet sessions = (HashSet) application.getAttribute("sessions");
            // 销毁的session均从HashSet集中移除
            sessions.remove(session);
        }


    }
