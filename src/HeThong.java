import model.*;
import quanly.*;
import tienich.*;
import java.time.LocalDateTime;

// Lop dieu phoi he thong va menu console
// Dong vai tro la Controller trung gian ket noi cac lop quan ly nghiep vu
public class HeThong {
    private QuanLyDanhMuc qlDanhMuc = new QuanLyDanhMuc();
    private QuanLyChuyenBay qlChuyenBay = new QuanLyChuyenBay();
    private QuanLyKhachHang qlKhachHang = new QuanLyKhachHang();
    private QuanLyNhanVien qlNhanVien = new QuanLyNhanVien();
    private QuanLyVe qlVe = new QuanLyVe();
    private QuanLyHoaDon qlHoaDon = new QuanLyHoaDon();
    private DocGhiFile docGhiFile = new DocGhiFile();

    public void khoiDong() {
        docGhiFile.khoiTaoDuLieu(qlDanhMuc, qlChuyenBay, qlKhachHang, qlNhanVien, qlVe, qlHoaDon);
        tinhLaiThongKe();
        chayMenuChinh();
    }

    // Tinh toan lai toan bo cac chi so thong ke (ve da ban, ve con lai, doanh thu, tong tien da mua)
    public void tinhLaiThongKe() {
        for (int i = 0; i < qlChuyenBay.getSoLuong(); i++) {
            ChuyenBay cb = qlChuyenBay.getChuyenBay(i);
            int daBan = qlVe.demVeHieuLucTheoChuyen(cb.getMaCB());
            cb.setSoVeDaBan(daBan);
            cb.setSoVeConLai(cb.getTongSoVeMoBan() - daBan);
            cb.setTongDoanhThu(qlHoaDon.tinhDoanhThuChuyenBay(cb.getMaCB()));
        }
        for (int i = 0; i < qlKhachHang.getSoLuong(); i++) {
            KhachHang kh = qlKhachHang.getKhachHang(i);
            kh.setTongSoVeDaMua(qlHoaDon.tinhSoVeKhachMua(kh.getMaKH()));
            kh.setTongTienDaMua(qlHoaDon.tinhTienKhachMua(kh.getMaKH()));
        }
        for (int i = 0; i < qlNhanVien.getSoLuong(); i++) {
            NhanVien nv = qlNhanVien.getNhanVien(i);
            nv.setTongSoHoaDonDaLap(qlHoaDon.demTongHoaDonNhanVien(nv.getMaNV()));
            nv.setTongSoVeDaBan(qlHoaDon.tinhSoVeNhanVienBan(nv.getMaNV()));
            nv.setTongTienDaBan(qlHoaDon.tinhTienNhanVienBan(nv.getMaNV()));
        }
    }

