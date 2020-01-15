package com.epam.servlets.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.util.logging.LogRecord;

public class UtilityFilter implements Filter {
    private FilterConfig filterConfig;
    String userName;

    public void init(final FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    public void destroy() {
        filterConfig = null;
    }

    public void doFilter(final ServletRequest request, final ServletResponse response, FilterChain chain)
            throws java.io.IOException, javax.servlet.ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        // Раскладываем адрес на составляющие
        String[] list = req.getRequestURI().split("/");
        String page = null;
        page = list[list.length - 1];
        if (((page != null) && page.equalsIgnoreCase("singIn")||page.equalsIgnoreCase("register"))) {
            if (req.getParameter("name") != null) {
                userName = req.getParameter("name");
            }
        }
            if (userName != null) {
            request.setAttribute("user", userName);
        }
        chain.doFilter(request, response);
    }


}
