
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
//            String courseId = request.getParameter("courseId");
            String courseId = "1";
            System.out.println("The Course Id   " + courseId);

            Connection connection = null;
            PreparedStatement statement = null;
            ResultSet resultSet = null;

            try {
                connection = GetConnect.getConnection();
                

                String sql = "select co.title as CourseTitle,co.description as CourseDescription"
                        + ",co.duration as CourseDuration,les.title as LessonTitle,les.content as LessonContent"
                        + ",les.video_url as LessonVideoURL,ins.teacher_name as TeacherName "
                        + "from lessons les "
                        + "inner join courses co using(course_id)"
                        + "inner join instructors ins using(instructor_id)"
                        + "where course_id=?";

                statement = connection.prepareStatement(sql);
                statement.setString(1, courseId);

                resultSet = statement.executeQuery();

                StringBuilder jsonData = new StringBuilder();
                jsonData.append("{\"data\": [");

                boolean isFirstRow = false;
                while (resultSet.next()) {
                    if (!isFirstRow) {
                        jsonData.append(",");
                    }
                    String CourseTitle = resultSet.getString("CourseTitle");
                    String CourseDescription = resultSet.getString("CourseDescription");
                    String CourseDuration = resultSet.getString("CourseDuration");
                    String LessonTitle = resultSet.getString("LessonTitle");
                    String LessonContent = resultSet.getString("LessonContent");
                    String VideoUrl = resultSet.getString("LessonVideoURL");
                    String teacher_name = resultSet.getString("TeacherName");

                    jsonData.append("{\"co_title\":\"").append(CourseTitle).append("\",");
                    jsonData.append("\"CourseDes\":\"").append(CourseDescription).append("\",");
                    jsonData.append("\"CourseDuration\":\"").append(CourseDuration).append("\",");
                    jsonData.append("\"LessonTitle\":\"").append(LessonTitle).append("\",");
                    jsonData.append("\"LessonContent\":\"").append(LessonContent).append("\",");
                    jsonData.append("\"videoUrl\":\"").append(VideoUrl).append("\"}");
                    jsonData.append("\"teacher_name\":\"").append(teacher_name).append("\",");

                    isFirstRow = false;
                }

                jsonData.append("]}");

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                out.print(jsonData);
                System.out.println("data   "+jsonData);
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
