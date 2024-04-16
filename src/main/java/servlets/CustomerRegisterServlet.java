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
import com.group2.constant.db.UsersDBConstants;
import com.group2.model.User;
import com.group2.model.UserRole;
import com.group2.service.UserService;
import com.group2.service.impl.UserServiceImpl;

public class CustomerRegisterServlet extends HttpServlet {

    UserService userService = new UserServiceImpl();

    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        PrintWriter pw = res.getWriter();
        res.setContentType(GlassesStoreConstants.CONTENT_TYPE_TEXT_HTML);

        String uName = req.getParameter(UsersDBConstants.COLUMN_USERNAME);
        String pWord = req.getParameter(UsersDBConstants.COLUMN_PASSWORD);
        String fName = req.getParameter(UsersDBConstants.COLUMN_FULLNAME);
        String addr = req.getParameter(UsersDBConstants.COLUMN_ADDRESS);
        String phNo = req.getParameter(UsersDBConstants.COLUMN_PHONE);
        String mail = req.getParameter(UsersDBConstants.COLUMN_MAIL);
        User user = new User();
        user.setUsername(uName);
        user.setEmail(mail);
        user.setFullName(fName);
        user.setPassword(pWord);
        user.setPhone(Long.parseLong(phNo));
        user.setAddress(addr);
        try {
            String respCode = userService.register(UserRole.CUSTOMER, user);
            System.out.println(respCode);
            if (ResponseCode.SUCCESS.name().equalsIgnoreCase(respCode)) {
                RequestDispatcher rd = req.getRequestDispatcher("CustomerLogin.html");
                rd.include(req, res);
                pw.println("<table class=\"tab\"><tr><td>User Registered Successfully</td></tr></table>");
            } else {
                RequestDispatcher rd = req.getRequestDispatcher("CustomerRegister.html");
                rd.include(req, res);
                pw.println("<table class=\"tab\"><tr><td>" + respCode + "</td></tr></table>");
                pw.println("Sorry for interruption! Try again");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}