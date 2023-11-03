
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = {"/NewCoursses"})
public class NewCoursses extends HttpServlet {

    Connection connection = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession();

            // Set session attribute
            String user_id = (String) session.getAttribute("user_id");
//            String user_id = "105";
            System.out.println("The user id from Coursses servlet " + user_id);
//            String user_id = getSession(request, response);

            connection = GetConnect.getConnection();

            System.out.println("There is no one ");
            String getData = "select*from courseOfNewUser where user_id= ? ";

            PreparedStatement statement = connection.prepareStatement(getData);
            statement.setString(1, user_id);
            ResultSet resultSet = statement.executeQuery();

            StringBuilder jsonData = new StringBuilder();

            jsonData.append("{\"data\": [");
            boolean isFirstRow = true;
            while (resultSet.next()) {
                if (!isFirstRow) {
                    jsonData.append(",");
                }
                String User_Name = resultSet.getString("User_Name");
                String E_mail = resultSet.getString("E_mail");
                String courseId = resultSet.getString("course_id");
                String courseImg = resultSet.getString("courseImg");
                String coursseDiscription = resultSet.getString("Description");
                String teacher_name = resultSet.getString("teacher_name");
                String Courssetitle = resultSet.getString("title");
                String coursseDuration = resultSet.getString("Duration");
                jsonData.append("{\"User_Name\":\"").append(User_Name).append("\",");
                jsonData.append("\"E_mail\":\"").append(E_mail).append("\",");
                jsonData.append("\"courseId\":\"").append(courseId).append("\",");
                jsonData.append("\"courseImg\":\"").append(courseImg).append("\",");
                jsonData.append("\"teacher_name\":\"").append(teacher_name).append("\",");
                jsonData.append("\"Courssetitle\":\"").append(Courssetitle).append("\",");
                jsonData.append("\"coursseDiscription\":\"").append(coursseDiscription).append("\",");
                jsonData.append("\"coursseDuration\":\"").append(coursseDuration).append("\"}");

                isFirstRow = false;
            }

            jsonData.append("]}");

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(jsonData.toString());
            System.out.println("Courses Not in Progress table       ");
            System.out.println("the Data    " + jsonData.toString());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
//}
// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
    }// </editor-fold>
}
