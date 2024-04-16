package com.group2.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.group2.constant.ResponseCode;
import com.group2.constant.db.GlassesDBConstants;
import com.group2.constant.db.UsersDBConstants;
import com.group2.model.Glasses;
import com.group2.model.StoreException;
import com.group2.model.User;
import com.group2.model.UserRole;
import com.group2.service.UserService;
import com.group2.util.DBUtil;

public class UserServiceImpl implements UserService {

    private static final String getAllUserQuery = "SELECT * FROM " + UsersDBConstants.TABLE_USERS;

    private static final String registerUserQuery = "INSERT INTO " + UsersDBConstants.TABLE_USERS
            + "  VALUES(?,?,?,?,?,?,?)";

    private static final String loginUserQuery = "SELECT * FROM " + UsersDBConstants.TABLE_USERS + " WHERE "
            + UsersDBConstants.COLUMN_USERNAME + "=? AND " + UsersDBConstants.COLUMN_PASSWORD + "=? AND "
            + UsersDBConstants.COLUMN_USERTYPE + "=?";

    @Override
    public User login(UserRole role, String email, String password, HttpSession session) throws StoreException {
        Connection con = DBUtil.getConnection();
        PreparedStatement ps;
        User user = null;
        try {
            String userType = UserRole.ADMIN.equals(role) ? "1" : "2";
            ps = con.prepareStatement(loginUserQuery);
            ps.setString(1, email);
            ps.setString(2, password);
            ps.setString(3, userType);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setFullName(rs.getString("fullname"));
                user.setPhone(rs.getLong("phone"));
                user.setEmail(email);
                user.setPassword(password);
                session.setAttribute(role.toString(), user.getEmail());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean isLoggedIn(UserRole role, HttpSession session) {
        if (role == null)
            role = UserRole.CUSTOMER;
        return session.getAttribute(role.toString()) != null;
    }

    @Override
    public boolean logout(HttpSession session) {
        session.removeAttribute(UserRole.CUSTOMER.toString());
        session.removeAttribute(UserRole.ADMIN.toString());
        session.invalidate();
        return true;
    }

    @Override
    public String register(UserRole role, User user) throws StoreException {
        String responseMessage = ResponseCode.FAILURE.name();
        Connection con = DBUtil.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(registerUserQuery);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFullName());
            ps.setString(4, user.getAddress());
            ps.setLong(5, user.getPhone());
            ps.setString(6, user.getEmail());
            int userType = UserRole.ADMIN.equals(role) ? 1 : 2;
            ps.setInt(7, userType);
            int k = ps.executeUpdate();
            if (k == 1) {
                responseMessage = ResponseCode.SUCCESS.name();
                ;
            }
        } catch (Exception e) {
            responseMessage += " : " + e.getMessage();
            if (responseMessage.contains("Duplicate"))
                responseMessage = "User already registered with this email !!";
            e.printStackTrace();
        }
        return responseMessage;
    }
    
    @Override
    public List<User> getAllUser() throws StoreException {
        List<User> users = new ArrayList<User>();
        Connection con = DBUtil.getConnection();

        try {
            PreparedStatement ps = con.prepareStatement(getAllUserQuery);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
            	String username = rs.getString(1);
                String email = rs.getString(2);
                String password = rs.getString(3);
                String fullName = rs.getString(4);
                Long phone = rs.getLong(5);
                String address = rs.getString(6);
                List<UserRole> roles = new ArrayList<UserRole>();

                if (rs.getInt(7) == 1) {
                    roles.add(UserRole.ADMIN);
                } else {
                    roles.add(UserRole.CUSTOMER);
                }
                
                User user = new User(username, email, password, fullName, phone, address, roles);
                users.add(user);
            }
        } catch (SQLException e) {

        }
        return users;
    }
    
//    private boolean isAuthorization(String auth) {
//    	String decodeString = "";
//    	String[] authParts = auth.split("\\s+");
//    	String authInfo = authParts[1];
//    	byte[] bytes = null;
//    	
//    	bytes = Base64.getDecoder().decode(authInfo);
//    	
//    	decodeString = new String(bytes);
//    	System.out.println(decodeString);
//    	
//    	String[] details = decodeString.split(":");
//    	String username = details[0];
//    	String password = details[1];
//    	
//    	return UserServiceImpl.authorization(username, password);
//    	 
//    }
    
//    public boolean authorization(String username, String password) {
//    	Connection con = null;
//		try {
//			con = DBUtil.getConnection();
//		} catch (StoreException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        PreparedStatement ps;
//        try {
//            ps = con.prepareStatement("SELECT * FROM " + UsersDBConstants.TABLE_USERS + " WHERE "
//                    + UsersDBConstants.COLUMN_USERNAME + "=? AND " + UsersDBConstants.COLUMN_PASSWORD + "=?");
//            ps.setString(1, username);
//            ps.setString(2, password);
//            ResultSet rs = ps.executeQuery();
//            
//            if (rs.next()) {
//                return true;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        
//        return false;
//    }
    
    
    
    
    

}
