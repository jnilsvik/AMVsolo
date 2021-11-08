package solo.z_scrap.lists;

import solo.models.ToolModel;
import solo.util.DBUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*  by Joachim */
@WebServlet(name = "td", value = "/td")
public class ToolDetail extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            getToolModel(out,request);
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("check doGet.catch");
        }
    }
    public void getToolModel(PrintWriter out, HttpServletRequest toolID) throws SQLException {
        Connection dbConnection = DBUtils.getNoErrorConnection(out);
        PreparedStatement statement = dbConnection.prepareStatement(
                "select * from Tool where toolID = ?");
        statement.setString(1, toolID.getParameter("toolID"));
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
        } else{
            out.println("something went wrong");
        }
    }
}