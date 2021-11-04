package solo.pages;

import solo.models.UserModel;
import solo.util.DBUtils;
import solo.util.hashPassword;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "register", value = "/register")
public class Register extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        //PrintWriter out = response.getWriter();
        //writeUserForm(out);

        try {
            request.getRequestDispatcher("/register.jsp").forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        UserModel model = new UserModel();
        model.setFirstname(request.getParameter("firstname"));
        model.setLastname(request.getParameter("lastname"));
        model.setEmail(request.getParameter("email"));
        model.setPassword(hashPassword.encryptThisString(request.getParameter("password")));
        model.setPhoneNumber(request.getParameter("phoneNumber"));
        model.setUnionMember(request.getParameter("unionMember") != null);
        model.setUserAdmin(request.getParameter("userAdmin") != null);

        writeUserToDB(model);
        response.sendRedirect("login");
    }

    public void writeUserToDB(UserModel model){
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection dbc = DriverManager.getConnection(
                    "jdbc:mariadb://172.17.0.1:3308/AMVDatabase", "root", "12345");
            PreparedStatement statement = dbc.prepareStatement(
                    "insert into AMVUser (email, passwordHash, firstName, lastName, phoneNumber, unionMember, userAdmin) values(?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, model.getEmail());
            statement.setString(2, model.getPassword());
            statement.setString(3, model.getFirstname());
            statement.setString(4, model.getLastname());
            statement.setString(5, model.getPhoneNumber());
            statement.setBoolean(6, model.isUnionMember());
            statement.setBoolean(7, model.isUserAdmin());
            statement.executeUpdate();

            statement.close();
            dbc.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void writeUserForm(PrintWriter out){
        out.print("<section class=\"main\">");
        out.print(    "<h1>Find Tool</h1>");
        out.print(    "<form action='register' method='POST'>");
        out.print(        "<label for = 'firstname'>First Name: </label><br>");
        out.print(        "<input type = 'text' name = 'firstname' required><br>");
        out.print(        "<label for = 'lastname' required>Last Name: </label><br>");
        out.print(        "<input type = 'text' name = 'lastname' required><br>");
        out.print(        "<label for = 'email'>Email: </label><br>");
        out.print(        "<input type = 'email' name = 'email' required><br>");
        out.print(        "<label for = 'password'>Password: </label><br>");
        out.print(        "<input type = 'password' name = 'password' required><br>");
        out.print(        "<label for = 'phone'>Phone: </label><br>");
        out.print(        "<input type = 'tel' name = 'phone' required pattern=\"[0-9]{5,9}\"><br>");
        out.print(        "<label for = 'unionmember'>Union Member: </label><br>");
        out.print(        "<input type = 'checkbox' name = 'unionmember' value = 'true'><br>");
        out.print(        "<label for = 'userAdmin'>Admin: </label><br>");
        out.print(        "<input type = 'checkbox' name = 'userAdmin' value = 'true'><br>");
        out.print(        "<input type = 'submit' value = 'Register User'>");
        out.print(    "</form>");
    }
}