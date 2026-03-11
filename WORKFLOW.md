# BeverageProject - Workflow Documentation

## Tổng Quan

Đây là ứng dụng quản lý cửa hàng tạp hóa/đồ uống được xây dựng bằng Java Swing, theo kiến trúc 3 lớp (3-Layer Architecture).

---

## Kiến Trúc Hệ Thống

```
┌─────────────────────────────────────────────────────────────┐
│                    GUI Layer (Presentation)                  │
│         MainForm.java + Panels + Dialogs (Swing)            │
└──────────────────────────┬──────────────────────────────────┘
                           │
┌──────────────────────────▼──────────────────────────────────┐
│                BUS/Service Layer (Business Logic)            │
│    AuthService, ProductService, CategoryService, ...         │
│              Interface: BUS/Abstractions/Services            │
└──────────────────────────┬──────────────────────────────────┘
                           │
┌──────────────────────────▼──────────────────────────────────┐
│                    DAO Layer (Data Access)                   │
│         ProductDAO, CategoryDAO, StaffDAO, ...               │
│              DbContext.java (Connection Manager)             │
└──────────────────────────┬──────────────────────────────────┘
                           │
┌──────────────────────────▼──────────────────────────────────┐
│                    Database (MS SQL Server)                  │
│                        GroceryDB                             │
└─────────────────────────────────────────────────────────────┘
```

---

## Cấu Trúc Thư Mục

```
src/main/java/com/fat/
├── BUS/                    # Business Logic Layer
│   ├── Abstractions/       # Service interfaces
│   │   └── Services/
│   ├── Services/           # Service implementations
│   └── Utils/              # Utilities (Validation, etc.)
├── Contract/               # Shared contracts
│   ├── Constants/          # Hằng số
│   ├── Enumerations/       # Enum types
│   ├── Exceptions/         # Custom exceptions
│   └── Shared/             # Shared utilities
├── DAO/                    # Data Access Layer
│   ├── Abstractions/       # DAO interfaces
│   ├── Repositories/       # DAO implementations
│   └── Utils/              # DbContext
├── DI/                     # Dependency Injection
│   └── AppModule.java      # Guice configuration
├── DTO/                    # Data Transfer Objects
│   ├── Categories/
│   ├── Customers/
│   ├── Imports/
│   ├── Products/
│   ├── Receipts/
│   ├── Staffs/
│   └── ...
└── GUI/                    # Presentation Layer
    ├── MainForm.java       # Main container
    ├── Components/         # Reusable components
    ├── Dialogs/            # Dialog windows
    ├── Forms/              # Login form
    ├── Panels/             # Module panels
    └── Utils/              # GUI utilities
```

---

## Workflow Chi Tiết

### 1. Khởi Động Ứng Dụng

**Entry Point:** `MainForm.main()`

```java
public static void main(String args[]) {
    // 1. Cài đặt UI theme
    FlatRobotoFont.install();
    FlatLightLaf.setup();
    
    // 2. Global exception handler
    Thread.setDefaultUncaughtExceptionHandler(new GlobalExceptionHandler());
    
    // 3. Khởi tạo Guice DI
    Injector injector = Guice.createInjector(new AppModule());
    
    // 4. Hiển thị MainForm
    MainForm mainForm = injector.getInstance(MainForm.class);
    mainForm.setVisible(true);
}
```

### 2. Luồng Đăng Nhập (Authentication Flow)

```
┌─────────────┐     ┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│  LoginForm  │────>│ AuthService │────>│ StaffService│────>│  StaffDAO   │
│  (GUI)      │     │ (validate)  │     │ (getByUser) │     │  (SQL)      │
└─────────────┘     └─────────────┘     └─────────────┘     └─────────────┘
                           │
                           ▼
                    ┌─────────────┐
                    │UserSessionDTO│ (Singleton - lưu session)
                    └─────────────┘
```

**Chi tiết:**
1. User nhập username/password vào `LoginForm`
2. `AuthService.login()` được gọi
3. Lấy thông tin staff từ database qua `StaffService` → `StaffDAO`
4. So sánh password bằng **BCrypt** hash
5. Nếu thành công, tạo session trong `UserSessionDTO`
6. Chuyển sang `MainForm`

### 3. Phân Quyền (Authorization)

**Mô hình phân quyền:**
- Mỗi **Staff** có một **Role**
- Mỗi **Role** có nhiều **RoleClaim** (quyền)
- **RoleClaim** định nghĩa: ClaimType (module) + Value (permission flags)

