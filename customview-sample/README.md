# CustomView

## Overview

* Android cung cấp một mô hình thành phần tinh vi và mạnh mẽ để xây dựng giao diện người dùng của bạn, dựa trên các lớp bố cục cơ bản là View và ViewGroup. Để bắt đầu, nền tảng Android cung cấp một loạt các lớp con như View, ViewGroup được dựng sẵn - được gọi là widget và layout.

* Một phần của danh sách bao gồm Button, TextView, CheckBox, ListView, …

* Có 1 số ViewGroup đánh chú ý để custom lại như: LinearLayout, RelativeLayout, FramgeLayout, …

* Việc tạo ra các lớp con của View tương ứng cho phép bạn kiểm soát được chính xác sự xuất hiện và các chức năng của view.

* Để bắt đầu với custom view cần chú ý những bước sau:

    * Extend 1 view có sẵn
    * Override một số phương thức từ class cha để có thể ghi đè, thường bắt đầu bằng "on" như:
        * onDraw(): Vẽ ra những hình hoặc là vẽ 1 bức ảnh đè lên nó, hàm này để custom những gì sẽ được hiển thị trong view của bạn custom
        * onMeasure(): Tùy chỉnh width và height của view bạn custom
        * onKeyDown(): Sử dụng để bắt sự kiện cho việc nhấn vào các phím cứng phía dưới như back, menu, ...
    * Sử dụng lớp mở rộng của bạn như là view bình thường.
    

## Fully Customized Components

* Các thành phần tùy chỉnh hoàn toàn có thể được sử dụng để tạo thành phần đồ họa mà bạn mong muốn và xuất hiện theo cách của bạn. Có thể textview mỗi chữ 1 màu, có thể là custom seekbar để giống như thanh âm lượng theo chiều dọc, ... Có rất nhiều thứ bạn có thể tự làm nó theo ý mình vì nó được custom từ các lớp View của Android nên sẽ chạy như 1 view bình thường. 

* May mắn là bạn có thể dễ dàng tạo ra được các thành phần trông như thế nào và hoạt động theo cách của bạn, giới hạn chỉ có thể là trí tưởng tưởng của bạn, tỉ lệ màn hình hoặc là sức mạnh xử lý không đủ. 

* Để tạo được thành phần tùy chỉnh hoàn toàn thì qua các bước sau:

    * Nếu bạn muốn mở rộng từ lớp cơ sở nhất, bạn có thể phải extend từ view và mặc nhiên là sẽ chưa có giao diện nào trong đó để có thể sử dụng được luôn, bạn phải tự vẽ các giao diện bằng hàm onDraw() để có thể vẽ được những hình bạn mong muốn có trong view của mình. 
    * Bạn có thể cung cấp một hàm tạo có thể lấy các thuộc tính và tham số từ XML, và bạn cũng có thể tiêu thụ các thuộc tính và tham số của riêng bạn (Có thể là màu của seekbar, ...)
    * Bạn có thể xử lý sự kiện của riêng bạn, các trình truy cập thuộc tính và thay đổi nó phức tạp hơn cho phù hợp với mục đích của bạn. 
    * Bạn chắc chắn sẽ muốn sử dụng ghi đè các hàm như onMeasure() để tùy chỉnh về mặt width và height hiển thi, cũng có thể là onDraw() để hiển thị những gì bạn muốn ở trên layout mà bạn custom. Mặc dù 2 hàm này cũng có tham số mặc định dành cho nó nếu bạn không chỉnh sửa gì
    * Có  những hàm khác bắt đầu bằng on... cũng là công cụ hỗ trợ cho bạn để có thể custom được.

### Extend onDraw() and onMeasure()

* **onDraw()** sử dụng đối tượng Canvas để vẽ ra những giao diện mà bạn muốn: đồ họa 2D, các thành phần tiêu chuẩn hoặc các thành phần nâng cao, văn bản được tạo kiểu hoặc bất cứ thứ gì. Đó không phải là đồ họa 3D,  nếu muốn sử dụng nó thì bạn phải sử dụng SurfaceView.

* **onMeasure()** là thành phần quan trọng hơn chút, nó quy định view của bạn sẽ nằm như nào so với khoảng trống trên giao diện mà nó được nằm trên đó. Nếu chúng ta muốn tự tính toán cho view của mình thì sẽ có thêm phương thức setMeasuredDimension ()  để giúp chúng ta có thể tùy chỉnh được width và height của view.

### Modifying an Existing View Type

* Có một cách dễ dàng hơn để bạn có thể bắt đầu với custom view là việc bạn chỉnh sửa với 1 view có sẵn, lúc đó có rất nhiều hàm bạn sẽ không phải tự làm mà đã hỗ trợ trong lớp đó rồi. Đơn giản là view đó đã rất giống những gì mà bạn cần, bạn phải chỉnh sửa đôi chút để có thể chạy như bạn mong muốn nên tốn rất ít thời gian. 

* Ví dụ nếu bạn muốn hiển thị seekbar, ta muốn hiển thị theo chiều dọc thì có thể xoay lại cái view 90 độ để có thể  hiển thị theo chiều dọc 1 cách đơn giản.

### Define Custom Attributes

* Để thêm những thuộc tính của file xml vào view custom của bạn, bạn tự tạo và chỉ định thuộc tính trong file đó để người dùng có thể custom được theo ý muốn của mình. Để có chức năng này bạn phải khai báo các thuộc tính đó ở trong android manifest

```
<declare-styleable name="MyCustomView">
  <attr name="my_custom_attribute" />
  <attr name="android:gravity" />
</declare-styleable>
```
