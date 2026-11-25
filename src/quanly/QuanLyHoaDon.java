package quanly;

import danhsach.DanhSachHoaDon;
import model.*;
import tienich.NhapLieu;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

// Lop quan ly nghiep vu va giao dien console hoa don, su dung DanhSachHoaDon de luu tru
public class QuanLyHoaDon {
    private DanhSachHoaDon dsHD;

    public QuanLyHoaDon() {
        this.dsHD = new DanhSachHoaDon();
    }

    public QuanLyHoaDon(DanhSachHoaDon dsHD) {
        this.dsHD = (dsHD != null) ? dsHD : new DanhSachHoaDon();
    }

    public DanhSachHoaDon getDsHD() { return dsHD; }
    public ArrayList<HoaDon> getDanhSachHoaDon() { return dsHD.getDanhSachHoaDon(); }
    public ArrayList<ChiTietHoaDon> getDanhSachChiTiet() { return dsHD.getDanhSachChiTiet(); }
    public int getSoLuong() { return dsHD.getSoLuong(); }
    public HoaDon getHoaDon(int index) { return dsHD.getHoaDon(index); }

    public void themHoaDon(HoaDon hd) { dsHD.themHoaDon(hd); }
    public void themChiTiet(ChiTietHoaDon ct) { dsHD.themChiTiet(ct); }
    public HoaDon timHoaDon(String maHD) { return dsHD.timHoaDon(maHD); }
    public ChiTietHoaDon timChiTietTheoVe(String maVe) { return dsHD.timChiTietTheoVe(maVe); }
    public String sinhMaHD() { return dsHD.sinhMaHD(); }

    public boolean kiemTraMaKHDaDung(String maKH) { return dsHD.kiemTraMaKHDaDung(maKH); }
    public boolean kiemTraMaNVDaDung(String maNV) { return dsHD.kiemTraMaNVDaDung(maNV); }
    public boolean kiemTraMaCBDaDung(String maCB) { return dsHD.kiemTraMaCBDaDung(maCB); }

    public double tinhDoanhThuChuyenBay(String maCB) { return dsHD.tinhDoanhThuChuyenBay(maCB); }
    public int tinhSoVeKhachMua(String maKH) { return dsHD.tinhSoVeKhachMua(maKH); }
    public double tinhTienKhachMua(String maKH) { return dsHD.tinhTienKhachMua(maKH); }
    public int demTongHoaDonNhanVien(String maNV) { return dsHD.demTongHoaDonNhanVien(maNV); }
    public int tinhSoVeNhanVienBan(String maNV) { return dsHD.tinhSoVeNhanVienBan(maNV); }
    public double tinhTienNhanVienBan(String maNV) { return dsHD.tinhTienNhanVienBan(maNV); }

    // Nghiep vu huy hoa don: chuyen trang thai HoaDon va cac Ve lien quan thanh DA_HUY
    // Giai phong ghe de khach khac co the dat
    public boolean huyHoaDon(String maHD, QuanLyChuyenBay qlCB, QuanLyVe qlVe) {
        HoaDon hd = timHoaDon(maHD);
        if (hd == null || !"HIEU_LUC".equalsIgnoreCase(hd.getTrangThai())) {
            System.out.println("Loi: Khong tim thay hoa don hoac hoa don khong o trang thai HIEU_LUC!");
            return false;
        }
        ChuyenBay cb = qlCB.timChuyenBay(hd.getMaCB());
        if (cb != null && !LocalDateTime.of(cb.getNgayDi(), cb.getGioDi()).isAfter(LocalDateTime.now())) {
            System.out.println("Loi: Chuyen bay da khoi hanh, khong the huy hoa don!");
            return false;
        }
        hd.setTrangThai("DA_HUY");
        for (ChiTietHoaDon ct : dsHD.getDanhSachChiTiet()) {
            if (ct.getMaHD().equalsIgnoreCase(maHD)) {
                Ve v = qlVe.timVe(ct.getMaVe());
                if (v != null) v.setTrangThai("DA_HUY");
            }
        }
        System.out.println("Huy hoa don " + maHD + " thanh cong! (Ghe da duoc tra lai trang thai trong)");
        return true;
    }

    // Chuyen toan bo luong giao dien xu ly huy hoa don vao dung QuanLyHoaDon theo yeu cau cua co
    public boolean xuLyHuyHoaDon(QuanLyChuyenBay qlCB, QuanLyVe qlVe) {
        System.out.println("\n==================================================");
        System.out.println("                  HUY HOA DON");
        System.out.println("==================================================");
        String maHD = NhapLieu.nhapChuoi("Nhap ma hoa don can huy");
        return huyHoaDon(maHD, qlCB, qlVe);
    }

