package quanly;

import model.NhanVien;
import tienich.NhapLieu;
import java.time.LocalDate;
import java.util.ArrayList;

// Lop quan ly danh sach nhan vien va nghiep vu phan cong lap hoa don
public class QuanLyNhanVien {
    private ArrayList<NhanVien> danhSachNhanVien = new ArrayList<NhanVien>();

    public ArrayList<NhanVien> getDanhSachNhanVien() { return danhSachNhanVien; }
    public int getSoLuong() { return danhSachNhanVien.size(); }
    public NhanVien getNhanVien(int index) { return (index >= 0 && index < danhSachNhanVien.size()) ? danhSachNhanVien.get(index) : null; }
    public void themNhanVien(NhanVien nv) { if (nv != null && timNhanVien(nv.getMaNV()) == null) danhSachNhanVien.add(nv); }

    public NhanVien timNhanVien(String maNV) {
        if (maNV == null) return null;
        for (NhanVien nv : danhSachNhanVien) {
            if (maNV.equalsIgnoreCase(nv.getMaNV())) return nv;
        }
        return null;
    }

    public static String kiemTraHopLe(String maNV, String hoTen, LocalDate ngaySinh, String email, String soDienThoai) {
        if (maNV == null || maNV.trim().isEmpty()) return "Ma nhan vien khong duoc de trong!";
        if (hoTen == null || hoTen.trim().isEmpty()) return "Ho ten khong duoc de trong!";
        if (ngaySinh != null && ngaySinh.isAfter(LocalDate.now())) return "Ngay sinh khong duoc o tuong lai!";
        if (email == null || !email.contains("@")) return "Email phai chua ky tu '@'!";
        if (soDienThoai == null || !soDienThoai.matches("\\d{9,11}")) return "So dien thoai phai tu 9 den 11 chu so!";
        return "";
    }

    public boolean suaNhanVien(String maNV, String tenMoi, String sdtMoi) {
        NhanVien nv = timNhanVien(maNV);
        if (nv == null) return false;
        if (tenMoi != null && !tenMoi.isEmpty()) nv.setHoTen(tenMoi);
        if (sdtMoi != null && !sdtMoi.isEmpty()) nv.setSoDienThoai(sdtMoi);
        return true;
    }

    // Rang buoc toan ven: khong xoa nhan vien da tung lap hoa don trong he thong
    public boolean xoaNhanVien(String maNV, QuanLyHoaDon qlHD) {
        NhanVien nv = timNhanVien(maNV);
        if (nv == null) {
            System.out.println("Loi: Khong tim thay ma " + maNV);
            return false;
        }
        if (qlHD.kiemTraMaNVDaDung(maNV)) {
            System.out.println("Loi: Khong the xoa nhan vien da tung lap hoa don!");
            return false;
        }
        danhSachNhanVien.remove(nv);
        System.out.println("Xoa nhan vien " + maNV + " thanh cong!");
        return true;
    }

    public ArrayList<NhanVien> layDanhSachHoatDong() {
        ArrayList<NhanVien> res = new ArrayList<NhanVien>();
        for (NhanVien nv : danhSachNhanVien) {
            if (nv.isTrangThaiLamViec()) res.add(nv);
        }
        return res;
    }

    public void hienThiDanhSach() {
        System.out.println("\n================ DANH SACH NHAN VIEN ================");
        if (danhSachNhanVien.isEmpty()) {
            System.out.println("Danh sach trong.");
            return;
        }
        for (int i = 0; i < danhSachNhanVien.size(); i++) {
            System.out.printf("[%d] %s%n", (i + 1), danhSachNhanVien.get(i));
        }
    }

    public void timKiem(String tuKhoa) {
        System.out.println("\n---------------- TIM KIEM NHAN VIEN: " + tuKhoa + " ----------------");
        int dem = 0;
        String tk = tuKhoa.toLowerCase();
        for (NhanVien nv : danhSachNhanVien) {
            if (nv.getMaNV().toLowerCase().contains(tk) || nv.getHoTen().toLowerCase().contains(tk)) {
                System.out.println(nv);
                dem++;
            }
        }
        if (dem == 0) System.out.println("Khong tim thay ket qua phu hop.");
    }

    public NhanVien chonNhanVienLapHoaDon() {
        ArrayList<NhanVien> act = layDanhSachHoatDong();
        if (act.isEmpty()) {
            System.out.println("Loi: Khong co nhan vien nao dang lam viec!");
            return null;
        }
        System.out.println("\n---------------- CHON NHAN VIEN LAP HOA DON ----------------");
        for (int i = 0; i < act.size(); i++) {
            System.out.printf("[%d] %s%n", (i + 1), act.get(i));
        }
        int stt = NhapLieu.nhapSoNguyen("Chon nhan vien truc ca chiu trach nhiem lap hoa don", 1, act.size());
        return act.get(stt - 1);
    }