    private void chayMenuChinh() {
        while (true) {
            System.out.println("\n==================================================");
            System.out.println("                    MENU CHINH");
            System.out.println("==================================================");
            System.out.println("1. Dat ve chuyen bay (Khach hang tu chon tuyen & ghe)");
            System.out.println("2. Lap hoa don ban ve tai quay (Nhan vien lap theo mau)");
            System.out.println("3. Tra cuu thong tin (Ve, Hoa don)");
            System.out.println("4. Quan ly khach hang");
            System.out.println("5. Quan ly nhan vien");
            System.out.println("6. Quan ly chuyen bay");
            System.out.println("7. Xem danh muc he thong");
            System.out.println("8. Thong ke doanh thu & bao cao");
            System.out.println("9. Huy hoa don");
            System.out.println("10. Xuat hoa don ra file .txt");
            System.out.println("11. Luu du lieu he thong");
            System.out.println("0. Thoat chuong trinh");
            System.out.println("==================================================");
            int c = NhapLieu.nhapSoNguyen("Lua chon cua ban", 0, 11);
            if (c == 1) xuLyDatVe();
            else if (c == 2) {
                if (qlHoaDon.themHoaDonTheoMauChung(qlKhachHang, qlNhanVien, qlChuyenBay, qlVe)) {
                    tinhLaiThongKe();
                    docGhiFile.luuTatCa(qlDanhMuc, qlChuyenBay, qlKhachHang, qlNhanVien, qlVe, qlHoaDon);
                }
            } else if (c == 3) xuLyTraCuu();
            else if (c == 4) {
                qlKhachHang.menuQuanLy(qlHoaDon);
                tinhLaiThongKe();
                docGhiFile.luuTatCa(qlDanhMuc, qlChuyenBay, qlKhachHang, qlNhanVien, qlVe, qlHoaDon);
            } else if (c == 5) {
                qlNhanVien.menuQuanLy(qlHoaDon);
                tinhLaiThongKe();
                docGhiFile.luuTatCa(qlDanhMuc, qlChuyenBay, qlKhachHang, qlNhanVien, qlVe, qlHoaDon);
            } else if (c == 6) {
                qlChuyenBay.menuQuanLy(qlDanhMuc, qlVe, qlHoaDon);
                tinhLaiThongKe();
                docGhiFile.luuTatCa(qlDanhMuc, qlChuyenBay, qlKhachHang, qlNhanVien, qlVe, qlHoaDon);
            } else if (c == 7) xuLyXemDanhMuc();
            else if (c == 8) xuLyThongKe();
            else if (c == 9) xuLyHuyHoaDon();
            else if (c == 10) qlHoaDon.xuatHoaDonRaFile(NhapLieu.nhapChuoi("Nhap ma hoa don can xuat"), qlKhachHang, qlNhanVien, qlChuyenBay, qlVe);
            else if (c == 11) {
                tinhLaiThongKe();
                docGhiFile.luuTatCa(qlDanhMuc, qlChuyenBay, qlKhachHang, qlNhanVien, qlVe, qlHoaDon);
                System.out.println("Luu thanh cong!");
            } else if (c == 0) {
                tinhLaiThongKe();
                docGhiFile.luuTatCa(qlDanhMuc, qlChuyenBay, qlKhachHang, qlNhanVien, qlVe, qlHoaDon);
                System.out.println("Tam biet!");
                return;
            }
        }
    }

