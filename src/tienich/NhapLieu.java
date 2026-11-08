package tienich;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

// Lop tien ich quan ly viec nhap du lieu; chi dung 1 Scanner duy nhat
public class NhapLieu {
    private static final Scanner scanner = new Scanner(System.in);
    public static final DateTimeFormatter dNgay = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final DateTimeFormatter dGio = DateTimeFormatter.ofPattern("HH:mm");
    public static final DateTimeFormatter dThoiGian = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    // Ho tro chuyen doi chuoi sang LocalDate linh hoat: dd/MM/yyyy, dd-MM-yyyy, hoac yyyy-MM-dd
    public static LocalDate parseNgayLinhHoat(String s) {
        if (s == null || s.trim().isEmpty()) return null;
        String t = s.trim();
        try {
            return LocalDate.parse(t.replace("-", "/"), DateTimeFormatter.ofPattern("d/M/yyyy"));
        } catch (Exception ignored) {}
        try {
            return LocalDate.parse(t.replace("/", "-"), DateTimeFormatter.ofPattern("yyyy-M-d"));
        } catch (Exception ignored) {}
        return null;
    }

    public static String dinhDangNgay(LocalDate d) {
        return (d != null) ? d.format(dNgay) : "";
    }

    // Ho tro chuyen doi chuoi sang LocalDateTime linh hoat (dd/MM/yyyy HH:mm hoac ISO yyyy-MM-ddTHH:mm)
    public static java.time.LocalDateTime parseThoiGianLinhHoat(String s) {
        if (s == null || s.trim().isEmpty()) return null;
        String t = s.trim();
        try {
            return java.time.LocalDateTime.parse(t, DateTimeFormatter.ofPattern("d/M/yyyy HH:mm:ss"));
        } catch (Exception ignored) {}
        try {
            return java.time.LocalDateTime.parse(t, DateTimeFormatter.ofPattern("d/M/yyyy HH:mm"));
        } catch (Exception ignored) {}
        try {
            return java.time.LocalDateTime.parse(t);
        } catch (Exception ignored) {}
        try {
            return java.time.LocalDateTime.parse(t, DateTimeFormatter.ofPattern("yyyy-M-d HH:mm:ss"));
        } catch (Exception ignored) {}
        try {
            return java.time.LocalDateTime.parse(t, DateTimeFormatter.ofPattern("yyyy-M-d HH:mm"));
        } catch (Exception ignored) {}
        return null;
    }

    public static String dinhDangThoiGian(java.time.LocalDateTime dt) {
        return (dt != null) ? dt.format(dThoiGian) : "";
    }

    private static String docDong() {
        if (!scanner.hasNextLine()) {
            System.out.println("\nKet thuc luong nhap. Dang thoat chuong trinh...");
            System.exit(0);
        }
        return scanner.nextLine();
    }

    public static String nhapChuoi(String tb) {
        while (true) {
            System.out.print(tb + ": ");
            String in = docDong();
            if (in == null || in.trim().isEmpty()) { System.out.println("Loi: Du lieu khong duoc de trong!"); continue; }
            if (in.contains("|")) { System.out.println("Loi: Khong duoc chua '|'!"); continue; }
            return in.trim();
        }
    }

    public static String nhapChuoiCoTheRong(String tb) {
        while (true) {
            System.out.print(tb + ": ");
            String in = docDong();
            if (in == null || in.trim().isEmpty()) return "";
            if (in.contains("|")) { System.out.println("Loi: Khong duoc chua '|'!"); continue; }
            return in.trim();
        }
    }

    public static int nhapSoNguyen(String tb, int min, int max) {
        while (true) {
            System.out.print(tb + " [" + min + " - " + max + "]: ");
            try {
                int v = Integer.parseInt(docDong().trim());
                if (v >= min && v <= max) return v;
            } catch (Exception ignored) {}
            System.out.println("Loi: Gia tri phai la so nguyen tu " + min + " den " + max + "!");
        }
    }

    public static double nhapSoThuc(String tb, double min, double max) {
        while (true) {
            System.out.print(tb + ": ");
            try {
                double v = Double.parseDouble(docDong().trim());
                if (!Double.isNaN(v) && !Double.isInfinite(v) && v >= min && v <= max) return v;
            } catch (Exception ignored) {}
            System.out.println("Loi: Gia tri phai la so thuc tu " + min + " den " + max + "!");
        }
    }

    public static LocalDate nhapNgay(String tb) {
        while (true) {
            System.out.print(tb + " (dd/MM/yyyy): ");
            String l = docDong();
            if (l != null && !l.trim().isEmpty()) {
                LocalDate res = parseNgayLinhHoat(l);
                if (res != null) return res;
            }
            System.out.println("Loi: Dinh dang ngay phai la dd/MM/yyyy (vi du: 28/11/2026)!");
        }
    }

    public static LocalDate nhapNgayCoTheRong(String tb) {
        while (true) {
            System.out.print(tb + " (dd/MM/yyyy, bo trong nhan Enter): ");
            String l = docDong();
            if (l == null || l.trim().isEmpty()) return null;
            LocalDate res = parseNgayLinhHoat(l);
            if (res != null) return res;
            System.out.println("Loi: Dinh dang ngay phai la dd/MM/yyyy (vi du: 28/11/2026)!");
        }
    }

    public static LocalTime nhapGio(String tb) {
        while (true) {
            System.out.print(tb + " (HH:mm): ");
            String l = docDong();
            if (l != null && !l.trim().isEmpty()) {
                try { return LocalTime.parse(l.trim(), dGio); } catch (Exception ignored) {}
            }
            System.out.println("Loi: Dinh dang gio HH:mm!");
        }
    }

    public static boolean nhapXacNhan(String tb) {
        while (true) {
            System.out.print(tb + " (Y/N): ");
            String l = docDong();
            if (l != null) {
                String t = l.trim().toUpperCase();
                if (t.equals("Y")) return true;
                if (t.equals("N")) return false;
            }
            System.out.println("Loi: Chi nhap 'Y' hoac 'N'!");
        }
    }
}
