package tienich;

import model.*;
import quanly.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

// Lop xu ly doc va ghi toan bo du lieu he thong ra tap tin van ban .txt
// Su dung co che ghi file tam .tmp va atomic move de bao ve an toan du lieu
public class DocGhiFile {
    private static String DATA_DIR = "data";
    private boolean coLoiDocFile = false;

    public DocGhiFile() {
        xacDinhThuMucData();
    }

    // Tu dong nhan dien thu muc data du nguoi dung dung o thu muc goc hay dung trong src
    private void xacDinhThuMucData() {
        if (new File("../data").exists() || new File("Main.java").exists()) {
            DATA_DIR = "../data";
        } else {
            DATA_DIR = "data";
        }
    }

    public boolean isCoLoiDocFile() { return coLoiDocFile; }
    public void setCoLoiDocFile(boolean coLoiDocFile) { this.coLoiDocFile = coLoiDocFile; }

    // Khoi tao du lieu: neu chua co file thi tu tao du lieu mau, neu da co thi doc file vao he thong
    public void khoiTaoDuLieu(QuanLyDanhMuc qlDM, QuanLyChuyenBay qlCB, QuanLyKhachHang qlKH,
                              QuanLyNhanVien qlNV, QuanLyVe qlVe, QuanLyHoaDon qlHD) {
        xacDinhThuMucData();
        try { Files.createDirectories(Paths.get(DATA_DIR)); } catch (IOException ignored) {}
        if (!new File(DATA_DIR + "/quocgia.txt").exists()) {
            System.out.println("Khoi tao du lieu mau lan dau vao thu muc data...");
            taoDuLieuMau(qlDM, qlCB, qlKH, qlNV, qlVe, qlHD);
            luuTatCa(qlDM, qlCB, qlKH, qlNV, qlVe, qlHD);
        } else {
            System.out.println("Dang doc du lieu tu thu muc data...");
            docTatCa(qlDM, qlCB, qlKH, qlNV, qlVe, qlHD);
        }
    }

