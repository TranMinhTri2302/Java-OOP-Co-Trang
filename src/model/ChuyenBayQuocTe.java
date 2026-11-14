package model;

import java.time.LocalDate;
import java.time.LocalTime;

// Ke thua (Inheritance) tu lop truu tuong ChuyenBay
public class ChuyenBayQuocTe extends ChuyenBay {
    private String quocGiaDen;
    private boolean yeuCauKiemTraVisa;
    private int soThangHieuLucHoChieuToiThieu;

    public ChuyenBayQuocTe() {
        super();
    }

    public ChuyenBayQuocTe(String maCB, String tenChuyenBay, String maMayBay, String maHang,
                           String maSanBayDi, String maSanBayDen, LocalDate ngayDi, LocalTime GioDi,
                           LocalDate ngayDen, LocalTime GioDen, double giaVeCoBan, int tongSoVeMoBan,
                           int soVeDaBan, int soVeConLai, double tongDoanhThu, String trangThai, String ghiChu,
                           String quocGiaDen, boolean yeuCauKiemTraVisa, int soThangHieuLucHoChieuToiThieu) {
        super(maCB, tenChuyenBay, maMayBay, maHang, maSanBayDi, maSanBayDen, ngayDi, GioDi, ngayDen, GioDen,
              giaVeCoBan, tongSoVeMoBan, soVeDaBan, soVeConLai, tongDoanhThu, trangThai, ghiChu);
        this.quocGiaDen = quocGiaDen;
        this.yeuCauKiemTraVisa = yeuCauKiemTraVisa;
        this.soThangHieuLucHoChieuToiThieu = soThangHieuLucHoChieuToiThieu;
    }

    @Override
    public String getLoaiChuyen() {
        return "QUOC_TE";
    }

    // Ghi de phuong thuc truu tuong de the hien tinh da hinh (Polymorphism):
    // Chuyen bay quoc te kiem tra: co so ho chieu, thoi han ho chieu (toi thieu so thang sau ngay den), va visa (neu co yeu cau)
    @Override
    public String kiemTraDieuKien(KhachHang khach) {
        if (khach == null) return "Khach hang khong ton tai";
        if (this.ngayDen == null) return "Chuyen bay chua xac dinh ngay den";
        if (khach.getSoHoChieu() == null || khach.getSoHoChieu().trim().isEmpty() || khach.getNgayHetHanHoChieu() == null) {
            return "Thieu so ho chieu hoac ngay het han ho chieu";
        }
        LocalDate hanToiThieu = this.ngayDen.plusMonths(this.soThangHieuLucHoChieuToiThieu);
        if (khach.getNgayHetHanHoChieu().isBefore(hanToiThieu)) {
            return "Ho chieu khong du hieu luc toi thieu (" + this.soThangHieuLucHoChieuToiThieu + " thang sau ngay den)";
        }
        if (this.yeuCauKiemTraVisa) {
            if (khach.getSoVisa() == null || khach.getSoVisa().trim().isEmpty()) {
                return "Chuyen bay yeu cau Visa nhung khach hang chua co so visa";
            }
            if (khach.getNgayHetHanVisa() == null || khach.getNgayHetHanVisa().isBefore(this.ngayDen)) {
                return "Visa da het han hoac khong hop le cho ngay bay";
            }
        }
        return "";
    }

    public String getQuocGiaDen() { return quocGiaDen; }
    public void setQuocGiaDen(String quocGiaDen) { this.quocGiaDen = quocGiaDen; }

    public boolean isYeuCauKiemTraVisa() { return yeuCauKiemTraVisa; }
    public void setYeuCauKiemTraVisa(boolean yeuCauKiemTraVisa) { this.yeuCauKiemTraVisa = yeuCauKiemTraVisa; }

    public int getSoThangHieuLucHoChieuToiThieu() { return soThangHieuLucHoChieuToiThieu; }
    public void setSoThangHieuLucHoChieuToiThieu(int soThangHieuLucHoChieuToiThieu) { this.soThangHieuLucHoChieuToiThieu = soThangHieuLucHoChieuToiThieu; }

    @Override
    public String toString() {
        String dDi = (ngayDi != null) ? ngayDi.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "";
        String dDen = (ngayDen != null) ? ngayDen.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "";
        return String.format("%-8s | %-20s | QUOC_TE | %-5s -> %-5s (%s) | %s %s -> %s %s | Gia: %,.0f VND | Con: %d/%d | %s",
                maCB, tenChuyenBay, maSanBayDi, maSanBayDen, quocGiaDen, dDi, GioDi, dDen, GioDen, giaVeCoBan, soVeConLai, tongSoVeMoBan, trangThai);
    }
}
