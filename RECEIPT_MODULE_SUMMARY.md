# TỔNG HỢP MODULE QUẢN LÝ HÓA ĐƠN (RECEIPT MANAGEMENT)

## 1. TRẠNG THÁI BAN ĐẦU

Module Quản lý Hóa Đơn đã được implement hoàn chỉnh với 3 layer:

- **DAO Layer**: ✅ ReceiptDAO, CustomerDAO, PromotionDAO
- **Service Layer**: ✅ ReceiptService, CustomerService, PromotionService
- **GUI Layer**: ✅ CustomersSelectionDialog, AddOrUpdateReceiptPanel, ReceiptPanel
- **MainForm**: ✅ Đã register ReceiptPanel vào CardLayout + tbtnReceiptActionPerformed

## 2. CÁC BUG ĐÃ FIX

### Bug #1: NullPointerException khi CustomerId = null (CRITICAL)

**File**: `ReceiptDAO.java`  
**Vấn đề**: `psReceipt.setInt(6, null)` crash khi khách hàng là "Khách lẻ"  
**Fix**:

```java
if (entity.getCustomerId() != null) {
    psReceipt.setInt(6, entity.getCustomerId());
} else {
    psReceipt.setNull(6, java.sql.Types.INTEGER);
}
```

### Bug #2: CreatedAt = null trong cache (CRITICAL)

**File**: `ReceiptService.java`  
**Vấn đề**: DB dùng `GETDATE()` nhưng không trả về `createdAt`, cached DTO có `null` → NPE khi sort/filter  
**Fix**: Thêm `dto.setCreatedAt(LocalDateTime.now())` sau khi `receiptDAO.add()`

### Bug #3: CustomerId = 0 thay vì null (IMPORTANT)

**File**: `ReceiptDAO.java` (getAll + getById)  
**Vấn đề**: `rs.getInt("CustomerId")` trả về 0 cho NULL trong DB thay vì null  
**Fix**:

```java
Integer customerId = rs.getObject("CustomerId") != null ? rs.getInt("CustomerId") : null;
```

### Bug #4: CustomerService cache rỗng (IMPORTANT)

**File**: `CustomerService.java`  
**Vấn đề**: `getCustomerById()` trả về null nếu cache chưa được populate  
**Fix**:

```java
public CustomerDTO getCustomerById(Integer id) {
    if (id == null) return null;
    if (customersCache.isEmpty()) {
        getAllCustomers();
    }
    return customersCache.stream()...
}
```

### Bug #5: Missing import BigDecimal (COMPILE ERROR)

**File**: `ReceiptPanel.java`  
**Fix**: Thêm `import java.math.BigDecimal;`

### Bug #6: ComponentShown listener gây duplicate load (PERF)

**File**: `ReceiptPanel.java`  
**Vấn đề**: Mỗi lần switch tab → loadData() chạy 3 lần (combo reset → ActionEvent chain)  
**Fix**: Xóa `addComponentListener`, để `tbtnReceiptActionPerformed` trong MainForm handle refresh

### Bug #7: GEN labels còn Import text (UI)

**File**: `ReceiptPanel.java`  
**Fix**: Thêm trong `setCss()`:

```java
jLabel1.setText("Mã hóa đơn");
jLabel3.setText("Thứ tự");
jLabel15.setText("Tổng giảm giá");
jLabel16.setText("Tổng thanh toán");
```

### Bug #8: Validation khách hàng thiếu (CRITICAL)

**File**: `AddOrUpdateReceiptPanel.java`  
**Vấn đề**: Chưa kiểm tra `selectedCustomer == null`, DB field `CustomerId` NOT NULL → crash  
**Fix**: Thêm validation trong `jButton2ActionPerformed`:

```java
if (selectedCustomer == null) {
    JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng trước khi tạo hóa đơn", "Chưa chọn khách hàng", JOptionPane.WARNING_MESSAGE);
    return;
}
```