    private void taoDuLieuMau(QuanLyDanhMuc qlDM, QuanLyChuyenBay qlCB, QuanLyKhachHang qlKH,
                              QuanLyNhanVien qlNV, QuanLyVe qlVe, QuanLyHoaDon qlHD) {
        qlDM.themQuocGia(new QuocGia("VN", "Viet Nam"));
        qlDM.themQuocGia(new QuocGia("JP", "Nhat Ban"));
        qlDM.themDiaDiem(new DiaDiem("DD01", "Ha Noi", "Thanh pho", "VN"));
        qlDM.themDiaDiem(new DiaDiem("DD02", "TP. Ho Chi Minh", "Thanh pho", "VN"));
        qlDM.themDiaDiem(new DiaDiem("DD03", "Tokyo", "Thanh pho", "JP"));
        qlDM.themSanBay(new SanBay("HAN", "San bay Noi Bai", "DD01", "Soc Son, Ha Noi", true));
        qlDM.themSanBay(new SanBay("SGN", "San bay Tan Son Nhat", "DD02", "Tan Binh, TP.HCM", true));
        qlDM.themSanBay(new SanBay("NRT", "San bay Narita", "DD03", "Narita, Chiba, Tokyo", true));
        qlDM.themHang(new HangHangKhong("VN", "Vietnam Airlines", "19001100", "contact@vietnamairlines.com", true));

        ArrayList<String> ghe = new ArrayList<String>(Arrays.asList("1A","1B","1C","1D","1E","1F","2A","2B","2C","2D","2E","2F","3A","3B","3C","3D","3E","3F"));
        qlDM.themMayBay(new MayBay("VN-A321", "VN", "Airbus A321", "Phan luc", 18, ghe, "SAN_SANG"));

        // 1 chuyen bay qua khu de demo chan huy ve khi da cat canh
        qlCB.themChuyenBay(new ChuyenBayNoiDia("VN100", "Ha Noi - TP.HCM", "VN-A321", "VN", "HAN", "SGN", LocalDate.of(2026, 10, 25), LocalTime.of(8, 0), LocalDate.of(2026, 10, 25), LocalTime.of(10, 15), 1500000.0, 18, 1, 17, 1500000.0, "DONG_BAN", "Bay thang", true, 7));
        // 1 chuyen bay cuoi thang 11
        qlCB.themChuyenBay(new ChuyenBayNoiDia("VN123", "Ha Noi - TP.HCM", "VN-A321", "VN", "HAN", "SGN", LocalDate.of(2026, 11, 28), LocalTime.of(8, 0), LocalDate.of(2026, 11, 28), LocalTime.of(10, 15), 1500000.0, 18, 2, 16, 3000000.0, "MO_BAN", "Bay thang", true, 7));
        // 3 chuyen bay trong thang 12
        qlCB.themChuyenBay(new ChuyenBayNoiDia("VN125", "Ha Noi - TP.HCM", "VN-A321", "VN", "HAN", "SGN", LocalDate.of(2026, 12, 5), LocalTime.of(14, 0), LocalDate.of(2026, 12, 5), LocalTime.of(16, 15), 1650000.0, 18, 0, 18, 0.0, "MO_BAN", "Bay chieu", true, 7));
        qlCB.themChuyenBay(new ChuyenBayQuocTe("VN456", "Ha Noi - Tokyo", "VN-A321", "VN", "HAN", "NRT", LocalDate.of(2026, 12, 15), LocalTime.of(23, 30), LocalDate.of(2026, 12, 16), LocalTime.of(6, 45), 8500000.0, 18, 0, 18, 0.0, "MO_BAN", "Bay dem", "Nhat Ban", true, 6));
        qlCB.themChuyenBay(new ChuyenBayNoiDia("VN789", "TP.HCM - Ha Noi", "VN-A321", "VN", "SGN", "HAN", LocalDate.of(2026, 12, 22), LocalTime.of(9, 0), LocalDate.of(2026, 12, 22), LocalTime.of(11, 15), 1550000.0, 18, 0, 18, 0.0, "MO_BAN", "Bay sang", true, 7));

        qlNV.themNhanVien(new NhanVien("NV01", "Nguyen Van An", LocalDate.of(1995, 5, 15), "Nam", "Ha Noi", "0912345678", "an.nv@flight.vn", "Nhan vien ban ve", true, 3, 3, 4500000.0));
        qlNV.themNhanVien(new NhanVien("NV02", "Tran Thi Binh", LocalDate.of(1998, 9, 20), "Nu", "TP.HCM", "0987654321", "binh.tt@flight.vn", "Nhan vien ban ve", true, 0, 0, 0.0));
        qlKH.themKhachHang(new KhachHang("KH01", "Pham Quoc Cuong", LocalDate.of(1990, 1, 10), "Nam", "Viet Nam", "Ha Noi", "0901234567", "cuong.pq@gmail.com", "001090012345", "B1234567", LocalDate.of(2030, 12, 31), "V9876543", LocalDate.of(2028, 12, 31), 3, 4500000.0));
        qlKH.themKhachHang(new KhachHang("KH02", "Le Hoang Nam", LocalDate.of(2018, 5, 1), "Nam", "Viet Nam", "TP.HCM", "0934567890", "nam.lh@gmail.com", "079092001122", "C7654321", LocalDate.of(2029, 6, 30), "", null, 0, 0.0));

        qlVe.themVe(new Ve("VE001", "VN100", "1A", "PHO_THONG", "THUONG", 1500000.0, "HIEU_LUC"));
        qlVe.themVe(new Ve("VE002", "VN123", "1A", "THUONG_GIA", "THUONG", 1500000.0, "HIEU_LUC"));
        qlVe.themVe(new Ve("VE003", "VN123", "2A", "PHO_THONG", "TRE_EM", 1500000.0, "HIEU_LUC"));

        qlHD.themHoaDon(new HoaDon("HD001", "KH01", "NV01", "VN100", LocalDateTime.of(2026, 10, 20, 8, 0), 1, 1500000.0, "TIEN_MAT", "HIEU_LUC"));
        qlHD.themHoaDon(new HoaDon("HD002", "KH01", "NV01", "VN123", LocalDateTime.of(2026, 11, 1, 22, 56), 1, 1500000.0, "TIEN_MAT", "HIEU_LUC"));
        qlHD.themHoaDon(new HoaDon("HD003", "KH01", "NV01", "VN123", LocalDateTime.of(2026, 11, 1, 22, 56), 1, 1500000.0, "TIEN_MAT", "HIEU_LUC"));

        qlHD.themChiTiet(new ChiTietHoaDon("HD001", "VE001", "KH01", "THUONG", 1500000.0, 1, 1500000.0));
        qlHD.themChiTiet(new ChiTietHoaDon("HD002", "VE002", "KH01", "THUONG", 1500000.0, 1, 1500000.0));
        qlHD.themChiTiet(new ChiTietHoaDon("HD003", "VE003", "KH02", "TRE_EM", 1500000.0, 1, 1500000.0));
    }

