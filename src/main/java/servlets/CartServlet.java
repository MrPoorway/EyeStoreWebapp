package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.group2.constant.GlassesStoreConstants;
import com.group2.model.Cart;
import com.group2.model.Glasses;
import com.group2.model.UserRole;
import com.group2.service.GlassesService;
import com.group2.service.impl.GlassesServiceImpl;
import com.group2.util.StoreUtil;

public class CartServlet extends HttpServlet {

    GlassesService glassesService = new GlassesServiceImpl();

    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        PrintWriter pw = res.getWriter();
        res.setContentType(GlassesStoreConstants.CONTENT_TYPE_TEXT_HTML);

        // Check if Customer is logged In
        if (!StoreUtil.isLoggedIn(UserRole.CUSTOMER, req.getSession())) {
            RequestDispatcher rd = req.getRequestDispatcher("CustomerLogin.html");
            rd.include(req, res);
            pw.println("<table class=\"tab\"><tr><td>Please Login First to Continue!!</td></tr></table>");
            return;
        }
        try {
            // Add/Remove Item from the cart if requested
            // store the comma separated glassesIds of cart in the session
            StoreUtil.updateCartItems(req);

            HttpSession session = req.getSession();
            String glassesIds = "";
            if (session.getAttribute("items") != null)
                glassesIds = (String) session.getAttribute("items");// read comma separated glassesIds from session

            RequestDispatcher rd = req.getRequestDispatcher("CustomerHome.html");
            rd.include(req, res);

            // Set the active tab as cart
            StoreUtil.setActiveTab(pw, "cart");

            // Read the glassess from the database with the respective glassesIds
            List<Glasses> glassess = glassesService.getGlassessByCommaSeperatedGlassesIds(glassesIds);
            List<Cart> cartItems = new ArrayList<Cart>();
            pw.println("<div id='topmid' style='background-color:grey'>Shopping Cart</div>");
            pw.println("<table class=\"table table-hover\" style='background-color:white'>\r\n"
                    + "  <thead>\r\n"
                    + "    <tr style='background-color:black; color:white;'>\r\n"
                    + "      <th scope=\"col\">GlassesId</th>\r\n"
                    + "      <th scope=\"col\">Name</th>\r\n"
                    + "      <th scope=\"col\">Type</th>\r\n"
                    + "      <th scope=\"col\">Price/Item</th>\r\n"
                    + "      <th scope=\"col\">Quantity</th>\r\n"
                    + "      <th scope=\"col\">Amount</th>\r\n"
                    + "    </tr>\r\n"
                    + "  </thead>\r\n"
                    + "  <tbody>\r\n");
            double amountToPay = 0;
            if (glassess == null || glassess.size() == 0) {
                pw.println("    <tr style='background-color:green'>\r\n"
                        + "      <th scope=\"row\" colspan='6' style='color:yellow; text-align:center;'> No Items In the Cart </th>\r\n"
                        + "    </tr>\r\n");
            }
            for (Glasses glasses : glassess) {
                int qty = (int) session.getAttribute("qty_" + glasses.getId());
                Cart cart = new Cart(glasses, qty);
                cartItems.add(cart);
                amountToPay += (qty * glasses.getPrice());
                pw.println(getRowData(cart));
            }

            // set cartItems and amountToPay in the session
            session.setAttribute("cartItems", cartItems);
            session.setAttribute("amountToPay", amountToPay);

            if (amountToPay > 0) {
                pw.println("    <tr style='background-color:green'>\r\n"
                		+ "      <th scope=\"row\" colspan='5' style='color:yellow; text-align:center;'> Total Amount To Pay </th>\r\n"
                        + "      <td colspan='1' style='color:white; font-weight:bold'><span>"
                		+ amountToPay + " &#8363</span></td>\r\n"
                        + "    </tr>\r\n");
            }
            pw.println("  </tbody>\r\n"
                    + "</table>");
            if (amountToPay > 0) {
                pw.println("<div style='text-align:right; margin-right:20px;'>\r\n"
                        + "<form action=\"checkout\" method=\"post\">"
						+ "<input type='submit' class=\"btn btn-primary\" name='pay' value='Proceed to Pay "
						+ amountToPay + "  &#8363'/></form>"
						+ "    </div>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getRowData(Cart cart) {
        Glasses glasses = cart.getGlasses();
        return "    <tr>\r\n"
                + "      <th scope=\"row\">" + glasses.getId() + "</th>\r\n"
                + "      <td>" + glasses.getName() + "</td>\r\n"
                + "      <td>" + glasses.getType() + "</td>\r\n"
				+ "      <td><span>" + glasses.getPrice() + " &#8363</span></td>\r\n"
                + "      <td><form method='post' action='cart'><button type='submit' name='removeFromCart' class=\"glyphicon glyphicon-minus btn btn-danger\"></button> "
                + "<input type='hidden' name='selectedGlassesId' value='" + glasses.getId() + "'/>"
                + cart.getQuantity()
                + " <button type='submit' name='addToCart' class=\"glyphicon glyphicon-plus btn btn-success\"></button></form></td>\r\n"
                + "      <td><span>" + (glasses.getPrice() * cart.getQuantity()) + " &#8363</span></td>\r\n"
                + "    </tr>\r\n";
    }

}
