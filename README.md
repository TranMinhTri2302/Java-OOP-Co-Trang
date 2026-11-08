# ĐỒ ÁN MÔN JAVA CƠ BẢN: HỆ THỐNG QUẢN LÝ BÁN VÉ MÁY BAY (JAVA CONSOLE)

Dự án ứng dụng Java Console chuẩn (JDK 8 trở lên), tuân thủ chặt chẽ các nguyên lý Lập trình hướng đối tượng (OOP), không sử dụng thư viện ngoài hay cơ sở dữ liệu bên ngoài.

---

## 1. YÊU CẦU MÔI TRƯỜNG & HƯỚNG DẪN BIÊN DỊCH, CHẠY CHƯƠNG TRÌNH

### Yêu cầu môi trường
- **Java Development Kit (JDK)**: Phiên bản JDK 8 trở lên (đã kiểm thử tương thích hoàn toàn trên OpenJDK 11 / 17 / 21).
- **Mã hóa ký tự**: UTF-8.

### Lệnh biên dịch và chạy nhanh trên Windows

**Cách 1: Chạy từ thư mục gốc dự án (`d:\Java OOP`) qua Terminal / CMD / PowerShell:**
```cmd
javac -cp src src/Main.java -d out
java -cp out Main
```
*(Hoặc lệnh 1 dòng: `javac -cp src src/Main.java && java -cp src Main`)*

**Cách 2: Bấm nút Run (Play) trong VS Code / Antigravity IDE (Code Runner tự cd vào `src`):**
```cmd
cd src
javac Main.java
java Main
```
*(Hệ thống đã tích hợp cơ chế nhận diện đường dẫn động, dù chạy ở thư mục gốc hay bên trong `src`, chương trình đều tự động trỏ đúng vào thư mục `data/` và `exports/`)*

### Lệnh tương đương cho hệ điều hành Linux hoặc macOS

```bash
# 1. Quét file nguồn
find src -name "*.java" > sources.txt

# 2. Biên dịch
javac -encoding UTF-8 -d out @sources.txt

# 3. Chạy chương trình
java -Dfile.encoding=UTF-8 -cp out Main
```

---

## 2. CẤU TRÚC THƯ MỤC VÀ MÔ TẢ CÁC LỚP

```
Java OOP/
├── src/
│   ├── Main.java                  (Lớp khởi chạy hệ thống, tối đa 10 dòng - thực tế 6 dòng)
│   ├── HeThong.java               (Điều phối giao diện console và menu chính, KHÔNG chứa ArrayList)
│   ├── model/                     (Các lớp thực thể và dữ liệu)
│   │   ├── ThongKe.java           (Interface DUY NHẤT trong toàn bộ dự án)
│   │   ├── QuocGia.java           (Quốc gia)
│   │   ├── DiaDiem.java           (Địa điểm/Thành phố thuộc quốc gia)
│   │   ├── SanBay.java            (Sân bay thuộc địa điểm)
│   │   ├── HangHangKhong.java     (Hãng hàng không)
│   │   ├── MayBay.java            (Tàu bay, quản lý danh sách mã ghế List<String>)
│   │   ├── ChuyenBay.java         (Lớp trừu tượng abstract, trung tâm hệ thống)
│   │   ├── ChuyenBayNoiDia.java   (Chuyến bay nội địa, kế thừa ChuyenBay)
│   │   ├── ChuyenBayQuocTe.java   (Chuyến bay quốc tế, kế thừa ChuyenBay)
│   │   ├── KhachHang.java         (Khách hàng / Người đặt vé / Người bay)
│   │   ├── NhanVien.java          (Nhân viên lập hóa đơn)
│   │   ├── Ve.java                (Vé máy bay, KHÔNG chứa maKH)
│   │   ├── HoaDon.java            (Hóa đơn thanh toán, implements ThongKe, lưu maKH người mua)
│   │   └── ChiTietHoaDon.java     (Chi tiết từng vé, lưu maKH người bay, soLuong luôn = 1)
│   ├── quanly/                    (Các lớp quản lý dữ liệu, chứa toàn bộ ArrayList)
│   │   ├── QuanLyDanhMuc.java     (Quản lý QuocGia, DiaDiem, SanBay, HangHangKhong, MayBay)
│   │   ├── QuanLyChuyenBay.java   (Quản lý ChuyenBay, kiểm tra trùng lịch tàu bay)
│   │   ├── QuanLyKhachHang.java   (Quản lý KhachHang)
│   │   ├── QuanLyNhanVien.java    (Quản lý NhanVien)
│   │   ├── QuanLyVe.java          (Quản lý Ve, kiểm tra trùng ghế)
│   │   └── QuanLyHoaDon.java      (Quản lý HoaDon và ChiTietHoaDon, xử lý hủy hóa đơn)
│   └── tienich/                   (Tiện ích dùng chung)
│       ├── NhapLieu.java          (Scanner duy nhất, kiểm tra dữ liệu đầu vào hợp lệ)
│       └── DocGhiFile.java        (Đọc/ghi file .txt, khởi tạo dữ liệu mẫu động, atomic file move)
├── data/                          (Thư mục lưu các file văn bản .txt tự động khởi tạo)
│   ├── quocgia.txt
│   ├── diadiem.txt
│   ├── sanbay.txt
│   ├── hanghangkhong.txt
│   ├── maybay.txt
│   ├── chuyenbay.txt
│   ├── khachhang.txt
│   ├── nhanvien.txt
│   ├── ve.txt
│   ├── hoadon.txt
│   └── chitiethoadon.txt
├── exports/                       (Thư mục lưu file hóa đơn xuất ra dạng văn bản in .txt)
└── README.md                      (Tài liệu báo cáo đồ án)
```

