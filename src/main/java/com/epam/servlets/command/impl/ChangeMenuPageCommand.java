package com.epam.servlets.command.impl;

import com.epam.servlets.dao.DAOException;
import com.epam.servlets.dao.DAOFactory;
import com.epam.servlets.dao.MenuDAO;
import com.epam.servlets.entities.Product;
import com.epam.servlets.fileManager.FileManager;
import com.epam.servlets.command.Command;
import com.epam.servlets.command.CommandException;
import com.epam.servlets.fileManager.FileManagerException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


public class ChangeMenuPageCommand implements Command {

    private static final String UPLOAD_PATH = "c:/temp";
    private MenuDAO menuDAO = DAOFactory.getInstance().getSqlMenuDAO();
    private File file;
    private AtomicInteger tempNumber = new AtomicInteger();
    private FileManager fileManager = new FileManager();

    @Override
    public String execute(HttpServletRequest req) throws CommandException {

        if (req.getParameter("create") != null) {
            return createProduct(req);
        }
        if (req.getParameter("delete") != null) {
            return deleteProduct(req);
        }
        if (req.getParameter("time") != null) {
            return updateProduct(req);
        } else {
            return getProduct(req);
        }
    }


    private void buildFile(HttpServletRequest request) {
        try {
            if (!request.getParts().isEmpty()) {
                Collection<Part> part2 = request.getParts();
                for (Part part : part2) {
                    File downloadedFile;
                    try {
                        String fileName = part.getSubmittedFileName();
                        String name = part.getName();
                        if ("file".equals(name)) {
                            String newFileName = "temp" + tempNumber.addAndGet(1) +
                                    fileName.substring(fileName.lastIndexOf("."));
                            downloadedFile = new File(
                                    UPLOAD_PATH + File.separator + newFileName);
                            part.write(UPLOAD_PATH +
                                    File.separator + newFileName);
                            file = downloadedFile;
                            // logger.log(Level.DEBUG, "file is " + downloadedFile.getName());
                        }
                    } catch (IOException e) {
                        // logger.log(Level.ERROR, "file read error ");
                    }
                }
            }
        } catch (IOException e) {
            // logger.log(Level.DEBUG, "file io exception ", e);
        } catch (ServletException e) {
            // logger.log(Level.DEBUG, "read file servlet exception ", e);
        }
    }


    private String createProduct(HttpServletRequest req) throws CommandException {
        String tag = req.getParameter("tag");
        String productName = req.getParameter("product");
        String cost = req.getParameter("cost");
        String time = req.getParameter("time");
        buildFile(req);
        try {
            menuDAO.createNewProduct(tag, productName, cost, time);
            fileManager.moveImageToResourceFolder(file, productName);
        } catch (DAOException | FileManagerException e) {
            throw new CommandException("Error in DAO", e);
        }
        return getProduct(req);
    }

    private String deleteProduct(HttpServletRequest req) throws CommandException {
        String productName = req.getParameter("product");
        try {
            menuDAO.deleteProduct(productName);
        } catch (DAOException e) {
            throw new CommandException("Error in DAO", e);
        }
        return getProduct(req);
    }

    private String updateProduct(HttpServletRequest req) throws CommandException {
        String tag[] = req.getParameterValues("tag");
        String[] productName = req.getParameterValues("product");
        String[] previousName = req.getParameterValues("previous");
        String[] cost = req.getParameterValues("cost");
        String[] time = req.getParameterValues("time");
        try {
            menuDAO.updateProduct(tag, productName, previousName, cost, time);
        } catch (DAOException e) {
            throw new CommandException("Error in DAO", e);
        }

        return getProduct(req);
    }

    private String getProduct(HttpServletRequest req) throws CommandException {
        String productName = req.getParameter("product");
        ArrayList<Product> menuList = new ArrayList();
        List<String> tags = null;
        if (req.getParameter("tag") != null) {
            tags = Arrays.asList(req.getParameterValues("tag"));
            tags = tags.stream().distinct().collect(Collectors.toList());
        }
        try {
            if (tags != null) {
                menuList = menuDAO.getProductListForChange(tags);
            } else {
                if (menuDAO.findProductByName(productName)) {
                    menuList.add(menuDAO.getProductForChange(productName));
                } else {
                    req.getSession().setAttribute("inf", "not exist");
                }
            }
        } catch (DAOException e) {
            throw new CommandException("Error in DAO", e);
        }
        req.getSession().setAttribute("menuList", menuList);
        return "changeMenu";
    }
}
