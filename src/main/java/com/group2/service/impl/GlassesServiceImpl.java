package com.group2.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.group2.constant.ResponseCode;
import com.group2.constant.db.GlassesDBConstants;
import com.group2.model.Glasses;
import com.group2.model.StoreException;
import com.group2.service.GlassesService;
import com.group2.util.DBUtil;

public class GlassesServiceImpl implements GlassesService {

    private static final String getAllGlassesQuery = "SELECT * FROM " + GlassesDBConstants.TABLE_GLASSES;
    private static final String getGlassesByIdQuery = "SELECT * FROM " + GlassesDBConstants.TABLE_GLASSES
            + " WHERE " + GlassesDBConstants.COLUMN_id + " = ?";
    
    private static final String deleteGlassesByIdQuery = "DELETE FROM " + GlassesDBConstants.TABLE_GLASSES + "  WHERE "
            + GlassesDBConstants.COLUMN_id + "=?";
    
    private static final String addGlassesQuery = "INSERT INTO " + GlassesDBConstants.TABLE_GLASSES + "  VALUES(?,?,?,?,?,?)";
    
    private static final String updateGlassesQtyByIdQuery = "UPDATE " + GlassesDBConstants.TABLE_GLASSES + " SET "
            + GlassesDBConstants.COLUMN_QUANTITY + "=?"
            		+ " WHERE " + GlassesDBConstants.COLUMN_id
            + "=?";

    private static final String updateGlassesByIdQuery = "UPDATE " + GlassesDBConstants.TABLE_GLASSES + " SET "
            + GlassesDBConstants.COLUMN_NAME + "=? , "
            + GlassesDBConstants.COLUMN_TYPE + "=?, "
            + GlassesDBConstants.COLUMN_PRICE + "=?, "
            + GlassesDBConstants.COLUMN_QUANTITY + "=? , "
            + GlassesDBConstants.COLUMN_IMAGE_URI + "=? "
            + "  WHERE " + GlassesDBConstants.COLUMN_id
            + "=?";
    
    private static final String getImageUriQuery = "SELECT" + GlassesDBConstants.COLUMN_IMAGE_URI + " FROM "+GlassesDBConstants.TABLE_GLASSES
    		+ " WHERE " + GlassesDBConstants.COLUMN_id + " = ?";

    @Override
    public Glasses getGlassesById(String glassesId) throws StoreException {
        Glasses glasses = null;
        Connection con = DBUtil.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(getGlassesByIdQuery);
            ps.setString(1, glassesId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int gId = rs.getInt(1);
                String gName = rs.getString(2);
                String gType = rs.getString(3);
                int gPrice = rs.getInt(4);
                int gQty = rs.getInt(5);
                String imageUri = rs.getString(6);

                glasses = new Glasses(gId, gName, gType, gPrice, gQty,imageUri);
            }
        } catch (SQLException e) {

        }
        return glasses;
    }

    @Override
    public List<Glasses> getAllGlasses() throws StoreException {
        List<Glasses> glassess = new ArrayList<Glasses>();
        Connection con = DBUtil.getConnection();

        try {
            PreparedStatement ps = con.prepareStatement(getAllGlassesQuery);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int gId = rs.getInt(1);
                String gName = rs.getString(2); 
                String gType = rs.getString(3);
                int gPrice = rs.getInt(4);
                int gQty = rs.getInt(5);
                String imageUri = rs.getString(6);

                Glasses glasses = new Glasses(gId, gName, gType, gPrice, gQty, imageUri);
                glassess.add(glasses);
            }
        } catch (SQLException e) {

        }
        return glassess;
    }

    @Override
    public String deleteGlassesById(String glassesId) throws StoreException {
        String response = ResponseCode.FAILURE.name();
        Connection con = DBUtil.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(deleteGlassesByIdQuery);
            ps.setString(1, glassesId);
            int k = ps.executeUpdate();
            if (k == 1) {
                response = ResponseCode.SUCCESS.name();
            }
        } catch (Exception e) {
            response += " : " + e.getMessage();
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public String addGlasses(Glasses glasses) throws StoreException {
        String responseCode = ResponseCode.FAILURE.name();
        Connection con = DBUtil.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(addGlassesQuery);
            ps.setInt(1, glasses.getId());
            ps.setString(2, glasses.getName());
            ps.setString(3, glasses.getType());
            ps.setDouble(4, glasses.getPrice());
            ps.setInt(5, glasses.getQuantity());
            ps.setString(6, glasses.getImageUri());
            int k = ps.executeUpdate();
            if (k == 1) {
                responseCode = ResponseCode.SUCCESS.name();
            }
        } catch (Exception e) {
            responseCode += " : " + e.getMessage();
            e.printStackTrace();
        }
        return responseCode;
    }

    @Override
    public String updateGlassesQtyById(int glassesId, int quantity) throws StoreException {
        String responseCode = ResponseCode.FAILURE.name();
        Connection con = DBUtil.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(updateGlassesQtyByIdQuery);
            ps.setInt(1, quantity);
            ps.setInt(2, glassesId);
            ps.executeUpdate();
            responseCode = ResponseCode.SUCCESS.name();
        } catch (Exception e) {
            responseCode += " : " + e.getMessage();
            e.printStackTrace();
        }
        return responseCode;
    }

    @Override
    public List<Glasses> getGlassessByCommaSeperatedGlassesIds(String commaSeperatedGlassesIds) throws StoreException {
        List<Glasses> glassess = new ArrayList<Glasses>();
        Connection con = DBUtil.getConnection();
        try {
            String getGlassessByCommaSeperatedGlassesIdsQuery = "SELECT * FROM " + GlassesDBConstants.TABLE_GLASSES
                    + " WHERE " +
                    GlassesDBConstants.COLUMN_id + " IN ( " + commaSeperatedGlassesIds + " )";
            PreparedStatement ps = con.prepareStatement(getGlassessByCommaSeperatedGlassesIdsQuery);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int gId = rs.getInt(1);
                String gName = rs.getString(2);
                String gType = rs.getString(3);
                int gPrice = rs.getInt(4);
                int gQty = rs.getInt(5);
                String imageUri = rs.getString(6);

                Glasses glasses = new Glasses(gId, gName, gType, gPrice, gQty, imageUri);
                glassess.add(glasses);
            }
        } catch (SQLException e) {

        }
        return glassess;
    }

    public String updateGlasses(Glasses glasses) throws StoreException {
        String responseCode = ResponseCode.FAILURE.name();
        Connection con = DBUtil.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(updateGlassesByIdQuery);
            ps.setString(1, glasses.getName());
            ps.setString(2, glasses.getType());
            ps.setDouble(3, glasses.getPrice());
            ps.setInt(4, glasses.getQuantity());
            ps.setString(5, glasses.getImageUri());
            ps.setInt(6, glasses.getId());
            ps.executeUpdate();
            responseCode = ResponseCode.SUCCESS.name();
        } catch (Exception e) {
            responseCode += " : " + e.getMessage();
            e.printStackTrace();
        }
        return responseCode;
    }
    
