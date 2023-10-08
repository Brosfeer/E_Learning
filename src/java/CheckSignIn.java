
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/CheckSignIn"})
public class CheckSignIn extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String user_name = request.getParameter("user_name");
            String password = request.getParameter("password");
            System.out.println("The User Name is   " + user_name);

            try {
//               
                Class.forName("com.mysql.cj.jdbc.Driver");
                String url = "jdbc:mysql://localhost:3306/e_learning?zeroDateTimeBehavior=convertToNull&useSSL=false";

                Connection conn = DriverManager.getConnection(url, "root", "root");

                String sql = "SELECT user_id FROM USERS WHERE `password`= ? AND `User_Name` =?";

                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, password);
                statement.setString(2, user_name);
                System.out.println(user_name);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {

                    String courseSQL = "SELECT title,description from  courses ";
                     
                    PreparedStatement lessons = conn.prepareStatement(courseSQL);
                    
//                    lessons.setString(1, user_id);
//                System.out.println(user_name);
                    ResultSet result = lessons.executeQuery();
                    while(result.next()){
                        System.out.println("After Ensur of id fine");
                        String courseTitle=result.getString("title");
                        String description=result.getString("description");
//                        String lessonContent=result.getString("lessons.contect");
                        System.out.println(courseTitle);
                        System.out.println(description);
                    }
                    String data = resultSet.getString("user_id");
                    if (!data.isEmpty()) {
                    out.print(data);
                } else {
                    out.print("flase");
                }
//                        System.out.println(data);
//                        out.println(data);

                }
                resultSet.close();
                statement.close();
                conn.close();
            } catch (Exception ex) {
            }
        }
    }
}
