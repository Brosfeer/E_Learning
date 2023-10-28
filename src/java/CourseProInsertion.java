
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

@WebServlet(urlPatterns = {"/CourseProInsertion"})
public class CourseProInsertion extends HttpServlet {

    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession();

            // get course_id from the request
            int courseId = Integer.parseInt(request.getParameter("courseId"));
            // get user_id attribute from the seesion 
            int user_id = Integer.parseInt((String) session.getAttribute("user_id"));

//            String courseId = "1";
            System.out.println("The Course Id   from CourseProInsertion  Servlet     " + courseId);
            System.out.println("The User Id   from CourseProInsertion  Servlet     " + user_id);
            // Set session attribute
            session.setAttribute("course_id", courseId);
            
            System.out.println("Session creaation fine");

//            String sql = "insert into course_progress(user_id,course_id) values(?,?)";
//            statement = connection.prepareStatement(sql);
//            statement.setInt(1, user_id);
//            statement.setInt(2, courseId);
//            statement.executeUpdate();
//            System.out.println("insertion fine             0_0");
            out.print("success");
        } catch (Exception e) {
            System.out.println("The Error  " + e.getMessage());
        }

    }

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

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
