
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = {"/UpdatePass"})
public class UpdatePass extends HttpServlet {

    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            HttpSession session=request.getSession();

            // Set session attribute
            String user_id = (String) session.getAttribute("user_id");
            // getting password from the request
            String password = request.getParameter("newPass").trim();
            System.out.println("The pass 0"+password);
            // getting Confirm password from the request
            String confirmPassword = request.getParameter("confirmNewPass").trim();

            // Define regular expression for validation
            String passwordRegex = "^[a-zA-Z0-9@$!%*?&]+$";

            // Validate password
            boolean isValidPassword = Pattern.matches(passwordRegex, password);
            if (!isValidPassword) {
                response.getWriter().println("Invalid password. It should contain at least one uppercase letter, one lowercase letter, one digit, one special character, and no spaces.");
                return;
            }

            // Validate confirm password
            boolean isConfirmPasswordMatched = password.equals(confirmPassword);
            if (!isConfirmPasswordMatched) {
                response.getWriter().println("Passwords do not match.");
                return;
            }

            try {
                connection = GetConnect.getConnection();

                String sql = "update users set password =? where user_id =?";
                statement = connection.prepareStatement(sql);
                statement.setString(1, password);
                statement.setString(2, user_id);
                System.out.println("The updation in fine ");
                statement.executeUpdate();
                out.print("Changed");
            } catch (Exception e) {
                System.out.println("The Error of change pass is  " + e.getMessage());
            }

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
