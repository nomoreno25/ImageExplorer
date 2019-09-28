package com.hailv.imageexplorer.Modal;

public class ImageDataModel {
    private String imagePath, imageDate;

    public String getImageDate() {
        return imageDate;
    }

    public void setImageDate(String imageDate) {
        this.imageDate = imageDate;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public ImageDataModel(String imagePath, String imageDate) {
        super();
        this.imageDate = imageDate;
        this.imagePath = imagePath;
    }
}
