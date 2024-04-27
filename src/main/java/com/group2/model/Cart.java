package com.group2.model;

import java.io.Serializable;

public class Cart implements Serializable {

    private Glasses glasses;
    private int quantity;

    public Cart(Glasses glasses, int quantity) {
        this.glasses = glasses;
        this.quantity = quantity;
    }

    public Glasses getGlasses() {
        return glasses;
    }

    public void setGlasses(Glasses glasses) {
        this.glasses = glasses;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
