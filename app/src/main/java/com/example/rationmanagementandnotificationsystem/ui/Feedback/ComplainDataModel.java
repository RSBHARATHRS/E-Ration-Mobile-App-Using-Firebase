package com.example.rationmanagementandnotificationsystem.ui.Feedback;

public class ComplainDataModel {
    String comp_id;
    String smartCard_ID;
    String comp_user_phone_no;
    String comp_user_name;
    String comp_obj;
    String comp_desc;
    String comp_date;
    String comp_time;
    String comp_status;//="received";

    public void setComp_status(String comp_status) {
        this.comp_status = comp_status;
    }

    public String getComp_status() {
        return comp_status;
    }


    public ComplainDataModel() {
    }

    public void setComp_id(String comp_id) {
        this.comp_id = comp_id;
    }

    public void setComp_date(String comp_date) {
        this.comp_date = comp_date;
    }

    public void setComp_time(String comp_time) {
        this.comp_time = comp_time;
    }

    public void setComp_user_name(String comp_user_name) {
        this.comp_user_name = comp_user_name;
    }

    public void setComp_obj(String comp_obj) {
        this.comp_obj = comp_obj;
    }

    public void setComp_desc(String comp_desc) {
        this.comp_desc = comp_desc;
    }
    public void setComp_user_phone_no(String comp_user_phone_no) {
        this.comp_user_phone_no = comp_user_phone_no;
    }


    public String getSmartCard_ID() {
        return smartCard_ID;
    }
    public String getComp_user_phone_no() {
        return comp_user_phone_no;
    }


    public void setSmartCard_ID(String smartCard_ID) {
        this.smartCard_ID = smartCard_ID;
    }
    public String getComp_id() {
        return comp_id;
    }

    public String getComp_date() {
        return comp_date;
    }

    public String getComp_time() {
        return comp_time;
    }

    public String getComp_user_name() {
        return comp_user_name;
    }

    public String getComp_obj() {
        return comp_obj;
    }

    public String getComp_desc() {
        return comp_desc;
    }









}