### Bug #9: Nút "Thêm mới" KH trong dialog sai logic

**File**: `CustomersSelectionDialog.java`  
**Vấn đề**: `btnAddActionPerformed` đang navigate CardLayout (sai vì đây là Dialog, không phải Panel)  
**Fix**: Mở `AddOrUpdateCustomerDialog` modal + reload data sau khi đóng:

```java
private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {
    java.awt.Frame parentFrame = (java.awt.Frame) SwingUtilities.getWindowAncestor(this);
    AddOrUpdateCustomerDialog dialog = new AddOrUpdateCustomerDialog(parentFrame, true, null);
    dialog.setLocationRelativeTo(this);
    dialog.setVisible(true);
    loadData(); // Refresh danh sách sau khi thêm KH mới
}
```

**Import cần thêm**: `import com.fat.GUI.Dialogs.Customers.AddOrUpdateCustomerDialog;`

### Bug #10: Danh sách sản phẩm trống (UI - ĐANG ĐIỀU TRA)

**File**: `AddOrUpdateReceiptPanel.java`  
**Vấn đề**: Method `loadProducts()` có filter `p.getStock() > 0` → nếu chưa nhập kho, tất cả SP bị ẩn  
**Fix đang chờ xác nhận**: Bỏ filter stock > 0, để validation ở bước thêm vào hóa đơn thay vì ở filter

## 3. FILE STRUCTURE

```
src/main/java/com/fat/
├── DAO/Repositories/
│   ├── ReceiptDAO.java ✅ Fixed #1, #3
│   ├── CustomerDAO.java ✅
│   └── PromotionDAO.java ✅
├── BUS/Services/
│   ├── ReceiptService.java ✅ Fixed #2
│   ├── CustomerService.java ✅ Fixed #4
│   └── PromotionService.java ✅
├── DTO/
│   ├── Receipts/
│   │   ├── ReceiptDTO.java ✅
│   │   └── ReceiptDetailDTO.java ✅
│   └── Customers/CustomerDTO.java ✅
├── Contract/Enumerations/
│   ├── ReceiptSort.java ✅
│   └── SortOrder.java ✅
└── GUI/
    ├── Dialogs/
    │   ├── Receipt/CustomersSelectionDialog.java ✅ Fixed #9
    │   └── Customers/AddOrUpdateCustomerDialog.java (existing)
    ├── Panels/Receipt/
    │   ├── ReceiptPanel.java ✅ Fixed #5, #6, #7
    │   └── AddOrUpdateReceiptPanel.java ✅ Fixed #8, #10 pending
    └── MainForm.java ✅
```

## 4. FLOW HOẠT ĐỘNG

### Tạo hóa đơn:

1. ReceiptPanel → Click "Thêm mới" → AddOrUpdateReceiptPanel
2. Chọn sản phẩm từ bảng trái (có filter theo danh mục + tìm kiếm)
3. Nhập số lượng → Click "Thêm mới" → Validate stock → Thêm vào bảng chi tiết (giá + giảm giá tự động tính từ PromotionService)
4. Click "..." → Mở CustomersSelectionDialog:
   - Chọn KH có sẵn → "Xác nhận"
   - Hoặc click "Thêm mới" → Mở AddOrUpdateCustomerDialog → Thêm KH mới → Auto refresh list
5. Click "Tạo phiếu nhập" → Validate (≥1 SP + KH required) → DAO transaction:
   - INSERT Receipt
   - INSERT ReceiptDetail (n dòng)
   - UPDATE Product.Stock (giảm)
   - INSERT InventoryHistory (Type=2)
6. Navigate về ReceiptPanel + refresh

### Xem danh sách + chi tiết:

1. Tab "HÓA ĐƠN" → `tbtnReceiptActionPerformed` → Refresh ReceiptPanel
2. Filter: Mã HD, ngày (from-to), tổng thanh toán, sort by/order
3. Click row → `showReceiptDetails` → Load full receipt với items từ DAO → Fill all fields + detail table

