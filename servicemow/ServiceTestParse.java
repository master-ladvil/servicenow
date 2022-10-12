import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import javax.servlet.http.*;
import java.io.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import javax.security.auth.login.LoginContext;
import org.json.simple.JSONObject;
import java.util.*;
import org.json.simple.parser.JSONParser;
import java.text.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import javax.servlet.ServletException;
public class ServiceTestParse {
    public ServiceTestParse(){
        try {
            //System.out.println("[+]inside getuser constructor..");
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
    public static Connection con = null;
    public static JSONObject generateAttr(JSONObject dbttr) throws Exception{
       ServiceTestParse sf = new ServiceTestParse();
       List<JSONObject> maps = sf.getmaps();
       List<JSONObject> map = sf.getmapvalues(maps);

       for(int i =0;i<map.size();i++){
           JSONObject ob = map.get(i);
           System.out.println(ob.get("attrname") + "->" + ob.get("dbattrname"));
       }
       ServiceDb dbob = new ServiceDb();
       List<JSONObject> attr = dbob.getserviceattr();
       //List<JSONObject> dbattr = sf.getdbattr();
       //System.out.println("\n\n"+dbattr+"\n\n");
       JSONObject sfttr = new JSONObject();
       //JSONObject dbttr = new JSONObject();
       for(int i=0;i<attr.size();i++){
           JSONObject ob = new JSONObject();
           ob = attr.get(i);
           
           sfttr.put(ob.get("attrname").toString(),"null");
       }
       sfttr.replace("timezone","Indian/Chagos");
        /*for(int i=0;i<dbattr.size();i++){
        JSONObject ob = new JSONObject();
        ob = dbattr.get(i);
        //System.out.println(ob.get("attrname"));
        dbttr.put(ob.get("attrname").toString(),ob.get("attrname").toString());
    } */
    //System.out.println("\n\n"+ dbttr + "\n\n");
    //System.out.println("\n\n"+dbttr+"\n\n");
       for(int i =0;i<map.size();i++){
           JSONObject mapping = map.get(i);
           sfttr.replace(mapping.get("attrname").toString(),sf.getval(mapping.get("dbattrname").toString(), dbttr));
       }
       System.out.println("\n\n"+sfttr+"\n\n");
       //System.out.println(dbttr);
       
       return sfttr;

    }
    public List<JSONObject> getsfattr(){
        Statement stmt;
        ResultSet rs = null;
        try{
            String query = String.format("select * from sfattr;");
            //System.out.println("Query -> " + query);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            List<JSONObject> role = ToJasonrs.getResultSet(rs);
            return role;
        }catch(Exception e){
            System.out.println(e);
        }
        return null;
    }
    public List<JSONObject> getdbattr(){
        Statement stmt;
        ResultSet rs = null;
        try{

            String query = String.format("select * from attributes where source = 'userdb';");
            //System.out.println("Query -> " + query);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            List<JSONObject> role = ToJasonrs.getResultSet(rs);
            return role;
        }catch(Exception e){
            System.out.println(e);
        }
        return null;
    }

    public List<JSONObject> getmaps(){
        Statement stmt;
        ResultSet rs = null;
        try{
            String query = String.format("select * from servicemap;");
            System.out.println("Query -> " + query);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            List<JSONObject> role = ToJasonrs.getResultSet(rs);
            return role;
        }catch(Exception e){
            System.out.println(e);
        }
        return null;
    }
    public String getval(String attr,JSONObject ob){
        System.out.println("\n\n"+attr+" ----> " + ob);
        return ob.get(attr).toString();
    }
    public List<JSONObject> getmapvalues(List<JSONObject> perm) throws Exception{
        
        List<JSONObject> maps = new ArrayList<JSONObject>();
        for (int i =0;i<perm.size();i++){
            ServiceTestParse sf = new ServiceTestParse();
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
    public String getAttributeValue(String id) throws Exception{
        Statement stmt;
        ResultSet rs = null;
        String query = String.format("select attrname from serviceattr where id = '%s';",id);
        //System.out.println("Query -> "+query);
        stmt = con.createStatement();
        rs = stmt.executeQuery(query);
        rs.next();
        String attr = rs.getString("attrname");
        return attr;
    }
}
