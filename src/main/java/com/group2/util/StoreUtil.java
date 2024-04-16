package com.group2.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.group2.model.UserRole;

/*
 * Store UTil File To Store Commonly used methods
 */
public class StoreUtil {

    /**
     * Check if the User is logged in with the requested role
     */
    public static boolean isLoggedIn(UserRole role, HttpSession session) {

        return session.getAttribute(role.toString()) != null;
    }

    /**
     * Modify the active tab in the page menu bar
     */
    public static void setActiveTab(PrintWriter pw, String activeTab) {

        pw.println("<script>document.getElementById(activeTab).classList.remove(\"active\");activeTab=" + activeTab
                + "</script>");
        pw.println("<script>document.getElementById('" + activeTab + "').classList.add(\"active\");</script>");

    }

    /**
     * Add/Remove/Update Item in the cart using the session
     */
    public static void updateCartItems(HttpServletRequest req) {
        String selectedGlassesId = req.getParameter("selectedGlassesId");
        HttpSession session = req.getSession();
        if (selectedGlassesId != null) { // add item to the cart

            // Items will contain comma separated glassesIds that needs to be added in the cart
            String items = (String) session.getAttribute("items");
            if (req.getParameter("addToCart") != null) { // add to cart
                if (items == null || items.length() == 0)
                    items = selectedGlassesId;
                else if (!items.contains(selectedGlassesId))
                    items = items + "," + selectedGlassesId; // if items already contains glassesId, don't add it

                // set the items in the session to be used later
                session.setAttribute("items", items);

                /*
                 * Quantity of each item in the cart will be stored in the session as:
                 * Prefixed with qty_ following its glassesId
                 * For example 2 no. of glasses with id 'myGlasses' in the cart will be
                 * added to the session as qty_myGlasses=2
                 */
                int itemQty = 0;
                if (session.getAttribute("qty_" + selectedGlassesId) != null)
                    itemQty = (int) session.getAttribute("qty_" + selectedGlassesId);
                itemQty += 1;
                session.setAttribute("qty_" + selectedGlassesId, itemQty);
            } else { // remove from the cart
                int itemQty = 0;
                if (session.getAttribute("qty_" + selectedGlassesId) != null)
                    itemQty = (int) session.getAttribute("qty_" + selectedGlassesId);
                if (itemQty > 1) {
                    itemQty--;
                    session.setAttribute("qty_" + selectedGlassesId, itemQty);
                } else {
                    session.removeAttribute("qty_" + selectedGlassesId);
                    items = items.replace(selectedGlassesId + ",", "");
                    items = items.replace("," + selectedGlassesId, "");
                    items = items.replace(selectedGlassesId, "");
                    session.setAttribute("items", items);
                }
            }
        }

    }
}
