package quanly;

import model.*;
import tienich.NhapLieu;
import java.time.LocalDateTime;
import java.util.ArrayList;

// Lop quan ly nghiep vu dat ve chuyen bay
// Thuc hien quy trinh dat ve voi buoc dau tien chon Loai chuyen bay (Noi dia / Quoc te)
// lam noi bat 100% tinh Ke thua (ChuyenBayNoiDia, ChuyenBayQuocTe) va Da hinh (kiemTraDieuKien, tinhGiaVe)
public class QuanLyDatVe {

    public boolean xuLyDatVe(QuanLyDanhMuc qlDM, QuanLyChuyenBay qlCB, QuanLyKhachHang qlKH,
                             QuanLyNhanVien qlNV, QuanLyVe qlVe, QuanLyHoaDon qlHD) {
        System.out.println("\n==================================================");
        System.out.println("            QUY TRINH DAT VE CHUYEN BAY");
        System.out.println("==================================================");
        System.out.println("BUOC 1: CHON LOAI CHUYEN BAY BAN MUON DAT");
        System.out.println("1. Chuyen bay NOI DIA (Trong nuoc - Kiem tra tuoi em be & VNeID muc 2)");
        System.out.println("2. Chuyen bay QUOC TE (Nuoc ngoai - Yeu cau Ho chieu, Visa)");
        System.out.println("0. Quay lai menu chinh");
        System.out.println("==================================================");
        int loaiChon = NhapLieu.nhapSoNguyen("Lua chon cua ban", 0, 2);
        if (loaiChon == 0) return false;

        LocalDateTime now = LocalDateTime.now();
        ArrayList<ChuyenBay> dsKhaDung = new ArrayList<ChuyenBay>();

        if (loaiChon == 1) {
            // Chuyen bay Noi dia: lay tu danh sach chuyen bay noi dia
            ArrayList<ChuyenBayNoiDia> dsND = qlCB.getDsCB().layDanhSachNoiDia();
            for (ChuyenBayNoiDia cb : dsND) {
                if ("MO_BAN".equalsIgnoreCase(cb.getTrangThai()) && cb.getSoVeConLai() > 0
                        && LocalDateTime.of(cb.getNgayDi(), cb.getGioDi()).isAfter(now)) {
                    dsKhaDung.add(cb);
                }
            }
        } else {
            // Chuyen bay Quoc te: lay tu danh sach chuyen bay quoc te
            ArrayList<ChuyenBayQuocTe> dsQT = qlCB.getDsCB().layDanhSachQuocTe();
            for (ChuyenBayQuocTe cb : dsQT) {
                if ("MO_BAN".equalsIgnoreCase(cb.getTrangThai()) && cb.getSoVeConLai() > 0
                        && LocalDateTime.of(cb.getNgayDi(), cb.getGioDi()).isAfter(now)) {
                    dsKhaDung.add(cb);
                }
            }
        }

        if (dsKhaDung.isEmpty()) {
            System.out.println("Hien tai khong co chuyen bay nao phu hop con ve o thoi diem tuong lai!");
            return false;
        }

        String tieuDeLoai = (loaiChon == 1) ? "CHUYEN BAY NOI DIA (TRONG NUOC)" : "CHUYEN BAY QUOC TE (NUOC NGOAI)";
        System.out.println("\n================ DANH SACH " + tieuDeLoai + " KHA DUNG ================");
        for (int i = 0; i < dsKhaDung.size(); i++) {
            ChuyenBay cb = dsKhaDung.get(i);
            String thongTinRieng = "";
            if (cb instanceof ChuyenBayNoiDia) {
                ChuyenBayNoiDia nd = (ChuyenBayNoiDia) cb;
                thongTinRieng = String.format(" | VNeID2: %s | Tuoi em be: >=%d ngay",
                        nd.isChapNhanVNEID2() ? "Co" : "Khong", nd.getDoTuoiEmBeToiThieu());
            } else if (cb instanceof ChuyenBayQuocTe) {
                ChuyenBayQuocTe qt = (ChuyenBayQuocTe) cb;
                thongTinRieng = String.format(" | Den: %s | Visa: %s | Han HC: >=%d thang",
                        qt.getQuocGiaDen(), qt.isYeuCauKiemTraVisa() ? "Bat buoc" : "Khong",
                        qt.getSoThangHieuLucHoChieuToiThieu());
            }
            System.out.printf("[%d] %-8s | %-18s | Tuyen: %s -> %s | Ngay: %s (%s - %s) | Gia: %,.0f VND | Con: %d/%d%s%n",
                    (i + 1), cb.getMaCB(), cb.getTenChuyenBay(), cb.getMaSanBayDi(), cb.getMaSanBayDen(),
                    NhapLieu.dinhDangNgay(cb.getNgayDi()), cb.getGioDi(), cb.getGioDen(),
                    cb.getGiaVeCoBan(), cb.getSoVeConLai(), cb.getTongSoVeMoBan(), thongTinRieng);
        }
        System.out.println("==========================================================================================================");

        int sttCB = NhapLieu.nhapSoNguyen("Chon STT chuyen bay ban muon dat (0 de thoat)", 0, dsKhaDung.size());
        if (sttCB == 0) return false;
        ChuyenBay cb = dsKhaDung.get(sttCB - 1);

        // Chon nguoi mua va nhan vien lap hoa don
        KhachHang buyer = qlKH.chonHoacThemKhachHang("CHON KHACH HANG MUA VE");
        if (buyer == null) return false;
        NhanVien nv = qlNV.chonNhanVienLapHoaDon();
        if (nv == null) return false;

        int n = NhapLieu.nhapSoNguyen("Nhap so luong ve can mua (So ve con lai: " + cb.getSoVeConLai() + ")", 1, cb.getSoVeConLai());
        KhachHang[] dsKhachBay = new KhachHang[n];
        String[] dsSoGhe = new String[n];
        String[] dsHangVe = new String[n];
        String[] dsLoaiVe = new String[n];
        double[] dsGiaVe = new double[n];
        MayBay mb = qlDM.timMayBay(cb.getMaMayBay());

        int batDauTu = 0;
        boolean nguoiMuaCoBay = NhapLieu.nhapXacNhan("Nguoi mua (" + buyer.getHoTen() + ") co truc tiep bay tren chuyen nay khong?");
        if (nguoiMuaCoBay) {
            if (qlVe.kiemTraKhachDaCoVeChuyenBay(buyer.getMaKH(), cb.getMaCB(), qlHD)) {
                System.out.println("Loi: Nguoi mua da co ve HIEU_LUC tren chuyen bay nay! Chuyen sang chon nguoi bay khac.");
            } else {
                // Da hinh: kiem tra dieu kien bay theo lop con tuong ung
                String err = cb.kiemTraDieuKien(buyer);
                if (!err.isEmpty()) {
                    System.out.println("Nguoi mua khong du dieu kien bay: " + err + ". Chuyen sang chon nguoi bay khac.");
                } else {
                    dsKhachBay[0] = buyer;
                    System.out.println("\n---------------- VE THU 1 / " + n + " (Nguoi bay: " + buyer.getHoTen() + ") ----------------");
                    System.out.println("Chon loai ve cho " + buyer.getHoTen() + ":");
                    System.out.println("1. Nguoi lon (THUONG)");
                    System.out.println("2. Tre em (TRE_EM)");
                    int cLoai = NhapLieu.nhapSoNguyen("Lua chon cua ban", 1, 2);
                    dsLoaiVe[0] = (cLoai == 2) ? "TRE_EM" : "THUONG";

                    String ghe = qlVe.chonGheTrongConsole(cb.getMaCB(), mb, dsSoGhe, 0);
                    if (ghe == null) return false;
                    dsSoGhe[0] = ghe;
                    dsHangVe[0] = qlVe.xacDinhHangVe(ghe);
                    // Tinh gia ve dua vao hang ve (Hang Thuong gia ap dung he so heSoThuongGia)
                    dsGiaVe[0] = cb.tinhGiaVe(dsHangVe[0]);
                    System.out.printf("-> Da chon ghe: %s (Hang: %s | Don gia: %,.0f VND)%n", ghe, dsHangVe[0], dsGiaVe[0]);
                    batDauTu = 1;
                }
            }
        }

        for (int i = batDauTu; i < n; i++) {
            System.out.println("\n---------------- VE THU " + (i + 1) + " / " + n + " ----------------");
            KhachHang p;
            while (true) {
                p = qlKH.chonHoacThemKhachHang("CHON NGUOI BAY CHO VE THU " + (i + 1));
                if (p == null) return false;
                if (qlVe.kiemTraKhachDaCoVeChuyenBay(p.getMaKH(), cb.getMaCB(), qlHD)) {
                    System.out.println("Loi: Khach da co ve HIEU_LUC tren chuyen bay nay!");
                    continue;
                }
                boolean daChon = false;
                for (int k = 0; k < i; k++) {
                    if (dsKhachBay[k].getMaKH().equalsIgnoreCase(p.getMaKH())) {
                        daChon = true;
                        break;
                    }
                }
                if (daChon) {
                    System.out.println("Loi: Khach nay da duoc chon cho mot ve khac trong lan mua nay!");
                    continue;
                }

                // The hien da hinh: kiem tra dieu kien bay tuong ung tung lop con
                String err = cb.kiemTraDieuKien(p);
                if (!err.isEmpty()) {
                    System.out.println("Tu choi: " + err);
                    continue;
                }
                break;
            }
            dsKhachBay[i] = p;

            System.out.println("Chon loai ve cho " + p.getHoTen() + ":");
            System.out.println("1. Nguoi lon (THUONG)");
            System.out.println("2. Tre em (TRE_EM)");
            int cLoai = NhapLieu.nhapSoNguyen("Lua chon cua ban", 1, 2);
            dsLoaiVe[i] = (cLoai == 2) ? "TRE_EM" : "THUONG";

            String ghe = qlVe.chonGheTrongConsole(cb.getMaCB(), mb, dsSoGhe, i);
            if (ghe == null) return false;
            dsSoGhe[i] = ghe;
            dsHangVe[i] = qlVe.xacDinhHangVe(ghe);
            // Da hinh tinh gia ve
            dsGiaVe[i] = cb.tinhGiaVe(dsHangVe[i]);
            System.out.printf("-> Da chon ghe: %s (Hang: %s | Don gia: %,.0f VND)%n", ghe, dsHangVe[i], dsGiaVe[i]);
        }

        double tongTien = 0;
        for (int i = 0; i < n; i++) {
            tongTien += dsGiaVe[i];
        }

        System.out.println("\n================ TOM TAT DON DAT VE ================");
        System.out.printf("Chuyen bay: %s (%s) | Nguoi mua: %s | Nhan vien lap: %s%n",
                cb.getMaCB(), cb.getTenChuyenBay(), buyer.getHoTen(), nv.getHoTen());
        System.out.printf("Loai chuyen: %s | So luong: %d ve | Tong thanh toan: %,.0f VND%n",
                cb.getLoaiChuyen(), n, tongTien);
        for (int i = 0; i < n; i++) {
            System.out.printf("  Ve %d: %-20s | Ghe: %-4s | Hang: %-10s | Loai: %-8s | Gia: %,.0f VND%n",
                    (i + 1), dsKhachBay[i].getHoTen(), dsSoGhe[i], dsHangVe[i], dsLoaiVe[i], dsGiaVe[i]);
        }

        System.out.println("\nChon phuong thuc thanh toan:");
        System.out.println("1. Tien mat (TIEN_MAT)");
        System.out.println("2. Chuyen khoan (CHUYEN_KHOAN)");
        System.out.println("3. The (THE)");
        int pt = NhapLieu.nhapSoNguyen("Lua chon phuong thuc", 1, 3);
        String pttt = (pt == 2) ? "CHUYEN_KHOAN" : (pt == 3 ? "THE" : "TIEN_MAT");
        if (!NhapLieu.nhapXacNhan("Xac nhan dat ve?")) {
            System.out.println("Da huy thao tac dat ve.");
            return false;
        }

        String maHD = qlHD.sinhMaHD();
        String[] dsMaVe = new String[n];
        for (int i = 0; i < n; i++) {
            String mv = qlVe.sinhMaVe();
            dsMaVe[i] = mv;
            qlVe.themVe(new Ve(mv, cb.getMaCB(), dsSoGhe[i], dsHangVe[i], dsLoaiVe[i], dsGiaVe[i], "HIEU_LUC"));
            qlHD.themChiTiet(new ChiTietHoaDon(maHD, mv, dsKhachBay[i].getMaKH(), dsLoaiVe[i], dsGiaVe[i], 1, dsGiaVe[i]));
        }
        qlHD.themHoaDon(new HoaDon(maHD, buyer.getMaKH(), nv.getMaNV(), cb.getMaCB(), LocalDateTime.now(), n, tongTien, pttt, "HIEU_LUC"));

        // Cap nhat so ve cua chuyen bay
        cb.setSoVeDaBan(cb.getSoVeDaBan() + n);
        cb.setSoVeConLai(cb.getTongSoVeMoBan() - cb.getSoVeDaBan());

        System.out.println("\nDAT VE THANH CONG!");
        System.out.println("Ma hoa don: " + maHD);
        System.out.println("Danh sach ma ve: " + String.join(", ", dsMaVe));
        return true;
    }
}
