package com.example.rationmanagementandnotificationsystem.ui.Todays_activity;

public class Product_datamodel {

    String product_name;
    String date;
    String price;
    String id;
    String time;



    //----------------------------------Constructor------------------------//

    public Product_datamodel() {

    }

    //------------------------------setter methods---------------------------//

    public void setId(String id) {
        this.id = id;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    public void setTime(String time) {
        this.time = time;
    }


//----------------------------------------getter method--------------------------//

    public String getId() {
        return id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getDate() {
        return date;
    }

    public String getPrice() {
        return price;
    }
    public String getTime() {
        return time;
    }


}
