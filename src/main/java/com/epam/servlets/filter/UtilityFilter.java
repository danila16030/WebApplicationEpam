package com.epam.servlets.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.LogRecord;

public class UtilityFilter implements Filter {
    private FilterConfig filterConfig;
    String userName;
    String previousPage;

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
        if (((page != null) && page.equals("singIn") || page.equals("register"))) {
            if (req.getParameter("name") != null) {
                userName = req.getParameter("name");
            }
        }
        if (userName != null) {
            request.setAttribute("user", userName);
        }
        String value = req.getParameter("move");
/*        if ((page != null) && page.equals("WebApplication_war_exploded") && userName != null
                && value == null && !previousPage.equals("client")) {
            resp.sendRedirect("client");
        }
*/
        chain.doFilter(request, response);
    }


}
