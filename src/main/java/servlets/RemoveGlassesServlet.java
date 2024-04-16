package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.group2.constant.ResponseCode;
import com.group2.model.UserRole;
import com.group2.service.GlassesService;
import com.group2.service.impl.GlassesServiceImpl;
import com.group2.util.StoreUtil;

public class RemoveGlassesServlet extends HttpServlet {

    GlassesService glassesService = new GlassesServiceImpl();

    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        PrintWriter pw = res.getWriter();
        res.setContentType("text/html");
        if (!StoreUtil.isLoggedIn(UserRole.ADMIN, req.getSession())) {
            RequestDispatcher rd = req.getRequestDispatcher("SellerLogin.html");
            rd.include(req, res);
            pw.println("<table class=\"tab\"><tr><td>Please Login First to Continue!!</td></tr></table>");
            return;
        }

        try {
            String glassesId = req.getParameter("glassesId");
            RequestDispatcher rd = req.getRequestDispatcher("SellerHome.html");
            rd.include(req, res);
            StoreUtil.setActiveTab(pw, "removeglasses");
            pw.println("<div class='container'>");
            if (glassesId == null || glassesId.isBlank()) {
                // render the remove glasses form;
                showRemoveGlassesForm(pw);
                return;
            } // else continue

            String responseCode = glassesService.deleteGlassesById(glassesId.trim());
            if (ResponseCode.SUCCESS.name().equalsIgnoreCase(responseCode)) {
                pw.println("<table class=\"tab my-5\"><tr><td>Glasses Removed Successfully</td></tr></table>");
                pw.println(
                        "<table class=\"tab\"><tr><td><a href=\"removeglasses\">Remove more Glassess</a></td></tr></table>");

            } else {
                pw.println("<table class=\"tab my-5\"><tr><td>Glasses Not Available In The Store</td></tr></table>");
                pw.println(
                        "<table class=\"tab\"><tr><td><a href=\"removeGlasses\">Remove more Glassess</a></td></tr></table>");
            }
            pw.println("</div>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<table class=\"tab\"><tr><td>Failed to Remove Glassess! Try Again</td></tr></table>");
        }
    }

    private static void showRemoveGlassesForm(PrintWriter pw) {
        String form = "<form action=\"removeglasses\" method=\"post\" class='my-5'>\r\n"
                + "        <table class=\"tab\">\r\n"
                + "        <tr>\r\n"
                + "            <td>\r\n"
                + "                <label for=\"glassesCode\">Enter GlassesId to Remove </label>\r\n"
                + "                <input type=\"text\" name=\"glassesId\" placeholder=\"Enter Glasses Id\" id=\"glassesCode\" required>\r\n"
                + "                <input class=\"btn btn-danger my-2\" type=\"submit\" value=\"Remove Glasses\">\r\n"
                + "            </td>\r\n"
                + "        </tr>\r\n"
                + "\r\n"
                + "        </table>\r\n"
                + "    </form>";
        pw.println(form);
    }

}
