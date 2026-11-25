package quanly;

import danhsach.DanhSachChuyenBay;
import model.*;
import tienich.NhapLieu;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

// Lop quan ly nghiep vu va giao dien console chuyen bay, su dung DanhSachChuyenBay de luu tru
public class QuanLyChuyenBay {
    private DanhSachChuyenBay dsCB;

    public QuanLyChuyenBay() {
        this.dsCB = new DanhSachChuyenBay();
    }

    public QuanLyChuyenBay(DanhSachChuyenBay dsCB) {
        this.dsCB = (dsCB != null) ? dsCB : new DanhSachChuyenBay();
    }

    public DanhSachChuyenBay getDsCB() { return dsCB; }
    public ArrayList<ChuyenBay> getDanhSachChuyenBay() { return dsCB.getDanhSach(); }
    public int getSoLuong() { return dsCB.getSoLuong(); }
    public ChuyenBay getChuyenBay(int index) { return dsCB.getChuyenBay(index); }
    public void themChuyenBay(ChuyenBay cb) { dsCB.themChuyenBay(cb); }
    public ChuyenBay timChuyenBay(String maCB) { return dsCB.timChuyenBay(maCB); }

    public boolean kiemTraTrungLich(String maMayBay, LocalDateTime startMoi, LocalDateTime endMoi, String maCBIgnore) {
        return dsCB.kiemTraTrungLich(maMayBay, startMoi, endMoi, maCBIgnore);
    }

    public boolean xoaChuyenBay(String maCB) {
        boolean ok = dsCB.xoaChuyenBay(maCB);
        if (ok) {
            System.out.println("Xoa chuyen bay " + maCB + " thanh cong!");
        } else {
            System.out.println("Loi: Khong tim thay chuyen bay " + maCB);
        }
        return ok;
    }

    public boolean xoaChuyenBay(String maCB, QuanLyVe qlVe, QuanLyHoaDon qlHD) {
        return xoaChuyenBay(maCB);
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
        boolean ok = dsCB.doiTrangThai(maCB);
        if (ok) {
            System.out.println("Chuyen bay " + maCB + " da chuyen sang trang thai: " + cb.getTrangThai());
        }
        return ok;
    }

    public ArrayList<ChuyenBay> layDanhSachChuyenBayKhaDung(String maSanBayDi, String maSanBayDen) {
        return dsCB.layDanhSachChuyenBayKhaDung(maSanBayDi, maSanBayDen);
    }

    public ArrayList<ChuyenBay> locChuyenBayDatVe(String maSanBayDi, String maSanBayDen, LocalDate ngayDi) {
        ArrayList<ChuyenBay> res = new ArrayList<ChuyenBay>();
        LocalDateTime now = LocalDateTime.now();
        for (ChuyenBay cb : dsCB.getDanhSach()) {
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
        dsCB.xuatDanhSach();
    }

    public void timKiemTheoMa(String maCB) {
        ChuyenBay cb = timChuyenBay(maCB);
        System.out.println(cb != null ? "Ket qua: " + cb : "Khong tim thay: " + maCB);
    }

    public void timKiemTheoTuyen(String sbDi, String sbDen) {
        System.out.println("\n---------------- TIM CHUYEN BAY " + sbDi + " -> " + sbDen + " ----------------");
        int d = 0;
        for (ChuyenBay cb : dsCB.getDanhSach()) {
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
        for (ChuyenBay cb : dsCB.getDanhSach()) {
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
        if (!start.isAfter(LocalDateTime.now())) {
            System.out.println("Loi: Thoi gian khoi hanh phai o tuong lai (sau thoi diem hien tai)!");
            return false;
        }
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
