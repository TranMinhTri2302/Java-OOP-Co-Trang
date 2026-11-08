package model;

// Lop Ve bieu dien mot ve vat ly tren chuyen bay gan voi so ghe cu the
// Luu y: Lop Ve khong chua maKH; khach bay duoc quan ly qua ChiTietHoaDon
public class Ve {
    private String maVe;
    private String maCB;
    private String soGhe;
    private String hangVe;
    private String loaiVe;
    private double giaVe;
    private String trangThai;

    public Ve() {
        this.hangVe = "PHO_THONG";
        this.loaiVe = "THUONG";
        this.trangThai = "HIEU_LUC";
    }

    public Ve(String maVe, String maCB, String soGhe, String hangVe, String loaiVe, double giaVe, String trangThai) {
        this.maVe = maVe;
        this.maCB = maCB;
        this.soGhe = soGhe;
        this.hangVe = hangVe;
        this.loaiVe = loaiVe;
        this.giaVe = giaVe;
        this.trangThai = trangThai;
    }

    public String getMaVe() { return maVe; }
    public void setMaVe(String maVe) { this.maVe = maVe; }

    public String getMaCB() { return maCB; }
    public void setMaCB(String maCB) { this.maCB = maCB; }

    public String getSoGhe() { return soGhe; }
    public void setSoGhe(String soGhe) { this.soGhe = soGhe; }

    public String getHangVe() { return hangVe; }
    public void setHangVe(String hangVe) { this.hangVe = hangVe; }

    public String getLoaiVe() { return loaiVe; }
    public void setLoaiVe(String loaiVe) { this.loaiVe = loaiVe; }

    public double getGiaVe() { return giaVe; }
    public void setGiaVe(double giaVe) { this.giaVe = giaVe; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }

    @Override
    public String toString() {
        return String.format("%-10s | CB: %-8s | Ghe: %-4s | Hang: %-10s | Loai: %-8s | Gia: %,.0f VND | %s",
                maVe, maCB, soGhe, hangVe, loaiVe, giaVe, trangThai);
    }
}
