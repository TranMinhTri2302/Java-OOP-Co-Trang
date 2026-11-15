public boolean huyHoaDon(String maHD, ChuyenBay cb) {
    HoaDon hd = timHoaDon(maHD);
    if (hd == null || !"HIEU_LUC".equalsIgnoreCase(hd.getTrangThai())) return false;
    hd.setTrangThai("DA_HUY");
    if (cb != null) {
        cb.setSoVeDaBan(cb.getSoVeDaBan() - hd.getSoLuongVe());
        cb.setSoVeConLai(cb.getTongSoVeMoBan() - cb.getSoVeDaBan());
    }
    return true;
}




public boolean huyHoaDon(String ma HD, ChuyenBay cb){
    HoaDon hd = timHoaDon(maHD);
    if (hd == null || )
}