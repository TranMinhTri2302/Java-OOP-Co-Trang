package model;

// Lop ChiTietHoaDon bieu dien tung dong chi tiet tren hoa don
// Moi chi tiet co mot thanhTien = donGiaVe * soLuong; HoaDon se co tongTien = tong cac thanhTien
public class ChiTietHoaDon {
    private String maHD;
    private String maVe;
    private String maKH;
    private String loaiVe;
    private double donGiaVe;
    private int soLuong;
    private double thanhTien;

    public ChiTietHoaDon() {
        this.soLuong = 1;
    }

    public ChiTietHoaDon(String maHD, String maVe, String maKH, String loaiVe, double donGiaVe, int soLuong, double thanhTien) {
        this.maHD = maHD;
        this.maVe = maVe;
        this.maKH = maKH;
        this.loaiVe = loaiVe;
        this.donGiaVe = donGiaVe;
        this.soLuong = (soLuong > 0) ? soLuong : 1;
        this.thanhTien = thanhTien;
    }

    public String getMaHD() { return maHD; }
    public void setMaHD(String maHD) { this.maHD = maHD; }

    public String getMaVe() { return maVe; }
    public void setMaVe(String maVe) { this.maVe = maVe; }

    public String getMaKH() { return maKH; }
    public void setMaKH(String maKH) { this.maKH = maKH; }

    public String getLoaiVe() { return loaiVe; }
    public void setLoaiVe(String loaiVe) { this.loaiVe = loaiVe; }

    public double getDonGiaVe() { return donGiaVe; }
    public void setDonGiaVe(double donGiaVe) { this.donGiaVe = donGiaVe; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = (soLuong > 0) ? soLuong : 1; }

    public double getThanhTien() { return thanhTien; }
    public void setThanhTien(double thanhTien) { this.thanhTien = thanhTien; }

    // Alias tuong thich cho tongTien cua chi tiet hoa don
    public double getTongTien() { return thanhTien; }
    public void setTongTien(double tongTien) { this.thanhTien = tongTien; }

    @Override
    public String toString() {
        return String.format("%-10s | Ve: %-10s | Nguoi bay: %-8s | Loai: %-8s | Don gia: %,.0f VND | SL: %d | Thanh tien: %,.0f VND",
                maHD, maVe, maKH, loaiVe, donGiaVe, soLuong, thanhTien);
    }
}
