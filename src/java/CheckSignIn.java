
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = {"/CheckSignIn"})
public class CheckSignIn extends HttpServlet {

    Connection conn = null;
    PreparedStatement statement =null;
    ResultSet resultSet =null;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            System.out.println("The E_mail is   " + email);
            conn = GetConnect.getConnection();

            String sql = "SELECT user_id FROM USERS WHERE `password`= ? AND `e_mail` =?";

            statement = conn.prepareStatement(sql);
            statement.setString(1, password);
            statement.setString(2, email);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                System.out.println("There Fine checking for user_id from chechingJava");
                String user_id = resultSet.getString("user_id");
                System.out.println("The userId  =      " + user_id);
                // Create a session
                HttpSession session = request.getSession();
                // Set session attribute
                session.setAttribute("user_id", user_id);
                System.out.println("The Sesson is done");
//                response.sendRedirect("osamapage.html");
//                out.print("osamapage.html");
                out.print("success");

            } else {
                out.print("flase");
            }
            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(CheckSignIn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void validation(HttpServletResponse response, String email, String pass) {
        try (PrintWriter out = response.getWriter()) {
            // Define regular expression for validation
            String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

            // Define regular expression for validation
            String passwordRegex = "^[a-zA-Z0-9@$!%*?&]+$";

// Validate email
            boolean isValidEmail = Pattern.matches(emailRegex, email);
            if (!isValidEmail) {
                System.out.println("Validation done");
                out.print("Invalid email address.");
                return;
            }

            // Validate password
            boolean isValidPassword = Pattern.matches(passwordRegex, pass);
            if (!isValidPassword) {
                out.print("Invalid password. It should contain at least one uppercase letter, one lowercase letter, one digit, one special character, and no spaces.");
                return;
            }

        }catch(Exception e){
            System.out.println("The error  "+e.getMessage());
        }
    }

}