### Quan hệ giữa các lớp:
1. **QuocGia (1) -> (n) DiaDiem (1) -> (n) SanBay**: Phân cấp địa lý 3 tầng phục vụ tìm chuyến bay.
2. **HangHangKhong (1) -> (n) MayBay**: Hãng sở hữu danh sách máy bay.
3. **HangHangKhong, MayBay, SanBay -> ChuyenBay**: Chuyến bay liên kết mã hãng, mã máy bay, sân bay đi và sân bay đến.
4. **ChuyenBay (1) -> (n) Ve**: Một chuyến bay có nhiều vé bán ra.
5. **ChuyenBay (1) -> (n) HoaDon**: Mỗi hóa đơn gắn với duy nhất 1 chuyến bay (`maCB`).
6. **HoaDon (1) -> (n) ChiTietHoaDon (1) -> (1) Ve**: Hóa đơn có nhiều dòng chi tiết, mỗi dòng chi tiết gắn đúng một vé.
7. **KhachHang**:
   - `HoaDon.maKH`: Là người mua/đại diện thanh toán hóa đơn.
   - `ChiTietHoaDon.maKH`: Là hành khách trực tiếp bay trên ghế tương ứng với `maVe`.

---

## 3. GIẢI THÍCH CHI TIẾT CÁC QUYẾT ĐỊNH KIẾN TRÚC & NGHIỆP VỤ

### 3.1. Vì sao `ChuyenBay` là lớp trung tâm của hệ thống?
`ChuyenBay` đóng vai trò cầu nối nghiệp vụ cốt lõi giữa hạ tầng vận hành và thương mại:
- **Hạ tầng**: Liên kết hãng hàng không (`maHang`), máy bay (`maMayBay`), hành trình sân bay đi (`maSanBayDi`), sân bay đến (`maSanBayDen`), cùng lịch trình thời gian (`ngayDi`, `GioDi`, `ngayDen`, `GioDen`).
- **Thương mại**: Kiểm soát toàn bộ số lượng vé mở bán (`tongSoVeMoBan`), vé đã bán (`soVeDaBan`), vé còn lại (`soVeConLai`), giá vé cơ bản (`giaVeCoBan`) và tổng doanh thu (`tongDoanhThu`).
- **Nghiệp vụ**: Quyết định điều kiện khách hàng có được phép đặt vé hay không qua phương thức đa hình `kiemTraDieuKien(KhachHang)`. Mọi vé và hóa đơn đều quy chiếu về mã chuyến bay (`maCB`).

### 3.2. Tính kế thừa và đa hình nằm ở đâu trong mã nguồn?
- **Kế thừa (Inheritance)**:
  - Lớp trừu tượng `ChuyenBay` (chứa các thuộc tính `protected` và các phương thức dùng chung) là cha của 2 lớp con:
    + `ChuyenBayNoiDia extends ChuyenBay`: Bổ sung `chapNhanVNEID2` và `DoTuoiEmBeToiThieu`.
    + `ChuyenBayQuocTe extends ChuyenBay`: Bổ sung `quocGiaDen`, `yeuCauKiemTraVisa`, `soThangHieuLucHoChieuToiThieu`.
