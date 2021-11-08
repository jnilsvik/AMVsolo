package solo.z_scrap.lists;

import solo.models.ToolModel;
import solo.util.Html;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

/*
by Joachim

prints all the tools NOW WITH IMAGES!
*/
@WebServlet(name = "xtl", value = "/xtl")
public class ToolAllListings extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection dbConnection = DriverManager.getConnection(
                    "jdbc:mariadb://172.17.0.1:3308/AMVDatabase", "root", "12345");
            Html.printHead(out);
            //Html.printSidebar(out);
            printCategories(out,dbConnection);
            printTools(out,dbConnection);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    void printCategories(PrintWriter out, Connection dbConnection){
        out.println("<section class=\"main\">");
        try {
            PreparedStatement ps = dbConnection.prepareStatement(
                    "SELECT toolCategory FROM Tool GROUP BY toolCategory");
            ResultSet rs1 = ps.executeQuery();

            out.println("<section class='categories'>");
            while (rs1.next()) {
                String category = rs1.getString("toolCategory");
                // TODO: 30.10.2021 find out how to filter 
                out.println("<FORM action='tc' method='get'");
                out.println("<div class='category-item' style='background-image: url(img/amv.png);'>");
                out.println("<div class='category-item-inner'>");
                out.println("<button name='category' type='submit' value='"+ category +"'>"+ category.replaceAll("_"," ") +"</button></div></div></FORM>");
            }
            out.println("</section>");}
        catch (SQLException e) {
            e.printStackTrace();
            out.print("smth weith categories");
        }
    }
    void printTools(PrintWriter out,Connection dbConnection){
        out.println("<section class='featured-products'>");
        try {
            PreparedStatement ps = dbConnection.prepareStatement(
                    "select * from Tool order by toolID");
            ResultSet rs2 = ps.executeQuery();

            ToolModel model = null;
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
            out.print("smth weith tools");
        }

    }
}