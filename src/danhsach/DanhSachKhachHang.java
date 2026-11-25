package danhsach;

import model.KhachHang;
import java.util.ArrayList;

// Lop dong goi danh sach doi tuong KhachHang va cac thao tac co ban tren tap hop
public class DanhSachKhachHang {
    private ArrayList<KhachHang> danhSach;

    public DanhSachKhachHang() {
        this.danhSach = new ArrayList<KhachHang>();
    }

    public DanhSachKhachHang(ArrayList<KhachHang> danhSach) {
        this.danhSach = (danhSach != null) ? danhSach : new ArrayList<KhachHang>();
    }

    public ArrayList<KhachHang> getDanhSach() {
        return danhSach;
    }

    public int getSoLuong() {
        return danhSach.size();
    }

    public KhachHang getKhachHang(int index) {
        if (index >= 0 && index < danhSach.size()) {
            return danhSach.get(index);
        }
        return null;
    }

    public void themKhachHang(KhachHang kh) {
        if (kh != null && timKhachHang(kh.getMaKH()) == null) {
            danhSach.add(kh);
        }
    }

    public boolean xoaKhachHang(String maKH) {
        KhachHang kh = timKhachHang(maKH);
        if (kh != null) {
            return danhSach.remove(kh);
        }
        return false;
    }

    public KhachHang timKhachHang(String maKH) {
        if (maKH == null) return null;
        for (KhachHang kh : danhSach) {
            if (maKH.equalsIgnoreCase(kh.getMaKH())) {
                return kh;
            }
        }
        return null;
    }

    public KhachHang timTheoCCCDHoacMa(String kw) {
        if (kw == null || kw.trim().isEmpty()) return null;
        String k = kw.trim();
        for (KhachHang kh : danhSach) {
            if (k.equalsIgnoreCase(kh.getMaKH()) ||
                (kh.getSoCCCD() != null && k.equalsIgnoreCase(kh.getSoCCCD())) ||
                (kh.getSoDienThoai() != null && k.equalsIgnoreCase(kh.getSoDienThoai()))) {
                return kh;
            }
        }
        return null;
    }

    public boolean suaKhachHang(String maKH, String tenMoi, String sdtMoi) {
        KhachHang kh = timKhachHang(maKH);
        if (kh == null) return false;
        if (tenMoi != null && !tenMoi.trim().isEmpty()) kh.setHoTen(tenMoi.trim());
        if (sdtMoi != null && !sdtMoi.trim().isEmpty()) kh.setSoDienThoai(sdtMoi.trim());
        return true;
    }

    public void xuatDanhSach() {
        if (danhSach.isEmpty()) {
            System.out.println("Danh sach khach hang dang trong.");
            return;
        }
        System.out.println("\n================================ DANH SACH KHACH HANG ================================");
        System.out.printf("%-8s | %-20s | %-12s | %-12s | %-12s | %s%n",
                "Ma KH", "Ho ten", "Ngay sinh", "Gioi tinh", "So DT", "Email");
        System.out.println("--------------------------------------------------------------------------------------");
        for (KhachHang kh : danhSach) {
            System.out.printf("%-8s | %-20s | %-12s | %-12s | %-12s | %s%n",
                    kh.getMaKH(), kh.getHoTen(), kh.getNgaySinh(), kh.getGioiTinh(), kh.getSoDienThoai(), kh.getEmail());
        }
        System.out.println("======================================================================================");
    }
}
