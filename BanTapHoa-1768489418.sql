--Create Database [GroceryDB]
USE [GroceryDB]
GO
/****** Object:  Table [dbo].[Category]    Script Date: 1/24/2026 4:52:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Category](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Name] [nvarchar](100) NOT NULL,
PRIMARY KEY CLUSTERED
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Customer]    Script Date: 1/24/2026 4:52:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Customer](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[FirstName] [nvarchar](max) NOT NULL,
	[LastName] [nvarchar](max) NOT NULL,
	[Address] [nvarchar](max) NOT NULL,
	[PhoneNumber] [nvarchar](max) NOT NULL,
	[CreatedAt] [datetime] NOT NULL,
PRIMARY KEY CLUSTERED
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Import]    Script Date: 1/24/2026 4:52:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Import](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[ImportCode] [nvarchar](100) NOT NULL,
	[SupplierId] [int] NOT NULL,
	[TotalPrice] [decimal](18, 0) NOT NULL,
	[CreatedAt] [datetime] NOT NULL,
	[UpdatedAt] [datetime] NOT NULL,
	[Status] [nvarchar](max) NOT NULL,
	[StaffId] [int] NOT NULL,
PRIMARY KEY CLUSTERED
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ImportDetail]    Script Date: 1/24/2026 4:52:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ImportDetail](
	[ImportId] [int] NOT NULL,
	[Quantity] [int] NOT NULL,
	[ProductId] [int] NOT NULL,
	[UnitName] [nvarchar](max) NOT NULL,
PRIMARY KEY CLUSTERED
(
	[ImportId] ASC,
	[ProductId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[InventoryHistory]    Script Date: 1/24/2026 4:52:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[InventoryHistory](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Quantity] [int] NOT NULL,
	[ProductId] [int] NOT NULL,
	[CreatedAt] [datetime] NOT NULL,
	[Type] [int] NOT NULL,
	[StockAfter] [int] NOT NULL,
PRIMARY KEY CLUSTERED
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Product]    Script Date: 1/24/2026 4:52:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Product](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Name] [nvarchar](100) NOT NULL,
	[Image] [nvarchar](max) NOT NULL,
	[Price] [decimal](18, 0) NOT NULL,
	[Unit] [nvarchar](100) NOT NULL,
	[CreatedAt] [datetime] NOT NULL,
	[UpdatedAt] [datetime] NOT NULL,
	[IsDeleted] [bit] NOT NULL,
	[CategoryId] [int] NOT NULL,
	[Stock] [int] NOT NULL,
PRIMARY KEY CLUSTERED
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Promotion]    Script Date: 1/24/2026 4:52:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Promotion](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Name] [nvarchar](230) NOT NULL,
	[StartDate] [datetime] NOT NULL,
	[EndDate] [datetime] NOT NULL,
	[CreatedAt] [datetime] NOT NULL,
PRIMARY KEY CLUSTERED
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[PromotionDetail]    Script Date: 1/24/2026 4:52:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PromotionDetail](
	[PromotionId] [int] NOT NULL,
	[ProductId] [int] NOT NULL,
	[DiscountPercentage] [decimal](18, 0) NOT NULL,
PRIMARY KEY CLUSTERED
(
	[PromotionId] ASC,
	[ProductId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Receipt]    Script Date: 1/24/2026 4:52:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Receipt](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Code] [nvarchar](max) NOT NULL,
	[CreatedAt] [datetime] NOT NULL,
	[StaffId] [int] NOT NULL,
	[SubTotalAmount] [decimal](18, 0) NOT NULL,
	[TotalDiscountAmount] [decimal](18, 0) NOT NULL,
	[TotalAmount] [decimal](18, 0) NOT NULL,
	[CustomerId] [int] NOT NULL,
PRIMARY KEY CLUSTERED
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ReceiptDetail]    Script Date: 1/24/2026 4:52:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ReceiptDetail](
	[ReceiptId] [int] IDENTITY(1,1) NOT NULL,
	[ProductId] [int] NOT NULL,
	[Quantity] [int] NOT NULL,
	[Price] [decimal](18, 0) NOT NULL,
	[DiscountAmount] [decimal](18, 0) NOT NULL,
	[SubTotalAmount] [decimal](18, 0) NOT NULL,
PRIMARY KEY CLUSTERED
(
	[ReceiptId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Role]    Script Date: 1/24/2026 4:52:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Role](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Name] [nvarchar](100) NOT NULL,
PRIMARY KEY CLUSTERED
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[RoleClaim]    Script Date: 1/24/2026 4:52:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[RoleClaim](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[RoleId] [int] NOT NULL,
	[ClaimType] [nvarchar](100) NOT NULL,
	[Value] [int] NOT NULL,
PRIMARY KEY CLUSTERED
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Staff]    Script Date: 1/24/2026 4:52:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Staff](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[FirstName] [nvarchar](max) NOT NULL,
	[LastName] [nvarchar](max) NOT NULL,
	[Birthday] [date] NOT NULL,
	[Salary] [decimal](18, 0) NOT NULL,
	[PhoneNumber] [nvarchar](max) NOT NULL,
	[RoleId] [int] NOT NULL,
	[CreatedAt] [datetime] NOT NULL,
	[UpdatedAt] [datetime] NOT NULL,
	[UserName] [nvarchar](max) NOT NULL,
	[Password] [nvarchar](max) NOT NULL,
PRIMARY KEY CLUSTERED
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Supplier]    Script Date: 1/24/2026 4:52:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Supplier](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Email] [nvarchar](max) NOT NULL,
	[PhoneNumber] [nvarchar](max) NOT NULL,
	[Name] [nvarchar](max) NOT NULL,
	[Address] [nvarchar](max) NOT NULL,
PRIMARY KEY CLUSTERED
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[Category] ON
GO
INSERT [dbo].[Category] ([Id], [Name]) VALUES (1, N'Đồ uống ')
GO
INSERT [dbo].[Category] ([Id], [Name]) VALUES (2, N'Thực phẩm khô')
GO
SET IDENTITY_INSERT [dbo].[Category] OFF
GO
SET IDENTITY_INSERT [dbo].[Product] ON
GO
INSERT [dbo].[Product] ([Id], [Name], [Image], [Price], [Unit], [CreatedAt], [UpdatedAt], [IsDeleted], [CategoryId], [Stock]) VALUES (1, N'Nước giải khát Coca Cola', N'image1.png', CAST(10000 AS Decimal(18, 0)), N'Lon', CAST(N'2026-01-18T08:30:43.950' AS DateTime), CAST(N'2026-01-18T08:30:43.950' AS DateTime), 0, 1, 0)
GO
INSERT [dbo].[Product] ([Id], [Name], [Image], [Price], [Unit], [CreatedAt], [UpdatedAt], [IsDeleted], [CategoryId], [Stock]) VALUES (2, N'Mì tôm Hảo Hảo chua cay', N'image1.png', CAST(4500 AS Decimal(18, 0)), N'Gói', CAST(N'2026-01-18T08:30:43.950' AS DateTime), CAST(N'2026-01-18T08:30:43.950' AS DateTime), 0, 2, 0)
GO
INSERT [dbo].[Product] ([Id], [Name], [Image], [Price], [Unit], [CreatedAt], [UpdatedAt], [IsDeleted], [CategoryId], [Stock]) VALUES (3, N'Đức Cóng 2', N'606913465_1245400644301762_6239801718936484647_n.png', CAST(1234 AS Decimal(18, 0)), N'123', CAST(N'2026-01-18T19:28:17.470' AS DateTime), CAST(N'2026-01-22T00:04:16.957' AS DateTime), 0, 2, 0)
GO
INSERT [dbo].[Product] ([Id], [Name], [Image], [Price], [Unit], [CreatedAt], [UpdatedAt], [IsDeleted], [CategoryId], [Stock]) VALUES (4, N'Bánh bim bimi', N'1768740810589_599837144_4252740038278400_893015976534846647_n.png', CAST(60000 AS Decimal(18, 0)), N'Bịch', CAST(N'2026-01-18T19:53:30.607' AS DateTime), CAST(N'2026-01-21T23:52:48.347' AS DateTime), 0, 1, 0)
GO
INSERT [dbo].[Product] ([Id], [Name], [Image], [Price], [Unit], [CreatedAt], [UpdatedAt], [IsDeleted], [CategoryId], [Stock]) VALUES (7, N'Bánh mì', N'1768922589836_606913465_1245400644301762_6239801718936484647_n.png', CAST(23000 AS Decimal(18, 0)), N'Bịch', CAST(N'2026-01-20T22:23:09.850' AS DateTime), CAST(N'2026-01-20T22:23:09.850' AS DateTime), 0, 2, 0)
GO
INSERT [dbo].[Product] ([Id], [Name], [Image], [Price], [Unit], [CreatedAt], [UpdatedAt], [IsDeleted], [CategoryId], [Stock]) VALUES (13, N'Test 23', N'1769015238485_Shopping Cart.png', CAST(1231 AS Decimal(18, 0)), N'123', CAST(N'2026-01-22T00:07:18.493' AS DateTime), CAST(N'2026-01-22T00:07:18.493' AS DateTime), 0, 1, 0)
GO
INSERT [dbo].[Product] ([Id], [Name], [Image], [Price], [Unit], [CreatedAt], [UpdatedAt], [IsDeleted], [CategoryId], [Stock]) VALUES (14, N'Test23221', N'1769015257064_Sorting.png', CAST(123 AS Decimal(18, 0)), N'123', CAST(N'2026-01-22T00:07:37.067' AS DateTime), CAST(N'2026-01-22T00:07:37.067' AS DateTime), 0, 2, 0)
GO
INSERT [dbo].[Product] ([Id], [Name], [Image], [Price], [Unit], [CreatedAt], [UpdatedAt], [IsDeleted], [CategoryId], [Stock]) VALUES (15, N'Đccc', N'1769015279257_Shopping Cart.png', CAST(123 AS Decimal(18, 0)), N'cc', CAST(N'2026-01-22T00:07:59.267' AS DateTime), CAST(N'2026-01-22T00:07:59.267' AS DateTime), 0, 2, 0)
GO
INSERT [dbo].[Product] ([Id], [Name], [Image], [Price], [Unit], [CreatedAt], [UpdatedAt], [IsDeleted], [CategoryId], [Stock]) VALUES (16, N'1ssd', N'1769015294410_Sorting.png', CAST(123 AS Decimal(18, 0)), N'21312', CAST(N'2026-01-22T00:08:14.410' AS DateTime), CAST(N'2026-01-22T00:08:14.410' AS DateTime), 0, 1, 0)
GO
INSERT [dbo].[Product] ([Id], [Name], [Image], [Price], [Unit], [CreatedAt], [UpdatedAt], [IsDeleted], [CategoryId], [Stock]) VALUES (17, N'12312', N'1769015302265_Shopping Cart.png', CAST(123 AS Decimal(18, 0)), N'123', CAST(N'2026-01-22T00:08:22.277' AS DateTime), CAST(N'2026-01-22T00:08:22.277' AS DateTime), 0, 1, 0)
GO
INSERT [dbo].[Product] ([Id], [Name], [Image], [Price], [Unit], [CreatedAt], [UpdatedAt], [IsDeleted], [CategoryId], [Stock]) VALUES (18, N'CC122', N'1769015312906_Shopping Cart.png', CAST(123 AS Decimal(18, 0)), N'213', CAST(N'2026-01-22T00:08:32.917' AS DateTime), CAST(N'2026-01-22T20:38:22.997' AS DateTime), 1, 1, 0)
GO
INSERT [dbo].[Product] ([Id], [Name], [Image], [Price], [Unit], [CreatedAt], [UpdatedAt], [IsDeleted], [CategoryId], [Stock]) VALUES (19, N'L', N'1769088789099_Shopping Cart.png', CAST(1244 AS Decimal(18, 0)), N'2CC', CAST(N'2026-01-22T20:33:09.113' AS DateTime), CAST(N'2026-01-24T00:17:23.773' AS DateTime), 0, 1, 0)
GO
SET IDENTITY_INSERT [dbo].[Product] OFF
GO
SET IDENTITY_INSERT [dbo].[Role] ON
GO
INSERT [dbo].[Role] ([Id], [Name]) VALUES (1, N'Admin')
GO
INSERT [dbo].[Role] ([Id], [Name]) VALUES (5, N'Nhân viên')
GO
SET IDENTITY_INSERT [dbo].[Role] OFF
GO
SET IDENTITY_INSERT [dbo].[RoleClaim] ON
GO
INSERT [dbo].[RoleClaim] ([Id], [RoleId], [ClaimType], [Value]) VALUES (12, 1, N'Bán hàng', 3)
GO
INSERT [dbo].[RoleClaim] ([Id], [RoleId], [ClaimType], [Value]) VALUES (13, 1, N'Nhân viên', 15)
GO
INSERT [dbo].[RoleClaim] ([Id], [RoleId], [ClaimType], [Value]) VALUES (14, 1, N'Phiếu nhập', 15)
GO
INSERT [dbo].[RoleClaim] ([Id], [RoleId], [ClaimType], [Value]) VALUES (15, 1, N'Sản phẩm', 15)
GO
INSERT [dbo].[RoleClaim] ([Id], [RoleId], [ClaimType], [Value]) VALUES (16, 1, N'Nhóm quyền', 15)
GO
INSERT [dbo].[RoleClaim] ([Id], [RoleId], [ClaimType], [Value]) VALUES (17, 1, N'Thống kê', 2)
GO
INSERT [dbo].[RoleClaim] ([Id], [RoleId], [ClaimType], [Value]) VALUES (18, 1, N'Hóa đơn', 2)
GO
INSERT [dbo].[RoleClaim] ([Id], [RoleId], [ClaimType], [Value]) VALUES (19, 1, N'Khách hàng', 15)
GO
INSERT [dbo].[RoleClaim] ([Id], [RoleId], [ClaimType], [Value]) VALUES (20, 1, N'Khuyến mãi', 15)
GO
INSERT [dbo].[RoleClaim] ([Id], [RoleId], [ClaimType], [Value]) VALUES (21, 1, N'Nhà cung cấp', 15)
GO
INSERT [dbo].[RoleClaim] ([Id], [RoleId], [ClaimType], [Value]) VALUES (22, 1, N'Danh mục', 15)
GO
INSERT [dbo].[RoleClaim] ([Id], [RoleId], [ClaimType], [Value]) VALUES (23, 5, N'Bán hàng', 3)
GO
INSERT [dbo].[RoleClaim] ([Id], [RoleId], [ClaimType], [Value]) VALUES (24, 5, N'Hóa đơn', 2)
GO
SET IDENTITY_INSERT [dbo].[RoleClaim] OFF
GO
/****** Object:  Index [UQ__Category__3214EC065D7E7BE5]    Script Date: 1/24/2026 4:52:17 PM ******/
ALTER TABLE [dbo].[Category] ADD UNIQUE NONCLUSTERED
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__Category__737584F6A19051ED]    Script Date: 1/24/2026 4:52:17 PM ******/
ALTER TABLE [dbo].[Category] ADD UNIQUE NONCLUSTERED
(
	[Name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [UQ__Customer__3214EC06790A1684]    Script Date: 1/24/2026 4:52:17 PM ******/
ALTER TABLE [dbo].[Customer] ADD UNIQUE NONCLUSTERED
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__Import__439991381AA25AAC]    Script Date: 1/24/2026 4:52:17 PM ******/
ALTER TABLE [dbo].[Import] ADD UNIQUE NONCLUSTERED
(
	[ImportCode] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [UQ__Inventor__3214EC06531B37BE]    Script Date: 1/24/2026 4:52:17 PM ******/
ALTER TABLE [dbo].[InventoryHistory] ADD UNIQUE NONCLUSTERED
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [UQ__Product__3214EC0625849D79]    Script Date: 1/24/2026 4:52:17 PM ******/
ALTER TABLE [dbo].[Product] ADD UNIQUE NONCLUSTERED
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__Product__737584F6449AFC1E]    Script Date: 1/24/2026 4:52:17 PM ******/
ALTER TABLE [dbo].[Product] ADD UNIQUE NONCLUSTERED
(
	[Name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [UQ__Promotio__3214EC06C477B0A2]    Script Date: 1/24/2026 4:52:17 PM ******/
ALTER TABLE [dbo].[Promotion] ADD UNIQUE NONCLUSTERED
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__Promotio__737584F65FC6A6A2]    Script Date: 1/24/2026 4:52:17 PM ******/
ALTER TABLE [dbo].[Promotion] ADD UNIQUE NONCLUSTERED
(
	[Name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [UQ__Receipt__3214EC0672E7B9D7]    Script Date: 1/24/2026 4:52:17 PM ******/
ALTER TABLE [dbo].[Receipt] ADD UNIQUE NONCLUSTERED
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [UQ__ReceiptD__CC08C421C4D9BC27]    Script Date: 1/24/2026 4:52:17 PM ******/
ALTER TABLE [dbo].[ReceiptDetail] ADD UNIQUE NONCLUSTERED
(
	[ReceiptId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [UQ__Role__3214EC062697158F]    Script Date: 1/24/2026 4:52:17 PM ******/
ALTER TABLE [dbo].[Role] ADD UNIQUE NONCLUSTERED
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__Role__737584F61ACB4534]    Script Date: 1/24/2026 4:52:17 PM ******/
ALTER TABLE [dbo].[Role] ADD UNIQUE NONCLUSTERED
(
	[Name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [UQ__RoleClai__3214EC063DB57D7B]    Script Date: 1/24/2026 4:52:17 PM ******/
ALTER TABLE [dbo].[RoleClaim] ADD UNIQUE NONCLUSTERED
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [UQ__Staff__3214EC06A8D18AE6]    Script Date: 1/24/2026 4:52:17 PM ******/
ALTER TABLE [dbo].[Staff] ADD UNIQUE NONCLUSTERED
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [UQ__Supplier__3214EC06E6D0D35C]    Script Date: 1/24/2026 4:52:17 PM ******/
ALTER TABLE [dbo].[Supplier] ADD UNIQUE NONCLUSTERED
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Customer] ADD  DEFAULT (getdate()) FOR [CreatedAt]
GO
ALTER TABLE [dbo].[InventoryHistory] ADD  DEFAULT (getdate()) FOR [CreatedAt]
GO
ALTER TABLE [dbo].[Product] ADD  CONSTRAINT [DF_Stock]  DEFAULT ((0)) FOR [Stock]
GO
ALTER TABLE [dbo].[Promotion] ADD  DEFAULT (getdate()) FOR [CreatedAt]
GO
ALTER TABLE [dbo].[Receipt] ADD  DEFAULT (getdate()) FOR [CreatedAt]
GO
ALTER TABLE [dbo].[Import]  WITH CHECK ADD  CONSTRAINT [Import_fk2] FOREIGN KEY([SupplierId])
REFERENCES [dbo].[Supplier] ([Id])
GO
ALTER TABLE [dbo].[Import] CHECK CONSTRAINT [Import_fk2]
GO
ALTER TABLE [dbo].[Import]  WITH CHECK ADD  CONSTRAINT [Import_fk7] FOREIGN KEY([StaffId])
REFERENCES [dbo].[Staff] ([Id])
GO
ALTER TABLE [dbo].[Import] CHECK CONSTRAINT [Import_fk7]
GO
ALTER TABLE [dbo].[ImportDetail]  WITH CHECK ADD  CONSTRAINT [ImportDetail_fk0] FOREIGN KEY([ImportId])
REFERENCES [dbo].[Import] ([Id])
GO
ALTER TABLE [dbo].[ImportDetail] CHECK CONSTRAINT [ImportDetail_fk0]
GO
ALTER TABLE [dbo].[ImportDetail]  WITH CHECK ADD  CONSTRAINT [ImportDetail_fk2] FOREIGN KEY([ProductId])
REFERENCES [dbo].[Product] ([Id])
GO
ALTER TABLE [dbo].[ImportDetail] CHECK CONSTRAINT [ImportDetail_fk2]
GO
ALTER TABLE [dbo].[InventoryHistory]  WITH CHECK ADD  CONSTRAINT [InventoryHistory_fk2] FOREIGN KEY([ProductId])
REFERENCES [dbo].[Product] ([Id])
GO
ALTER TABLE [dbo].[InventoryHistory] CHECK CONSTRAINT [InventoryHistory_fk2]
GO
ALTER TABLE [dbo].[Product]  WITH CHECK ADD  CONSTRAINT [Product_fk8] FOREIGN KEY([CategoryId])
REFERENCES [dbo].[Category] ([Id])
GO
ALTER TABLE [dbo].[Product] CHECK CONSTRAINT [Product_fk8]
GO
ALTER TABLE [dbo].[PromotionDetail]  WITH CHECK ADD  CONSTRAINT [PromotionDetail_fk0] FOREIGN KEY([PromotionId])
REFERENCES [dbo].[Promotion] ([Id])
GO
ALTER TABLE [dbo].[PromotionDetail] CHECK CONSTRAINT [PromotionDetail_fk0]
GO
ALTER TABLE [dbo].[PromotionDetail]  WITH CHECK ADD  CONSTRAINT [PromotionDetail_fk1] FOREIGN KEY([ProductId])
REFERENCES [dbo].[Product] ([Id])
GO
ALTER TABLE [dbo].[PromotionDetail] CHECK CONSTRAINT [PromotionDetail_fk1]
GO
ALTER TABLE [dbo].[Receipt]  WITH CHECK ADD  CONSTRAINT [Receipt_fk3] FOREIGN KEY([StaffId])
REFERENCES [dbo].[Staff] ([Id])
GO
ALTER TABLE [dbo].[Receipt] CHECK CONSTRAINT [Receipt_fk3]
GO
ALTER TABLE [dbo].[Receipt]  WITH CHECK ADD  CONSTRAINT [Receipt_fk7] FOREIGN KEY([CustomerId])
REFERENCES [dbo].[Customer] ([Id])
GO
ALTER TABLE [dbo].[Receipt] CHECK CONSTRAINT [Receipt_fk7]
GO
ALTER TABLE [dbo].[ReceiptDetail]  WITH CHECK ADD  CONSTRAINT [ReceiptDetail_fk0] FOREIGN KEY([ReceiptId])
REFERENCES [dbo].[Receipt] ([Id])
GO
ALTER TABLE [dbo].[ReceiptDetail] CHECK CONSTRAINT [ReceiptDetail_fk0]
GO
ALTER TABLE [dbo].[ReceiptDetail]  WITH CHECK ADD  CONSTRAINT [ReceiptDetail_fk1] FOREIGN KEY([ProductId])
REFERENCES [dbo].[Product] ([Id])
GO
ALTER TABLE [dbo].[ReceiptDetail] CHECK CONSTRAINT [ReceiptDetail_fk1]
GO
ALTER TABLE [dbo].[RoleClaim]  WITH CHECK ADD  CONSTRAINT [RoleClaim_fk1] FOREIGN KEY([RoleId])
REFERENCES [dbo].[Role] ([Id])
GO
ALTER TABLE [dbo].[RoleClaim] CHECK CONSTRAINT [RoleClaim_fk1]
GO
ALTER TABLE [dbo].[Staff]  WITH CHECK ADD  CONSTRAINT [Staff_fk6] FOREIGN KEY([RoleId])
REFERENCES [dbo].[Role] ([Id])
GO
ALTER TABLE [dbo].[Staff] CHECK CONSTRAINT [Staff_fk6]
GO
