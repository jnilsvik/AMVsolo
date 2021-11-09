package solo.z_scrap.lists;


import solo.models.ToolModel;
import solo.util.DBUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*  by Joachim (though its all copypaste pretty much :/ )

    gets a tool id from landing page and prints all information about it
*/
@WebServlet(name = "xtd2", value = "/xtd2")
public class ToolDetail_ALT extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection dbConnecton = DriverManager.getConnection(
                    "jdbc:mariadb://172.17.0.1:3308/AMVDatabase", "root", "12345");
            String toolQ = "select * from Tool where ToolID = ?";
            PreparedStatement statement = dbConnecton.prepareStatement(toolQ);
            statement.setString(1, request.getParameter("toolID"));
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                out.println(rs.getInt("toolID"));
                out.println(rs.getString("toolName"));
                out.println(rs.getString("toolCategory"));
                out.println(rs.getBoolean("maintenance"));
                out.println(rs.getInt("priceFirst"));
                out.println(rs.getInt("priceAfter"));
                out.println(rs.getInt("certificateID"));
                out.println(rs.getString("description"));
                out.println(rs.getString("picturePath"));
            } else {
                out.println("Cant find that tool");
            }
        }
        catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
            out.println("helloX");
        }
    }
}