package quanly;

import model.KhachHang;
import tienich.NhapLieu;
import java.time.LocalDate;
import java.util.ArrayList;

// Lop quan ly danh sach khach hang va cac nghiep vu them, sua, xoa, tim kiem
public class QuanLyKhachHang {
    private ArrayList<KhachHang> danhSachKhachHang = new ArrayList<KhachHang>();

    public ArrayList<KhachHang> getDanhSachKhachHang() { return danhSachKhachHang; }
    public int getSoLuong() { return danhSachKhachHang.size(); }
    public KhachHang getKhachHang(int index) { return (index >= 0 && index < danhSachKhachHang.size()) ? danhSachKhachHang.get(index) : null; }
    public void themKhachHang(KhachHang kh) { if (kh != null && timKhachHang(kh.getMaKH()) == null) danhSachKhachHang.add(kh); }

    public KhachHang timKhachHang(String maKH) {
        if (maKH == null) return null;
        for (KhachHang kh : danhSachKhachHang) {
            if (maKH.equalsIgnoreCase(kh.getMaKH())) return kh;
        }
        return null;
    }

    // Tra cuu khach hang theo CCCD hoac Ma KH hoac SDT de tai su dung thong tin bao mat
    public KhachHang timKhachHangTheoCCCDHoacMa(String kw) {
        if (kw == null || kw.trim().isEmpty()) return null;
        String k = kw.trim();
        for (KhachHang kh : danhSachKhachHang) {
            if (k.equalsIgnoreCase(kh.getMaKH()) ||
                (kh.getSoCCCD() != null && k.equalsIgnoreCase(kh.getSoCCCD())) ||
                (kh.getSoDienThoai() != null && k.equalsIgnoreCase(kh.getSoDienThoai()))) {
                return kh;
            }
        }
        return null;
    }

    public static String kiemTraHopLe(String maKH, String hoTen, LocalDate ngaySinh, String email, String soDienThoai) {
        if (maKH == null || maKH.trim().isEmpty()) return "Ma khach hang khong duoc de trong!";
        if (hoTen == null || hoTen.trim().isEmpty()) return "Ho ten khong duoc de trong!";
        if (ngaySinh != null && ngaySinh.isAfter(LocalDate.now())) return "Ngay sinh khong duoc o tuong lai!";
        if (email == null || !email.contains("@")) return "Email phai chua ky tu '@'!";
        if (soDienThoai == null || !soDienThoai.matches("\\d{9,11}")) return "So dien thoai phai tu 9 den 11 chu so!";
        return "";
    }

    // Rang buoc toan ven: khong duoc xoa khach hang da tung co hoa don hoac ve bay
    public boolean xoaKhachHang(String maKH, QuanLyHoaDon qlHD) {
        KhachHang kh = timKhachHang(maKH);
        if (kh == null) {
            System.out.println("Loi: Khong tim thay ma " + maKH);
            return false;
        }
        if (qlHD.kiemTraMaKHDaDung(maKH)) {
            System.out.println("Loi: Khong duoc xoa khach da co hoa don hoac ve bay tham chieu (ke ca da huy)!");
            return false;
        }
        danhSachKhachHang.remove(kh);
        System.out.println("Xoa khach hang " + maKH + " thanh cong!");
        return true;
    }

    public void hienThiDanhSach() {
        System.out.println("\n================ DANH SACH KHACH HANG ================");
        if (danhSachKhachHang.isEmpty()) {
            System.out.println("Danh sach trong.");
            return;
        }
        for (int i = 0; i < danhSachKhachHang.size(); i++) {
            System.out.printf("[%d] %s%n", (i + 1), danhSachKhachHang.get(i));
        }
    }

    public void timKiem(String tuKhoa) {
        System.out.println("\n---------------- TIM KIEM KHACH HANG: " + tuKhoa + " ----------------");
        int dem = 0;
        String tk = tuKhoa.toLowerCase();
        for (KhachHang kh : danhSachKhachHang) {
            if (kh.getMaKH().toLowerCase().contains(tk) || kh.getHoTen().toLowerCase().contains(tk)) {
                System.out.println(kh);
                dem++;
            }
        }
        if (dem == 0) System.out.println("Khong tim thay ket qua phu hop.");
    }

