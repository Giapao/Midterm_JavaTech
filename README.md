# Midterm_JavaTech
DỰ ÁN THƯƠNG MẠI ĐIỆN TỬ VỚI SPRINGBOOT
--------------------------------------------------------------
I. Các nguyên tắc và mẫu thiết kế để phát triển phần mềm:
1. Nguyên tắc:
- SoC (Separation of Concerns) hay Tách biệt trách nhiệm: Các lớp và các tầng của dự án được phân chia theo từng chức năng cụ thể ( Controller, Service, Repository) đồng thời đảm bảo tính mô đun
- SPR (Single Responsibility Principle): Khiến mỗi lớp đảm nhiệm 1 trách nhiệm duy nhất, để dễ kiểm tra và bảo trì trong tương lai
2. Mẫu thiết kế:
DTO (Data Transfer Object): Dùng để truyền dữ liệu giữa các tầng, giảm sự rang buộc của các lớp
Repository Pattern: Dùng để truy cập dữ liệu và tương tác với các CSDL
3. Thực hành:
- Thiết kế các API
- Xử lý lỗi
- Bảo mật với JWT
II. Cấu trúc của code:
Controller:	Xử lý các yêu cầu liên quan đến HTTP và phản hồi
-	AuthController
-	CartController
-	OrderController
-	ProductController
DTO: 
-	CartItemDTO
-	OrderDTO
Mapper:	Thiết lập chuyển đổi giữa DTO và Model
-	OrderMapper
Model:
-	Cart
-	CartItem
-	Order
-	Product
-	User

Repository:
-	CartRepository
-	OrderRepository
-	ProductRepository
-	UserRepository
Security:
-	CustomUserDetailsService
-	JwtFilter
-	SecurityConfig
-	JwtUtil
Service:
-	CartService
-	OrderService
-	ProductService
-	UserService
III. Hướng dẫn chạy ứng dụng:
Yêu cầu: Maven, MySQL, Postman hoặc tool khác kiểm tra API
-	Mã nguồn dự án: 
-	Cấu hình lại cơ sở dữ liệu application.properties phù hợp với MySQL đang sử dụng
----------------------------------------------------------------------------------
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce spring.datasource.username=your_username 
spring.datasource.password=your_password spring.jpa.hibernate.ddl-auto=update spring.jpa.show-sql=true
-	----------------------------------------------------------------------------------
-	Trong IDE thực hiệnh lệnh run
-	Vì ứng dụng chưa hoàn thiện phần front-end nên sau khi run, sẽ vào đường dẫn:
http://localhost:8080
1.	Đăng kí: 
POST http://localhost:8080/auth/register
Headers: Content-Type: application/x-www-form-urlencoded
Body: (x-www-form-urlencoded)
(Key) username: (Value) Tên người dùng
(Key) password: (Value) Mật khẩu
Response: 200 OK

2.	Đăng nhập:
POST http://localhost:8080/auth/login
Headers: Content-Type: application/x-www-form-urlencoded
Body: (x-www-form-urlencoded)
(Key) username: (Value) Tên người dùng đã tạo
(Key) password: (Value) Mật khẩu đã tạo
Response: Trả về token JWT
Ví  dụ: 
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
Vào terminal của SQL thực hiện dòng lệnh: UPDATE user SET role = 'ROLE_ADMIN' WHERE username = 'testuser';
Khi thực hiện các lệnh trong Postman khi đã đăng nhập hãy thêm:
Headers:
Key: Authorization
Value: Bearer <JWT_Token>
Để có thể thực hiện được các lệnh
