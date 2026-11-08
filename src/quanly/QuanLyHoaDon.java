package quanly;

import model.*;
import tienich.NhapLieu;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

// Lop quan ly danh sach hoa don, chi tiet hoa don, huy hoa don va xuat file
public class QuanLyHoaDon {
    private ArrayList<HoaDon> danhSachHoaDon = new ArrayList<HoaDon>();
    private ArrayList<ChiTietHoaDon> danhSachChiTiet = new ArrayList<ChiTietHoaDon>();

    public ArrayList<HoaDon> getDanhSachHoaDon() { return danhSachHoaDon; }
    public ArrayList<ChiTietHoaDon> getDanhSachChiTiet() { return danhSachChiTiet; }
    public int getSoLuong() { return danhSachHoaDon.size(); }
    public HoaDon getHoaDon(int index) { return (index >= 0 && index < danhSachHoaDon.size()) ? danhSachHoaDon.get(index) : null; }

    public void themHoaDon(HoaDon hd) { if (hd != null && timHoaDon(hd.getMaHD()) == null) danhSachHoaDon.add(hd); }
    public void themChiTiet(ChiTietHoaDon ct) { if (ct != null) danhSachChiTiet.add(ct); }

    public HoaDon timHoaDon(String maHD) {
        if (maHD == null) return null;
        for (HoaDon hd : danhSachHoaDon) if (maHD.equalsIgnoreCase(hd.getMaHD())) return hd;
        return null;
    }

    public ChiTietHoaDon timChiTietTheoVe(String maVe) {
        if (maVe == null) return null;
        for (ChiTietHoaDon ct : danhSachChiTiet) if (maVe.equalsIgnoreCase(ct.getMaVe())) return ct;
        return null;
    }

    public String sinhMaHD() {
        int max = 0;
        for (HoaDon hd : danhSachHoaDon) {
            String ma = hd.getMaHD();
            if (ma != null && ma.toUpperCase().startsWith("HD")) {
                try {
                    int num = Integer.parseInt(ma.substring(2).trim());
                    if (num > max) max = num;
                } catch (NumberFormatException ignored) {}
            }
        }
        return String.format("HD%03d", max + 1);
    }

    public boolean kiemTraMaKHDaDung(String maKH) {
        for (HoaDon hd : danhSachHoaDon) if (hd.getMaKH().equalsIgnoreCase(maKH)) return true;
        for (ChiTietHoaDon ct : danhSachChiTiet) if (ct.getMaKH().equalsIgnoreCase(maKH)) return true;
        return false;
    }

    public boolean kiemTraMaNVDaDung(String maNV) {
        for (HoaDon hd : danhSachHoaDon) if (hd.getMaNV().equalsIgnoreCase(maNV)) return true;
        return false;
    }

    public boolean kiemTraMaCBDaDung(String maCB) {
        for (HoaDon hd : danhSachHoaDon) if (hd.getMaCB().equalsIgnoreCase(maCB)) return true;
        return false;
    }

    // Nghiep vu huy hoa don: chuyen trang thai HoaDon va cac Ve lien quan thanh DA_HUY
    // Giai phong ghe de khach khac co the dat
    public boolean huyHoaDon(String maHD, QuanLyChuyenBay qlCB, QuanLyVe qlVe) {
        HoaDon hd = timHoaDon(maHD);
        if (hd == null || !"HIEU_LUC".equalsIgnoreCase(hd.getTrangThai())) {
            System.out.println("Loi: Khong tim thay hoa don hoac hoa don khong o trang thai HIEU_LUC!"); return false;
        }
        ChuyenBay cb = qlCB.timChuyenBay(hd.getMaCB());
        if (cb != null && !LocalDateTime.of(cb.getNgayDi(), cb.getGioDi()).isAfter(LocalDateTime.now())) {
            System.out.println("Loi: Chuyen bay da khoi hanh, khong the huy hoa don!"); return false;
        }
        hd.setTrangThai("DA_HUY");
        for (ChiTietHoaDon ct : danhSachChiTiet) {
            if (ct.getMaHD().equalsIgnoreCase(maHD)) {
                Ve v = qlVe.timVe(ct.getMaVe());
                if (v != null) v.setTrangThai("DA_HUY");
            }
        }
        System.out.println("Huy hoa don " + maHD + " thanh cong! (Ghe da duoc tra lai trang thai trong)");
        return true;
    }

