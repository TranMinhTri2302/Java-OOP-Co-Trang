package danhsach;

import model.ChiTietHoaDon;
import model.HoaDon;
import model.ThongKe;
import java.util.ArrayList;

// Lop dong goi danh sach doi tuong HoaDon va ChiTietHoaDon, cac thao tac co ban tren tap hop
public class DanhSachHoaDon {
    private ArrayList<HoaDon> danhSachHoaDon;
    private ArrayList<ChiTietHoaDon> danhSachChiTiet;

    public DanhSachHoaDon() {
        this.danhSachHoaDon = new ArrayList<HoaDon>();
        this.danhSachChiTiet = new ArrayList<ChiTietHoaDon>();
    }

    public DanhSachHoaDon(ArrayList<HoaDon> dsHD, ArrayList<ChiTietHoaDon> dsCT) {
        this.danhSachHoaDon = (dsHD != null) ? dsHD : new ArrayList<HoaDon>();
        this.danhSachChiTiet = (dsCT != null) ? dsCT : new ArrayList<ChiTietHoaDon>();
    }

    public ArrayList<HoaDon> getDanhSachHoaDon() {
        return danhSachHoaDon;
    }

    public ArrayList<ChiTietHoaDon> getDanhSachChiTiet() {
        return danhSachChiTiet;
    }

    public int getSoLuong() {
        return danhSachHoaDon.size();
    }

    public HoaDon getHoaDon(int index) {
        if (index >= 0 && index < danhSachHoaDon.size()) {
            return danhSachHoaDon.get(index);
        }
        return null;
    }

    public void themHoaDon(HoaDon hd) {
        if (hd != null && timHoaDon(hd.getMaHD()) == null) {
            danhSachHoaDon.add(hd);
        }
    }

    public void themChiTiet(ChiTietHoaDon ct) {
        if (ct != null) {
            danhSachChiTiet.add(ct);
        }
    }

    public HoaDon timHoaDon(String maHD) {
        if (maHD == null) return null;
        for (HoaDon hd : danhSachHoaDon) {
            if (maHD.equalsIgnoreCase(hd.getMaHD())) {
                return hd;
            }
        }
        return null;
    }

    public ChiTietHoaDon timChiTietTheoVe(String maVe) {
        if (maVe == null) return null;
        for (ChiTietHoaDon ct : danhSachChiTiet) {
            if (maVe.equalsIgnoreCase(ct.getMaVe())) {
                return ct;
            }
        }
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

    public double tinhDoanhThuChuyenBay(String maCB) {
        double t = 0;
        for (HoaDon h : danhSachHoaDon) {
            if (h.getMaCB().equalsIgnoreCase(maCB) && "HIEU_LUC".equalsIgnoreCase(h.getTrangThai())) {
                t += h.getTongTien();
            }
        }
        return t;
    }

    public int tinhSoVeKhachMua(String maKH) {
        int t = 0;
        for (HoaDon h : danhSachHoaDon) {
            if (h.getMaKH().equalsIgnoreCase(maKH) && "HIEU_LUC".equalsIgnoreCase(h.getTrangThai())) {
                t += h.getSoLuongVe();
            }
        }
        return t;
    }

    public double tinhTienKhachMua(String maKH) {
        double t = 0;
        for (HoaDon h : danhSachHoaDon) {
            if (h.getMaKH().equalsIgnoreCase(maKH) && "HIEU_LUC".equalsIgnoreCase(h.getTrangThai())) {
                t += h.getTongTien();
            }
        }
        return t;
    }

    public int demTongHoaDonNhanVien(String maNV) {
        int d = 0;
        for (HoaDon h : danhSachHoaDon) {
            if (h.getMaNV().equalsIgnoreCase(maNV)) d++;
        }
        return d;
    }

    public int tinhSoVeNhanVienBan(String maNV) {
        int t = 0;
        for (HoaDon h : danhSachHoaDon) {
            if (h.getMaNV().equalsIgnoreCase(maNV) && "HIEU_LUC".equalsIgnoreCase(h.getTrangThai())) {
                t += h.getSoLuongVe();
            }
        }
        return t;
    }

    public double tinhTienNhanVienBan(String maNV) {
        double t = 0;
        for (HoaDon h : danhSachHoaDon) {
            if (h.getMaNV().equalsIgnoreCase(maNV) && "HIEU_LUC".equalsIgnoreCase(h.getTrangThai())) {
                t += h.getTongTien();
            }
        }
        return t;
    }

    public ArrayList<HoaDon> timHoaDonTheoKhoangTien(double minTien, double maxTien) {
        ArrayList<HoaDon> kq = new ArrayList<HoaDon>();
        for (HoaDon hd : danhSachHoaDon) {
            if (hd.getTongTien() >= minTien && hd.getTongTien() <= maxTien && "HIEU_LUC".equalsIgnoreCase(hd.getTrangThai())) {
                kq.add(hd);
            }
        }
        return kq;
    }

    public ArrayList<HoaDon> timHoaDonTheoKhach(String maKH) {
        ArrayList<HoaDon> kq = new ArrayList<HoaDon>();
        for (HoaDon hd : danhSachHoaDon) {
            if (hd.getMaKH().equalsIgnoreCase(maKH)) {
                kq.add(hd);
            }
        }
        return kq;
    }

    // Tinh tong doanh thu trong khoang 3 nam gan nhat (namHienTai - 2 den namHienTai) qua interface ThongKe
    public double tinhTongDoanhThu3NamGanNhat(int namHienTai) {
        double tong = 0;
        int namBatDau = namHienTai - 2;
        for (HoaDon hd : danhSachHoaDon) {
            if ("HIEU_LUC".equalsIgnoreCase(hd.getTrangThai()) && hd.getNgayLap() != null) {
                int y = hd.getNgayLap().getYear();
                if (y >= namBatDau && y <= namHienTai) {
                    ThongKe tk = hd;
                    tong += tk.tinhTongTien();
                }
            }
        }
        return tong;
    }

    public void xuatDanhSach() {
        if (danhSachHoaDon.isEmpty()) {
            System.out.println("Danh sach hoa don dang trong.");
            return;
        }
        System.out.println("\n================================ DANH SACH HOA DON ================================");
        System.out.printf("%-8s | %-8s | %-8s | %-8s | %-16s | %-4s | %-12s | %s%n",
                "Ma HD", "Ma KH", "Ma NV", "Ma CB", "Ngay lap", "Ve", "Tong tien", "Trang thai");
        System.out.println("----------------------------------------------------------------------------------");
        for (HoaDon hd : danhSachHoaDon) {
            System.out.printf("%-8s | %-8s | %-8s | %-8s | %-16s | %-4d | %,.0f VND | %s%n",
                    hd.getMaHD(), hd.getMaKH(), hd.getMaNV(), hd.getMaCB(),
                    hd.getNgayLap().toLocalDate(), hd.getSoLuongVe(), hd.getTongTien(), hd.getTrangThai());
        }
        System.out.println("==================================================================================");
    }
}
