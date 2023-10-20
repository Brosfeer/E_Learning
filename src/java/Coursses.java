
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

            String sql = "select course_id from course_progress where user_id =?";
            PreparedStatement state = connection.prepareStatement(sql);
            state.setString(1, user_id);
            ResultSet result = state.executeQuery();

            if (result.next()) {
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
                    String coursseDiscription = resultSet.getString("Description");
                    String teacher_name = resultSet.getString("teacher_name");
                    String Courssetitle = resultSet.getString("title");
                    String coursseDuration = resultSet.getString("Duration");
                    jsonData.append("{\"User_Name\":\"").append(User_Name).append("\",");
                    jsonData.append("{\"E_mail\":\"").append(E_mail).append("\",");
                    jsonData.append("\"teacher_name\":\"").append(teacher_name).append("\",");
                    jsonData.append("\"Courssetitle\":\"").append(Courssetitle).append("\",");
                    jsonData.append("\"coursseDiscription\":\"").append(coursseDiscription).append("\",");
                    jsonData.append("\"coursseDuration\":\"").append(coursseDuration).append("\"}");

                    isFirstRow = false;
                }

                jsonData.append("]}");
                String getDataInSinged = "select*from coursesProfileDetails where user_id=?";

                PreparedStatement query = connection.prepareStatement(getDataInSinged);
                query.setString(1, user_id);
                ResultSet res = query.executeQuery();
                System.out.println("Ther SQL Comand fine");

                StringBuilder jsonDataInSigned = new StringBuilder();

                jsonDataInSigned.append("{\"data of in signed\": [");
                System.out.println("There is Fine");
                boolean isFirstR = true;
                while (res.next()) {
                    if (!isFirstR) {
                        jsonDataInSigned.append(",");
                    }
//                    String User_Name = resultSet.getString("User_Name");
//                    String E_mail = resultSet.getString("E_mail");
                    String coursseDisc = res.getString("Description");
                    String teach_name = res.getString("teacher_name");
                    String Cotitle = res.getString("title");
                    String coDuration = res.getString("Duration");
//                    jsonData.append("{\"User_Name\":\"").append(User_Name).append("\",");
//                    jsonData.append("{\"E_mail\":\"").append(E_mail).append("\",");
                    jsonDataInSigned.append("\"teach_name\":\"").append(teach_name).append("\",");
                    jsonDataInSigned.append("\"CoTitle\":\"").append(Cotitle).append("\",");
                    jsonDataInSigned.append("\"coDiscription\":\"").append(coursseDisc).append("\",");
                    jsonDataInSigned.append("\"coDuration\":\"").append(coDuration).append("\"}");

                    isFirstR = false;
                }

                jsonDataInSigned.append("]}");
                jsonData.append(jsonDataInSigned);

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                out.print(jsonData.toString());
                System.out.println("the Data    " + jsonData.toString());
            } else {
                System.out.println("There is no one ");
                String getData = "select us.User_Name, us.E_mail,co.title,"
                        + "co.Description,co.Duration,ins.teacher_name FROM courses co inner join instructors ins using(instructor_id) inner join users us where us.user_id=? ";

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
                    String coursseDiscription = resultSet.getString("Description");
                    String teacher_name = resultSet.getString("teacher_name");
                    String Courssetitle = resultSet.getString("title");
                    String coursseDuration = resultSet.getString("Duration");
                    jsonData.append("{\"User_Name\":\"").append(User_Name).append("\",");
                    jsonData.append("{\"E_mail\":\"").append(E_mail).append("\",");
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
                
//                out.print("");
            }

//                }
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

    public static String getSession(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        // Set session attribute
        String user_id = (String) session.getAttribute("user_id");
        System.out.println("The User id   " + user_id);
        return user_id;
    }
}
