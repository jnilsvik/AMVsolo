package solo.util;

import solo.models.BookingModel;
import solo.models.ToolModel;
import solo.models.UserModel;

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

@WebServlet(name = "mt", value = "/mt")
public class ModuleTest extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        Connection dbc = DBUtils.getNoErrorConnection(out);

        Html.printHead(out);
        out.println("<section class='book'>");
        try {
            TestBookingModule(out, dbc);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        out.println("</section>");
        out.println("<section class='printBook'>");
        try {
            TestBookingModulePrint(out,dbc);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        out.println("</section>");
        out.println("<section class='user'>");
        try {
            TestUserModule(out,dbc);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        out.println("</section>");
        out.println("<section class='userPrint'>");
        try {
            TestUserModulePrint(out,dbc);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        out.println("</section>");
        out.println("<section class='tool'>");
        try {
            TestToolModule(out,dbc);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        out.println("</section>");
        out.println("<section class='toolPrint'>");
        try {
            TestToolModulePrint(out,dbc);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        out.println("</section>");
    }

    void TestBookingModule(PrintWriter out, Connection dbConnection) throws SQLException{
        PreparedStatement statement = dbConnection.prepareStatement(
                "select * from Booking where toolID = 2");
        ResultSet rs = statement.executeQuery();

        BookingModel model;
        if (rs.next()) {
            model = new BookingModel(
                    rs.getInt("orderID"),
                    rs.getInt("toolID"),
                    rs.getInt("userID"),
                    rs.getTimestamp("startDate").toLocalDateTime().toLocalDate(),
                    rs.getTimestamp("endDate").toLocalDateTime().toLocalDate(),
                    rs.getTimestamp("returnDate").toLocalDateTime().toLocalDate());
            out.println(model.getOrderID());
            out.println(model.getToolID());
            out.println(model.getUserID());
            out.println(model.getStartDate());
            out.println(model.getEndDate());
            out.println(model.getReturnDate());
        }
    }

    void TestUserModule(PrintWriter out, Connection dbConnection) throws SQLException{
        PreparedStatement statement = dbConnection.prepareStatement(
                "select * from AMVUser order by userID ");
        ResultSet rs = statement.executeQuery();

        UserModel model;
        if (rs.next()) {
            model = new UserModel(
                    rs.getInt("userID"),
                    rs.getString("firstname"),
                    rs.getString("lastname"),
                    rs.getString("phoneNumber"),
                    "password",
                    rs.getBoolean("unionMember"),
                    rs.getBoolean("userAdmin"),
                    rs.getString("email"));
            out.println(model.getUserID());
            out.println(model.getEmail());
            out.println(model.getPassword());
            out.println(model.getFirstname());
            out.println(model.getLastname());
            out.println(model.getPhoneNumber());
            out.println(model.isUnionMember());
            out.println(model.isUserAdmin());
        }
    }

    void TestToolModule(PrintWriter out, Connection dbConnection) throws SQLException{
        PreparedStatement statement = dbConnection.prepareStatement(
                "select * from Tool order by toolID ");
        ResultSet rs = statement.executeQuery();

        ToolModel model;
        if (rs.next()) {
            model = new ToolModel(
                    rs.getInt("toolID"),
                    rs.getString("toolName"),
                    rs.getString("toolCategory"),
                    rs.getBoolean("maintenance"),
                    rs.getInt("priceFirst"),
                    rs.getInt("priceAfter"),
                    rs.getInt("certificateID"),
                    rs.getString("description"),
                    rs.getString("picturePath"));
            out.println(model.getToolID());
            out.println(model.getToolName());
            out.println(model.getToolCategory());
            out.println(model.getMaintenance());
            out.println(model.getPriceFirst());
            out.println(model.getPriceAfter());
            out.println(model.getCertificateID());
            out.println(model.getDescription());
            out.println(model.getPicturePath());
        }
    }

    void TestBookingModulePrint(PrintWriter out, Connection dbConnection) throws SQLException{
        PreparedStatement statement = dbConnection.prepareStatement(
                "select * from Booking where toolID = 2");
        ResultSet rs = statement.executeQuery();

        if (rs.next()) {
            out.println(rs.getInt("orderID"));
            out.println(rs.getInt("toolID"));
            out.println(rs.getInt("userID"));
            out.println(rs.getTimestamp("startDate").toLocalDateTime().toLocalDate());
            out.println(rs.getTimestamp("endDate").toLocalDateTime().toLocalDate());
            out.println(rs.getTimestamp("returnDate").toLocalDateTime().toLocalDate());
        }
    }

    void TestUserModulePrint(PrintWriter out, Connection dbConnection) throws SQLException{
        PreparedStatement statement = dbConnection.prepareStatement(
                "select * from AMVUser order by userID ");
        ResultSet rs = statement.executeQuery();

        if (rs.next()) {
            out.println(rs.getInt("userID"));
            out.println(rs.getString("email"));
            out.println("password");
            out.println(rs.getString("firstname"));
            out.println(rs.getString("lastname"));
            out.println(rs.getString("phoneNumber"));
            out.println(rs.getBoolean("unionMember"));
            out.println(rs.getBoolean("userAdmin"));
        }
    }

    void TestToolModulePrint(PrintWriter out, Connection dbConnection) throws SQLException{
        PreparedStatement statement = dbConnection.prepareStatement(
                "select * from Tool order by toolID ");
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
        }
    }
}
