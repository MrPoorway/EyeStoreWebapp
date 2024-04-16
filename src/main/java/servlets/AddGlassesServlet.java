package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.group2.constant.GlassesStoreConstants;
import com.group2.constant.db.GlassesDBConstants;
import com.group2.model.Glasses;
import com.group2.model.UserRole;
import com.group2.service.GlassesService;
import com.group2.service.impl.GlassesServiceImpl;
import com.group2.util.StoreUtil;

public class AddGlassesServlet extends HttpServlet {
    GlassesService glassesService = new GlassesServiceImpl();

    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        PrintWriter pw = res.getWriter();
        res.setContentType(GlassesStoreConstants.CONTENT_TYPE_TEXT_HTML);

        if (!StoreUtil.isLoggedIn(UserRole.ADMIN, req.getSession())) {
            RequestDispatcher rd = req.getRequestDispatcher("SellerLogin.html");
            rd.include(req, res);
            pw.println("<table class=\"tab\"><tr><td>Please Login First to Continue!!</td></tr></table>");
            return;
        }

        String bName = req.getParameter(GlassesDBConstants.COLUMN_NAME);
        RequestDispatcher rd = req.getRequestDispatcher("SellerHome.html");
        rd.include(req, res);
        StoreUtil.setActiveTab(pw, "addglasses");
        pw.println("<div class='container my-2'>");
        if(bName == null || bName.isBlank()) {
            //render the add glasses form;
            showAddGlassesForm(pw);
            return;
        } //else process the add glasses
        
 
        try {

        	int uniqueID = UUID.randomUUID().hashCode();
        	int gId = uniqueID;
            String gType = req.getParameter(GlassesDBConstants.COLUMN_TYPE);
            double gPrice = Integer.parseInt(req.getParameter(GlassesDBConstants.COLUMN_PRICE));
            int gQty = Integer.parseInt(req.getParameter(GlassesDBConstants.COLUMN_QUANTITY));
//            String imageUri = req.getParameter(GlassesDBConstants.COLUMN_IMAGE_URI);
            String imageUri = req.getParameter("imageUri");

            Glasses glasses = new Glasses(gId, bName, gType, gPrice, gQty, imageUri);
            String message = glassesService.addGlasses(glasses);
            if ("SUCCESS".equalsIgnoreCase(message)) {
                pw.println(
                        "<table class=\"tab\"><tr><td>Glasses Detail Updated Successfully!<br/>Add More Glassess</td></tr></table>");
            } else {
                pw.println("<table class=\"tab\"><tr><td>Failed to Add Glassess! Fill up CareFully</td></tr></table>");
                //rd.include(req, res);
            }
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<table class=\"tab\"><tr><td>Failed to Add Glassess! Fill up CareFully</td></tr></table>");
        }
    }
    
    private static void showAddGlassesForm(PrintWriter pw) {
        String form = "<table class=\"tab my-5\" style=\"width:40%;\">\r\n"
                + "        <tr>\r\n"
                + "            <td>\r\n"
                + "                <form action=\"addglasses\" method=\"post\">\r\n"
                + "                    <label for=\"glassesName\">Glasses Name : </label> <input type=\"text\" name=\"name\" id=\"glassesName\" placeholder=\"Enter Glasses name\" required><br/>\r\n"
                + "                    <label for=\"glassesType\">Glasses Type : </label><input type=\"text\" name=\"type\" id=\"glassesType\" placeholder=\"Enter Type Name\" required><br/>\r\n"
                + "                    <label for=\"glassesPrice\">Glasses Price : </label><input type=\"number\" name=\"price\" placeholder=\"Enter the Price\" required><br/>\r\n"
                + "                    <label for=\"glassesQuantity\">Glasses Quantity : </label><input type=\"number\" name=\"quantity\" id=\"glassesQuantity\" placeholder=\"Enter the quantity\" required><br/>\r\n"
                + "                    <label for=\"imageUri\">Image Uri : </label> <input type=\"text\" name=\"imageUri\" id=\"imageUri\" placeholder=\"Enter Image Uri\" required><br/>\r\n"
                + "                    <input class=\"btn btn-success my-2\" type=\"submit\" value=\" Add Glasses \">\r\n"
                + "                </form>\r\n"
                + "            </td>\r\n"
                + "        </tr>  \r\n"
                + "        <!-- <tr>\r\n"
                + "            <td><a href=\"index.html\">Go Back To Home Page</a></td>\r\n"
                + "        </tr> -->\r\n"
                + "    </table>";
        pw.println(form);
    }
}
