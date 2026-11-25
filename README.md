# Airline Ticket Management System (Java Console)

Ứng dụng quản lý bán vé máy bay viết bằng Java Core theo mô hình Lập trình hướng đối tượng (OOP). Hệ thống hỗ trợ đầy đủ quy trình đặt chỗ, tra cứu chuyến bay, quản lý hành khách, nhân viên, xuất hóa đơn và báo cáo doanh thu với cơ chế lưu trữ dữ liệu tập tin an toàn.

---

## Tính năng chính

- **Quy trình đặt vé máy bay:**
  - Phân loại chuyến bay **Nội địa** và **Quốc tế** ngay từ đầu luồng đặt vé.
  - Tự động áp dụng điều kiện bay đa hình: kiểm tra độ tuổi đối với chuyến nội địa; kiểm tra thời hạn hộ chiếu và Visa đối với chuyến quốc tế.
  - Chặn đặt trùng lặp: Ngăn hành khách đặt nhiều vé trên cùng một chuyến bay khi vé cũ còn hiệu lực.
  - Sơ đồ khoang ghế trực quan, phân chia vị trí ghế theo khoang **Thương gia** (hàng ghế phía trước) và **Phổ thông** đồng bộ theo cấu hình tàu bay.

- **Quản lý hóa đơn & Hủy vé:**
  - Lập hóa đơn bán vé tại quầy bởi nhân viên phụ trách.
  - Nghiệp vụ hủy vé an toàn: tự động hoàn trả trạng thái ghế trống cho chuyến bay chưa cất cánh.
  - Ràng buộc nghiệp vụ: Chặn thao tác hủy vé đối với các chuyến bay đã khởi hành trong quá khứ.
  - Xuất hóa đơn ra tập tin văn bản (`.txt`) để lưu trữ hoặc in ấn.

- **Quản lý lịch bay & Tàu bay:**
  - Quản lý danh sách chuyến bay theo tuyến bay và hãng hàng không.
  - Thuật toán kiểm tra chống xung đột lịch trình: Ngăn một máy bay bị xếp 2 chuyến bay trùng giờ cất/hạ cánh hoặc không đủ thời gian giãn cách.

- **Thống kê doanh thu & Báo cáo:**
  - Thống kê doanh thu theo từng chuyến bay và theo nhân viên lập hóa đơn.
  - Báo cáo tài chính chi tiết theo mốc thời gian **3 năm gần nhất**, hiển thị đầy đủ ngày giờ lập hóa đơn để đối soát minh bạch.

- **Lưu trữ dữ liệu an toàn (Data Persistence):**
  - Tự động nạp dữ liệu khi mở ứng dụng và lưu dữ liệu vào thư mục `data/` khi thoát.
  - Cơ chế **Atomic File Move**: Ghi tạm ra file `.tmp` trước khi thay thế file chính thức, chống hỏng dữ liệu khi bị ngắt đột ngột.

---

## Cấu trúc mã nguồn

Hệ thống được thiết kế theo kiến trúc 4 tầng phân tách trách nhiệm rõ ràng:

```
src/
├── Main.java                  # Điểm khởi chạy chương trình (Entry point)
├── HeThong.java               # Điều phối giao diện menu Console và tương tác người dùng
│
├── model/                     # Các lớp thực thể (Entities & Models)
│   ├── ChuyenBay.java         # Lớp trừu tượng (abstract class) cơ sở
│   ├── ChuyenBayNoiDia.java   # Lớp con chuyến bay nội địa
│   ├── ChuyenBayQuocTe.java   # Lớp con chuyến bay quốc tế
│   ├── KhachHang.java         # Thông tin hành khách & người mua vé
│   ├── NhanVien.java          # Thông tin nhân viên bán vé
│   ├── HoaDon.java            # Hóa đơn thanh toán (implements ThongKe)
│   ├── ChiTietHoaDon.java     # Chi tiết từng vé trong hóa đơn
│   ├── Ve.java                # Thực thể vé máy bay và số ghế
│   ├── MayBay.java            # Tàu bay và sơ đồ ghế
│   ├── SanBay.java            # Sân bay
│   ├── HangHangKhong.java     # Hãng hàng không
│   ├── DiaDiem.java           # Địa điểm / Thành phố
│   ├── QuocGia.java           # Quốc gia
│   └── ThongKe.java           # Interface tính toán doanh thu
│
├── danhsach/                  # Tầng lưu trữ dữ liệu (Data Access / Collections)
│   ├── DanhSachChuyenBay.java
│   ├── DanhSachHoaDon.java
│   ├── DanhSachKhachHang.java
│   ├── DanhSachNhanVien.java
│   ├── DanhSachVe.java
│   └── DanhSachDanhMuc.java
│
├── quanly/                    # Tầng xử lý logic nghiệp vụ (Business Services)
│   ├── QuanLyDatVe.java       # Điều phối quy trình đặt vé và kiểm tra điều kiện
│   ├── QuanLyHoaDon.java      # Nghiệp vụ tạo, hủy và thống kê hóa đơn
│   ├── QuanLyChuyenBay.java   # Quản lý chuyến bay, kiểm tra chống trùng lịch
│   ├── QuanLyKhachHang.java   # Quản lý và lọc khách hàng VIP
│   ├── QuanLyNhanVien.java    # Quản lý hồ sơ nhân viên
│   ├── QuanLyVe.java          # Tra cứu và kiểm tra ghế
│   └── QuanLyDanhMuc.java     # Quản lý sân bay, hãng bay, máy bay
│
└── tienich/                   # Tiện ích dùng chung (Utilities)
    ├── NhapLieu.java          # Xử lý nhập liệu bàn phím và kiểm tra hợp lệ
    └── DocGhiFile.java        # Xử lý đọc/ghi file text UTF-8 an toàn
```

