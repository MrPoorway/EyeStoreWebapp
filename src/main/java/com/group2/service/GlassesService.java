package com.group2.service;

import java.util.List;

import com.group2.model.Glasses;
import com.group2.model.StoreException;

public interface GlassesService {

    public Glasses getGlassesById(String glassesId) throws StoreException;

    public List<Glasses> getAllGlasses() throws StoreException;

    public List<Glasses> getGlassessByCommaSeperatedGlassesIds(String commaSeperatedGlassesIds) throws StoreException;

    public String deleteGlassesById(String glassesId) throws StoreException;

    public String addGlasses(Glasses glasses) throws StoreException;

    public String updateGlassesQtyById(int glassesId, int quantity) throws StoreException;
    
    public String updateGlasses(Glasses glasses) throws StoreException;

}