- **Đa hình (Polymorphism)**:
  - Danh sách chuyến bay được lưu trữ đồng nhất bằng biến kiểu cha: `ArrayList<ChuyenBay>` trong `QuanLyChuyenBay`.
  - Phương thức trừu tượng `public abstract String kiemTraDieuKien(KhachHang khach);` được khai báo ở `ChuyenBay` và được override ở cả hai lớp con:
    + `ChuyenBayNoiDia`: Kiểm tra số ngày tuổi của khách hàng tính từ `ngaySinh` đến `ngayDi` có đạt `DoTuoiEmBeToiThieu` ngày hay không.
    + `ChuyenBayQuocTe`: Kiểm tra số hộ chiếu, hạn hộ chiếu phải còn ít nhất `soThangHieuLucHoChieuToiThieu` tháng sau `ngayDen`, và visa (nếu chuyến yêu cầu visa).
  - Khi đặt vé, hệ thống chỉ cần gọi `cb.kiemTraDieuKien(khach)` thông qua tham chiếu kiểu `ChuyenBay`, Java tự động gọi đúng cài đặt tương ứng với kiểu đối tượng thực tế tại runtime.

### 3.3. Interface `ThongKe` được dùng ở đâu và như thế nào?
- Là interface **duy nhất** của dự án:
  ```java
  public interface ThongKe {
      double tinhTongTien();
  }
  ```
- Lớp `HoaDon implements ThongKe`:
  + Trả về `tongTien` nếu hóa đơn có trạng thái `HIEU_LUC`.
  + Trả về `0.0` nếu hóa đơn có trạng thái `DA_HUY`.
- Trong `QuanLyHoaDon.tinhTongDoanhThuThucTe()`: Toàn bộ danh sách hóa đơn được duyệt qua biến kiểu interface `ThongKe tk : danhSachHoaDon`, sau đó cộng dồn `tk.tinhTongTien()`. Điều này đảm bảo tính trừu tượng và tách biệt logic tính toán thống kê.

### 3.4. Vì sao không có lớp `Ghe` mà vẫn đảm bảo không bị trùng ghế và phân biệt được hạng vé?
- Mỗi `MayBay` chứa danh sách mã ghế sẵn có `danhSachSoGhe: List<String>` (ví dụ: `["1A", "1B", "1C", "1D", "2A", ...]`).
- **Phân khoang hạng vé trực quan**:
  + Hàng ghế số 1 (`1A` -> `1F`): Khoang Thương gia (`THUONG_GIA`).
  + Từ hàng 2 trở đi (`2A` -> `3F`...): Khoang Phổ thông (`PHO_THONG`).
  Khi hành khách chọn ghế, hệ thống tự động gán `hangVe` tương ứng vào vé máy bay.
- **Loại vé**: Cho phép phân loại `THUONG` (Người lớn) hoặc `TRE_EM` (Trẻ em) cho từng vé bay.
- Trạng thái bận/rỗi của ghế không gắn cố định vào tàu bay mà phụ thuộc vào từng chuyến bay:
  + Khi đặt vé cho chuyến bay `maCB`, hệ thống tìm tất cả các vé trong `QuanLyVe` có `maCB == chuyenBay.getMaCB()` và trạng thái `HIEU_LUC`.
  + Tập hợp các số ghế này chính là các ghế đã có người đặt.
  + Các ghế còn lại hiển thị cho khách lựa chọn chính là hiệu của `danhSachSoGhe` của máy bay trừ đi danh sách ghế của các vé hiệu lực trên chuyến đó.
- Nhờ vậy, cùng một số ghế `1A` có thể được bán hoàn toàn độc lập trên hai chuyến bay khác nhau `CB01` và `CB02` mà không bao giờ bị trùng, không cần tạo lớp `Ghe` phức tạp.

### 3.5. Vì sao trường `soLuong` trong `ChiTietHoaDon` luôn bằng 1?
- Mỗi vé máy bay (`Ve`) là một thực thể vật lý độc nhất, gắn liền với một số ghế cụ thể trên máy bay và một hành khách cụ thể (`maKH` trong `ChiTietHoaDon`).
- Do mỗi dòng chi tiết hóa đơn gắn với duy nhất 1 vé (`maVe`), số lượng vé của dòng đó trên chuyến bay bắt buộc luôn là 1. Việc mua nhiều vé trong một hóa đơn được biểu diễn bằng nhiều dòng `ChiTietHoaDon` khác nhau (mỗi dòng tương ứng với 1 khách bay và 1 ghế).

