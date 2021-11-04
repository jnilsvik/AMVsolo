package solo.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

public class DBUtils {
    private static final DBUtils INSTANCE = new DBUtils();
    static Connection connection;

    public static DBUtils getINSTANCE() {
        return INSTANCE;
    }

    private Connection getConnection(PrintWriter out) throws SQLException {
        try{
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            DataSource ds = (DataSource)envCtx.lookup("jdbc/database");

            return ds.getConnection();
        }
        catch(NamingException ex)
        {
            out.println(ex.getMessage());
        }
        return null;
    }

    public static Connection getNoErrorConnection(PrintWriter out){
        Connection dbConnection = null;
        try{
            dbConnection = DBUtils.getINSTANCE().getConnection(out);
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return dbConnection;
    }
}

