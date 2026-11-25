package quanly;

import danhsach.DanhSachKhachHang;
import model.KhachHang;
import tienich.NhapLieu;
import java.time.LocalDate;
import java.util.ArrayList;

// Lop quan ly nghiep vu va giao dien console khach hang, su dung DanhSachKhachHang de luu tru
public class QuanLyKhachHang {
    private DanhSachKhachHang dsKH;

    public QuanLyKhachHang() {
        this.dsKH = new DanhSachKhachHang();
    }

    public QuanLyKhachHang(DanhSachKhachHang dsKH) {
        this.dsKH = (dsKH != null) ? dsKH : new DanhSachKhachHang();
    }

    public DanhSachKhachHang getDsKH() { return dsKH; }
    public ArrayList<KhachHang> getDanhSachKhachHang() { return dsKH.getDanhSach(); }
    public int getSoLuong() { return dsKH.getSoLuong(); }
    public KhachHang getKhachHang(int index) { return dsKH.getKhachHang(index); }
    public void themKhachHang(KhachHang kh) { dsKH.themKhachHang(kh); }
    public KhachHang timKhachHang(String maKH) { return dsKH.timKhachHang(maKH); }
    public KhachHang timKhachHangTheoCCCDHoacMa(String kw) { return dsKH.timTheoCCCDHoacMa(kw); }

    public static String kiemTraHopLe(String maKH, String hoTen, LocalDate ngaySinh, String email, String soDienThoai) {
        if (maKH == null || maKH.trim().isEmpty()) return "Ma khach hang khong duoc de trong!";
        if (hoTen == null || hoTen.trim().isEmpty()) return "Ho ten khong duoc de trong!";
        if (ngaySinh != null && ngaySinh.isAfter(LocalDate.now())) return "Ngay sinh khong duoc o tuong lai!";
        if (email == null || !email.contains("@")) return "Email phai chua ky tu '@'!";
        if (soDienThoai == null || !soDienThoai.matches("\\d{9,11}")) return "So dien thoai phai tu 9 den 11 chu so!";
        return "";
    }

    public boolean suaKhachHang(String maKH, String tenMoi, String sdtMoi) {
        return dsKH.suaKhachHang(maKH, tenMoi, sdtMoi);
    }

    public boolean xoaKhachHang(String maKH) {
        boolean ok = dsKH.xoaKhachHang(maKH);
        if (ok) {
            System.out.println("Xoa khach hang " + maKH + " thanh cong!");
        } else {
            System.out.println("Loi: Khong tim thay ma " + maKH);
        }
        return ok;
    }

    public boolean xoaKhachHang(String maKH, QuanLyHoaDon qlHD) {
        return xoaKhachHang(maKH);
    }

    public void hienThiDanhSach() {
        dsKH.xuatDanhSach();
    }

    public void timKiem(String tuKhoa) {
        System.out.println("\n---------------- TIM KIEM NANG CAO (TEN HOAC MA KH): " + tuKhoa + " ----------------");
        int dem = 0;
        String tk = tuKhoa.toLowerCase();
        for (KhachHang kh : dsKH.getDanhSach()) {
            if (kh.getMaKH().toLowerCase().contains(tk) || kh.getHoTen().toLowerCase().contains(tk)) {
                System.out.println(kh);
                dem++;
            }
        }
        if (dem == 0) System.out.println("Khong tim thay ket qua phu hop.");
    }

    public ArrayList<KhachHang> timKhachHangTheoChiTieu(double minTien) {
        ArrayList<KhachHang> kq = new ArrayList<KhachHang>();
        for (KhachHang kh : dsKH.getDanhSach()) {
            if (kh.getTongTienDaMua() >= minTien) kq.add(kh);
        }
        return kq;
    }

