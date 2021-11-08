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

prints all the users
*/
@WebServlet(name = "lu", value = "/lu")
public class ListUsers extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            Html.printSidebar(out);
            out.println("<!DOCTYPE html>" +
                    "<head>" +
                    "  <title>Sorting Tables w/ JavaScript</title>" +
                    "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />" +
                    "  <meta charset=\"utf-8\" />" +
                    "  <link rel=\"stylesheet\" href=\"CSS/tabelsort.css\">" +
                    "</head>" +
                    "<body>" +
                    "    <h3>User Stuff</h3>" +
                    "    <table class=\"table-sortable\">" +
                    "    <thead>" +
                    "        <tr>" +
                    "            <th>User ID</th>" +
                    "            <th>Email</th>" +
                    "            <th>Firstname</th>" +
                    "            <th>Lastname</th>" +
                    "            <th>Phone nmbr</th>" +
                    "            <th>Union</th>" +
                    "            <th>Admin</th>" +
                    "        </tr>" +
                    "</thead>" +
                    "<tbody>");

            Connection dbConnection = DBUtils.getNoErrorConnection(out);
            String userQ = "select * from AMVUser order by userID ";
            PreparedStatement statement = dbConnection.prepareStatement(userQ);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                //prints them to the table
                out.println("<tr>");
                out.println("<td>" + rs.getInt("userID") + "</th>");
                out.println("<td>" + rs.getString("email") + "</th>");
                out.println("<td>" + rs.getString("firstname") + "</th>");
                out.println("<td>" + rs.getString("lastname") + "</th>");
                out.println("<td>" + rs.getString("phoneNumber") + "</th>");
                out.println("<td>" + rs.getBoolean("unionMember") + "</th>");
                out.println("<td>" + rs.getBoolean("userAdmin") + "</th>");
                out.println("</tr>");
            }
            out.println("</tbody></table>" +
                    "<script src=\"JS/tabelsort.js\"></script>" +
                    "</body>" +
                    "</html>");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}