package solo.util;

//by Paul

import java.io.PrintWriter;

public class Html {
    public static void printHead(PrintWriter out){
        out.println("<!DOCTYPE html>");
        out.println("<head>");
        out.println("  <title>Toollist</title>"); // TODO: 09.11.2021 set the titel to a string or smth
        out.println("  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />");
        out.println("  <meta charset=\"utf-8\" />");
        out.println("  <link rel=\"stylesheet\" href=\"css/list.css\">" );
        out.println("  <link rel=\"stylesheet\" href=\"css/style.css\">" );
        out.println("</head>");
        out.println("<body>");
    }
}
