package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

// Ke thua (Inheritance) tu lop truu tuong ChuyenBay
public class ChuyenBayNoiDia extends ChuyenBay {
    private boolean chapNhanVNEID2;
    private int DoTuoiEmBeToiThieu;

    public ChuyenBayNoiDia() {
        super();
    }

    public ChuyenBayNoiDia(String maCB, String tenChuyenBay, String maMayBay, String maHang,
                           String maSanBayDi, String maSanBayDen, LocalDate ngayDi, LocalTime GioDi,
                           LocalDate ngayDen, LocalTime GioDen, double giaVeCoBan, int tongSoVeMoBan,
                           int soVeDaBan, int soVeConLai, double tongDoanhThu, String trangThai, String ghiChu,
                           boolean chapNhanVNEID2, int DoTuoiEmBeToiThieu) {
        super(maCB, tenChuyenBay, maMayBay, maHang, maSanBayDi, maSanBayDen, ngayDi, GioDi, ngayDen, GioDen,
              giaVeCoBan, tongSoVeMoBan, soVeDaBan, soVeConLai, tongDoanhThu, trangThai, ghiChu);
        this.chapNhanVNEID2 = chapNhanVNEID2;
        this.DoTuoiEmBeToiThieu = DoTuoiEmBeToiThieu;
    }

    @Override
    public String getLoaiChuyen() {
        return "NOI_DIA";
    }

    // Ghi de phuong thuc truu tuong de the hien tinh da hinh (Polymorphism):
    // 1. Chuyen bay noi dia yeu cau hanh khach phai dat du so ngay tuoi toi thieu (em be phai >= DoTuoiEmBeToiThieu)
    // 2. Hanh khach tu 14 tuoi tro len bat buoc phai co so CCCD hoac dinh danh VNeID muc 2
    @Override
    public String kiemTraDieuKien(KhachHang khach) {
        if (khach == null) return "Khach hang khong ton tai";
        if (khach.getNgaySinh() == null) return "Ngay sinh khong duoc de trong";
        if (this.ngayDi == null) return "Chuyen bay chua xac dinh ngay di";
        long soNgayTuoi = ChronoUnit.DAYS.between(khach.getNgaySinh(), this.ngayDi);
        if (soNgayTuoi < this.DoTuoiEmBeToiThieu) {
            return "Chua du tuoi toi thieu (" + this.DoTuoiEmBeToiThieu + " ngay tuoi), hien tai: " + soNgayTuoi + " ngay";
        }
        if (soNgayTuoi >= 14 * 365) {
            if (khach.getSoCCCD() == null || khach.getSoCCCD().trim().isEmpty()) {
                return "Hanh khach tu 14 tuoi tro len bat buoc phai co so CCCD hoac tai khoan VNeID muc 2 de bay noi dia";
            }
        }
        return "";
    }

    public boolean isChapNhanVNEID2() { return chapNhanVNEID2; }
    public void setChapNhanVNEID2(boolean chapNhanVNEID2) { this.chapNhanVNEID2 = chapNhanVNEID2; }

    public int getDoTuoiEmBeToiThieu() { return DoTuoiEmBeToiThieu; }
    public void setDoTuoiEmBeToiThieu(int doTuoiEmBeToiThieu) { this.DoTuoiEmBeToiThieu = doTuoiEmBeToiThieu; }

    @Override
    public String toString() {
        String dDi = (ngayDi != null) ? ngayDi.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "";
        String dDen = (ngayDen != null) ? ngayDen.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "";
        return String.format("%-8s | %-20s | NOI_DIA | %-5s -> %-5s | %s %s -> %s %s | Gia: %,.0f VND | Con: %d/%d | %s",
                maCB, tenChuyenBay, maSanBayDi, maSanBayDen, dDi, GioDi, dDen, GioDen, giaVeCoBan, soVeConLai, tongSoVeMoBan, trangThai);
    }
}