    public void timKiemNangCaoTheoChiTieuConsole(double minTien) {
        ArrayList<KhachHang> ds = timKhachHangTheoChiTieu(minTien);
        System.out.printf("\n------- KHACH HANG CO CHI TIEU TU %,.0f VND TRO LEN (Tong: %d) -------%n", minTien, ds.size());
        if (ds.isEmpty()) {
            System.out.println("Khong co khach hang nao dat muc chi tieu nay.");
            return;
        }
        for (KhachHang kh : ds) System.out.println(kh);
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
        LocalDate hanVisa = (!visa.isEmpty()) ? NhapLieu.nhapNgay("Ngay het han visa") : null;

        KhachHang kh = new KhachHang(maKH, hoTen, ns, gt, qt, dc, sdt, email, cccd, hc, hanHC, visa, hanVisa, 0, 0.0);
        themKhachHang(kh);
        System.out.println("Them khach hang moi thanh cong!");
        return kh;
    }

    public KhachHang chonHoacThemKhachHang(String tieuDe) {
        while (true) {
            System.out.println("\n==================================================");
            System.out.println("          " + tieuDe);
            System.out.println("==================================================");
            System.out.println("1. Nhap thong tin khach hang moi");
            System.out.println("2. Tra cuu khach hang cu (nhap CCCD hoac Ma KH)");
            System.out.println("0. Huy thao tac");
            System.out.println("==================================================");
            int c = NhapLieu.nhapSoNguyen("Lua chon cua ban", 0, 2);
            if (c == 0) return null;
            if (c == 1) return nhapKhachHangMoi();
            if (c == 2) {
                while (true) {
                    String kw = NhapLieu.nhapChuoi("Nhap so CCCD hoac Ma KH (nhap 0 de quay lai)");
                    if ("0".equals(kw)) break;
                    KhachHang kh = timKhachHangTheoCCCDHoacMa(kw);
                    if (kh != null) {
                        System.out.println("-> Tim thay: " + kh.getHoTen() + " (Ma: " + kh.getMaKH() + ", CCCD: " + kh.getSoCCCD() + ")");
                        if (NhapLieu.nhapXacNhan("Xac nhan su dung thong tin nay?")) {
                            return kh;
                        }
                    } else {
                        System.out.println("Loi: Khong tim thay khach hang voi thong tin '" + kw + "'. Vui long thu lai hoac nhap 0 de quay lai!");
                    }
                }
            }
        }
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
            else if (c == 2) menuTimKiemConsole();
            else if (c == 3) nhapKhachHangMoi();
            else if (c == 4) suaKhachHangConsole();
            else if (c == 5) xoaKhachHang(NhapLieu.nhapChuoi("Nhap ma can xoa"), qlHD);
        }
    }

    private void menuTimKiemConsole() {
        System.out.println("\n--- CHON HINH THUC TIM KIEM KHACH HANG ---");
        System.out.println("1. Tim kiem co ban (theo ma KH)");
        System.out.println("2. Tim kiem nang cao (theo ten hoac ma KH)");
        System.out.println("3. Tim kiem nang cao (khach hang co tong hoa don/chi tieu tu X VND)");
        int loai = NhapLieu.nhapSoNguyen("Lua chon cua ban", 1, 3);
        if (loai == 1) {
            String ma = NhapLieu.nhapChuoi("Nhap ma KH can tim");
            KhachHang kh = timKhachHang(ma);
            System.out.println(kh != null ? ("-> Ket qua: " + kh) : ("Khong tim thay khach hang co ma: " + ma));
        } else if (loai == 2) {
            timKiem(NhapLieu.nhapChuoi("Nhap tu khoa ten hoac ma KH"));
        } else {
            double min = NhapLieu.nhapSoThuc("Nhap so tien toi thieu (vi du: 1000000)", 0, 1000000000);
            timKiemNangCaoTheoChiTieuConsole(min);
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
            kh.setNgayHetHanVisa(NhapLieu.nhapNgay("Ngay het han visa"));
        }
        System.out.println("Cap nhat thong tin thanh cong!");
    }
}