    // Quy trinh dat ve chuyen bay day du: Chon San bay di & den qua Quoc gia,
    // chon chuyen bay kha dung, xac minh dieu kien hanh khach, chon ghe tren so do khoang ghe
    private void xuLyDatVe() {
        System.out.println("\n==================================================");
        System.out.println("            QUY TRINH DAT VE CHUYEN BAY");
        System.out.println("==================================================");
        SanBay sbDi = qlDanhMuc.chonSanBayTungBuoc("xuat phat");
        if (sbDi == null) return;
        SanBay sbDen;
        while (true) {
            sbDen = qlDanhMuc.chonSanBayTungBuoc("ha canh");
            if (sbDen == null) return;
            if (!sbDen.getMaSanBay().equalsIgnoreCase(sbDi.getMaSanBay())) break;
            System.out.println("Loi: San bay den khong duoc trung san bay di!");
        }

        ChuyenBay cb = qlChuyenBay.chonChuyenBayDatVeConsole(sbDi.getMaSanBay(), sbDen.getMaSanBay());
        if (cb == null) return;

        KhachHang buyer = qlKhachHang.chonHoacThemKhachHang("CHON KHACH HANG MUA VE");
        if (buyer == null) return;
        NhanVien nv = qlNhanVien.chonNhanVienLapHoaDon();
        if (nv == null) return;

        int n = NhapLieu.nhapSoNguyen("Nhap so luong ve can mua (So ve con lai: " + cb.getSoVeConLai() + ")", 1, cb.getSoVeConLai());
        KhachHang[] dsKhachBay = new KhachHang[n];
        String[] dsSoGhe = new String[n];
        String[] dsHangVe = new String[n];
        String[] dsLoaiVe = new String[n];
        MayBay mb = qlDanhMuc.timMayBay(cb.getMaMayBay());

        int batDauTu = 0;
        boolean nguoiMuaCoBay = NhapLieu.nhapXacNhan("Nguoi mua (" + buyer.getHoTen() + ") co truc tiep bay tren chuyen nay khong?");
        if (nguoiMuaCoBay) {
            if (qlVe.kiemTraKhachDaCoVeChuyenBay(buyer.getMaKH(), cb.getMaCB(), qlHoaDon)) {
                System.out.println("Loi: Nguoi mua da co ve HIEU_LUC tren chuyen bay nay! Chuyen sang chon nguoi bay khac.");
            } else {
                String err = cb.kiemTraDieuKien(buyer);
                if (!err.isEmpty()) {
                    System.out.println("Nguoi mua khong du dieu kien bay: " + err + ". Chuyen sang chon nguoi bay khac.");
                } else {
                    dsKhachBay[0] = buyer;
                    System.out.println("\n---------------- VE THU 1 / " + n + " (Nguoi bay: " + buyer.getHoTen() + ") ----------------");
                    System.out.println("Chon loai ve cho " + buyer.getHoTen() + ":");
                    System.out.println("1. Nguoi lon (THUONG)");
                    System.out.println("2. Tre em (TRE_EM)");
                    int cLoai = NhapLieu.nhapSoNguyen("Lua chon cua ban", 1, 2);
                    dsLoaiVe[0] = (cLoai == 2) ? "TRE_EM" : "THUONG";

                    String ghe = qlVe.chonGheTrongConsole(cb.getMaCB(), mb, dsSoGhe, 0);
                    if (ghe == null) return;
                    dsSoGhe[0] = ghe;
                    dsHangVe[0] = qlVe.xacDinhHangVe(ghe);
                    System.out.println("-> Da chon ghe: " + ghe + " (Hang: " + dsHangVe[0] + ")");
                    batDauTu = 1;
                }
            }
        }

        for (int i = batDauTu; i < n; i++) {
            System.out.println("\n---------------- VE THU " + (i + 1) + " / " + n + " ----------------");
            KhachHang p;
            while (true) {
                p = qlKhachHang.chonHoacThemKhachHang("CHON NGUOI BAY CHO VE THU " + (i + 1));
                if (p == null) return;
                if (qlVe.kiemTraKhachDaCoVeChuyenBay(p.getMaKH(), cb.getMaCB(), qlHoaDon)) {
                    System.out.println("Loi: Khach da co ve HIEU_LUC tren chuyen bay nay!");
                    continue;
                }
                boolean daChon = false;
                for (int k = 0; k < i; k++) {
                    if (dsKhachBay[k].getMaKH().equalsIgnoreCase(p.getMaKH())) {
                        daChon = true;
                        break;
                    }
                }
                if (daChon) {
                    System.out.println("Loi: Khach nay da duoc chon cho mot ve khac trong lan mua nay!");
                    continue;
                }

                // The hien da hinh: kiem tra dieu kien bay tuong ung tung lop con
                String err = cb.kiemTraDieuKien(p);
                if (!err.isEmpty()) {
                    System.out.println("Tu choi: " + err);
                    continue;
                }
                break;
            }
            dsKhachBay[i] = p;

            System.out.println("Chon loai ve cho " + p.getHoTen() + ":");
            System.out.println("1. Nguoi lon (THUONG)");
            System.out.println("2. Tre em (TRE_EM)");
            int cLoai = NhapLieu.nhapSoNguyen("Lua chon cua ban", 1, 2);
            dsLoaiVe[i] = (cLoai == 2) ? "TRE_EM" : "THUONG";

            String ghe = qlVe.chonGheTrongConsole(cb.getMaCB(), mb, dsSoGhe, i);
            if (ghe == null) return;
            dsSoGhe[i] = ghe;
            dsHangVe[i] = qlVe.xacDinhHangVe(ghe);
            System.out.println("-> Da chon ghe: " + ghe + " (Hang: " + dsHangVe[i] + ")");
        }

        double tongTien = n * cb.getGiaVeCoBan();
        System.out.println("\n================ TOM TAT DON DAT VE ================");
        System.out.printf("Chuyen bay: %s | Nguoi mua: %s | Nhan vien lap: %s%n", cb.getMaCB(), buyer.getHoTen(), nv.getHoTen());
        System.out.printf("So luong: %d ve | Tong thanh toan: %,.0f VND%n", n, tongTien);
        for (int i = 0; i < n; i++) {
            System.out.printf("  Ve %d: %-20s | Ghe: %-4s | Hang: %-10s | Loai: %s%n",
                    (i + 1), dsKhachBay[i].getHoTen(), dsSoGhe[i], dsHangVe[i], dsLoaiVe[i]);
        }

        System.out.println("\nChon phuong thuc thanh toan:");
        System.out.println("1. Tien mat (TIEN_MAT)");
        System.out.println("2. Chuyen khoan (CHUYEN_KHOAN)");
        System.out.println("3. The (THE)");
        int pt = NhapLieu.nhapSoNguyen("Lua chon phuong thuc", 1, 3);
        String pttt = (pt == 2) ? "CHUYEN_KHOAN" : (pt == 3 ? "THE" : "TIEN_MAT");
        if (!NhapLieu.nhapXacNhan("Xac nhan dat ve?")) {
            System.out.println("Da huy thao tac dat ve.");
            return;
        }

        String maHD = qlHoaDon.sinhMaHD();
        String[] dsMaVe = new String[n];
        for (int i = 0; i < n; i++) {
            String mv = qlVe.sinhMaVe();
            dsMaVe[i] = mv;
            qlVe.themVe(new Ve(mv, cb.getMaCB(), dsSoGhe[i], dsHangVe[i], dsLoaiVe[i], cb.getGiaVeCoBan(), "HIEU_LUC"));
            qlHoaDon.themChiTiet(new ChiTietHoaDon(maHD, mv, dsKhachBay[i].getMaKH(), dsLoaiVe[i], cb.getGiaVeCoBan(), 1, cb.getGiaVeCoBan()));
        }
        qlHoaDon.themHoaDon(new HoaDon(maHD, buyer.getMaKH(), nv.getMaNV(), cb.getMaCB(), LocalDateTime.now(), n, tongTien, pttt, "HIEU_LUC"));
        tinhLaiThongKe();
        docGhiFile.luuTatCa(qlDanhMuc, qlChuyenBay, qlKhachHang, qlNhanVien, qlVe, qlHoaDon);
        System.out.println("\nDAT VE THANH CONG!");
        System.out.println("Ma hoa don: " + maHD);
        System.out.println("Danh sach ma ve: " + String.join(", ", dsMaVe));
    }