    // Thong ke chi tiet hoa don va tong doanh thu thuc te su dung interface ThongKe
    public void thongKeChiTietHoaDon() {
        System.out.println("\n================ DANH SACH CHI TIET CAC HOA DON ================");
        if (dsHD.getDanhSachChiTiet().isEmpty()) {
            System.out.println("Chua co chi tiet hoa don nao.");
        } else {
            for (int i = 0; i < dsHD.getDanhSachChiTiet().size(); i++) {
                System.out.printf("[%d] %s%n", (i + 1), dsHD.getDanhSachChiTiet().get(i));
            }
        }
        int demHieuLuc = 0;
        double tongTien = 0;
        int tongVe = 0;
        for (HoaDon hd : dsHD.getDanhSachHoaDon()) {
            if ("HIEU_LUC".equalsIgnoreCase(hd.getTrangThai())) {
                demHieuLuc++;
                tongVe += hd.getSoLuongVe();
            }
            // Ap dung da hinh (Polymorphism): bien kieu interface ThongKe tro toi doi tuong HoaDon
            ThongKe tk = hd;
            tongTien += tk.tinhTongTien();
        }
        System.out.println("--------------------------------------------------------------------------------------------------");
        System.out.printf("Tong so hoa don hieu luc  : %d%n", demHieuLuc);
        System.out.printf("Tong so ve / chi tiet     : %d ve%n", tongVe);
        System.out.printf("Tong doanh thu thuc te    : %,.0f VND (tinh qua interface ThongKe)%n", tongTien);
        System.out.println("==================================================================================================");
    }

    // Tim kiem nang cao: loc hoa don theo khoang tien
    public ArrayList<HoaDon> timHoaDonTheoKhoangTien(double minTien, double maxTien) {
        return dsHD.timHoaDonTheoKhoangTien(minTien, maxTien);
    }

    public void traCuuTheoKhoangTienConsole(double minTien, double maxTien) {
        ArrayList<HoaDon> ds = timHoaDonTheoKhoangTien(minTien, maxTien);
        System.out.printf("\n------- DANH SACH HOA DON TU %,.0f VND DEN %,.0f VND (Tong: %d) -------%n", minTien, maxTien, ds.size());
        if (ds.isEmpty()) {
            System.out.println("Khong co hoa don nao phu hop.");
            return;
        }
        for (HoaDon hd : ds) System.out.println(hd);
    }

    public void traCuuTheoKhachMua(String maKH) {
        System.out.println("\n---------------- HOA DON CUA KHACH HANG: " + maKH + " ----------------");
        int dem = 0;
        for (HoaDon hd : dsHD.getDanhSachHoaDon()) {
            if (hd.getMaKH().equalsIgnoreCase(maKH)) {
                System.out.println(hd);
                dem++;
            }
        }
        if (dem == 0) System.out.println("Khong tim thay hoa don nao.");
    }

    // Tinh tong doanh thu 3 nam gan nhat qua interface ThongKe
    public double tinhTongDoanhThu3Nam(int namHienTai) {
        return dsHD.tinhTongDoanhThu3NamGanNhat(namHienTai);
    }

