package quanly;

import model.*;
import tienich.NhapLieu;
import java.util.ArrayList;

// Lop quan ly cac danh muc he thong: QuocGia, DiaDiem, SanBay, HangHangKhong, MayBay
public class QuanLyDanhMuc {
    private ArrayList<QuocGia> danhSachQuocGia = new ArrayList<QuocGia>();
    private ArrayList<DiaDiem> danhSachDiaDiem = new ArrayList<DiaDiem>();
    private ArrayList<SanBay> danhSachSanBay = new ArrayList<SanBay>();
    private ArrayList<HangHangKhong> danhSachHang = new ArrayList<HangHangKhong>();
    private ArrayList<MayBay> danhSachMayBay = new ArrayList<MayBay>();

    public ArrayList<QuocGia> getDanhSachQuocGia() { return danhSachQuocGia; }
    public ArrayList<DiaDiem> getDanhSachDiaDiem() { return danhSachDiaDiem; }
    public ArrayList<SanBay> getDanhSachSanBay() { return danhSachSanBay; }
    public ArrayList<HangHangKhong> getDanhSachHang() { return danhSachHang; }
    public ArrayList<MayBay> getDanhSachMayBay() { return danhSachMayBay; }

    public int getSoLuongQuocGia() { return danhSachQuocGia.size(); }
    public QuocGia getQuocGia(int index) { return (index >= 0 && index < danhSachQuocGia.size()) ? danhSachQuocGia.get(index) : null; }
    public int getSoLuongDiaDiem() { return danhSachDiaDiem.size(); }
    public DiaDiem getDiaDiem(int index) { return (index >= 0 && index < danhSachDiaDiem.size()) ? danhSachDiaDiem.get(index) : null; }
    public int getSoLuongSanBay() { return danhSachSanBay.size(); }
    public SanBay getSanBay(int index) { return (index >= 0 && index < danhSachSanBay.size()) ? danhSachSanBay.get(index) : null; }
    public int getSoLuongHang() { return danhSachHang.size(); }
    public HangHangKhong getHang(int index) { return (index >= 0 && index < danhSachHang.size()) ? danhSachHang.get(index) : null; }
    public int getSoLuongMayBay() { return danhSachMayBay.size(); }
    public MayBay getMayBay(int index) { return (index >= 0 && index < danhSachMayBay.size()) ? danhSachMayBay.get(index) : null; }

    public void themQuocGia(QuocGia qg) { if (qg != null && timQuocGia(qg.getMaQuocGia()) == null) danhSachQuocGia.add(qg); }
    public QuocGia timQuocGia(String maQG) {
        if (maQG == null) return null;
        for (QuocGia qg : danhSachQuocGia) if (maQG.equalsIgnoreCase(qg.getMaQuocGia())) return qg;
        return null;
    }

    public void themDiaDiem(DiaDiem dd) { if (dd != null && timDiaDiem(dd.getMaDiaDiem()) == null) danhSachDiaDiem.add(dd); }
    public DiaDiem timDiaDiem(String maDD) {
        if (maDD == null) return null;
        for (DiaDiem dd : danhSachDiaDiem) if (maDD.equalsIgnoreCase(dd.getMaDiaDiem())) return dd;
        return null;
    }

    public void themSanBay(SanBay sb) { if (sb != null && timSanBay(sb.getMaSanBay()) == null) danhSachSanBay.add(sb); }
    public SanBay timSanBay(String maSB) {
        if (maSB == null) return null;
        for (SanBay sb : danhSachSanBay) if (maSB.equalsIgnoreCase(sb.getMaSanBay())) return sb;
        return null;
    }

    public void themHang(HangHangKhong hang) { if (hang != null && timHang(hang.getMaHang()) == null) danhSachHang.add(hang); }
    public HangHangKhong timHang(String maHang) {
        if (maHang == null) return null;
        for (HangHangKhong h : danhSachHang) if (maHang.equalsIgnoreCase(h.getMaHang())) return h;
        return null;
    }

    public void themMayBay(MayBay mb) { if (mb != null && timMayBay(mb.getMaMayBay()) == null) danhSachMayBay.add(mb); }
    public MayBay timMayBay(String maMB) {
        if (maMB == null) return null;
        for (MayBay mb : danhSachMayBay) if (maMB.equalsIgnoreCase(mb.getMaMayBay())) return mb;
        return null;
    }

    // Tra ve quoc gia cua mot san bay thong qua lien ket: SanBay -> DiaDiem -> QuocGia
    public QuocGia layQuocGiaCuaSanBay(String maSanBay) {
        SanBay sb = timSanBay(maSanBay); if (sb == null) return null;
        DiaDiem dd = timDiaDiem(sb.getMaDiaDiem()); return dd != null ? timQuocGia(dd.getMaQuocGia()) : null;
    }

    public void xemDanhSachQuocGia() {
        System.out.println("\n================ DANH MUC QUOC GIA ================");
        for (QuocGia qg : danhSachQuocGia) System.out.println(qg);
    }
    public void xemDanhSachDiaDiem() {
        System.out.println("\n================ DANH MUC DIA DIEM ================");
        for (DiaDiem dd : danhSachDiaDiem) System.out.println(dd);
    }
    public void xemDanhSachSanBay() {
        System.out.println("\n================ DANH MUC SAN BAY ================");
        for (SanBay sb : danhSachSanBay) System.out.println(sb);
    }
    public void xemDanhSachHang() {
        System.out.println("\n================ DANH MUC HANG HANG KHONG ================");
        for (HangHangKhong h : danhSachHang) System.out.println(h);
    }
    public void xemDanhSachMayBay() {
        System.out.println("\n================ DANH MUC MAY BAY ================");
        for (MayBay mb : danhSachMayBay) System.out.println(mb);
    }

    // Cho phep nguoi dung chon san bay theo tung buoc phan cap: Quoc gia -> Dia diem -> San bay
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