    private void xuLyTraCuu() {
        while (true) {
            System.out.println("\n==================================================");
            System.out.println("                TRA CUU THONG TIN");
            System.out.println("==================================================");
            System.out.println("1. Tra cuu chi tiet ve theo ma ve");
            System.out.println("2. Tra cuu hoa don theo ma hoa don");
            System.out.println("3. Tra cuu hoa don theo ma khach hang mua");
            System.out.println("4. Tim kiem nang cao hoa don theo khoang tien");
            System.out.println("0. Quay lai menu chinh");
            System.out.println("==================================================");
            int c = NhapLieu.nhapSoNguyen("Lua chon cua ban", 0, 4);
            if (c == 0) break;
            if (c == 1) qlVe.traCuuChiTietVe(NhapLieu.nhapChuoi("Nhap ma ve"), qlHoaDon, qlKhachHang, qlChuyenBay);
            else if (c == 2) {
                HoaDon hd = qlHoaDon.timHoaDon(NhapLieu.nhapChuoi("Nhap ma hoa don"));
                System.out.println(hd != null ? hd : "Khong tim thay hoa don!");
            } else if (c == 3) qlHoaDon.traCuuTheoKhachMua(NhapLieu.nhapChuoi("Nhap ma khach hang"));
            else if (c == 4) {
                double min = NhapLieu.nhapSoThuc("Nhap so tien toi thieu (vi du: 1000000)", 0, 1000000000);
                double max = NhapLieu.nhapSoThuc("Nhap so tien toi da (vi du: 100000000)", min, 1000000000);
                qlHoaDon.traCuuTheoKhoangTienConsole(min, max);
            }
        }
    }

