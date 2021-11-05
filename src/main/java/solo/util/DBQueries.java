package solo.util;

import solo.models.BookingModel;
import solo.models.ToolModel;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class DBQueries {
    public static ToolModel getToolModelByID(String toolID, PrintWriter out) throws SQLException {
        Connection dbConnection = DBUtils.getNoErrorConnection(out);
        PreparedStatement statement = dbConnection.prepareStatement(
                "select * from Tool where toolID = ?");
        statement.setString(1, toolID);
        ResultSet rs = statement.executeQuery();

        ToolModel model = null;
        while (rs.next()) {
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
        }
        return model;
    }

    public static LinkedList<BookingModel> getBookedDates(PrintWriter out,String toolID) throws SQLException{
        Connection dbConnection = DBUtils.getNoErrorConnection(out);
        PreparedStatement statement = dbConnection.prepareStatement(
                "select * from Booking where toolID = ?");
        statement.setString(1, toolID);
        ResultSet rs = statement.executeQuery();

        LinkedList<BookingModel> model = null;
        while (rs.next()){
            model.add(new BookingModel(
                    rs.getInt("orderID"),
                    rs.getInt("userID"),
                    rs.getInt("toolID"),
                    rs.getTimestamp("startDate").toLocalDateTime().toLocalDate(),
                    rs.getTimestamp("endDate").toLocalDateTime().toLocalDate(),
                    rs.getTimestamp("returnDate").toLocalDateTime().toLocalDate()));
        }
        if (model.size() != 0) {
            for(BookingModel b: model){
                out.print(b.getStartDate());
                out.print(b.getEndDate());
                out.print(b.getReturnDate());
                out.print(b.getUserID());
                out.print(b.getToolID());
            }
        }return model;

    }
    void printBookedDates(LinkedList<BookingModel> model){
    }
}