//    //get imageUri
//    public Glasses getImageUri(String glassesId) throws StoreException {
//    	Glasses glassesImage = null;
//        Connection con = DBUtil.getConnection();
//        try {
//            PreparedStatement ps = con.prepareStatement(getImageUriQuery);
//            ps.setString(1, glassesId);
//            ResultSet rs = ps.executeQuery();
//            
//            while (rs.next()) {
//            	String imageUri = rs.getString(1);
//            	glassesImage = new Glasses(imageUri);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return glassesImage;
//    }
    
//    public Glasses getImageUri(String glassesId) throws StoreException {
//        Glasses glassesImage = null;
//        Connection con = DBUtil.getConnection();
//        try {
//            PreparedStatement ps = con.prepareStatement(getImageUriQuery);
//            ps.setString(1, glassesId);
//            ResultSet rs = ps.executeQuery();
//
//            if (rs.next()) {
//                String imageUri = rs.getString(1);
//                glassesImage = new Glasses(imageUri);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return glassesImage;
//    }
    

//    public Glasses getImageUri(String glassesId) throws StoreException {
//        Glasses glassesImage = null;
//        Connection con = DBUtil.getConnection();
//        try {
//            PreparedStatement ps = con.prepareStatement(getImageUriQuery);
//            ps.setString(1, glassesId);
//            ResultSet rs = ps.executeQuery();
//
//            if (rs.next()) {
//                String imageUri = rs.getString(1);
//                glassesImage = new Glasses(imageUri);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return glassesImage;
//    }
    
    
//    public Glasses getImageUri(String glassesId) throws StoreException {
//    	Glasses glasses = null;
//        Connection con = DBUtil.getConnection();
//        try {
//            PreparedStatement ps = con.prepareStatement(getImageUriQuery);
//            ps.setString(1, glassesId);
//            ResultSet rs = ps.executeQuery();
//
//            if (rs.next()) {
//            	String imageUri = rs.getString(1);
//            	
//            	glasses = new Glasses(imageUri);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return glasses;
//    }
    
//    public String getImageUri(String glassesId) throws StoreException {
//        String imageUri = "";
//        Connection con = DBUtil.getConnection();
//        try {
//            PreparedStatement ps = con.prepareStatement(getImageUriQuery);
//            ps.setString(1, glassesId);
//            ResultSet rs = ps.executeQuery();
//
//            if (rs.next()) {
//                imageUri = rs.getString(1);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return imageUri;
//    }
    
//    public String getImageUri(String glassesId) throws StoreException {
//        String imageUri = "";
//        Connection con = DBUtil.getConnection();
//        try {
//            PreparedStatement ps = con.prepareStatement(getImageUriQuery);
//            ps.setString(1, glassesId);
//            ResultSet rs = ps.executeQuery();
//
//            if (rs.next()) {
//                imageUri = rs.getString(6);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return imageUri;
//    }
    
            
            
    
    
    
    

}
