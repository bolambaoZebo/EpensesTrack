package com.example.app.mytipid;

public class MyListData{


    private String id;
    private String description;
    private String type;
    private String price;
    private String date;
    private int imgId;

    public MyListData(){}
    public MyListData(String id,String description,String type, String price,String date,int imgId) {
        this.description = description;
        this.id = id;
        this.price = price;
        this.date = date;
        this.imgId = imgId;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public int getImgId() {
        return imgId;
    }
    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public void setStudent_login_id(String toString) {
    }
}