**Permission Flags:**
| Flag | Ý nghĩa |
|------|---------|
| READ | Xem dữ liệu |
| CREATE | Thêm mới |
| UPDATE | Cập nhật |
| DELETE | Xóa |

**Kiểm tra quyền:**
```java
// MainForm.java
private void checkPermissionAndSetButtonState() {
    int roleId = userSession.getRoleId();
    
    // Hiển thị/ẩn menu theo quyền
    tbtnStaff.setVisible(roleService.checkPermission(roleId, Function.STAFF, Action.READ));
    tbtnProduct.setVisible(roleService.checkPermission(roleId, Function.PRODUCT, Action.READ));
    // ...
}
```

### 4. Điều Hướng Module (Navigation)

**MainForm** sử dụng **CardLayout** để chuyển đổi giữa các Panel:

```java
private void showPanel(String panelName) {
    CardLayout cl = (CardLayout) contentPanel.getLayout();
    cl.show(contentPanel, panelName);
}
```

**Các Module:**

| Button | Panel | Chức năng |
|--------|-------|-----------|
| tbtnTrangChu | DashboardPanel | Trang chủ, tổng quan |
| tbtnStaff | StaffsPanel | Quản lý nhân viên |
| tbtnRole | RolesPanel | Quản lý vai trò/phân quyền |
| tbtnCategory | CategoriesPanel | Quản lý danh mục |
| tbtnProduct | ProductsPanel | Quản lý sản phẩm |
| tbtnSupplier | SuppliersPanel | Quản lý nhà cung cấp |
| tbtnCustomer | CustomersPanel | Quản lý khách hàng |
| tbtnImport | ImportsPanel | Quản lý nhập hàng |
| tbtnReceipt | ReceiptsPanel | Quản lý hóa đơn bán |
| tbtnPromotion | PromotionsPanel | Quản lý khuyến mãi |
| tbtnStatistic | StatisticsPanel | Thống kê báo cáo |

### 5. Luồng CRUD Operations

#### Ví dụ: Thêm Sản Phẩm Mới

```
┌─────────────────────────────────────────────────────────────────────────┐
│ 1. GUI: ProductsPanel                                                    │
│    - User click "Thêm sản phẩm"                                         │
│    - Mở AddOrUpdateProductDialog                                        │
│    - User điền thông tin → ProductDTO                                   │
└────────────────────────────────┬────────────────────────────────────────┘
                                 │
┌────────────────────────────────▼────────────────────────────────────────┐
│ 2. Service: ProductService.createProduct(dto)                           │
│    - ValidatorUtil.validate(dto)     // Jakarta Bean Validation         │
│    - productDAO.isExistByName(...)   // Kiểm tra trùng tên              │
│    - UploadImageService.uploadImage(...) // Lưu ảnh                     │
│    - productDAO.add(dto)             // Gọi DAO                         │
│    - productsCache.addFirst(dto)     // Cập nhật cache                  │
└────────────────────────────────┬────────────────────────────────────────┘
                                 │
┌────────────────────────────────▼────────────────────────────────────────┐
│ 3. DAO: ProductDAO.add(dto)                                             │
│    - Connection conn = DbContext.getConnection()                        │
│    - PreparedStatement với INSERT SQL                                   │
│    - Trả về ID mới tạo                                                  │
└────────────────────────────────┬────────────────────────────────────────┘
                                 │
┌────────────────────────────────▼────────────────────────────────────────┐
│ 4. Database: GroceryDB                                                   │
│    - INSERT INTO Products (...) VALUES (...)                            │
│    - RETURN @@IDENTITY                                                  │
└────────────────────────────────┬────────────────────────────────────────┘
                                 │
┌────────────────────────────────▼────────────────────────────────────────┐
│ 5. GUI: ProductsPanel.refreshTable()                                    │
│    - Reload data từ cache/service                                       │
│    - Cập nhật JTable                                                    │
└─────────────────────────────────────────────────────────────────────────┘
```

### 6. Quản Lý Nhập Hàng (Import Flow)

```
┌──────────────┐     ┌──────────────────────────────┐
│  Chọn NCC    │────>│ Thêm sản phẩm vào đơn nhập  │
│  (Supplier)  │     │ (ImportDetail)               │
└──────────────┘     └───────────────┬──────────────┘
                                     │
                     ┌───────────────▼──────────────┐
                     │ Xác nhận đơn nhập            │
                     │ - Tạo Import record          │
                     │ - Tạo ImportDetail records   │
                     │ - Cập nhật Stock sản phẩm    │
                     │ - Ghi InventoryHistory       │
                     └──────────────────────────────┘
```