---

## Hướng dẫn cài đặt & Chạy chương trình

### Yêu cầu hệ thống
- **Java Development Kit (JDK):** JDK 17 hoặc JDK 21 trở lên.
- **Hệ điều hành:** Windows, macOS hoặc Linux.
- **Thư viện:** Không sử dụng thư viện bên ngoài (thuần Java Standard Library).

### Cách 1: Chạy bằng dòng lệnh (Terminal / Command Prompt / PowerShell)

1. Mở terminal tại thư mục gốc của dự án (`Java OOP`).
2. Biên dịch mã nguồn:
   ```bash
   javac -encoding UTF-8 -cp src -d out src/Main.java
   ```
3. Chạy chương trình:
   ```bash
   java -Dfile.encoding=UTF-8 -cp out Main
   ```

### Cách 2: Chạy trên IDE (IntelliJ IDEA / Eclipse / VS Code)

1. Mở IDE và chọn **Open / Import Project**.
2. Chọn thư mục gốc của dự án (`Java OOP`).
3. Đảm bảo IDE nhận diện thư mục `src` là **Sources Root**.
4. Mở file `src/Main.java` và nhấn nút **Run** (hoặc `Shift + F10`).

---

## Dữ liệu mẫu kiểm thử nhanh (Demo Data)

Hệ thống đã chuẩn bị sẵn bộ dữ liệu kiểm thử trong thư mục `data/`:

| Đối tượng | Mã mẫu | Ghi chú |
| :--- | :--- | :--- |
| **Chuyến bay Nội địa** | `VN123` | Tuyến Hà Nội (HAN) $\rightarrow$ TP.HCM (SGN) |
| **Chuyến bay Quốc tế** | `VN456` | Tuyến Hà Nội (HAN) $\rightarrow$ Tokyo (NRT) - Yêu cầu Hộ chiếu & Visa |
| **Khách hàng** | `KH01` | Nguyễn Văn A (Khách phổ thông, chưa có Visa) |
| **Khách hàng** | `KH02` | Trần Thị B (Đã có hộ chiếu & Visa còn hạn) |
| **Nhân viên** | `NV01` | Nguyễn Văn An (Nhân viên bán vé ca sáng) |
| **Hóa đơn mẫu** | `HD001`, `HD002` | Đã lưu sẵn trong hệ thống phục vụ xem thống kê |

---

## Ứng dụng các nguyên lý OOP

- **Kế thừa (Inheritance):** Lớp cha trừu tượng `ChuyenBay` định nghĩa các thuộc tính cốt lõi của hành trình bay; 2 lớp con `ChuyenBayNoiDia` và `ChuyenBayQuocTe` kế thừa và mở rộng thêm các quy định giấy tờ, thủ tục bay đặc thù.
- **Đa hình (Polymorphism):**
  - Phương thức trừu tượng `kiemTraDieuKien(KhachHang)` được hiện thực hóa khác nhau ở mỗi loại chuyến bay (Nội địa kiểm tra độ tuổi, Quốc tế kiểm tra Hộ chiếu và Visa).
  - Duyệt danh sách doanh thu thông qua Interface `ThongKe`.
- **Đóng gói (Encapsulation):** Toàn bộ dữ liệu mảng và thao tác lưu trữ được đóng gói trong các lớp thuộc package `danhsach`. Tầng `HeThong` và `quanly` chỉ giao tiếp qua các phương thức nghiệp vụ, không trực tiếp thao tác trên danh sách thô.
- **Trừu tượng hóa (Abstraction):** Interface `ThongKe` chuẩn hóa hành vi báo cáo tài chính độc lập với cấu trúc nội bộ của hóa đơn.