    // Thong ke doanh thu 3 nam gan nhat co day du ngay thang theo dung cau hoi van dap cua co
    public void thongKeDoanhThu3NamGanNhatConsole(int namHienTai) {
        System.out.println("\n================ THONG KE DOANH THU 3 NAM GAN NHAT (" + (namHienTai - 2) + " - " + namHienTai + ") ================");
        int namBatDau = namHienTai - 2;
        double tong3Nam = 0;
        int tongVe3Nam = 0;
        int tongHD3Nam = 0;

        for (int y = namBatDau; y <= namHienTai; y++) {
            double tongNam = 0;
            int soHDNam = 0;
            int soVeNam = 0;
            System.out.println("\n--- NAM " + y + " ---");
            boolean coDuLieu = false;
            for (HoaDon hd : dsHD.getDanhSachHoaDon()) {
                if ("HIEU_LUC".equalsIgnoreCase(hd.getTrangThai()) && hd.getNgayLap() != null && hd.getNgayLap().getYear() == y) {
                    coDuLieu = true;
                    soHDNam++;
                    soVeNam += hd.getSoLuongVe();
                    ThongKe tk = hd;
                    tongNam += tk.tinhTongTien();
                    System.out.printf("  HD: %-8s | Ngay lap: %s | Chuyen: %-6s | Ve: %-2d | Tien: %,.0f VND%n",
                            hd.getMaHD(), NhapLieu.dinhDangThoiGian(hd.getNgayLap()), hd.getMaCB(), hd.getSoLuongVe(), hd.getTongTien());
                }
            }
            if (!coDuLieu) {
                System.out.println("  (Khong co hoa don nao trong nam " + y + ")");
            } else {
                System.out.printf("  => Tong nam %d: %d hoa don | %d ve | Doanh thu: %,.0f VND%n", y, soHDNam, soVeNam, tongNam);
            }
            tong3Nam += tongNam;
            tongVe3Nam += soVeNam;
            tongHD3Nam += soHDNam;
        }
        System.out.println("--------------------------------------------------------------------------------------------------");
        System.out.printf("TONG CONG 3 NAM (%d - %d): %d hoa don | %d ve | %,.0f VND (tinh qua ThongKe)%n",
                namBatDau, namHienTai, tongHD3Nam, tongVe3Nam, tong3Nam);
        System.out.println("==================================================================================================");
    }

