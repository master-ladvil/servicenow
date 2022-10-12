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

public class ServiceMap extends HttpServlet {
    ServiceDb sf = new ServiceDb();
    public List<JSONObject> getmapvalues(List<JSONObject> perm) throws Exception{
        
        List<JSONObject> maps = new ArrayList<JSONObject>();
        for (int i =0;i<perm.size();i++){
            JSONObject job = new JSONObject();
            job = perm.get(i);
            String map1 = sf.getAttributeValue(job.get("map1").toString());
            String map2 = sf.getAttributeValue(job.get("map2").toString());
            JSONObject ob = new JSONObject();
            ob.put("attrname",map1);
            ob.put("dbattrname",map2);
            ob.put("id",job.get("id").toString());
            maps.add(ob);
        }
        return maps;
    }
   
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            
            List<JSONObject> perm = sf.getmaps();
            
            response.setContentType("text/json");
            PrintWriter out = response.getWriter();
            response.addHeader("Access-Control-Allow-Origin", "*");
            List<JSONObject> maps = getmapvalues(perm);
            out.println(maps);
        } catch (Exception e) {
            System.out.println(e);

        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            ServiceDb sf = new ServiceDb();
            String map1 = request.getParameter("m1");
            String map2 = request.getParameter("m2");
            //JSONParser parser = new JSONParser();
            //JSONObject job = (JSONObject) parser.parse(map);
            String[] m1 = map1.split("\\,");
            String[] m2 = map2.split("\\,");
            sf.delmap();
            for(int i =0 ;i<m1.length;i++){
                System.out.println(m1[i] + " -> " + m2[i]);
                sf.addmap(m1[i], m2[i]);
            }
            response.setContentType("text/plain");
            PrintWriter out = response.getWriter();
            response.addHeader("Access-Control-Allow-Origin", "*");
            out.println("success");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
}
