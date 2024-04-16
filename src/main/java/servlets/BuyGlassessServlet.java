package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.group2.constant.GlassesStoreConstants;
import com.group2.model.Glasses;
import com.group2.model.UserRole;
import com.group2.service.GlassesService;
import com.group2.service.impl.GlassesServiceImpl;
import com.group2.util.StoreUtil;

public class BuyGlassessServlet extends HttpServlet {
    GlassesService glassesService = new GlassesServiceImpl();

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        PrintWriter pw = res.getWriter();
        res.setContentType(GlassesStoreConstants.CONTENT_TYPE_TEXT_HTML);
        if (!StoreUtil.isLoggedIn(UserRole.CUSTOMER, req.getSession())) {
            RequestDispatcher rd = req.getRequestDispatcher("CustomerLogin.html");
            rd.include(req, res);
            pw.println("<table class=\"tab\"><tr><td>Please Login First to Continue!!</td></tr></table>");
            return;
        }
        try {
            List<Glasses> glassess = glassesService.getAllGlasses();
            RequestDispatcher rd = req.getRequestDispatcher("CustomerHome.html");
            rd.include(req, res);
            StoreUtil.setActiveTab(pw, "cart");
            pw.println("<div class=\"tab hd brown \">Glassess Available In Our Store</div>");
            pw.println("<div class=\"tab\"><form action=\"buys\" method=\"post\">");
            pw.println("<table>\r\n" +
                    "			<tr>\r\n" +
                    "				<th>Glassess</th>\r\n" +
                    "				<th>Code</th>\r\n" +
                    "				<th>Name</th>\r\n" +
                    "				<th>Type</th>\r\n" +
                    "				<th>Price</th>\r\n" +
                    "				<th>Avail</th>\r\n" +
                    "				<th>Qty</th>\r\n" +
                    "			</tr>");
            int i = 0;
            for (Glasses glasses : glassess) {
                int gId = glasses.getId();
                String gName = glasses.getName();
                String gType = glasses.getType();
                double gPrice = glasses.getPrice();
                int gAvl = glasses.getQuantity();
                i = i + 1;
                String n = "checked" + Integer.toString(i);
                String q = "qty" + Integer.toString(i);
                pw.println("<tr>\r\n" +
                        "				<td>\r\n" +
                        "					<input type=\"checkbox\" name=" + n + " value=\"pay\">\r\n" + // Value is
                                                                                                          // made equal
                                                                                                          // to gId
                        "				</td>");
                pw.println("<td>" + gId + "</td>");
                pw.println("<td>" + gName + "</td>");
                pw.println("<td>" + gType + "</td>");
                pw.println("<td>" + gPrice + "</td>");
                pw.println("<td>" + gAvl + "</td>");
                pw.println("<td><input type=\"text\" name=" + q + " value=\"0\" text-align=\"center\"></td></tr>");

            }
            pw.println("</table>\r\n" + "<input type=\"submit\" value=\" PAY NOW \">" + "<br/>" +
                    "	</form>\r\n" +
                    "	</div>");
            // pw.println("<div class=\"tab\"><a href=\"AddGlasses.html\">Add More
            // Glassess</a></div>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
