package solo.pages.toolPrev;

import solo.models.ToolModel;
import solo.util.DBQueries;
import solo.util.DBUtils;
import solo.util.Html;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*  by Joachim
* dunno why it dont work, smth with the toolmodel not being loaded?*/
@WebServlet(name = "td", value = "/td")
public class ToolDetail extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        Html.printHead(out);
        try {
            writeToolDetails((DBQueries.getToolModelByID(request.getParameter("toolID"),out)),out);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void writeToolDetails(ToolModel model, PrintWriter out){
        out.println("<h1>"+model.getToolName()+"</h1>");
        out.println("<img src='img/"+model.getPicturePath()+"'>");
        out.println("<p>"+model.getDescription()+"</p>");
        out.println("<p>Price first day: " + model.getPriceFirst()+"</p>");
        out.println("<p>Price after first day: "+ model.getPriceAfter()+"</p>");
        //out.println(model.getMaintenance()); // this is quite a dumb attribute btw
        //book now
        out.print("<form action='b' method='GET'>");
        out.print("<button type='submit' name='book' value='book'></button>");
        // TODO: 08.11.2021 prevent end date from being more that 3 days after start date
        //calendar
        out.print("set start date");
        out.print("<set end dat");
        out.print("</form>");
        LocalDate rSD;
        LocalDate rED;

        //prints calendar
        Calendar(out, model.getToolID());
    }
    void Calendar2(PrintWriter out, int tID) throws SQLException {
        // TODO: 08.11.2021 needs a way to check amount of days in month, start date NAME (monday)
        Connection dbConnection = DBUtils.getNoErrorConnection(out);
        String toolQ = "select * from Booking where toolID = ?"; // what about when a tool hasn't been delivered back yet?
        PreparedStatement statement = dbConnection.prepareStatement(toolQ);
        statement.setInt(1, tID);
        //statement.setString(2, LocalDate.now().toString());
        ResultSet rs = statement.executeQuery();

        LocalDate startDate;
        LocalDate endDate;
        List<LocalDate> takenDates = null;
        while (rs.next()){
            startDate = rs.getDate("startDate").toLocalDate();
            endDate = rs.getDate("endDate").toLocalDate();
            while (startDate.isBefore(endDate)){
                takenDates.add(startDate);
                startDate = startDate.plusDays(1);
            }
        }
        //create the table heading and starts the body
        out.println(
                "<table class='table-sortable'>" +
                "    <thead>" +
                "        <tr>" +
                "            <th>Monday</th>" +
                "            <th>Tuesday</th>" +
                "            <th>Wednesday</th>" +
                "            <th>Thursday</th>" +
                "            <th>Friday</th>" +
                "            <th>Saturday</th>" +
                "            <th>Sunday</th>" +
                "        </tr>" +
                "    </thead>" +
                "<tbody>");

        //formatting help
        LocalDate date = LocalDate.now();
        int dayInMonth = date.getDayOfMonth();
        int lengthOfMonth = date.lengthOfMonth();
        int newWeek = 0;

        //status checking
        String status;
        String colour;
        while (dayInMonth < lengthOfMonth){
            status = "Available";
            colour = "#00FF00";
            if (takenDates.contains(date)){
                status = "Unavailable";
                colour = "#FF0000";
            }
            //day printing
            out.print("<td bgcolor=" + colour + ">" + date + "<br>" + status + "</td>");
            //week division
            if (newWeek == 7){
                out.println("</tr><tr>");
                newWeek = 0;
            }
            date.plusDays(1);
            dayInMonth++;
            newWeek++;
        }

    }
    public static void main(String[] args){
        System.out.println(LocalDate.now().with(DayOfWeek.MONDAY));
        System.out.println(LocalDate.now());

        LocalDate currentDate = LocalDate.now();
        currentDate = currentDate.with(DayOfWeek.MONDAY);
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String currentDateFormat = currentDate.format(formatters);
        System.out.println(currentDateFormat);
    }//End of main

    public void Calendar(PrintWriter out, int toolID) {
        try {
            Connection db = DBUtils.getNoErrorConnection(out);
            PreparedStatement st2 = db
                    .prepareStatement("SELECT * FROM Booking WHERE toolID = ? AND returnDate IS NULL");
            st2.setInt(1, toolID);
            ResultSet rs2 = st2.executeQuery();

            List<LocalDate> totalDates = new ArrayList<>();
            while (rs2.next()) {

                LocalDate dateStart = rs2.getDate("startDate").toLocalDate();
                LocalDate dateEnd = rs2.getDate("endDate").toLocalDate();

                while (!dateStart.isAfter(dateEnd)) {
                    totalDates.add(dateStart);
                    dateStart = dateStart.plusDays(1);
                }
            }
            LocalDate currentDate = LocalDate.now();
            currentDate = currentDate.with(DayOfWeek.MONDAY);
            int days = 0;
            int resetWeek = 1;
            DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            out.println("<h2>Available dates</h2>");
            out.println("<table>");
            out.println("   <tr>");
            out.println("       <th>Monday</th>");
            out.println("       <th>Tuesday</th>");
            out.println("       <th>Wednesday</th>");
            out.println("       <th>Thursday</th>");
            out.println("       <th>Friday</th>");
            out.println("       <th>Saturday</th>");
            out.println("       <th>Sunday</th>");
            out.println("   </tr>");
            out.println("<tr>");

            while (days <= 120) {
                //sets colour dependant on availability
                String status = "Available";
                String color = "#00FF00";
                if (totalDates.contains(currentDate)) {
                    status = "Booked";
                    color = "#FF0000";
                }
                //Print the actual line
                String currentDateFormat = currentDate.format(formatters);
                out.println("<td bgcolor=" + color + ">" + currentDateFormat + "<br>" + status + "</td>");
                //resets the week (amount of days per coloums
                if (resetWeek == 7) {
                    out.println("</tr>");
                    out.println("<tr>");
                    resetWeek = 0;
                }
                currentDate = currentDate.plusDays(1);
                days++;
                resetWeek++;
            }

            out.println("</tr>");
            out.println("</table>");
        } catch (Exception e) {
            out.println("error");
        }
    }
}