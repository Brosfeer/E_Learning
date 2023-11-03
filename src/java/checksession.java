import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(urlPatterns = {"/checksession"})
public class checksession extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
//            HttpSession session = request.getSession();
//                 
//            String userid = (String) session.getAttribute("user_id");
//            
//            System.out.println(userid);
//            
//             if (session != null) {
//            session.invalidate(); // Delete the session
//            response.setStatus(HttpServletResponse.SC_OK); // Set the response status to 200 (OK)
//        }
//             System.out.println(userid);
//        }
//        
//            

            HttpSession session = request.getSession(false); // Retrieve the existing session, if available
//            String userId = (String) session.getAttribute("user_id");
            System.out.println("User ID before session deletion: ");

            if (session != null) {
                session.invalidate(); // Delete the session
                System.out.println("Session deleted successfully");
                System.out.println("User ID after session deletion: " );
                
            }
            HttpSession ses=request.getSession();
            String user_id=(String) ses.getAttribute("user_id");
            System.out.println("THe user id after deletion seccuss " +user_id);
            out.print("oky");
//            HttpSession session = request.getSession(); // Retrieve the existing session, if available
//            String userid = (String) session.getAttribute("user_id");
//            System.out.print("the user id  before deletion " + userid);
//            if (session != null) {
//                session.invalidate(); // Delete the session
//                System.out.print("the user id   " + userid);
//            }

            // Redirect the user to a new page (e.g., index.html)
//        response.sendRedirect("index.html");
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
