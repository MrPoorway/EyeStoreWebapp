package com.group2.model;

import java.io.Serializable;

public class Cart implements Serializable {

    private Glasses book;
    private int quantity;

    public Cart(Glasses book, int quantity) {
        this.book = book;
        this.quantity = quantity;
    }

    public Glasses getGlasses() {
        return book;
    }

    public void setBook(Glasses book) {
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
