
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

public class ServiceSync extends HttpServlet {

    public void syncuser()throws Exception {
        try {
            ServiceDb sdb = new ServiceDb();
            String uri = "https://dev100506.service-now.com/api/now/table/sys_user";
            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            String code = "YWRtaW46b2dJbEctIVR1Mks0";
            con.setRequestProperty("Authorization", "Basic YWRtaW46b2dJbEctIVR1Mks0");
            con.setRequestProperty("Content-Type", "application/json");
            int responseCode = con.getResponseCode();
            System.out.println(responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("inside if");
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
                String inputLine;
                StringBuffer res = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    res.append(inputLine);
                }
                System.out.println(res.toString());
                in.close(); System.out.println("finished input");
                JSONParser parser = new JSONParser();
                JSONObject ob = (JSONObject) parser.parse(res.toString());
                System.out.println(ob);
                List<JSONObject> users = (List<JSONObject>) ob.get("result");
                System.out.println("deleting");
                sdb.deldb();
                System.out.println("db");
                for (int i = 0; i < users.size(); i++) {
                    JSONObject job = users.get(i);
                    sdb.addtodb(job);
                }
                // System.out.println(sdb.getUsers());
                
            } else {
                System.out.println("REquest not Worked");
            }
            System.out.println("\n\n inside Servic eNoe ");
        }catch (Exception e) {
            System.out.println(e);
        }

    }

}