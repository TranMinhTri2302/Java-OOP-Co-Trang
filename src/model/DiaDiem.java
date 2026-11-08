package model;

public class DiaDiem {
    private String maDiaDiem;
    private String tenDiaDiem;
    private String loaiDiaDiem;
    private String maQuocGia;

    public DiaDiem() {}

    public DiaDiem(String maDiaDiem, String tenDiaDiem, String loaiDiaDiem, String maQuocGia) {
        this.maDiaDiem = maDiaDiem;
        this.tenDiaDiem = tenDiaDiem;
        this.loaiDiaDiem = loaiDiaDiem;
        this.maQuocGia = maQuocGia;
    }

    public String getMaDiaDiem() { return maDiaDiem; }
    public void setMaDiaDiem(String maDiaDiem) { this.maDiaDiem = maDiaDiem; }

    public String getTenDiaDiem() { return tenDiaDiem; }
    public void setTenDiaDiem(String tenDiaDiem) { this.tenDiaDiem = tenDiaDiem; }

    public String getLoaiDiaDiem() { return loaiDiaDiem; }
    public void setLoaiDiaDiem(String loaiDiaDiem) { this.loaiDiaDiem = loaiDiaDiem; }

    public String getMaQuocGia() { return maQuocGia; }
    public void setMaQuocGia(String maQuocGia) { this.maQuocGia = maQuocGia; }

    @Override
    public String toString() {
        return String.format("%-10s | %-20s | %-12s | QG: %s", maDiaDiem, tenDiaDiem, loaiDiaDiem, maQuocGia);
    }
}
