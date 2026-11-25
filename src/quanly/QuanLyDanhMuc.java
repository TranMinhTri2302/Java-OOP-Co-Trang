package quanly;

import danhsach.DanhSachDanhMuc;
import model.*;
import tienich.NhapLieu;
import java.util.ArrayList;

// Lop quan ly nghiep vu va giao dien console cac danh muc, su dung DanhSachDanhMuc de luu tru
public class QuanLyDanhMuc {
    private DanhSachDanhMuc dsDM;

    public QuanLyDanhMuc() {
        this.dsDM = new DanhSachDanhMuc();
    }

    public QuanLyDanhMuc(DanhSachDanhMuc dsDM) {
        this.dsDM = (dsDM != null) ? dsDM : new DanhSachDanhMuc();
    }

    public DanhSachDanhMuc getDsDM() { return dsDM; }
    public ArrayList<QuocGia> getDanhSachQuocGia() { return dsDM.getDanhSachQuocGia(); }
    public ArrayList<DiaDiem> getDanhSachDiaDiem() { return dsDM.getDanhSachDiaDiem(); }
    public ArrayList<SanBay> getDanhSachSanBay() { return dsDM.getDanhSachSanBay(); }
    public ArrayList<HangHangKhong> getDanhSachHang() { return dsDM.getDanhSachHang(); }
    public ArrayList<MayBay> getDanhSachMayBay() { return dsDM.getDanhSachMayBay(); }

    public int getSoLuongQuocGia() { return dsDM.getSoLuongQuocGia(); }
    public QuocGia getQuocGia(int index) { return dsDM.getQuocGia(index); }
    public int getSoLuongDiaDiem() { return dsDM.getSoLuongDiaDiem(); }
    public DiaDiem getDiaDiem(int index) { return dsDM.getDiaDiem(index); }
    public int getSoLuongSanBay() { return dsDM.getSoLuongSanBay(); }
    public SanBay getSanBay(int index) { return dsDM.getSanBay(index); }
    public int getSoLuongHang() { return dsDM.getSoLuongHang(); }
    public HangHangKhong getHang(int index) { return dsDM.getHang(index); }
    public int getSoLuongMayBay() { return dsDM.getSoLuongMayBay(); }
    public MayBay getMayBay(int index) { return dsDM.getMayBay(index); }

    public void themQuocGia(QuocGia qg) { dsDM.themQuocGia(qg); }
    public QuocGia timQuocGia(String maQG) { return dsDM.timQuocGia(maQG); }
    public void themDiaDiem(DiaDiem dd) { dsDM.themDiaDiem(dd); }
    public DiaDiem timDiaDiem(String maDD) { return dsDM.timDiaDiem(maDD); }
    public void themSanBay(SanBay sb) { dsDM.themSanBay(sb); }
    public SanBay timSanBay(String maSB) { return dsDM.timSanBay(maSB); }
    public void themHang(HangHangKhong hang) { dsDM.themHang(hang); }
    public HangHangKhong timHang(String maHang) { return dsDM.timHang(maHang); }
    public void themMayBay(MayBay mb) { dsDM.themMayBay(mb); }
    public MayBay timMayBay(String maMB) { return dsDM.timMayBay(maMB); }

    public QuocGia layQuocGiaCuaSanBay(String maSanBay) {
        SanBay sb = timSanBay(maSanBay);
        if (sb == null) return null;
        DiaDiem dd = timDiaDiem(sb.getMaDiaDiem());
        return dd != null ? timQuocGia(dd.getMaQuocGia()) : null;
    }