### 3.6. Vì sao một hóa đơn có thể có nhiều vé nhưng chỉ thuộc về một chuyến bay (`maCB`)?
- Đây là quy tắc nghiệp vụ chuẩn của hệ thống: một giao dịch đặt chỗ theo chặng bay tập trung cho phép một người đặt vé cho cả gia đình hoặc đoàn thể trên cùng một chuyến bay.
- Ràng buộc một hóa đơn gắn với một `maCB` giúp đơn giản hóa logic kiểm tra điều kiện bay, áp dụng giá vé cơ bản của chuyến bay, và kiểm soát tình trạng chỗ ngồi đồng nhất trong cùng một thời điểm.

### 3.7. Phân biệt người mua (`HoaDon.maKH`) và người bay (`ChiTietHoaDon.maKH`), và cách tra cứu khách hàng của `Ve`?
- **Người mua (`HoaDon.maKH`)**: Là cá nhân thanh toán đơn hàng (cha mẹ, trưởng đoàn, thư ký cơ quan). Người mua có thể bay hoặc không bay trên chuyến bay đó.
- **Người bay (`ChiTietHoaDon.maKH`)**: Là hành khách thực tế ngồi trên ghế và phải thỏa mãn các điều kiện giấy tờ/độ tuổi bay của chuyến bay (`kiemTraDieuKien`).
- **Cách tra cứu khách hàng của một `Ve`**: Vì lớp `Ve` không chứa thuộc tính `maKH` (tuân thủ ràng buộc cứng), khách hàng của một vé được tìm bằng cách:
  Tra trong danh sách `ChiTietHoaDon` của `QuanLyHoaDon` dòng có `maVe` trùng khớp, lấy trường `maKH` (người bay), sau đó tra trong `QuanLyKhachHang` để lấy đầy đủ hồ sơ hành khách.

### 3.8. Cách tính thống kê doanh thu và xử lý nghiệp vụ khi hủy hóa đơn?
- **Khi hủy hóa đơn**:
  1. Chuyển trạng thái `HoaDon.trangThai` từ `HIEU_LUC` sang `DA_HUY`.
  2. Tìm tất cả các `ChiTietHoaDon` thuộc hóa đơn đó, chuyển trạng thái các vé `Ve` tương ứng thành `DA_HUY`.
  3. Hoàn trả trạng thái ghế: Các vé đã hủy không còn nằm trong diện vé hiệu lực, do đó số ghế lập tức được giải phóng trở lại danh sách ghế trống cho chuyến bay.
  4. Cập nhật lại số liệu trên `ChuyenBay`: Giảm `soVeDaBan`, tăng `soVeConLai`, và trừ `tongDoanhThu` tương ứng.
  5. Cập nhật chỉ số của `KhachHang` (người mua) và `NhanVien` (người lập).
- **Thống kê doanh thu**:
  - Nhờ đa hình `ThongKe`, hàm tính tổng doanh thu chỉ cộng tiền từ những hóa đơn có `trangThai == "HIEU_LUC"`. Các hóa đơn đã hủy trả về 0đ, không làm sai lệch số liệu doanh thu thực tế.

### 3.9. Định dạng file, vị trí lưu trữ và quy ước đọc ghi dữ liệu?
- **Vị trí**: Thư mục `data/` tại thư mục gốc của dự án.
- **Định dạng**: File văn bản thuần UTF-8, các trường thông tin phân tách nhau bằng ký tự dấu gạch đứng `|`.
  + Ví dụ `quocgia.txt`: `VN|Việt Nam`
  + Ví dụ `ve.txt`: `VE001|CB01|1A|PHO_THONG|THUONG|1500000.0|HIEU_LUC`
- **An toàn dữ liệu (Atomic Move)**:
  Khi ghi file, chương trình ghi dữ liệu ra file tạm có đuôi `.tmp` trước. Chỉ khi toàn bộ dữ liệu ghi thành công không có lỗi ngoại lệ, hệ thống mới dùng `Files.move(..., StandardCopyOption.ATOMIC_MOVE, REPLACE_EXISTING)` để ghi đè file chính thức. Cơ chế này chống hỏng dữ liệu khi chương trình bị tắt đột ngột giữa chừng.

