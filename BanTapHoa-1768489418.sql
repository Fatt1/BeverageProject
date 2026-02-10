--Create database [GroceryDB]
USE [GroceryDB]
GO
/****** Object:  Table [dbo].[Category]    Script Date: 2/4/2026 4:24:14 PM ******/
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
/****** Object:  Table [dbo].[Customer]    Script Date: 2/4/2026 4:24:14 PM ******/
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
/****** Object:  Table [dbo].[Import]    Script Date: 2/4/2026 4:24:14 PM ******/
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
/****** Object:  Table [dbo].[ImportDetail]    Script Date: 2/4/2026 4:24:14 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ImportDetail](
	[ImportId] [int] NOT NULL,
	[Quantity] [int] NOT NULL,
	[ProductId] [int] NOT NULL,
	[ImportPrice] [decimal](18, 0) NOT NULL,
PRIMARY KEY CLUSTERED
(
	[ImportId] ASC,
	[ProductId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[InventoryHistory]    Script Date: 2/4/2026 4:24:14 PM ******/
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
/****** Object:  Table [dbo].[Product]    Script Date: 2/4/2026 4:24:14 PM ******/
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
/****** Object:  Table [dbo].[Promotion]    Script Date: 2/4/2026 4:24:14 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Promotion](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Name] [nvarchar](230) NOT NULL,
	[StartDate] [date] NULL,
	[EndDate] [date] NULL,
PRIMARY KEY CLUSTERED
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[PromotionDetail]    Script Date: 2/4/2026 4:24:14 PM ******/
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
/****** Object:  Table [dbo].[Receipt]    Script Date: 2/4/2026 4:24:14 PM ******/
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
/****** Object:  Table [dbo].[ReceiptDetail]    Script Date: 2/4/2026 4:24:14 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ReceiptDetail](
	[ReceiptId] [int] NOT NULL,
	[ProductId] [int] NOT NULL,
	[Quantity] [int] NOT NULL,
	[Price] [decimal](18, 0) NOT NULL,
	[DiscountAmount] [decimal](18, 0) NOT NULL,
	[SubTotalAmount] [decimal](18, 0) NOT NULL,
PRIMARY KEY CLUSTERED
(
	[ReceiptId] ASC,
	[ProductId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Role]    Script Date: 2/4/2026 4:24:14 PM ******/
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
/****** Object:  Table [dbo].[RoleClaim]    Script Date: 2/4/2026 4:24:14 PM ******/
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
/****** Object:  Table [dbo].[Staff]    Script Date: 2/4/2026 4:24:14 PM ******/
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
/****** Object:  Table [dbo].[Supplier]    Script Date: 2/4/2026 4:24:14 PM ******/
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
-- ===== DELETE OLD DATA =====
DELETE FROM [dbo].[ReceiptDetail]
GO
DELETE FROM [dbo].[Receipt]
GO
DELETE FROM [dbo].[ImportDetail]
GO
DELETE FROM [dbo].[Import]
GO
DELETE FROM [dbo].[Customer]
GO
DBCC CHECKIDENT ('[dbo].[Receipt]', RESEED, 0)
GO
DBCC CHECKIDENT ('[dbo].[ReceiptDetail]', RESEED, 0)
GO
DBCC CHECKIDENT ('[dbo].[Import]', RESEED, 0)
GO
DBCC CHECKIDENT ('[dbo].[ImportDetail]', RESEED, 0)
GO
DBCC CHECKIDENT ('[dbo].[Customer]', RESEED, 0)
GO

