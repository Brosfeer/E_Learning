import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/onClickJava"})
public class onClickJava extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Connection connection = null;
            PreparedStatement statement = null;
            ResultSet resultSet = null;

            try {
//                Class.forName("com.mysql.cj.jdbc.Driver");
//                String url = "jdbc:mysql://localhost:3306/e_learning?zeroDateTimeBehavior=convertToNull&useSSL=false";
//                connection = DriverManager.getConnection(url, "root", "admin123");
                connection = GetConnect.getConnection();

                String sql = "SELECT users.User_Name, users.E_mail, students.address FROM users JOIN students ON users.user_id = students.user_id WHERE users.user_id = 1";

                statement = connection.prepareStatement(sql);

                resultSet = statement.executeQuery();

                StringBuilder jsonData = new StringBuilder();
                jsonData.append("{\"data\": [");

                boolean isFirstRow = true;
                while (resultSet.next()) {
                    if (!isFirstRow) {
                        jsonData.append(",");
                    }
                    String userName = resultSet.getString("User_Name");
                    String email = resultSet.getString("E_mail");
                    String address = resultSet.getString("address");

                    jsonData.append("{\"userName\":\"").append(userName).append("\",");
                    jsonData.append("\"email\":\"").append(email).append("\",");
                    jsonData.append("\"address\":\"").append(address).append("\"}");

                    isFirstRow = false;
                }

                jsonData.append("]}");

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                out.print(jsonData.toString());
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            } finally {
                // إغلاق الموارد
                try {
                    if (resultSet != null) {
                        resultSet.close();
                    }
                    if (statement != null) {
                        statement.close();
                    }
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}