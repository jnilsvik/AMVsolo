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
        String password = hashPassword.encryptThisString(request.getParameter("pass"));

        if(CheckUserExist(email,password)){
            HttpSession session=request.getSession();
            session.setAttribute("email", email);
            session.setAttribute("admin", CheckUserAdmin(email));
            session.setAttribute("union", CheckUserUnion(email));

            try {
                request.getRequestDispatcher("/landing.jsp").forward(request,response);
            } catch (ServletException e) {
                e.printStackTrace();
            }
        } else {
            out.print("Invalid email or password"); // TODO: 10.11.2021 check this one for better way
        }
    }
    public boolean CheckUserExist(String email, String pw){
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection dbC = DriverManager.getConnection(
                    "jdbc:mariadb://172.17.0.1:3308/AMVDatabase", "root", "12345");
            PreparedStatement ps = dbC.prepareStatement(
                    "select * from AMVUser where email=? and passwordHash=?");
            ps.setString(1, email);
            ps.setString(2, pw);
            ResultSet rs1 = ps.executeQuery();

            return rs1.next(); // TODO: 10.11.2021 not sure if this is a good way of doing it
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    boolean CheckUserAdmin(String email){
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection dbC = DriverManager.getConnection(
                    "jdbc:mariadb://172.17.0.1:3308/AMVDatabase", "root", "12345");
            PreparedStatement ps = dbC.prepareStatement(
                    "select userAdmin from AMVUser where email=?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            return rs.getBoolean("userAdmin");
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    boolean CheckUserUnion(String email){
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection dbC = DriverManager.getConnection(
                    "jdbc:mariadb://172.17.0.1:3308/AMVDatabase", "root", "12345");
            PreparedStatement ps = dbC.prepareStatement(
                    "select unionMember from AMVUser where email=?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

             return rs.getBoolean("unionMember");
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    boolean ValidateUserIsAdmin(HttpSession session){
        String a = session.getAttribute("admin").toString();
        if (a.equals("1")) {
            return true;
        }
        return false;
    }

    // no clue how to call this...
    void Logout(HttpServletRequest request,HttpServletResponse response){
        HttpSession session = request.getSession(false);
        session.invalidate();
        try {
            request.getRequestDispatcher("/login.jsp").forward(request,response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}