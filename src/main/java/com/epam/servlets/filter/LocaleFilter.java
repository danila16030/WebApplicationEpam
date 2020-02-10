package com.epam.servlets.filter;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class LocaleFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        Cookie cookie = null;
        if (req.getCookies() != null) {
            Cookie[] cookies = req.getCookies();
            for (Cookie c : cookies) {
                if (c.getName().equals("locale")) {
                    cookie = c;
                }
            }
        }
        if (cookie == null) {
            cookie = new Cookie("locale", "be_US");
            cookie.setMaxAge(60 * 60 * 48);
            resp.addCookie(cookie);
        }

        if (req.getParameter("language") != null) {
            if (req.getParameter("language").equals("en")) {
                cookie.setValue("be_US");
                resp.addCookie(cookie);
            } else {
                cookie.setValue("be_RU");
                resp.addCookie(cookie);
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
