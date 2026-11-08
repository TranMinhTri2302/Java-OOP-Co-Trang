package model;

import java.time.LocalDate;
import java.time.LocalTime;

// Lop truu tuong trung tam, ap dung tinh dong goi va tinh truu tuong (Abstraction)
// Cac thuoc tinh protected de cac lop con ke thua co the truc tiep su dung
public abstract class ChuyenBay {
    protected String maCB;
    protected String tenChuyenBay;
    protected String maMayBay;
    protected String maHang;
    protected String maSanBayDi;
    protected String maSanBayDen;
    protected LocalDate ngayDi;
    protected LocalTime GioDi;
    protected LocalDate ngayDen;
    protected LocalTime GioDen;
    protected double giaVeCoBan;
    protected int tongSoVeMoBan;
    protected int soVeDaBan;
    protected int soVeConLai;
    protected double tongDoanhThu;
    protected String trangThai;
    protected String ghiChu;

    public ChuyenBay() {}

    public ChuyenBay(String maCB, String tenChuyenBay, String maMayBay, String maHang,
                     String maSanBayDi, String maSanBayDen, LocalDate ngayDi, LocalTime GioDi,
                     LocalDate ngayDen, LocalTime GioDen, double giaVeCoBan, int tongSoVeMoBan,
                     int soVeDaBan, int soVeConLai, double tongDoanhThu, String trangThai, String ghiChu) {
        this.maCB = maCB;
        this.tenChuyenBay = tenChuyenBay;
        this.maMayBay = maMayBay;
        this.maHang = maHang;
        this.maSanBayDi = maSanBayDi;
        this.maSanBayDen = maSanBayDen;
        this.ngayDi = ngayDi;
        this.GioDi = GioDi;
        this.ngayDen = ngayDen;
        this.GioDen = GioDen;
        this.giaVeCoBan = giaVeCoBan;
        this.tongSoVeMoBan = tongSoVeMoBan;
        this.soVeDaBan = soVeDaBan;
        this.soVeConLai = soVeConLai;
        this.tongDoanhThu = tongDoanhThu;
        this.trangThai = trangThai;
        this.ghiChu = ghiChu;
    }

    // Phuong thuc truu tuong de cac lop con ghi de (Polymorphism): kiem tra khach hang co du dieu kien bay
    public abstract String kiemTraDieuKien(KhachHang khach);
    // Phuong thuc truu tuong xac dinh loai chuyen bay: NOI_DIA hoac QUOC_TE
    public abstract String getLoaiChuyen();

    public String getMaCB() { return maCB; }
    public void setMaCB(String maCB) { this.maCB = maCB; }

    public String getTenChuyenBay() { return tenChuyenBay; }
    public void setTenChuyenBay(String tenChuyenBay) { this.tenChuyenBay = tenChuyenBay; }

    public String getMaMayBay() { return maMayBay; }
    public void setMaMayBay(String maMayBay) { this.maMayBay = maMayBay; }

    public String getMaHang() { return maHang; }
    public void setMaHang(String maHang) { this.maHang = maHang; }

    public String getMaSanBayDi() { return maSanBayDi; }
    public void setMaSanBayDi(String maSanBayDi) { this.maSanBayDi = maSanBayDi; }

    public String getMaSanBayDen() { return maSanBayDen; }
    public void setMaSanBayDen(String maSanBayDen) { this.maSanBayDen = maSanBayDen; }

    public LocalDate getNgayDi() { return ngayDi; }
    public void setNgayDi(LocalDate ngayDi) { this.ngayDi = ngayDi; }

    public LocalTime getGioDi() { return GioDi; }
    public void setGioDi(LocalTime gioDi) { this.GioDi = gioDi; }

    public LocalDate getNgayDen() { return ngayDen; }
    public void setNgayDen(LocalDate ngayDen) { this.ngayDen = ngayDen; }

    public LocalTime getGioDen() { return GioDen; }
    public void setGioDen(LocalTime gioDen) { this.GioDen = gioDen; }

    public double getGiaVeCoBan() { return giaVeCoBan; }
    public void setGiaVeCoBan(double giaVeCoBan) { this.giaVeCoBan = giaVeCoBan; }

    public int getTongSoVeMoBan() { return tongSoVeMoBan; }
    public void setTongSoVeMoBan(int tongSoVeMoBan) { this.tongSoVeMoBan = tongSoVeMoBan; }

    public int getSoVeDaBan() { return soVeDaBan; }
    public void setSoVeDaBan(int soVeDaBan) { this.soVeDaBan = soVeDaBan; }

    public int getSoVeConLai() { return soVeConLai; }
    public void setSoVeConLai(int soVeConLai) { this.soVeConLai = soVeConLai; }

    public double getTongDoanhThu() { return tongDoanhThu; }
    public void setTongDoanhThu(double tongDoanhThu) { this.tongDoanhThu = tongDoanhThu; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }

    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }
}
