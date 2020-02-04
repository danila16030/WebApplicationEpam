package com.epam.servlets.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class UtilityFilter implements Filter {
    private FilterConfig filterConfig;
    private String userName;
    private String previousPage;

    public void init(final FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    public void destroy() {
        filterConfig = null;
    }

    public void doFilter(final ServletRequest request, final ServletResponse response, FilterChain chain)
            throws java.io.IOException, javax.servlet.ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String[] list = req.getRequestURI().split("/");
        String page;
        page = list[list.length - 1];
        if (list.length > 2) {
            previousPage = list[list.length - 1];
        }
        if (req.getServletPath().equals("/index.jsp") && previousPage != null && previousPage.equals("client")) {
            userName = null;
        }

        if ((page.equals("singIn") || page.equals("register"))) {
            if (req.getParameter("name") != null) {
                userName = req.getParameter("name");

            }
        }
        /*String a = (String) req.getSession().getAttribute("inf");
        if (a != null && !a.equals("")) {
            if (pos!=null&&!pos.equals(req.getServletPath())) {
                req.getSession().setAttribute("inf", "");
            }
            pos = req.getServletPath();
        }*/
        if (userName != null) {
            request.setAttribute("user", userName);
        }
        if (page.equals("WebApplication_war_exploded") && userName != null) {
            resp.sendRedirect("client");
        }
        if (page.equals("client") && userName == null) {
            resp.sendRedirect("/WebApplication_war_exploded");
            return;
        }
        chain.doFilter(request, response);
    }

}
