--Create database GroceryDB
--Use GroceryDB;
CREATE TABLE [Product] (
	[Id] int IDENTITY(1,1) NOT NULL UNIQUE,
	[Name] nvarchar(100) NOT NULL UNIQUE,
	[Image] nvarchar(max) NOT NULL,
	[Price] decimal(18,0) NOT NULL,
	[Unit] nvarchar(100) NOT NULL,
	[CreatedAt] datetime NOT NULL,
	[UpdatedAt] datetime NOT NULL,
	[IsDeleted] bit NOT NULL,
	[CategoryId] int NOT NULL,
	[Stock] int Default 0 NOT NULL,
	PRIMARY KEY ([Id])
);



CREATE TABLE [Category] (
	[Id] int IDENTITY(1,1) NOT NULL UNIQUE,
	[Name] nvarchar(100) NOT NULL UNIQUE,
	PRIMARY KEY ([Id])
);

CREATE TABLE [Receipt] (
	[Id] int IDENTITY(1,1) NOT NULL UNIQUE,
	[Code] nvarchar(max) NOT NULL,
	[CreatedAt] datetime Default getdate() NOT NULL,
	[StaffId] int NOT NULL,
	[SubTotalAmount] decimal(18,0) NOT NULL,
	[TotalDiscountAmount] decimal(18,0) NOT NULL,
	[TotalAmount] decimal(18,0) NOT NULL,
	[CustomerId] int NOT NULL,
	PRIMARY KEY ([Id])
);

CREATE TABLE [Supplier] (
	[Id] int IDENTITY(1,1) NOT NULL UNIQUE,
	[Email] nvarchar(max) NOT NULL,
	[PhoneNumber] nvarchar(max) NOT NULL,
	[Name] nvarchar(max) NOT NULL,
	[Address] nvarchar(max) NOT NULL,
	PRIMARY KEY ([Id])
);

CREATE TABLE [ReceiptDetail] (
	[ReceiptId] int IDENTITY(1,1) NOT NULL UNIQUE,
	[ProductId] int NOT NULL,
	[Quantity] int NOT NULL,
	[Price] decimal(18,0) NOT NULL,
	[DiscountAmount] decimal(18,0) NOT NULL,
	[SubTotalAmount] decimal(18,0) NOT NULL,
	PRIMARY KEY ([ReceiptId])
);

CREATE TABLE [Import] (
	[Id] int IDENTITY(1,1) NOT NULL,
	[ImportCode] nvarchar(100) NOT NULL UNIQUE,
	[SupplierId] int NOT NULL,
	[TotalPrice] decimal(18,0) NOT NULL,
	[CreatedAt] datetime NOT NULL,
	[UpdatedAt] datetime NOT NULL,
	[Status] nvarchar(max) NOT NULL,
	[StaffId] int NOT NULL,
	PRIMARY KEY ([Id])
);

CREATE TABLE [ImportDetail] (
	[ImportId] int NOT NULL,
	[Quantity] int NOT NULL,
	[ProductId] int NOT NULL,
	[UnitName] nvarchar(max) NOT NULL,
	PRIMARY KEY ([ImportId], [ProductId])
);

CREATE TABLE [Staff] (
	[Id] int IDENTITY(1,1) NOT NULL UNIQUE,
	[FirstName] nvarchar(max) NOT NULL,
	[LastName] nvarchar(max) NOT NULL,
	[Birthday] datetime NOT NULL,
	[Salary] decimal(18,0) NOT NULL,
	[PhoneNumber] nvarchar(max) NOT NULL,
	[RoleId] int NOT NULL,
	[CreatedAt] datetime NOT NULL,
	[UpdatedAt] datetime NOT NULL,
	[UserName] nvarchar(max) NOT NULL,
	[Password] nvarchar(max) NOT NULL,
	PRIMARY KEY ([Id])
);

CREATE TABLE [Customer] (
	[Id] int IDENTITY(1,1) NOT NULL UNIQUE,
	[FirstName] nvarchar(max) NOT NULL,
	[LastName] nvarchar(max) NOT NULL,
	[Address] nvarchar(max) NOT NULL,
	[PhoneNumber] nvarchar(max) NOT NULL,
	[CreatedAt] datetime Default getdate() NOT NULL,
	PRIMARY KEY ([Id])
);

CREATE TABLE [Promotion] (
	[Id] int IDENTITY(1,1) NOT NULL UNIQUE,
	[Name] nvarchar(230) NOT NULL UNIQUE,
	[StartDate] datetime NOT NULL,
	[EndDate] datetime NOT NULL,
	[CreatedAt] datetime Default getdate() NOT NULL,
	PRIMARY KEY ([Id])
);

CREATE TABLE [PromotionDetail] (
	[PromotionId] int NOT NULL,
	[ProductId] int NOT NULL,
	[DiscountPercentage] decimal(18,0) NOT NULL,
	PRIMARY KEY ([PromotionId], [ProductId])
);

CREATE TABLE [RoleClaim] (
	[Id] int IDENTITY(1,1) NOT NULL UNIQUE,
	[RoleId] int NOT NULL,
	[ClaimType] int NOT NULL,
	[Value] int NOT NULL,
	PRIMARY KEY ([Id])
);

CREATE TABLE [Role] (
	[Id] int IDENTITY(1,1) NOT NULL UNIQUE,
	[Name] nvarchar(100) NOT NULL UNIQUE,
	PRIMARY KEY ([Id])
);

