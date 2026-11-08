package model;

import java.util.ArrayList;
import java.util.List;

public class MayBay {
    private String maMayBay;
    private String maHang;
    private String tenMayBay;
    private String loaiMayBay;
    private int tongSoGhe;
    private List<String> danhSachSoGhe;
    private String trangThai;

    public MayBay() {
        this.danhSachSoGhe = new ArrayList<>();
    }

    public MayBay(String maMayBay, String maHang, String tenMayBay, String loaiMayBay,
                  int tongSoGhe, List<String> danhSachSoGhe, String trangThai) {
        this.maMayBay = maMayBay;
        this.maHang = maHang;
        this.tenMayBay = tenMayBay;
        this.loaiMayBay = loaiMayBay;
        this.tongSoGhe = tongSoGhe;
        this.danhSachSoGhe = (danhSachSoGhe != null) ? danhSachSoGhe : new ArrayList<>();
        this.trangThai = trangThai;
    }

    public String getMaMayBay() { return maMayBay; }
    public void setMaMayBay(String maMayBay) { this.maMayBay = maMayBay; }

    public String getMaHang() { return maHang; }
    public void setMaHang(String maHang) { this.maHang = maHang; }

    public String getTenMayBay() { return tenMayBay; }
    public void setTenMayBay(String tenMayBay) { this.tenMayBay = tenMayBay; }

    public String getLoaiMayBay() { return loaiMayBay; }
    public void setLoaiMayBay(String loaiMayBay) { this.loaiMayBay = loaiMayBay; }

    public int getTongSoGhe() { return tongSoGhe; }
    public void setTongSoGhe(int tongSoGhe) { this.tongSoGhe = tongSoGhe; }

    public List<String> getDanhSachSoGhe() { return danhSachSoGhe; }
    public void setDanhSachSoGhe(List<String> danhSachSoGhe) { this.danhSachSoGhe = danhSachSoGhe; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }

    @Override
    public String toString() {
        return String.format("%-10s | %-8s | %-15s | %-12s | Ghe: %-3d | %s",
                maMayBay, maHang, tenMayBay, loaiMayBay, tongSoGhe, trangThai);
    }
}
