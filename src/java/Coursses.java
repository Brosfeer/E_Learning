
import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author Computer
 */
@WebServlet(urlPatterns = {"/Coursses"})
public class Coursses extends HttpServlet {

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

            boolean isInProgress = checkCourseInProgress(user_id);

            if (isInProgress) {
                System.out.println("there is course in progress");
                String getData = "select*from coursesProfileDetails where user_id=?";

                PreparedStatement statement = connection.prepareStatement(getData);
                statement.setString(1, user_id);
                ResultSet resultSet = statement.executeQuery();
                System.out.println("Ther SQL Comand fine");

                StringBuilder jsonData = new StringBuilder();

                jsonData.append("{\"data\": [");
                System.out.println("There is Fine");
                boolean isFirstRow = true;
                while (resultSet.next()) {
                    if (!isFirstRow) {
                        jsonData.append(",");
                    }
                    String User_Name = resultSet.getString("User_Name");
                    String E_mail = resultSet.getString("E_mail");
                    String courseId = resultSet.getString("course_id");
                    String coursseDiscription = resultSet.getString("Description");
                    String teacher_name = resultSet.getString("teacher_name");
                    String Courssetitle = resultSet.getString("title");
                    String coursseDuration = resultSet.getString("Duration");
                    jsonData.append("{\"User_Name\":\"").append(User_Name).append("\",");
                    jsonData.append("\"E_mail\":\"").append(E_mail).append("\",");
                    jsonData.append("\"courseId\":\"").append(courseId).append("\",");
                    jsonData.append("\"teacher_name\":\"").append(teacher_name).append("\",");
                    jsonData.append("\"Courssetitle\":\"").append(Courssetitle).append("\",");
                    jsonData.append("\"coursseDiscription\":\"").append(coursseDiscription).append("\",");
                    jsonData.append("\"coursseDuration\":\"").append(coursseDuration).append("\"}");

                    isFirstRow = false;
                }

                jsonData.append("]}");
                
                
                String getRest = "SELECT co.course_id,co.title, co.description, co.duration, ins.teacher_name \n"
                        + "FROM courses co \n"
                        + "LEFT JOIN course_progress pro ON co.course_id = pro.course_id AND pro.user_id =?\n"
                        + "INNER JOIN instructors ins USING (instructor_id) \n"
                        + "WHERE pro.course_id IS NULL";

                PreparedStatement statem = connection.prepareStatement(getRest);
                statem.setString(1, user_id);
                ResultSet resultRes = statem.executeQuery();
                System.out.println("Ther SQL Comand fine");

                StringBuilder jsonRestData = new StringBuilder();

                jsonRestData.append(",{\"Restdata\": [");
                System.out.println("There is Fine");
                boolean isFirstRo = true;
                while (resultRes.next()) {
                    if (!isFirstRo) {
                        jsonRestData.append(",");
                    }
//                    String User_Name = resultSet.getString("User_Name");
//                    String E_mail = resultSet.getString("E_mail");
                    String courseId = resultRes.getString("course_id");
                    String coursseDiscription = resultRes.getString("Description");
                    String teacher_name = resultRes.getString("teacher_name");
                    String Courssetitle = resultRes.getString("title");
                    String coursseDuration = resultRes.getString("Duration");
//                    jsonData.append("{\"User_Name\":\"").append(User_Name).append("\",");
//                    jsonData.append("\"E_mail\":\"").append(E_mail).append("\",");
                    jsonRestData.append("{\"courseId\":\"").append(courseId).append("\",");
                    jsonRestData.append("\"teacher_name\":\"").append(teacher_name).append("\",");
                    jsonRestData.append("\"Courssetitle\":\"").append(Courssetitle).append("\",");
                    jsonRestData.append("\"coursseDiscription\":\"").append(coursseDiscription).append("\",");
                    jsonRestData.append("\"coursseDuration\":\"").append(coursseDuration).append("\"}");

                    isFirstRo = false;
                }

                jsonRestData.append("]}");

//                StringBuilder returnData=new StringBuilder();
//                returnData.append(jsonData);
//                returnData.append(jsonRestData);

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                out.print(jsonData.toString());
                System.out.println("the Data    " + jsonData.toString());
            } else {
                System.out.println("There is no one ");
                String getData = "select*from courseOfNewUser where user_id= ? ";

                PreparedStatement statement = connection.prepareStatement(getData);
                statement.setString(1, user_id);
                ResultSet resultSet = statement.executeQuery();

                StringBuilder jsonData = new StringBuilder();

                jsonData.append("{\"data2\": [");
                boolean isFirstRow = true;
                while (resultSet.next()) {
                    if (!isFirstRow) {
                        jsonData.append(",");
                    }
                    String User_Name = resultSet.getString("User_Name");
                    String E_mail = resultSet.getString("E_mail");
                    String courseId = resultSet.getString("course_id");
                    String coursseDiscription = resultSet.getString("Description");
                    String teacher_name = resultSet.getString("teacher_name");
                    String Courssetitle = resultSet.getString("title");
                    String coursseDuration = resultSet.getString("Duration");
                    jsonData.append("{\"User_Name\":\"").append(User_Name).append("\",");
                    jsonData.append("\"E_mail\":\"").append(E_mail).append("\",");
                    jsonData.append("\"courseId\":\"").append(courseId).append("\",");
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

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    boolean checkCourseInProgress(String user_id) throws SQLException {
        String sql = "select course_id from course_progress where user_id =?";
        PreparedStatement state = connection.prepareStatement(sql);
        state.setString(1, user_id);
        ResultSet result = state.executeQuery();
        if (result.next()) {
            return true;
        } else {
            return false;
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
