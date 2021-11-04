package solo.models;

import solo.util.DBUtils;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
//By Paul
public class ToolModel {

    private int toolID;
    private String toolName;
    private String toolCategory;
    private boolean maintenance;
    private int priceFirst;
    private int priceAfter;
    private int certificateID;
    private String description;
    private String picturePath;


    public ToolModel(int id, String name, String category, boolean maintenance, int priceFirst, int priceAfter, int certificateID, String description, String picturePath) {
        this.toolID = id;
        this.toolName = name;
        this.toolCategory = category;
        this.maintenance = maintenance;
        this.priceFirst = priceFirst;
        this.priceAfter = priceAfter;
        this.certificateID = certificateID;
        this.description = description;
        this.picturePath = picturePath;
    }

    public int getToolID() {
        return toolID;
    }

    public void setToolID(int toolID) {
        this.toolID = toolID;
    }

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public String getToolCategory() {
        return toolCategory;
    }

    public void setToolCategory(String toolCategory) {
        this.toolCategory = toolCategory;
    }

    public boolean getMaintenance() {
        return maintenance;
    }

    public void setMaintenance(boolean maintenance) {
        this.maintenance = maintenance;
    }

    public int getPriceFirst() {
        return priceFirst;
    }

    public void setPriceFirst(int priceFirst) {
        this.priceFirst = priceFirst;
    }

    public int getPriceAfter() {
        return priceAfter;
    }

    public void setPriceAfter(int priceAfter) {
        this.priceAfter = priceAfter;
    }

    public int getCertificateID() {
        return certificateID;
    }

    public void setCertificateID(int certificateID) {
        this.certificateID = certificateID;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getPicturePath(){
        return picturePath;
    }

    public void setPicturePath(String picturePath){
        this.picturePath = picturePath;
    }

    public LinkedList<LocalDate> getUsedDates(PrintWriter out) throws SQLException {
        Connection dbConnection = DBUtils.getNoErrorConnection(out);
        String query = "SELECT startDate, endDate FROM Booking WHERE toolID = ?;";
        PreparedStatement statement = dbConnection.prepareStatement(query);
        statement.setInt(1, toolID);
        LinkedList<LocalDate> dayDates = new LinkedList<>();
        ResultSet rs = statement.executeQuery();
        while(rs.next()){
            LocalDate start = rs.getTimestamp("startDate").toLocalDateTime().toLocalDate();
            LocalDate end = rs.getTimestamp("endDate").toLocalDateTime().toLocalDate();
            addDatesToLinkedList(dayDates, start, end);
        }
        return dayDates;
    }

    private void addDatesToLinkedList(LinkedList<LocalDate> dates, LocalDate start, LocalDate end){
        while(start.isBefore(end)){
            start = start.plusDays(1);
            dates.add(start);
        }
    }
}
