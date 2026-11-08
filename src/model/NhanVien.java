package model;

import java.time.LocalDate;

public class NhanVien {
    private String maNV;
    private String hoTen;
    private LocalDate ngaySinh;
    private String gioiTinh;
    private String diaChi;
    private String soDienThoai;
    private String email;
    private String chucVu;
    private boolean trangThaiLamViec;
    private int tongSoHoaDonDaLap;
    private int tongSoVeDaBan;
    private double tongTienDaBan;

    public NhanVien() {}

    public NhanVien(String maNV, String hoTen, LocalDate ngaySinh, String gioiTinh,
                   String diaChi, String soDienThoai, String email, String chucVu,
                   boolean trangThaiLamViec, int tongSoHoaDonDaLap, int tongSoVeDaBan, double tongTienDaBan) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.chucVu = chucVu;
        this.trangThaiLamViec = trangThaiLamViec;
        this.tongSoHoaDonDaLap = tongSoHoaDonDaLap;
        this.tongSoVeDaBan = tongSoVeDaBan;
        this.tongTienDaBan = tongTienDaBan;
    }

    public String getMaNV() { return maNV; }
    public void setMaNV(String maNV) { this.maNV = maNV; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public LocalDate getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(LocalDate ngaySinh) { this.ngaySinh = ngaySinh; }

    public String getGioiTinh() { return gioiTinh; }
    public void setGioiTinh(String gioiTinh) { this.gioiTinh = gioiTinh; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getChucVu() { return chucVu; }
    public void setChucVu(String chucVu) { this.chucVu = chucVu; }

    public boolean isTrangThaiLamViec() { return trangThaiLamViec; }
    public void setTrangThaiLamViec(boolean trangThaiLamViec) { this.trangThaiLamViec = trangThaiLamViec; }

    public int getTongSoHoaDonDaLap() { return tongSoHoaDonDaLap; }
    public void setTongSoHoaDonDaLap(int tongSoHoaDonDaLap) { this.tongSoHoaDonDaLap = tongSoHoaDonDaLap; }

    public int getTongSoVeDaBan() { return tongSoVeDaBan; }
    public void setTongSoVeDaBan(int tongSoVeDaBan) { this.tongSoVeDaBan = tongSoVeDaBan; }

    public double getTongTienDaBan() { return tongTienDaBan; }
    public void setTongTienDaBan(double tongTienDaBan) { this.tongTienDaBan = tongTienDaBan; }

    @Override
    public String toString() {
        return String.format("%-8s | %-20s | %-12s | %-15s | HD: %-3d | Ve: %-3d | %,.0f VND | %s",
                maNV, hoTen, soDienThoai, chucVu, tongSoHoaDonDaLap, tongSoVeDaBan, tongTienDaBan, (trangThaiLamViec ? "Dang lam" : "Nghi"));
    }
}
