package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.group2.model.Glasses;
import com.group2.model.UserRole;
import com.group2.service.GlassesService;
import com.group2.service.impl.GlassesServiceImpl;
import com.group2.util.StoreUtil;

public class StoreGlassesServlet extends HttpServlet {

    // glasses service for database operations and logics
    GlassesService glassesService = new GlassesServiceImpl();

    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        PrintWriter pw = res.getWriter();
        res.setContentType("text/html");

        // Check if the customer is logged in, or else return to login page
        if (!StoreUtil.isLoggedIn(UserRole.ADMIN, req.getSession())) {
            RequestDispatcher rd = req.getRequestDispatcher("SellerLogin.html");
            rd.include(req, res);
            pw.println("<table class=\"tab\"><tr><td>Please Login First to Continue!!</td></tr></table>");
            return;
        }
        try {

            // Add/Remove Item from the cart if requested
            // store the comma separated glassesIds of cart in the session
            // StoreUtil.updateCartItems(req);

            RequestDispatcher rd = req.getRequestDispatcher("SellerHome.html");
            rd.include(req, res);
            pw.println("<div class='container'>");
            // Set the active tab as cart
            StoreUtil.setActiveTab(pw, "storeglassess");

            // Read the glassess from the database with the respective glassesIds
            List<Glasses> glassess = glassesService.getAllGlasses();
            pw.println("<div id='topmid' style='background-color:grey'>Glassess Available In the Store</div>");
            pw.println("<table class=\"table table-hover\" style='background-color:white'>\r\n"
                    + "  <thead>\r\n"
                    + "    <tr style='background-color:black; color:white;'>\r\n"
                    + "      <th scope=\"col\">Glasses Id</th>\r\n"
                    + "      <th scope=\"col\">Name</th>\r\n"
                    + "      <th scope=\"col\">Type</th>\r\n"
                    + "      <th scope=\"col\">Price</th>\r\n"
                    + "      <th scope=\"col\">Quantity</th>\r\n"
                    + "      <th scope=\"col\">Action</th>\r\n"
                    + "    </tr>\r\n"
                    + "  </thead>\r\n"
                    + "  <tbody>\r\n");
            if (glassess == null || glassess.size() == 0) {
                pw.println("    <tr style='background-color:green'>\r\n"
                        + "      <th scope=\"row\" colspan='6' style='color:yellow; text-align:center;'> No Glassess Available in the store </th>\r\n"
                        + "    </tr>\r\n");
            }
            for (Glasses glasses : glassess) {
                pw.println(getRowData(glasses));
            }

            pw.println("  </tbody>\r\n"
                    + "</table></div>");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getRowData(Glasses glasses) {
        return "    <tr>\r\n"
                + "      <th scope=\"row\">" + glasses.getId() + "</th>\r\n"
                + "      <td>" + glasses.getName() + "</td>\r\n"
                + "      <td>" + glasses.getType() + "</td>\r\n"
                + "      <td><span>" + glasses.getPrice() + " &#8363</span></td>\r\n"
                + "      <td>"
                + glasses.getQuantity()
                + "      </td>\r\n"
                + "      <td><form method='post' action='updateglasses'>"
                + "          <input type='hidden' name='glassesId' value='" + glasses.getId() + "'/>"
                + "          <button type='submit' class=\"btn btn-success\">Update</button>"
                + "          </form>"
                + "    </tr>\r\n";
    }

}
