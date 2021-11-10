package solo.z_scrap.lists;

import solo.util.DBUtils;
import solo.util.Html;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
by Joachim

prints all the tools
*/
@WebServlet(name = "lt", value = "/lt")
public class ListTools extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            Connection dbConnection = DBUtils.getNoErrorConnection(out);
            String toolQ = "select * from Tool order by toolID ";
            PreparedStatement statement = dbConnection.prepareStatement(toolQ);
            ResultSet rs = statement.executeQuery();
            //HTML SPAM!
            out.println(
                    "<!DOCTYPE html>" +
                            "<head>" +
                            "  <title>Sorting Tables w/ JavaScript</title>" +
                            "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />" +
                            "  <meta charset=\"utf-8\" />" +
                            "  <link rel=\"stylesheet\" href=\"CSS/tabelsort.css\">" +
                            "</head>" +
                            "<body>" +
                            "    <h3>Tool Stuff</h3>" +
                            "    <table class=\"table-sortable\">" +
                            "    <thead>" +
                            "        <tr>" +
                            "            <th>toolName</th>" +
                            "            <th>category</th>" +
                            "            <th>priceFirst</th>" +
                            "            <th>priceAfter</th>" +
                            "            <th>maintenance</th>" +
                            "            <th>certificateID</th>" +
                            "        </tr>" +
                            "    </thead>" +
                            "<tbody>");

            //create a tool model as long as there are RS's left
            while (rs.next()) {
                //prints them to the table
                out.println("<tr>");
                out.println("<td>" + rs.getString("toolName") + "</th>");
                out.println("<td>" + rs.getString("toolCategory") + "</th>");
                out.println("<td>" + rs.getInt("priceFirst") + "</th>");
                out.println("<td>" + rs.getInt("priceAfter") + "</th>");
                out.println("<td>" + rs.getBoolean("maintenance") + "</th>");
                out.println("<td>" + rs.getInt("certificateID") + "</th>");
                out.println("</tr>");
            }
            //adds script for sorting
            out.println("</tbody></table>" +
                    "<script src=\"JS/tabelsort.js\"></script>" +
                    "</body>" +
                    "</html>");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}