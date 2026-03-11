# Session Summary - BeverageProject

## Project Overview
Java Swing desktop app - Phần mềm quản lý bán tạp hóa "Apolo"
- **Architecture**: 3-layer DAO → BUS (Service) → GUI
- **GUI**: Java Swing, GroupLayout, JTabbedPane
- **DB**: SQL Server (JDBC), Singleton pattern cho services
- **Libraries**: com.toedter.calendar (JDateChooser, JMonthChooser, JYearChooser), ExcelHelper, FormatterUtil

---

## Changes Made This Session

### 1. Promotion Module Polish

**PromotionPanel2.java** (`src/main/java/com/fat/GUI/Dialogs/Promotions/`)
- Added `private boolean isUpdating = false;` to prevent recursive TableModelListener (Stack Overflow fix)
- Added `validateAndUpdatePrice(int row, int column)` method: validates numeric input, recalculates counterpart column (Giá sau giảm ↔ Giảm giá %), uses try-finally to reset `isUpdating`
- Added blue selection color: `setSelectionBackground(new java.awt.Color(0, 120, 215))` + `setSelectionForeground(Color.WHITE)`

**PromotionItemDetailDTO.java** (`src/main/java/com/fat/DTO/Promotions/`)
- Added missing setter: `public void setDiscountPercentage(double discountPercentage) { this.discountPercentage = discountPercentage; }`

**PromotionsPanel.java** + **SelectProductDialog.java**
- Added blue selection color on all JTables: RGB(0, 120, 215)

---

### 2. Customer Statistics Module (NEW)

#### New DTO
**CustomerQuarterStatisticDTO.java** (`src/main/java/com/fat/DTO/Statistics/`)
```java
// Fields: int customerId, String customerName, BigDecimal q1Amount, q2Amount, q3Amount, q4Amount, totalAmount
```

#### DAO/Service Layer
**IStatisticDAO.java** + **IStatisticService.java** - Added:
```java
List<CustomerStatisticDTO> getCustomerStatistic(LocalDate fromDate, LocalDate toDate);
List<CustomerQuarterStatisticDTO> getCustomerQuarterStatistic(int year);
```

**StatisticDAO.java** - SQL implementations:
- `getCustomerStatistic`: LEFT JOIN Customer + Receipt with BETWEEN date range, GROUP BY c.Id, c.FirstName, c.LastName (NOTE: table has FirstName + LastName, NOT Name column!)
  - Concatenates: `(c.FirstName + ' ' + c.LastName) AS customerName`
- `getCustomerQuarterStatistic`: CASE WHEN DATEPART(QUARTER,...) for Q1-Q4, filter by YEAR()

**StatisticService.java** - Delegates to DAO (pass-through)

#### GUI - CustomerPanel.java (REWRITTEN)
`src/main/java/com/fat/GUI/Panels/Statistics/CustomerPanel.java`
- Now uses JTabbedPane with 4 tabs pointing to Customer/ sub-panels
- Used in StatisticPanel's CardLayout with key "KHACHHANG"

#### 4 new sub-panels in `src/main/java/com/fat/GUI/Panels/Statistics/Customer/`

**CustomerYearPanel.java**
- Filter: txtSearch (JTextField) + yearFrom + yearTo (JYearChooser)
- Pattern: "Thống kê" button → DB call → stores `fullList` → calls `filterTable()`
- `keyReleased` on txtSearch → `filterTable()` only (in-memory, no DB)
- No auto-load in constructor

**CustomerMonthOfYear.java**
- Filter: txtSearch + jYear (JYearChooser) + monthFrom + monthTo (JMonthChooser)
- Uses `TemporalAdjusters.lastDayOfMonth()` for toDate
- Same search pattern as above

**CustomerDayToDay.java**
- Filter: txtSearch + dateFrom + dateTo (JDateChooser)
- Constructor sets: `dateFrom.setDate(new Date()); dateTo.setDate(new Date());`
- Converts Date via `.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()`
- Same search pattern as above

**CustomerQuarter.java**
- Filter: txtSearch + jYear (JYearChooser)
- Table columns: STT, Mã KH, Tên khách hàng, QUÝ 1 (Q1), QUÝ 2 (Q2), QUÝ 3 (Q3), QUÝ 4 (Q4), TỔNG CỘNG
- Constructor sets column max width: col 0 = 70px, col 1 = 70px
- Uses `CustomerQuarterStatisticDTO` + `getCustomerQuarterStatistic(year)`
- Same search pattern as above

#### Current state of all 4 Customer panels (after all edits):
```java
// Fields pattern (CustomerYearPanel example):
private final IStatisticService statisticService;
private JTable tblCustomer;
private JTextField txtSearch;
private com.toedter.calendar.JYearChooser yearFrom;
private com.toedter.calendar.JYearChooser yearTo;
private List<CustomerStatisticDTO> fullList = new java.util.ArrayList<>();

// Constructor: NO auto-load, just init
public CustomerYearPanel() {
    statisticService = StatisticService.getInstance();
    initComponents();
}

// btnStatisticAction: hits DB, stores fullList, calls filterTable()
// filterTable(): filters fullList in-memory by txtSearch keyword, calls initTable()
// initTable(): populates DefaultTableModel, applies center renderer
// NO setRowHeight() — uses L&F default (same as Staff panels)
```

---

### 3. Key Bug Fixes

| Bug | Fix |
|-----|-----|
| Startup error dialogs on all 4 Customer panels | Removed auto-load from constructors |
| "Lỗi khi truy xuất thống kê khách hàng" on every keystroke | Search was calling DB on each key press → separated DB load (button) from in-memory filter (keystroke) |
| Same error on "Thống kê" button click | SQL used `c.Name` but Customer table has `FirstName` + `LastName` → fixed to `(c.FirstName + ' ' + c.LastName)` and `GROUP BY c.Id, c.FirstName, c.LastName` |
| Row height mismatch Customer vs Staff table | Removed explicit `setRowHeight()` from Customer panels → both use L&F default |

---

## Database Schema (Key Tables)

```sql
Customer: Id, FirstName, LastName, Address, PhoneNumber, CreatedAt
Receipt:  Id, Code, CreatedAt, StaffId, SubTotalAmount, TotalDiscountAmount, TotalAmount, CustomerId
Staff:    Id, FirstName, LastName, ...
```
**Important**: Customer has `FirstName` + `LastName`, NOT `Name`!

---

## File Locations Reference

```
src/main/java/com/fat/
├── BUS/
│   ├── Abstractions/Services/IStatisticService.java  ← added 2 methods
│   └── Services/StatisticService.java                ← implemented 2 methods
├── DAO/
│   ├── Abstractions/Repositories/IStatisticDAO.java  ← added 2 methods
│   └── Repositories/StatisticDAO.java                ← SQL implemented
├── DTO/Statistics/
│   ├── CustomerStatisticDTO.java                     ← existing
│   └── CustomerQuarterStatisticDTO.java              ← NEW
└── GUI/Panels/Statistics/
    ├── CustomerPanel.java                            ← REWRITTEN (JTabbedPane)
    └── Customer/
        ├── CustomerYearPanel.java                    ← NEW
        ├── CustomerMonthOfYear.java                  ← NEW
        ├── CustomerDayToDay.java                     ← NEW
        └── CustomerQuarter.java                      ← NEW
```

---

## Pending / Possible Next Tasks
- None explicitly requested. All features working as of end of session.
- If issues arise, check: SQL column names, FormatterUtil.toVND() compatibility with Double vs BigDecimal
