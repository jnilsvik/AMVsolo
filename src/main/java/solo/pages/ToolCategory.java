package solo.pages;

import solo.models.ToolModel;
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

/*
by Joachim

prints all the tools NOW WITH IMAGES!
*/
@WebServlet(name = "tc", value = "/tc")
public class ToolCategory extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        Connection dbConnection = DBUtils.getNoErrorConnection(out);
        printStart(out);
        //Html.printSidebar(out);
        printTools(out,dbConnection,request);
        out.println();
    }
    // TODO: 30.10.2021 should switch this over into Html.java
    void printStart(PrintWriter out){
        out.println("<!DOCTYPE html>");
        out.println("<head>");
        out.println("  <title>Toollist</title>");
        out.println("  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />");
        out.println("  <meta charset=\"utf-8\" />");
        out.println("  <link rel=\"stylesheet\" href=\"css/list.css\">" );
        out.println("  <link rel=\"stylesheet\" href=\"css/style.css\">" );
        out.println("</head>");
        out.println("<body>");
    }

    void printTools(PrintWriter out,Connection dbConnection,HttpServletRequest category){
        // Prints tool's
        out.println("<section class='featured-products'>");
        try {
            PreparedStatement ps = dbConnection.prepareStatement(
                "select * from Tool where toolCategory =?");
            ps.setString(1, String.valueOf(category.getParameter("category")));

            ResultSet rs2 = ps.executeQuery();

            ToolModel model;
            // TODO: 30.10.2021 migth put this in dbq to simplyfy code, mby also make collection 
            while (rs2.next()) {
                model = new ToolModel(
                        rs2.getInt("toolID"),
                        rs2.getString("toolName"),
                        rs2.getString("toolCategory"),
                        rs2.getBoolean("maintenance"),
                        rs2.getInt("priceFirst"),
                        rs2.getInt("priceAfter"),
                        rs2.getInt("certificateID"),
                        rs2.getString("toolDescription"),
                        rs2.getString("picturePath"));

                out.println("<FORM action='td' method='get'>");             //FORM open
                out.println("<div class='featured-product-item'>");         //div open
                out.println("    <div style='background-image: url(img/"+   //img open
                        model.getPicturePath()                              //img path
                                .replaceAll(" ","%20")
                                .replaceAll("æ","%C3%A6")
                                .replaceAll("ø","%C3%B8")
                                .replaceAll("å","%C3%A5") +
                        ");' class='featured-product-item-image'>");         //img class specification
                out.println("    </div>");                                 //img close
                out.println("    <p class='title'>");                       //title open
                out.println(model.getToolName().replaceAll("_"," "));       //title "tool name"
                out.println("    </p>");                                    //title end
                out.println("    <button name='toolID' type='submit' value='"+model.getToolID() +"'>");
                out.println("        View item");                           //button content
                out.println("    </button>");                               //button close
                out.println("</div></FORM>");                               //FORM close | div close
            }
            out.println("</table></section></section></body></html>");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
}