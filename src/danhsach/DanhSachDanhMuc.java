package danhsach;

import model.*;
import java.util.ArrayList;

// Lop dong goi danh sach cac danh muc he thong: QuocGia, DiaDiem, SanBay, HangHangKhong, MayBay
public class DanhSachDanhMuc {
    private ArrayList<QuocGia> danhSachQuocGia;
    private ArrayList<DiaDiem> danhSachDiaDiem;
    private ArrayList<SanBay> danhSachSanBay;
    private ArrayList<HangHangKhong> danhSachHang;
    private ArrayList<MayBay> danhSachMayBay;

    public DanhSachDanhMuc() {
        this.danhSachQuocGia = new ArrayList<QuocGia>();
        this.danhSachDiaDiem = new ArrayList<DiaDiem>();
        this.danhSachSanBay = new ArrayList<SanBay>();
        this.danhSachHang = new ArrayList<HangHangKhong>();
        this.danhSachMayBay = new ArrayList<MayBay>();
    }

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

    public void themQuocGia(QuocGia qg) {
        if (qg != null && timQuocGia(qg.getMaQuocGia()) == null) danhSachQuocGia.add(qg);
    }
    public QuocGia timQuocGia(String maQG) {
        if (maQG == null) return null;
        for (QuocGia qg : danhSachQuocGia) if (maQG.equalsIgnoreCase(qg.getMaQuocGia())) return qg;
        return null;
    }

    public void themDiaDiem(DiaDiem dd) {
        if (dd != null && timDiaDiem(dd.getMaDiaDiem()) == null) danhSachDiaDiem.add(dd);
    }
    public DiaDiem timDiaDiem(String maDD) {
        if (maDD == null) return null;
        for (DiaDiem dd : danhSachDiaDiem) if (maDD.equalsIgnoreCase(dd.getMaDiaDiem())) return dd;
        return null;
    }

    public void themSanBay(SanBay sb) {
        if (sb != null && timSanBay(sb.getMaSanBay()) == null) danhSachSanBay.add(sb);
    }
    public SanBay timSanBay(String maSB) {
        if (maSB == null) return null;
        for (SanBay sb : danhSachSanBay) if (maSB.equalsIgnoreCase(sb.getMaSanBay())) return sb;
        return null;
    }

    public void themHang(HangHangKhong hang) {
        if (hang != null && timHang(hang.getMaHang()) == null) danhSachHang.add(hang);
    }
    public HangHangKhong timHang(String maHang) {
        if (maHang == null) return null;
        for (HangHangKhong h : danhSachHang) if (maHang.equalsIgnoreCase(h.getMaHang())) return h;
        return null;
    }

    public void themMayBay(MayBay mb) {
        if (mb != null && timMayBay(mb.getMaMayBay()) == null) danhSachMayBay.add(mb);
    }
    public MayBay timMayBay(String maMB) {
        if (maMB == null) return null;
        for (MayBay mb : danhSachMayBay) if (maMB.equalsIgnoreCase(mb.getMaMayBay())) return mb;
        return null;
    }
}
