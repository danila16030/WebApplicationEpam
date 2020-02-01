package com.epam.servlets.controller;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.StringWriter;

public class TagController extends SimpleTagSupport {
    private String value;

    public void setValue(String val) {
        this.value = val;
    }

    StringWriter sw = new StringWriter();

    public void doTag() throws JspException, IOException {
        if (value != null) {
            /* Use message from attribute */
            JspWriter out = getJspContext().getOut();
            out.println(value);
        } else {
            /* use message from the body */
            getJspBody().invoke(sw);
            getJspContext().getOut().println(sw.toString());
        }
    }
}
