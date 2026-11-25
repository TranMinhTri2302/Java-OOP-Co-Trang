import model.*;
import quanly.*;
import tienich.*;

// Lop dieu phoi he thong va menu console
// Dong vai tro la Controller trung gian dieu phoi giua giao dien Menu va cac lop quan ly nghiep vu
// HeThong chi goi cac ham nghiep vu tu cac lop quan ly, khong truc tiep xu ly logic hay luu tru du lieu
public class HeThong {
    private QuanLyDanhMuc qlDanhMuc = new QuanLyDanhMuc();
    private QuanLyChuyenBay qlChuyenBay = new QuanLyChuyenBay();
    private QuanLyKhachHang qlKhachHang = new QuanLyKhachHang();
    private QuanLyNhanVien qlNhanVien = new QuanLyNhanVien();
    private QuanLyVe qlVe = new QuanLyVe();
    private QuanLyHoaDon qlHoaDon = new QuanLyHoaDon();
    private QuanLyDatVe qlDatVe = new QuanLyDatVe();
    private DocGhiFile docGhiFile = new DocGhiFile();

    public void khoiDong() {
        docGhiFile.khoiTaoDuLieu(qlDanhMuc, qlChuyenBay, qlKhachHang, qlNhanVien, qlVe, qlHoaDon);
        tinhLaiThongKe();
        chayMenuChinh();
    }

    // Tinh toan lai toan bo cac chi so thong ke (ve da ban, ve con lai, doanh thu, tong tien da mua)
    public void tinhLaiThongKe() {
        for (int i = 0; i < qlChuyenBay.getSoLuong(); i++) {
            ChuyenBay cb = qlChuyenBay.getChuyenBay(i);
            int daBan = qlVe.demVeHieuLucTheoChuyen(cb.getMaCB());
            cb.setSoVeDaBan(daBan);
            cb.setSoVeConLai(cb.getTongSoVeMoBan() - daBan);
            cb.setTongDoanhThu(qlHoaDon.tinhDoanhThuChuyenBay(cb.getMaCB()));
        }
        for (int i = 0; i < qlKhachHang.getSoLuong(); i++) {
            KhachHang kh = qlKhachHang.getKhachHang(i);
            kh.setTongSoVeDaMua(qlHoaDon.tinhSoVeKhachMua(kh.getMaKH()));
            kh.setTongTienDaMua(qlHoaDon.tinhTienKhachMua(kh.getMaKH()));
        }
        for (int i = 0; i < qlNhanVien.getSoLuong(); i++) {
            NhanVien nv = qlNhanVien.getNhanVien(i);
            nv.setTongSoHoaDonDaLap(qlHoaDon.demTongHoaDonNhanVien(nv.getMaNV()));
            nv.setTongSoVeDaBan(qlHoaDon.tinhSoVeNhanVienBan(nv.getMaNV()));
            nv.setTongTienDaBan(qlHoaDon.tinhTienNhanVienBan(nv.getMaNV()));
        }
    }

    private void luuHeThong() {
        tinhLaiThongKe();
        docGhiFile.luuTatCa(qlDanhMuc, qlChuyenBay, qlKhachHang, qlNhanVien, qlVe, qlHoaDon);
    }

    private void chayMenuChinh() {
        while (true) {
            System.out.println("\n==================================================");
            System.out.println("                    MENU CHINH");
            System.out.println("==================================================");
            System.out.println("1. Dat ve chuyen bay (Chon Noi dia / Quoc te)");
            System.out.println("2. Lap hoa don ban ve tai quay (Nhan vien lap theo mau)");
            System.out.println("3. Tra cuu thong tin (Ve, Hoa don)");
            System.out.println("4. Quan ly khach hang");
            System.out.println("5. Quan ly nhan vien");
            System.out.println("6. Quan ly chuyen bay");
            System.out.println("7. Xem danh muc he thong");
            System.out.println("8. Thong ke doanh thu & bao cao (co 3 nam gan nhat)");
            System.out.println("9. Huy hoa don");
            System.out.println("10. Xuat hoa don ra file .txt");
            System.out.println("11. Luu du lieu he thong");
            System.out.println("0. Thoat chuong trinh");
            System.out.println("==================================================");
            int c = NhapLieu.nhapSoNguyen("Lua chon cua ban", 0, 11);

            switch (c) {
                case 1:
                    if (qlDatVe.xuLyDatVe(qlDanhMuc, qlChuyenBay, qlKhachHang, qlNhanVien, qlVe, qlHoaDon)) {
                        luuHeThong();
                    }
                    break;
                case 2:
                    if (qlHoaDon.themHoaDonTheoMauChung(qlKhachHang, qlNhanVien, qlChuyenBay, qlVe)) {
                        luuHeThong();
                    }
                    break;
                case 3:
                    qlHoaDon.menuTraCuu(qlVe, qlKhachHang, qlChuyenBay);
                    break;
                case 4:
                    qlKhachHang.menuQuanLy(qlHoaDon);
                    luuHeThong();
                    break;
                case 5:
                    qlNhanVien.menuQuanLy(qlHoaDon);
                    luuHeThong();
                    break;
                case 6:
                    qlChuyenBay.menuQuanLy(qlDanhMuc, qlVe, qlHoaDon);
                    luuHeThong();
                    break;
                case 7:
                    qlDanhMuc.menuXemDanhMuc();
                    break;
                case 8:
                    qlHoaDon.menuThongKe(qlChuyenBay, qlNhanVien);
                    break;
                case 9:
                    if (qlHoaDon.xuLyHuyHoaDon(qlChuyenBay, qlVe)) {
                        luuHeThong();
                    }
                    break;
                case 10:
                    qlHoaDon.xuatHoaDonRaFile(NhapLieu.nhapChuoi("Nhap ma hoa don can xuat"), qlKhachHang, qlNhanVien, qlChuyenBay, qlVe);
                    break;
                case 11:
                    luuHeThong();
                    System.out.println("Luu thanh cong!");
                    break;
                case 0:
                    luuHeThong();
                    System.out.println("Tam biet!");
                    return;
            }
        }
    }
}
