package solo.pages;

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
import java.time.LocalDate;
import java.util.LinkedList;

@WebServlet(name = "b", value = "/b")
public class Booking extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();

        out.println("<form action='b' method='post'>");
        out.println(    "<label for='dateStart'> start: </label>");
        out.println(    "<input type='date' name='dateStart' id='dateStart' min='"+LocalDate.now()+"' required>");
        out.println(    "<label for='dateEnd'> until: </label>");
        out.println(    "<input type='date' name='dateEnd' id='dateEnd' min='"+LocalDate.now()+"' max='"+LocalDate.now().plusDays(3)+"' required>");
        // TODO: 08.11.2021 currently this is does not work as intended as we cannot limit the the date, will prob need to use the amount of days
        out.println(    "<input type='hidden' name='uID' value='"+ req.getParameter("userID") +"'>");
        out.println(    "<input type='hidden' name='tID' value='"+ req.getParameter("toolID") +"'>");
        out.println(    "<input type='submit'>");
        out.println("</form>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();

        LocalDate reqStartDate = LocalDate.parse(req.getParameter("dateStart"));
        LocalDate reqEndDate = LocalDate.parse(req.getParameter("dateEnd"));

        try {
            Html.printHead(out);
            // * declares the model for transfer
            BookingModel bModel = new BookingModel(
                    0,
                    Integer.parseInt(req.getParameter("userID")),
                    Integer.parseInt(req.getParameter("toolID")),
                    reqStartDate,
                    reqEndDate,
                    null);
            // gets the tools booked dates, compares them to the requested dates -> returns bool. if true = insert tool into db
            if(CompareAndValidateBooking(reqStartDate, reqEndDate, GetBookedDates(out,req.getParameter("toolID")))){
                InsertBooking(out, bModel);
                out.print("Booking complete"); // TODO: 31.10.2021 re-dir. to "booking complete" page
            } else {
                out.print("Could not complete"); // TODO: 31.10.2021 figure out what to w/this
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static LinkedList<BookingModel> GetBookedDates(PrintWriter out, String toolID) throws SQLException{
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
        return model;
    }

    boolean CompareAndValidateBooking(LocalDate rSD, LocalDate rED, LinkedList<BookingModel> bookings){
        boolean ava = true;
        for (BookingModel b : bookings){
            // ! why? (removes the relevance of endDate) & checks if req_endDate crashes
            // (om vår forespurt dato er etter bookings ende dato) OG (ende dato er før booking starter) =TRUE
            if ((rSD.isAfter(b.getEndDate()) || rSD.isAfter(b.getReturnDate())) && rED.isBefore(b.getStartDate())){
                continue;
            } else ava=false;
        }
        return ava;
    }

    void InsertBooking(PrintWriter out, BookingModel model) throws SQLException{
        Connection dbc = DBUtils.getNoErrorConnection(out);
        PreparedStatement statement = dbc.prepareStatement(
                "insert into Booking(userID, toolID, startDate, endDate, totalPrice) values (?,?,?,?,?)");
        statement.setInt(1, model.getUserID());
        statement.setInt(1, model.getToolID());
        statement.setObject(1, model.getStartDate());
        statement.setObject(1, model.getEndDate());
        statement.setDouble(1, model.getTotalPrice(out)); //todo check this one
        statement.executeUpdate();
    }
}