### 3.10. Các điểm giới hạn của hệ thống (System Limitations)
1. **Múi giờ**: Hệ thống mặc định toàn bộ thời gian khởi hành và hạ cánh trên cùng một múi giờ chuẩn (Local System Time), chưa xử lý lệch múi giờ quốc tế.
2. **Xác thực giấy tờ**: Giấy tờ tùy thân (CCCD, Hộ chiếu, Visa) chỉ được kiểm tra logic chuỗi và hạn sử dụng ngày tháng mô phỏng; không kết nối API Cổng dịch vụ công quốc gia hay cơ sở dữ liệu Bộ Ngoại giao.
3. **Thanh toán**: Phương thức thanh toán (`TIEN_MAT`, `CHUYEN_KHOAN`, `THE`) chỉ là thông tin ghi nhận nghiệp vụ kế toán, chưa tích hợp cổng thanh toán thực tế (VNPay, Napas).
4. **Hủy hóa đơn**: Nghiệp vụ hủy vé hoàn trả chỗ ngồi và tính lại doanh thu sổ sách, mô phỏng không bao gồm luồng chuyển tiền hoàn về tài khoản thực tế.
5. **Độ tuổi em bé**: `DoTuoiEmBeToiThieu` được tính chính xác theo đơn vị **ngày** tuổi (thông thường tối thiểu 7 ngày tuổi sau sinh).
6. **VNeID**: `chapNhanVNEID2` là cờ đánh dấu chính sách chấp nhận giấy tờ điện tử của chặng bay, không phải tích hợp định danh điện tử thật.

### 3.11. Hai điều chỉnh kỹ thuật so với sơ đồ ban đầu
1. **Kiểu dữ liệu Ngày/Giờ**: Bốn trường `ngayDi`, `ngayDen` được sử dụng kiểu `LocalDate`; `GioDi`, `GioDen` được sử dụng kiểu `LocalTime` (thuộc gói chuẩn `java.time` từ Java 8) thay vì kiểu `String`. Tên thuộc tính vẫn giữ đúng 100% nguyên gốc (`GioDi`, `GioDen`). Điều này giúp kiểm tra logic thời gian đến sau thời gian đi và kiểm tra chồng lịch bay một cách chính xác tuyệt đối.
2. **Khách hàng của Vé**: Thay vì lưu `maKH` trực tiếp trong lớp `Ve`, khách của vé được xác định qua quan hệ `ChiTietHoaDon.maKH` ứng với `maVe`. Điều này vừa thỏa mãn ràng buộc cứng "lớp Ve không có maKH", vừa mô hình hóa chính xác nghiệp vụ thực tế: người mua hóa đơn (`HoaDon.maKH`) mua vé cho hành khách bay cụ thể (`ChiTietHoaDon.maKH`).

---

## 4. 8 CÂU HỎI VẤN ĐÁP GIẢNG VIÊN VÀ CÂU TRẢ LỜI NGẮN GỌN (BÁM SÁT CODE)

#### Câu 1: Em hãy chỉ ra tính Đa hình (Polymorphism) được thể hiện ở dòng lệnh nào trong dự án?
> **Trả lời:**
> Tính đa hình thể hiện rõ nhất ở 2 vị trí:
> 1. Khi đặt vé trong `HeThong.java`, hệ thống duyệt `ChuyenBay cb` và gọi `cb.kiemTraDieuKien(khach)`. Tại runtime, nếu `cb` là `ChuyenBayNoiDia` thì chạy logic kiểm tra số ngày tuổi; nếu là `ChuyenBayQuocTe` thì chạy logic kiểm tra hộ chiếu và visa.
> 2. Trong hàm `QuanLyHoaDon.tinhTongDoanhThuThucTe()`, duyệt danh sách hóa đơn qua tham chiếu interface `ThongKe tk : danhSachHoaDon` và gọi `tk.tinhTongTien()`.

#### Câu 2: Tại sao trong lớp `Ve` không có thuộc tính `maKH`? Làm sao để in ra danh sách hành khách đi trên chuyến bay?
> **Trả lời:**
> Vì một vé thuộc về một chi tiết hóa đơn, trong đó `ChiTietHoaDon` lưu `maVe` và `maKH` (người bay). Để in danh sách hành khách của chuyến bay `maCB`:
> - Lấy tất cả vé có `trangThai == "HIEU_LUC"` của `maCB`.
> - Với mỗi `maVe`, tìm dòng `ChiTietHoaDon` tương ứng để lấy `maKH`.
> - Lấy thông tin khách hàng từ `QuanLyKhachHang` bằng `maKH` đó.

