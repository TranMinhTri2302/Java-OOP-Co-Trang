package quanly;

import model.*;
import tienich.NhapLieu;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

// Lop quan ly danh sach cac chuyen bay va cac nghiep vu kiem tra lich bay, chong gio
public class QuanLyChuyenBay {
    private ArrayList<ChuyenBay> danhSachChuyenBay = new ArrayList<ChuyenBay>();

    public ArrayList<ChuyenBay> getDanhSachChuyenBay() { return danhSachChuyenBay; }
    public int getSoLuong() { return danhSachChuyenBay.size(); }
    public ChuyenBay getChuyenBay(int index) { return (index >= 0 && index < danhSachChuyenBay.size()) ? danhSachChuyenBay.get(index) : null; }

    public void themChuyenBay(ChuyenBay cb) {
        if (cb != null && timChuyenBay(cb.getMaCB()) == null) danhSachChuyenBay.add(cb);
    }

    public ChuyenBay timChuyenBay(String maCB) {
        if (maCB == null) return null;
        for (ChuyenBay cb : danhSachChuyenBay) {
            if (maCB.equalsIgnoreCase(cb.getMaCB())) return cb;
        }
        return null;
    }

    // Kiem tra chong lich bay tren cung mot may bay:
    // Hai chuyen bay bi trung lich khi: start1 < end2 && start2 < end1
    public boolean kiemTraTrungLich(String maMayBay, LocalDateTime startMoi, LocalDateTime endMoi, String maCBIgnore) {
        for (ChuyenBay cb : danhSachChuyenBay) {
            if (cb.getMaCB().equalsIgnoreCase(maCBIgnore) || "HUY".equalsIgnoreCase(cb.getTrangThai())) continue;
            if (cb.getMaMayBay().equalsIgnoreCase(maMayBay)) {
                LocalDateTime startHienTai = LocalDateTime.of(cb.getNgayDi(), cb.getGioDi());
                LocalDateTime endHienTai = LocalDateTime.of(cb.getNgayDen(), cb.getGioDen());
                if (startMoi.isBefore(endHienTai) && startHienTai.isBefore(endMoi)) return true;
            }
        }
        return false;
    }

    // Rang buoc toan ven: chi duoc xoa chuyen bay khi chua co ve hoac hoa don nao phat sinh
    public boolean xoaChuyenBay(String maCB, QuanLyVe qlVe, QuanLyHoaDon qlHD) {
        ChuyenBay cb = timChuyenBay(maCB);
        if (cb == null) {
            System.out.println("Loi: Khong tim thay chuyen bay " + maCB);
            return false;
        }
        if (qlVe.kiemTraMaCBDaDung(maCB) || qlHD.kiemTraMaCBDaDung(maCB)) {
            System.out.println("Loi: Khong the xoa chuyen bay da co ve hoac hoa don tham chieu (ke ca da huy)!");
            return false;
        }
        danhSachChuyenBay.remove(cb);
        System.out.println("Xoa chuyen bay " + maCB + " thanh cong!");
        return true;
    }

    public boolean doiTrangThai(String maCB) {
        ChuyenBay cb = timChuyenBay(maCB);
        if (cb == null) {
            System.out.println("Loi: Khong tim thay chuyen bay!");
            return false;
        }
        if ("HUY".equalsIgnoreCase(cb.getTrangThai())) {
            System.out.println("Loi: Chuyen bay da bi huy!");
            return false;
        }
        String moi = "MO_BAN".equalsIgnoreCase(cb.getTrangThai()) ? "DONG_BAN" : "MO_BAN";
        cb.setTrangThai(moi);
        System.out.println("Chuyen bay " + maCB + " da chuyen sang trang thai: " + moi);
        return true;
    }

    // Lay tat ca cac chuyen bay kha dung cho mot tuyen bay o tuong lai
    public ArrayList<ChuyenBay> layDanhSachChuyenBayKhaDung(String maSanBayDi, String maSanBayDen) {
        ArrayList<ChuyenBay> res = new ArrayList<ChuyenBay>();
        LocalDateTime now = LocalDateTime.now();
        for (ChuyenBay cb : danhSachChuyenBay) {
            if (cb.getMaSanBayDi().equalsIgnoreCase(maSanBayDi) && cb.getMaSanBayDen().equalsIgnoreCase(maSanBayDen) &&
                "MO_BAN".equalsIgnoreCase(cb.getTrangThai()) && cb.getSoVeConLai() > 0) {
                if (LocalDateTime.of(cb.getNgayDi(), cb.getGioDi()).isAfter(now)) res.add(cb);
            }
        }
        return res;
    }