### 7. Quản Lý Bán Hàng (Receipt Flow)

```
┌──────────────┐     ┌──────────────────────────────┐
│ Chọn/Tạo    │────>│ Thêm sản phẩm vào hóa đơn   │
│ Customer     │     │ (ReceiptDetail)              │
└──────────────┘     └───────────────┬──────────────┘
                                     │
                     ┌───────────────▼──────────────┐
                     │ Áp dụng khuyến mãi (nếu có) │
                     │ - Kiểm tra Promotion active  │
                     │ - Tính DiscountAmount        │
                     └───────────────┬──────────────┘
                                     │
                     ┌───────────────▼──────────────┐
                     │ Thanh toán                   │
                     │ - Tạo Receipt record         │
                     │ - Tạo ReceiptDetail records  │
                     │ - Trừ Stock sản phẩm         │
                     │ - Ghi InventoryHistory       │
                     │ - In hóa đơn (PDF)           │
                     └──────────────────────────────┘
```

---

## Database Schema

### Các Bảng Chính

```sql
-- Người dùng & Phân quyền
Staff (Id, FirstName, LastName, UserName, Password, RoleId, Salary, ...)
Role (Id, Name)
RoleClaim (Id, RoleId, ClaimType, Value)

-- Sản phẩm
Product (Id, Name, Price, Unit, Stock, CategoryId, Image, ...)
Category (Id, Name)

-- Nhà cung cấp & Nhập hàng
Supplier (Id, Name, Email, PhoneNumber, Address)
Import (Id, ImportCode, SupplierId, TotalPrice, Status, StaffId, CreatedAt)
ImportDetail (ImportId, ProductId, Quantity, ImportPrice)

-- Khách hàng & Bán hàng
Customer (Id, FirstName, LastName, PhoneNumber, Address)
Receipt (Id, Code, CustomerId, StaffId, SubTotalAmount, TotalDiscountAmount, TotalAmount, CreatedAt)
ReceiptDetail (ReceiptId, ProductId, Quantity, Price, DiscountAmount)

-- Khuyến mãi
Promotion (Id, Name, StartDate, EndDate, ...)
PromotionDetail (PromotionId, ProductId, DiscountPercentage)

-- Tồn kho
InventoryHistory (Id, ProductId, Quantity, Type, StockAfter, CreatedAt)
```

---

## Công Nghệ Sử Dụng

| Thành phần | Công nghệ |
|------------|-----------|
| **Language** | Java 21 |
| **UI Framework** | Swing + FlatLaf (Modern Look & Feel) |
| **Layout Manager** | MiGLayout |
| **Database** | MS SQL Server |
| **JDBC Driver** | mssql-jdbc 12.4.3 |
| **DI Framework** | Google Guice |
| **Validation** | Jakarta Bean Validation 3.x |
| **Password Hashing** | BCrypt (jbcrypt) |
| **PDF Generation** | iText/OpenPDF |
| **Build Tool** | Maven |

---

## Design Patterns Áp Dụng

| Pattern | Sử dụng |
|---------|---------|
| **Singleton** | Tất cả Services (`ProductService.getInstance()`) |
| **Repository/DAO** | Các class DAO tách biệt data access |
| **DTO** | Data Transfer Objects giữa các layer |
| **Factory** | Tạo dialog/panel động |
| **Observer** | Event handling trong GUI |
| **CardLayout** | Navigation giữa các panel |

---

## Cấu Hình Database

File: `src/main/java/com/fat/DAO/Utils/DbContext.java`

```java
private static final String URL = "jdbc:sqlserver://localhost:1434;"
        + "database=GroceryDB;"
        + "user=sa;"
        + "password=YourStrong!Passw0rd;"
        + "encrypt=true;"
        + "trustServerCertificate=true;"
        + "loginTimeout=30;";
```

---

## Chạy Ứng Dụng

### Prerequisites
- JDK 21+
- MS SQL Server với database `GroceryDB`
- Maven 3.x

### Build & Run

```bash
# Build
mvn clean install

# Run
mvn exec:java -Dexec.mainClass="com.fat.GUI.MainForm"
```

### Import Database

```bash
# Sử dụng file SQL đính kèm
sqlcmd -S localhost,1434 -U sa -P YourStrong!Passw0rd -i BanTapHoa-1768489418.sql
```

---

## Tác giả

BeverageProject - Ứng dụng quản lý cửa hàng tạp hóa/đồ uống
