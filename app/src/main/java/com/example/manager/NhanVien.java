package com.example.manager;

public class NhanVien {
    public int id;
    public String ten;
    public String sdt;
    public byte[] image ;

    public NhanVien(int id, String ten, String sdt, byte[] image) {
        this.id = id;
        this.ten = ten;
        this.sdt = sdt;
        this.image = image;
    }
}