## 5. VALIDATION RULES

**Tạo hóa đơn (AddOrUpdateReceiptPanel.jButton2ActionPerformed)**:

- ✅ Phải có ≥1 sản phẩm: `receiptDetails.isEmpty()`
- ✅ Phải chọn khách hàng: `selectedCustomer == null`
- ✅ Validation từ Service: ValidationException

**Thêm sản phẩm vào hóa đơn (btnAddActionPerformed)**:

- ✅ Chọn sản phẩm: `selectedProduct == null`
- ✅ Nhập số lượng: `qtyStr.isBlank()` hoặc `quantity <= 0`
- ✅ Kiểm tra tồn kho: `quantity > selectedProduct.getStock()`
- ✅ Không trùng sản phẩm: Check `productId` trong `receiptDetails`

**Thêm khách hàng mới (AddOrUpdateCustomerDialog)**:

- ✅ ValidatorUtil.validate(dto) — check required fields
- ✅ Số điện thoại unique: `customersCache.stream().anyMatch(...)`

## 6. DATABASE SCHEMA (liên quan)

```sql
-- Receipt table
CREATE TABLE Receipt (
    Id INT PRIMARY KEY IDENTITY,
    Code NVARCHAR(50) UNIQUE NOT NULL,
    CreatedAt DATETIME DEFAULT GETDATE(),
    StaffId INT NOT NULL,
    CustomerId INT NOT NULL, -- BẮT BUỘC (Bug #8)
    SubTotalAmount DECIMAL(18,2),
    TotalDiscountAmount DECIMAL(18,2),
    TotalAmount DECIMAL(18,2)
);

-- ReceiptDetail table
CREATE TABLE ReceiptDetail (
    ReceiptId INT,
    ProductId INT,
    Quantity INT,
    Price DECIMAL(18,2),
    DiscountAmount DECIMAL(18,2),
    SubTotalAmount DECIMAL(18,2),
    PRIMARY KEY (ReceiptId, ProductId)
);

-- InventoryHistory table (Type = 2: Xuất bán)
CREATE TABLE InventoryHistory (
    Id INT PRIMARY KEY IDENTITY,
    Quantity INT,
    ProductId INT,
    CreatedAt DATETIME DEFAULT GETDATE(),
    Type INT, -- 1=Nhập, 2=Xuất
    StockAfter INT
);
```

## 7. GIT STATUS

**Commit đã push lên main (commit d753f2f)**:

- 10 files changed, 3874 insertions(+)
- Tất cả 3 file GUI Receipt (_.java + _.form)
- Fixed DAO + Service bugs #1-4
- Registered ReceiptPanel trong MainForm

**Cần fix thêm** (chưa commit):

- Bug #8: Validation khách hàng (AddOrUpdateReceiptPanel.java)
- Bug #9: Nút thêm KH trong dialog (CustomersSelectionDialog.java)
- Bug #10: Filter stock > 0 (AddOrUpdateReceiptPanel.java) - **USER ĐÃ UNDO FIX NÀY**

## 8. VẤN ĐỀ ĐANG MỞ

### Bug #10: Danh sách sản phẩm trống

**Triệu chứng**: Bảng sản phẩm bên trái AddOrUpdateReceiptPanel không hiện gì dù có data trong DB

**Code hiện tại** (`AddOrUpdateReceiptPanel.loadProducts()`):

```java
// Chỉ hiện sản phẩm còn tồn kho
products = products.stream()
    .filter(p -> p.getStock() != null && p.getStock() > 0)
    .toList();
```

**Nguyên nhân**: Nếu chưa nhập kho (Import) → tất cả Product.Stock = 0 → filter loại hết

**Giải pháp đề xuất**:

