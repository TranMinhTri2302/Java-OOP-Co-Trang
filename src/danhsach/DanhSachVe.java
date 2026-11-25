package danhsach;

import model.Ve;
import java.util.ArrayList;

// Lop dong goi danh sach doi tuong Ve va cac thao tac co ban tren tap hop
public class DanhSachVe {
    private ArrayList<Ve> danhSach;

    public DanhSachVe() {
        this.danhSach = new ArrayList<Ve>();
    }

    public DanhSachVe(ArrayList<Ve> danhSach) {
        this.danhSach = (danhSach != null) ? danhSach : new ArrayList<Ve>();
    }

    public ArrayList<Ve> getDanhSach() {
        return danhSach;
    }

    public int getSoLuong() {
        return danhSach.size();
    }

    public Ve getVe(int index) {
        if (index >= 0 && index < danhSach.size()) {
            return danhSach.get(index);
        }
        return null;
    }

    public void themVe(Ve ve) {
        if (ve != null && timVe(ve.getMaVe()) == null) {
            danhSach.add(ve);
        }
    }

    public boolean xoaVe(String maVe) {
        Ve v = timVe(maVe);
        if (v != null) {
            return danhSach.remove(v);
        }
        return false;
    }

    public Ve timVe(String maVe) {
        if (maVe == null) return null;
        for (Ve v : danhSach) {
            if (maVe.equalsIgnoreCase(v.getMaVe())) {
                return v;
            }
        }
        return null;
    }

    public boolean suaVe(String maVe, String hangVeMoi, double giaVeMoi) {
        Ve v = timVe(maVe);
        if (v == null) return false;
        if (hangVeMoi != null && !hangVeMoi.isEmpty()) v.setHangVe(hangVeMoi);
        if (giaVeMoi > 0) v.setGiaVe(giaVeMoi);
        return true;
    }

    public String sinhMaVe() {
        int max = 0;
        for (Ve v : danhSach) {
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
        for (Ve v : danhSach) {
            if (v.getMaCB().equalsIgnoreCase(maCB) && "HIEU_LUC".equalsIgnoreCase(v.getTrangThai())) {
                d++;
            }
        }
        return d;
    }

    public ArrayList<Ve> layVeTheoChuyenBay(String maCB) {
        ArrayList<Ve> res = new ArrayList<Ve>();
        for (Ve v : danhSach) {
            if (v.getMaCB().equalsIgnoreCase(maCB)) {
                res.add(v);
            }
        }
        return res;
    }

    public void xuatDanhSach() {
        if (danhSach.isEmpty()) {
            System.out.println("Danh sach ve dang trong.");
            return;
        }
        System.out.println("\n================================ DANH SACH VE BAY ================================");
        System.out.printf("%-8s | %-8s | %-6s | %-12s | %-10s | %-12s | %s%n",
                "Ma ve", "Ma CB", "Ghe", "Hang ve", "Loai ve", "Gia ve", "Trang thai");
        System.out.println("----------------------------------------------------------------------------------");
        for (Ve v : danhSach) {
            System.out.printf("%-8s | %-8s | %-6s | %-12s | %-10s | %,.0f VND | %s%n",
                    v.getMaVe(), v.getMaCB(), v.getSoGhe(), v.getHangVe(), v.getLoaiVe(), v.getGiaVe(), v.getTrangThai());
        }
        System.out.println("==================================================================================");
    }
}
