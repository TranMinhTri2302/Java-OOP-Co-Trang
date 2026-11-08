package model;

public class HangHangKhong {
    private String maHang;
    private String tenHang;
    private String soDienThoai;
    private String email;
    private boolean trangThaiHoatDong;

    public HangHangKhong() {}

    public HangHangKhong(String maHang, String tenHang, String soDienThoai, String email, boolean trangThaiHoatDong) {
        this.maHang = maHang;
        this.tenHang = tenHang;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.trangThaiHoatDong = trangThaiHoatDong;
    }

    public String getMaHang() { return maHang; }
    public void setMaHang(String maHang) { this.maHang = maHang; }

    public String getTenHang() { return tenHang; }
    public void setTenHang(String tenHang) { this.tenHang = tenHang; }

    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public boolean isTrangThaiHoatDong() { return trangThaiHoatDong; }
    public void setTrangThaiHoatDong(boolean trangThaiHoatDong) { this.trangThaiHoatDong = trangThaiHoatDong; }

    @Override
    public String toString() {
        return String.format("%-8s | %-20s | %-12s | %s", maHang, tenHang, soDienThoai, (trangThaiHoatDong ? "Hoat dong" : "Tam dung"));
    }
}