1. **Option A** (recommended): Bỏ filter `stock > 0`, để validation ở `btnAddActionPerformed` (đã có sẵn)
2. **Option B**: Hiển thị SP có stock = 0 nhưng disable/màu xám
3. **Option C**: Thêm message "Chưa có sản phẩm nào còn hàng" nếu list rỗng

**Code fix (Option A)** - User đã undo, cần apply lại:

```java
private void loadProducts() {
    String keyword = txtSearch1.getText().trim();
    List<ProductDTO> products = productService.getAllProducts();

    // Filter theo category
    if (selectedCategoryId != null && selectedCategoryId != 0) {
        products = products.stream()
            .filter(p -> p.getCategoryId().equals(selectedCategoryId))
            .toList();
    }

    // Filter theo keyword
    if (!keyword.isBlank()) {
        products = products.stream()
            .filter(p -> p.getName().toLowerCase().contains(keyword.toLowerCase()))
            .toList();
    }

    // BỎ FILTER STOCK (validation đã có ở btnAddActionPerformed)

    allProducts = products;
    fillProductTable(products);
}
```

## 9. KIẾN TRÚC & DEPENDENCIES

### Singleton Services:

- `ReceiptService.getInstance()`
- `CustomerService.getInstance()`
- `ProductService.getInstance()`
- `CategoryService.getInstance()`
- `StaffService.getInstance()`
- `PromotionService.getInstance()`

### Session:

```java
UserSessionDTO session = UserSessionDTO.getInstance();
Integer staffId = session.getStaffId();
String staffName = session.getStaffName();
```

### Utilities:

- `FormatterUtil.toVND(BigDecimal)` — format tiền VND
- `FormatterUtil.toDateTime(LocalDateTime)` — format ngày giờ
- `ExcelHelper.exportToExcel(JTable, String, String)` — xuất Excel
- `ValidatorUtil.validate(DTO)` — validation

### Tính toán giảm giá:

```java
BigDecimal discountAmount = promotionService.calculateDiscountPrice(productId, originalPrice);
// Thành tiền = (Giá bán - Giảm giá) × Số lượng
BigDecimal subTotal = price.subtract(discountAmount).multiply(new BigDecimal(quantity));
```

## 10. TESTING CHECKLIST

- [ ] Tạo hóa đơn với khách hàng có sẵn
- [ ] Tạo hóa đơn với khách hàng mới (click "Thêm mới" trong dialog)
- [ ] Validation: Không chọn KH → Show error
- [ ] Validation: Không có SP → Show error
- [ ] Validation: Số lượng > tồn kho → Show error
- [ ] Filter/search sản phẩm theo danh mục + tên
- [ ] Filter hóa đơn theo mã, ngày, tổng tiền
- [ ] Sort hóa đơn theo ngày/tổng tiền + tăng/giảm dần
- [ ] Xem chi tiết hóa đơn (click row)
- [ ] Xuất Excel danh sách hóa đơn
- [ ] Tồn kho tự động giảm sau khi tạo hóa đơn
- [ ] InventoryHistory ghi log Type=2
- [ ] **Danh sách sản phẩm hiển thị đúng (Bug #10)**

## 11. NEXT STEPS

1. **Apply fix Bug #10** — Bỏ filter stock > 0 trong `loadProducts()`
2. **Test full flow** — Từ tạo KH mới → Chọn SP → Tạo hóa đơn → Kiểm tra DB
3. **Commit & Push**:
   ```bash
   git add src/main/java/com/fat/GUI/Panels/Receipt/AddOrUpdateReceiptPanel.java
   git add src/main/java/com/fat/GUI/Dialogs/Receipt/CustomersSelectionDialog.java
   git commit -m "fix: Receipt validation + customer dialog + product list filter"
   git push origin main
   ```
4. **Document** — Update README với hướng dẫn sử dụng module Receipt

---

**File này được tạo tự động để tổng hợp context cho session mới. Đọc kỹ Section 8 (Bug #10) để tiếp tục fix.**
