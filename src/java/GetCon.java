
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = {"/GetCon"})
public class GetCon extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = null;
        try (PrintWriter out = response.getWriter()) {
            String name = request.getParameter("name").trim();
            String email = request.getParameter("email").trim();
            int age = Integer.parseInt(request.getParameter("age"));
            String address = request.getParameter("address").trim();
            String phone_no = request.getParameter("phoneNo").trim();
            String password = request.getParameter("password").trim();
            String confirmPassword = request.getParameter("confirmPassword").trim();
            //ensure for receiving data
//            checkInput(response, name, email, password, confirmPassword, address, phone_no, age);
            String restrictedCharsAndNumbersRegex = "^[^\"*\\s0-9]*$"; // Matches any character except double quote, asterisk, space, or digit

            // Define regular expression for validation
            String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

            // Matches exactly 10 digits (adjust as per your phone number format)
            String phoneNumberRegex = "^[0-9]{10}$";

            // Define regular expression for validation
            String passwordRegex = "^[a-zA-Z0-9@$!%*?&]+$";

            // Validate name
            boolean containsRestrictedCharsOrNumbersName = !Pattern.matches(restrictedCharsAndNumbersRegex, name);
            if (containsRestrictedCharsOrNumbersName) {
                out.println("Invalid name. remove other character");
                return;
            }

            // Validate email
            boolean isValidEmail = Pattern.matches(emailRegex, email);
            if (!isValidEmail) {
                out.print("Invalid email address.");
                return;
            }

            // Validate password
            boolean isValidPassword = Pattern.matches(passwordRegex, password);
            if (!isValidPassword) {
                out.print("Invalid password. It should contain at least one letter, one digit, one special character, and no spaces.");
                return;
            }

            // Validate confirm password
            boolean isConfirmPasswordMatched = password.equals(confirmPassword);
            if (!isConfirmPasswordMatched) {
                out.println("Passwords do not match.");
                return;
            }
            // Validate address
            boolean containsRestrictedCharsOrNumbersAddress = !Pattern.matches(restrictedCharsAndNumbersRegex, address);
            if (containsRestrictedCharsOrNumbersAddress) {
                out.print("Invalid address. It should not contain number or other character.");
                return;
            }

            // Validate phone number
            boolean containsphoneNumberRegex = !Pattern.matches(phoneNumberRegex, phone_no);
            if (containsphoneNumberRegex) {
                out.print("Invalid phone number. It should contain only number");
                return;
            }
            //end validation of input
            try {

                // making connection to mysql DB
                conn = GetConnect.getConnection();

                System.out.println("Befor inserting is fine");
                // Begin the transaction
                conn.setAutoCommit(false);

                // Create a statement object
                String email_check = "select password from USERS where e_mail=?;";
                PreparedStatement check_fo_email = conn.prepareStatement(email_check);
                check_fo_email.setString(1, email);
                ResultSet u_email = check_fo_email.executeQuery();
                if (u_email.next()) {
                    String User_Email = u_email.getString("password");
                    System.out.println("Check for Email fine   " + User_Email);
                    PrintWriter outer = response.getWriter();
                    outer.print("Exit");
                    System.out.println("The request and query is fine");
                    return;

                } else {
                    // Insert data into 'users' table
                    String sql_user = "INSERT INTO USERS(User_Name,Password,E_mail) VALUES(?,?,?);";
                    // Create a statement object
                    PreparedStatement userStatement = conn.prepareStatement(sql_user, PreparedStatement.RETURN_GENERATED_KEYS);
                    userStatement.setString(1, name);
                    userStatement.setString(2, password);
                    userStatement.setString(3, email);
                    userStatement.executeUpdate();

                    // Retrieve the generated user_id value
                    int userId;
                    try (ResultSet generatedKeys = userStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            userId = generatedKeys.getInt(1);
                            // Create a session
                            HttpSession session = request.getSession();
                            session.setAttribute("user_is", userId);
//                            buildSession(request, email);
                        } else {
                            throw new SQLException("Failed to retrieve the generated user_id.");
                        }
                    } catch (SQLException e) {
                        // Rollback the transaction in case of any error
                        if (conn != null) {
                            conn.rollback();
                        }
                        throw e;
                    }
//                    out.print("First table fine");

                    // Insert data into 'students' table
                    String sql_students = "insert into students(user_id,age,address,phone_no) values(?,?,?,?)";
                    PreparedStatement studentStatement = conn.prepareStatement(sql_students);
                    studentStatement.setInt(1, userId);
                    studentStatement.setInt(2, age);
                    studentStatement.setString(3, address);
                    studentStatement.setString(4, phone_no);
                    studentStatement.executeUpdate();
                    out.print("new");
                    conn.commit();
                    System.out.println("After insertion fine");
//                    out.print("insertion successfuly");
//                    response.sendRedirect("osamapage.html");

                }
            } catch (Exception e) {
                // Rollback the transaction in case of any error
                try {
                    if (conn != null) {
                        conn.rollback();
                    }
                } catch (SQLException rollbackException) {
                    rollbackException.printStackTrace();
                }
                e.printStackTrace();
            } finally {
                // Close the database connection and resources
                try {
                    if (conn != null) {
                        conn.setAutoCommit(true);
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void checkInput(HttpServletResponse response, String name, String email, String pass, String passCon, String address, String phone, int age) {
        try (PrintWriter out = response.getWriter()) {
            // Define regular expression for validation
            String restrictedCharsAndNumbersRegex = "^[^\"*\\s0-9]*$"; // Matches any character except double quote, asterisk, space, or digit

            // Define regular expression for validation
            String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

            // Matches exactly 10 digits (adjust as per your phone number format)
            String phoneNumberRegex = "^[0-9]{10}$";

            // Define regular expression for validation
            String passwordRegex = "^[a-zA-Z0-9@$!%*?&]+$";

            // Validate name
            boolean containsRestrictedCharsOrNumbersName = !Pattern.matches(restrictedCharsAndNumbersRegex, name);
            if (containsRestrictedCharsOrNumbersName) {
                out.println("Invalid name. remove other character");
                return;
            }

            // Validate email
            boolean isValidEmail = Pattern.matches(emailRegex, email);
            if (!isValidEmail) {
                out.print("Invalid email address.");
                return;
            }

            // Validate password
            boolean isValidPassword = Pattern.matches(passwordRegex, pass);
            if (!isValidPassword) {
                out.print("Invalid password. It should contain at least one letter, one digit, one special character, and no spaces.");
                return;
            }

            // Validate confirm password
            boolean isConfirmPasswordMatched = pass.equals(passCon);
            if (!isConfirmPasswordMatched) {
                out.println("Passwords do not match.");
                return;
            }
            // Validate address
            boolean containsRestrictedCharsOrNumbersAddress = !Pattern.matches(restrictedCharsAndNumbersRegex, address);
            if (containsRestrictedCharsOrNumbersAddress) {
                out.print("Invalid address. It should not contain number or other character.");
                return;
            }

            // Validate phone number
            boolean containsphoneNumberRegex = !Pattern.matches(phoneNumberRegex, phone);
            if (containsphoneNumberRegex) {
                out.print("Invalid phone number. It should contain only number");
                return;
            }
        } catch (Exception e) {
            System.out.println("The error  " + e.getMessage());
        }
    }

    boolean isExist() {
        return true;
    }

    void buildSession(HttpServletRequest request, String user_id) {
        HttpSession session = request.getSession();
        session.setAttribute("user_is", user_id);
    }
}
