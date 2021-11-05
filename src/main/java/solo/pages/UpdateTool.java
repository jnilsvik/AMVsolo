package solo.pages;


import solo.models.ToolModel;
import solo.models.UserModel;
import solo.util.DBQueries;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "ut", value = "/ut")
public class UpdateTool extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        
        try {
            ToolModel model;
            model = DBQueries.getToolModelByID("1",out);  // TODO: 05.11.2021 stop using "1" for tool id 

            out.println(model.getToolName());
            out.println(model.getToolCategory());
            out.println(model.getDescription());
            out.println(model.getCertificateID());
            out.println(model.getMaintenance());
            out.println(model.getPicturePath()); // TODO: 05.11.2021 need to figure out how this works with file uploads
            out.println(model.getPriceFirst());
            out.println(model.getPriceAfter());

            out.println("<form action='register' method='POST'>" +
                    "            <input name = 'firstname' type = 'text'><br>" +
                    "            <label for = 'firstname'>First Name: </label><br>" +
                    "            <p> " +
                    "            <input name = 'firstname' type = 'text'><br>" +
                    "            <label for = 'firstname'>First Name: </label><br>" +
                    "            <input name = 'firstname' type = 'text'><br>" +
                    "            <label for = 'firstname'>First Name: </label><br>" +
                    "            <input name = 'firstname' type = 'text'><br>" +
                    "            <label for = 'firstname'>First Name: </label><br>" +
                    "            <input name = 'firstname' type = 'text'><br>" +
                    "            <label for = 'firstname'>First Name: </label><br>" +
                    "            <input name = 'firstname' type = 'text'><br>" +
                    "            <label for = 'firstname'>First Name: </label><br>" +
                    "            <input name = 'firstname' type = 'text'><br>" +
                    "            <label for = 'firstname'>First Name: </label><br>" +
                    "            <input name = 'firstname' type = 'text'><br>" +
                    "            <label for = 'firstname'>First Name: </label><br>" +
                    "        </form>");

/*
            model.getToolName()
            model.getToolCategory()
            model.getDescription()
            model.getCertificateID()
            model.getMaintenance()
            model.getPicturePath() // TODO: 05.11.2021 need to figure out how this works with file uploads
            model.getPriceFirst()
            model.getPriceAfter()
            
            
            model.setToolName();
            model.setToolCategory();
            model.setDescription();
            model.setCertificateID();
            model.setMaintenance();
            model.setPicturePath(); // TODO: 05.11.2021 need to figure out how this works with file uploads 
            model.setPriceFirst();
            model.setPriceAfter();
 */
        } catch (SQLException e) {
            e.printStackTrace();
        }


        

    }
}