    // Loc chuyen bay hop le de ban ve (phai MO_BAN, con ve, va gio bay o tuong lai)
    public ArrayList<ChuyenBay> locChuyenBayDatVe(String maSanBayDi, String maSanBayDen, LocalDate ngayDi) {
        ArrayList<ChuyenBay> res = new ArrayList<ChuyenBay>();
        LocalDateTime now = LocalDateTime.now();
        for (ChuyenBay cb : danhSachChuyenBay) {
            if (cb.getMaSanBayDi().equalsIgnoreCase(maSanBayDi) && cb.getMaSanBayDen().equalsIgnoreCase(maSanBayDen) &&
                cb.getNgayDi().equals(ngayDi) && "MO_BAN".equalsIgnoreCase(cb.getTrangThai()) && cb.getSoVeConLai() > 0) {
                if (LocalDateTime.of(cb.getNgayDi(), cb.getGioDi()).isAfter(now)) res.add(cb);
            }
        }
        return res;
    }

    public ChuyenBay chonChuyenBayDatVeConsole(String maSanBayDi, String maSanBayDen) {
        ArrayList<ChuyenBay> ds = layDanhSachChuyenBayKhaDung(maSanBayDi, maSanBayDen);
        if (ds.isEmpty()) {
            System.out.println("Hien tai khong co chuyen bay nao con ve cho tuyen bay nay o thoi diem tuong lai!");
            System.out.println("(Goi y: Ban co the them chuyen bay moi tai Menu 5 -> 5. Them chuyen bay moi)");
            return null;
        }

        System.out.println("\n================ CAC CHUYEN BAY KHA DUNG CHO TUYEN [" + maSanBayDi + " -> " + maSanBayDen + "] ================");
        for (int i = 0; i < ds.size(); i++) {
            ChuyenBay cb = ds.get(i);
            System.out.printf("[%d] %-8s | %-18s | Ngay: %s (%s - %s) | %,.0f VND | Con: %d/%d%n",
                    (i + 1), cb.getMaCB(), cb.getTenChuyenBay(), NhapLieu.dinhDangNgay(cb.getNgayDi()), cb.getGioDi(), cb.getGioDen(),
                    cb.getGiaVeCoBan(), cb.getSoVeConLai(), cb.getTongSoVeMoBan());
        }
        System.out.println("[0] Loc theo ngay bay cu the");
        System.out.println("==========================================================================================");

        int stt = NhapLieu.nhapSoNguyen("Chon STT chuyen bay ban muon dat (hoac 0 de loc theo ngay)", 0, ds.size());
        if (stt > 0) return ds.get(stt - 1);

        while (true) {
            LocalDate ngayDi = NhapLieu.nhapNgayCoTheRong("Nhap ngay bay muon tim (bo trong de thoat)");
            if (ngayDi == null) return null;
            ArrayList<ChuyenBay> theoNgay = locChuyenBayDatVe(maSanBayDi, maSanBayDen, ngayDi);
            if (theoNgay.isEmpty()) {
                System.out.println("Khong co chuyen bay phu hop vao ngay " + NhapLieu.dinhDangNgay(ngayDi) + ". Vui long thu ngay khac!");
                continue;
            }
            System.out.println("\n---------------- CAC CHUYEN BAY NGAY " + NhapLieu.dinhDangNgay(ngayDi) + " ----------------");
            for (int i = 0; i < theoNgay.size(); i++) {
                ChuyenBay cb = theoNgay.get(i);
                System.out.printf("[%d] %-8s | %-18s | %s -> %s | %,.0f VND | Con: %d/%d%n",
                        (i + 1), cb.getMaCB(), cb.getTenChuyenBay(), cb.getGioDi(), cb.getGioDen(),
                        cb.getGiaVeCoBan(), cb.getSoVeConLai(), cb.getTongSoVeMoBan());
            }
            int pick = NhapLieu.nhapSoNguyen("Chon STT chuyen bay (0 de tim ngay khac)", 0, theoNgay.size());
            if (pick > 0) return theoNgay.get(pick - 1);
        }
    }

    public void hienThiDanhSach() {
        System.out.println("\n================ DANH SACH CHUYEN BAY ================");
        if (danhSachChuyenBay.isEmpty()) {
            System.out.println("Danh sach trong.");
            return;
        }
        for (int i = 0; i < danhSachChuyenBay.size(); i++) {
            System.out.printf("[%d] %s%n", (i + 1), danhSachChuyenBay.get(i));
        }
    }

