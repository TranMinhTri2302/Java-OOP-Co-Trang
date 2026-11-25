package danhsach;

import model.ChuyenBay;
import model.ChuyenBayNoiDia;
import model.ChuyenBayQuocTe;
import java.time.LocalDateTime;
import java.util.ArrayList;

// Lop dong goi danh sach doi tuong ChuyenBay va cac thao tac co ban tren tap hop
public class DanhSachChuyenBay {
    private ArrayList<ChuyenBay> danhSach;

    public DanhSachChuyenBay() {
        this.danhSach = new ArrayList<ChuyenBay>();
    }

    public DanhSachChuyenBay(ArrayList<ChuyenBay> danhSach) {
        this.danhSach = (danhSach != null) ? danhSach : new ArrayList<ChuyenBay>();
    }

    public ArrayList<ChuyenBay> getDanhSach() {
        return danhSach;
    }

    public int getSoLuong() {
        return danhSach.size();
    }

    public ChuyenBay getChuyenBay(int index) {
        if (index >= 0 && index < danhSach.size()) {
            return danhSach.get(index);
        }
        return null;
    }

    public void themChuyenBay(ChuyenBay cb) {
        if (cb != null && timChuyenBay(cb.getMaCB()) == null) {
            danhSach.add(cb);
        }
    }

    public boolean xoaChuyenBay(String maCB) {
        ChuyenBay cb = timChuyenBay(maCB);
        if (cb != null) {
            return danhSach.remove(cb);
        }
        return false;
    }

    public ChuyenBay timChuyenBay(String maCB) {
        if (maCB == null) return null;
        for (ChuyenBay cb : danhSach) {
            if (maCB.equalsIgnoreCase(cb.getMaCB())) {
                return cb;
            }
        }
        return null;
    }

    // Lay danh sach cac chuyen bay noi dia (thuoc lop con ChuyenBayNoiDia)
    public ArrayList<ChuyenBayNoiDia> layDanhSachNoiDia() {
        ArrayList<ChuyenBayNoiDia> res = new ArrayList<ChuyenBayNoiDia>();
        for (ChuyenBay cb : danhSach) {
            if (cb instanceof ChuyenBayNoiDia) {
                res.add((ChuyenBayNoiDia) cb);
            }
        }
        return res;
    }

    // Lay danh sach cac chuyen bay quoc te (thuoc lop con ChuyenBayQuocTe)
    public ArrayList<ChuyenBayQuocTe> layDanhSachQuocTe() {
        ArrayList<ChuyenBayQuocTe> res = new ArrayList<ChuyenBayQuocTe>();
        for (ChuyenBay cb : danhSach) {
            if (cb instanceof ChuyenBayQuocTe) {
                res.add((ChuyenBayQuocTe) cb);
            }
        }
        return res;
    }

    // Lay tat ca cac chuyen bay kha dung cho mot tuyen bay o tuong lai
    public ArrayList<ChuyenBay> layDanhSachChuyenBayKhaDung(String maSanBayDi, String maSanBayDen) {
        ArrayList<ChuyenBay> res = new ArrayList<ChuyenBay>();
        LocalDateTime now = LocalDateTime.now();
        for (ChuyenBay cb : danhSach) {
            if (cb.getMaSanBayDi().equalsIgnoreCase(maSanBayDi) &&
                cb.getMaSanBayDen().equalsIgnoreCase(maSanBayDen) &&
                "MO_BAN".equalsIgnoreCase(cb.getTrangThai()) &&
                cb.getSoVeConLai() > 0 &&
                LocalDateTime.of(cb.getNgayDi(), cb.getGioDi()).isAfter(now)) {
                res.add(cb);
            }
        }
        return res;
    }

    // Kiem tra chong lich bay tren cung mot may bay
    public boolean kiemTraTrungLich(String maMayBay, LocalDateTime startMoi, LocalDateTime endMoi, String maCBIgnore) {
        for (ChuyenBay cb : danhSach) {
            if (cb.getMaCB().equalsIgnoreCase(maCBIgnore) || "HUY".equalsIgnoreCase(cb.getTrangThai())) continue;
            if (cb.getMaMayBay().equalsIgnoreCase(maMayBay)) {
                LocalDateTime startHienTai = LocalDateTime.of(cb.getNgayDi(), cb.getGioDi());
                LocalDateTime endHienTai = LocalDateTime.of(cb.getNgayDen(), cb.getGioDen());
                if (startMoi.isBefore(endHienTai) && startHienTai.isBefore(endMoi)) return true;
            }
        }
        return false;
    }

    public boolean doiTrangThai(String maCB) {
        ChuyenBay cb = timChuyenBay(maCB);
        if (cb == null) return false;
        if ("HUY".equalsIgnoreCase(cb.getTrangThai())) return false;
        String moi = "MO_BAN".equalsIgnoreCase(cb.getTrangThai()) ? "DONG_BAN" : "MO_BAN";
        cb.setTrangThai(moi);
        return true;
    }

    public void xuatDanhSach() {
        if (danhSach.isEmpty()) {
            System.out.println("Danh sach chuyen bay dang trong.");
            return;
        }
        System.out.println("\n================================================ DANH SACH CHUYEN BAY ================================================");
        System.out.printf("%-8s | %-20s | %-8s | %-8s | %-12s | %-12s | %-10s | %-10s | %s%n",
                "Ma CB", "Ten chuyen bay", "Di", "Den", "Ngay di", "Gio di", "Gia ve", "Trang thai", "Loai chuyen");
        System.out.println("----------------------------------------------------------------------------------------------------------------------");
        for (ChuyenBay cb : danhSach) {
            System.out.printf("%-8s | %-20s | %-8s | %-8s | %-12s | %-12s | %,.0f | %-10s | %s%n",
                    cb.getMaCB(), cb.getTenChuyenBay(), cb.getMaSanBayDi(), cb.getMaSanBayDen(),
                    cb.getNgayDi(), cb.getGioDi(), cb.getGiaVeCoBan(), cb.getTrangThai(), cb.getLoaiChuyen());
        }
        System.out.println("======================================================================================================================");
    }
}