#### Câu 3: Làm thế nào chương trình ngăn chặn việc 2 người đặt trùng cùng một ghế trên một chuyến bay mà không cần lớp `Ghe`?
> **Trả lời:**
> Trong `QuanLyVe.kiemTraGheDaDat(maCB, soGhe)`: Phương thức duyệt toàn bộ `ArrayList<Ve>` và kiểm tra nếu có vé nào thỏa mãn đồng thời: `v.getMaCB().equals(maCB)` VÀ `v.getSoGhe().equalsIgnoreCase(soGhe)` VÀ `v.getTrangThai().equals("HIEU_LUC")` thì báo ghế đã bị đặt. Ghế chỉ bận trên chuyến bay đó, sang chuyến bay khác ghế đó hoàn toàn rỗi.

#### Câu 4: Điều kiện kiểm tra chồng lịch máy bay (overlap schedule) được cài đặt như thế nào?
> **Trả lời:**
> Được cài đặt trong `QuanLyChuyenBay.kiemTraChongLichMayBay(...)`: Với hai khoảng thời gian $[Start_1, End_1]$ và $[Start_2, End_2]$ của cùng một `maMayBay`, điều kiện bị trùng lịch là:
> `start1.isBefore(end2) && start2.isBefore(end1)`.
> Thời điểm được xác định chính xác bằng `LocalDateTime.of(ngay, gio)`.

#### Câu 5: Tại sao lớp `Main` chỉ có 6 dòng code? Việc điều phối hệ thống được chuyển cho lớp nào?
> **Trả lời:**
> Tuân thủ nguyên lý Single Responsibility (Đơn nhiệm): `Main` chỉ đóng vai trò là điểm vào (entry point) để khởi tạo đối tượng `HeThong` và gọi `heThong.khoiDong()`. Mọi logic hiển thị menu, nhận lệnh từ người dùng và điều phối các dịch vụ quản lý do `HeThong.java` đảm nhiệm.

#### Câu 6: Dữ liệu được quản lý ở đâu? Tại sao trong `HeThong.java` không có khai báo `ArrayList` nào?
> **Trả lời:**
> Theo ràng buộc kiến trúc phân tầng, toàn bộ các `ArrayList` lưu trữ dữ liệu được cô lập hoàn toàn bên trong 6 lớp quản lý chuyên biệt (`QuanLyDanhMuc`, `QuanLyChuyenBay`, `QuanLyKhachHang`, `QuanLyNhanVien`, `QuanLyVe`, `QuanLyHoaDon`). `HeThong` chỉ giữ tham chiếu đến các đối tượng quản lý này và gọi các hàm nghiệp vụ, không trực tiếp lưu trữ hay thao tác trên danh sách thô.

#### Câu 7: Khi xóa một khách hàng hoặc chuyến bay, hệ thống kiểm tra ràng buộc toàn vẹn dữ liệu như thế nào?
> **Trả lời:**
> - Với khách hàng: `QuanLyKhachHang.xoaKhachHang()` kiểm tra xem `maKH` có tồn tại trong bất kỳ `HoaDon` (người mua) hay `ChiTietHoaDon` (người bay) nào không (kể cả đã hủy). Nếu có, từ chối xóa để đảm bảo toàn vẹn lịch sử hóa đơn.
> - Với chuyến bay: `QuanLyChuyenBay.xoaChuyenBay()` kiểm tra xem có bất kỳ `Ve` hoặc `HoaDon` nào tham chiếu đến `maCB` hay không. Nếu đã phát sinh vé/hóa đơn thì tuyệt đối không được xóa.

#### Câu 8: Cơ chế Atomic Move khi lưu file giải quyết vấn đề gì?
> **Trả lời:**
> Khi lưu dữ liệu xuống đĩa, nếu ghi trực tiếp vào file chính và gặp sự cố (mất điện, tắt ứng dụng đột ngột, lỗi ổ đĩa), file dữ liệu sẽ bị cắt cụt và hỏng hoàn toàn. Hệ thống khắc phục bằng cách ghi toàn bộ ra file tạm `.tmp`. Khi hoàn tất thành công, phương thức `Files.move` với cờ `ATOMIC_MOVE` sẽ hoán đổi file tạm thành file chính thức trong một thao tác nguyên tử ở cấp hệ điều hành, đảm bảo an toàn dữ liệu 100%.
