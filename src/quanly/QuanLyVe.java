package quanly;

import model.*;
import tienich.NhapLieu;
import java.util.ArrayList;

// Lop quan ly danh sach ve va logic kiem tra trang thai ghe ngoi tren tung chuyen bay
public class QuanLyVe {
    private ArrayList<Ve> danhSachVe = new ArrayList<Ve>();

    public ArrayList<Ve> getDanhSachVe() { return danhSachVe; }
    public int getSoLuong() { return danhSachVe.size(); }
    public Ve getVe(int index) { return (index >= 0 && index < danhSachVe.size()) ? danhSachVe.get(index) : null; }
    public void themVe(Ve ve) { if (ve != null && timVe(ve.getMaVe()) == null) danhSachVe.add(ve); }

    public Ve timVe(String maVe) {
        if (maVe == null) return null;
        for (Ve v : danhSachVe) {
            if (maVe.equalsIgnoreCase(v.getMaVe())) return v;
        }
        return null;
    }

    public String sinhMaVe() {
        int max = 0;
        for (Ve v : danhSachVe) {
            String ma = v.getMaVe();
            if (ma != null && ma.toUpperCase().startsWith("VE")) {
                try {
                    int num = Integer.parseInt(ma.substring(2).trim());
                    if (num > max) max = num;
                } catch (NumberFormatException ignored) {}
            }
        }
        return String.format("VE%03d", max + 1);
    }

    public int demVeHieuLucTheoChuyen(String maCB) {
        int d = 0;
        for (Ve v : danhSachVe) {
            if (v.getMaCB().equalsIgnoreCase(maCB) && "HIEU_LUC".equalsIgnoreCase(v.getTrangThai())) d++;
        }
        return d;
    }

    // Kiem tra khach da co ve hieu luc tren chuyen bay chua (moi khach toi da 1 ve hieu luc / chuyen bay)
    public boolean kiemTraKhachDaCoVeChuyenBay(String maKH, String maCB, QuanLyHoaDon qlHD) {
        for (Ve v : danhSachVe) {
            if (v.getMaCB().equalsIgnoreCase(maCB) && "HIEU_LUC".equalsIgnoreCase(v.getTrangThai())) {
                ChiTietHoaDon ct = qlHD.timChiTietTheoVe(v.getMaVe());
                if (ct != null && ct.getMaKH().equalsIgnoreCase(maKH)) return true;
            }
        }
        return false;
    }

    // Lay danh sach ghe trong cho mot chuyen bay cu the (danhSachSoGhe cua may bay tru di cac ghe da co ve HIEU_LUC)
    public ArrayList<String> layDanhSachGheTrong(String maCB, MayBay mb) {
        ArrayList<String> res = new ArrayList<String>();
        if (mb == null) return res;
        for (String ghe : mb.getDanhSachSoGhe()) {
            boolean daDat = false;
            for (Ve v : danhSachVe) {
                if (v.getMaCB().equalsIgnoreCase(maCB) && "HIEU_LUC".equalsIgnoreCase(v.getTrangThai()) && v.getSoGhe().equalsIgnoreCase(ghe)) {
                    daDat = true;
                    break;
                }
            }
            if (!daDat) res.add(ghe);
        }
        return res;
    }

    // Xac dinh hang ve theo quy uoc so ghe: hang 1 la THUONG_GIA, tu hang 2 tro di la PHO_THONG
    public String xacDinhHangVe(String soGhe) {
        if (soGhe == null || soGhe.trim().isEmpty()) return "PHO_THONG";
        return soGhe.trim().startsWith("1") ? "THUONG_GIA" : "PHO_THONG";
    }

    // Hien thi va cho phep nguoi dung chon so ghe con trong theo tung khoang ghe
    public String chonGheTrongConsole(String maCB, MayBay mb, String[] gheDaChon, int soLuong) {
        ArrayList<String> trong = layDanhSachGheTrong(maCB, mb);
        ArrayList<String> khaDung = new ArrayList<String>();
        for (String g : trong) {
            boolean da = false;
            for (int k = 0; k < soLuong; k++) {
                if (g.equalsIgnoreCase(gheDaChon[k])) {
                    da = true;
                    break;
                }
            }
            if (!da) khaDung.add(g);
        }
        if (khaDung.isEmpty()) {
            System.out.println("Loi: Khong con ghe trong tren chuyen bay!");
            return null;
        }

        System.out.println("\n---------------- SO DO GHE CON TRONG ----------------");
        System.out.println("[KHOANG THUONG GIA - HANG 1]:");
        int demTG = 0;
        for (String g : khaDung) {
            if (g.startsWith("1")) {
                System.out.printf("[%s] ", g);
                demTG++;
            }
        }
        if (demTG == 0) System.out.print("(Het cho thuong gia)");
        System.out.println();

        System.out.println("[KHOANG PHO THONG - TU HANG 2 TRO DI]:");
        int demPT = 0;
        for (String g : khaDung) {
            if (!g.startsWith("1")) {
                System.out.printf("[%s] ", g);
                demPT++;
                if (demPT % 6 == 0) System.out.println();
            }
        }
        if (demPT == 0) System.out.print("(Het cho pho thong)");
        System.out.println();

        while (true) {
            String chon = NhapLieu.nhapChuoi("Nhap so ghe ban muon chon (vi du: 1A)");
            for (String g : khaDung) {
                if (chon.equalsIgnoreCase(g)) return g;
            }
            System.out.println("Loi: So ghe khong hop le hoac da co nguoi dat!");
        }
    }

    public boolean kiemTraMaCBDaDung(String maCB) {
        for (Ve v : danhSachVe) {
            if (v.getMaCB().equalsIgnoreCase(maCB)) return true;
        }
        return false;
    }

    public void hienThiDanhSach() {
        System.out.println("\n================ DANH SACH VE ================");
        if (danhSachVe.isEmpty()) {
            System.out.println("Danh sach trong.");
            return;
        }
        for (int i = 0; i < danhSachVe.size(); i++) {
            System.out.printf("[%d] %s%n", (i + 1), danhSachVe.get(i));
        }
    }

    // Tra cuu chi tiet ve: tim nguoi bay qua ChiTietHoaDon vi Ve khong co maKH
    public void traCuuChiTietVe(String maVe, QuanLyHoaDon qlHD, QuanLyKhachHang qlKH, QuanLyChuyenBay qlCB) {
        Ve v = timVe(maVe);
        if (v == null) {
            System.out.println("Khong tim thay ma ve: " + maVe);
            return;
        }
        System.out.println("\n================ THONG TIN CHI TIET VE ================");
        System.out.printf("Ma ve: %s | Chuyen bay: %s | So ghe: %s%n", v.getMaVe(), v.getMaCB(), v.getSoGhe());
        System.out.printf("Hang ve: %s | Loai ve: %s | Gia ve: %,.0f VND | Trang thai: %s%n",
                v.getHangVe(), v.getLoaiVe(), v.getGiaVe(), v.getTrangThai());
        ChiTietHoaDon ct = qlHD.timChiTietTheoVe(maVe);
        if (ct != null) {
            KhachHang p = qlKH.timKhachHang(ct.getMaKH());
            System.out.printf("Ma hoa don: %s | Nguoi bay: %s (Ma KH: %s)%n",
                    ct.getMaHD(), (p != null ? p.getHoTen() : ct.getMaKH()), ct.getMaKH());
        }
        System.out.println("========================================================");
    }
}
