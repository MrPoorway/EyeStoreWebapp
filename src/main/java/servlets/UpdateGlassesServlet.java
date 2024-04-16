package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.group2.constant.GlassesStoreConstants;
import com.group2.constant.ResponseCode;
import com.group2.constant.db.GlassesDBConstants;
import com.group2.model.Glasses;
import com.group2.model.UserRole;
import com.group2.service.GlassesService;
import com.group2.service.impl.GlassesServiceImpl;
import com.group2.util.StoreUtil;

public class UpdateGlassesServlet extends HttpServlet {
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

        RequestDispatcher rd = req.getRequestDispatcher("SellerHome.html");
        rd.include(req, res);
        StoreUtil.setActiveTab(pw, "storeglassess");
        pw.println("<div class='container my-2'>");

        try {
            if (req.getParameter("updateFormSubmitted") != null) {
                String gName = req.getParameter(GlassesDBConstants.COLUMN_NAME);
                int gId = Integer.parseInt(req.getParameter(GlassesDBConstants.COLUMN_id));
                String gType = req.getParameter(GlassesDBConstants.COLUMN_TYPE);
                double gPrice = Double.parseDouble(req.getParameter(GlassesDBConstants.COLUMN_PRICE));
                int gQty = Integer.parseInt(req.getParameter(GlassesDBConstants.COLUMN_QUANTITY));
//                String imageUri = req.getParameter(GlassesDBConstants.COLUMN_IMAGE_URI);
                String imageUri = req.getParameter("imageUri");


                Glasses glasses = new Glasses(gId, gName, gType, gPrice, gQty,imageUri);
                String message = glassesService.updateGlasses(glasses);
                if (ResponseCode.SUCCESS.name().equalsIgnoreCase(message)) {
                    pw.println(
                            "<table class=\"tab\"><tr><td>Glasses Detail Updated Successfully!</td></tr></table>");
                } else {
                    pw.println("<table class=\"tab\"><tr><td>Failed to Update Glasses!!</td></tr></table>");
                }

                return;
            }

            String glassesId = req.getParameter("glassesId");

            if (glassesId != null) {
                Glasses glasses = glassesService.getGlassesById(glassesId);
                showUpdateGlassesForm(pw, glasses);
            }

        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<table class=\"tab\"><tr><td>Failed to Load Glasses data!!</td></tr></table>");
        }
    }

    private static void showUpdateGlassesForm(PrintWriter pw, Glasses glasses) {
        String form = "<table class=\"tab my-5\" style=\"width:40%;\">\r\n"
                + "        <tr>\r\n"
                + "            <td>\r\n"
                + "                <form action=\"updateglasses\" method=\"post\">\r\n"
                + "                    <label for=\"glassesId\">Glasses Id : </label><input type=\"text\" name=\"id\" id=\"glassesId\" placeholder=\"Enter Glasses Id\" value='"
                + glasses.getId() + "' readonly><br/>"
                + "                    <label for=\"glassesName\">Glasses Name : </label> <input type=\"text\" name=\"name\" id=\"glassesName\" placeholder=\"Enter Glasses's name\" value='"
                + glasses.getName() + "' required><br/>\r\n"
                + "                    <label for=\"glassesType\">Glasses Type : </label><input type=\"text\" name=\"type\" id=\"glassesType\" placeholder=\"Enter Type Name\" value='"
                + glasses.getType() + "' required><br/>\r\n"
                + "                    <label for=\"glassesPrice\">Glasses Price : </label><input type=\"number\" name=\"price\" placeholder=\"Enter the Price\" value='"
                + glasses.getPrice() + "' required><br/>\r\n"
                + "                    <label for=\"glassesQuantity\">Glasses Quantity : </label><input type=\"number\" name=\"quantity\" id=\"glassesQuantity\" placeholder=\"Enter the quantity\" value='"
                + glasses.getQuantity() + "' required><br/>\r\n"
                + "                    <label for=\"imageUri\">Image Uri : </label> <input type=\"text\" name=\"imageUri\" id=\"imageUri\" placeholder=\"Enter Image Uri\" value='"
                + glasses.getImageUri() + "' required><br/>\r\n"
                + "                    <input class=\"btn btn-success my-2\" type=\"submit\" name='updateFormSubmitted' value=\" Update Glasses \">\r\n"
                + "                </form>\r\n"
                + "            </td>\r\n"
                + "        </tr>  \r\n"
                + "    </table>";
        pw.println(form);
    }
}
