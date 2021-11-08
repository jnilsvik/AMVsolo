package solo.z_scrap.lists;

import solo.models.BookingModel;
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
import java.util.LinkedList;

@WebServlet(name = "test", value = "/test")
public class TEST extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        Html.printHead(out);
        Connection dbConnection = DBUtils.getNoErrorConnection(out);
        try {
            PreparedStatement statement = dbConnection.prepareStatement(
                    //"select * from Booking where toolID = ?");
                    "select * from Booking where toolID = 2");
            //statement.setString(1, req.getParameter("toolID"));
            ResultSet rs = statement.executeQuery();

            LinkedList<BookingModel> model = null;
            while (rs.next()) {
                model.add(new BookingModel(
                        rs.getInt("orderID"),
                        rs.getInt("userID"),
                        rs.getInt("toolID"),
                        rs.getTimestamp("startDate").toLocalDateTime().toLocalDate(),
                        rs.getTimestamp("endDate").toLocalDateTime().toLocalDate(),
                        rs.getTimestamp("returnDate").toLocalDateTime().toLocalDate()));
            }
            if(model.size() == 0)
                for (BookingModel b : model) {
                    out.print(b.getStartDate());
                    out.print(b.getEndDate());
                    out.print(b.getReturnDate());
                    out.print(b.getUserID());
                    out.print(b.getToolID());
                }

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
