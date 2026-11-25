package quanly;

import danhsach.DanhSachNhanVien;
import model.NhanVien;
import tienich.NhapLieu;
import java.time.LocalDate;
import java.util.ArrayList;

// Lop quan ly nghiep vu va giao dien console nhan vien, su dung DanhSachNhanVien de luu tru
public class QuanLyNhanVien {
    private DanhSachNhanVien dsNV;

    public QuanLyNhanVien() {
        this.dsNV = new DanhSachNhanVien();
    }

    public QuanLyNhanVien(DanhSachNhanVien dsNV) {
        this.dsNV = (dsNV != null) ? dsNV : new DanhSachNhanVien();
    }

    public DanhSachNhanVien getDsNV() { return dsNV; }
    public ArrayList<NhanVien> getDanhSachNhanVien() { return dsNV.getDanhSach(); }
    public int getSoLuong() { return dsNV.getSoLuong(); }
    public NhanVien getNhanVien(int index) { return dsNV.getNhanVien(index); }
    public void themNhanVien(NhanVien nv) { dsNV.themNhanVien(nv); }
    public NhanVien timNhanVien(String maNV) { return dsNV.timNhanVien(maNV); }
    public ArrayList<NhanVien> layDanhSachHoatDong() { return dsNV.layDanhSachHoatDong(); }

    public static String kiemTraHopLe(String maNV, String hoTen, LocalDate ngaySinh, String email, String soDienThoai) {
        if (maNV == null || maNV.trim().isEmpty()) return "Ma nhan vien khong duoc de trong!";
        if (hoTen == null || hoTen.trim().isEmpty()) return "Ho ten khong duoc de trong!";
        if (ngaySinh != null && ngaySinh.isAfter(LocalDate.now())) return "Ngay sinh khong duoc o tuong lai!";
        if (email == null || !email.contains("@")) return "Email phai chua ky tu '@'!";
        if (soDienThoai == null || !soDienThoai.matches("\\d{9,11}")) return "So dien thoai phai tu 9 den 11 chu so!";
        return "";
    }

    public boolean suaNhanVien(String maNV, String tenMoi, String sdtMoi) {
        return dsNV.suaNhanVien(maNV, tenMoi, sdtMoi);
    }

    public boolean xoaNhanVien(String maNV) {
        boolean ok = dsNV.xoaNhanVien(maNV);
        if (ok) {
            System.out.println("Xoa nhan vien " + maNV + " thanh cong!");
        } else {
            System.out.println("Loi: Khong tim thay ma " + maNV);
        }
        return ok;
    }

    public boolean xoaNhanVien(String maNV, QuanLyHoaDon qlHD) {
        return xoaNhanVien(maNV);
    }

    public void hienThiDanhSach() {
        dsNV.xuatDanhSach();
    }

    public void timKiem(String tuKhoa) {
        System.out.println("\n---------------- TIM KIEM NHAN VIEN: " + tuKhoa + " ----------------");
        int dem = 0;
        String tk = tuKhoa.toLowerCase();
        for (NhanVien nv : dsNV.getDanhSach()) {
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
