package danhsach;

import model.NhanVien;
import java.util.ArrayList;

// Lop dong goi danh sach doi tuong NhanVien va cac thao tac co ban tren tap hop
public class DanhSachNhanVien {
    private ArrayList<NhanVien> danhSach;

    public DanhSachNhanVien() {
        this.danhSach = new ArrayList<NhanVien>();
    }

    public DanhSachNhanVien(ArrayList<NhanVien> danhSach) {
        this.danhSach = (danhSach != null) ? danhSach : new ArrayList<NhanVien>();
    }

    public ArrayList<NhanVien> getDanhSach() {
        return danhSach;
    }

    public int getSoLuong() {
        return danhSach.size();
    }

    public NhanVien getNhanVien(int index) {
        if (index >= 0 && index < danhSach.size()) {
            return danhSach.get(index);
        }
        return null;
    }

    public void themNhanVien(NhanVien nv) {
        if (nv != null && timNhanVien(nv.getMaNV()) == null) {
            danhSach.add(nv);
        }
    }

    public boolean xoaNhanVien(String maNV) {
        NhanVien nv = timNhanVien(maNV);
        if (nv != null) {
            return danhSach.remove(nv);
        }
        return false;
    }

    public NhanVien timNhanVien(String maNV) {
        if (maNV == null) return null;
        for (NhanVien nv : danhSach) {
            if (maNV.equalsIgnoreCase(nv.getMaNV())) {
                return nv;
            }
        }
        return null;
    }

    public boolean suaNhanVien(String maNV, String tenMoi, String sdtMoi) {
        NhanVien nv = timNhanVien(maNV);
        if (nv == null) return false;
        if (tenMoi != null && !tenMoi.trim().isEmpty()) nv.setHoTen(tenMoi.trim());
        if (sdtMoi != null && !sdtMoi.trim().isEmpty()) nv.setSoDienThoai(sdtMoi.trim());
        return true;
    }

    public ArrayList<NhanVien> layDanhSachHoatDong() {
        ArrayList<NhanVien> res = new ArrayList<NhanVien>();
        for (NhanVien nv : danhSach) {
            if (nv.isTrangThaiLamViec()) {
                res.add(nv);
            }
        }
        return res;
    }

    public void xuatDanhSach() {
        if (danhSach.isEmpty()) {
            System.out.println("Danh sach nhan vien dang trong.");
            return;
        }
        System.out.println("\n================================ DANH SACH NHAN VIEN ================================");
        System.out.printf("%-8s | %-20s | %-16s | %-12s | %s%n",
                "Ma NV", "Ho ten", "Chuc vu", "So DT", "Trang thai");
        System.out.println("--------------------------------------------------------------------------------------");
        for (NhanVien nv : danhSach) {
            System.out.printf("%-8s | %-20s | %-16s | %-12s | %s%n",
                    nv.getMaNV(), nv.getHoTen(), nv.getChucVu(), nv.getSoDienThoai(),
                    nv.isTrangThaiLamViec() ? "Dang lam viec" : "Nghi viec");
        }
        System.out.println("======================================================================================");
    }
}
