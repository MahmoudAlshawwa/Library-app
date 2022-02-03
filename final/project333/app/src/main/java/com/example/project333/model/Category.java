package com.example.project333.model;

import java.io.Serializable;

public class Category implements Serializable {
    private int id;
    private String name;
    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public Category(int id,String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setId(int n) {
        this.id = n;
    }

    public void setName(String name) {
        this.name = name;
    }
}