    public double tinhDoanhThuChuyenBay(String maCB) {
        double t = 0; for (HoaDon h : danhSachHoaDon) if (h.getMaCB().equalsIgnoreCase(maCB) && "HIEU_LUC".equalsIgnoreCase(h.getTrangThai())) t += h.getTongTien();
        return t;
    }
    public int tinhSoVeKhachMua(String maKH) {
        int t = 0; for (HoaDon h : danhSachHoaDon) if (h.getMaKH().equalsIgnoreCase(maKH) && "HIEU_LUC".equalsIgnoreCase(h.getTrangThai())) t += h.getSoLuongVe();
        return t;
    }
    public double tinhTienKhachMua(String maKH) {
        double t = 0; for (HoaDon h : danhSachHoaDon) if (h.getMaKH().equalsIgnoreCase(maKH) && "HIEU_LUC".equalsIgnoreCase(h.getTrangThai())) t += h.getTongTien();
        return t;
    }
    public int demTongHoaDonNhanVien(String maNV) {
        int d = 0; for (HoaDon h : danhSachHoaDon) if (h.getMaNV().equalsIgnoreCase(maNV)) d++;
        return d;
    }
    public int tinhSoVeNhanVienBan(String maNV) {
        int t = 0; for (HoaDon h : danhSachHoaDon) if (h.getMaNV().equalsIgnoreCase(maNV) && "HIEU_LUC".equalsIgnoreCase(h.getTrangThai())) t += h.getSoLuongVe();
        return t;
    }
    public double tinhTienNhanVienBan(String maNV) {
        double t = 0; for (HoaDon h : danhSachHoaDon) if (h.getMaNV().equalsIgnoreCase(maNV) && "HIEU_LUC".equalsIgnoreCase(h.getTrangThai())) t += h.getTongTien();
        return t;
    }

    // Tinh tong hop doanh thu thuc te su dung interface ThongKe de the hien tinh da hinh
    public void thongKeTongHopHoaDon() {
        int demHieuLuc = 0; double tongTien = 0;
        for (HoaDon hd : danhSachHoaDon) {
            if ("HIEU_LUC".equalsIgnoreCase(hd.getTrangThai())) demHieuLuc++;
            // Ap dung da hinh (Polymorphism): bien kieu interface ThongKe tro toi doi tuong HoaDon
            ThongKe tk = hd;
            tongTien += tk.tinhTongTien();
        }
        System.out.println("\n================ THONG KE TONG HOP HOA DON ================");
        System.out.printf("Tong so hoa don con hieu luc : %d%n", demHieuLuc);
        System.out.printf("Tong doanh thu thuc te       : %,.0f VND%n", tongTien);
        System.out.println("===========================================================");
    }

    public void traCuuTheoKhachMua(String maKH) {
        System.out.println("\n---------------- HOA DON CUA KHACH HANG: " + maKH + " ----------------");
        int dem = 0;
        for (HoaDon hd : danhSachHoaDon) if (hd.getMaKH().equalsIgnoreCase(maKH)) { System.out.println(hd); dem++; }
        if (dem == 0) System.out.println("Khong tim thay hoa don nao.");
    }

