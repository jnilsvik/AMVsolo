package solo.pages;

import solo.util.hashPassword;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet(name = "login", value = "/login")
public class Login extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        try {
            request.getRequestDispatcher("/login.jsp").forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String email = request.getParameter("email");
        String password = hashPassword.encryptThisString(request.getParameter("password"));
        if(Validation(email,password)){
            HttpSession session=request.getSession();
            session.setAttribute("email", email); //TODO a way to set attributes
            try {
                request.getRequestDispatcher("/landing.jsp").forward(request,response);
            } catch (ServletException e) {
                e.printStackTrace();
            }
        } else {
            out.print("Invalid email or password");
        }
    }
    public boolean Validation(String email, String pw){
        boolean exists = false;
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mariadb://172.17.0.1:3308/AMVDatabase", "root", "12345");
            PreparedStatement ps = con.prepareStatement(
                    "select * from AMVUser where email=? and passwordHash=?");
            ps.setString(1, email);
            ps.setString(2, pw);
            ResultSet rs1 = ps.executeQuery();
            exists = rs1.next();
        } catch (Exception e){
            e.printStackTrace();
        }
        return exists;
    }
}