    // Doc cac dong tu tap tin va tach cac truong du lieu theo ky tu phan cach '|'
    private ArrayList<String[]> docCacDong(String tenFile, int minCols) {
        ArrayList<String[]> res = new ArrayList<String[]>();
        File f = new File(DATA_DIR + "/" + tenFile);
        if (!f.exists()) return res;
        int d = 0;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8))) {
            String l;
            while ((l = br.readLine()) != null) {
                d++; if (l.trim().isEmpty()) continue;
                String[] p = l.split("\\|", -1);
                if (p.length < minCols) { System.out.println("Loi doc file '" + tenFile + "' dong " + d); this.coLoiDocFile = true; continue; }
                res.add(p);
            }
        } catch (Exception e) { System.out.println("Loi file '" + tenFile + "' dong " + d); this.coLoiDocFile = true; }
        return res;
    }

    public void docTatCa(QuanLyDanhMuc qlDM, QuanLyChuyenBay qlCB, QuanLyKhachHang qlKH, QuanLyNhanVien qlNV, QuanLyVe qlVe, QuanLyHoaDon qlHD) {
        for (String[] p : docCacDong("quocgia.txt", 2)) {
            try { qlDM.themQuocGia(new QuocGia(p[0], p[1])); } catch (Exception ignored) {}
        }
        for (String[] p : docCacDong("diadiem.txt", 4)) {
            try { qlDM.themDiaDiem(new DiaDiem(p[0], p[1], p[2], p[3])); } catch (Exception ignored) {}
        }
        for (String[] p : docCacDong("sanbay.txt", 5)) {
            try { qlDM.themSanBay(new SanBay(p[0], p[1], p[2], p[3], Boolean.parseBoolean(p[4]))); } catch (Exception ignored) {}
        }
        for (String[] p : docCacDong("hang.txt", 5)) {
            try { qlDM.themHang(new HangHangKhong(p[0], p[1], p[2], p[3], Boolean.parseBoolean(p[4]))); } catch (Exception ignored) {}
        }
        for (String[] p : docCacDong("maybay.txt", 7)) {
            try {
                ArrayList<String> gl = new ArrayList<String>();
                if (!p[5].trim().isEmpty()) for (String g : p[5].split(",")) gl.add(g.trim());
                qlDM.themMayBay(new MayBay(p[0], p[1], p[2], p[3], Integer.parseInt(p[4]), gl, p[6]));
            } catch (Exception ignored) {}
        }
        for (String[] p : docCacDong("khachhang.txt", 15)) {
            try {
                LocalDate ns = NhapLieu.parseNgayLinhHoat(p[2]), hHC = NhapLieu.parseNgayLinhHoat(p[10]), hV = NhapLieu.parseNgayLinhHoat(p[12]);
                qlKH.themKhachHang(new KhachHang(p[0], p[1], ns, p[3], p[4], p[5], p[6], p[7], p[8], p[9], hHC, p[11], hV, Integer.parseInt(p[13]), Double.parseDouble(p[14])));
            } catch (Exception ignored) {}
        }
        for (String[] p : docCacDong("nhanvien.txt", 12)) {
            try {
                LocalDate ns = NhapLieu.parseNgayLinhHoat(p[2]);
                qlNV.themNhanVien(new NhanVien(p[0], p[1], ns, p[3], p[4], p[5], p[6], p[7], Boolean.parseBoolean(p[8]), Integer.parseInt(p[9]), Integer.parseInt(p[10]), Double.parseDouble(p[11])));
            } catch (Exception ignored) {}
        }
        for (String[] p : docCacDong("chuyenbay.txt", 18)) {
            try {
                // Kiem tra doi tuong con la loai nao truoc khi doc cac thuoc tinh tuong ung
                String loaiChuyen = p[17];
                LocalDate d1 = NhapLieu.parseNgayLinhHoat(p[6]), d2 = NhapLieu.parseNgayLinhHoat(p[8]);
                LocalTime t1 = LocalTime.parse(p[7]), t2 = LocalTime.parse(p[9]);
                double g = Double.parseDouble(p[10]), rev = Double.parseDouble(p[14]);
                int tV = Integer.parseInt(p[11]), sB = Integer.parseInt(p[12]), cL = Integer.parseInt(p[13]);

                if ("NOI_DIA".equalsIgnoreCase(loaiChuyen) && p.length >= 20) {
                    // Lop con ChuyenBayNoiDia: doc them chapNhanVNEID2 va DoTuoiEmBeToiThieu
                    boolean vneid = Boolean.parseBoolean(p[18]);
                    int tuoiToiThieu = Integer.parseInt(p[19]);
                    qlCB.themChuyenBay(new ChuyenBayNoiDia(p[0], p[1], p[2], p[3], p[4], p[5], d1, t1, d2, t2, g, tV, sB, cL, rev, p[15], p[16], vneid, tuoiToiThieu));
                } else if ("QUOC_TE".equalsIgnoreCase(loaiChuyen) && p.length >= 21) {
                    // Lop con ChuyenBayQuocTe: doc them quocGiaDen, yeuCauKiemTraVisa va soThangHieuLucHoChieuToiThieu
                    String qgDen = p[18];
                    boolean visa = Boolean.parseBoolean(p[19]);
                    int hanHC = Integer.parseInt(p[20]);
                    qlCB.themChuyenBay(new ChuyenBayQuocTe(p[0], p[1], p[2], p[3], p[4], p[5], d1, t1, d2, t2, g, tV, sB, cL, rev, p[15], p[16], qgDen, visa, hanHC));
                }
            } catch (Exception ignored) {}
        }
        for (String[] p : docCacDong("ve.txt", 7)) {
            try { qlVe.themVe(new Ve(p[0], p[1], p[2], p[3], p[4], Double.parseDouble(p[5]), p[6])); } catch (Exception ignored) {}
        }
        for (String[] p : docCacDong("hoadon.txt", 9)) {
            try {
                LocalDateTime nl = NhapLieu.parseThoiGianLinhHoat(p[4]);
                if (nl == null) nl = LocalDateTime.now();
                qlHD.themHoaDon(new HoaDon(p[0], p[1], p[2], p[3], nl, Integer.parseInt(p[5]), Double.parseDouble(p[6]), p[7], p[8]));
            } catch (Exception ignored) {}
        }
        for (String[] p : docCacDong("chitiethoadon.txt", 7)) {
            try { qlHD.themChiTiet(new ChiTietHoaDon(p[0], p[1], p[2], p[3], Double.parseDouble(p[4]), Integer.parseInt(p[5]), Double.parseDouble(p[6]))); } catch (Exception ignored) {}
        }
    }

    // Ghi du lieu an toan: ghi ra file tam .tmp truoc, sau do hoan doi sang file chinh
    private boolean ghiFileAnToan(String path, ArrayList<String> lines) {
        if (coLoiDocFile) return false;
        Path tgt = Paths.get(path), tmp = Paths.get(path + ".tmp");
        try {
            try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tmp.toFile()), StandardCharsets.UTF_8))) {
                for (String l : lines) { bw.write(l); bw.newLine(); }
            }
            Files.move(tmp, tgt, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (Exception e) { System.out.println("Loi luu file " + path); return false; }
    }

    public void luuTatCa(QuanLyDanhMuc qlDM, QuanLyChuyenBay qlCB, QuanLyKhachHang qlKH, QuanLyNhanVien qlNV, QuanLyVe qlVe, QuanLyHoaDon qlHD) {
        if (coLoiDocFile) return;
        ArrayList<String> l1 = new ArrayList<String>(), l2 = new ArrayList<String>(), l3 = new ArrayList<String>(), l4 = new ArrayList<String>(), l5 = new ArrayList<String>();
        for (QuocGia qg : qlDM.getDanhSachQuocGia()) l1.add(qg.getMaQuocGia() + "|" + qg.getTenQuocGia());
        ghiFileAnToan(DATA_DIR + "/quocgia.txt", l1);
        for (DiaDiem dd : qlDM.getDanhSachDiaDiem()) l2.add(dd.getMaDiaDiem() + "|" + dd.getTenDiaDiem() + "|" + dd.getLoaiDiaDiem() + "|" + dd.getMaQuocGia());
        ghiFileAnToan(DATA_DIR + "/diadiem.txt", l2);
        for (SanBay sb : qlDM.getDanhSachSanBay()) l3.add(sb.getMaSanBay() + "|" + sb.getTenSanBay() + "|" + sb.getMaDiaDiem() + "|" + sb.getDiaChi() + "|" + sb.isTrangThaiHoatDong());
        ghiFileAnToan(DATA_DIR + "/sanbay.txt", l3);
        for (HangHangKhong h : qlDM.getDanhSachHang()) l4.add(h.getMaHang() + "|" + h.getTenHang() + "|" + h.getSoDienThoai() + "|" + h.getEmail() + "|" + h.isTrangThaiHoatDong());
        ghiFileAnToan(DATA_DIR + "/hang.txt", l4);
        for (MayBay mb : qlDM.getDanhSachMayBay()) {
            StringBuilder sg = new StringBuilder();
            for (int k = 0; k < mb.getDanhSachSoGhe().size(); k++) sg.append(k > 0 ? "," : "").append(mb.getDanhSachSoGhe().get(k));
            l5.add(mb.getMaMayBay() + "|" + mb.getMaHang() + "|" + mb.getTenMayBay() + "|" + mb.getLoaiMayBay() + "|" + mb.getTongSoGhe() + "|" + sg + "|" + mb.getTrangThai());
        }
        ghiFileAnToan(DATA_DIR + "/maybay.txt", l5);

        ArrayList<String> l6 = new ArrayList<String>(), l7 = new ArrayList<String>(), l8 = new ArrayList<String>(), l9 = new ArrayList<String>(), l10 = new ArrayList<String>(), l11 = new ArrayList<String>();
        for (KhachHang k : qlKH.getDanhSachKhachHang()) {
            String ns = NhapLieu.dinhDangNgay(k.getNgaySinh()), hHC = NhapLieu.dinhDangNgay(k.getNgayHetHanHoChieu()), hV = NhapLieu.dinhDangNgay(k.getNgayHetHanVisa());
            l6.add(k.getMaKH() + "|" + k.getHoTen() + "|" + ns + "|" + k.getGioiTinh() + "|" + k.getQuocTich() + "|" + k.getDiaChi() + "|" + k.getSoDienThoai() + "|" + k.getEmail() + "|" + k.getSoCCCD() + "|" + (k.getSoHoChieu() != null ? k.getSoHoChieu() : "") + "|" + hHC + "|" + (k.getSoVisa() != null ? k.getSoVisa() : "") + "|" + hV + "|" + k.getTongSoVeDaMua() + "|" + k.getTongTienDaMua());
        }
        ghiFileAnToan(DATA_DIR + "/khachhang.txt", l6);
        for (NhanVien n : qlNV.getDanhSachNhanVien()) {
            String ns = NhapLieu.dinhDangNgay(n.getNgaySinh());
            l7.add(n.getMaNV() + "|" + n.getHoTen() + "|" + ns + "|" + n.getGioiTinh() + "|" + n.getDiaChi() + "|" + n.getSoDienThoai() + "|" + n.getEmail() + "|" + n.getChucVu() + "|" + n.isTrangThaiLamViec() + "|" + n.getTongSoHoaDonDaLap() + "|" + n.getTongSoVeDaBan() + "|" + n.getTongTienDaBan());
        }
        ghiFileAnToan(DATA_DIR + "/nhanvien.txt", l7);
        for (ChuyenBay c : qlCB.getDanhSachChuyenBay()) {
            String dDi = NhapLieu.dinhDangNgay(c.getNgayDi());
            String dDen = NhapLieu.dinhDangNgay(c.getNgayDen());
            String b = c.getMaCB() + "|" + c.getTenChuyenBay() + "|" + c.getMaMayBay() + "|" + c.getMaHang() + "|" + c.getMaSanBayDi() + "|" + c.getMaSanBayDen() + "|" + dDi + "|" + c.getGioDi() + "|" + dDen + "|" + c.getGioDen() + "|" + c.getGiaVeCoBan() + "|" + c.getTongSoVeMoBan() + "|" + c.getSoVeDaBan() + "|" + c.getSoVeConLai() + "|" + c.getTongDoanhThu() + "|" + c.getTrangThai() + "|" + c.getGhiChu() + "|" + c.getLoaiChuyen();
            if (c instanceof ChuyenBayNoiDia) l8.add(b + "|" + ((ChuyenBayNoiDia) c).isChapNhanVNEID2() + "|" + ((ChuyenBayNoiDia) c).getDoTuoiEmBeToiThieu());
            else if (c instanceof ChuyenBayQuocTe) l8.add(b + "|" + ((ChuyenBayQuocTe) c).getQuocGiaDen() + "|" + ((ChuyenBayQuocTe) c).isYeuCauKiemTraVisa() + "|" + ((ChuyenBayQuocTe) c).getSoThangHieuLucHoChieuToiThieu());
        }
        ghiFileAnToan(DATA_DIR + "/chuyenbay.txt", l8);
        for (Ve v : qlVe.getDanhSachVe()) l9.add(v.getMaVe() + "|" + v.getMaCB() + "|" + v.getSoGhe() + "|" + v.getHangVe() + "|" + v.getLoaiVe() + "|" + v.getGiaVe() + "|" + v.getTrangThai());
        ghiFileAnToan(DATA_DIR + "/ve.txt", l9);
        for (HoaDon hd : qlHD.getDanhSachHoaDon()) {
            String nl = NhapLieu.dinhDangThoiGian(hd.getNgayLap());
            l10.add(hd.getMaHD() + "|" + hd.getMaKH() + "|" + hd.getMaNV() + "|" + hd.getMaCB() + "|" + nl + "|" + hd.getSoLuongVe() + "|" + hd.getTongTien() + "|" + hd.getPhuongThucThanhToan() + "|" + hd.getTrangThai());
        }
        ghiFileAnToan(DATA_DIR + "/hoadon.txt", l10);
        for (ChiTietHoaDon ct : qlHD.getDanhSachChiTiet()) l11.add(ct.getMaHD() + "|" + ct.getMaVe() + "|" + ct.getMaKH() + "|" + ct.getLoaiVe() + "|" + ct.getDonGiaVe() + "|" + ct.getSoLuong() + "|" + ct.getTongTien());
        ghiFileAnToan(DATA_DIR + "/chitiethoadon.txt", l11);
    }
}