    public void timKiemTheoMa(String maCB) {
        ChuyenBay cb = timChuyenBay(maCB);
        System.out.println(cb != null ? "Ket qua: " + cb : "Khong tim thay: " + maCB);
    }

    public void timKiemTheoTuyen(String sbDi, String sbDen) {
        System.out.println("\n---------------- TIM CHUYEN BAY " + sbDi + " -> " + sbDen + " ----------------");
        int d = 0;
        for (ChuyenBay cb : danhSachChuyenBay) {
            if (cb.getMaSanBayDi().equalsIgnoreCase(sbDi) && cb.getMaSanBayDen().equalsIgnoreCase(sbDen)) {
                System.out.println(cb);
                d++;
            }
        }
        if (d == 0) System.out.println("Khong co chuyen bay phu hop.");
    }

    public void timKiemTheoNgay(LocalDate ngayDi) {
        System.out.println("\n---------------- TIM CHUYEN BAY NGAY " + ngayDi + " ----------------");
        int d = 0;
        for (ChuyenBay cb : danhSachChuyenBay) {
            if (cb.getNgayDi().equals(ngayDi)) {
                System.out.println(cb);
                d++;
            }
        }
        if (d == 0) System.out.println("Khong co chuyen bay trong ngay.");
    }

    public boolean themChuyenBayConsole(QuanLyDanhMuc qlDM) {
        System.out.println("\n---------------- THEM CHUYEN BAY MOI ----------------");
        String maCB = NhapLieu.nhapChuoi("Nhap ma CB (vi du: VN789)");
        if (timChuyenBay(maCB) != null) {
            System.out.println("Loi: Ma da ton tai tren he thong!");
            return false;
        }
        String tenCB = NhapLieu.nhapChuoi("Nhap ten CB");

        String maMB = NhapLieu.nhapChuoi("Nhap ma MB (vi du: VN-A321)");
        MayBay mb = qlDM.timMayBay(maMB);
        if (mb == null || !"SAN_SANG".equalsIgnoreCase(mb.getTrangThai())) {
            System.out.println("Loi: May bay khong hop le hoac khong o trang thai SAN_SANG!");
            return false;
        }
        String maHang = mb.getMaHang();

        String sbDi = NhapLieu.nhapChuoi("Nhap ma san bay di");
        String sbDen = NhapLieu.nhapChuoi("Nhap ma san bay den");
        SanBay s1 = qlDM.timSanBay(sbDi);
        SanBay s2 = qlDM.timSanBay(sbDen);
        if (s1 == null || s2 == null || sbDi.equalsIgnoreCase(sbDen) || !s1.isTrangThaiHoatDong() || !s2.isTrangThaiHoatDong()) {
            System.out.println("Loi: San bay di va den khong hop le hoac trung nhau!");
            return false;
        }
        QuocGia qgDi = qlDM.layQuocGiaCuaSanBay(sbDi);
        QuocGia qgDen = qlDM.layQuocGiaCuaSanBay(sbDen);
        if (qgDi == null || qgDen == null) {
            System.out.println("Loi: Khong xac dinh duoc quoc gia cua san bay!");
            return false;
        }
        boolean laNoiDia = qgDi.getMaQuocGia().equalsIgnoreCase(qgDen.getMaQuocGia());

        LocalDate ngayDi = NhapLieu.nhapNgay("Nhap ngay di");
        LocalTime gioDi = NhapLieu.nhapGio("Nhap gio di");
        LocalDate ngayDen = NhapLieu.nhapNgay("Nhap ngay den");
        LocalTime gioDen = NhapLieu.nhapGio("Nhap gio den");
        LocalDateTime start = LocalDateTime.of(ngayDi, gioDi);
        LocalDateTime end = LocalDateTime.of(ngayDen, gioDen);
        if (!end.isAfter(start) || kiemTraTrungLich(maMB, start, end, maCB)) {
            System.out.println("Loi: Thoi diem den phai sau ngay di hoac may bay bi trung lich!");
            return false;
        }

        double giaVe = NhapLieu.nhapSoThuc("Nhap gia ve co ban", 0, 1000000000);
        int tongVe = NhapLieu.nhapSoNguyen("Nhap tong so ve mo ban", 1, mb.getTongSoGhe());
        String ghiChu = NhapLieu.nhapChuoiCoTheRong("Nhap ghi chu");

        ChuyenBay cbMoi = laNoiDia ?
            new ChuyenBayNoiDia(maCB, tenCB, maMB, maHang, sbDi, sbDen, ngayDi, gioDi, ngayDen, gioDen,
                    giaVe, tongVe, 0, tongVe, 0.0, "MO_BAN", ghiChu,
                    NhapLieu.nhapXacNhan("Chap nhan VNeID muc 2?"),
                    NhapLieu.nhapSoNguyen("Tuoi em be toi thieu (ngay)", 0, 365)) :
            new ChuyenBayQuocTe(maCB, tenCB, maMB, maHang, sbDi, sbDen, ngayDi, gioDi, ngayDen, gioDen,
                    giaVe, tongVe, 0, tongVe, 0.0, "MO_BAN", ghiChu, qgDen.getTenQuocGia(),
                    NhapLieu.nhapXacNhan("Yeu cau Visa?"),
                    NhapLieu.nhapSoNguyen("So thang hieu luc ho chieu toi thieu", 1, 36));

        themChuyenBay(cbMoi);
        System.out.println("Them chuyen bay moi thanh cong!");
        return true;
    }

