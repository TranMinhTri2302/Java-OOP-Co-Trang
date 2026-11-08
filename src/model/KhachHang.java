package model;

import java.time.LocalDate;

public class KhachHang {
    private String maKH;
    private String hoTen;
    private LocalDate ngaySinh;
    private String gioiTinh;
    private String quocTich;
    private String diaChi;
    private String soDienThoai;
    private String email;
    private String soCCCD;
    private String soHoChieu;
    private LocalDate ngayHetHanHoChieu;
    private String soVisa;
    private LocalDate ngayHetHanVisa;
    private int tongSoVeDaMua;
    private double tongTienDaMua;

    public KhachHang() {}

    public KhachHang(String maKH, String hoTen, LocalDate ngaySinh, String gioiTinh, String quocTich,
                     String diaChi, String soDienThoai, String email, String soCCCD,
                     String soHoChieu, LocalDate ngayHetHanHoChieu, String soVisa, LocalDate ngayHetHanVisa,
                     int tongSoVeDaMua, double tongTienDaMua) {
        this.maKH = maKH;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.quocTich = quocTich;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.soCCCD = soCCCD;
        this.soHoChieu = soHoChieu;
        this.ngayHetHanHoChieu = ngayHetHanHoChieu;
        this.soVisa = soVisa;
        this.ngayHetHanVisa = ngayHetHanVisa;
        this.tongSoVeDaMua = tongSoVeDaMua;
        this.tongTienDaMua = tongTienDaMua;
    }

    public String getMaKH() { return maKH; }
    public void setMaKH(String maKH) { this.maKH = maKH; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public LocalDate getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(LocalDate ngaySinh) { this.ngaySinh = ngaySinh; }

    public String getGioiTinh() { return gioiTinh; }
    public void setGioiTinh(String gioiTinh) { this.gioiTinh = gioiTinh; }

    public String getQuocTich() { return quocTich; }
    public void setQuocTich(String quocTich) { this.quocTich = quocTich; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSoCCCD() { return soCCCD; }
    public void setSoCCCD(String soCCCD) { this.soCCCD = soCCCD; }

    public String getSoHoChieu() { return soHoChieu; }
    public void setSoHoChieu(String soHoChieu) { this.soHoChieu = soHoChieu; }

    public LocalDate getNgayHetHanHoChieu() { return ngayHetHanHoChieu; }
    public void setNgayHetHanHoChieu(LocalDate ngayHetHanHoChieu) { this.ngayHetHanHoChieu = ngayHetHanHoChieu; }

    public String getSoVisa() { return soVisa; }
    public void setSoVisa(String soVisa) { this.soVisa = soVisa; }

    public LocalDate getNgayHetHanVisa() { return ngayHetHanVisa; }
    public void setNgayHetHanVisa(LocalDate ngayHetHanVisa) { this.ngayHetHanVisa = ngayHetHanVisa; }

    public int getTongSoVeDaMua() { return tongSoVeDaMua; }
    public void setTongSoVeDaMua(int tongSoVeDaMua) { this.tongSoVeDaMua = tongSoVeDaMua; }

    public double getTongTienDaMua() { return tongTienDaMua; }
    public void setTongTienDaMua(double tongTienDaMua) { this.tongTienDaMua = tongTienDaMua; }

    @Override
    public String toString() {
        String ns = (ngaySinh != null) ? ngaySinh.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "";
        return String.format("%-8s | %-20s | %-10s | %-4s | %-12s | CCCD: %-12s | Da mua: %d ve (%,.0f VND)",
                maKH, hoTen, ns, gioiTinh, soDienThoai, (soCCCD != null ? soCCCD : ""), tongSoVeDaMua, tongTienDaMua);
    }
}