CREATE TABLE [InventoryHistory] (
	[Id] int IDENTITY(1,1) NOT NULL UNIQUE,
	[Quantity] int NOT NULL,
	[ProductId] int NOT NULL,
	[CreatedAt] DateTime Default getdate() NOT NULL,
	[Type] int NOT NULL,
	[StockAfter] int NOT NULL,
	PRIMARY KEY ([Id])
);

ALTER TABLE [Product] ADD CONSTRAINT [Product_fk8] FOREIGN KEY ([CategoryId]) REFERENCES [Category]([Id]);

ALTER TABLE [Receipt] ADD CONSTRAINT [Receipt_fk3] FOREIGN KEY ([StaffId]) REFERENCES [Staff]([Id]);

ALTER TABLE [Receipt] ADD CONSTRAINT [Receipt_fk7] FOREIGN KEY ([CustomerId]) REFERENCES [Customer]([Id]);

ALTER TABLE [ReceiptDetail] ADD CONSTRAINT [ReceiptDetail_fk0] FOREIGN KEY ([ReceiptId]) REFERENCES [Receipt]([Id]);

ALTER TABLE [ReceiptDetail] ADD CONSTRAINT [ReceiptDetail_fk1] FOREIGN KEY ([ProductId]) REFERENCES [Product]([Id]);
ALTER TABLE [Import] ADD CONSTRAINT [Import_fk2] FOREIGN KEY ([SupplierId]) REFERENCES [Supplier]([Id]);

ALTER TABLE [Import] ADD CONSTRAINT [Import_fk7] FOREIGN KEY ([StaffId]) REFERENCES [Staff]([Id]);
ALTER TABLE [ImportDetail] ADD CONSTRAINT [ImportDetail_fk0] FOREIGN KEY ([ImportId]) REFERENCES [Import]([Id]);

ALTER TABLE [ImportDetail] ADD CONSTRAINT [ImportDetail_fk2] FOREIGN KEY ([ProductId]) REFERENCES [Product]([Id]);
ALTER TABLE [Staff] ADD CONSTRAINT [Staff_fk6] FOREIGN KEY ([RoleId]) REFERENCES [Role]([Id]);


ALTER TABLE [PromotionDetail] ADD CONSTRAINT [PromotionDetail_fk0] FOREIGN KEY ([PromotionId]) REFERENCES [Promotion]([Id]);

ALTER TABLE [PromotionDetail] ADD CONSTRAINT [PromotionDetail_fk1] FOREIGN KEY ([ProductId]) REFERENCES [Product]([Id]);
ALTER TABLE [RoleClaim] ADD CONSTRAINT [RoleClaim_fk1] FOREIGN KEY ([RoleId]) REFERENCES [Role]([Id]);

ALTER TABLE [InventoryHistory] ADD CONSTRAINT [InventoryHistory_fk2] FOREIGN KEY ([ProductId]) REFERENCES [Product]([Id]);

-- 1. Tạo 2 Category
INSERT INTO [Category] ([Name]) 
VALUES (N'Đồ uống'); -- Sẽ có Id = 1

INSERT INTO [Category] ([Name]) 
VALUES (N'Thực phẩm khô'); -- Sẽ có Id = 2

SET IDENTITY_INSERT [dbo].[Product] ON
INSERT INTO [dbo].[Product] ([Id], [Name], [Image], [Price], [Unit], [CreatedAt], [UpdatedAt], [IsDeleted], [CategoryId], [Stock]) VALUES (1, N'Nước giải khát Coca Cola', N'image1.png', CAST(10000 AS Decimal(18, 0)), N'Lon', N'2026-01-18 08:30:43', N'2026-01-18 08:30:43', 0, 1, 0)
INSERT INTO [dbo].[Product] ([Id], [Name], [Image], [Price], [Unit], [CreatedAt], [UpdatedAt], [IsDeleted], [CategoryId], [Stock]) VALUES (2, N'Mì tôm Hảo Hảo chua cay', N'image1.png', CAST(4500 AS Decimal(18, 0)), N'Gói', N'2026-01-18 08:30:43', N'2026-01-18 08:30:43', 0, 2, 0)
INSERT INTO [dbo].[Product] ([Id], [Name], [Image], [Price], [Unit], [CreatedAt], [UpdatedAt], [IsDeleted], [CategoryId], [Stock]) VALUES (3, N'Đức', N'606913465_1245400644301762_6239801718936484647_n.png', CAST(123 AS Decimal(18, 0)), N'123', N'2026-01-18 19:28:17', N'2026-01-18 19:28:17', 0, 1, 0)
INSERT INTO [dbo].[Product] ([Id], [Name], [Image], [Price], [Unit], [CreatedAt], [UpdatedAt], [IsDeleted], [CategoryId], [Stock]) VALUES (4, N'Bánh bim bimi', N'1768740810589_599837144_4252740038278400_893015976534846647_n.png', CAST(50000 AS Decimal(18, 0)), N'Bịch', N'2026-01-18 19:53:30', N'2026-01-18 19:53:30', 0, 1, 0)
INSERT INTO [dbo].[Product] ([Id], [Name], [Image], [Price], [Unit], [CreatedAt], [UpdatedAt], [IsDeleted], [CategoryId], [Stock]) VALUES (5, N'Test', N'1768740935507_599837144_4252740038278400_893015976534846647_n.png', CAST(2222222 AS Decimal(18, 0)), N'Test', N'2026-01-18 19:55:35', N'2026-01-18 19:55:35', 0, 1, 0)
SET IDENTITY_INSERT [dbo].[Product] OFF
