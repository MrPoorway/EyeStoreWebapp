package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.group2.model.UserRole;
import com.group2.util.StoreUtil;
//Http Servlet extended class for showing the about information
public class AboutServlet extends HttpServlet {

    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        PrintWriter pw = res.getWriter();
        res.setContentType("text/html; charset=UTF-8");
        //If the store is logged in as customer or seller show about info
        if (StoreUtil.isLoggedIn(UserRole.CUSTOMER, req.getSession())) {
            RequestDispatcher rd = req.getRequestDispatcher("CustomerHome.html");
            rd.include(req, res);
            StoreUtil.setActiveTab(pw, "about");
            pw.println("<div class='about-iframe'>" +
                    "<h2>Group 2 - K15DCPM01</h2>" +
                    "<p>" +
                        "Le Hong Phuc - 2104110008 - 0859556099 - <a href='mailto:poorway1@gmail.com'>poorway1@gmail.com</a> - <a href='https://github.com/MrPoorway/' target='_blank'>GitHub</a><br>" +
                        "Nguyen Trung Hieu - 2104110011 - 0385699744 - <a href='mailto:hieunguyen080398@gmail.com'>hieunguyen080398@gmail.com</a> - <a href='https://github.com/TrunqHieuu' target='_blank'>GitHub</a><br>" +
                        "Nguyen Tan Minh Man - 2108110013<br>" +
                        "Le Tuan Kiet - 2104110030<br>" +
                        "Vo Van Toan - 2104110021" +
                    "</p>" +
                  "</div>");


        } else if (StoreUtil.isLoggedIn(UserRole.ADMIN, req.getSession())) {
            RequestDispatcher rd = req.getRequestDispatcher("SellerHome.html");
            rd.include(req, res);
            StoreUtil.setActiveTab(pw, "about");
            pw.println("<div class='about-iframe'>" +
                    "<h2>Group 2 - K15DCPM01</h2>" +
                    "<p>" +
                    	"Le Hong Phuc - 2104110008 - 0859556099 - <a href='mailto:poorway1@gmail.com'>poorway1@gmail.com</a> - <a href='https://github.com/MrPoorway/' target='_blank'>GitHub</a><br>" +
                    	"Nguyen Trung Hieu - 2104110011 - 0385699744 - <a href='mailto:hieunguyen080398@gmail.com'>hieunguyen080398@gmail.com</a> - <a href='https://github.com/TrunqHieuu' target='_blank'>GitHub</a><br>" +
                    	"Nguyen Tan Minh Man - 2108110013<br>" +
                    	"Le Tuan Kiet - 2104110030<br>" +
                    	"Vo Van Toan - 2104110021" +
                    "</p>" +
                  "</div>");

        } else {
            //If the user is not logged in, ask to login first
            //Proceed only if logged in or forword to login page
            RequestDispatcher rd = req.getRequestDispatcher("login.html");
            rd.include(req, res);
            pw.println("<table class=\"tab\"><tr><td>Please Login First to Continue!!</td></tr></table>");
        }

    }

}
