
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

public class ServiceNow extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ServiceDb sdb = new ServiceDb();
            response.setContentType("text/json");
            PrintWriter out = response.getWriter();
            response.addHeader("Access-Control-Allow-Origin", "*");
            out.println(sdb.getUsers());
        }catch (Exception e) {
            System.out.println(e);
        }

    }
    public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
        try{
            String country = request.getParameter("country");
        String homephone= request.getParameter("homephone");
        String phone= request.getParameter("phone");
        String timezone = request.getParameter("timezone");
        String employeenumber = request.getParameter("employeenumber");
        String language = request.getParameter("language");
        String name = request.getParameter("name");
        String gender = request.getParameter("gender");
        String city= request.getParameter("city");
        String username = request.getParameter("username");
        String roles = request.getParameter("roles");
        String title = request.getParameter("title");
        String street = request.getParameter("street");
        String email = request.getParameter("email");
        

        //creating json
        JSONObject job = new JSONObject();
        job.put("country",country);
        job.put("home_phone",homephone);
        job.put("phone",phone);
        job.put("time_zone",timezone);
        job.put("employee_number",employeenumber);
        job.put("preferred_language",language);
        job.put("name",name);
        job.put("gender",gender);
        job.put("city",city);
        job.put("roles",roles);
        job.put("title",title);
        job.put("street",street);
        job.put("email",email);



        //send it to servicenow
        String url = "https://dev100506.service-now.com/api/now/table/sys_user";
        URL uri = new URL(url);
        HttpURLConnection con = (HttpURLConnection) uri.openConnection();
        con.setRequestProperty("Authorization", "Basic YWRtaW46b2dJbEctIVR1Mks0");
        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestMethod("POST");

        // request
        OutputStream os = con.getOutputStream();
        os.write(String.valueOf(job).getBytes("UTF-8"));
        os.close();

        // response
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer res = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            res.append(inputLine);
        }
        in.close();
        System.out.println(res.toString());


        }catch(Exception e){
            System.out.println(e);
        }
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        response.addHeader("Access-Control-Allow-Origin", "*");
        out.println("success");
    }

}