    public void xemDanhSachQuocGia() {
        System.out.println("\n================ DANH MUC QUOC GIA ================");
        for (QuocGia qg : dsDM.getDanhSachQuocGia()) System.out.println(qg);
    }
    public void xemDanhSachDiaDiem() {
        System.out.println("\n================ DANH MUC DIA DIEM ================");
        for (DiaDiem dd : dsDM.getDanhSachDiaDiem()) System.out.println(dd);
    }
    public void xemDanhSachSanBay() {
        System.out.println("\n================ DANH MUC SAN BAY ================");
        for (SanBay sb : dsDM.getDanhSachSanBay()) System.out.println(sb);
    }
    public void xemDanhSachHang() {
        System.out.println("\n================ DANH MUC HANG HANG KHONG ================");
        for (HangHangKhong h : dsDM.getDanhSachHang()) System.out.println(h);
    }
    public void xemDanhSachMayBay() {
        System.out.println("\n================ DANH MUC MAY BAY ================");
        for (MayBay mb : dsDM.getDanhSachMayBay()) System.out.println(mb);
    }

    public void menuXemDanhMuc() {
        while (true) {
            System.out.println("\n==================================================");
            System.out.println("                XEM DANH MUC HE THONG");
            System.out.println("==================================================");
            System.out.println("1. Danh muc Quoc gia");
            System.out.println("2. Danh muc Dia diem");
            System.out.println("3. Danh muc San bay");
            System.out.println("4. Danh muc Hang hang khong");
            System.out.println("5. Danh muc May bay");
            System.out.println("0. Quay lai menu chinh");
            System.out.println("==================================================");
            int c = NhapLieu.nhapSoNguyen("Lua chon cua ban", 0, 5);
            if (c == 0) break;
            if (c == 1) xemDanhSachQuocGia();
            else if (c == 2) xemDanhSachDiaDiem();
            else if (c == 3) xemDanhSachSanBay();
            else if (c == 4) xemDanhSachHang();
            else if (c == 5) xemDanhSachMayBay();
        }
    }

    public SanBay chonSanBayTungBuoc(String moTa) {
        if (getSoLuongQuocGia() == 0) return null;
        System.out.println("\nChon quoc gia " + moTa + ":");
        for (int i = 0; i < getSoLuongQuocGia(); i++) {
            System.out.printf("[%d] %s (%s)%n", (i + 1), getQuocGia(i).getTenQuocGia(), getQuocGia(i).getMaQuocGia());
        }
        int cQG = NhapLieu.nhapSoNguyen("Chon STT quoc gia (0 de huy)", 0, getSoLuongQuocGia());
        if (cQG == 0) return null;
        QuocGia qg = getQuocGia(cQG - 1);

        int demDD = 0;
        int[] idxDD = new int[getSoLuongDiaDiem()];
        for (int i = 0; i < getSoLuongDiaDiem(); i++) {
            if (getDiaDiem(i).getMaQuocGia().equalsIgnoreCase(qg.getMaQuocGia())) {
                idxDD[demDD++] = i;
                System.out.printf("[%d] %s (%s)%n", demDD, getDiaDiem(i).getTenDiaDiem(), getDiaDiem(i).getMaDiaDiem());
            }
        }
        if (demDD == 0) return null;
        int cDD = NhapLieu.nhapSoNguyen("Chon STT dia diem (0 de huy)", 0, demDD);
        if (cDD == 0) return null;

        DiaDiem dd = getDiaDiem(idxDD[cDD - 1]);
        int demSB = 0;
        int[] idxSB = new int[getSoLuongSanBay()];
        for (int i = 0; i < getSoLuongSanBay(); i++) {
            SanBay sb = getSanBay(i);
            if (sb.getMaDiaDiem().equalsIgnoreCase(dd.getMaDiaDiem()) && sb.isTrangThaiHoatDong()) {
                idxSB[demSB++] = i;
                System.out.printf("[%d] %s (%s)%n", demSB, sb.getTenSanBay(), sb.getMaSanBay());
            }
        }
        if (demSB == 0) return null;
        int cSB = NhapLieu.nhapSoNguyen("Chon STT san bay (0 de huy)", 0, demSB);
        return (cSB == 0) ? null : getSanBay(idxSB[cSB - 1]);
    }
}
