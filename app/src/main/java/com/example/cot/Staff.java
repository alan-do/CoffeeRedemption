package com.example.cot;

import java.io.Serializable;

public class Staff implements Serializable {
    private int Id;
    private  String Name;
    private  int Luong;
    private  int ViTri;
    private  String TaiKhoan;
    private  String MatKhau;


    public Staff( String name, String taikhoan,String matkhau,int id,int luong,int vitri) {
        Id=id;
        Name = name;
        Luong = luong;
        ViTri = vitri;
        TaiKhoan = taikhoan;
        MatKhau = matkhau;
    }

    public int getLuong() {
        return Luong;
    }

    public void setLuong(int luong) {
        Luong = luong;
    }

    public int getViTri() {
        return ViTri;
    }

    public void setViTri(int viTri) {
        ViTri = viTri;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTaiKhoan() {
        return TaiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        TaiKhoan = taiKhoan;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String matKhau) {
        MatKhau = matKhau;
    }
}
