import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.HttpResponse;
import java.sql.*;
import javax.security.auth.login.LoginContext;
import org.json.simple.JSONObject;
import java.util.*;
import org.json.simple.parser.JSONParser;
import java.text.*;

public class ServiceDb {
    public static Connection con;
    public ServiceDb() {
        try {
            Logs.addlog("[+]inside ServiceDb constructor..");
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

    public void addtodb(JSONObject user) {
        String country = user.get("country").toString();
        String home_phone = user.get("home_phone").toString();
        String phone = user.get("phone").toString();
        String name = user.get("name").toString();
        String employee_number = user.get("employee_number").toString();
        String gender = user.get("gender").toString();
        String city = user.get("city").toString();
        String user_name = user.get("user_name").toString();
        String roles = user.get("roles").toString();
        String title = user.get("title").toString();
        String street = user.get("street").toString();
        String email = user.get("email").toString();
        String preferred_language = user.get("preferred_language").toString();
        String time_zone = user.get("time_zone").toString();

        Statement stmt;
        try {
            String query = String.format(
                    "insert into serviceuser(country,homephone,phone,name,employeenumber,gender,city,username,roles,title,street,email,preferredlanguage,timezone) values( '%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s');",
                    country, home_phone, phone, name, employee_number, gender, city, user_name, roles, title, street,
                    email, preferred_language, time_zone);
            System.out.println("Query -> " + query);
            stmt = con.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public List<JSONObject> getUsers() {
        Statement stmt;
        ResultSet rs = null;
        try {
            String query = String.format("select * from serviceuser;");
            System.out.println("Query -> " + query);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            List<JSONObject> users = ToJasonrs.getResultSet(rs);
            return users;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public void delUsers() {
        Statement stmt;
        ResultSet rs = null;
        try {
            String query = String.format("delete from serviceuser;");
            System.out.println("Query -> " + query);
            stmt = con.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void addrole(JSONObject roles) throws Exception {
        String name = roles.get("name").toString();
        String id = roles.get("sys_id").toString();
        Statement stmt;
        String query = String.format("insert into servicerole(id,name) values('%s','%s');", id, name);
        System.out.println("Query -> " + query);
        stmt = con.createStatement();
        stmt.executeUpdate(query);
    }

    public List<JSONObject> getrole() {
        Statement stmt;
        ResultSet rs = null;
        try {
            String query = String.format("select * from servicerole;");
            System.out.println("Query -> " + query);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            List<JSONObject> users = ToJasonrs.getResultSet(rs);
            return users;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public void delrole() {
        Statement stmt;
        ResultSet rs = null;
        try {
            String query = String.format("delete from servicerole;");
            System.out.println("Query -> " + query);
            stmt = con.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void deldb() {
        Statement stmt;
        ResultSet rs = null;
        try {
            String query = String.format("delete from serviceuser;");
            System.out.println("Query -> " + query);
            stmt = con.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public List<JSONObject> getserviceattr() {
        Statement stmt;
        ResultSet rs = null;
        try {
            String query = String.format("select * from attributes where source = '3';");
            System.out.println("Query -> " + query);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            List<JSONObject> role = ToJasonrs.getResultSet(rs);
            return role;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public List<JSONObject> gettableattr() {
        Statement stmt;
        ResultSet rs = null;
        try {
            String query = String.format("select * from attributes where source = '4';");
            System.out.println("Query -> " + query);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            List<JSONObject> role = ToJasonrs.getResultSet(rs);
            return role;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public String getAttributeValue(String id) throws Exception {
        Statement stmt;
        ResultSet rs = null;
        String query = String.format("select attrname from attributes where id = '%s';", id);
        System.out.println("Query -> " + query);
        stmt = con.createStatement();
        rs = stmt.executeQuery(query);
        rs.next();
        String attr = rs.getString("attrname");
        return attr;
    }

    public List<JSONObject> getmaps() {
        Statement stmt;
        ResultSet rs = null;
        try {
            String query = String.format("select * from maps where app = '2';");
            System.out.println("Query -> " + query);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            List<JSONObject> role = ToJasonrs.getResultSet(rs);
            return role;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public void delmap() {
        Statement stmt;
        try {
            String query = String.format("delete from maps where app = '2';");
            System.out.println("Query -> " + query);
            stmt = con.createStatement();
            stmt.executeUpdate(query);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void addmap(String m11, String m22) {

        Statement stmt;
        ResultSet rs = null;
        try {
            String sfquery = String.format("select id from attributes where attrname = '%s' and source = '3';", m11);
            String dbquery = String.format("select id from attributes where attrname = '%s' and source = '4';", m22);
            String sfid;
            stmt = con.createStatement();
            rs = stmt.executeQuery(sfquery);
            rs.next();
            sfid = rs.getString("id");
            System.out.println("sfid -> " + sfid);
            rs = null;
            rs = stmt.executeQuery(dbquery);
            rs.next();
            String dbid = rs.getString("id");
            System.out.println("dbid -> " + dbid);
            String query = String.format("insert into maps(map1,map2,app) values('%s','%s','%s');", sfid, dbid,"2");
            System.out.println("Query -> " + query);
            stmt = con.createStatement();
            stmt.executeUpdate(query);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public JSONObject grtUser(String email) {
        Statement stmt;
        ResultSet rs = null;
        try {
            String query = String.format("select * from usertable where email = '%s';", email);
            System.out.println("Query -> " + query);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            List<JSONObject> userlist = ToJasonrs.getResultSet(rs);
            JSONObject user = userlist.get(0);
            return user;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public String addtoservice(JSONObject ob) throws Exception {
        try {

            String country = ob.get("country").toString();
            String homephone = ob.get("homephone").toString();
            String phone = ob.get("phone").toString();
            String timezone = ob.get("timezone").toString();
            String employeenumber = ob.get("employeenumber").toString();
            String language = ob.get("language").toString();
            String name = ob.get("name").toString();
            String gender = ob.get("gender").toString();
            String city = ob.get("city").toString();
            String username = ob.get("username").toString();
            String roles = ob.get("roles").toString();
            String title = ob.get("title").toString();
            String street = ob.get("street").toString();
            String email = ob.get("email").toString();

            // creating json
            JSONObject job = new JSONObject();
            job.put("country", country);
            job.put("home_phone", homephone);
            job.put("phone", phone);
            job.put("time_zone", timezone);
            job.put("employee_number", employeenumber);
            job.put("preferred_language", language);
            job.put("name", name);
            job.put("gender", gender);
            job.put("city", city);
            job.put("roles", roles);
            job.put("title", title);
            job.put("street", street);
            job.put("email", email);

            System.out.println("\n\n\n\nto salesforce -> " + job + "\n\n");
            // send it to servicenow
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
            JSONParser parser = new JSONParser();
            JSONObject obs = (JSONObject) parser.parse(res.toString());
            JSONObject obd = (JSONObject) obs.get("result");
            return (obd.get("sys_id").toString());

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public void adtotable(JSONObject user)throws Exception{
        System.out.println("\n\nUsers--------------------->"+user+"\n\n");
        String country = user.get("country").toString();
        String home_phone = user.get("homephone").toString();
        String phone = user.get("phone").toString();
        String name = user.get("name").toString();
        String employee_number = user.get("employeenumber").toString();
        String gender = user.get("gender").toString();
        String city = user.get("city").toString();
        String user_name = "null";
        String roles = user.get("roles").toString();
        String title = user.get("title").toString();
        String street = user.get("street").toString();
        String email = user.get("email").toString();
        String preferred_language = user.get("language").toString();
        String time_zone = user.get("timezone").toString();
        String id = user.get("id").toString();

        Statement stmt;
        try {
            String query = String.format(
                    "insert into serviceuser(sys_id,country,homephone,phone,name,employeenumber,gender,city,username,roles,title,street,email,preferredlanguage,timezone) values( '%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s');",
                    id,country, home_phone, phone, name, employee_number, gender, city, user_name, roles, title, street,
                    email, preferred_language, time_zone);
            System.out.println("Query -> " + query);
            stmt = con.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    

}