    public NhanVien nhapNhanVienMoi() {
        System.out.println("\n---------------- THEM NHAN VIEN MOI ----------------");
        String maNV;
        while (true) {
            maNV = NhapLieu.nhapChuoi("Nhap ma NV (vi du: NV03)");
            if (timNhanVien(maNV) == null) break;
            System.out.println("Loi: Ma da ton tai tren he thong!");
        }
        String hoTen = NhapLieu.nhapChuoi("Nhap ho ten");
        LocalDate ns;
        while (true) {
            ns = NhapLieu.nhapNgay("Nhap ngay sinh");
            if (!ns.isAfter(LocalDate.now())) break;
            System.out.println("Loi: Ngay sinh khong duoc o tuong lai!");
        }
        String gt = NhapLieu.nhapChuoi("Gioi tinh (Nam/Nu)");
        String dc = NhapLieu.nhapChuoi("Dia chi");
        String sdt;
        while (true) {
            sdt = NhapLieu.nhapChuoi("So dien thoai (9-11 so)");
            if (sdt.matches("\\d{9,11}")) break;
            System.out.println("Loi: So dien thoai phai tu 9 den 11 chu so!");
        }
        String email;
        while (true) {
            email = NhapLieu.nhapChuoi("Email");
            if (email.contains("@")) break;
            System.out.println("Loi: Email phai chua ky tu '@'!");
        }
        String cv = NhapLieu.nhapChuoi("Chuc vu");
        boolean tt = NhapLieu.nhapXacNhan("Nhan vien dang lam viec?");

        NhanVien nv = new NhanVien(maNV, hoTen, ns, gt, dc, sdt, email, cv, tt, 0, 0, 0.0);
        themNhanVien(nv);
        System.out.println("Them nhan vien moi thanh cong!");
        return nv;
    }

    public void menuQuanLy(QuanLyHoaDon qlHD) {
        while (true) {
            System.out.println("\n==================================================");
            System.out.println("                QUAN LY NHAN VIEN");
            System.out.println("==================================================");
            System.out.println("1. Xem danh sach nhan vien");
            System.out.println("2. Tim kiem nhan vien");
            System.out.println("3. Them nhan vien moi");
            System.out.println("4. Sua thong tin nhan vien");
            System.out.println("5. Xoa nhan vien");
            System.out.println("0. Quay lai menu chinh");
            System.out.println("==================================================");
            int c = NhapLieu.nhapSoNguyen("Lua chon cua ban", 0, 5);
            if (c == 0) break;
            if (c == 1) hienThiDanhSach();
            else if (c == 2) timKiem(NhapLieu.nhapChuoi("Nhap tu khoa tim kiem"));
            else if (c == 3) nhapNhanVienMoi();
            else if (c == 4) suaNhanVienConsole();
            else if (c == 5) xoaNhanVien(NhapLieu.nhapChuoi("Nhap ma can xoa"), qlHD);
        }
    }

    private void suaNhanVienConsole() {
        String ma = NhapLieu.nhapChuoi("Nhap ma nhan vien can sua");
        NhanVien nv = timNhanVien(ma);
        if (nv == null) {
            System.out.println("Khong tim thay: " + ma);
            return;
        }
        System.out.println("Thong tin hien tai: " + nv);
        String ten = NhapLieu.nhapChuoiCoTheRong("Ho ten (" + nv.getHoTen() + ")");
        if (!ten.isEmpty()) nv.setHoTen(ten);
        LocalDate ns = NhapLieu.nhapNgayCoTheRong("Ngay sinh (" + NhapLieu.dinhDangNgay(nv.getNgaySinh()) + ")");
        if (ns != null && !ns.isAfter(LocalDate.now())) nv.setNgaySinh(ns);
        String sdt = NhapLieu.nhapChuoiCoTheRong("SDT (" + nv.getSoDienThoai() + ")");
        if (!sdt.isEmpty() && sdt.matches("\\d{9,11}")) nv.setSoDienThoai(sdt);
        String email = NhapLieu.nhapChuoiCoTheRong("Email (" + nv.getEmail() + ")");
        if (!email.isEmpty() && email.contains("@")) nv.setEmail(email);
        String cv = NhapLieu.nhapChuoiCoTheRong("Chuc vu (" + nv.getChucVu() + ")");
        if (!cv.isEmpty()) nv.setChucVu(cv);
        if (NhapLieu.nhapXacNhan("Doi trang thai lam viec?")) nv.setTrangThaiLamViec(!nv.isTrangThaiLamViec());
        System.out.println("Cap nhat thong tin thanh cong!");
    }
}
