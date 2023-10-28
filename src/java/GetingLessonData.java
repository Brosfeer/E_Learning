/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ThinkPad
 */
@WebServlet(urlPatterns = {"/GetingLessonData"})
public class GetingLessonData extends HttpServlet {

    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession();

            // get course_id
//            String courseId = request.getParameter("courseId");
            // get session attribute
            String user_id = (String) session.getAttribute("user_id");
            String courseId = String.valueOf(session.getAttribute("course_id"));

//            String courseId = "1";
            System.out.println("The Course Id   from GetingLessonData  Servlet     " + courseId);
            System.out.println("The User Id   from GetingLessonData  Servlet     " + user_id);

            try {
                connection = GetConnect.getConnection();

                ///insertion the courseId for the user in course progress
                if (!isInCourseProgress(courseId)) {
                    insertCourseIdInCoursePro(user_id, courseId);
                }
                
                

                String sql = "select co.title as CourseTitle,co.description as courseDescription\n"
                        + ",co.duration as CourseDuration,les.title as LessonTitle,les.content as LessonContent\n"
                        + ",les.video_url as LessonVideoURL,ins.teacher_name as TeacherName\n"
                        + "from lessons les inner join courses co using(course_id) \n"
                        + "inner join instructors ins using(instructor_id) where course_id=? ";
                int course_Id = Integer.parseInt(courseId);
                statement = connection.prepareStatement(sql);
//                statement.setInt(1, '1');
                statement.setInt(1, course_Id);
                

                resultSet = statement.executeQuery();
                // Create a list to store the data
                List<Lesson> lessonList = new ArrayList<>();
                while (resultSet.next()) {

                    String CourseTitle = resultSet.getString("CourseTitle");
                    String courseDescription = resultSet.getString("courseDescription");
                    String CourseDuration = resultSet.getString("courseDuration");
                    String LessonTitle = resultSet.getString("LessonTitle");
                    String LessonContent = resultSet.getString("LessonContent");
                    String VideoUrl = resultSet.getString("LessonVideoURL");
                    String TeacherName = resultSet.getString("TeacherName");

                    // Create a Lesson object
                    Lesson lesson = new Lesson(CourseTitle,courseDescription, CourseDuration, LessonTitle, LessonContent, VideoUrl, TeacherName);

                    // Add the Lesson object to the list
                    lessonList.add(lesson);
                }

                // Create Gson object
                Gson gson = new GsonBuilder().setPrettyPrinting().create();

                // Convert the list to JSON
                String json = gson.toJson(lessonList);

                // Write the JSON to a file
                out.print(json);
                System.out.println("Data successfully   " + json);

                connection.close();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    void insertCourseIdInCoursePro(String user_id, String course_id) {
        try {
            int courseId = Integer.parseInt(course_id);
            int userId = Integer.parseInt(user_id);
            String sql = "insert into course_progress(user_id,course_id) values(?,?)";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setInt(2, courseId);
            statement.executeUpdate();
            System.out.println("insertion fine             0_0");
        } catch (Exception e) {
            System.out.println("The Error in the insertion method of CourseProgress" + e.getMessage());
        }
    }

    public boolean isInCourseProgress(String course_id) {
        try {
            String getUserId = "select user_id from course_progress where course_id=?";
            statement = connection.prepareStatement(getUserId);
            statement.setString(1, course_id);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String user_id = resultSet.getString("user_id");
                if (!user_id.isEmpty()) {
                    System.out.println("The Course in the course progress");
                    return true;
                }
            }

        } catch (Exception e) {
            System.out.println("The Error for checking if user have course in progress " + e.getMessage());
        }
        return false;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
// Define a Lesson class to represent the data structure

class Lesson {

    private String courseTitle;
    private String courseDescription;
    private String courseDuration;
    private String lessonTitle;
    private String lessonContent;
    private String videoUrl;
    private String teacherName;

    public Lesson(String courseTitle,String courseDescription, String courseDuration, String lessonTitle, String lessonContent, String videoUrl, String teacherName) {
        this.courseTitle = courseTitle;
        this.courseDescription = courseDescription;
        this.courseDuration = courseDuration;
        this.lessonTitle = lessonTitle;
        this.lessonContent = lessonContent;
        this.videoUrl = "https://youtu.be/ItZN6o0ylao?si=FYwcFrYFvt4MGmN7";
        this.teacherName = teacherName;
    }
}
