package model;

public class QuocGia {
    private String maQuocGia;
    private String tenQuocGia;

    public QuocGia() {}

    public QuocGia(String maQuocGia, String tenQuocGia) {
        this.maQuocGia = maQuocGia;
        this.tenQuocGia = tenQuocGia;
    }

    public String getMaQuocGia() { return maQuocGia; }
    public void setMaQuocGia(String maQuocGia) { this.maQuocGia = maQuocGia; }

    public String getTenQuocGia() { return tenQuocGia; }
    public void setTenQuocGia(String tenQuocGia) { this.tenQuocGia = tenQuocGia; }

    @Override
    public String toString() {
        return String.format("%-10s | %s", maQuocGia, tenQuocGia);
    }
}
