package model;

public class SanBay {
    private String maSanBay;
    private String tenSanBay;
    private String maDiaDiem;
    private String diaChi;
    private boolean trangThaiHoatDong;

    public SanBay() {}

    public SanBay(String maSanBay, String tenSanBay, String maDiaDiem, String diaChi, boolean trangThaiHoatDong) {
        this.maSanBay = maSanBay;
        this.tenSanBay = tenSanBay;
        this.maDiaDiem = maDiaDiem;
        this.diaChi = diaChi;
        this.trangThaiHoatDong = trangThaiHoatDong;
    }

    public String getMaSanBay() { return maSanBay; }
    public void setMaSanBay(String maSanBay) { this.maSanBay = maSanBay; }

    public String getTenSanBay() { return tenSanBay; }
    public void setTenSanBay(String tenSanBay) { this.tenSanBay = tenSanBay; }

    public String getMaDiaDiem() { return maDiaDiem; }
    public void setMaDiaDiem(String maDiaDiem) { this.maDiaDiem = maDiaDiem; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    public boolean isTrangThaiHoatDong() { return trangThaiHoatDong; }
    public void setTrangThaiHoatDong(boolean trangThaiHoatDong) { this.trangThaiHoatDong = trangThaiHoatDong; }

    @Override
    public String toString() {
        return String.format("%-8s | %-25s | DD: %-6s | %s", maSanBay, tenSanBay, maDiaDiem, (trangThaiHoatDong ? "Hoat dong" : "Tam dung"));
    }
}
