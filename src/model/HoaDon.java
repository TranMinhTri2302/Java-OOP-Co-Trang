package model;

import java.time.LocalDateTime;

// Lop HoaDon implement interface ThongKe de thuc hien da hinh trong tinh toan doanh thu
public class HoaDon implements ThongKe {
    // Bien static va phuong thuc static dung chung cho hoa don
    public static final String DON_VI_TIEN = "VND";
    public static String dinhDangTien(double tien) {
        return String.format("%,.0f " + DON_VI_TIEN, tien);
    }

    private String maHD;
    private String maKH;
    private String maNV;
    private String maCB;
    private LocalDateTime ngayLap;
    private int soLuongVe;
    private double tongTien;
    private String phuongThucThanhToan;
    private String trangThai;

    public HoaDon() {}

    public HoaDon(String maHD, String maKH, String maNV, String maCB, LocalDateTime ngayLap,
                  int soLuongVe, double tongTien, String phuongThucThanhToan, String trangThai) {
        this.maHD = maHD;
        this.maKH = maKH;
        this.maNV = maNV;
        this.maCB = maCB;
        this.ngayLap = ngayLap;
        this.soLuongVe = soLuongVe;
        this.tongTien = tongTien;
        this.phuongThucThanhToan = phuongThucThanhToan;
        this.trangThai = trangThai;
    }

    // Cai dat phuong thuc tu interface ThongKe (Polymorphism):
    // Neu hoa don con HIEU_LUC thi tra ve tong tien thuc te; neu da huy tra ve 0
    @Override
    public double tinhTongTien() {
        return "HIEU_LUC".equals(this.trangThai) ? this.tongTien : 0.0;
    }

    public String getMaHD() { return maHD; }
    public void setMaHD(String maHD) { this.maHD = maHD; }

    public String getMaKH() { return maKH; }
    public void setMaKH(String maKH) { this.maKH = maKH; }

    public String getMaNV() { return maNV; }
    public void setMaNV(String maNV) { this.maNV = maNV; }

    public String getMaCB() { return maCB; }
    public void setMaCB(String maCB) { this.maCB = maCB; }

    public LocalDateTime getNgayLap() { return ngayLap; }
    public void setNgayLap(LocalDateTime ngayLap) { this.ngayLap = ngayLap; }

    public int getSoLuongVe() { return soLuongVe; }
    public void setSoLuongVe(int soLuongVe) { this.soLuongVe = soLuongVe; }

    public double getTongTien() { return tongTien; }
    public void setTongTien(double tongTien) { this.tongTien = tongTien; }

    public String getPhuongThucThanhToan() { return phuongThucThanhToan; }
    public void setPhuongThucThanhToan(String phuongThucThanhToan) { this.phuongThucThanhToan = phuongThucThanhToan; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }

    @Override
    public String toString() {
        String nl = (ngayLap != null) ? ngayLap.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) : "";
        return String.format("%-10s | KH Mua: %-8s | NV: %-8s | CB: %-8s | %s | Ve: %-2d | %,.0f VND | %-12s | %s",
                maHD, maKH, maNV, maCB, nl, soLuongVe, tongTien, phuongThucThanhToan, trangThai);
    }
}