    public boolean suaChuyenBayConsole(QuanLyDanhMuc qlDM, QuanLyVe qlVe) {
        String maCB = NhapLieu.nhapChuoi("Nhap ma chuyen bay can sua");
        ChuyenBay cb = timChuyenBay(maCB);
        if (cb == null) {
            System.out.println("Khong tim thay: " + maCB);
            return false;
        }
        System.out.println("Thong tin hien tai: " + cb);

        String ten = NhapLieu.nhapChuoiCoTheRong("Ten chuyen (" + cb.getTenChuyenBay() + ")");
        if (!ten.isEmpty()) cb.setTenChuyenBay(ten);
        double giaMoi = NhapLieu.nhapSoThuc("Gia ve moi (0 de giu nguyen)", 0, 1000000000);
        if (giaMoi > 0) cb.setGiaVeCoBan(giaMoi);

        MayBay mb = qlDM.timMayBay(cb.getMaMayBay());
        int maxGhe = mb != null ? mb.getTongSoGhe() : 300;
        int minVe = cb.getSoVeDaBan() > 0 ? cb.getSoVeDaBan() : 1;
        int veMoi = NhapLieu.nhapSoNguyen("Tong ve moi (min " + minVe + ", max " + maxGhe + ")", minVe, maxGhe);
        cb.setTongSoVeMoBan(veMoi);
        cb.setSoVeConLai(veMoi - cb.getSoVeDaBan());

        String gc = NhapLieu.nhapChuoiCoTheRong("Ghi chu (" + cb.getGhiChu() + ")");
        if (!gc.isEmpty()) cb.setGhiChu(gc);
        System.out.println("Cap nhat chuyen bay thanh cong!");
        return true;
    }

    public void menuQuanLy(QuanLyDanhMuc qlDM, QuanLyVe qlVe, QuanLyHoaDon qlHD) {
        while (true) {
            System.out.println("\n==================================================");
            System.out.println("               QUAN LY CHUYEN BAY");
            System.out.println("==================================================");
            System.out.println("1. Xem danh sach chuyen bay");
            System.out.println("2. Tim kiem theo ma chuyen bay");
            System.out.println("3. Tim kiem theo tuyen bay (SB di - SB den)");
            System.out.println("4. Tim kiem theo ngay khoi hanh");
            System.out.println("5. Them chuyen bay moi");
            System.out.println("6. Sua thong tin chuyen bay");
            System.out.println("7. Xoa chuyen bay");
            System.out.println("8. Thay doi trang thai (MO_BAN / DONG_BAN)");
            System.out.println("0. Quay lai menu chinh");
            System.out.println("==================================================");
            int c = NhapLieu.nhapSoNguyen("Lua chon cua ban", 0, 8);
            if (c == 0) break;
            if (c == 1) hienThiDanhSach();
            else if (c == 2) timKiemTheoMa(NhapLieu.nhapChuoi("Nhap ma chuyen bay"));
            else if (c == 3) timKiemTheoTuyen(NhapLieu.nhapChuoi("Ma san bay di"), NhapLieu.nhapChuoi("Ma san bay den"));
            else if (c == 4) timKiemTheoNgay(NhapLieu.nhapNgay("Ngay di"));
            else if (c == 5) themChuyenBayConsole(qlDM);
            else if (c == 6) suaChuyenBayConsole(qlDM, qlVe);
            else if (c == 7) xoaChuyenBay(NhapLieu.nhapChuoi("Ma can xoa"), qlVe, qlHD);
            else if (c == 8) doiTrangThai(NhapLieu.nhapChuoi("Ma chuyen bay can doi trang thai"));
        }
    }
}
