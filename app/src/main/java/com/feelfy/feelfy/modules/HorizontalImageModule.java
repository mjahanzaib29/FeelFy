package com.feelfy.feelfy.modules;

public class HorizontalImageModule {
    private int image;
    private int ic_vector_add;
    private String id;
    private String photopath;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhotopath() {
        return photopath;
    }

    public HorizontalImageModule() {
    }

    public void setPhotopath(String photopath) {
        this.photopath = photopath;
    }

    public HorizontalImageModule(int image, int ic_vector_add) {
        this.image =image;
        this.ic_vector_add = ic_vector_add;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getIc_vector_add() {
        return ic_vector_add;
    }

    public void setIc_vector_add(int ic_vector_add) {
        this.ic_vector_add = ic_vector_add;
    }
}
