package solo.models;

import solo.util.DBUtils;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;

public class BookingModel {

    private int orderID;
    private int userID;
    private int toolID;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate returnDate;

    public BookingModel(int orderID, int userID, int toolID, LocalDate startDate, LocalDate endDate, LocalDate returnDate) {
        this.orderID = orderID;
        this.userID = userID;
        this.toolID = toolID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.returnDate = returnDate;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getToolID() {
        return toolID;
    }

    public void setToolID(int toolID) {
        this.toolID = toolID;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public double getTotalPrice(PrintWriter out){
        double totalPrice = 0;
        try {
            Connection db = DBUtils.getNoErrorConnection(out);
            String query = "SELECT Tool.priceAfter, Tool.priceFirst\n" +
                    "FROM Tool\n" +
                    " WHERE Tool.toolID = ?;";
            PreparedStatement statement = db.prepareStatement(query);
            statement.setInt(1, toolID);
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) throw new SQLException("No tool could be found");
            double priceFirstDay = rs.getDouble("priceFirstDay");
            double priceAfterFirstDay = rs.getDouble("priceAfterFirstDay");
            totalPrice = calculateTotalPrice(priceFirstDay, priceAfterFirstDay);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return totalPrice;
    }

    private double calculateTotalPrice(double priceFirstDay, double priceAfterFirstDay){
        Period period = Period.between(startDate, endDate);
        period.minusDays(1);
        return priceAfterFirstDay + priceAfterFirstDay * period.getDays();
    }

    public String getToolName(PrintWriter out){
        String toolName = "";
        try {
            Connection db = DBUtils.getNoErrorConnection(out);
            String query = "SELECT toolName FROM Tool WHERE toolID = ?;";
            PreparedStatement statement = db.prepareStatement(query);
            statement.setInt(1, toolID);
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) throw new SQLException("No tool could be found");
            toolName = rs.getString("toolName");
        } catch (SQLException e){
            e.printStackTrace();
        }
        //Currently, there is no toolName stored in the db will be added
        return "currently no toolName stored";
    }
}
