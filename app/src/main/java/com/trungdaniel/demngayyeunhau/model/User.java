package com.trungdaniel.demngayyeunhau.model;

import java.util.Date;

public class User {
    private int uID;
    private String tenBan,tenNguoiAy;
    private Date dateStart;

    public User() {

    }

    public User(String tenBan, String tenNguoiAy, Date dateStart) {
        this.tenBan = tenBan;
        this.tenNguoiAy = tenNguoiAy;
        this.dateStart = dateStart;
    }

    public User(int uID, String tenBan, String tenNguoiAy, Date dateStart) {
        this.uID = uID;
        this.tenBan = tenBan;
        this.tenNguoiAy = tenNguoiAy;
        this.dateStart = dateStart;
    }

    public int getuID() {
        return uID;
    }

    public void setuID(int uID) {
        this.uID = uID;
    }

    public String getTenBan() {
        return tenBan;
    }

    public void setTenBan(String tenBan) {
        this.tenBan = tenBan;
    }

    public String getTenNguoiAy() {
        return tenNguoiAy;
    }

    public void setTenNguoiAy(String tenNguoiAy) {
        this.tenNguoiAy = tenNguoiAy;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }
}
