
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import javax.servlet.http.*;
import java.io.*;
import java.net.http.HttpResponse;
import java.sql.*;
import javax.security.auth.login.LoginContext;
import org.json.simple.JSONObject;
import java.util.*;
import org.json.simple.parser.JSONParser;
import java.text.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import javax.servlet.ServletException;

public class ServiceUserDb extends HttpServlet {
    public static Connection con;

    public ServiceUserDb() {
        try {
            System.out.println("[+]inside getuser constructor..");
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/elloauth", "postgres", "pwd");
            if (con != null) {
                System.out.println("connection estabished");
            } else {
                System.out.println("Connection failed");
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException{
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String title = request.getParameter("title");
        String street = request.getParameter("street");
        String city = request.getParameter("city");
        String country = request.getParameter("country");
        String timezone = request.getParameter("timezone");
        String language = request.getParameter("language");
        String gender = request.getParameter("gender");
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        Statement stmt;
        try{
            //usertable
            String query= String.format("insert into usertable(name,email,phone,title,street,city,country,timezone,gender,language) values('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s');",name,email,phone,title,street,city,country,timezone,gender,language);
            System.out.println("Query -> "+query);
            stmt = con.createStatement();
            stmt.executeUpdate(query);


            //mapping
            ServiceDb userob = new ServiceDb();
            JSONObject user = userob.grtUser(email);
            ServiceTestParse getatob = new ServiceTestParse();
            JSONObject attr = getatob.generateAttr(user);
            System.out.println(attr);
            //service now
            String id = userob.addtoservice(attr);
            response.addHeader("Access-Control-Allow-Origin", "*");
            out.println(id);
            attr.put("id",id);
            userob.adtotable(attr);

        }catch(Exception e){System.out.println(e);}
        
        
    }
}
