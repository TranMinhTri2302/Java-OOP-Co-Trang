package quanly;

import danhsach.DanhSachVe;
import model.*;
import tienich.NhapLieu;
import java.util.ArrayList;

// Lop quan ly nghiep vu va giao dien console ve bay, su dung DanhSachVe de luu tru
public class QuanLyVe {
    private DanhSachVe dsVe;

    public QuanLyVe() {
        this.dsVe = new DanhSachVe();
    }

    public QuanLyVe(DanhSachVe dsVe) {
        this.dsVe = (dsVe != null) ? dsVe : new DanhSachVe();
    }

    public DanhSachVe getDsVe() { return dsVe; }
    public ArrayList<Ve> getDanhSachVe() { return dsVe.getDanhSach(); }
    public int getSoLuong() { return dsVe.getSoLuong(); }
    public Ve getVe(int index) { return dsVe.getVe(index); }
    public void themVe(Ve ve) { dsVe.themVe(ve); }
    public Ve timVe(String maVe) { return dsVe.timVe(maVe); }
    public boolean xoaVe(String maVe) { return dsVe.xoaVe(maVe); }
    public boolean suaVe(String maVe, String hangVeMoi, double giaVeMoi) { return dsVe.suaVe(maVe, hangVeMoi, giaVeMoi); }
    public String sinhMaVe() { return dsVe.sinhMaVe(); }
    public int demVeHieuLucTheoChuyen(String maCB) { return dsVe.demVeHieuLucTheoChuyen(maCB); }

    public boolean kiemTraKhachDaCoVeChuyenBay(String maKH, String maCB, QuanLyHoaDon qlHD) {
        for (Ve v : dsVe.getDanhSach()) {
            if (v.getMaCB().equalsIgnoreCase(maCB) && "HIEU_LUC".equalsIgnoreCase(v.getTrangThai())) {
                ChiTietHoaDon ct = qlHD.timChiTietTheoVe(v.getMaVe());
                if (ct != null && ct.getMaKH().equalsIgnoreCase(maKH)) return true;
            }
        }
        return false;
    }

    public ArrayList<String> layDanhSachGheTrong(String maCB, MayBay mb) {
        ArrayList<String> res = new ArrayList<String>();
        if (mb == null) return res;
        for (String ghe : mb.getDanhSachSoGhe()) {
            boolean daDat = false;
            for (Ve v : dsVe.getDanhSach()) {
                if (v.getMaCB().equalsIgnoreCase(maCB) && "HIEU_LUC".equalsIgnoreCase(v.getTrangThai()) && v.getSoGhe().equalsIgnoreCase(ghe)) {
                    daDat = true;
                    break;
                }
            }
            if (!daDat) res.add(ghe);
        }
        return res;
    }

    public String xacDinhHangVe(String soGhe) {
        if (soGhe == null || soGhe.trim().isEmpty()) return "PHO_THONG";
        return soGhe.trim().startsWith("1") ? "THUONG_GIA" : "PHO_THONG";
    }

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
        for (Ve v : dsVe.getDanhSach()) {
            if (v.getMaCB().equalsIgnoreCase(maCB)) return true;
        }
        return false;
    }

    public void hienThiDanhSach() {
        dsVe.xuatDanhSach();
    }

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
