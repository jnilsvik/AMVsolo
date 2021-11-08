package solo.pages;

import solo.models.ToolModel;
import solo.util.DBQueries;
import solo.util.Html;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

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
        out.println("img model.getPicturePath());
        model.getDescription();

        out.println("Price first day: " + model.getPriceFirst());
        out.println("Price after first day: "+ model.getPriceAfter());
        out.println(model.getMaintenance()); // this is quite a dumb attribute btw

        //book now
        out.print("<button type='submit' name='book' value='book>");
        //calendar



        // no need
        out.println(model.getToolCategory());





    }

}