    private void xuLyXemDanhMuc() {
        while (true) {
            System.out.println("\n==================================================");
            System.out.println("                XEM DANH MUC HE THONG");
            System.out.println("==================================================");
            System.out.println("1. Danh muc Quoc gia");
            System.out.println("2. Danh muc Dia diem");
            System.out.println("3. Danh muc San bay");
            System.out.println("4. Danh muc Hang hang khong");
            System.out.println("5. Danh muc May bay");
            System.out.println("0. Quay lai menu chinh");
            System.out.println("==================================================");
            int c = NhapLieu.nhapSoNguyen("Lua chon cua ban", 0, 5);
            if (c == 0) break;
            if (c == 1) qlDanhMuc.xemDanhSachQuocGia();
            else if (c == 2) qlDanhMuc.xemDanhSachDiaDiem();
            else if (c == 3) qlDanhMuc.xemDanhSachSanBay();
            else if (c == 4) qlDanhMuc.xemDanhSachHang();
            else if (c == 5) qlDanhMuc.xemDanhSachMayBay();
        }
    }

    private void xuLyThongKe() {
        tinhLaiThongKe();
        while (true) {
            System.out.println("\n==================================================");
            System.out.println("                THONG KE & BAO CAO");
            System.out.println("==================================================");
            System.out.println("1. Thong ke doanh thu theo chuyen bay (san pham)");
            System.out.println("2. Thong ke chi tiet hoa don & tong doanh thu (ThongKe)");
            System.out.println("0. Quay lai menu chinh");
            System.out.println("==================================================");
            int c = NhapLieu.nhapSoNguyen("Lua chon cua ban", 0, 2);
            if (c == 0) break;
            if (c == 1) {
                System.out.println("\n================ DOANH THU THEO CHUYEN BAY ================");
                for (int i = 0; i < qlChuyenBay.getSoLuong(); i++) {
                    ChuyenBay cb = qlChuyenBay.getChuyenBay(i);
                    System.out.printf("%-8s | %-20s | Ban: %-2d | Con: %-2d | Doanh thu: %,.0f VND%n",
                            cb.getMaCB(), cb.getTenChuyenBay(), cb.getSoVeDaBan(), cb.getSoVeConLai(), cb.getTongDoanhThu());
                }
            } else if (c == 2) {
                qlHoaDon.thongKeChiTietHoaDon();
                System.out.println("\n--- DOANH SO NHAN VIEN SALE ---");
                for (int i = 0; i < qlNhanVien.getSoLuong(); i++) {
                    NhanVien nv = qlNhanVien.getNhanVien(i);
                    System.out.printf("%-8s | %-20s | HD: %-2d | Ve: %-2d | Doanh so: %,.0f VND%n",
                            nv.getMaNV(), nv.getHoTen(), nv.getTongSoHoaDonDaLap(), nv.getTongSoVeDaBan(), nv.getTongTienDaBan());
                }
            }
        }
    }

    private void xuLyHuyHoaDon() {
        System.out.println("\n==================================================");
        System.out.println("                  HUY HOA DON");
        System.out.println("==================================================");
        if (qlHoaDon.huyHoaDon(NhapLieu.nhapChuoi("Nhap ma hoa don can huy"), qlChuyenBay, qlVe)) {
            tinhLaiThongKe();
            docGhiFile.luuTatCa(qlDanhMuc, qlChuyenBay, qlKhachHang, qlNhanVien, qlVe, qlHoaDon);
        }
    }
}


