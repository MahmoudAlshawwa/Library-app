package com.example.project333.model;

import java.io.Serializable;

public class Book implements Serializable {
//    private static final AtomicInteger count = new AtomicInteger(0);
    private String authorName;
    private int categoryId;
    private int id;
    private String imageURL;
    private boolean isFavourite;
    private String name;
    private int pagesNumber;
    private int releaseYear;

    public Book() {
    }

    public Book(String name, String authorName, String imageURL, int releaseYear, int pagesNumber, int categoryId) {
        this.name = name;
        this.authorName = authorName;
        this.imageURL = imageURL;
        this.releaseYear = releaseYear;
        this.pagesNumber = pagesNumber;
        this.categoryId = categoryId;
    }

    public String getAuthorName() {
        return this.authorName;
    }

    public int getCategoryId() {
        return this.categoryId;
    }

    public int getId() {
        return this.id;
    }

    public String getImageURL() {
        return this.imageURL;
    }

    public String getName() {
        return this.name;
    }

    public int getPagesNumber() {
        return this.pagesNumber;
    }

    public int getReleaseYear() {
        return this.releaseYear;
    }

    public boolean isFavourite() {
        return this.isFavourite;
    }

    public void setAuthorName(String string2) {
        this.authorName = string2;
    }

    public void setCategoryId(int n) {
        this.categoryId = n;
    }

    public void setFavourite(boolean bl) {
        this.isFavourite = bl;
    }

    public void setId(int n) {
        this.id = n;
    }

    public void setImageURL(String string2) {
        this.imageURL = string2;
    }

    public void setName(String string2) {
        this.name = string2;
    }

    public void setPagesNumber(int n) {
        this.pagesNumber = n;
    }

    public void setReleaseYear(int n) {
        this.releaseYear = n;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Book{id=");
        stringBuilder.append(this.id);
        stringBuilder.append(", name='");
        stringBuilder.append(this.name);
        stringBuilder.append('\'');
        stringBuilder.append(", authorName='");
        stringBuilder.append(this.authorName);
        stringBuilder.append('\'');
        stringBuilder.append(", imageURL='");
        stringBuilder.append(this.imageURL);
        stringBuilder.append('\'');
        stringBuilder.append(", releaseYear=");
        stringBuilder.append(this.releaseYear);
        stringBuilder.append(", pagesNumber=");
        stringBuilder.append(this.pagesNumber);
        stringBuilder.append(", isFavourite=");
        stringBuilder.append(this.isFavourite);
        stringBuilder.append(", categoryId=");
        stringBuilder.append(this.categoryId);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}