    // Menu tra cuu thong tin hoa don va ve
    public void menuTraCuu(QuanLyVe qlVe, QuanLyKhachHang qlKH, QuanLyChuyenBay qlCB) {
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
            if (c == 1) qlVe.traCuuChiTietVe(NhapLieu.nhapChuoi("Nhap ma ve"), this, qlKH, qlCB);
            else if (c == 2) {
                HoaDon hd = timHoaDon(NhapLieu.nhapChuoi("Nhap ma hoa don"));
                System.out.println(hd != null ? hd : "Khong tim thay hoa don!");
            } else if (c == 3) traCuuTheoKhachMua(NhapLieu.nhapChuoi("Nhap ma khach hang"));
            else if (c == 4) {
                double min = NhapLieu.nhapSoThuc("Nhap so tien toi thieu (vi du: 1000000)", 0, 1000000000);
                double max = NhapLieu.nhapSoThuc("Nhap so tien toi da (vi du: 100000000)", min, 1000000000);
                traCuuTheoKhoangTienConsole(min, max);
            }
        }
    }

    // Menu thong ke va bao cao
    public void menuThongKe(QuanLyChuyenBay qlCB, QuanLyNhanVien qlNV) {
        while (true) {
            System.out.println("\n==================================================");
            System.out.println("                THONG KE & BAO CAO");
            System.out.println("==================================================");
            System.out.println("1. Thong ke doanh thu theo chuyen bay (san pham)");
            System.out.println("2. Thong ke chi tiet hoa don & tong doanh thu (ThongKe)");
            System.out.println("3. Thong ke doanh thu 3 nam gan nhat (co ngay thang)");
            System.out.println("0. Quay lai menu chinh");
            System.out.println("==================================================");
            int c = NhapLieu.nhapSoNguyen("Lua chon cua ban", 0, 3);
            if (c == 0) break;
            if (c == 1) {
                System.out.println("\n================ DOANH THU THEO CHUYEN BAY ================");
                for (int i = 0; i < qlCB.getSoLuong(); i++) {
                    ChuyenBay cb = qlCB.getChuyenBay(i);
                    System.out.printf("%-8s | %-20s | Ban: %-2d | Con: %-2d | Doanh thu: %,.0f VND%n",
                            cb.getMaCB(), cb.getTenChuyenBay(), cb.getSoVeDaBan(), cb.getSoVeConLai(), cb.getTongDoanhThu());
                }
            } else if (c == 2) {
                thongKeChiTietHoaDon();
                System.out.println("\n--- DOANH SO NHAN VIEN SALE ---");
                for (int i = 0; i < qlNV.getSoLuong(); i++) {
                    NhanVien nv = qlNV.getNhanVien(i);
                    System.out.printf("%-8s | %-20s | HD: %-2d | Ve: %-2d | Doanh so: %,.0f VND%n",
                            nv.getMaNV(), nv.getHoTen(), nv.getTongSoHoaDonDaLap(), nv.getTongSoVeDaBan(), nv.getTongTienDaBan());
                }
            } else if (c == 3) {
                int nam = NhapLieu.nhapSoNguyen("Nhap nam can thong ke (vi du: 2026)", 2000, 2100);
                thongKeDoanhThu3NamGanNhatConsole(nam);
            }
        }
    }

    // Ham lap hoa don co ban tai quay theo yeu cau cua co
    public boolean themHoaDonTheoMauChung(QuanLyKhachHang qlKH, QuanLyNhanVien qlNV, QuanLyChuyenBay qlCB, QuanLyVe qlVe) {
        System.out.println("\n==================================================");
        System.out.println("     LAP HOA DON BAN VE TAI QUAY (NHAN VIEN)");
        System.out.println("==================================================");

        NhanVien nv = qlNV.chonNhanVienLapHoaDon();
        if (nv == null) return false;
        String maNV = nv.getMaNV();

        String maHDMacDinh = sinhMaHD();
        String maHD = NhapLieu.nhapChuoiCoTheRong("Nhap ma hoa don (Enter de lay tu dong: " + maHDMacDinh + ")");
        if (maHD.isEmpty()) maHD = maHDMacDinh;
        while (timHoaDon(maHD) != null) {
            System.out.println("Loi: Ma hoa don " + maHD + " da ton tai!");
            maHD = NhapLieu.nhapChuoi("Nhap lai ma hoa don");
        }

        String maKH;
        while (true) {
            maKH = NhapLieu.nhapChuoi("Nhap ma khach hang (vi du: KH01)");
            if (qlKH.timKhachHang(maKH) != null) break;
            System.out.println("Loi: Khach hang " + maKH + " chua ton tai! Vui long nhap ma KH hop le.");
        }

        LocalDate d = NhapLieu.nhapNgayCoTheRong("Nhap ngay lap hoa don");
        LocalDateTime ngayLap = (d != null) ? d.atTime(LocalTime.now().getHour(), LocalTime.now().getMinute()) : LocalDateTime.now();

        double tongTien = 0.0;
        int tongSoVe = 0;

        int soLuongChiTiet = NhapLieu.nhapSoNguyen("Nhap so luong chi tiet hoa don", 1, 50);

        String maCBLuu = "";
        for (int i = 1; i <= soLuongChiTiet; i++) {
            System.out.println("\n--- Chi tiet hoa don thu " + i + " / " + soLuongChiTiet + " ---");
            ChuyenBay sp;
            while (true) {
                String maSP = NhapLieu.nhapChuoi("Nhap ma san pham (ma chuyen bay, vi du: VN123, VN125)");
                sp = qlCB.timChuyenBay(maSP);
                if (sp != null && sp.getSoVeConLai() > 0) break;
                System.out.println("Loi: San pham khong ton tai hoac da het ve!");
            }
            maCBLuu = sp.getMaCB();

            int soLuong = NhapLieu.nhapSoNguyen("Nhap so luong", 1, sp.getSoVeConLai());
            tongSoVe += soLuong;

            double donGia = sp.getGiaVeCoBan();
            double thanhTien = donGia * soLuong;
            tongTien += thanhTien;

            for (int s = 1; s <= soLuong; s++) {
                String maVe = qlVe.sinhMaVe();
                qlVe.themVe(new Ve(maVe, sp.getMaCB(), "A" + i + "-" + s, "PHO_THONG", "THUONG", donGia, "HIEU_LUC"));
                this.themChiTiet(new ChiTietHoaDon(maHD, maVe, maKH, "THUONG", donGia, 1, donGia));
            }

            sp.setSoVeDaBan(sp.getSoVeDaBan() + soLuong);
            sp.setSoVeConLai(sp.getTongSoVeMoBan() - sp.getSoVeDaBan());

            System.out.printf("-> Da them chi tiet: SP: %s | Don gia: %,.0f VND | SL: %d | Thanh tien: %,.0f VND%n",
                    sp.getMaCB(), donGia, soLuong, thanhTien);
        }

        this.themHoaDon(new HoaDon(maHD, maKH, maNV, maCBLuu, ngayLap, tongSoVe, tongTien, "TIEN_MAT", "HIEU_LUC"));
        System.out.println("\nTHEM HOA DON THEO MAU THANH CONG!");
        System.out.printf("Ma HD: %s | Ma KH: %s | NV: %s | So luong chi tiet: %d | Tong so ve: %d | Tong tien: %,.0f VND%n",
                maHD, maKH, nv.getHoTen(), soLuongChiTiet, tongSoVe, tongTien);
        return true;
    }

    // Xuat hoa don ra tap tin van ban .txt voi dinh dang ro rang, dep mat
    public boolean xuatHoaDonRaFile(String maHD, QuanLyKhachHang qlKH, QuanLyNhanVien qlNV, QuanLyChuyenBay qlCB, QuanLyVe qlVe) {
        HoaDon hd = timHoaDon(maHD);
        if (hd == null) { System.out.println("Loi: Khong tim thay hoa don " + maHD); return false; }
        try {
            String expDir = (new File("../exports").exists() || new File("Main.java").exists()) ? "../exports" : "exports";
            Files.createDirectories(Paths.get(expDir));
            String filePath = expDir + "/" + maHD + ".txt";
            BufferedWriter w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8));
            KhachHang buyer = qlKH.timKhachHang(hd.getMaKH());
            NhanVien nv = qlNV.timNhanVien(hd.getMaNV());
            ChuyenBay cb = qlCB.timChuyenBay(hd.getMaCB());

            w.write("========================================================================\r\n"
                  + "                           HOA DON BAN VE MAY BAY                       \r\n"
                  + "========================================================================\r\n"
                  + String.format("Ma hoa don     : %s\r\nNgay lap       : %s\r\nTrang thai     : %s\r\nHinh thuc TT   : %s\r\n", hd.getMaHD(), NhapLieu.dinhDangThoiGian(hd.getNgayLap()), hd.getTrangThai(), hd.getPhuongThucThanhToan())
                  + "------------------------------------------------------------------------\r\nTHONG TIN NGUOI MUA:\r\n"
                  + (buyer != null ? String.format("  Ma KH: %s | Ho ten: %s | SDT: %s | Email: %s\r\n", buyer.getMaKH(), buyer.getHoTen(), buyer.getSoDienThoai(), buyer.getEmail()) : "  Ma KH: " + hd.getMaKH() + "\r\n")
                  + "------------------------------------------------------------------------\r\nTHONG TIN NHAN VIEN LAP:\r\n"
                  + (nv != null ? String.format("  Ma NV: %s - %s (%s)\r\n", nv.getMaNV(), nv.getHoTen(), nv.getChucVu()) : "  Ma NV: " + hd.getMaNV() + "\r\n")
                  + "------------------------------------------------------------------------\r\nTHONG TIN CHUYEN BAY:\r\n"
                  + (cb != null ? String.format("  Ma CB: %s - %s (%s)\r\n  Hanh trinh: %s -> %s | Thoi gian: %s %s -> %s %s\r\n", cb.getMaCB(), cb.getTenChuyenBay(), cb.getLoaiChuyen(), cb.getMaSanBayDi(), cb.getMaSanBayDen(), NhapLieu.dinhDangNgay(cb.getNgayDi()), cb.getGioDi(), NhapLieu.dinhDangNgay(cb.getNgayDen()), cb.getGioDen()) : "  Ma CB: " + hd.getMaCB() + "\r\n")
                  + "------------------------------------------------------------------------\r\nDANH SACH VE BAY:\r\n"
                  + String.format("%-4s | %-10s | %-20s | %-6s | %-12s | %s\r\n", "STT", "Ma ve", "Nguoi bay", "Ghe", "Hang ve", "Gia ve")
                  + "------------------------------------------------------------------------\r\n");

            int stt = 1;
            for (ChiTietHoaDon ct : dsHD.getDanhSachChiTiet()) {
                if (ct.getMaHD().equalsIgnoreCase(maHD)) {
                    Ve v = qlVe.timVe(ct.getMaVe());
                    KhachHang p = qlKH.timKhachHang(ct.getMaKH());
                    w.write(String.format("%-4d | %-10s | %-20s | %-6s | %-12s | %,.0f VND\r\n",
                            stt++, ct.getMaVe(), (p != null ? p.getHoTen() : ct.getMaKH()), (v != null ? v.getSoGhe() : "-"), (v != null ? v.getHangVe() : "PHO_THONG"), ct.getDonGiaVe()));
                }
            }
            w.write("========================================================================\r\n"
                  + String.format("TONG CONG: %d ve  |  TONG TIEN: %,.0f VND\r\n", hd.getSoLuongVe(), hd.getTongTien())
                  + "========================================================================\r\n"
                  + "Ghi chu: Day la hoa don mo phong, khong phai hoa don dien tu phap ly.\r\n");
            w.close();
            System.out.println("Xuat hoa don thanh cong ra file: " + filePath);
            return true;
        } catch (Exception e) {
            System.out.println("Loi xuat hoa don: " + e.getMessage());
            return false;
        }
    }
}