INSERT [dbo].[Category] ([Id], [Name]) VALUES (1, N'Đồ uống')
GO
INSERT [dbo].[Category] ([Id], [Name]) VALUES (2, N'Thực phẩm khô')
GO
SET IDENTITY_INSERT [dbo].[Category] OFF
GO
SET IDENTITY_INSERT [dbo].[InventoryHistory] ON
GO
INSERT [dbo].[InventoryHistory] ([Id], [Quantity], [ProductId], [CreatedAt], [Type], [StockAfter]) VALUES (3, 10, 2, CAST(N'2025-12-31T08:30:00.123' AS DateTime), 0, 17)
GO
INSERT [dbo].[InventoryHistory] ([Id], [Quantity], [ProductId], [CreatedAt], [Type], [StockAfter]) VALUES (4, 12, 2, CAST(N'2025-11-01T08:30:00.123' AS DateTime), 0, 12)
GO
INSERT [dbo].[InventoryHistory] ([Id], [Quantity], [ProductId], [CreatedAt], [Type], [StockAfter]) VALUES (7, -5, 2, CAST(N'2025-12-03T08:30:00.123' AS DateTime), 1, 7)
GO
SET IDENTITY_INSERT [dbo].[InventoryHistory] OFF
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
INSERT [dbo].[Product] ([Id], [Name], [Image], [Price], [Unit], [CreatedAt], [UpdatedAt], [IsDeleted], [CategoryId], [Stock]) VALUES (18, N'CC122', N'1769015312906_Shopping Cart.png', CAST(123 AS Decimal(18, 0)), N'213', CAST(N'2026-01-22T00:08:32.917' AS DateTime), CAST(N'2026-01-22T20:38:22.997' AS DateTime), 0, 1, 0)
GO
INSERT [dbo].[Product] ([Id], [Name], [Image], [Price], [Unit], [CreatedAt], [UpdatedAt], [IsDeleted], [CategoryId], [Stock]) VALUES (19, N'123', N'1769088789099_Shopping Cart.png', CAST(123 AS Decimal(18, 0)), N'2CC2', CAST(N'2026-01-22T20:33:09.113' AS DateTime), CAST(N'2026-01-31T21:24:17.640' AS DateTime), 0, 1, 0)
GO
INSERT [dbo].[Product] ([Id], [Name], [Image], [Price], [Unit], [CreatedAt], [UpdatedAt], [IsDeleted], [CategoryId], [Stock]) VALUES (20, N'TT', N'1769411736407_606913465_1245400644301762_6239801718936484647_n.png', CAST(2123 AS Decimal(18, 0)), N'123', CAST(N'2026-01-26T14:15:17.283' AS DateTime), CAST(N'2026-01-26T14:15:36.267' AS DateTime), 0, 1, 0)
GO
SET IDENTITY_INSERT [dbo].[Product] OFF
GO
SET IDENTITY_INSERT [dbo].[Promotion] ON
GO
INSERT [dbo].[Promotion] ([Id], [Name], [StartDate], [EndDate]) VALUES (1, N'tezz', CAST(N'2025-12-12' AS Date), CAST(N'2026-12-12' AS Date))
GO
SET IDENTITY_INSERT [dbo].[Promotion] OFF
GO
INSERT [dbo].[PromotionDetail] ([PromotionId], [ProductId], [DiscountPercentage]) VALUES (1, 13, CAST(15 AS Decimal(18, 0)))
GO
INSERT [dbo].[PromotionDetail] ([PromotionId], [ProductId], [DiscountPercentage]) VALUES (1, 20, CAST(5 AS Decimal(18, 0)))
GO
SET IDENTITY_INSERT [dbo].[Role] ON
GO
INSERT [dbo].[Role] ([Id], [Name]) VALUES (1, N'Admin')
GO
INSERT [dbo].[Role] ([Id], [Name]) VALUES (7, N'Nhân viên')
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
INSERT [dbo].[RoleClaim] ([Id], [RoleId], [ClaimType], [Value]) VALUES (33, 7, N'Bán hàng', 3)
GO
INSERT [dbo].[RoleClaim] ([Id], [RoleId], [ClaimType], [Value]) VALUES (34, 7, N'Hóa đơn', 2)
GO
INSERT [dbo].[RoleClaim] ([Id], [RoleId], [ClaimType], [Value]) VALUES (35, 7, N'Danh mục', 2)
GO
INSERT [dbo].[RoleClaim] ([Id], [RoleId], [ClaimType], [Value]) VALUES (36, 7, N'Sản phẩm', 2)
GO
INSERT [dbo].[RoleClaim] ([Id], [RoleId], [ClaimType], [Value]) VALUES (37, 7, N'Nhóm quyền', 2)
GO
SET IDENTITY_INSERT [dbo].[RoleClaim] OFF
GO
SET IDENTITY_INSERT [dbo].[Staff] ON
GO
INSERT [dbo].[Staff] ([Id], [FirstName], [LastName], [Birthday], [Salary], [PhoneNumber], [RoleId], [CreatedAt], [UpdatedAt], [UserName], [Password]) VALUES (1, N'Tấn Phát', N'Hà', CAST(N'2005-01-18' AS Date), CAST(100000 AS Decimal(18, 0)), N'1234567892', 1, CAST(N'2026-01-26T13:28:16.067' AS DateTime), CAST(N'2026-01-26T13:28:16.067' AS DateTime), N'tanphatpro_2005', N'$2a$10$bs.71GFOE8BAt1T9jf.Ht..9sWNJ2dz3N/mBCkterTNzAJhtdM3aG')
GO
INSERT [dbo].[Staff] ([Id], [FirstName], [LastName], [Birthday], [Salary], [PhoneNumber], [RoleId], [CreatedAt], [UpdatedAt], [UserName], [Password]) VALUES (2, N'Chính Thành', N'Trần', CAST(N'2026-01-13' AS Date), CAST(12345678 AS Decimal(18, 0)), N'12345678901', 7, CAST(N'2026-01-26T13:30:13.483' AS DateTime), CAST(N'2026-01-26T13:30:13.483' AS DateTime), N'tanphatpro_2002', N'$2a$10$muVGq0MHEXG.dzlmdvgXU.ypjEhRVmo4is2h7nT4ShZqwjDeqdzNW')
GO
SET IDENTITY_INSERT [dbo].[Staff] OFF
GO
SET IDENTITY_INSERT [dbo].[Supplier] ON
GO
INSERT [dbo].[Supplier] ([Id], [Email], [PhoneNumber], [Name], [Address]) VALUES (2, N'Fatt2', N'123@gmai.com', N'1233456780', N'123123')
GO
SET IDENTITY_INSERT [dbo].[Supplier] OFF
GO
-- ===== INSERT CUSTOMER =====
SET IDENTITY_INSERT [dbo].[Customer] ON
GO
INSERT [dbo].[Customer] ([Id], [FirstName], [LastName], [Address], [PhoneNumber], [CreatedAt]) VALUES (1, N'Nguyễn', N'Văn A', N'123 Nguyễn Huệ, TP HCM', N'0912345678', GETDATE())
GO
INSERT [dbo].[Customer] ([Id], [FirstName], [LastName], [Address], [PhoneNumber], [CreatedAt]) VALUES (2, N'Trần', N'Thị B', N'456 Lê Lợi, TP HCM', N'0987654321', GETDATE())
GO
INSERT [dbo].[Customer] ([Id], [FirstName], [LastName], [Address], [PhoneNumber], [CreatedAt]) VALUES (3, N'Phạm', N'Văn C', N'789 Võ Văn Kiệt, TP HCM', N'0934567890', GETDATE())
GO
SET IDENTITY_INSERT [dbo].[Customer] OFF
GO
-- ===== INSERT RECEIPT (Năm 2023) =====
SET IDENTITY_INSERT [dbo].[Receipt] ON
GO
INSERT [dbo].[Receipt] ([Id], [Code], [CreatedAt], [StaffId], [SubTotalAmount], [TotalDiscountAmount], [TotalAmount], [CustomerId]) VALUES (1, N'HD001-2023', CAST(N'2023-01-15T10:30:00' AS DateTime), 1, CAST(100000 AS Decimal(18, 0)), CAST(10000 AS Decimal(18, 0)), CAST(90000 AS Decimal(18, 0)), 1)
GO
INSERT [dbo].[Receipt] ([Id], [Code], [CreatedAt], [StaffId], [SubTotalAmount], [TotalDiscountAmount], [TotalAmount], [CustomerId]) VALUES (2, N'HD002-2023', CAST(N'2023-02-20T14:15:00' AS DateTime), 2, CAST(150000 AS Decimal(18, 0)), CAST(15000 AS Decimal(18, 0)), CAST(135000 AS Decimal(18, 0)), 2)
GO
INSERT [dbo].[Receipt] ([Id], [Code], [CreatedAt], [StaffId], [SubTotalAmount], [TotalDiscountAmount], [TotalAmount], [CustomerId]) VALUES (3, N'HD003-2023', CAST(N'2023-03-10T09:45:00' AS DateTime), 1, CAST(200000 AS Decimal(18, 0)), CAST(20000 AS Decimal(18, 0)), CAST(180000 AS Decimal(18, 0)), 3)
GO
INSERT [dbo].[Receipt] ([Id], [Code], [CreatedAt], [StaffId], [SubTotalAmount], [TotalDiscountAmount], [TotalAmount], [CustomerId]) VALUES (4, N'HD004-2023', CAST(N'2023-06-25T11:20:00' AS DateTime), 1, CAST(250000 AS Decimal(18, 0)), CAST(25000 AS Decimal(18, 0)), CAST(225000 AS Decimal(18, 0)), 1)
GO
INSERT [dbo].[Receipt] ([Id], [Code], [CreatedAt], [StaffId], [SubTotalAmount], [TotalDiscountAmount], [TotalAmount], [CustomerId]) VALUES (5, N'HD005-2023', CAST(N'2023-09-12T15:30:00' AS DateTime), 2, CAST(180000 AS Decimal(18, 0)), CAST(18000 AS Decimal(18, 0)), CAST(162000 AS Decimal(18, 0)), 2)
GO
INSERT [dbo].[Receipt] ([Id], [Code], [CreatedAt], [StaffId], [SubTotalAmount], [TotalDiscountAmount], [TotalAmount], [CustomerId]) VALUES (6, N'HD006-2023', CAST(N'2023-12-30T16:45:00' AS DateTime), 1, CAST(300000 AS Decimal(18, 0)), CAST(30000 AS Decimal(18, 0)), CAST(270000 AS Decimal(18, 0)), 3)
GO
-- ===== INSERT RECEIPT (Năm 2024) =====
INSERT [dbo].[Receipt] ([Id], [Code], [CreatedAt], [StaffId], [SubTotalAmount], [TotalDiscountAmount], [TotalAmount], [CustomerId]) VALUES (7, N'HD007-2024', CAST(N'2024-01-10T08:30:00' AS DateTime), 1, CAST(120000 AS Decimal(18, 0)), CAST(12000 AS Decimal(18, 0)), CAST(108000 AS Decimal(18, 0)), 1)
GO
INSERT [dbo].[Receipt] ([Id], [Code], [CreatedAt], [StaffId], [SubTotalAmount], [TotalDiscountAmount], [TotalAmount], [CustomerId]) VALUES (8, N'HD008-2024', CAST(N'2024-04-15T13:00:00' AS DateTime), 2, CAST(170000 AS Decimal(18, 0)), CAST(17000 AS Decimal(18, 0)), CAST(153000 AS Decimal(18, 0)), 2)
GO
INSERT [dbo].[Receipt] ([Id], [Code], [CreatedAt], [StaffId], [SubTotalAmount], [TotalDiscountAmount], [TotalAmount], [CustomerId]) VALUES (9, N'HD009-2024', CAST(N'2024-07-20T10:15:00' AS DateTime), 1, CAST(220000 AS Decimal(18, 0)), CAST(22000 AS Decimal(18, 0)), CAST(198000 AS Decimal(18, 0)), 3)
GO
INSERT [dbo].[Receipt] ([Id], [Code], [CreatedAt], [StaffId], [SubTotalAmount], [TotalDiscountAmount], [TotalAmount], [CustomerId]) VALUES (10, N'HD010-2024', CAST(N'2024-10-05T14:30:00' AS DateTime), 2, CAST(280000 AS Decimal(18, 0)), CAST(28000 AS Decimal(18, 0)), CAST(252000 AS Decimal(18, 0)), 1)
GO
-- ===== INSERT RECEIPT (Năm 2025) =====
INSERT [dbo].[Receipt] ([Id], [Code], [CreatedAt], [StaffId], [SubTotalAmount], [TotalDiscountAmount], [TotalAmount], [CustomerId]) VALUES (11, N'HD011-2025', CAST(N'2025-01-05T09:00:00' AS DateTime), 1, CAST(150000 AS Decimal(18, 0)), CAST(15000 AS Decimal(18, 0)), CAST(135000 AS Decimal(18, 0)), 1)
GO
INSERT [dbo].[Receipt] ([Id], [Code], [CreatedAt], [StaffId], [SubTotalAmount], [TotalDiscountAmount], [TotalAmount], [CustomerId]) VALUES (12, N'HD012-2025', CAST(N'2025-01-15T10:30:00' AS DateTime), 2, CAST(180000 AS Decimal(18, 0)), CAST(18000 AS Decimal(18, 0)), CAST(162000 AS Decimal(18, 0)), 2)
GO
INSERT [dbo].[Receipt] ([Id], [Code], [CreatedAt], [StaffId], [SubTotalAmount], [TotalDiscountAmount], [TotalAmount], [CustomerId]) VALUES (13, N'HD013-2025', CAST(N'2025-02-10T11:45:00' AS DateTime), 1, CAST(200000 AS Decimal(18, 0)), CAST(20000 AS Decimal(18, 0)), CAST(180000 AS Decimal(18, 0)), 3)
GO
INSERT [dbo].[Receipt] ([Id], [Code], [CreatedAt], [StaffId], [SubTotalAmount], [TotalDiscountAmount], [TotalAmount], [CustomerId]) VALUES (14, N'HD014-2025', CAST(N'2025-03-20T13:20:00' AS DateTime), 2, CAST(220000 AS Decimal(18, 0)), CAST(22000 AS Decimal(18, 0)), CAST(198000 AS Decimal(18, 0)), 1)
GO
INSERT [dbo].[Receipt] ([Id], [Code], [CreatedAt], [StaffId], [SubTotalAmount], [TotalDiscountAmount], [TotalAmount], [CustomerId]) VALUES (15, N'HD015-2025', CAST(N'2025-04-12T14:00:00' AS DateTime), 1, CAST(190000 AS Decimal(18, 0)), CAST(19000 AS Decimal(18, 0)), CAST(171000 AS Decimal(18, 0)), 2)
GO
INSERT [dbo].[Receipt] ([Id], [Code], [CreatedAt], [StaffId], [SubTotalAmount], [TotalDiscountAmount], [TotalAmount], [CustomerId]) VALUES (16, N'HD016-2025', CAST(N'2025-05-08T09:30:00' AS DateTime), 2, CAST(240000 AS Decimal(18, 0)), CAST(24000 AS Decimal(18, 0)), CAST(216000 AS Decimal(18, 0)), 3)
GO
INSERT [dbo].[Receipt] ([Id], [Code], [CreatedAt], [StaffId], [SubTotalAmount], [TotalDiscountAmount], [TotalAmount], [CustomerId]) VALUES (17, N'HD017-2025', CAST(N'2025-06-18T15:45:00' AS DateTime), 1, CAST(210000 AS Decimal(18, 0)), CAST(21000 AS Decimal(18, 0)), CAST(189000 AS Decimal(18, 0)), 1)
GO
INSERT [dbo].[Receipt] ([Id], [Code], [CreatedAt], [StaffId], [SubTotalAmount], [TotalDiscountAmount], [TotalAmount], [CustomerId]) VALUES (18, N'HD018-2025', CAST(N'2025-07-25T10:00:00' AS DateTime), 2, CAST(270000 AS Decimal(18, 0)), CAST(27000 AS Decimal(18, 0)), CAST(243000 AS Decimal(18, 0)), 2)
GO
INSERT [dbo].[Receipt] ([Id], [Code], [CreatedAt], [StaffId], [SubTotalAmount], [TotalDiscountAmount], [TotalAmount], [CustomerId]) VALUES (19, N'HD019-2025', CAST(N'2025-08-30T12:15:00' AS DateTime), 1, CAST(250000 AS Decimal(18, 0)), CAST(25000 AS Decimal(18, 0)), CAST(225000 AS Decimal(18, 0)), 3)
GO
INSERT [dbo].[Receipt] ([Id], [Code], [CreatedAt], [StaffId], [SubTotalAmount], [TotalDiscountAmount], [TotalAmount], [CustomerId]) VALUES (20, N'HD020-2025', CAST(N'2025-09-14T13:30:00' AS DateTime), 2, CAST(180000 AS Decimal(18, 0)), CAST(18000 AS Decimal(18, 0)), CAST(162000 AS Decimal(18, 0)), 1)
GO
INSERT [dbo].[Receipt] ([Id], [Code], [CreatedAt], [StaffId], [SubTotalAmount], [TotalDiscountAmount], [TotalAmount], [CustomerId]) VALUES (21, N'HD021-2025', CAST(N'2025-10-22T11:00:00' AS DateTime), 1, CAST(290000 AS Decimal(18, 0)), CAST(29000 AS Decimal(18, 0)), CAST(261000 AS Decimal(18, 0)), 2)
GO
INSERT [dbo].[Receipt] ([Id], [Code], [CreatedAt], [StaffId], [SubTotalAmount], [TotalDiscountAmount], [TotalAmount], [CustomerId]) VALUES (22, N'HD022-2025', CAST(N'2025-11-10T16:20:00' AS DateTime), 2, CAST(230000 AS Decimal(18, 0)), CAST(23000 AS Decimal(18, 0)), CAST(207000 AS Decimal(18, 0)), 3)
GO
INSERT [dbo].[Receipt] ([Id], [Code], [CreatedAt], [StaffId], [SubTotalAmount], [TotalDiscountAmount], [TotalAmount], [CustomerId]) VALUES (23, N'HD023-2025', CAST(N'2025-12-28T17:45:00' AS DateTime), 1, CAST(320000 AS Decimal(18, 0)), CAST(32000 AS Decimal(18, 0)), CAST(288000 AS Decimal(18, 0)), 1)
GO
SET IDENTITY_INSERT [dbo].[Receipt] OFF
GO
-- ===== INSERT RECEIPT DETAIL =====
SET IDENTITY_INSERT [dbo].[ReceiptDetail] ON
GO
INSERT [dbo].[ReceiptDetail] ([ReceiptId], [ProductId], [Quantity], [Price], [DiscountAmount], [SubTotalAmount]) VALUES (1, 1, 5, CAST(10000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(50000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ReceiptDetail] ([ReceiptId], [ProductId], [Quantity], [Price], [DiscountAmount], [SubTotalAmount]) VALUES (1, 2, 10, CAST(5000 AS Decimal(18, 0)), CAST(10000 AS Decimal(18, 0)), CAST(50000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ReceiptDetail] ([ReceiptId], [ProductId], [Quantity], [Price], [DiscountAmount], [SubTotalAmount]) VALUES (2, 1, 3, CAST(10000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(30000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ReceiptDetail] ([ReceiptId], [ProductId], [Quantity], [Price], [DiscountAmount], [SubTotalAmount]) VALUES (2, 4, 2, CAST(60000 AS Decimal(18, 0)), CAST(15000 AS Decimal(18, 0)), CAST(105000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ReceiptDetail] ([ReceiptId], [ProductId], [Quantity], [Price], [DiscountAmount], [SubTotalAmount]) VALUES (3, 2, 20, CAST(10000 AS Decimal(18, 0)), CAST(20000 AS Decimal(18, 0)), CAST(180000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ReceiptDetail] ([ReceiptId], [ProductId], [Quantity], [Price], [DiscountAmount], [SubTotalAmount]) VALUES (4, 1, 15, CAST(10000 AS Decimal(18, 0)), CAST(25000 AS Decimal(18, 0)), CAST(125000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ReceiptDetail] ([ReceiptId], [ProductId], [Quantity], [Price], [DiscountAmount], [SubTotalAmount]) VALUES (4, 4, 2, CAST(60000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(120000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ReceiptDetail] ([ReceiptId], [ProductId], [Quantity], [Price], [DiscountAmount], [SubTotalAmount]) VALUES (5, 7, 8, CAST(20000 AS Decimal(18, 0)), CAST(18000 AS Decimal(18, 0)), CAST(142000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ReceiptDetail] ([ReceiptId], [ProductId], [Quantity], [Price], [DiscountAmount], [SubTotalAmount]) VALUES (6, 1, 10, CAST(10000 AS Decimal(18, 0)), CAST(30000 AS Decimal(18, 0)), CAST(70000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ReceiptDetail] ([ReceiptId], [ProductId], [Quantity], [Price], [DiscountAmount], [SubTotalAmount]) VALUES (6, 4, 4, CAST(60000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(240000 AS Decimal(18, 0)))
GO
-- Insert cho 2024, 2025
INSERT [dbo].[ReceiptDetail] ([ReceiptId], [ProductId], [Quantity], [Price], [DiscountAmount], [SubTotalAmount]) VALUES (7, 1, 6, CAST(10000 AS Decimal(18, 0)), CAST(12000 AS Decimal(18, 0)), CAST(48000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ReceiptDetail] ([ReceiptId], [ProductId], [Quantity], [Price], [DiscountAmount], [SubTotalAmount]) VALUES (7, 2, 12, CAST(5000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(60000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ReceiptDetail] ([ReceiptId], [ProductId], [Quantity], [Price], [DiscountAmount], [SubTotalAmount]) VALUES (8, 4, 2, CAST(85000 AS Decimal(18, 0)), CAST(17000 AS Decimal(18, 0)), CAST(153000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ReceiptDetail] ([ReceiptId], [ProductId], [Quantity], [Price], [DiscountAmount], [SubTotalAmount]) VALUES (9, 7, 10, CAST(20000 AS Decimal(18, 0)), CAST(22000 AS Decimal(18, 0)), CAST(198000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ReceiptDetail] ([ReceiptId], [ProductId], [Quantity], [Price], [DiscountAmount], [SubTotalAmount]) VALUES (10, 1, 14, CAST(10000 AS Decimal(18, 0)), CAST(28000 AS Decimal(18, 0)), CAST(112000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ReceiptDetail] ([ReceiptId], [ProductId], [Quantity], [Price], [DiscountAmount], [SubTotalAmount]) VALUES (10, 4, 2, CAST(84000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(168000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ReceiptDetail] ([ReceiptId], [ProductId], [Quantity], [Price], [DiscountAmount], [SubTotalAmount]) VALUES (11, 1, 5, CAST(10000 AS Decimal(18, 0)), CAST(15000 AS Decimal(18, 0)), CAST(35000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ReceiptDetail] ([ReceiptId], [ProductId], [Quantity], [Price], [DiscountAmount], [SubTotalAmount]) VALUES (11, 2, 23, CAST(5000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(115000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ReceiptDetail] ([ReceiptId], [ProductId], [Quantity], [Price], [DiscountAmount], [SubTotalAmount]) VALUES (12, 4, 2, CAST(90000 AS Decimal(18, 0)), CAST(18000 AS Decimal(18, 0)), CAST(162000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ReceiptDetail] ([ReceiptId], [ProductId], [Quantity], [Price], [DiscountAmount], [SubTotalAmount]) VALUES (13, 7, 10, CAST(20000 AS Decimal(18, 0)), CAST(20000 AS Decimal(18, 0)), CAST(180000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ReceiptDetail] ([ReceiptId], [ProductId], [Quantity], [Price], [DiscountAmount], [SubTotalAmount]) VALUES (14, 1, 10, CAST(10000 AS Decimal(18, 0)), CAST(22000 AS Decimal(18, 0)), CAST(78000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ReceiptDetail] ([ReceiptId], [ProductId], [Quantity], [Price], [DiscountAmount], [SubTotalAmount]) VALUES (14, 2, 28, CAST(5000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(140000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ReceiptDetail] ([ReceiptId], [ProductId], [Quantity], [Price], [DiscountAmount], [SubTotalAmount]) VALUES (15, 4, 2, CAST(95000 AS Decimal(18, 0)), CAST(19000 AS Decimal(18, 0)), CAST(171000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ReceiptDetail] ([ReceiptId], [ProductId], [Quantity], [Price], [DiscountAmount], [SubTotalAmount]) VALUES (16, 1, 12, CAST(10000 AS Decimal(18, 0)), CAST(24000 AS Decimal(18, 0)), CAST(96000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ReceiptDetail] ([ReceiptId], [ProductId], [Quantity], [Price], [DiscountAmount], [SubTotalAmount]) VALUES (16, 7, 5, CAST(24000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(120000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ReceiptDetail] ([ReceiptId], [ProductId], [Quantity], [Price], [DiscountAmount], [SubTotalAmount]) VALUES (17, 2, 22, CAST(8000 AS Decimal(18, 0)), CAST(21000 AS Decimal(18, 0)), CAST(155000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ReceiptDetail] ([ReceiptId], [ProductId], [Quantity], [Price], [DiscountAmount], [SubTotalAmount]) VALUES (17, 4, 1, CAST(54000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(54000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ReceiptDetail] ([ReceiptId], [ProductId], [Quantity], [Price], [DiscountAmount], [SubTotalAmount]) VALUES (18, 1, 10, CAST(10000 AS Decimal(18, 0)), CAST(27000 AS Decimal(18, 0)), CAST(73000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ReceiptDetail] ([ReceiptId], [ProductId], [Quantity], [Price], [DiscountAmount], [SubTotalAmount]) VALUES (18, 4, 3, CAST(66000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(198000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ReceiptDetail] ([ReceiptId], [ProductId], [Quantity], [Price], [DiscountAmount], [SubTotalAmount]) VALUES (19, 7, 12, CAST(20000 AS Decimal(18, 0)), CAST(25000 AS Decimal(18, 0)), CAST(215000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ReceiptDetail] ([ReceiptId], [ProductId], [Quantity], [Price], [DiscountAmount], [SubTotalAmount]) VALUES (20, 2, 25, CAST(6000 AS Decimal(18, 0)), CAST(18000 AS Decimal(18, 0)), CAST(132000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ReceiptDetail] ([ReceiptId], [ProductId], [Quantity], [Price], [DiscountAmount], [SubTotalAmount]) VALUES (20, 1, 3, CAST(10000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(30000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ReceiptDetail] ([ReceiptId], [ProductId], [Quantity], [Price], [DiscountAmount], [SubTotalAmount]) VALUES (21, 4, 2, CAST(100000 AS Decimal(18, 0)), CAST(29000 AS Decimal(18, 0)), CAST(171000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ReceiptDetail] ([ReceiptId], [ProductId], [Quantity], [Price], [DiscountAmount], [SubTotalAmount]) VALUES (21, 7, 5, CAST(24000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(120000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ReceiptDetail] ([ReceiptId], [ProductId], [Quantity], [Price], [DiscountAmount], [SubTotalAmount]) VALUES (22, 1, 11, CAST(10000 AS Decimal(18, 0)), CAST(23000 AS Decimal(18, 0)), CAST(87000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ReceiptDetail] ([ReceiptId], [ProductId], [Quantity], [Price], [DiscountAmount], [SubTotalAmount]) VALUES (22, 2, 28, CAST(5000 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(140000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ReceiptDetail] ([ReceiptId], [ProductId], [Quantity], [Price], [DiscountAmount], [SubTotalAmount]) VALUES (23, 1, 15, CAST(10000 AS Decimal(18, 0)), CAST(32000 AS Decimal(18, 0)), CAST(118000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ReceiptDetail] ([ReceiptId], [ProductId], [Quantity], [Price], [DiscountAmount], [SubTotalAmount]) VALUES (23, 4, 3, CAST(67333 AS Decimal(18, 0)), CAST(0 AS Decimal(18, 0)), CAST(202000 AS Decimal(18, 0)))
GO
SET IDENTITY_INSERT [dbo].[ReceiptDetail] OFF
GO
-- ===== INSERT IMPORT (Nhập hàng) =====
SET IDENTITY_INSERT [dbo].[Import] ON
GO
INSERT [dbo].[Import] ([Id], [ImportCode], [SupplierId], [TotalPrice], [CreatedAt], [UpdatedAt], [Status], [StaffId]) VALUES (1, N'PH001-2023', 2, CAST(50000 AS Decimal(18, 0)), CAST(N'2023-01-10T08:00:00' AS DateTime), CAST(N'2023-01-10T08:00:00' AS DateTime), N'Hoàn thành', 1)
GO
INSERT [dbo].[Import] ([Id], [ImportCode], [SupplierId], [TotalPrice], [CreatedAt], [UpdatedAt], [Status], [StaffId]) VALUES (2, N'PH002-2023', 2, CAST(75000 AS Decimal(18, 0)), CAST(N'2023-02-15T09:30:00' AS DateTime), CAST(N'2023-02-15T09:30:00' AS DateTime), N'Hoàn thành', 2)
GO
INSERT [dbo].[Import] ([Id], [ImportCode], [SupplierId], [TotalPrice], [CreatedAt], [UpdatedAt], [Status], [StaffId]) VALUES (3, N'PH003-2023', 2, CAST(100000 AS Decimal(18, 0)), CAST(N'2023-03-05T10:00:00' AS DateTime), CAST(N'2023-03-05T10:00:00' AS DateTime), N'Hoàn thành', 1)
GO
INSERT [dbo].[Import] ([Id], [ImportCode], [SupplierId], [TotalPrice], [CreatedAt], [UpdatedAt], [Status], [StaffId]) VALUES (4, N'PH004-2023', 2, CAST(120000 AS Decimal(18, 0)), CAST(N'2023-06-20T11:15:00' AS DateTime), CAST(N'2023-06-20T11:15:00' AS DateTime), N'Hoàn thành', 2)
GO
INSERT [dbo].[Import] ([Id], [ImportCode], [SupplierId], [TotalPrice], [CreatedAt], [UpdatedAt], [Status], [StaffId]) VALUES (5, N'PH005-2023', 2, CAST(90000 AS Decimal(18, 0)), CAST(N'2023-09-08T14:45:00' AS DateTime), CAST(N'2023-09-08T14:45:00' AS DateTime), N'Hoàn thành', 1)
GO
INSERT [dbo].[Import] ([Id], [ImportCode], [SupplierId], [TotalPrice], [CreatedAt], [UpdatedAt], [Status], [StaffId]) VALUES (6, N'PH006-2023', 2, CAST(150000 AS Decimal(18, 0)), CAST(N'2023-12-25T16:00:00' AS DateTime), CAST(N'2023-12-25T16:00:00' AS DateTime), N'Hoàn thành', 2)
GO
INSERT [dbo].[Import] ([Id], [ImportCode], [SupplierId], [TotalPrice], [CreatedAt], [UpdatedAt], [Status], [StaffId]) VALUES (7, N'PH007-2024', 2, CAST(60000 AS Decimal(18, 0)), CAST(N'2024-01-08T08:30:00' AS DateTime), CAST(N'2024-01-08T08:30:00' AS DateTime), N'Hoàn thành', 1)
GO
INSERT [dbo].[Import] ([Id], [ImportCode], [SupplierId], [TotalPrice], [CreatedAt], [UpdatedAt], [Status], [StaffId]) VALUES (8, N'PH008-2024', 2, CAST(85000 AS Decimal(18, 0)), CAST(N'2024-04-10T13:00:00' AS DateTime), CAST(N'2024-04-10T13:00:00' AS DateTime), N'Hoàn thành', 2)
GO
INSERT [dbo].[Import] ([Id], [ImportCode], [SupplierId], [TotalPrice], [CreatedAt], [UpdatedAt], [Status], [StaffId]) VALUES (9, N'PH009-2024', 2, CAST(110000 AS Decimal(18, 0)), CAST(N'2024-07-15T10:00:00' AS DateTime), CAST(N'2024-07-15T10:00:00' AS DateTime), N'Hoàn thành', 1)
GO
INSERT [dbo].[Import] ([Id], [ImportCode], [SupplierId], [TotalPrice], [CreatedAt], [UpdatedAt], [Status], [StaffId]) VALUES (10, N'PH010-2024', 2, CAST(140000 AS Decimal(18, 0)), CAST(N'2024-10-01T14:30:00' AS DateTime), CAST(N'2024-10-01T14:30:00' AS DateTime), N'Hoàn thành', 2)
GO
INSERT [dbo].[Import] ([Id], [ImportCode], [SupplierId], [TotalPrice], [CreatedAt], [UpdatedAt], [Status], [StaffId]) VALUES (11, N'PH011-2025', 2, CAST(75000 AS Decimal(18, 0)), CAST(N'2025-01-03T09:00:00' AS DateTime), CAST(N'2025-01-03T09:00:00' AS DateTime), N'Hoàn thành', 1)
GO
INSERT [dbo].[Import] ([Id], [ImportCode], [SupplierId], [TotalPrice], [CreatedAt], [UpdatedAt], [Status], [StaffId]) VALUES (12, N'PH012-2025', 2, CAST(90000 AS Decimal(18, 0)), CAST(N'2025-01-12T10:30:00' AS DateTime), CAST(N'2025-01-12T10:30:00' AS DateTime), N'Hoàn thành', 2)
GO
INSERT [dbo].[Import] ([Id], [ImportCode], [SupplierId], [TotalPrice], [CreatedAt], [UpdatedAt], [Status], [StaffId]) VALUES (13, N'PH013-2025', 2, CAST(100000 AS Decimal(18, 0)), CAST(N'2025-02-08T11:45:00' AS DateTime), CAST(N'2025-02-08T11:45:00' AS DateTime), N'Hoàn thành', 1)
GO
INSERT [dbo].[Import] ([Id], [ImportCode], [SupplierId], [TotalPrice], [CreatedAt], [UpdatedAt], [Status], [StaffId]) VALUES (14, N'PH014-2025', 2, CAST(110000 AS Decimal(18, 0)), CAST(N'2025-03-18T13:20:00' AS DateTime), CAST(N'2025-03-18T13:20:00' AS DateTime), N'Hoàn thành', 2)
GO
INSERT [dbo].[Import] ([Id], [ImportCode], [SupplierId], [TotalPrice], [CreatedAt], [UpdatedAt], [Status], [StaffId]) VALUES (15, N'PH015-2025', 2, CAST(95000 AS Decimal(18, 0)), CAST(N'2025-04-10T14:00:00' AS DateTime), CAST(N'2025-04-10T14:00:00' AS DateTime), N'Hoàn thành', 1)
GO
INSERT [dbo].[Import] ([Id], [ImportCode], [SupplierId], [TotalPrice], [CreatedAt], [UpdatedAt], [Status], [StaffId]) VALUES (16, N'PH016-2025', 2, CAST(120000 AS Decimal(18, 0)), CAST(N'2025-05-05T09:30:00' AS DateTime), CAST(N'2025-05-05T09:30:00' AS DateTime), N'Hoàn thành', 2)
GO
INSERT [dbo].[Import] ([Id], [ImportCode], [SupplierId], [TotalPrice], [CreatedAt], [UpdatedAt], [Status], [StaffId]) VALUES (17, N'PH017-2025', 2, CAST(105000 AS Decimal(18, 0)), CAST(N'2025-06-15T15:45:00' AS DateTime), CAST(N'2025-06-15T15:45:00' AS DateTime), N'Hoàn thành', 1)
GO
INSERT [dbo].[Import] ([Id], [ImportCode], [SupplierId], [TotalPrice], [CreatedAt], [UpdatedAt], [Status], [StaffId]) VALUES (18, N'PH018-2025', 2, CAST(135000 AS Decimal(18, 0)), CAST(N'2025-07-22T10:00:00' AS DateTime), CAST(N'2025-07-22T10:00:00' AS DateTime), N'Hoàn thành', 2)
GO
INSERT [dbo].[Import] ([Id], [ImportCode], [SupplierId], [TotalPrice], [CreatedAt], [UpdatedAt], [Status], [StaffId]) VALUES (19, N'PH019-2025', 2, CAST(125000 AS Decimal(18, 0)), CAST(N'2025-08-28T12:15:00' AS DateTime), CAST(N'2025-08-28T12:15:00' AS DateTime), N'Hoàn thành', 1)
GO
INSERT [dbo].[Import] ([Id], [ImportCode], [SupplierId], [TotalPrice], [CreatedAt], [UpdatedAt], [Status], [StaffId]) VALUES (20, N'PH020-2025', 2, CAST(90000 AS Decimal(18, 0)), CAST(N'2025-09-10T13:30:00' AS DateTime), CAST(N'2025-09-10T13:30:00' AS DateTime), N'Hoàn thành', 2)
GO
INSERT [dbo].[Import] ([Id], [ImportCode], [SupplierId], [TotalPrice], [CreatedAt], [UpdatedAt], [Status], [StaffId]) VALUES (21, N'PH021-2025', 2, CAST(145000 AS Decimal(18, 0)), CAST(N'2025-10-20T11:00:00' AS DateTime), CAST(N'2025-10-20T11:00:00' AS DateTime), N'Hoàn thành', 1)
GO
INSERT [dbo].[Import] ([Id], [ImportCode], [SupplierId], [TotalPrice], [CreatedAt], [UpdatedAt], [Status], [StaffId]) VALUES (22, N'PH022-2025', 2, CAST(115000 AS Decimal(18, 0)), CAST(N'2025-11-08T16:20:00' AS DateTime), CAST(N'2025-11-08T16:20:00' AS DateTime), N'Hoàn thành', 2)
GO
INSERT [dbo].[Import] ([Id], [ImportCode], [SupplierId], [TotalPrice], [CreatedAt], [UpdatedAt], [Status], [StaffId]) VALUES (23, N'PH023-2025', 2, CAST(160000 AS Decimal(18, 0)), CAST(N'2025-12-26T17:45:00' AS DateTime), CAST(N'2025-12-26T17:45:00' AS DateTime), N'Hoàn thành', 1)
GO
SET IDENTITY_INSERT [dbo].[Import] OFF
GO
-- ===== INSERT IMPORT DETAIL =====
SET IDENTITY_INSERT [dbo].[ImportDetail] ON
GO
INSERT [dbo].[ImportDetail] ([ImportId], [ProductId], [Quantity], [ImportPrice]) VALUES (1, 1, 5, CAST(8000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ImportDetail] ([ImportId], [ProductId], [Quantity], [ImportPrice]) VALUES (2, 2, 15, CAST(3000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ImportDetail] ([ImportId], [ProductId], [Quantity], [ImportPrice]) VALUES (2, 4, 1, CAST(30000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ImportDetail] ([ImportId], [ProductId], [Quantity], [ImportPrice]) VALUES (3, 1, 10, CAST(8000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ImportDetail] ([ImportId], [ProductId], [Quantity], [ImportPrice]) VALUES (3, 7, 2, CAST(10000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ImportDetail] ([ImportId], [ProductId], [Quantity], [ImportPrice]) VALUES (4, 4, 2, CAST(50000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ImportDetail] ([ImportId], [ProductId], [Quantity], [ImportPrice]) VALUES (4, 2, 6, CAST(3000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ImportDetail] ([ImportId], [ProductId], [Quantity], [ImportPrice]) VALUES (5, 1, 8, CAST(8500 AS Decimal(18, 0)))
GO
INSERT [dbo].[ImportDetail] ([ImportId], [ProductId], [Quantity], [ImportPrice]) VALUES (5, 7, 1, CAST(15000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ImportDetail] ([ImportId], [ProductId], [Quantity], [ImportPrice]) VALUES (6, 4, 2, CAST(55000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ImportDetail] ([ImportId], [ProductId], [Quantity], [ImportPrice]) VALUES (6, 1, 5, CAST(8000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ImportDetail] ([ImportId], [ProductId], [Quantity], [ImportPrice]) VALUES (7, 1, 6, CAST(8000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ImportDetail] ([ImportId], [ProductId], [Quantity], [ImportPrice]) VALUES (7, 2, 4, CAST(3000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ImportDetail] ([ImportId], [ProductId], [Quantity], [ImportPrice]) VALUES (8, 4, 2, CAST(40000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ImportDetail] ([ImportId], [ProductId], [Quantity], [ImportPrice]) VALUES (8, 7, 1, CAST(5000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ImportDetail] ([ImportId], [ProductId], [Quantity], [ImportPrice]) VALUES (9, 7, 10, CAST(10000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ImportDetail] ([ImportId], [ProductId], [Quantity], [ImportPrice]) VALUES (10, 1, 14, CAST(8000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ImportDetail] ([ImportId], [ProductId], [Quantity], [ImportPrice]) VALUES (10, 4, 1, CAST(60000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ImportDetail] ([ImportId], [ProductId], [Quantity], [ImportPrice]) VALUES (11, 1, 5, CAST(8000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ImportDetail] ([ImportId], [ProductId], [Quantity], [ImportPrice]) VALUES (11, 2, 15, CAST(3000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ImportDetail] ([ImportId], [ProductId], [Quantity], [ImportPrice]) VALUES (12, 4, 2, CAST(40000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ImportDetail] ([ImportId], [ProductId], [Quantity], [ImportPrice]) VALUES (12, 1, 3, CAST(8000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ImportDetail] ([ImportId], [ProductId], [Quantity], [ImportPrice]) VALUES (13, 7, 10, CAST(10000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ImportDetail] ([ImportId], [ProductId], [Quantity], [ImportPrice]) VALUES (14, 1, 10, CAST(8000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ImportDetail] ([ImportId], [ProductId], [Quantity], [ImportPrice]) VALUES (14, 2, 8, CAST(3000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ImportDetail] ([ImportId], [ProductId], [Quantity], [ImportPrice]) VALUES (15, 4, 2, CAST(42000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ImportDetail] ([ImportId], [ProductId], [Quantity], [ImportPrice]) VALUES (15, 7, 1, CAST(11000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ImportDetail] ([ImportId], [ProductId], [Quantity], [ImportPrice]) VALUES (16, 1, 12, CAST(8000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ImportDetail] ([ImportId], [ProductId], [Quantity], [ImportPrice]) VALUES (16, 7, 1, CAST(24000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ImportDetail] ([ImportId], [ProductId], [Quantity], [ImportPrice]) VALUES (17, 2, 15, CAST(3000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ImportDetail] ([ImportId], [ProductId], [Quantity], [ImportPrice]) VALUES (17, 4, 1, CAST(60000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ImportDetail] ([ImportId], [ProductId], [Quantity], [ImportPrice]) VALUES (18, 1, 10, CAST(8000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ImportDetail] ([ImportId], [ProductId], [Quantity], [ImportPrice]) VALUES (18, 4, 3, CAST(45000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ImportDetail] ([ImportId], [ProductId], [Quantity], [ImportPrice]) VALUES (19, 7, 12, CAST(9500 AS Decimal(18, 0)))
GO
INSERT [dbo].[ImportDetail] ([ImportId], [ProductId], [Quantity], [ImportPrice]) VALUES (20, 2, 25, CAST(3000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ImportDetail] ([ImportId], [ProductId], [Quantity], [ImportPrice]) VALUES (20, 1, 3, CAST(8000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ImportDetail] ([ImportId], [ProductId], [Quantity], [ImportPrice]) VALUES (21, 4, 2, CAST(65000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ImportDetail] ([ImportId], [ProductId], [Quantity], [ImportPrice]) VALUES (21, 7, 1, CAST(15000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ImportDetail] ([ImportId], [ProductId], [Quantity], [ImportPrice]) VALUES (22, 1, 11, CAST(8000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ImportDetail] ([ImportId], [ProductId], [Quantity], [ImportPrice]) VALUES (22, 2, 15, CAST(3000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ImportDetail] ([ImportId], [ProductId], [Quantity], [ImportPrice]) VALUES (23, 1, 15, CAST(8000 AS Decimal(18, 0)))
GO
INSERT [dbo].[ImportDetail] ([ImportId], [ProductId], [Quantity], [ImportPrice]) VALUES (23, 4, 2, CAST(60000 AS Decimal(18, 0)))
GO
SET IDENTITY_INSERT [dbo].[ImportDetail] OFF
GO
/****** Object:  Index [UQ__Category__3214EC065CB284E4]    Script Date: 2/4/2026 4:24:14 PM ******/
ALTER TABLE [dbo].[Category] ADD UNIQUE NONCLUSTERED
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__Category__737584F6A6CA66DC]    Script Date: 2/4/2026 4:24:14 PM ******/
ALTER TABLE [dbo].[Category] ADD UNIQUE NONCLUSTERED
(
	[Name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [UQ__Customer__3214EC065BC373A2]    Script Date: 2/4/2026 4:24:14 PM ******/
ALTER TABLE [dbo].[Customer] ADD UNIQUE NONCLUSTERED
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__Import__43999138EAD4C022]    Script Date: 2/4/2026 4:24:14 PM ******/
ALTER TABLE [dbo].[Import] ADD UNIQUE NONCLUSTERED
(
	[ImportCode] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [UQ__Inventor__3214EC061A2E3F1E]    Script Date: 2/4/2026 4:24:14 PM ******/
ALTER TABLE [dbo].[InventoryHistory] ADD UNIQUE NONCLUSTERED
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [UQ__Product__3214EC06D6D60BF5]    Script Date: 2/4/2026 4:24:14 PM ******/
ALTER TABLE [dbo].[Product] ADD UNIQUE NONCLUSTERED
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__Product__737584F6F7DE3211]    Script Date: 2/4/2026 4:24:14 PM ******/
ALTER TABLE [dbo].[Product] ADD UNIQUE NONCLUSTERED
(
	[Name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [UQ__Promotio__3214EC06012A7898]    Script Date: 2/4/2026 4:24:14 PM ******/
ALTER TABLE [dbo].[Promotion] ADD UNIQUE NONCLUSTERED
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__Promotio__737584F634B60315]    Script Date: 2/4/2026 4:24:14 PM ******/
ALTER TABLE [dbo].[Promotion] ADD UNIQUE NONCLUSTERED
(
	[Name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [UQ__Receipt__3214EC06C9489F58]    Script Date: 2/4/2026 4:24:14 PM ******/
ALTER TABLE [dbo].[Receipt] ADD UNIQUE NONCLUSTERED
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [UQ__ReceiptD__CC08C421E95FDC5C]    Script Date: 2/4/2026 4:24:14 PM ******/
ALTER TABLE [dbo].[ReceiptDetail] ADD UNIQUE NONCLUSTERED
(
	[ReceiptId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [UQ__Role__3214EC062835017E]    Script Date: 2/4/2026 4:24:14 PM ******/
ALTER TABLE [dbo].[Role] ADD UNIQUE NONCLUSTERED
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__Role__737584F6BC369B97]    Script Date: 2/4/2026 4:24:14 PM ******/
ALTER TABLE [dbo].[Role] ADD UNIQUE NONCLUSTERED
(
	[Name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [UQ__RoleClai__3214EC06BF81827F]    Script Date: 2/4/2026 4:24:14 PM ******/
ALTER TABLE [dbo].[RoleClaim] ADD UNIQUE NONCLUSTERED
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [UQ__Staff__3214EC06DC8F2209]    Script Date: 2/4/2026 4:24:14 PM ******/
ALTER TABLE [dbo].[Staff] ADD UNIQUE NONCLUSTERED
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [UQ__Supplier__3214EC063ACD0C87]    Script Date: 2/4/2026 4:24:14 PM ******/
ALTER TABLE [dbo].[Supplier] ADD UNIQUE NONCLUSTERED
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Customer] ADD  DEFAULT (getdate()) FOR [CreatedAt]
GO
ALTER TABLE [dbo].[ImportDetail] ADD  DEFAULT ((0)) FOR [ImportPrice]
GO
ALTER TABLE [dbo].[InventoryHistory] ADD  DEFAULT (getdate()) FOR [CreatedAt]
GO
ALTER TABLE [dbo].[Product] ADD  CONSTRAINT [DF_Stock]  DEFAULT ((0)) FOR [Stock]
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
ON DELETE CASCADE
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
ON DELETE CASCADE
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
ALTER TABLE [dbo].[RoleClaim]  WITH CHECK ADD  CONSTRAINT [FK_RoleClaim_Role] FOREIGN KEY([RoleId])
REFERENCES [dbo].[Role] ([Id])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[RoleClaim] CHECK CONSTRAINT [FK_RoleClaim_Role]
GO
ALTER TABLE [dbo].[Staff]  WITH CHECK ADD  CONSTRAINT [Staff_fk6] FOREIGN KEY([RoleId])
REFERENCES [dbo].[Role] ([Id])
GO
ALTER TABLE [dbo].[Staff] CHECK CONSTRAINT [Staff_fk6]
GO


