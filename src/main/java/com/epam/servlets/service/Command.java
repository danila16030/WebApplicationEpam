package com.epam.servlets.service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface Command {
    boolean execute(HttpServletRequest req);//лист содеожит комманду и пароль

}
