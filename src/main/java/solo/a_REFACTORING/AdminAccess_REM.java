package solo.a_REFACTORING;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// by Dilan
public class AdminAccess_REM {
    public static boolean accessRights(String email)   {
        boolean isAdmin = false;
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mariadb://172.17.0.1:3308/AMVDatabase","root","12345");
            PreparedStatement ps = con.prepareStatement("select userAdmin from AMVUser where email=?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                isAdmin  = rs.getBoolean("userAdmin");
            }

        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return isAdmin;
    }

}