    // Menu them 1 hoa don moi viet truc tiep trong class QuanLyHoaDon theo dung yeu cau:
    // Su dung toan bo bien cuc bo: nhap ma hd, ma kh, ngay lap, gan tong tien = 0,
    // hoi so luong chi tiet hoa don, lap qua tung chi tiet de nhap san pham va so luong,
    // getDonGia tu danh sach san pham (QuanLyChuyenBay), tinh thanh tien, cong don tong tien,
    // them cthdon vao danhSachChiTiet va cap nhat thay doi so luong cua danh sach san pham.
    // Chuc nang danh cho nhan vien ban ve lap hoa don truc tiep tai quay theo dung mau co giao:
    // Su dung toan bo bien cuc bo: nhan vien truc ca, nhap ma hd, nhap ma kh, nhap ngay lap, gan tong tien = 0,
    // hoi so luong chi tiet hoa don, vong lap: nhap ma san pham (ma chuyen bay), so luong,
    // getDonGia tu danh sach san pham (QuanLyChuyenBay), tinh thanh tien, cong don tong tien,
    // them cthdon vao danhSachChiTiet va cap nhat thay doi so luong cua danh sach san pham.
    public boolean themHoaDonTheoMauChung(QuanLyKhachHang qlKH, QuanLyNhanVien qlNV, QuanLyChuyenBay qlCB, QuanLyVe qlVe) {
        System.out.println("\n==================================================");
        System.out.println("     LAP HOA DON BAN VE TAI QUAY (NHAN VIEN)");
        System.out.println("==================================================");

        // Nhan vien truc ca chiu trach nhiem lap hoa don (bien cuc bo)
        NhanVien nv = qlNV.chonNhanVienLapHoaDon();
        if (nv == null) return false;
        String maNV = nv.getMaNV();

        // 1. Nhap ma hd (bien cuc bo)
        String maHDMacDinh = sinhMaHD();
        String maHD = NhapLieu.nhapChuoiCoTheRong("Nhap ma hoa don (Enter de lay tu dong: " + maHDMacDinh + ")");
        if (maHD.isEmpty()) maHD = maHDMacDinh;
        while (timHoaDon(maHD) != null) {
            System.out.println("Loi: Ma hoa don " + maHD + " da ton tai!");
            maHD = NhapLieu.nhapChuoi("Nhap lai ma hoa don");
        }

        // 2. Nhap ma kh (bien cuc bo)
        String maKH;
        while (true) {
            maKH = NhapLieu.nhapChuoi("Nhap ma khach hang (vi du: KH01)");
            if (qlKH.timKhachHang(maKH) != null) break;
            System.out.println("Loi: Khach hang " + maKH + " chua ton tai! Vui long nhap ma KH hop le.");
        }

        // 3. Nhap ngay lap (bien cuc bo)
        LocalDate d = NhapLieu.nhapNgayCoTheRong("Nhap ngay lap hoa don");
        LocalDateTime ngayLap = (d != null) ? d.atTime(LocalTime.now().getHour(), LocalTime.now().getMinute()) : LocalDateTime.now();

        // 4. Gan tong tien = 0 (bien cuc bo)
        double tongTien = 0.0;

        // 5. Roi moi hoi la nhap so luong chi tiet hoa don (bien cuc bo)
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

            // getDonGia tu danh sach san pham (QuanLyChuyenBay)
            double donGia = sp.getGiaVeCoBan();

            // Tinh thanh tien
            double thanhTien = donGia * soLuong;

            // Cong don tong tien
            tongTien += thanhTien;

            // Tao ma ve va them cthdon vao danh sach chi tiet hoa don
            String maVe = qlVe.sinhMaVe();
            qlVe.themVe(new Ve(maVe, sp.getMaCB(), "AUTO-" + i, "PHO_THONG", "THUONG", donGia, "HIEU_LUC"));
            this.themChiTiet(new ChiTietHoaDon(maHD, maVe, maKH, "THUONG", donGia, soLuong, thanhTien));

            // Cap nhat va thay doi so luong cua danh sach san pham
            sp.setSoVeDaBan(sp.getSoVeDaBan() + soLuong);
            sp.setSoVeConLai(sp.getTongSoVeMoBan() - sp.getSoVeDaBan());

            System.out.printf("-> Da them chi tiet: SP: %s | Don gia: %,.0f VND | SL: %d | Thanh tien: %,.0f VND%n",
                    sp.getMaCB(), donGia, soLuong, thanhTien);
        }

        // Them hoa don vao danhSachHoaDon
        this.themHoaDon(new HoaDon(maHD, maKH, maNV, maCBLuu, ngayLap, soLuongChiTiet, tongTien, "TIEN_MAT", "HIEU_LUC"));
        System.out.println("\nTHEM HOA DON THEO MAU THANH CONG!");
        System.out.printf("Ma HD: %s | Ma KH: %s | NV: %s | So luong chi tiet: %d | Tong tien: %,.0f VND%n",
                maHD, maKH, nv.getHoTen(), soLuongChiTiet, tongTien);
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
            for (ChiTietHoaDon ct : danhSachChiTiet) {
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
        } catch (Exception e) { System.out.println("Loi xuat hoa don: " + e.getMessage()); return false; }
    }
}