    public KhachHang nhapKhachHangMoi() {
        System.out.println("\n---------------- THEM KHACH HANG MOI ----------------");
        String maKH;
        while (true) {
            maKH = NhapLieu.nhapChuoi("Nhap ma KH (vi du: KH03)");
            if (timKhachHang(maKH) == null) break;
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
        String qt = NhapLieu.nhapChuoi("Quoc tich");
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
        String cccd = NhapLieu.nhapChuoiCoTheRong("So CCCD");
        String hc = NhapLieu.nhapChuoiCoTheRong("So ho chieu");
        LocalDate hanHC = (!hc.isEmpty()) ? NhapLieu.nhapNgay("Ngay het han ho chieu") : null;
        String visa = NhapLieu.nhapChuoiCoTheRong("So visa (neu co)");
        LocalDate hanVisa = null;

        KhachHang kh = new KhachHang(maKH, hoTen, ns, gt, qt, dc, sdt, email, cccd, hc, hanHC, visa, hanVisa, 0, 0.0);
        themKhachHang(kh);
        System.out.println("Them khach hang moi thanh cong!");
        return kh;
    }

    // Chon khach hang bao mat: khong in toan bo danh sach ma cho phep nhap moi hoac tra cuu CCCD/Ma KH
    public KhachHang chonHoacThemKhachHang(String tieuDe) {
        System.out.println("\n==================================================");
        System.out.println("          " + tieuDe);
        System.out.println("==================================================");
        System.out.println("1. Nhap thong tin khach hang moi");
        System.out.println("2. Tra cuu khach hang cu (nhap CCCD hoac Ma KH)");
        System.out.println("0. Huy thao tac");
        System.out.println("==================================================");
        int c = NhapLieu.nhapSoNguyen("Lua chon cua ban", 0, 2);
        if (c == 1) {
            return nhapKhachHangMoi();
        } else if (c == 2) {
            String kw = NhapLieu.nhapChuoi("Nhap so CCCD hoac Ma KH");
            KhachHang kh = timKhachHangTheoCCCDHoacMa(kw);
            if (kh != null) {
                System.out.println("-> Tim thay: " + kh.getHoTen() + " (Ma: " + kh.getMaKH() + ", CCCD: " + kh.getSoCCCD() + ")");
                if (NhapLieu.nhapXacNhan("Xac nhan su dung thong tin nay?")) {
                    return kh;
                }
            } else {
                System.out.println("Khong tim thay thong tin phu hop! Chuyen sang them moi...");
                return nhapKhachHangMoi();
            }
        }
        return null;
    }

    public void menuQuanLy(QuanLyHoaDon qlHD) {
        while (true) {
            System.out.println("\n==================================================");
            System.out.println("               QUAN LY KHACH HANG");
            System.out.println("==================================================");
            System.out.println("1. Xem danh sach khach hang");
            System.out.println("2. Tim kiem khach hang");
            System.out.println("3. Them khach hang moi");
            System.out.println("4. Sua thong tin khach hang");
            System.out.println("5. Xoa khach hang");
            System.out.println("0. Quay lai menu chinh");
            System.out.println("==================================================");
            int c = NhapLieu.nhapSoNguyen("Lua chon cua ban", 0, 5);
            if (c == 0) break;
            if (c == 1) hienThiDanhSach();
            else if (c == 2) timKiem(NhapLieu.nhapChuoi("Nhap tu khoa tim kiem"));
            else if (c == 3) nhapKhachHangMoi();
            else if (c == 4) suaKhachHangConsole();
            else if (c == 5) xoaKhachHang(NhapLieu.nhapChuoi("Nhap ma can xoa"), qlHD);
        }
    }

    private void suaKhachHangConsole() {
        String ma = NhapLieu.nhapChuoi("Nhap ma khach hang can sua");
        KhachHang kh = timKhachHang(ma);
        if (kh == null) {
            System.out.println("Khong tim thay: " + ma);
            return;
        }
        System.out.println("Thong tin hien tai: " + kh);
        String ten = NhapLieu.nhapChuoiCoTheRong("Ho ten (" + kh.getHoTen() + ")");
        if (!ten.isEmpty()) kh.setHoTen(ten);
        LocalDate ns = NhapLieu.nhapNgayCoTheRong("Ngay sinh (" + NhapLieu.dinhDangNgay(kh.getNgaySinh()) + ")");
        if (ns != null && !ns.isAfter(LocalDate.now())) kh.setNgaySinh(ns);
        String sdt = NhapLieu.nhapChuoiCoTheRong("SDT (" + kh.getSoDienThoai() + ")");
        if (!sdt.isEmpty() && sdt.matches("\\d{9,11}")) kh.setSoDienThoai(sdt);
        String email = NhapLieu.nhapChuoiCoTheRong("Email (" + kh.getEmail() + ")");
        if (!email.isEmpty() && email.contains("@")) kh.setEmail(email);
        String hc = NhapLieu.nhapChuoiCoTheRong("So ho chieu (" + (kh.getSoHoChieu() != null ? kh.getSoHoChieu() : "") + ")");
        if (!hc.isEmpty()) {
            kh.setSoHoChieu(hc);
            kh.setNgayHetHanHoChieu(NhapLieu.nhapNgay("Ngay het han ho chieu"));
        }
        String visa = NhapLieu.nhapChuoiCoTheRong("So visa (" + (kh.getSoVisa() != null ? kh.getSoVisa() : "") + ")");
        if (!visa.isEmpty()) {
            kh.setSoVisa(visa);
        }
        System.out.println("Cap nhat thong tin thanh cong!");
    }
}
