package com.epam.servlets.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class LocaleFilter implements Filter {
    private String locale;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        locale = "be_RU";
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        if (locale != null && session.getAttribute("locale") == null) {
            session.setAttribute("locale", locale);
        }
        filterChain.doFilter(request, servletResponse);
    }

    @Override
    public void destroy() {
        locale = null;
    }
}
