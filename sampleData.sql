USE [master]
GO
/****** Object:  Database [Java]    Script Date: 1/6/2024 9:54:48 PM ******/
CREATE DATABASE [Java]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'Java', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.PHAMHUYDB\MSSQL\DATA\Java.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'Java_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.PHAMHUYDB\MSSQL\DATA\Java_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
ALTER DATABASE [Java] SET COMPATIBILITY_LEVEL = 160
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [Java].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [Java] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [Java] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [Java] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [Java] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [Java] SET ARITHABORT OFF 
GO
ALTER DATABASE [Java] SET AUTO_CLOSE ON 
GO
ALTER DATABASE [Java] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [Java] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [Java] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [Java] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [Java] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [Java] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [Java] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [Java] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [Java] SET  ENABLE_BROKER 
GO
ALTER DATABASE [Java] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [Java] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [Java] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [Java] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [Java] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [Java] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [Java] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [Java] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [Java] SET  MULTI_USER 
GO
ALTER DATABASE [Java] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [Java] SET DB_CHAINING OFF 
GO
ALTER DATABASE [Java] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [Java] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [Java] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [Java] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
ALTER DATABASE [Java] SET QUERY_STORE = ON
GO
ALTER DATABASE [Java] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [Java]
GO

/****** Object:  UserDefinedDataType [dbo].[GROUP_ID_TYPE]    Script Date: 1/6/2024 9:54:48 PM ******/
CREATE TYPE [dbo].[GROUP_ID_TYPE] FROM [varchar](64) NOT NULL
GO
/****** Object:  UserDefinedDataType [dbo].[USERNAME_TYPE]    Script Date: 1/6/2024 9:54:48 PM ******/
CREATE TYPE [dbo].[USERNAME_TYPE] FROM [varchar](64) NOT NULL
GO
/****** Object:  Table [dbo].[UserInfo]    Script Date: 1/6/2024 9:54:48 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[UserInfo](
	[username] [dbo].[USERNAME_TYPE] NOT NULL,
	[fullname] [nvarchar](100) NOT NULL,
	[email] [varchar](50) NOT NULL,
	[address] [nvarchar](100) NOT NULL,
	[birthdate] [date] NOT NULL,
	[gender] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[UserFriend]    Script Date: 1/6/2024 9:54:48 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[UserFriend](
	[username] [dbo].[USERNAME_TYPE] NOT NULL,
	[friend] [dbo].[USERNAME_TYPE] NOT NULL,
	[start_history_msg] [int] NOT NULL,
	[last_read_msg] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[username] ASC,
	[friend] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  UserDefinedFunction [dbo].[list_friends_info]    Script Date: 1/6/2024 9:54:48 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE FUNCTION [dbo].[list_friends_info](@usr USERNAME_TYPE)
RETURNS TABLE AS
RETURN
	SELECT uf.*
	FROM UserFriend
	JOIN UserInfo uf ON uf.username = UserFriend.friend
	WHERE UserFriend.username = @usr
GO
/****** Object:  Table [dbo].[GroupChat]    Script Date: 1/6/2024 9:54:48 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[GroupChat](
	[id] [dbo].[GROUP_ID_TYPE] NOT NULL,
	[name] [varchar](50) NOT NULL,
	[date] [datetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[GroupChatMember]    Script Date: 1/6/2024 9:54:48 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[GroupChatMember](
	[group_id] [dbo].[GROUP_ID_TYPE] NOT NULL,
	[username] [dbo].[USERNAME_TYPE] NOT NULL,
	[joined_date] [datetime] NOT NULL,
	[is_admin] [bit] NOT NULL,
	[start_history_msg] [int] NOT NULL,
	[last_read_msg] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[group_id] ASC,
	[username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  UserDefinedFunction [dbo].[list_group_of_user]    Script Date: 1/6/2024 9:54:48 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[list_group_of_user](@usr USERNAME_TYPE)
RETURNS TABLE AS
RETURN
	select gc.*
	from GroupChat gc JOIN GroupChatMember gcm on gc.id = gcm.group_id
	where gcm.username = @usr
GO
/****** Object:  Table [dbo].[GroupChatMessage]    Script Date: 1/6/2024 9:54:48 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[GroupChatMessage](
	[id] [int] NOT NULL,
	[group_id] [dbo].[GROUP_ID_TYPE] NOT NULL,
	[sender] [dbo].[USERNAME_TYPE] NOT NULL,
	[sent_date] [datetime] NOT NULL,
	[cipher_msg] [varbinary](max) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC,
	[group_id] ASC,
	[sender] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  UserDefinedFunction [dbo].[get_all_group_msg]    Script Date: 1/6/2024 9:54:48 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[get_all_group_msg](@username USERNAME_TYPE, @group_id GROUP_ID_TYPE) 
RETURNS TABLE AS
RETURN
	select gcmsg.*
	from GroupChatMessage gcmsg
	join GroupChatMember gcm on gcm.group_id = gcmsg.group_id and
		  gcm.username = @username and gcm.group_id = @group_id
	where gcmsg.id >= gcm.start_history_msg
GO
/****** Object:  UserDefinedFunction [dbo].[get_unread_group_msg]    Script Date: 1/6/2024 9:54:48 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[get_unread_group_msg](@username USERNAME_TYPE, @group_id GROUP_ID_TYPE) 
RETURNS TABLE AS
RETURN
	select gcmsg.*
	from GroupChatMessage gcmsg
	join GroupChatMember gcm on gcm.group_id = gcmsg.group_id and
		  gcm.username = @username and gcm.group_id = @group_id
	where gcmsg.id > gcm.last_read_msg
GO
/****** Object:  Table [dbo].[FriendChat]    Script Date: 1/6/2024 9:54:48 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[FriendChat](
	[id] [int] NOT NULL,
	[sender] [dbo].[USERNAME_TYPE] NOT NULL,
	[friend] [dbo].[USERNAME_TYPE] NOT NULL,
	[sent_date] [datetime] NOT NULL,
	[msg] [varbinary](max) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC,
	[sender] ASC,
	[friend] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  UserDefinedFunction [dbo].[get_unread_friend_msg]    Script Date: 1/6/2024 9:54:48 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[get_unread_friend_msg](@username USERNAME_TYPE, @friend USERNAME_TYPE) 
RETURNS TABLE AS
RETURN
	select fc.*
	from FriendChat fc
	join UserFriend uf on uf.username = @username and uf.friend = @friend
	where fc.sender = @friend and fc.friend = @username and fc.id > uf.last_read_msg
GO
/****** Object:  UserDefinedFunction [dbo].[get_all_friend_msg]    Script Date: 1/6/2024 9:54:48 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[get_all_friend_msg](@username USERNAME_TYPE, @friend USERNAME_TYPE) 
RETURNS TABLE AS
RETURN
	select fc.*
	from FriendChat fc
	join UserFriend uf on uf.username = @username and uf.friend = @friend
	where ((fc.sender = @username and fc.friend = @friend) or
              (fc.friend = @username and fc.sender = @friend)) and fc.id >= uf.start_history_msg
GO
/****** Object:  Table [dbo].[Account]    Script Date: 1/6/2024 9:54:48 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Account](
	[username] [dbo].[USERNAME_TYPE] NOT NULL,
	[password] [varchar](512) NOT NULL,
	[type] [int] NOT NULL,
	[is_locked] [bit] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[BlockUser]    Script Date: 1/6/2024 9:54:48 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BlockUser](
	[username] [dbo].[USERNAME_TYPE] NOT NULL,
	[target] [dbo].[USERNAME_TYPE] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[FriendRequest]    Script Date: 1/6/2024 9:54:48 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[FriendRequest](
	[initiator] [dbo].[USERNAME_TYPE] NOT NULL,
	[target] [dbo].[USERNAME_TYPE] NOT NULL,
	[date] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[initiator] ASC,
	[target] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[GroupChatSecret]    Script Date: 1/6/2024 9:54:48 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[GroupChatSecret](
	[username] [dbo].[USERNAME_TYPE] NOT NULL,
	[target] [dbo].[GROUP_ID_TYPE] NOT NULL,
	[aes_params] [varbinary](300) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[username] ASC,
	[target] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[LoginRecord]    Script Date: 1/6/2024 9:54:48 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[LoginRecord](
	[username] [dbo].[USERNAME_TYPE] NOT NULL,
	[ts] [datetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[username] ASC,
	[ts] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[RegistrationRecord]    Script Date: 1/6/2024 9:54:48 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[RegistrationRecord](
	[username] [dbo].[USERNAME_TYPE] NOT NULL,
	[ts] [datetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[SpamReport]    Script Date: 1/6/2024 9:54:48 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[SpamReport](
	[reporter] [dbo].[USERNAME_TYPE] NOT NULL,
	[target] [dbo].[USERNAME_TYPE] NOT NULL,
	[reason] [varchar](500) NOT NULL,
	[date] [datetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[reporter] ASC,
	[date] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[UserChatSecret]    Script Date: 1/6/2024 9:54:48 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[UserChatSecret](
	[username] [dbo].[USERNAME_TYPE] NOT NULL,
	[target] [dbo].[USERNAME_TYPE] NOT NULL,
	[aes_params] [varbinary](300) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[username] ASC,
	[target] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[UserIdentity]    Script Date: 1/6/2024 9:54:48 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[UserIdentity](
	[username] [dbo].[USERNAME_TYPE] NOT NULL,
	[public_key] [varbinary](2048) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [dbo].[Account] ([username], [password], [type], [is_locked]) VALUES (N'__REMOVED__', N'', 0, 1)
INSERT [dbo].[Account] ([username], [password], [type], [is_locked]) VALUES (N'1', N'T8grJq7LR9KGjE7741gXMqPny8xsLvsyBiwIFwoF7rg=', 0, 0)
INSERT [dbo].[Account] ([username], [password], [type], [is_locked]) VALUES (N'2', N'eF8+x+sy8wuQzQ/PNlfTiLX/Qpfy+XFv9m6bacBd3Qk=', 0, 0)
INSERT [dbo].[Account] ([username], [password], [type], [is_locked]) VALUES (N'3', N'xvOsV5RKUxSQzTmQLQ93dxX9AF76yaMGItX1IF5/aJQ=', 0, 0)
INSERT [dbo].[Account] ([username], [password], [type], [is_locked]) VALUES (N'admin', N'2CSU8F1pF7oC96qilonMtES7c/IDgIdssF0fN1N7eJI=', 1, 0)
INSERT [dbo].[Account] ([username], [password], [type], [is_locked]) VALUES (N'huy', N'cs1pvnvQBHIMvmpCm1oPmyC9AlgyFOGr2m4/bEnAjOs=', 0, 0)
INSERT [dbo].[Account] ([username], [password], [type], [is_locked]) VALUES (N'phat', N'lHDmFf5Um2cvNno6a5no9ffIrRLVjd2neY4aqthVPIQ=', 0, 0)
INSERT [dbo].[Account] ([username], [password], [type], [is_locked]) VALUES (N'toan', N'l0HgTppcqnHbodmuIfyMpsG0mf3dyEY6PY4a5Jag8K8=', 0, 0)
INSERT [dbo].[Account] ([username], [password], [type], [is_locked]) VALUES (N'tuong', N'sRZptpHszK6cwOUvb1GbwEMRZnil+1YY3sSEmFaVGrg=', 0, 0)
GO
INSERT [dbo].[BlockUser] ([username], [target]) VALUES (N'1', N'2')
INSERT [dbo].[BlockUser] ([username], [target]) VALUES (N'2', N'1')
INSERT [dbo].[BlockUser] ([username], [target]) VALUES (N'huy', N'2')
GO
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (1, N'1', N'2', CAST(N'2024-01-04T16:54:30.613' AS DateTime), 0x19DD1050CB72423492C7AE103FF6DA1A)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (1, N'1', N'huy', CAST(N'2024-01-06T11:15:03.527' AS DateTime), 0x21378CA236E64D283E4C40C82F895083)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (1, N'3', N'1', CAST(N'2024-01-06T12:03:58.087' AS DateTime), 0x638031DCA410AF20054D9E9BF8B64F33)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (1, N'tuong', N'toan', CAST(N'2024-01-06T14:42:08.847' AS DateTime), 0x5D43EF4FA3275FD85954CC4239B01F8C)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (2, N'2', N'1', CAST(N'2024-01-04T16:54:41.153' AS DateTime), 0x6836864A189A416A406E900A10B7EBB7)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (2, N'huy', N'1', CAST(N'2024-01-06T11:15:29.027' AS DateTime), 0x7A81E8FF252C4930F61DFB027009973C)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (2, N'tuong', N'toan', CAST(N'2024-01-06T14:42:28.983' AS DateTime), 0x5D43EF4FA3275FD85954CC4239B01F8C)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (3, N'1', N'2', CAST(N'2024-01-04T16:54:57.560' AS DateTime), 0x2EE65403ABBB958B23A4C172173AEB01)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (3, N'huy', N'1', CAST(N'2024-01-06T11:28:38.317' AS DateTime), 0x3555595765C5AF5C8CBCBECEA2AB86E4)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (4, N'2', N'1', CAST(N'2024-01-04T16:55:02.867' AS DateTime), 0x901FC09833C501A17A6213B6B01876CF)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (4, N'huy', N'1', CAST(N'2024-01-06T11:28:59.167' AS DateTime), 0x0D7E31313C11E5C3953043CD76612F59)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (5, N'2', N'1', CAST(N'2024-01-04T16:55:08.043' AS DateTime), 0xFE32E64DA78580EA0240DFED2423E9A7)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (5, N'huy', N'1', CAST(N'2024-01-06T11:29:01.677' AS DateTime), 0x14E099CD5F7D459A2A05882567A93227)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (6, N'1', N'2', CAST(N'2024-01-04T16:55:10.267' AS DateTime), 0x79DBE2DAD4DB73827A625AE51754B233)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (6, N'huy', N'1', CAST(N'2024-01-06T11:29:03.973' AS DateTime), 0x83D625825FD5267812F0E0FF2DF9BD13)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (7, N'1', N'2', CAST(N'2024-01-04T16:55:11.113' AS DateTime), 0x79DBE2DAD4DB73827A625AE51754B233)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (7, N'huy', N'1', CAST(N'2024-01-06T11:56:02.540' AS DateTime), 0x7A81E8FF252C4930F61DFB027009973C)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (8, N'1', N'2', CAST(N'2024-01-04T16:55:11.280' AS DateTime), 0x79DBE2DAD4DB73827A625AE51754B233)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (8, N'1', N'huy', CAST(N'2024-01-06T11:56:09.217' AS DateTime), 0xB8D24D261089770443804C9009829A79)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (9, N'1', N'2', CAST(N'2024-01-04T16:55:11.457' AS DateTime), 0x79DBE2DAD4DB73827A625AE51754B233)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (9, N'1', N'huy', CAST(N'2024-01-06T11:57:05.577' AS DateTime), 0x14271640FBF3C526875FB74C668BAF29)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (10, N'1', N'2', CAST(N'2024-01-04T16:55:11.607' AS DateTime), 0x79DBE2DAD4DB73827A625AE51754B233)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (10, N'huy', N'1', CAST(N'2024-01-06T11:57:14.750' AS DateTime), 0xDBDFC5472E1FEDA34F7A685540D2D729BB69BA980FEB34AAF51DA70C97EC5ECC402B9A51CFC3B32DF27D579CF185F11CBC8C5A6597257ACDDA332AC2248E561D4E103E459126373F226909CCEBEC9C14)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (11, N'1', N'2', CAST(N'2024-01-04T16:55:11.757' AS DateTime), 0x79DBE2DAD4DB73827A625AE51754B233)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (11, N'huy', N'1', CAST(N'2024-01-06T11:57:16.287' AS DateTime), 0x1587053FF8FE8AF00E7646488CF12987)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (12, N'1', N'2', CAST(N'2024-01-04T16:55:11.897' AS DateTime), 0x79DBE2DAD4DB73827A625AE51754B233)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (12, N'huy', N'1', CAST(N'2024-01-06T11:57:16.680' AS DateTime), 0x1587053FF8FE8AF00E7646488CF12987)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (13, N'1', N'2', CAST(N'2024-01-04T16:55:12.077' AS DateTime), 0x79DBE2DAD4DB73827A625AE51754B233)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (13, N'huy', N'1', CAST(N'2024-01-06T11:57:16.827' AS DateTime), 0x1587053FF8FE8AF00E7646488CF12987)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (14, N'1', N'2', CAST(N'2024-01-04T16:55:12.197' AS DateTime), 0x79DBE2DAD4DB73827A625AE51754B233)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (14, N'huy', N'1', CAST(N'2024-01-06T11:57:16.957' AS DateTime), 0x1587053FF8FE8AF00E7646488CF12987)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (15, N'1', N'2', CAST(N'2024-01-04T16:55:12.357' AS DateTime), 0x79DBE2DAD4DB73827A625AE51754B233)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (15, N'huy', N'1', CAST(N'2024-01-06T11:57:17.127' AS DateTime), 0x1587053FF8FE8AF00E7646488CF12987)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (16, N'1', N'2', CAST(N'2024-01-04T16:55:12.507' AS DateTime), 0x79DBE2DAD4DB73827A625AE51754B233)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (16, N'huy', N'1', CAST(N'2024-01-06T11:57:17.290' AS DateTime), 0x1587053FF8FE8AF00E7646488CF12987)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (17, N'1', N'2', CAST(N'2024-01-04T16:55:12.650' AS DateTime), 0x79DBE2DAD4DB73827A625AE51754B233)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (17, N'huy', N'1', CAST(N'2024-01-06T11:57:17.440' AS DateTime), 0x1587053FF8FE8AF00E7646488CF12987)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (18, N'1', N'2', CAST(N'2024-01-04T16:55:12.800' AS DateTime), 0x79DBE2DAD4DB73827A625AE51754B233)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (18, N'huy', N'1', CAST(N'2024-01-06T11:57:17.563' AS DateTime), 0x1587053FF8FE8AF00E7646488CF12987)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (19, N'1', N'2', CAST(N'2024-01-04T16:55:12.927' AS DateTime), 0x79DBE2DAD4DB73827A625AE51754B233)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (19, N'huy', N'1', CAST(N'2024-01-06T11:57:17.707' AS DateTime), 0x1587053FF8FE8AF00E7646488CF12987)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (20, N'1', N'2', CAST(N'2024-01-04T16:55:13.063' AS DateTime), 0x79DBE2DAD4DB73827A625AE51754B233)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (20, N'huy', N'1', CAST(N'2024-01-06T11:57:17.847' AS DateTime), 0x1587053FF8FE8AF00E7646488CF12987)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (21, N'1', N'2', CAST(N'2024-01-04T16:55:13.223' AS DateTime), 0x79DBE2DAD4DB73827A625AE51754B233)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (21, N'huy', N'1', CAST(N'2024-01-06T11:57:17.973' AS DateTime), 0x1587053FF8FE8AF00E7646488CF12987)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (22, N'1', N'2', CAST(N'2024-01-04T16:55:13.370' AS DateTime), 0x79DBE2DAD4DB73827A625AE51754B233)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (22, N'huy', N'1', CAST(N'2024-01-06T11:57:18.117' AS DateTime), 0x1587053FF8FE8AF00E7646488CF12987)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (23, N'1', N'2', CAST(N'2024-01-04T16:55:13.503' AS DateTime), 0x79DBE2DAD4DB73827A625AE51754B233)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (23, N'huy', N'1', CAST(N'2024-01-06T11:57:18.257' AS DateTime), 0x1587053FF8FE8AF00E7646488CF12987)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (24, N'1', N'2', CAST(N'2024-01-04T16:55:13.653' AS DateTime), 0x79DBE2DAD4DB73827A625AE51754B233)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (24, N'huy', N'1', CAST(N'2024-01-06T11:57:18.390' AS DateTime), 0x1587053FF8FE8AF00E7646488CF12987)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (25, N'1', N'2', CAST(N'2024-01-04T16:55:13.810' AS DateTime), 0x79DBE2DAD4DB73827A625AE51754B233)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (25, N'huy', N'1', CAST(N'2024-01-06T11:57:18.527' AS DateTime), 0x1587053FF8FE8AF00E7646488CF12987)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (26, N'1', N'2', CAST(N'2024-01-04T16:55:13.960' AS DateTime), 0x79DBE2DAD4DB73827A625AE51754B233)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (27, N'1', N'2', CAST(N'2024-01-04T16:55:14.120' AS DateTime), 0x79DBE2DAD4DB73827A625AE51754B233)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (28, N'1', N'2', CAST(N'2024-01-04T16:55:14.267' AS DateTime), 0x79DBE2DAD4DB73827A625AE51754B233)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (29, N'1', N'2', CAST(N'2024-01-04T16:55:14.420' AS DateTime), 0x79DBE2DAD4DB73827A625AE51754B233)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (30, N'1', N'2', CAST(N'2024-01-04T16:55:14.570' AS DateTime), 0x79DBE2DAD4DB73827A625AE51754B233)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (31, N'1', N'2', CAST(N'2024-01-04T16:55:14.737' AS DateTime), 0x79DBE2DAD4DB73827A625AE51754B233)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (32, N'2', N'1', CAST(N'2024-01-04T16:55:18.420' AS DateTime), 0x36EDC7316D828402D35F41BE0053708D)
INSERT [dbo].[FriendChat] ([id], [sender], [friend], [sent_date], [msg]) VALUES (33, N'1', N'2', CAST(N'2024-01-04T16:55:38.017' AS DateTime), 0xBC2FC6E7302ED0C2F5FED5A874ABF7E2)
GO
INSERT [dbo].[GroupChat] ([id], [name], [date]) VALUES (N'aHV5U2F0IEphbiAwNiAxMToxNzoyMiBJQ1QgMjAyNA==', N'huy', CAST(N'2024-01-06T11:17:22.180' AS DateTime))
INSERT [dbo].[GroupChat] ([id], [name], [date]) VALUES (N'aHV5U2F0IEphbiAwNiAxNzo0NTo0MyBJQ1QgMjAyNA==', N'huy', CAST(N'2024-01-06T17:45:43.630' AS DateTime))
INSERT [dbo].[GroupChat] ([id], [name], [date]) VALUES (N'dGFvR3JvdXBTYXQgSmFuIDA2IDEyOjEyOjM5IElDVCAyMDI0', N'', CAST(N'2024-01-06T12:12:39.917' AS DateTime))
INSERT [dbo].[GroupChat] ([id], [name], [date]) VALUES (N'MTEyMlNhdCBKYW4gMDYgMTI6Mjk6NTAgSUNUIDIwMjQ=', N'1122', CAST(N'2024-01-06T12:29:50.147' AS DateTime))
INSERT [dbo].[GroupChat] ([id], [name], [date]) VALUES (N'MTIzU2F0IEphbiAwNiAxMjoxMDozMiBJQ1QgMjAyNA==', N'123', CAST(N'2024-01-06T12:10:32.037' AS DateTime))
INSERT [dbo].[GroupChat] ([id], [name], [date]) VALUES (N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'', CAST(N'2024-01-06T12:32:08.210' AS DateTime))
INSERT [dbo].[GroupChat] ([id], [name], [date]) VALUES (N'MTIzU2F0IEphbiAwNiAxMToxNjo1NiBJQ1QgMjAyNA==', N'123', CAST(N'2024-01-06T11:16:56.363' AS DateTime))
INSERT [dbo].[GroupChat] ([id], [name], [date]) VALUES (N'MTIzU2F0IEphbiAwNiAxMToxNzoxMCBJQ1QgMjAyNA==', N'123', CAST(N'2024-01-06T11:17:10.327' AS DateTime))
INSERT [dbo].[GroupChat] ([id], [name], [date]) VALUES (N'MTIzU2F0IEphbiAwNiAxMToyNToxMCBJQ1QgMjAyNA==', N'123', CAST(N'2024-01-06T11:25:10.677' AS DateTime))
INSERT [dbo].[GroupChat] ([id], [name], [date]) VALUES (N'MTIzU2F0IEphbiAwNiAxMToyOTo1OSBJQ1QgMjAyNA==', N'123', CAST(N'2024-01-06T11:29:59.073' AS DateTime))
INSERT [dbo].[GroupChat] ([id], [name], [date]) VALUES (N'MTIzYnJvY2hhdFNhdCBKYW4gMDYgMTE6NTg6MDQgSUNUIDIwMjQ=', N'group', CAST(N'2024-01-06T11:58:04.683' AS DateTime))
INSERT [dbo].[GroupChat] ([id], [name], [date]) VALUES (N'MXRhbyBncm91cFNhdCBKYW4gMDYgMTI6MDQ6MzUgSUNUIDIwMjQ=', N'1tao group', CAST(N'2024-01-06T12:04:35.967' AS DateTime))
INSERT [dbo].[GroupChat] ([id], [name], [date]) VALUES (N'NTU1U2F0IEphbiAwNiAxMToxODowNSBJQ1QgMjAyNA==', N'555', CAST(N'2024-01-06T11:18:05.293' AS DateTime))
INSERT [dbo].[GroupChat] ([id], [name], [date]) VALUES (N'R3JvdXBUdW9uZ1NhdCBKYW4gMDYgMTY6MTk6NTkgSUNUIDIwMjQ=', N'GroupTuong', CAST(N'2024-01-06T16:19:59.397' AS DateTime))
INSERT [dbo].[GroupChat] ([id], [name], [date]) VALUES (N'Z3JvdXB0dW9uZ3Rhb1NhdCBKYW4gMDYgMTI6MDc6NDkgSUNUIDIwMjQ=', N'', CAST(N'2024-01-06T12:07:49.270' AS DateTime))
GO
INSERT [dbo].[GroupChatMember] ([group_id], [username], [joined_date], [is_admin], [start_history_msg], [last_read_msg]) VALUES (N'aHV5U2F0IEphbiAwNiAxMToxNzoyMiBJQ1QgMjAyNA==', N'1', CAST(N'2024-01-06T11:17:22.180' AS DateTime), 0, 0, 0)
INSERT [dbo].[GroupChatMember] ([group_id], [username], [joined_date], [is_admin], [start_history_msg], [last_read_msg]) VALUES (N'aHV5U2F0IEphbiAwNiAxMToxNzoyMiBJQ1QgMjAyNA==', N'2', CAST(N'2024-01-06T11:17:22.180' AS DateTime), 0, 0, 0)
INSERT [dbo].[GroupChatMember] ([group_id], [username], [joined_date], [is_admin], [start_history_msg], [last_read_msg]) VALUES (N'aHV5U2F0IEphbiAwNiAxMToxNzoyMiBJQ1QgMjAyNA==', N'huy', CAST(N'2024-01-06T11:17:22.180' AS DateTime), 1, 0, 25)
INSERT [dbo].[GroupChatMember] ([group_id], [username], [joined_date], [is_admin], [start_history_msg], [last_read_msg]) VALUES (N'aHV5U2F0IEphbiAwNiAxNzo0NTo0MyBJQ1QgMjAyNA==', N'tuong', CAST(N'2024-01-06T17:45:43.630' AS DateTime), 1, 0, 0)
INSERT [dbo].[GroupChatMember] ([group_id], [username], [joined_date], [is_admin], [start_history_msg], [last_read_msg]) VALUES (N'dGFvR3JvdXBTYXQgSmFuIDA2IDEyOjEyOjM5IElDVCAyMDI0', N'phat', CAST(N'2024-01-06T12:12:39.917' AS DateTime), 0, 0, 4)
INSERT [dbo].[GroupChatMember] ([group_id], [username], [joined_date], [is_admin], [start_history_msg], [last_read_msg]) VALUES (N'dGFvR3JvdXBTYXQgSmFuIDA2IDEyOjEyOjM5IElDVCAyMDI0', N'toan', CAST(N'2024-01-06T12:12:39.917' AS DateTime), 0, 3, 4)
INSERT [dbo].[GroupChatMember] ([group_id], [username], [joined_date], [is_admin], [start_history_msg], [last_read_msg]) VALUES (N'dGFvR3JvdXBTYXQgSmFuIDA2IDEyOjEyOjM5IElDVCAyMDI0', N'tuong', CAST(N'2024-01-06T12:12:39.917' AS DateTime), 1, 0, 4)
INSERT [dbo].[GroupChatMember] ([group_id], [username], [joined_date], [is_admin], [start_history_msg], [last_read_msg]) VALUES (N'MTEyMlNhdCBKYW4gMDYgMTI6Mjk6NTAgSUNUIDIwMjQ=', N'huy', CAST(N'2024-01-06T17:45:31.687' AS DateTime), 1, 0, 0)
INSERT [dbo].[GroupChatMember] ([group_id], [username], [joined_date], [is_admin], [start_history_msg], [last_read_msg]) VALUES (N'MTEyMlNhdCBKYW4gMDYgMTI6Mjk6NTAgSUNUIDIwMjQ=', N'phat', CAST(N'2024-01-06T17:45:32.513' AS DateTime), 0, 0, 0)
INSERT [dbo].[GroupChatMember] ([group_id], [username], [joined_date], [is_admin], [start_history_msg], [last_read_msg]) VALUES (N'MTEyMlNhdCBKYW4gMDYgMTI6Mjk6NTAgSUNUIDIwMjQ=', N'toan', CAST(N'2024-01-06T17:45:33.533' AS DateTime), 0, 0, 0)
INSERT [dbo].[GroupChatMember] ([group_id], [username], [joined_date], [is_admin], [start_history_msg], [last_read_msg]) VALUES (N'MTEyMlNhdCBKYW4gMDYgMTI6Mjk6NTAgSUNUIDIwMjQ=', N'tuong', CAST(N'2024-01-06T12:29:50.147' AS DateTime), 1, 0, 8)
INSERT [dbo].[GroupChatMember] ([group_id], [username], [joined_date], [is_admin], [start_history_msg], [last_read_msg]) VALUES (N'MTIzU2F0IEphbiAwNiAxMjoxMDozMiBJQ1QgMjAyNA==', N'phat', CAST(N'2024-01-06T12:10:32.037' AS DateTime), 0, 0, 26)
INSERT [dbo].[GroupChatMember] ([group_id], [username], [joined_date], [is_admin], [start_history_msg], [last_read_msg]) VALUES (N'MTIzU2F0IEphbiAwNiAxMjoxMDozMiBJQ1QgMjAyNA==', N'tuong', CAST(N'2024-01-06T12:10:32.037' AS DateTime), 1, 26, 26)
INSERT [dbo].[GroupChatMember] ([group_id], [username], [joined_date], [is_admin], [start_history_msg], [last_read_msg]) VALUES (N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'phat', CAST(N'2024-01-06T12:32:08.210' AS DateTime), 0, 0, 82)
INSERT [dbo].[GroupChatMember] ([group_id], [username], [joined_date], [is_admin], [start_history_msg], [last_read_msg]) VALUES (N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:32:08.210' AS DateTime), 0, 0, 82)
INSERT [dbo].[GroupChatMember] ([group_id], [username], [joined_date], [is_admin], [start_history_msg], [last_read_msg]) VALUES (N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'tuong', CAST(N'2024-01-06T12:32:08.210' AS DateTime), 1, 0, 82)
INSERT [dbo].[GroupChatMember] ([group_id], [username], [joined_date], [is_admin], [start_history_msg], [last_read_msg]) VALUES (N'MTIzU2F0IEphbiAwNiAxMToxNjo1NiBJQ1QgMjAyNA==', N'1', CAST(N'2024-01-06T11:16:56.363' AS DateTime), 0, 0, 0)
INSERT [dbo].[GroupChatMember] ([group_id], [username], [joined_date], [is_admin], [start_history_msg], [last_read_msg]) VALUES (N'MTIzU2F0IEphbiAwNiAxMToxNjo1NiBJQ1QgMjAyNA==', N'2', CAST(N'2024-01-06T11:16:56.363' AS DateTime), 0, 0, 0)
INSERT [dbo].[GroupChatMember] ([group_id], [username], [joined_date], [is_admin], [start_history_msg], [last_read_msg]) VALUES (N'MTIzU2F0IEphbiAwNiAxMToxNjo1NiBJQ1QgMjAyNA==', N'huy', CAST(N'2024-01-06T11:16:56.363' AS DateTime), 1, 0, 0)
INSERT [dbo].[GroupChatMember] ([group_id], [username], [joined_date], [is_admin], [start_history_msg], [last_read_msg]) VALUES (N'MTIzU2F0IEphbiAwNiAxMToxNzoxMCBJQ1QgMjAyNA==', N'1', CAST(N'2024-01-06T11:17:10.327' AS DateTime), 0, 0, 0)
INSERT [dbo].[GroupChatMember] ([group_id], [username], [joined_date], [is_admin], [start_history_msg], [last_read_msg]) VALUES (N'MTIzU2F0IEphbiAwNiAxMToxNzoxMCBJQ1QgMjAyNA==', N'2', CAST(N'2024-01-06T11:17:10.327' AS DateTime), 0, 0, 0)
INSERT [dbo].[GroupChatMember] ([group_id], [username], [joined_date], [is_admin], [start_history_msg], [last_read_msg]) VALUES (N'MTIzU2F0IEphbiAwNiAxMToxNzoxMCBJQ1QgMjAyNA==', N'huy', CAST(N'2024-01-06T11:17:10.327' AS DateTime), 1, 0, 0)
INSERT [dbo].[GroupChatMember] ([group_id], [username], [joined_date], [is_admin], [start_history_msg], [last_read_msg]) VALUES (N'MTIzU2F0IEphbiAwNiAxMToyNToxMCBJQ1QgMjAyNA==', N'1', CAST(N'2024-01-06T11:25:10.677' AS DateTime), 0, 0, 0)
INSERT [dbo].[GroupChatMember] ([group_id], [username], [joined_date], [is_admin], [start_history_msg], [last_read_msg]) VALUES (N'MTIzU2F0IEphbiAwNiAxMToyNToxMCBJQ1QgMjAyNA==', N'2', CAST(N'2024-01-06T11:25:10.677' AS DateTime), 0, 0, 0)
INSERT [dbo].[GroupChatMember] ([group_id], [username], [joined_date], [is_admin], [start_history_msg], [last_read_msg]) VALUES (N'MTIzU2F0IEphbiAwNiAxMToyNToxMCBJQ1QgMjAyNA==', N'huy', CAST(N'2024-01-06T11:25:10.677' AS DateTime), 1, 0, 0)
INSERT [dbo].[GroupChatMember] ([group_id], [username], [joined_date], [is_admin], [start_history_msg], [last_read_msg]) VALUES (N'MTIzU2F0IEphbiAwNiAxMToyOTo1OSBJQ1QgMjAyNA==', N'1', CAST(N'2024-01-06T11:29:59.073' AS DateTime), 1, 0, 0)
INSERT [dbo].[GroupChatMember] ([group_id], [username], [joined_date], [is_admin], [start_history_msg], [last_read_msg]) VALUES (N'MTIzU2F0IEphbiAwNiAxMToyOTo1OSBJQ1QgMjAyNA==', N'2', CAST(N'2024-01-06T11:29:59.073' AS DateTime), 0, 0, 0)
INSERT [dbo].[GroupChatMember] ([group_id], [username], [joined_date], [is_admin], [start_history_msg], [last_read_msg]) VALUES (N'MTIzU2F0IEphbiAwNiAxMToyOTo1OSBJQ1QgMjAyNA==', N'huy', CAST(N'2024-01-06T11:29:59.073' AS DateTime), 0, 0, 0)
INSERT [dbo].[GroupChatMember] ([group_id], [username], [joined_date], [is_admin], [start_history_msg], [last_read_msg]) VALUES (N'MTIzYnJvY2hhdFNhdCBKYW4gMDYgMTE6NTg6MDQgSUNUIDIwMjQ=', N'1', CAST(N'2024-01-06T11:58:04.683' AS DateTime), 1, 0, 0)
INSERT [dbo].[GroupChatMember] ([group_id], [username], [joined_date], [is_admin], [start_history_msg], [last_read_msg]) VALUES (N'MTIzYnJvY2hhdFNhdCBKYW4gMDYgMTE6NTg6MDQgSUNUIDIwMjQ=', N'2', CAST(N'2024-01-06T12:02:33.630' AS DateTime), 0, 0, 0)
INSERT [dbo].[GroupChatMember] ([group_id], [username], [joined_date], [is_admin], [start_history_msg], [last_read_msg]) VALUES (N'MTIzYnJvY2hhdFNhdCBKYW4gMDYgMTE6NTg6MDQgSUNUIDIwMjQ=', N'huy', CAST(N'2024-01-06T11:58:04.683' AS DateTime), 0, 0, 0)
INSERT [dbo].[GroupChatMember] ([group_id], [username], [joined_date], [is_admin], [start_history_msg], [last_read_msg]) VALUES (N'MXRhbyBncm91cFNhdCBKYW4gMDYgMTI6MDQ6MzUgSUNUIDIwMjQ=', N'1', CAST(N'2024-01-06T12:04:35.967' AS DateTime), 1, 0, 0)
INSERT [dbo].[GroupChatMember] ([group_id], [username], [joined_date], [is_admin], [start_history_msg], [last_read_msg]) VALUES (N'MXRhbyBncm91cFNhdCBKYW4gMDYgMTI6MDQ6MzUgSUNUIDIwMjQ=', N'3', CAST(N'2024-01-06T12:04:35.967' AS DateTime), 0, 0, 5)
INSERT [dbo].[GroupChatMember] ([group_id], [username], [joined_date], [is_admin], [start_history_msg], [last_read_msg]) VALUES (N'MXRhbyBncm91cFNhdCBKYW4gMDYgMTI6MDQ6MzUgSUNUIDIwMjQ=', N'huy', CAST(N'2024-01-06T12:04:35.967' AS DateTime), 0, 0, 5)
INSERT [dbo].[GroupChatMember] ([group_id], [username], [joined_date], [is_admin], [start_history_msg], [last_read_msg]) VALUES (N'NTU1U2F0IEphbiAwNiAxMToxODowNSBJQ1QgMjAyNA==', N'1', CAST(N'2024-01-06T11:18:05.293' AS DateTime), 0, 0, 0)
INSERT [dbo].[GroupChatMember] ([group_id], [username], [joined_date], [is_admin], [start_history_msg], [last_read_msg]) VALUES (N'NTU1U2F0IEphbiAwNiAxMToxODowNSBJQ1QgMjAyNA==', N'2', CAST(N'2024-01-06T11:18:05.293' AS DateTime), 0, 0, 0)
INSERT [dbo].[GroupChatMember] ([group_id], [username], [joined_date], [is_admin], [start_history_msg], [last_read_msg]) VALUES (N'NTU1U2F0IEphbiAwNiAxMToxODowNSBJQ1QgMjAyNA==', N'huy', CAST(N'2024-01-06T11:18:05.293' AS DateTime), 1, 0, 0)
INSERT [dbo].[GroupChatMember] ([group_id], [username], [joined_date], [is_admin], [start_history_msg], [last_read_msg]) VALUES (N'R3JvdXBUdW9uZ1NhdCBKYW4gMDYgMTY6MTk6NTkgSUNUIDIwMjQ=', N'huy', CAST(N'2024-01-06T17:12:04.160' AS DateTime), 0, 0, 0)
INSERT [dbo].[GroupChatMember] ([group_id], [username], [joined_date], [is_admin], [start_history_msg], [last_read_msg]) VALUES (N'R3JvdXBUdW9uZ1NhdCBKYW4gMDYgMTY6MTk6NTkgSUNUIDIwMjQ=', N'phat', CAST(N'2024-01-06T17:12:03.780' AS DateTime), 0, 0, 0)
INSERT [dbo].[GroupChatMember] ([group_id], [username], [joined_date], [is_admin], [start_history_msg], [last_read_msg]) VALUES (N'R3JvdXBUdW9uZ1NhdCBKYW4gMDYgMTY6MTk6NTkgSUNUIDIwMjQ=', N'tuong', CAST(N'2024-01-06T16:19:59.397' AS DateTime), 1, 0, 4)
INSERT [dbo].[GroupChatMember] ([group_id], [username], [joined_date], [is_admin], [start_history_msg], [last_read_msg]) VALUES (N'Z3JvdXB0dW9uZ3Rhb1NhdCBKYW4gMDYgMTI6MDc6NDkgSUNUIDIwMjQ=', N'phat', CAST(N'2024-01-06T14:19:01.297' AS DateTime), 0, 0, 16)
INSERT [dbo].[GroupChatMember] ([group_id], [username], [joined_date], [is_admin], [start_history_msg], [last_read_msg]) VALUES (N'Z3JvdXB0dW9uZ3Rhb1NhdCBKYW4gMDYgMTI6MDc6NDkgSUNUIDIwMjQ=', N'toan', CAST(N'2024-01-06T16:18:55.410' AS DateTime), 0, 0, 16)
INSERT [dbo].[GroupChatMember] ([group_id], [username], [joined_date], [is_admin], [start_history_msg], [last_read_msg]) VALUES (N'Z3JvdXB0dW9uZ3Rhb1NhdCBKYW4gMDYgMTI6MDc6NDkgSUNUIDIwMjQ=', N'tuong', CAST(N'2024-01-06T12:07:49.270' AS DateTime), 1, 3, 16)
GO
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (1, N'aHV5U2F0IEphbiAwNiAxMToxNzoyMiBJQ1QgMjAyNA==', N'huy', CAST(N'2024-01-06T12:17:33.910' AS DateTime), 0x5CD1855666984B087DFF0A695420152B)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (1, N'dGFvR3JvdXBTYXQgSmFuIDA2IDEyOjEyOjM5IElDVCAyMDI0', N'toan', CAST(N'2024-01-06T12:12:58.747' AS DateTime), 0xD6BCF6C2DD60D996C7C88B441F4FAC7D)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (1, N'MTEyMlNhdCBKYW4gMDYgMTI6Mjk6NTAgSUNUIDIwMjQ=', N'tuong', CAST(N'2024-01-06T14:15:44.260' AS DateTime), 0x8CA34426BECBE8734465C42AC0B4FE0A)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (1, N'MTIzU2F0IEphbiAwNiAxMjoxMDozMiBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:10:37.087' AS DateTime), 0x50E3C1B22FA92552890FCB83731A7C23)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (1, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:32:13.947' AS DateTime), 0x1C2B39E7BF850C2E0216C864A6DEDBFB)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (1, N'MXRhbyBncm91cFNhdCBKYW4gMDYgMTI6MDQ6MzUgSUNUIDIwMjQ=', N'3', CAST(N'2024-01-06T12:05:49.557' AS DateTime), 0xD5505C18F8387B7B1AD4B15573995F07)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (1, N'R3JvdXBUdW9uZ1NhdCBKYW4gMDYgMTY6MTk6NTkgSUNUIDIwMjQ=', N'phat', CAST(N'2024-01-06T16:22:19.417' AS DateTime), 0x6DA816D47B82DD657EFEBDD55A822587)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (1, N'Z3JvdXB0dW9uZ3Rhb1NhdCBKYW4gMDYgMTI6MDc6NDkgSUNUIDIwMjQ=', N'toan', CAST(N'2024-01-06T12:08:01.530' AS DateTime), 0x921168AC84BE18F60733FA10B8473616)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (2, N'aHV5U2F0IEphbiAwNiAxMToxNzoyMiBJQ1QgMjAyNA==', N'huy', CAST(N'2024-01-06T12:17:34.003' AS DateTime), 0x5CD1855666984B087DFF0A695420152B)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (2, N'dGFvR3JvdXBTYXQgSmFuIDA2IDEyOjEyOjM5IElDVCAyMDI0', N'toan', CAST(N'2024-01-06T12:13:22.907' AS DateTime), 0x6A264B9AFA3E8B33549B77292209E4B9)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (2, N'MTEyMlNhdCBKYW4gMDYgMTI6Mjk6NTAgSUNUIDIwMjQ=', N'tuong', CAST(N'2024-01-06T14:15:44.380' AS DateTime), 0x8CA34426BECBE8734465C42AC0B4FE0A)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (2, N'MTIzU2F0IEphbiAwNiAxMjoxMDozMiBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:10:43.457' AS DateTime), 0x641D1EFEAEC844F0CC0A229217A6B96B)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (2, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:32:17.727' AS DateTime), 0x1C2B39E7BF850C2E0216C864A6DEDBFB)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (2, N'MXRhbyBncm91cFNhdCBKYW4gMDYgMTI6MDQ6MzUgSUNUIDIwMjQ=', N'3', CAST(N'2024-01-06T12:05:55.910' AS DateTime), 0xD5505C18F8387B7B1AD4B15573995F07)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (2, N'R3JvdXBUdW9uZ1NhdCBKYW4gMDYgMTY6MTk6NTkgSUNUIDIwMjQ=', N'tuong', CAST(N'2024-01-06T16:22:32.297' AS DateTime), 0x5AE5714B1175425AD820BD850C38FACD)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (2, N'Z3JvdXB0dW9uZ3Rhb1NhdCBKYW4gMDYgMTI6MDc6NDkgSUNUIDIwMjQ=', N'toan', CAST(N'2024-01-06T12:08:14.473' AS DateTime), 0x0E0F42312656EA133B0EA41F875CD0E3)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (3, N'aHV5U2F0IEphbiAwNiAxMToxNzoyMiBJQ1QgMjAyNA==', N'huy', CAST(N'2024-01-06T12:17:34.127' AS DateTime), 0x5CD1855666984B087DFF0A695420152B)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (3, N'dGFvR3JvdXBTYXQgSmFuIDA2IDEyOjEyOjM5IElDVCAyMDI0', N'toan', CAST(N'2024-01-06T12:13:27.863' AS DateTime), 0x812EF29173597E1155B1CFA94E189CD0)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (3, N'MTEyMlNhdCBKYW4gMDYgMTI6Mjk6NTAgSUNUIDIwMjQ=', N'tuong', CAST(N'2024-01-06T14:15:44.507' AS DateTime), 0x8CA34426BECBE8734465C42AC0B4FE0A)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (3, N'MTIzU2F0IEphbiAwNiAxMjoxMDozMiBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:11:33.260' AS DateTime), 0xB3CC9CC8DA3A0B0496D667F86D0FCACC)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (3, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:32:19.247' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (3, N'MXRhbyBncm91cFNhdCBKYW4gMDYgMTI6MDQ6MzUgSUNUIDIwMjQ=', N'3', CAST(N'2024-01-06T12:05:57.563' AS DateTime), 0x7D6E753E73D35CED335B7F827B8C9BA5)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (3, N'R3JvdXBUdW9uZ1NhdCBKYW4gMDYgMTY6MTk6NTkgSUNUIDIwMjQ=', N'tuong', CAST(N'2024-01-06T16:22:33.500' AS DateTime), 0x167D773F3FFF2BB77951B17C9BC9877D)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (3, N'Z3JvdXB0dW9uZ3Rhb1NhdCBKYW4gMDYgMTI6MDc6NDkgSUNUIDIwMjQ=', N'phat', CAST(N'2024-01-06T12:09:27.290' AS DateTime), 0x431CD290390D76B1802ED2AE159ED648)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (4, N'aHV5U2F0IEphbiAwNiAxMToxNzoyMiBJQ1QgMjAyNA==', N'huy', CAST(N'2024-01-06T12:17:34.250' AS DateTime), 0x5CD1855666984B087DFF0A695420152B)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (4, N'dGFvR3JvdXBTYXQgSmFuIDA2IDEyOjEyOjM5IElDVCAyMDI0', N'tuong', CAST(N'2024-01-06T12:29:06.830' AS DateTime), 0x6808357908BFB2AA0E1DE5EB593F1DF4)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (4, N'MTEyMlNhdCBKYW4gMDYgMTI6Mjk6NTAgSUNUIDIwMjQ=', N'tuong', CAST(N'2024-01-06T14:15:44.657' AS DateTime), 0x8CA34426BECBE8734465C42AC0B4FE0A)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (4, N'MTIzU2F0IEphbiAwNiAxMjoxMDozMiBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:11:33.383' AS DateTime), 0xB3CC9CC8DA3A0B0496D667F86D0FCACC)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (4, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:32:19.393' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (4, N'MXRhbyBncm91cFNhdCBKYW4gMDYgMTI6MDQ6MzUgSUNUIDIwMjQ=', N'3', CAST(N'2024-01-06T12:06:06.710' AS DateTime), 0xD84D0738FADD733AEB485E4CC7E141DE)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (4, N'R3JvdXBUdW9uZ1NhdCBKYW4gMDYgMTY6MTk6NTkgSUNUIDIwMjQ=', N'tuong', CAST(N'2024-01-06T16:22:34.927' AS DateTime), 0x774554DB14C61F66FCB9FC6EF4920DEF)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (4, N'Z3JvdXB0dW9uZ3Rhb1NhdCBKYW4gMDYgMTI6MDc6NDkgSUNUIDIwMjQ=', N'toan', CAST(N'2024-01-06T12:09:48.583' AS DateTime), 0x431CD290390D76B1802ED2AE159ED648)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (5, N'aHV5U2F0IEphbiAwNiAxMToxNzoyMiBJQ1QgMjAyNA==', N'huy', CAST(N'2024-01-06T12:17:34.363' AS DateTime), 0x5CD1855666984B087DFF0A695420152B)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (5, N'MTEyMlNhdCBKYW4gMDYgMTI6Mjk6NTAgSUNUIDIwMjQ=', N'tuong', CAST(N'2024-01-06T14:15:44.773' AS DateTime), 0x8CA34426BECBE8734465C42AC0B4FE0A)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (5, N'MTIzU2F0IEphbiAwNiAxMjoxMDozMiBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:11:33.653' AS DateTime), 0xB3CC9CC8DA3A0B0496D667F86D0FCACC)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (5, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:32:19.527' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (5, N'MXRhbyBncm91cFNhdCBKYW4gMDYgMTI6MDQ6MzUgSUNUIDIwMjQ=', N'3', CAST(N'2024-01-06T12:06:09.217' AS DateTime), 0xD84D0738FADD733AEB485E4CC7E141DE)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (5, N'Z3JvdXB0dW9uZ3Rhb1NhdCBKYW4gMDYgMTI6MDc6NDkgSUNUIDIwMjQ=', N'phat', CAST(N'2024-01-06T14:18:17.527' AS DateTime), 0x7D3053570456E05F621F62BCF4E6AE20)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (6, N'aHV5U2F0IEphbiAwNiAxMToxNzoyMiBJQ1QgMjAyNA==', N'huy', CAST(N'2024-01-06T12:17:34.490' AS DateTime), 0x5CD1855666984B087DFF0A695420152B)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (6, N'MTEyMlNhdCBKYW4gMDYgMTI6Mjk6NTAgSUNUIDIwMjQ=', N'tuong', CAST(N'2024-01-06T14:15:44.913' AS DateTime), 0x8CA34426BECBE8734465C42AC0B4FE0A)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (6, N'MTIzU2F0IEphbiAwNiAxMjoxMDozMiBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:11:33.787' AS DateTime), 0xB3CC9CC8DA3A0B0496D667F86D0FCACC)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (6, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:32:19.670' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (6, N'Z3JvdXB0dW9uZ3Rhb1NhdCBKYW4gMDYgMTI6MDc6NDkgSUNUIDIwMjQ=', N'phat', CAST(N'2024-01-06T14:19:15.987' AS DateTime), 0xC1F85B565003E79AC61B9F3EBB2A465F)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (7, N'aHV5U2F0IEphbiAwNiAxMToxNzoyMiBJQ1QgMjAyNA==', N'huy', CAST(N'2024-01-06T12:17:34.603' AS DateTime), 0x5CD1855666984B087DFF0A695420152B)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (7, N'MTEyMlNhdCBKYW4gMDYgMTI6Mjk6NTAgSUNUIDIwMjQ=', N'tuong', CAST(N'2024-01-06T14:15:45.040' AS DateTime), 0x8CA34426BECBE8734465C42AC0B4FE0A)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (7, N'MTIzU2F0IEphbiAwNiAxMjoxMDozMiBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:17:01.943' AS DateTime), 0xB3CC9CC8DA3A0B0496D667F86D0FCACC)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (7, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:32:19.797' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (7, N'Z3JvdXB0dW9uZ3Rhb1NhdCBKYW4gMDYgMTI6MDc6NDkgSUNUIDIwMjQ=', N'phat', CAST(N'2024-01-06T14:19:16.120' AS DateTime), 0xC1F85B565003E79AC61B9F3EBB2A465F)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (8, N'aHV5U2F0IEphbiAwNiAxMToxNzoyMiBJQ1QgMjAyNA==', N'huy', CAST(N'2024-01-06T12:17:34.727' AS DateTime), 0x5CD1855666984B087DFF0A695420152B)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (8, N'MTEyMlNhdCBKYW4gMDYgMTI6Mjk6NTAgSUNUIDIwMjQ=', N'tuong', CAST(N'2024-01-06T16:38:15.830' AS DateTime), 0x04E22C59F90B9DEBFCF6B44B5AD90CA9)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (8, N'MTIzU2F0IEphbiAwNiAxMjoxMDozMiBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:17:02.073' AS DateTime), 0xB3CC9CC8DA3A0B0496D667F86D0FCACC)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (8, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:32:19.933' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (8, N'Z3JvdXB0dW9uZ3Rhb1NhdCBKYW4gMDYgMTI6MDc6NDkgSUNUIDIwMjQ=', N'phat', CAST(N'2024-01-06T14:19:16.270' AS DateTime), 0xC1F85B565003E79AC61B9F3EBB2A465F)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (9, N'aHV5U2F0IEphbiAwNiAxMToxNzoyMiBJQ1QgMjAyNA==', N'huy', CAST(N'2024-01-06T12:17:34.837' AS DateTime), 0x5CD1855666984B087DFF0A695420152B)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (9, N'MTIzU2F0IEphbiAwNiAxMjoxMDozMiBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:17:02.217' AS DateTime), 0xB3CC9CC8DA3A0B0496D667F86D0FCACC)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (9, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:32:20.053' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (9, N'Z3JvdXB0dW9uZ3Rhb1NhdCBKYW4gMDYgMTI6MDc6NDkgSUNUIDIwMjQ=', N'phat', CAST(N'2024-01-06T14:19:16.403' AS DateTime), 0xC1F85B565003E79AC61B9F3EBB2A465F)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (10, N'aHV5U2F0IEphbiAwNiAxMToxNzoyMiBJQ1QgMjAyNA==', N'huy', CAST(N'2024-01-06T12:17:34.953' AS DateTime), 0x5CD1855666984B087DFF0A695420152B)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (10, N'MTIzU2F0IEphbiAwNiAxMjoxMDozMiBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:17:02.373' AS DateTime), 0xB3CC9CC8DA3A0B0496D667F86D0FCACC)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (10, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:17.860' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (10, N'Z3JvdXB0dW9uZ3Rhb1NhdCBKYW4gMDYgMTI6MDc6NDkgSUNUIDIwMjQ=', N'phat', CAST(N'2024-01-06T14:19:16.550' AS DateTime), 0xC1F85B565003E79AC61B9F3EBB2A465F)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (11, N'aHV5U2F0IEphbiAwNiAxMToxNzoyMiBJQ1QgMjAyNA==', N'huy', CAST(N'2024-01-06T12:17:35.077' AS DateTime), 0x5CD1855666984B087DFF0A695420152B)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (11, N'MTIzU2F0IEphbiAwNiAxMjoxMDozMiBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:17:02.493' AS DateTime), 0xB3CC9CC8DA3A0B0496D667F86D0FCACC)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (11, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:17.987' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (11, N'Z3JvdXB0dW9uZ3Rhb1NhdCBKYW4gMDYgMTI6MDc6NDkgSUNUIDIwMjQ=', N'phat', CAST(N'2024-01-06T14:19:16.667' AS DateTime), 0xC1F85B565003E79AC61B9F3EBB2A465F)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (12, N'aHV5U2F0IEphbiAwNiAxMToxNzoyMiBJQ1QgMjAyNA==', N'huy', CAST(N'2024-01-06T12:17:35.193' AS DateTime), 0x5CD1855666984B087DFF0A695420152B)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (12, N'MTIzU2F0IEphbiAwNiAxMjoxMDozMiBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:17:02.623' AS DateTime), 0xB3CC9CC8DA3A0B0496D667F86D0FCACC)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (12, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:18.113' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (12, N'Z3JvdXB0dW9uZ3Rhb1NhdCBKYW4gMDYgMTI6MDc6NDkgSUNUIDIwMjQ=', N'phat', CAST(N'2024-01-06T14:19:16.800' AS DateTime), 0xC1F85B565003E79AC61B9F3EBB2A465F)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (13, N'aHV5U2F0IEphbiAwNiAxMToxNzoyMiBJQ1QgMjAyNA==', N'huy', CAST(N'2024-01-06T12:17:35.307' AS DateTime), 0x5CD1855666984B087DFF0A695420152B)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (13, N'MTIzU2F0IEphbiAwNiAxMjoxMDozMiBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:17:02.740' AS DateTime), 0xB3CC9CC8DA3A0B0496D667F86D0FCACC)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (13, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:18.230' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (13, N'Z3JvdXB0dW9uZ3Rhb1NhdCBKYW4gMDYgMTI6MDc6NDkgSUNUIDIwMjQ=', N'phat', CAST(N'2024-01-06T14:19:16.963' AS DateTime), 0xC1F85B565003E79AC61B9F3EBB2A465F)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (14, N'aHV5U2F0IEphbiAwNiAxMToxNzoyMiBJQ1QgMjAyNA==', N'huy', CAST(N'2024-01-06T12:17:35.420' AS DateTime), 0x5CD1855666984B087DFF0A695420152B)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (14, N'MTIzU2F0IEphbiAwNiAxMjoxMDozMiBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:17:02.883' AS DateTime), 0xB3CC9CC8DA3A0B0496D667F86D0FCACC)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (14, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:18.350' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (14, N'Z3JvdXB0dW9uZ3Rhb1NhdCBKYW4gMDYgMTI6MDc6NDkgSUNUIDIwMjQ=', N'phat', CAST(N'2024-01-06T14:19:17.103' AS DateTime), 0xC1F85B565003E79AC61B9F3EBB2A465F)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (15, N'aHV5U2F0IEphbiAwNiAxMToxNzoyMiBJQ1QgMjAyNA==', N'huy', CAST(N'2024-01-06T12:17:35.547' AS DateTime), 0x5CD1855666984B087DFF0A695420152B)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (15, N'MTIzU2F0IEphbiAwNiAxMjoxMDozMiBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:17:03.003' AS DateTime), 0xB3CC9CC8DA3A0B0496D667F86D0FCACC)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (15, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:18.473' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (15, N'Z3JvdXB0dW9uZ3Rhb1NhdCBKYW4gMDYgMTI6MDc6NDkgSUNUIDIwMjQ=', N'phat', CAST(N'2024-01-06T14:19:17.237' AS DateTime), 0xC1F85B565003E79AC61B9F3EBB2A465F)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (16, N'aHV5U2F0IEphbiAwNiAxMToxNzoyMiBJQ1QgMjAyNA==', N'huy', CAST(N'2024-01-06T12:17:35.677' AS DateTime), 0x5CD1855666984B087DFF0A695420152B)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (16, N'MTIzU2F0IEphbiAwNiAxMjoxMDozMiBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:17:03.130' AS DateTime), 0xB3CC9CC8DA3A0B0496D667F86D0FCACC)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (16, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:19.407' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (16, N'Z3JvdXB0dW9uZ3Rhb1NhdCBKYW4gMDYgMTI6MDc6NDkgSUNUIDIwMjQ=', N'phat', CAST(N'2024-01-06T14:19:17.380' AS DateTime), 0xC1F85B565003E79AC61B9F3EBB2A465F)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (17, N'aHV5U2F0IEphbiAwNiAxMToxNzoyMiBJQ1QgMjAyNA==', N'huy', CAST(N'2024-01-06T12:17:35.800' AS DateTime), 0x5CD1855666984B087DFF0A695420152B)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (17, N'MTIzU2F0IEphbiAwNiAxMjoxMDozMiBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:17:03.253' AS DateTime), 0xB3CC9CC8DA3A0B0496D667F86D0FCACC)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (17, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:19.543' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (18, N'aHV5U2F0IEphbiAwNiAxMToxNzoyMiBJQ1QgMjAyNA==', N'huy', CAST(N'2024-01-06T12:17:35.917' AS DateTime), 0x5CD1855666984B087DFF0A695420152B)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (18, N'MTIzU2F0IEphbiAwNiAxMjoxMDozMiBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:17:03.380' AS DateTime), 0xB3CC9CC8DA3A0B0496D667F86D0FCACC)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (18, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:19.660' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (19, N'aHV5U2F0IEphbiAwNiAxMToxNzoyMiBJQ1QgMjAyNA==', N'huy', CAST(N'2024-01-06T12:17:36.050' AS DateTime), 0x5CD1855666984B087DFF0A695420152B)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (19, N'MTIzU2F0IEphbiAwNiAxMjoxMDozMiBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:17:03.513' AS DateTime), 0xB3CC9CC8DA3A0B0496D667F86D0FCACC)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (19, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:19.790' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (20, N'aHV5U2F0IEphbiAwNiAxMToxNzoyMiBJQ1QgMjAyNA==', N'huy', CAST(N'2024-01-06T12:17:36.170' AS DateTime), 0x5CD1855666984B087DFF0A695420152B)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (20, N'MTIzU2F0IEphbiAwNiAxMjoxMDozMiBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:17:03.643' AS DateTime), 0xB3CC9CC8DA3A0B0496D667F86D0FCACC)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (20, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:19.913' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (21, N'aHV5U2F0IEphbiAwNiAxMToxNzoyMiBJQ1QgMjAyNA==', N'huy', CAST(N'2024-01-06T12:17:36.303' AS DateTime), 0x5CD1855666984B087DFF0A695420152B)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (21, N'MTIzU2F0IEphbiAwNiAxMjoxMDozMiBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:17:04.510' AS DateTime), 0xB3CC9CC8DA3A0B0496D667F86D0FCACC)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (21, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:20.023' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
GO
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (22, N'aHV5U2F0IEphbiAwNiAxMToxNzoyMiBJQ1QgMjAyNA==', N'huy', CAST(N'2024-01-06T12:17:36.430' AS DateTime), 0x5CD1855666984B087DFF0A695420152B)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (22, N'MTIzU2F0IEphbiAwNiAxMjoxMDozMiBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:17:04.647' AS DateTime), 0xB3CC9CC8DA3A0B0496D667F86D0FCACC)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (22, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:20.143' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (23, N'aHV5U2F0IEphbiAwNiAxMToxNzoyMiBJQ1QgMjAyNA==', N'huy', CAST(N'2024-01-06T12:17:36.557' AS DateTime), 0x5CD1855666984B087DFF0A695420152B)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (23, N'MTIzU2F0IEphbiAwNiAxMjoxMDozMiBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:17:04.767' AS DateTime), 0xB3CC9CC8DA3A0B0496D667F86D0FCACC)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (23, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:20.263' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (24, N'aHV5U2F0IEphbiAwNiAxMToxNzoyMiBJQ1QgMjAyNA==', N'huy', CAST(N'2024-01-06T12:17:36.687' AS DateTime), 0x5CD1855666984B087DFF0A695420152B)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (24, N'MTIzU2F0IEphbiAwNiAxMjoxMDozMiBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:17:04.893' AS DateTime), 0xB3CC9CC8DA3A0B0496D667F86D0FCACC)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (24, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:20.400' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (25, N'aHV5U2F0IEphbiAwNiAxMToxNzoyMiBJQ1QgMjAyNA==', N'huy', CAST(N'2024-01-06T12:17:36.833' AS DateTime), 0x5CD1855666984B087DFF0A695420152B)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (25, N'MTIzU2F0IEphbiAwNiAxMjoxMDozMiBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:17:05.033' AS DateTime), 0xB3CC9CC8DA3A0B0496D667F86D0FCACC)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (25, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:20.510' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (26, N'MTIzU2F0IEphbiAwNiAxMjoxMDozMiBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:17:05.173' AS DateTime), 0xB3CC9CC8DA3A0B0496D667F86D0FCACC)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (26, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:20.633' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (27, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:20.763' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (28, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:20.887' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (29, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:21.007' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (30, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:21.130' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (31, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:21.273' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (32, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:21.397' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (33, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:21.520' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (34, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:21.657' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (35, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:21.787' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (36, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:21.917' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (37, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:22.073' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (38, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:22.217' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (39, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:22.337' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (40, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:22.497' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (41, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:23.583' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (42, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:23.737' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (43, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:23.867' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (44, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:23.997' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (45, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:24.117' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (46, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:24.240' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (47, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:24.367' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (48, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:24.497' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (49, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:24.627' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (50, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:24.757' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (51, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:24.873' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (52, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:24.993' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (53, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:25.117' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (54, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:25.240' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (55, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:25.363' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (56, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:25.500' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (57, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:25.630' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (58, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:25.760' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (59, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:25.893' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (60, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:26.023' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (61, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:26.160' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (62, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:26.297' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (63, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:26.417' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (64, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:26.553' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (65, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:26.673' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (66, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:26.807' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (67, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:26.947' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (68, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:27.087' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (69, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:27.217' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (70, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'toan', CAST(N'2024-01-06T12:35:27.347' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (71, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'tuong', CAST(N'2024-01-06T16:17:38.117' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (72, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'tuong', CAST(N'2024-01-06T16:17:38.243' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (73, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'tuong', CAST(N'2024-01-06T16:17:38.377' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (74, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'tuong', CAST(N'2024-01-06T16:17:38.497' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (75, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'tuong', CAST(N'2024-01-06T16:17:38.623' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (76, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'tuong', CAST(N'2024-01-06T16:17:38.743' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (77, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'tuong', CAST(N'2024-01-06T16:17:38.870' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (78, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'tuong', CAST(N'2024-01-06T16:17:38.997' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (79, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'tuong', CAST(N'2024-01-06T16:17:39.133' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (80, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'tuong', CAST(N'2024-01-06T16:17:39.263' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (81, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'tuong', CAST(N'2024-01-06T16:17:39.397' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
INSERT [dbo].[GroupChatMessage] ([id], [group_id], [sender], [sent_date], [cipher_msg]) VALUES (82, N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', N'tuong', CAST(N'2024-01-06T16:17:42.543' AS DateTime), 0x56261EF61275AA0D800FA5DA793BA504)
GO
INSERT [dbo].[GroupChatSecret] ([username], [target], [aes_params]) VALUES (N'1', N'aHV5U2F0IEphbiAwNiAxMToxNzoyMiBJQ1QgMjAyNA==', 0x1102739B3582EF8CA1508D56EBF913414352C4638E236B018C798AF1A561B8127A79DB3510336851DED8AC7E260B667F37198F1DB4E18040060FEA9F58A8A5ABE195258E980D3F6D471554D5AD3DB7F96737E738C3AD547386CDF644710270B3C7360305435A8B87081C63078F1AC5CEA2142BDCC4111F5DDDCB25A63B3FA6DAA82A08308B5811F389DF5F8CD70A38F9BAD19254D714B54CE6B0514BBB322004EB37ED0D4B6C96927051D4DD5D743AE3C130F9A3BED8834D593EB708C634EED9F459C31A41541CDB656A06F0E4894CF970BC929F5937F8F6E71FF2CFDB16984CCC13B241BC7C3554D20D29F57C67D088D7BFE2A9D98D52E4ECCD86813FE3016D)
INSERT [dbo].[GroupChatSecret] ([username], [target], [aes_params]) VALUES (N'1', N'MTIzU2F0IEphbiAwNiAxMToxNjo1NiBJQ1QgMjAyNA==', 0x5939C1C6EAEF510953C310DD6971F82715CE85AC5592204B2AE2FBC4E9ED536DECBDD4186F4D8CEDAA9184B607BFA3AC75FA2F3359E6089099CC8854D6FF6F070DFC1E29592283C68358AC9A1E94F3F88999BEFCA0DEBDD1B507BA37AF5F908BB43487C098B5A47871DE4325C47F7FB4794E15B2C03CEE017548543A6AEB5078AC63DC180061F09D4C18859DE2FCC0F4313F743115AD3F7C9FF96AD1EF7B690A56E78C116F8B9B2AF03D6764F75A27D5DDDE856D3B2869C528CFBEA5656D10394484519731F7FFEE5E72FD6BEB3C7FC5C2AA8F46630B49A6F7C72035AD6180444AF36314DE7A10F819E88E3B7D7F56DEE5E4532AD1D1EBEB3F75F29A9C2BCC73)
INSERT [dbo].[GroupChatSecret] ([username], [target], [aes_params]) VALUES (N'1', N'MTIzU2F0IEphbiAwNiAxMToxNzoxMCBJQ1QgMjAyNA==', 0x3A2518AF4CBF39FD9D71AD5B8FA9259217683AF0AF8DD9819A4780CB4612DEE179EA9EA235A58FB888276657B070E8DBBF00117FA0CFF3D68775A8043779BAB5C86EE63E6E32399F756E66B58BCAB4E8B22EF85D518B9DF99033412EA24789E0C53116D0FD33ACC82C80EA54C9B8FC55AD536075B0893D138497FC353AB1879669CFDF7BD5E103E443B85CEED52961B26BA23ED0896DD750817A8516E8949ED2205534F6F9227A2D1110784D068923CC0E11ADD2CC4510136ADB7CDF6CCE7177879A9113E506D724F259176E5A16712C50CA05E84753F7E9F97FFAD02FE209C1B1FA009499DEBC9E0A6B0ACF4DB881B63972823CA4908051FB37EBF00C261EE6)
INSERT [dbo].[GroupChatSecret] ([username], [target], [aes_params]) VALUES (N'1', N'MTIzU2F0IEphbiAwNiAxMToyNToxMCBJQ1QgMjAyNA==', 0xAC4FD57BC976FFB9F080AD99EA606263DCAFFDB35D93027E891452CE10EE4CFA270E481D34EE3643EBCE983AE91B02147D691DE1C80B6ADA4A9105DF9CAA2AC40CA05400E6DCA824E6AA0DE3D6DB8DECB129244B41A8F2D01B7926E98531E642E8B97B77C7166ADBB44116D6A91A85D0646361A23CE413CDE8B9A49DDFB9CE0AF160D1222241DFA0B254AAEA6A05854D618926E6739CCB35576ABC67A259CD2814410108F5DD30620C68ED026B8DCC38AAD8E32887D602595A4FB49FE8FF92A46B2C879DCF796D889CD08CB8019438232D19C43D9FCA4A464EFB1C08D9396BB83D9113B7D083CBB5C3FD2B07EB558CF1A661E16E8318D8D3BAAB5A32C8D0B524)
INSERT [dbo].[GroupChatSecret] ([username], [target], [aes_params]) VALUES (N'1', N'MTIzU2F0IEphbiAwNiAxMToyOTo1OSBJQ1QgMjAyNA==', 0x9F5A00DD578671A5EABF8684DE72062B2BBB4A27F5369318E264C645EB6FEC32562608648F0EEC35946AC66C827A4A22307B1690237E5859A3C06C53E53B7C1F788703089EF2CF21861607E84696CCA8280C99B32ACF0A0EAAC758BE312AB7539B821C9FECD7777077CC713D2E17945AB1DDDEDE796C258E9F4C192FDCB6AD42F6D2FBDB38FC5FF96BFAE88FD083F9C234EDE82FAE3759C3F3975AB2089EAD492D562905BBD256C5E2610885E42BAB5A68F9A347A896F00897EAFBC0A6BC06D02BFA77E00A593C2E4D74B5ACAC1219AFA9DCCD80CBAE3EBDC302BB43A601D8A0BF61B420E4C34A96F6E22B13E04E3A65DD3A89B8F2F14EBE590AB08467E31746)
INSERT [dbo].[GroupChatSecret] ([username], [target], [aes_params]) VALUES (N'1', N'MTIzYnJvY2hhdFNhdCBKYW4gMDYgMTE6NTg6MDQgSUNUIDIwMjQ=', 0xCD8A8E6BE32466D4ED637BD849523A3CB50C2108E0BC70BC19C45B96809091C9311473F6BC344F96CFE3CBC61AC760F0D6807CC681A401400553FCF3DA317FDD5398AB09CCF1DA3697A05E01F66D89E3D3892849104DDA0156CF200DD7228C1B11F5151D58269CE83DF6764931AB60A0E040BD5A2EF45C4D2B33EEAE4E4820F8ECFEFEA5BCC1130D93B76263D68383E2A7C8BDEC022B12C852A5FE929BE9AA8609C033D36B0D3C227C6BDF56AE2DBC7644743CE3C30A3DE6A840DBF8D74A0D30375DADC961521C69A28E40C79054EF7A6EB4C7EBF39CD345D435609031634AA37F6D1CA59A47485C627AB2E930181318F32684B8EC710D86B01AE550F32033BA)
INSERT [dbo].[GroupChatSecret] ([username], [target], [aes_params]) VALUES (N'1', N'MXRhbyBncm91cFNhdCBKYW4gMDYgMTI6MDQ6MzUgSUNUIDIwMjQ=', 0x9696D782E8CE4A4929FE1CD8717C0CDF63B4E3813464538B596F0B0D5D55F514D3B94BC75086D402E2E2CE6FA6A2477D5A27A332921376A79F53B762545B79DCDB4872D4F2C13653DBE8438D77BA8077115B2D29DE388A7D82E53925082B5B64DE7EBA309E5FA515602AE0F998C3BB602368F15251E305EC456FA6CCC0C847D9FF2101A88E5521F15D3EFA6045925CDB076059C270A2CD9B6B4448B511E0743BD5E6903B46126939171C72E673F4862935C2EB7D6214827CE9024D46A1C2DB136511E9A19D270C282FF6D3A725740EE6AC766AC4C1D3D90FACF7D7DD7D0E6C933ABF824B5B1DC9CA87CFE291E14D30A4772E68EBE2ECA0ACC689A24DE46A605E)
INSERT [dbo].[GroupChatSecret] ([username], [target], [aes_params]) VALUES (N'1', N'NTU1U2F0IEphbiAwNiAxMToxODowNSBJQ1QgMjAyNA==', 0x32B4C7B844C17BB5AD920005F0E001DA3A9169DDB49D20F3FC0B8B9C2F7F60893EDAC90E865A72A90FB5A22A5431ECB797755D55FD60769BF2F5CF0986C09DC999BF6AFF6B2A74E519CF66DF87C0BEBF1F6FEA0FEE7CE477BF9AEE7DE6769D07ABDA16A66983231D18F0607856C879A9DA1056012ADBF5114B0D064AF197808B2788CE6F2075BAD37B633B383191778BF647844AF09C96619430D4144035B53AE61C2DDB5292EEC3B86116D0302E3BD0538094D9E705A29F3D0943CE94D77F51B5C8BA1B219624A673E12EB8008CA2E4885351D068A0B2324E44F64D598610D713A961FE5B732A0845837C346643F3976EB5BE72DC5099F0EEED4EFDCCEA1D46)
INSERT [dbo].[GroupChatSecret] ([username], [target], [aes_params]) VALUES (N'2', N'aHV5U2F0IEphbiAwNiAxMToxNzoyMiBJQ1QgMjAyNA==', 0x853DA47FB643AC72A92CCB59464A7864B0AD388FDAC620900E7C8BFEDD8F4162F2874416E5A6FA87B579591FB6EAA6F74ABEE4D74C02BE9A31EC33C985A611E3ABCB2AF95414FCD8F79A0E39E3CED67435AE057BF3DE9F70E9B8CCEDE196B70A3796D17BB629CB42D1B2478AD2E72A304FC48F99BFF2765C28610D64F5B0390876B423ABFE688264255FC91BD7193769A6CBCE5930B50CD059775783DB4989506C1E39118C30E04EE45DA6C4A3840BE6FEED30B34B3697DD0CA05575270BC53ED4FA5657800EA0E12730AC078DA16688A8A9983F1D4EB82DEB9A34781D156C6F737B64D6849FDC821433EFA3915924031A34548E023D9F92323E02E8BAA9F2D5)
INSERT [dbo].[GroupChatSecret] ([username], [target], [aes_params]) VALUES (N'2', N'MTIzU2F0IEphbiAwNiAxMToxNjo1NiBJQ1QgMjAyNA==', 0x2BD89D1AC5786682B8BDFF338BE9BBC07B9E2FEF2009BDA9F5F0A4D28D645655B7B2FB62D4B72D5DD74E402CB724879DECAA5933BDC854D45C5271F19F1397DC523BDB2441EEB647D8936125D1A2674A63E663B4304F11CDDDFC421655B999533FB766A4957747ECFFF3BB8B0EB8CA7F6AF2CDD0A01947F982FCBA52CC6CEF5D4FECB29ED9F135DFD231E4B07B7D6A2ACBC7CB7043DDA77D89AEF82E7F403C1A8AA5D852A54C30503AA1F5941B779989E7E6E985C15565A8D4695C898F1E6F94ED381FCAE3B4EEF924E95A02EBC538ECF2166498D8D4A29CBA3B6FDD7A2826F398EAE400646FD4E80ED2BBA27748978C082EC27594861EF06C766FE5FC015484)
INSERT [dbo].[GroupChatSecret] ([username], [target], [aes_params]) VALUES (N'2', N'MTIzU2F0IEphbiAwNiAxMToxNzoxMCBJQ1QgMjAyNA==', 0x04ED572132ED3917F2FE86AF849D12E9BD23A62C595A01F107597789BDC28510E255B1CB15484465C7EF26078D57E3BFCE696FB8C7AED00293FA4C61F0EAB6EC093632098130D7D2DE9EE931CABF5EE24A4BCCEFD32EF66D6306E09BD9F8C05C7F7DBD91E038925B2FFFB0791389C2287B68837903AB02D8E5C36F7D9D662757599464294B07ABC01A311F84B6DF16B17CB4DDB67051C7832AABCC8CFEC54E5CC7E896CC2DC03E3A1690752A6128B4267C95DE7109C8595BB117E76606A63D6CFB3847DF961390BD0036D3F603C99707DC000625EDF6C56EDD963F06817CFE4CAFBFC3101CC0136DFCA564835D534C9D6CCD689B00776A512657EE3EB3AE4923)
INSERT [dbo].[GroupChatSecret] ([username], [target], [aes_params]) VALUES (N'2', N'MTIzU2F0IEphbiAwNiAxMToyNToxMCBJQ1QgMjAyNA==', 0x2D811CB0E84EA708386F201DD82E9A92A3ED0E1945A2786166CBC8FA10A9CD8272C1CAF58E0E65363FE2DCE1C46D960D65BA45B3814FB6EE4427A1E2D7E99ACE0697A416E13AF46E53B507951C4E510FB8DB9034D05321D34D289049294775DA66D2FB5AD6C62494E4C25DB93C1A8B78F5C5DECAF7852F8A931AD2FEA69ECC22FEA8D4B77D7D55D53613C968CBEB2A4C7C75E95724EC84902A86AAABC11EEAD2577FC740F25C93C2A26B40F66F7899C556E3033FB7B8125090876EB1EE5D506F92526013DAA19CC4B92B9DC48B7FFD66B0DE0CB140C3C4848FA98F8B25615D27E0DB9E762D93EB1D636538B7B9307FEAA82A92B4E2800BBC7E2522D0BEC0A9AD)
INSERT [dbo].[GroupChatSecret] ([username], [target], [aes_params]) VALUES (N'2', N'MTIzU2F0IEphbiAwNiAxMToyOTo1OSBJQ1QgMjAyNA==', 0x9D2465E0BFBB446026F0F6CA8B5624B2F6A7F4F4050117F47F951AEDC72A21B9CF60567B34EC849BE95547E52D0ED33630859474AE25535EE0323B96198BBE6A8BBEA58FDB10FEEB9A5E7BD5F4C12D7EA15D625E3CC4D595D995CC96CE7DC68E56F9C1A9F0B097379D5CA462285347D66DA41BF855655E07AFD1322A8A3600A8164F1101E502B871D14315A0E3D077928F92E351857CFAF7C2456A43FBC1DC9498419EC23B85C6E2517195B69C7E4FB8EB1D0A364719030C27B7CFB8E738BB3263811A033393B352E2FB7937BBF81FAC66CD5DEE5BBCF0C7E4F963B1F2D450756B983B70B201CBE0AED6409535089EAD9EB79D8F7AC89775A3E46E0144572D0F)
INSERT [dbo].[GroupChatSecret] ([username], [target], [aes_params]) VALUES (N'2', N'MTIzYnJvY2hhdFNhdCBKYW4gMDYgMTE6NTg6MDQgSUNUIDIwMjQ=', 0xA5079313F087546BD7E9FF99D55A131167A58F79FE6F8B0C2A352F6B28D7B7A57744E4FF0067E06BC54CF4FB658740457B93B725590EC4665BA5E1D30D58053EA94FB78F46CB309D5910F343E22250C6A74AE9E6F2843D60094257E57ED09F9646605CE4B1636A280DB5174002B2A88DD9EE69432884BC70A76450F40BFB985D38BEF4AE44A6385557206746C0E8ADED60EAA4B7D0759882BB62E209AE3F361B6AB305998BDF9E04F8CDC78E434627917EFD724A0855004B945817ECA2A8C7D4861C4C2BA5BD447E4ECCA33BE527F77629A9D4B3B3E41D496989602951255BF0CEB1F9CF86275F7E4917B30B7EDE5218060E38640BDC4CDD6109EB35A17EF811)
INSERT [dbo].[GroupChatSecret] ([username], [target], [aes_params]) VALUES (N'2', N'NTU1U2F0IEphbiAwNiAxMToxODowNSBJQ1QgMjAyNA==', 0x720916C07D80E419AD2CDF33B9C0690E8BDC4C1CFB7A831FB38FD0A404C2D6E9D1CEFC716525367C7664ADD6A343DE9FAC185E682F253C590EEEB8DBCF1D98E6F0FE4BA6DC78AC58BB196E3C7503DF611BEC21B71D45958D94DC2B255F202CAB21E54B143B1AA6D1C2B1E683CDE564BF21333566E7D36614B3CA7C9ECBDF135BB484F904E00F2A766488E30868E4EF072346EDF3DFEFB3C4CF87A28B7446F7CF8A179E79FA7A03175A6E5741EFDC5C9A3B8AAA0500371360EE5434F83CD9EA8F753A24C7A09A9F09295C30E0A720781EAC7B1624AD8F51A019141FAB76E187EBA234A117EA823136C0101AD301D6C941B4C79A353AB47E0B13AE8C7963AD8A8B)
INSERT [dbo].[GroupChatSecret] ([username], [target], [aes_params]) VALUES (N'3', N'MXRhbyBncm91cFNhdCBKYW4gMDYgMTI6MDQ6MzUgSUNUIDIwMjQ=', 0x43BF3F11846E94BD09B96757C0AA54B0F3D5977B560CEFE9A9FABAB553C7FA623E99D14F26929E76E5C1CC5652C9568069447FAA18AAF3F2A5A47F53FF858CD749457863A5AB124D03F98DC9FE87E39FB8B8B4282FA89EC8A04D90EE5C2A9D11120CEA265F18772B0FDC39340C3EFABD84AB46BF734AF5472410920BC7D68D8E8825AB6D83A8CCB87F2EF79A4F18CF9099CF2EC44FA802DD7EDC0C645D945B432F16BE6181A9EEA47100A716BA6205D868B139F080129D402A216A87EFE8EC444E9E476EDB4901E0251568C00B4813A52B4E4D90C23C808EF96479EA463B790915D0B83DBB6407B4243812AC070614606BE01EADF99EE29B41C603954A927FFC)
INSERT [dbo].[GroupChatSecret] ([username], [target], [aes_params]) VALUES (N'huy', N'aHV5U2F0IEphbiAwNiAxMToxNzoyMiBJQ1QgMjAyNA==', 0x53804D393FD112A67CDE04B94886E5E3A66B5F015B3CCC8247E9BD7FDA81F8B3D8082969A0E5006DFB609712A69917B91407715A9885BC5B7B516FDEA49A1AADE63F6F1348445E6AE956614E3A30A8B4301BE23AD5DF3D7B2AC97285462428E44BE410844C773F2290F18A884107AEB5736E60170CD47FF29A85DB922C84E9BADE0B0A53E8069BE3FB2BA22828A28D5AA0A680A147BA7BA64D33B9829A2D71C41848547F6679BCD82296D68E284921AD2F4A081B82FC3BBE70C37F10E60BEA0402B291435F061AD10A82A0CAC3FA65D5A630259A659C365C2EB85EF1FC3C5D10FDCAB2AC826D1713F0437348068ADD80E36400DB192819DECE515AB37FAB2CCE)
INSERT [dbo].[GroupChatSecret] ([username], [target], [aes_params]) VALUES (N'huy', N'MTEyMlNhdCBKYW4gMDYgMTI6Mjk6NTAgSUNUIDIwMjQ=', 0x52BECC8708ED86EF9D126DF74CF1DC4C4C88A43CC58A084149E91188E4757698D360061DD82958223213070FA3EF214013569CD5A51E1D185139AFD809908603FE9201F8A44F2020308CC869FD0E1913DA092AD1CEBF9FC87F6B31508D30AA087B3FF2BB05331D6AD0874504ECFAADDE745AD6FDE9ED17311C8A6B79DA1BCB7FA80880544D8B5313AFD0F9D6DB02BC2EDC3950C2AB14E25114A7E6E5E743870D0BAD175B7B4B34320E086D969BF33BDA4D0D8FE6C8BAC01AF5D53CA3FBADA06D3673276050E660807571408EEECC36427D7F687CECC9613F8118FD83A516442C0A2BCCA6021832AD5603D451FAD095F69EB9F9367EF0A13C32194D7028B4EA18)
INSERT [dbo].[GroupChatSecret] ([username], [target], [aes_params]) VALUES (N'huy', N'MTIzU2F0IEphbiAwNiAxMToxNjo1NiBJQ1QgMjAyNA==', 0x9EAF7701648A6259C65777612B734EA86A774681A6D094FB108113EE5954D0C634C90B9ED81A38C4B80F47A6BD92CFE1E7A7111FFCDFE14973F957D817DE03AC4F19BE8856991C811748C3540028479A50113ECD599534CE304E95E3E12F2BE64F267CD2E6D6A2CC852300E79BD533ED734140A2B5722FDF1EA776A5E4752475F309ED68D17E7D7BBD9A0C9181A1D347D6813A934742715438E0947CD4576CE6759C7B04F3E34BBAFB4369DAB7F7A486B67D1D08A93AC8A8B9B59CB63CF515CB80E5961601FA561CC6D70D5C6D68A1FF3C5B432F3AE6DC7855C775960C40A568285D36AA7BC34139ACC4448294C0E585E003D4CB7C1C7E6C7369595A36A60C82)
INSERT [dbo].[GroupChatSecret] ([username], [target], [aes_params]) VALUES (N'huy', N'MTIzU2F0IEphbiAwNiAxMToxNzoxMCBJQ1QgMjAyNA==', 0xD7161AE0B33C68DF0497A7BF37F43CF60C770A83EEFE2874375420DA40A1D36D597BA0A6782BD0CA7C353D40C242190C20ED39AA71FEBFF941C579FC4BFED0369A76ED7258D823F5AB4F0E1C1E5CE41333AAF86B003F873E0AD3054141521BA04AEEA12581D8C762C8339D6A9AF9FA16B9BDD1B43853F9EFF7A52E38FB47934ED40F0BF333FA692E81FA699E5E7B2F6F8EA157EEA41F367ABB60B34B77960A91150A1CECB32E6E9CFFF3D120C3B4AD28F909F8BB369719E7294FC223A1D0C2917DD803293313D9906B7B742DF9F26992D5FE76142806E653B41784A4D35BA4902A4B33E18197D5CCFD936EC4AAE6C79982445DCCFF6AD993ABD14AD4C2F0244E)
INSERT [dbo].[GroupChatSecret] ([username], [target], [aes_params]) VALUES (N'huy', N'MTIzU2F0IEphbiAwNiAxMToyNToxMCBJQ1QgMjAyNA==', 0x475027CF966D1F9E05F092A6B439CE17124ED6770276E93B48E8F871C1E8D4E18A0A50497ED1226D7A97384539D59F566A525B2F82B55BBED812FB629E1D9F7C69BCBDF0B2E2E5427E6000D37989B6852383E9419DEF9BC1500BA1A36D5325C214436DC71BB2DDD165BE094A55982826BC24950CB1C0A4BD6DDE5107A4496E274CC21AE5F5FAEDB9A9CC042101F6CC72EF599C78716D2E7C141314151D563BCFEA49F3281475BEEE7B0AE24B48E093F73706A8B3AA0BFE926332D54811209C6FCC44B688249D107D9B534B3CEAFBFAF4C52E00BBA51E24B487BD4E87724D2A415991B7FCB4614FD328F530BB31CB31B130EA36D977365A643A147FCA9FE2C969)
INSERT [dbo].[GroupChatSecret] ([username], [target], [aes_params]) VALUES (N'huy', N'MTIzU2F0IEphbiAwNiAxMToyOTo1OSBJQ1QgMjAyNA==', 0x71E2A5CE51C8F9796FF165373E2CFA09943221F8C697728DCBC46DD8B5735973E343EE84256645C5C300CA8960D5C83661A9587DCD3464DE5671331FFBD953FA6F256598FB4C86057E1312FA0AB2FEA5D690DA1F3B5887FE622753616D15BC2887386BABDB0A5F21130B6BB5793DB8F63E90D706747D7B6BD9C85D72D164F8034B364503A1520493D381C8BAA3244B3240763F555B4421E6F94022425C61FF063365A73B11E43C3ABA0A489BFD0B7756A367A624EE050598EA41B59F2F78A56A280E8F19FBCDD24E927EADBBCE7A8E0910CDF1257567C1DD33EAB4F2652085B20BEBA07BED990EAE2EC0A9BED07CBBDD35E928E24605953EB8545CF08150C72B)
INSERT [dbo].[GroupChatSecret] ([username], [target], [aes_params]) VALUES (N'huy', N'MTIzYnJvY2hhdFNhdCBKYW4gMDYgMTE6NTg6MDQgSUNUIDIwMjQ=', 0x4961F3DA05A0A46AE2891075C92AC85CC5E9579C325E5ECD945526B09CDDF37671878222D84C2AABFF27EE6F5ED81657FE92D4850F877A058D41C74D5F0E14CD401969DD5F9CD562793950120AD452D31469938D0E0210E05917B234A23FB32F853803DF0B7ACAFB38D7A1A79C060FE2EE3DECA6414176F0BB89ABCF73A11972F85D79499AD99FA15E0CA74669A6598F240B1BD95C9B4DACD2234FCD17C6F85DB4921FDAB34061070A2CA37AC83B843294CEC3CD7DE1D32CCDA132C9D927E8B40FE8AE34FFA4EF3ABEDC60A947929D35964E82F46CFD3BFE15663A44952A6DE4C79D4B8D4346862415223225C79C2D400F3A5A54B3D928E1B6DF4FE904726C29)
INSERT [dbo].[GroupChatSecret] ([username], [target], [aes_params]) VALUES (N'huy', N'MXRhbyBncm91cFNhdCBKYW4gMDYgMTI6MDQ6MzUgSUNUIDIwMjQ=', 0x27C9C8487ABBB456DD1B812CD3C69A4EFB1145DD1E2BC100A76A4EB9CE1124C44FBBA35C702EB50B3C614495B705F313ED341302C586C5A45F4F5193CEBB2FAA2B17643F8D94D102817B53429808BABF0B3A739FE20B286CA2F08839834FDBD40AD7231B4EC1E5E1CEB221C842276392C0356A03C55E55ACE5436FF06DCFCCAE870ABBC612E158C1476028C472488E2E9A3B6A077A23288E8B9BA1341923732E64F113137060E364386CC68B1D3E891A64447BFB7A54D3314CD9114D7F1C9FA5CB6317E84691D12E5116B83D1BC2C71C48541470C9D4E95D11C9C2F8982A7E39A8C03D16FC97C90F1099822A4CA31BE553A129D118E7C2C42AE0612D1EB78A5A)
INSERT [dbo].[GroupChatSecret] ([username], [target], [aes_params]) VALUES (N'huy', N'NTU1U2F0IEphbiAwNiAxMToxODowNSBJQ1QgMjAyNA==', 0x5C8D7BF1349E4F1118EB6972E2C7DAFFEC78828BEDF72D828DE6FE4756B5AAC8ED20BB471BE376088B6325A828FE68B702961A333E9531D8F7FE44BB665C078E03A87ADEAF2DC85FD00A77A794DED35323871673AD092379DB1569D3BAFF3329D426549C094229BE8DD0918288D9FCC428D61DBA52577CD3942747681EC5EE70BB40A6D4D18DE9AEFAFEABFBB08F9C58A15890E2D162E69F3562E7B2657A702CD34F1241A834224FACF0AA20B053C95132AA520C533677E665C01925F21A0791AC7585F87580346A81C22367300AA1AE4912559946C2AD4F4E0C64D050CD8AA8D4CA65526594731EC1460603ED41E230F18BDCBA2A7100DF59DA8170586F433D)
INSERT [dbo].[GroupChatSecret] ([username], [target], [aes_params]) VALUES (N'huy', N'R3JvdXBUdW9uZ1NhdCBKYW4gMDYgMTY6MTk6NTkgSUNUIDIwMjQ=', 0xCCAD4C4021C29EC2EE8EA35F3E91548FADABEC3CA09D614FC254BA897B1842EDFB2F229668E499ADA48619D8DEC1D516173339014F19BFAEC1E8FDB5066AACF6F5436CBAD2CFBCBD973112F06AEFE016C4A30CEBA326EDAB8D967456E1FCED532825B4F2AFBA2339EC3E51F3733ACEC02462470AB0B99227793D9615C96716A6310FE1F00860B973D16E1228EA9690A1CB33D67F67F4F5957C5E84AA667353C8723E7232696F250C1B9959D07566627650C79D0A3EFB710A0107B22A40D45B7A3720320F974D88F55895BD471280D9485FBD6A1F318897AFCB6BEED44DEECF7DC17FD949445C0F1F94C6373BDE506CB6F9E9E8CB03FEFF8C6D3EC2A312367C22)
INSERT [dbo].[GroupChatSecret] ([username], [target], [aes_params]) VALUES (N'phat', N'dGFvR3JvdXBTYXQgSmFuIDA2IDEyOjEyOjM5IElDVCAyMDI0', 0x68197B5709D76D55559D71FE202D0DEE871653CB3001ADF1AACEFFC89E4D75E0F719537BEB49C2D25DFD1C3A3B567E426075A643F731888A18F38397C88483767A0398BC2DD61D09DEF6C671CF4B265FFE010BA30B3F48890EA684C2E7A4066ADEE6642B7DA3883428B49C25E7ADF7E08694DFF6E873C8AB92473775044BA09A0DF0D4DA588506EDA8A22AADD7083F7D98DF405B8104BC94BAAFF4EC4454FFE8E2F163F837BE653ADB8F1BB2415962C4E406F98EEBEF492ECDE2D9DC8022EB37E525262A0D3B4A578383DA2FBC3D282D2597E10ED1808D0ED0C5E1F4776981E888E1BB07DBF6756290D022E5DA85E04FBD517119A82AED1A84D28DCA9DB8479E)
INSERT [dbo].[GroupChatSecret] ([username], [target], [aes_params]) VALUES (N'phat', N'MTEyMlNhdCBKYW4gMDYgMTI6Mjk6NTAgSUNUIDIwMjQ=', 0x42B943C89D3358D95BE18277220D0C331F4733BFEF3B584435C5CB66162DFE07297B59FC9285547D809CB3F6AFBA565940C41108D15FF01BDD06BDBB514F6B141ACE288D27EC5F305B3F4A9D8999521A95063D8B084C99D06B9314A3B3F67F3EC9172929C363EA108792B44473BC66A293A668A50A1A928BFC21F7814EF8E2FC876161CE9E06C1E96C1276C7F7440392EE309C432B3707C7DD07DB7EA076C441B11F7FA2E662AD336A6A438CAA58A12C6AB0891575D90DEC185CB4A90306E5E9752B9981CD87094E547BFCEA00EA621909793174FC8C42634762B76C24D2FD822F4A326FB68082A3A63BE27CEE764415E452E3B0A76341309FC11AF21F897A55)
INSERT [dbo].[GroupChatSecret] ([username], [target], [aes_params]) VALUES (N'phat', N'MTIzU2F0IEphbiAwNiAxMjoxMDozMiBJQ1QgMjAyNA==', 0x27911C620DB8A7DB662A6535EAEB284F94F302303F8FC94F01EFFEB2CF82F61A454DD0ED4BF4DBC979CFD9F7039CBEB009E851E73B0E6B11536713BBE4E859FF2EFED27F92BB92E81266D8A506B9A7CE1F280E5466A49CF6A42D5012C697057AECCB343633F11088E334CE2B8AA95B3C5A1EBB32BD73695B5C7EC94D08AA61A3EC720550CF94C6798EF9788CBDBC4122B2A658D0D34DFDA54C63972FD879363ED8D6397D1E715F276DADBFD2E7A783B8C9151F5A1ED17F05E041F1952DC7C04A711883E6018A5D60F870DF98D0675AF40B53906573253741A8C2753F1C2E64C62A5124BA69D0374D4EF47CABB690341B4ACFEE2643B70FFC47ECA283891192D1)
INSERT [dbo].[GroupChatSecret] ([username], [target], [aes_params]) VALUES (N'phat', N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', 0x24644D94E3C25B36BC46FD663CD002E795C83723E4CD01078EA6EF6165FCF4539ACC8B55F1E64335C6494790D50FC4E43D75019DEEFFEF544F1C2D9E726EE78A9F65FC85778B3A7AAC032A7953DC9B3ED6B6273661595FEE8F516275A18274A2BE3CE25EF2EE0A0FF611CE88EFA18E4C1215874A92A6D18147322C8AF23438F56FF9BE67BC944D310DD8548B59C81E9A6653090140CF25F63DBB06FA5AD661FB55727916EB2889F6D8331F205F84ED6FC3CDC3432919947E3BC62D31A1A85EB81427CE1C61280104C23788F1E922A103F2FB0260DA98C62795F3D88BBBD8F7B4049DB51B66A4270BD37E5AB222CB026F779675C9E83F52DFDB85D3BB81E0EF62)
INSERT [dbo].[GroupChatSecret] ([username], [target], [aes_params]) VALUES (N'phat', N'R3JvdXBUdW9uZ1NhdCBKYW4gMDYgMTY6MTk6NTkgSUNUIDIwMjQ=', 0x372F6801E7CD019B265AA68FF3BE7BB65CCB786AED105BB8CACB68A282ED64F7D77BF00BDF87B8CF5834F075201DAD3347942B8DBDF3A0B2A8A1E755623694A4168A0F00B1E60CB19F775B9A5C25F3280419366C6E64FCE136C9E92561A9B7F2ABDEAE2DC90BBE5B1DAF3F3F7499D670F85C11A92CA0748B57D7F9ED3DCFD1CB34CD11EB3D90238E9606481C92F7BCA71309A0DE0500AA3D36A2A577382B7738655DF7008C362B28CDF2C9C10CA030693CCB3EE64BB19B02248E6A4B8D731F5D8EBCA4D83DFA49672B9F1C071531DF29A87563AEE9E7B89CC14969BEDF70F186D4969BEDD018C62B2C762353DB41639E9EE8C8C30D80A8632C2C2EB41114217C)
INSERT [dbo].[GroupChatSecret] ([username], [target], [aes_params]) VALUES (N'phat', N'Z3JvdXB0dW9uZ3Rhb1NhdCBKYW4gMDYgMTI6MDc6NDkgSUNUIDIwMjQ=', 0x9F857105D6D3D42B9F4BFE8EE8000C48C00A6D07F2A5FF2024ED7556863ADA2F001358CF9590C07D173063681A79B4406FD15C84CF52DCEA632422DFB22D1ACFF415D690808F20FF8E976B6D8A809B31B08350F95047F9AA1EDD6FAD2853536F080EAF764EE7CB7FC639721431D0671D8462B2B7D9DDFC71334B4285F3CDD266D43253E2B71E4DC52035275A0315D5A4BB803C35D19ADF5E4153BD80A7FD8E3417189DE14AFB813A74D9E2F5D3D4F79A0DD691F16AC9B2C51300C4E5E8E588427CF7C91AFB5AFABBBA2F3AB638F37F981326A6E9DEA6CEC923B4900C9F96F76939B09C238C2D329AD40A56B190772EDA16C7B0818537414F2595EBF4DB0B4082)
INSERT [dbo].[GroupChatSecret] ([username], [target], [aes_params]) VALUES (N'toan', N'dGFvR3JvdXBTYXQgSmFuIDA2IDEyOjEyOjM5IElDVCAyMDI0', 0x90695F7E0334DED291453BB50402EB0190F303B0D86C10B9072FD70A804E3C76B35D88A4FC95849434962436DB08047C5B86B044407AFAC5BBE6BFADE74D6FAB1B87DA1B23ED814FCF9B38721442BF87484729004EB1202C85B02C75FCAA8AE0B876D15C957CB07F27B025EEE5FECD3584CEFF682DEAC18B1437A197163A86713A27077F77B5B2CFB8CEFE12276CED9F0F98358DACF18A3B247DB5CDD213B721093B6F37C721A2EA9FB34806AF3DA6E03E7DBC949B40656F280B050540D0C02F173CA372E9F5F57CCB42DE070436E3333CE59F60506F50BED6816B8F09E7AAC2B3491B40C8DD23FA2365713A33E306D75B1FCD2839669767D6AD24006A2EF2B2)
INSERT [dbo].[GroupChatSecret] ([username], [target], [aes_params]) VALUES (N'toan', N'MTEyMlNhdCBKYW4gMDYgMTI6Mjk6NTAgSUNUIDIwMjQ=', 0x9BDB9797E605B0847CC6D2144417588F10685BD9C8418CC90550BA7C6853E0F91DBD64D78C1874B37BD5E3A36E7619D01725E8B3FA428CE84051F187E114B30041253D8B9155D5F94BC363A170BF85516424761530DB1AE85EB4C3D17FE19C9AD0C7BEE6C38BF88125C1AB29F96707971C6117EFB75293118C5CD5E6139D6DE27C50E93CA4E4F7721F7AEC263574653CBEC2B68C3CF5F5EE4E92C0598AC3D77FE2A33DD31AE7F0EC86A9EF8B16209C77B31B525ABC1DB61806FAD842C78651ACDE3EE14421B7F68B49E0D9DE4BA15F9296F4D1B06253A4EE246A1F409C3C889DB66090687CD2F250098B3652B3EDBFAACAF241C8128D5679D0A6F5C75A97566A)
INSERT [dbo].[GroupChatSecret] ([username], [target], [aes_params]) VALUES (N'toan', N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', 0x5CB3472D1483345291E69EB9BAECF0F668B1889206850AE829C1032D1D878FDB1A4C5CFBEDD0F37DF9DF2A5A9AB4CB666487E927368B4AC4B39763C2B3512FBC59AB4F1A6240A3DFD09931545AEDA652AEA17B6F5EE1CC462C521D6DA928F31D630234DD27A50429A3CE652AA1816ADF8A498F8A68D0FFFC4A1FED95DF94FE25E21680AEC9A7F6D639A6528405ED830D60D25964CFE6FBC8A180E38B6B3E64633780D76D193470522FAE0A5C4446998BF48DA0775F665D8E93DDC54DE01CDA45BF5EB37BD020DF9959A3DEFFED86F801DAF92C2FB04949D69BD558B1BCED107F70E0BA2E0C8F08824ABA113B8D86E6E9E205E0C9A6742CFEF03B4656C8CC354F)
INSERT [dbo].[GroupChatSecret] ([username], [target], [aes_params]) VALUES (N'toan', N'Z3JvdXB0dW9uZ3Rhb1NhdCBKYW4gMDYgMTI6MDc6NDkgSUNUIDIwMjQ=', 0x707C72327DCC0C633A9A4A64EBA2EFF30CB174EDA4FBC6C8FD9FD599431526D167CBC574FD4BEFE8B99A418FD96B1414C722634476C96C357389B892AB58D4A9A265751E804A4C56D5D650ACF0D7CD27153B1A318C6F04781799CB1151F32A9C4F90127FE5FE24F3E685DE1597B1271ABF1E223D6687CBB74FDA017BD5F06E3994A0F899D9E6A1E2A06B68E9A2F999062CC1963340B08217945DF2ED9FA236AD024181ED49357FBEF255B5E8CB0A0D6E89451AED58D09F12683BC0B2B4209D1E25EDACE68E6117129DE893BFEA2531FA63633313C605CA5C9E64036A3941A972D5EA08CF0DFE027341D53A29F01EA32DF221B1D1E96BB0CC99B6EF13757CE013)
INSERT [dbo].[GroupChatSecret] ([username], [target], [aes_params]) VALUES (N'tuong', N'aHV5U2F0IEphbiAwNiAxNzo0NTo0MyBJQ1QgMjAyNA==', 0xA7965E37A3EA2E8E760B109469AD4059F537957444EA0F96705AE3EE4421765A83C29CD6DC68C53350BCDBCDB6EA1785E2AED728BF75D7DAAB623536A33748E8ABB9B28331E947B356A61D0487893F9314C5763BED689CE733D4697F6757FF8BD7BA8932A48E7AF832AA052C96710EFB6B47A551A81E2F0E883051BAD6FA67A6B076382FEF0BC44AF1A4FBE38DE31278781220D524B67F62471AEB4A4A71E65E5CA13C03100E081A8AFFEDF221E5CBDE1C1F9932503E4374F14EF7C5EA2F7AC56C9404913D3CDB426C9A4C72EF2BD42A818BA740161F03202379832E95FF2DC1615DC814590742851A452BBD9D9F6C886C0859A8BB7ED32A7E799B5C61FE5DE1)
INSERT [dbo].[GroupChatSecret] ([username], [target], [aes_params]) VALUES (N'tuong', N'dGFvR3JvdXBTYXQgSmFuIDA2IDEyOjEyOjM5IElDVCAyMDI0', 0x7CC97E915CD60143F908A9E12B880E9FC5956EB6F640654A4DC9F05FD61A4BAB50BB527E9EC19E60055544D103C9C7D08191A8B5C40EFA20873D1531CCE60723B2A2D73F6CE01A27B1D8918E627FDB9D54DF843A12B388F71AD3DAAED29F7249B7999D67942248C9BF8B03A87C64A6861B0E9736A96D0F9D7A9853C2F57BABB48156261B40ED194482A54F57CD535E92CEFF997932879D08D831DDADF19A104BF705BD047B9F5807D4256E749A666BB101006874400C9412A20DA5B8D74169DD6DB8553CC94C64E8E3B52A971A2E64157FB27DF7737828BC3F3EB8C82364185479BCAACB6AFBED74922F0C1340AEEF1C828457009AB45AAEED6C2A446BE3DDDD)
INSERT [dbo].[GroupChatSecret] ([username], [target], [aes_params]) VALUES (N'tuong', N'MTEyMlNhdCBKYW4gMDYgMTI6Mjk6NTAgSUNUIDIwMjQ=', 0x6886665430C6B79704EFC97D9F515B9926CA8B010B4C2C9B56B6E396F0E7B476589989054713DF37565BE1514417165D42E3289B796C747314715DEFA043988E4FB9F45EA4591B125F1ADFD1E938DBA5E0F938EABE3C16E450EB17760E0377B732EA314B6259D2F0EC984966A1FA58DBA87BFEFDA21E0289EC9D77C7B19B4EB2F5521BA98DE1A0D5B2D9662768EDA976B729FDB25B9C960715E99C879216B0D0DE8B81B20D3D896AE1957A40EFD7D9BD5053405C451A753380EC48E96159DB0F4B8964A48CBE24846EE0CB3B0F8F4772716B3F22348D77965E7D3DA95F8E5BEAD743ADB4779ADF20504CDD0125EF3EC9323F508F74D09B33B0173E19CE735D33)
INSERT [dbo].[GroupChatSecret] ([username], [target], [aes_params]) VALUES (N'tuong', N'MTIzU2F0IEphbiAwNiAxMjoxMDozMiBJQ1QgMjAyNA==', 0x320CB98F8FCE13A19E8AB0F66BB2A385617D0CE91B50482D0DC48007B8B2E18E7D8692D57F14FEDD033FB588F288D825C6830DCFBD1990B1809F69E6AB7D251C130B2B2C460E880F68F889D8DCBF86381775A0F3025BBF9F90A720DFA75FEAE1A27E437B9BA29D36D2EC736817FACFD7E2EEE01EE95CC2C7250ADBE32E9E56F6810EDA404DBC0E43D987932B6A0D481397EF4A0863E0438C9920DEFA0ED7F60F601AF7566C8F2B4BBA6DABD962B7808C5776D21CDAB9DDE8D2F40EC7363A272547130797D12A31BED171F5C377984C6131E9B3C3C044412E789AB0132495209F8FB1E91A3289681DC75E813B698E389FF7203290A25AD4677ACB4C12BD2DF6C1)
INSERT [dbo].[GroupChatSecret] ([username], [target], [aes_params]) VALUES (N'tuong', N'MTIzU2F0IEphbiAwNiAxMjozMjowOCBJQ1QgMjAyNA==', 0x3FEA147191653A72BF0E3BBADF12AFEAC55B4BD4087ECDED5AA0C883D2E1746D767CF7B47AEBEA54C6671D95005086DD35F64A61164A148B765D524B3FCA4BA82F39BC3520F37DC7347D6489E147C2BAFD0D9712FCA82F452F30E338510388F8F2D729E58489AB6618C85F0C962AAE21599DD5E979A6A7140526A04016ADB087A1B66E2DF580A865BF9BAB3321EDEB6DA29F0950CD97F767DCFA5B27427ED332ACC427DFDF7EB6052A2430DDFB3DB0046C4C93C618AA47D59957DD22B302D5D18A01830B7C51F29FC0C419684558C123C80EB40F19378FCFC5A3B43985804453756C522FB98D37CFB87928F567441BCFCAC0E2286A55C5633E95662C1E000B96)
INSERT [dbo].[GroupChatSecret] ([username], [target], [aes_params]) VALUES (N'tuong', N'R3JvdXBUdW9uZ1NhdCBKYW4gMDYgMTY6MTk6NTkgSUNUIDIwMjQ=', 0x6175E6771CCD72EA874F45688BE1885A425A82F3C866FD077E5785EF84BDD3083D1EE2EE48B6EEBEC20B47FAC7F4954BF5819CA48EB297A3FC5098F3A001F36465B5C9ABAE196A9C27B5118A225CD9E07FF81C56D08F37684A529C065528B33D716C1B4D11E9F4BE04FDFEDF4488A3E8187C3F6AB5A521F7B91790D99D414C6C8663D1300C3A7A5F3E803513517E0AD0FEEBB441BB3C4F4A25E149DE7CB77DFD024B0CE6C72FE201F2B686E4B590DF802184661DFEA98DF6C7A9B38AC600AAC5455630E5FE2D1C2C868C1B748980D0BFECD0221CFF63ACC907D7B4CB4BB697F1E9DE657D0E12AA90CF13B13A1A7BB024AA08021F64582F02EC71C53EB0F98E03)
INSERT [dbo].[GroupChatSecret] ([username], [target], [aes_params]) VALUES (N'tuong', N'Z3JvdXB0dW9uZ3Rhb1NhdCBKYW4gMDYgMTI6MDc6NDkgSUNUIDIwMjQ=', 0x24642055D18050ED276085EFE347E5C6B9E63227909C18CF22DD433CB4384AEE4BF12A8B4487F7FD0232752604499A052A4B02D41E7E26FBD65EE403D34B386465582D19FB0FD9184B78EB9CEFF99E1BFC168CE14F6147699B25E8C69BB1D0B78F1A2A0283A8E0774D7BDC82881F45D3E6BBF3830A0A0F39150E4C5E0D0883DF6B032E87239AE47A9D043CB490B0ECA64335F61E1720C9E1133027C1270857B4D0E4D6FD50D8F60345E644F6F25D98A7A8B5CFC3E6495A94A7EE4015C06E539CE66BFC7582FB22B3A9383E511CCDA21CEF38680E1AB0AF311FEF9AAF0B5CD3705E894F0CECEA7BF2BF2E14921BBD4A2A5E6BD2284E02F030E452045D31DA2BF5)
GO
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'1', CAST(N'2024-01-04T16:51:47.977' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'1', CAST(N'2024-01-04T16:53:26.540' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'1', CAST(N'2024-01-04T17:01:01.227' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'1', CAST(N'2024-01-04T17:02:42.580' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'1', CAST(N'2024-01-04T17:07:40.690' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'1', CAST(N'2024-01-04T17:10:09.647' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'1', CAST(N'2024-01-04T17:10:58.203' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'1', CAST(N'2024-01-04T17:11:20.387' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'1', CAST(N'2024-01-04T21:00:45.147' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'1', CAST(N'2024-01-04T21:02:13.967' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'1', CAST(N'2024-01-04T21:03:34.847' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'1', CAST(N'2024-01-04T21:04:58.037' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'1', CAST(N'2024-01-04T21:06:56.327' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'1', CAST(N'2024-01-04T21:10:12.863' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'1', CAST(N'2024-01-05T01:00:49.200' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'1', CAST(N'2024-01-05T14:30:34.463' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'1', CAST(N'2024-01-06T11:10:23.293' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'1', CAST(N'2024-01-06T11:14:42.983' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'1', CAST(N'2024-01-06T11:28:00.397' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'1', CAST(N'2024-01-06T11:55:43.897' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'1', CAST(N'2024-01-06T12:02:22.903' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'2', CAST(N'2024-01-04T16:53:30.817' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'2', CAST(N'2024-01-04T17:01:08.153' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'2', CAST(N'2024-01-04T17:02:51.863' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'2', CAST(N'2024-01-04T21:02:37.270' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'2', CAST(N'2024-01-04T21:03:47.923' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'2', CAST(N'2024-01-04T21:10:17.920' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'2', CAST(N'2024-01-05T01:00:56.227' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'2', CAST(N'2024-01-06T11:16:15.957' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'3', CAST(N'2024-01-06T12:01:06.000' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'admin', CAST(N'2024-01-04T16:51:16.647' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'admin', CAST(N'2024-01-04T17:08:43.733' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'admin', CAST(N'2024-01-04T21:00:30.450' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'admin', CAST(N'2024-01-04T21:02:29.520' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'admin', CAST(N'2024-01-05T14:29:08.267' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'admin', CAST(N'2024-01-06T11:10:10.680' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'admin', CAST(N'2024-01-06T11:30:15.333' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'admin', CAST(N'2024-01-06T12:25:20.617' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'admin', CAST(N'2024-01-06T12:26:05.243' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'admin', CAST(N'2024-01-06T12:27:04.293' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'admin', CAST(N'2024-01-06T12:36:32.047' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'admin', CAST(N'2024-01-06T12:37:45.133' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'admin', CAST(N'2024-01-06T16:17:26.617' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'huy', CAST(N'2024-01-06T11:14:32.957' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'huy', CAST(N'2024-01-06T11:23:32.310' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'huy', CAST(N'2024-01-06T11:27:05.650' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'huy', CAST(N'2024-01-06T11:32:22.387' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'huy', CAST(N'2024-01-06T11:33:57.683' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'huy', CAST(N'2024-01-06T11:55:20.807' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'huy', CAST(N'2024-01-06T12:17:15.900' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'huy', CAST(N'2024-01-06T17:08:14.900' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'phat', CAST(N'2024-01-06T12:07:09.497' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'phat', CAST(N'2024-01-06T12:12:30.757' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'phat', CAST(N'2024-01-06T14:17:20.260' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'phat', CAST(N'2024-01-06T14:18:11.710' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'phat', CAST(N'2024-01-06T14:18:34.357' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'phat', CAST(N'2024-01-06T14:19:12.590' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'phat', CAST(N'2024-01-06T14:37:06.787' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'phat', CAST(N'2024-01-06T16:19:17.480' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'phat', CAST(N'2024-01-06T16:44:34.697' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'toan', CAST(N'2024-01-06T12:07:05.257' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'toan', CAST(N'2024-01-06T12:12:26.493' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'toan', CAST(N'2024-01-06T12:16:48.857' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'toan', CAST(N'2024-01-06T12:23:46.447' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'toan', CAST(N'2024-01-06T12:28:57.457' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'toan', CAST(N'2024-01-06T12:31:31.207' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'toan', CAST(N'2024-01-06T12:34:10.027' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'toan', CAST(N'2024-01-06T12:36:11.533' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'toan', CAST(N'2024-01-06T14:38:33.347' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'toan', CAST(N'2024-01-06T14:41:38.993' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'toan', CAST(N'2024-01-06T16:19:10.307' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T12:06:55.257' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T12:12:22.550' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T12:28:51.333' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T12:29:27.887' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T12:31:36.097' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T12:36:51.600' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T12:38:09.127' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T13:29:36.267' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T14:11:19.707' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T14:15:40.017' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T14:32:13.857' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T14:36:45.570' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T14:38:22.063' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T14:41:26.500' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T16:17:33.553' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T16:18:08.527' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T16:38:09.667' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T16:38:57.687' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T16:41:04.863' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T16:42:58.317' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T16:44:22.293' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T16:48:01.240' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T16:49:19.733' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T16:57:01.467' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T16:58:47.650' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T16:59:26.450' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T17:00:46.517' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T17:01:13.150' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T17:01:59.987' AS DateTime))
GO
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T17:03:51.097' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T17:06:11.900' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T17:07:29.353' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T17:10:14.397' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T17:10:44.847' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T17:11:51.987' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T17:12:36.920' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T17:13:16.307' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T17:28:05.857' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T17:29:23.860' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T17:30:37.367' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T17:39:28.680' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T17:40:17.937' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T17:40:39.923' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T17:44:36.287' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T17:44:57.570' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T17:45:27.707' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T17:46:44.837' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T18:19:13.537' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T18:19:46.317' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T18:20:53.883' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T18:22:41.720' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T18:23:21.137' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T18:27:09.787' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T18:29:43.220' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T18:30:52.747' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T18:31:27.567' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T18:38:06.477' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T19:06:52.813' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T19:07:43.280' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T19:11:12.400' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T19:15:42.030' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T19:18:47.343' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T19:20:14.440' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T19:25:45.610' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T20:02:25.367' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T20:03:45.833' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T20:04:45.040' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T20:05:10.540' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T20:05:37.477' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T20:06:04.930' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T20:09:23.807' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T20:11:29.247' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T20:33:20.553' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T20:34:20.423' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T20:44:20.133' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T20:46:19.007' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T20:49:02.710' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T20:50:52.410' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T21:05:03.580' AS DateTime))
INSERT [dbo].[LoginRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T21:42:35.960' AS DateTime))
GO
INSERT [dbo].[RegistrationRecord] ([username], [ts]) VALUES (N'1', CAST(N'2024-01-04T16:51:40.723' AS DateTime))
INSERT [dbo].[RegistrationRecord] ([username], [ts]) VALUES (N'2', CAST(N'2024-01-04T16:51:46.030' AS DateTime))
INSERT [dbo].[RegistrationRecord] ([username], [ts]) VALUES (N'3', CAST(N'2024-01-06T12:01:04.713' AS DateTime))
INSERT [dbo].[RegistrationRecord] ([username], [ts]) VALUES (N'huy', CAST(N'2024-01-06T11:14:30.800' AS DateTime))
INSERT [dbo].[RegistrationRecord] ([username], [ts]) VALUES (N'phat', CAST(N'2024-01-06T12:06:52.037' AS DateTime))
INSERT [dbo].[RegistrationRecord] ([username], [ts]) VALUES (N'toan', CAST(N'2024-01-06T12:06:47.140' AS DateTime))
INSERT [dbo].[RegistrationRecord] ([username], [ts]) VALUES (N'tuong', CAST(N'2024-01-06T12:06:43.350' AS DateTime))
GO
INSERT [dbo].[SpamReport] ([reporter], [target], [reason], [date]) VALUES (N'1', N'2', N'noi nhieu', CAST(N'2024-01-06T11:30:08.487' AS DateTime))
GO
INSERT [dbo].[UserChatSecret] ([username], [target], [aes_params]) VALUES (N'1', N'2', 0x7D474D0384F200362E4ACF7A0404A7AEF1B395777C6128CE4D3751E94FD60164773C8795CF8BCEE6D66E9A513F7C32FFB7E29B255416FD987E61F05FAD40FFDAEFBDD10C2B947157DF5D3F67041EE7C2B8DA4A08385CD085B67EF91A8D05DB235B965B753924E30F8F238908ACF74DAC34D2104A05400C8D68231A62A9675402B107454CE6C48CA70FEA216D266C066B9D5E19FBCAD7EF4A173554E5EA71DB6C56BCC5F43FE819C752B8519C3729F1C52977678E171ABE7192890B0BFB71EA27329D838AABE8E9F412AA26919FD4983E5706C00180C0322C0C8F88D44D64C5CB5F6C2984322E0860F227CEB5965E92BE6F3847373558521BEC1E1D7AC4DE24CE)
INSERT [dbo].[UserChatSecret] ([username], [target], [aes_params]) VALUES (N'1', N'3', 0xA432DE2F352E9C443BA2BC3DEB93BA955FD65B698F9DF2038A9C165CAB8CEA3407C98AFBA036A2DCF2BC6AB8D05AF2C5F0E0775618C82F98A3BEFADED4F957237D731B8873B89434E7328AD479C362527A924BA250C22C7F62CBF2BDA2B586137A35293DD8CCBB8287B8159A02942B3B362B7825D827EDA5A866C5BF07FAD8C8B1941FD5D6BC7A48DA9DC0C844269CF66CE984EE65DA7151FD2D2A3A77A9D554EFCB209744C5170226A34F16D5BE7BACCF96132BDDAC296E6CFB52756E757D8B8E3B626A1AEFF50C0A871E157F0D00EE4F6B4C3A607FF79001C42E609CEBD97136FD30FA6B4E2D56752A589DB490B7C690A6F4B436E2FFC89A6CC68A6E1C06F9)
INSERT [dbo].[UserChatSecret] ([username], [target], [aes_params]) VALUES (N'1', N'huy', 0x3097945124AEF248DEB3CD028B7BA0F60F84196463C2C315A10388DC90B81C9C30B1D6E7086B8C109B4D8F44C5C3F009BA88CFADE27D85FBFE42CCF43C8E1DE8F12A66EE450C2E27AA6ABED1217F6A8489B524DD20E1EE91CE111BCC94BB95E473FB61875F4D4A3A4AC453D990CC372684AE1F909849AD03FAB32655BCFDF5ADDD29FDBA0B8C34538D90F575C192A4E761A8CD5C3191604959130F7340F677B70749E82DECC41BEF573AB88D71BF41BBF23CC5E199D6DC718400F0D43E7683A60E3F666220E0D8729F351B13FB56C1765823665215F3CE41E454377ED80FF84CFD9B9261CE81AFC2BF8C052800C999B983D254097CFD60783FB92DBEEDC1A742)
INSERT [dbo].[UserChatSecret] ([username], [target], [aes_params]) VALUES (N'2', N'1', 0x0ECC6072C421C4CF545A63F3A64BEBA9CAF85B47E99E4EF743D7CB47B1943F0A0B685E37041B23876EADCEEF7B74DDD743941568145278F83818D6A2F81DAB2E4C822C684155175286A6641D9157264BD19B494045BFB007A90FA58F410E088F730A684D129F4E7D54FCBE45BF4087FAB524DFB1FEBEF399C793E55F90373DA4407EA034CDF6E327720B410631A8C2C19BAA9843AA7EE7D5CDC3382C6DDF8A7D372BA7FFAEDC7969A7D790AE2ED053C7B495D8CAA1F853FB50EB960B4B9A93F417D411A7FA9D21B0CB10574A07415014865F984E3AE1461B522DC73E50819173A6B2454495329A5F8F99AC45D3404CB5D450ACB77C50B334AC5068A8682BF60F)
INSERT [dbo].[UserChatSecret] ([username], [target], [aes_params]) VALUES (N'2', N'huy', 0xA6D63C59C7A2BEABFFA2EFAA722A4A811B2BFB3EFA4674E4487E3D8843ED57A5C4F71FC8588F6BFA63AD51F5C68D5B4652E5D2151171644FB5B6A42E50E493C8B6B1B75A58C9FF17B95277FC8EE7088E8FCB1E7EDC75980DE35AB7EBD7057FA42FED9DFCAC779D682DC4CF2284E1BF5BDDDF1061F6953C88C6655FF6B4FAAE48D29B2109093A16A951941A6BD4D16E910A1F66F90DF9CBB3A83DA63140860BA42F587411977D6CE1AEC6481670C715D4B0BEFD589C4B1D9037B08520C1DBAD03F4BF82AFD7643C3D3181C76BB8A371F517840C695B139CF21F21F6DD0BD60EA7F32637FF794A9EFACB41724D56D47007EB09A94EEDF81C6E39D618812EFC6206)
INSERT [dbo].[UserChatSecret] ([username], [target], [aes_params]) VALUES (N'3', N'1', 0x451EF315CFA424398F8ACD7948C641CF41883CB777131934A816EBA82B234BBC18CD631962CF3C17A5ADFCFABD782AFD9A6D9D3277F9E0C4702423EDBC12968E39134DAFB17B13BE330D97FF027E59A2DA5F8DE835E93FCFC1133F31313A10066C5565E0DA79356E9C8FB6099A677F3E20A5E2AA0688840D8020044C2C58D58C9DCC13DF855FFDCF1795BBD13B0AB515C376F69043B863F7A75C9DD0B8233A234E92515812B67889A940670254F1DDDDB624FB83AB4BA0FD124BAE7CAEFC7301336638BD90A0B160A5D1D7BA311B9BFB799E558B07BB22E910948539A90E177571C9E9CE0D06B16A8DC13532E11A36FA801CAA7A3833B0B7CD929D332E94CB08)
INSERT [dbo].[UserChatSecret] ([username], [target], [aes_params]) VALUES (N'huy', N'1', 0x531313044D123F5FF6176AFCA2673B3FC0B441A99C652259C6BAE99117CCEF79EB780B5423C2183D09CE7CA599475611821162457015F375089982F9C064CEA34CB16E74571EA16B30F6AEE5EF81FE5E92FA64203FC799283007144CA255B6F52E909E1E2FA71B96C88A6B1198E3918067B26BDF2F21CCBE90DA8CE75AF65CE960751028D902BEF75E737140F9DC314A66A1D196A5F35CA93F9274698C3F3938BD1895F35D656F43221B7512488D36F6552B89D382827A01B99B976BBCF1C6296C6F24D53F2E07ADBAFB81493A4DB15EDDF3F613F231BD8F61A6FEBF5F7365E65B592032483278E3F2AC55F78B19979B3026AA134B42606961ED618DFFE9A848)
INSERT [dbo].[UserChatSecret] ([username], [target], [aes_params]) VALUES (N'huy', N'2', 0x200B5536F41BC8F3415B3D52F488C85F1389373EAB2FF012887FC9460718D7CE4FDF40D0BE17F27216C127D47C2A45AA256D883917C6519D897838A0D5D871CE2B9648E22A044F9330CC24E8084FF150924415980E92A70D20546B0C1F2BBF3FC11273549EB781ED87F570931490049D695425CDF1F0D2E96BB3B831FA0E27F86A5C5B5D9E3D28E44DD8940918F8944876908FC26B58E3C7D31F6C30A2C35AD13C68AC095F3B6F19EC7B182210B4144E7D30D019A4A6BD23690A11C5EEC295F64E9F8A7A1EA63AA1386E3FC9032CCA443CC799A44FEBCB0B53B8A1B66F0F1F9D28F05DDFF286DEEC91BE78783C8C3527F18AD4079968BFF58A80EA2AC6F6103C)
INSERT [dbo].[UserChatSecret] ([username], [target], [aes_params]) VALUES (N'huy', N'tuong', 0xB9B925AC31B98771377F5EA975B35F437E63441E9911391F46089AE8ABE2CD8C296FA655949AE0D2F5EBB9649432284E3D0CF509C4C15E5013F8E7FFEA13C8D9A41651D5FDAE9F5C22D4994B1CF2B76D77724F5B86189A9BD12814661F0C0252586C9AE57CCD36CE0B9ED82DDA7DCD2379E77B4F3A5439D0D8206EA55EB36B2DDC197AE30DCF21BBD61B8306683C2BE518CEF456AE8071FADB1B3AF7A1D717DF9ED19E39E655C385F6ECCA7CE59B9336381622239AA2BDABB9E4352D0815F1300F7E8B7656C130287F64444D731AC1F802669AB74B6FE3131BB805D5D5640407652556102B43356A70E4EDF3BF0303A3F5C7A4E85239DB954686958C3084BE1F)
INSERT [dbo].[UserChatSecret] ([username], [target], [aes_params]) VALUES (N'phat', N'toan', 0x227EB8E5617E51038CD9045FA3C828C06B7BFAEBC67B8AFB9AC4242EBA2DD78CA6732DF91D67BB419D1C01E4081A2ABFD7852C9AACB3291643A3481D732A25BD0D06C12A0BB2E2C258EC34240D89A18A2FC73659C1C37EBCFEA4CADFB83E851A6F97B8E8AC2C7BEB8F1468C4CD91AA72587AC71A315C008C74BA53CF6B165119F291C2AD90CF12514CAEE526C176330077BEBCE9A7E566DF526F31FDDD2D634ABF11D9AB13FE2632A6786418D7D26859E9F2A3B05A78B81616B87F1A55532D2CC42E2260EB0DF0FBF101A525D33E2DA73051B1F80C75CC45E55FD516C50FF280FEED886B551FE19350C2456E70A6C4242835C2E8235D1B3B440A3CD69A0B2E8E)
INSERT [dbo].[UserChatSecret] ([username], [target], [aes_params]) VALUES (N'phat', N'tuong', 0x2EA62F74D4F4A8AFDBFAFAB2EC1E289F4BDFC18DEC74E55EBE352F3AACCA8907C4CC2CEF04309254FD92F0657E7BDF0D55905FE9C93DFF41FEA09BED8D1B58F89103BD09D08FBBB05072DAF7EF782E31B847ED64AE6696A7838E44993F8AC7AFC1475CF3D8D81C14B83A1B65AC416B24ACFBDCA50F9E3384F32AC8FEB6F2EFED6842DE0A96FA42CDE820774DB5470E04A21FED6578C3F17CE0D9C8208BDC76D3BB3AC3A9BDA0BF7103201E4CDB7927C675D2E98942AEDF576E04A534928A0AC09EC69CA94EF4286964AAE46DD8FAB728257E693CA7D6340CF788B026AB74E9905C30707135B4AE2FDA04759F46978AEB2235497A06DCBE3B96CEF66522B348E2)
INSERT [dbo].[UserChatSecret] ([username], [target], [aes_params]) VALUES (N'toan', N'phat', 0x98BC91E588FCF0A87BA56ADDF20A7E750F658561D73BE1C431DABEC8743F1BA3AC6060D1E5A1FEC1C4A30E69E596B2265F71EDCA8B2D428C05C80FAE45B1D9B5474400B6B394EE3DBDBB6900B8A68F140FA1A9F4F79DEBC308F27EA4E04CD2B2260522026F2A2F72869299DBE86E84033E5EAFE50700E99CD30A30AF72396E5EB9B1FE379D94FDD905F0A5E1EBE53164B34927AE4B73EAEB81925EB23B1E5E1996A0FE47D36447BB4C1769F08D25228B596C23209591A3CA57D1045B9EAFABD10AA6CE5729A8E9697A45C548142926DE1740D3E2B8C8A43573FD08F541CC940FC26CD81E4BAA6A4FAC5374AFA17F5AC9684B455B4C8DA0EC0A923F7C4D0D050C)
INSERT [dbo].[UserChatSecret] ([username], [target], [aes_params]) VALUES (N'toan', N'tuong', 0x309DF94DFACC95E9AAAD54B524D4703A39424DFFE73966C90A320756325ABF313A036F6712785D955FDADC8CE80252C3AD5C764F0C22D3152A462642DC611ED7D5748BB1248EA5DC52402AFFEE3031B4DDF4D81A9AD14CF6975BA2CBA281C46EC9AAE88150EF5CF8972C7B08F9DEAA69AA8F17C8C4FB5DA15E6ECEA6A49724EAF49A4D444A95A79BD8DFB0BDBBD8307F09FD439BD68AB41EFC1D54F945E6413F70271F886AAB7B3F743DC658BA25D340B3785A27D304DB14C5752E3E3A6592FB9F3AB37BBDC8E549BB1D3E61405A17F3F0CBD5BF2F30B82E1CDD84B1C49054D0FA3C365863FE94F72DC02BDFCA5DC8E301744B2693FC852D7698C67362A97F17)
INSERT [dbo].[UserChatSecret] ([username], [target], [aes_params]) VALUES (N'tuong', N'huy', 0x59956CA89E8DBCAB47EDEF692FDD2AE97FDE6261EEA436967E6AD64BF289BD2FADDE933CA00DF0EED111D9F4E669C67DA8F19B2446364F7B6C7D5AB508FC583270A20213C1D608E143A4D1ADE4DBD5BDA214DE55800214B3FCE78A71AA00B5A2C1EDF150327D52C8622F8010DAF7AA0AFACD742B9412EF5E5F4F8F4A34419354978959E4171F6F6735CD490A47488794C638EF959E38FF8A47D36CD849DF2BFF7F9A405C7209FBC0398FBBB2C2CF5913F00445E752553CA5120FFC8B01C113B2E71DB2F0753E3A7A5E697907B4D30F8744745EC1F8CED54EA4A67F1F0CB90D39311D8AD8ED571135B4AF2B1F1C643D404E58117EDF793B73B7150C5A84BDE272)
INSERT [dbo].[UserChatSecret] ([username], [target], [aes_params]) VALUES (N'tuong', N'phat', 0x6532826A46BB95BE4E0DFC60DAD26CDA144A9CD25B902AE611EC10BC9124B44F0EB5E8C928E3A1A4F927414C026BC4025BD2609DF26BB0ABC05B497E4645012ED3E171638B657ECA996D6A83C2BC3132AFE1D8E628EEE03FA694C18D458B0FF621996104691AD2F6C74A8DE4DA84E03890C3D4B030F73AB846FF6245373D2FC727248AF54C6F34A4231972BFD107FD064DC300CF023998035DEC601307EFB2595581082960E5B002F579AF8C6230B0546D338B260328AC7770621E8B3566567C8F65FB345BF789D044462284367B6EE56A4AD042CAB8EF33604620E7BC4AA36E5D2E1F83BF5D46220D8CFCAFB0B45AFBD66CA6C8CFDC5A313CF01B1D849AC388)
INSERT [dbo].[UserChatSecret] ([username], [target], [aes_params]) VALUES (N'tuong', N'toan', 0x6892723B0EA1EDF1666AC387B51032519528D4612A6D38F967D7D8EF8B655C55D748CF85EC3A84613FA0EA11E0A10C485CEC543FA9B88B221CDD385D42258CEE2EDE7E2D7156BF5C71FC8397FF3228F47FF2D6B6205263D4816930BCC14CABFC338230431021581ED062896A90666674F6F7C27642F4FDF36B24419808A8625320333D934D421A666F378538865A112F58E48E0713A603080B643AF6D6434C29D0F95DA9E51E0C7E596B939094BBADBD0BA4D32D6FB7008149876E32E78342C5BB90508DCBA7C4F54BDA645B4AEA3771CA58355B033C6C30616CA1564AC0CA8C76F68B17E54232C5CD16223A25410F070C3913975CB3CF4846DE1413B3DB016E)
GO
INSERT [dbo].[UserFriend] ([username], [friend], [start_history_msg], [last_read_msg]) VALUES (N'1', N'2', 0, 33)
INSERT [dbo].[UserFriend] ([username], [friend], [start_history_msg], [last_read_msg]) VALUES (N'1', N'3', 0, 1)
INSERT [dbo].[UserFriend] ([username], [friend], [start_history_msg], [last_read_msg]) VALUES (N'1', N'huy', 0, 25)
INSERT [dbo].[UserFriend] ([username], [friend], [start_history_msg], [last_read_msg]) VALUES (N'2', N'1', 0, 33)
INSERT [dbo].[UserFriend] ([username], [friend], [start_history_msg], [last_read_msg]) VALUES (N'2', N'huy', 0, 0)
INSERT [dbo].[UserFriend] ([username], [friend], [start_history_msg], [last_read_msg]) VALUES (N'3', N'1', 2, 1)
INSERT [dbo].[UserFriend] ([username], [friend], [start_history_msg], [last_read_msg]) VALUES (N'huy', N'1', 26, 25)
INSERT [dbo].[UserFriend] ([username], [friend], [start_history_msg], [last_read_msg]) VALUES (N'huy', N'2', 0, 0)
INSERT [dbo].[UserFriend] ([username], [friend], [start_history_msg], [last_read_msg]) VALUES (N'huy', N'tuong', 0, 0)
INSERT [dbo].[UserFriend] ([username], [friend], [start_history_msg], [last_read_msg]) VALUES (N'phat', N'toan', 0, 0)
INSERT [dbo].[UserFriend] ([username], [friend], [start_history_msg], [last_read_msg]) VALUES (N'phat', N'tuong', 0, 0)
INSERT [dbo].[UserFriend] ([username], [friend], [start_history_msg], [last_read_msg]) VALUES (N'toan', N'phat', 0, 0)
INSERT [dbo].[UserFriend] ([username], [friend], [start_history_msg], [last_read_msg]) VALUES (N'toan', N'tuong', 0, 2)
INSERT [dbo].[UserFriend] ([username], [friend], [start_history_msg], [last_read_msg]) VALUES (N'tuong', N'huy', 0, 0)
INSERT [dbo].[UserFriend] ([username], [friend], [start_history_msg], [last_read_msg]) VALUES (N'tuong', N'phat', 0, 0)
INSERT [dbo].[UserFriend] ([username], [friend], [start_history_msg], [last_read_msg]) VALUES (N'tuong', N'toan', 2, 2)
GO
INSERT [dbo].[UserIdentity] ([username], [public_key]) VALUES (N'1', 0x30820122300D06092A864886F70D01010105000382010F003082010A0282010100AE77D68DF77CFF3031052F284EFDE90AF3784000C0C79ED53EC082A55082A50A5A00DA13C422B06BD6F954ED244DBCA51881DBC6BFF5198957BB830CD0B47B4EEC8B3EB79D0A7798832F6B12FAECEB105476FD513437880131B27AE26F8197FB89BFB83519588CC98E11AC2F1ADD4F7F8A93EB488E87A7D11D087F5EB334D2E64AA04BB8AB531DAE4EB28037F9723A5EE5DE38A2B729914680877CB1948335958DB23F87C189E0E472DD93574BBD7859427388926F1B58F317D9A7FB060159FB874AC94BB2DECCACBB29993331031B09A66E906B7F75B50E18165151A83C0A3BDE0F15A141A5DFA574960ED8589D71FF1E9A5E7DC899735ED5213E2D270353050203010001)
INSERT [dbo].[UserIdentity] ([username], [public_key]) VALUES (N'2', 0x30820122300D06092A864886F70D01010105000382010F003082010A0282010100D05CFE511F3D09A905B41719A5A4899F5ECEA10CE1281D08C87A7BA52FE01027C63CB52C79840A69CB481FD229502B2C631372930EBCD3E86EC5CDBBDE0475B9CBF16D10BDCAEE50B8AA9B6E969FD7B8B25C95A58FEEBF97BED0D5EF0F04927C20E8D94914AC3C891E7D8B44AEA3D1810399BA333824C816F1C55734BE1E98AAE3D6703402496F61D419968BA22CA965389A626A695397A6EA486642F5466826F7CA74B49A5D94FDA9C29ECC1301B61600FB4F1650032ECFAE5B4820FED8DFCED0F488718703B109DA8D76F837842A7D5E25E04BD2CDC985D882A8238ACA260093CC5F9A09E0FA192F7ECCA85D97DDE4120CD4BEBB6BF465215CF6ADE53E24AB0203010001)
INSERT [dbo].[UserIdentity] ([username], [public_key]) VALUES (N'3', 0x30820122300D06092A864886F70D01010105000382010F003082010A0282010100A1D219791D7421B7D45EC91194F891FDC690ABB7BF780B4AF5B5C7152BDCDE79DB975C3664EC164E543BD7459B98FFA700422D9CA46EDF048974E83D869FCAF1C524071158A74A4FE0B016D022B34A93F05678BEEAC06789B4DE689396252ACC22AD56CE6E4A9AADB3B3C5502DE1D7988BD3C23B2E7FCE39D71AD1B504A6DFE6C054142F47A3D0247DDEC53CBC6E1930F7E0D48846E1693748FAB551A8541B1FD430C7B08E1E8D62144F11F5D3433ECA6586DC05BE5452EAA5E514FEB15E6646D0E8DC7ABBF1BE2D69FEB0F873BA80A35FD99F21099B61D445AE6610023817BC90F0608A6B3207C43AFB3E025325CC5380B55A80C30A17D2B1D7331AE37F16C10203010001)
INSERT [dbo].[UserIdentity] ([username], [public_key]) VALUES (N'huy', 0x30820122300D06092A864886F70D01010105000382010F003082010A0282010100DBBD997A4589AF43C40B58D9332CF3D72504346AC0686BDCB8B336D9146341912FCC76BF1D6881CBA70AE52778620C1DBCC2D5FE2741D4FA2C9F69FEC8FBD1BC47CC503028D72191F99109F7636A5827C07F9C8588B25CE75D5B8353D0C5D8359B5A908012D4EBEAC944581DCA8E88C64D3E96F5156250F2EAB8A3F26DD48D7D5DC7C50771C88E842005CA6F8FE3F57ABB60D921B2D96A537187E1B9008B6E6E12617AA1C1787F0628967979E5E87E88DF5BFAB8716D49DF7DF6CCA05930B0AABD9018FEDA34D3DE0A63D45D3DBA406DCCA9C97847C122A45CDB3CF7A53B94B7CA26E0520BBA15219008FE36A16DC6C4F296B837576D4D42D4270A8EFFECAA2F0203010001)
INSERT [dbo].[UserIdentity] ([username], [public_key]) VALUES (N'phat', 0x30820122300D06092A864886F70D01010105000382010F003082010A0282010100A6E32060C7EA3B00E7A08C019A43449B6E535497FD7EC779B1E2A5905DFC64C9957F758C5C4F0BA9BEC5FFCB82A0B62DFF71782A44FBFF904C47236E955A7266231D681427977194C8895F8E58A1DEBD9BFAD6F6AFFF7A43ED7633BB9BCAF1008906777F3ED8AAFD8CCFF26A6E123557559BCD93D691A92948FB54FE941DEBFC61FC4D1FDD4BFE911CECA1E0EB4DFC83735050B8226FB1D91D2C9B473B61B3CAE6A089388A6EE11DC25ECE072E888A2B515EA070CCB5DA3BD5627024445BA3BF0846BA5C9622488CB5930CAC34250FCEB97D07D5E02527D69338B38A25FE6AB2717EF4BFA4BEADB74224503FF713B1FA57E57EE3F9D1FFB6B8E6315F394E2E830203010001)
INSERT [dbo].[UserIdentity] ([username], [public_key]) VALUES (N'toan', 0x30820122300D06092A864886F70D01010105000382010F003082010A0282010100B1679CD1657B0705F9B13F04F246CB9AD3E6B5551AC56D3D19AF9361E6613EE4BA5EC9B0BFC0AD1361FE93F9CBB724539D811B1CB80F1F547A8C8942755346469735481E452B580ECB39592FA0B594AF195620CB6ACD8825FFC66112DE6BFE81435DB054A2500C69739D829CB17A3A64D96F70D9AA459FF7DEA283C6809D5B592F791F3CE6876B8FA2AC019EB015D47DC32FFA3B6D3D979F1FDB9F2B5CF5070D74CB6A81E9324160355F9764A2BB477EF7E6147BD23EC4773160D38393417E6D677C194EBA20882D4190712D6BB6BEFD4DAAA3C62A9F1D409D465A5D2C13AF84788C7FAC7097C11230A81ED50E59940E01131D44F46C710F61ABE19E57461DBF0203010001)
INSERT [dbo].[UserIdentity] ([username], [public_key]) VALUES (N'tuong', 0x30820122300D06092A864886F70D01010105000382010F003082010A0282010100A906A8A8839B3A9AA7719941DDE07F33FA47A80A484D00D5214E94247F861DA243B491EEFD7C0C9046D7E8A5F697E1D56398356E3B7B71FE42570750EECC4DE3AF59C33F8CE7D91FEFB52F42359797D5BD8D436E1E299D9EE519BD4AE4E9AC73D94CD2CAF2DD0E2E41B00B7B79223B7C42E21A816EC70FEDF5CD68073BCE9E237B72219D1EE497C57CE7526FFE903CB424585C425DA36A6993B7C4CCFF8228B073F827F6242DA217284C1A6A81E2B1F845B483581013BAD46CC3A0B4A174146A6D0C38FC59694EAF1E7B322C2227C44ADC79F7D544830818B3E0F94D3DD8F3C080960DDD16A66CFCC4D680F5C30557EA7B8ADFF24B647E479127A4A3D824F7570203010001)
GO
INSERT [dbo].[UserInfo] ([username], [fullname], [email], [address], [birthdate], [gender]) VALUES (N'__REMOVED__', N'', N'', N'', CAST(N'1900-01-01' AS Date), 0)
INSERT [dbo].[UserInfo] ([username], [fullname], [email], [address], [birthdate], [gender]) VALUES (N'1', N'', N'', N'', CAST(N'2024-01-04' AS Date), 0)
INSERT [dbo].[UserInfo] ([username], [fullname], [email], [address], [birthdate], [gender]) VALUES (N'2', N'', N'', N'', CAST(N'2024-01-04' AS Date), 0)
INSERT [dbo].[UserInfo] ([username], [fullname], [email], [address], [birthdate], [gender]) VALUES (N'3', N'', N'', N'', CAST(N'2024-01-06' AS Date), 0)
INSERT [dbo].[UserInfo] ([username], [fullname], [email], [address], [birthdate], [gender]) VALUES (N'huy', N'', N'', N'', CAST(N'2024-01-06' AS Date), 0)
INSERT [dbo].[UserInfo] ([username], [fullname], [email], [address], [birthdate], [gender]) VALUES (N'phat', N'', N'', N'', CAST(N'2024-01-06' AS Date), 0)
INSERT [dbo].[UserInfo] ([username], [fullname], [email], [address], [birthdate], [gender]) VALUES (N'toan', N'', N'', N'', CAST(N'2024-01-06' AS Date), 0)
INSERT [dbo].[UserInfo] ([username], [fullname], [email], [address], [birthdate], [gender]) VALUES (N'tuong', N'', N'', N'', CAST(N'2024-01-06' AS Date), 0)
GO
ALTER TABLE [dbo].[BlockUser]  WITH CHECK ADD  CONSTRAINT [FK_TARGET_USER] FOREIGN KEY([username])
REFERENCES [dbo].[UserInfo] ([username])
GO
ALTER TABLE [dbo].[BlockUser] CHECK CONSTRAINT [FK_TARGET_USER]
GO
ALTER TABLE [dbo].[BlockUser]  WITH CHECK ADD  CONSTRAINT [FK_USERNAME_USER] FOREIGN KEY([username])
REFERENCES [dbo].[UserInfo] ([username])
GO
ALTER TABLE [dbo].[BlockUser] CHECK CONSTRAINT [FK_USERNAME_USER]
GO
ALTER TABLE [dbo].[FriendChat]  WITH CHECK ADD  CONSTRAINT [FK_FC_USER1] FOREIGN KEY([sender])
REFERENCES [dbo].[UserInfo] ([username])
GO
ALTER TABLE [dbo].[FriendChat] CHECK CONSTRAINT [FK_FC_USER1]
GO
ALTER TABLE [dbo].[FriendChat]  WITH CHECK ADD  CONSTRAINT [FK_FC_USER2] FOREIGN KEY([friend])
REFERENCES [dbo].[UserInfo] ([username])
GO
ALTER TABLE [dbo].[FriendChat] CHECK CONSTRAINT [FK_FC_USER2]
GO
ALTER TABLE [dbo].[FriendRequest]  WITH CHECK ADD  CONSTRAINT [FK_FR_USER1] FOREIGN KEY([initiator])
REFERENCES [dbo].[UserInfo] ([username])
GO
ALTER TABLE [dbo].[FriendRequest] CHECK CONSTRAINT [FK_FR_USER1]
GO
ALTER TABLE [dbo].[FriendRequest]  WITH CHECK ADD  CONSTRAINT [FK_FR_USER2] FOREIGN KEY([target])
REFERENCES [dbo].[UserInfo] ([username])
GO
ALTER TABLE [dbo].[FriendRequest] CHECK CONSTRAINT [FK_FR_USER2]
GO
ALTER TABLE [dbo].[GroupChatMember]  WITH CHECK ADD  CONSTRAINT [FK_GCM_GROUPCHAT] FOREIGN KEY([group_id])
REFERENCES [dbo].[GroupChat] ([id])
GO
ALTER TABLE [dbo].[GroupChatMember] CHECK CONSTRAINT [FK_GCM_GROUPCHAT]
GO
ALTER TABLE [dbo].[GroupChatMember]  WITH CHECK ADD  CONSTRAINT [FK_GCM_USER] FOREIGN KEY([username])
REFERENCES [dbo].[UserInfo] ([username])
GO
ALTER TABLE [dbo].[GroupChatMember] CHECK CONSTRAINT [FK_GCM_USER]
GO
ALTER TABLE [dbo].[GroupChatMessage]  WITH CHECK ADD  CONSTRAINT [FK_GCMSG_GROUPCHAT] FOREIGN KEY([group_id])
REFERENCES [dbo].[GroupChat] ([id])
GO
ALTER TABLE [dbo].[GroupChatMessage] CHECK CONSTRAINT [FK_GCMSG_GROUPCHAT]
GO
ALTER TABLE [dbo].[GroupChatMessage]  WITH CHECK ADD  CONSTRAINT [FK_GCMSG_USER] FOREIGN KEY([sender])
REFERENCES [dbo].[UserInfo] ([username])
GO
ALTER TABLE [dbo].[GroupChatMessage] CHECK CONSTRAINT [FK_GCMSG_USER]
GO
ALTER TABLE [dbo].[GroupChatSecret]  WITH CHECK ADD  CONSTRAINT [FK_GCS_USER] FOREIGN KEY([username])
REFERENCES [dbo].[UserInfo] ([username])
GO
ALTER TABLE [dbo].[GroupChatSecret] CHECK CONSTRAINT [FK_GCS_USER]
GO
ALTER TABLE [dbo].[GroupChatSecret]  WITH CHECK ADD  CONSTRAINT [FK_UCS_GROUP] FOREIGN KEY([target])
REFERENCES [dbo].[GroupChat] ([id])
GO
ALTER TABLE [dbo].[GroupChatSecret] CHECK CONSTRAINT [FK_UCS_GROUP]
GO
ALTER TABLE [dbo].[LoginRecord]  WITH CHECK ADD  CONSTRAINT [FK_LOGINRECORD_ACCOUNT] FOREIGN KEY([username])
REFERENCES [dbo].[Account] ([username])
GO
ALTER TABLE [dbo].[LoginRecord] CHECK CONSTRAINT [FK_LOGINRECORD_ACCOUNT]
GO
ALTER TABLE [dbo].[RegistrationRecord]  WITH CHECK ADD  CONSTRAINT [FK_REGISTRATION_USER] FOREIGN KEY([username])
REFERENCES [dbo].[UserInfo] ([username])
GO
ALTER TABLE [dbo].[RegistrationRecord] CHECK CONSTRAINT [FK_REGISTRATION_USER]
GO
ALTER TABLE [dbo].[SpamReport]  WITH CHECK ADD  CONSTRAINT [FK_SPAMREPORT_USER1] FOREIGN KEY([reporter])
REFERENCES [dbo].[UserInfo] ([username])
GO
ALTER TABLE [dbo].[SpamReport] CHECK CONSTRAINT [FK_SPAMREPORT_USER1]
GO
ALTER TABLE [dbo].[SpamReport]  WITH CHECK ADD  CONSTRAINT [FK_SPAMREPORT_USER2] FOREIGN KEY([target])
REFERENCES [dbo].[UserInfo] ([username])
GO
ALTER TABLE [dbo].[SpamReport] CHECK CONSTRAINT [FK_SPAMREPORT_USER2]
GO
ALTER TABLE [dbo].[UserChatSecret]  WITH CHECK ADD  CONSTRAINT [FK_UCS_USER1] FOREIGN KEY([username])
REFERENCES [dbo].[UserInfo] ([username])
GO
ALTER TABLE [dbo].[UserChatSecret] CHECK CONSTRAINT [FK_UCS_USER1]
GO
ALTER TABLE [dbo].[UserChatSecret]  WITH CHECK ADD  CONSTRAINT [FK_UCS_USER2] FOREIGN KEY([target])
REFERENCES [dbo].[UserInfo] ([username])
GO
ALTER TABLE [dbo].[UserChatSecret] CHECK CONSTRAINT [FK_UCS_USER2]
GO
ALTER TABLE [dbo].[UserFriend]  WITH CHECK ADD  CONSTRAINT [FK_USERFRIEND_USER1] FOREIGN KEY([username])
REFERENCES [dbo].[UserInfo] ([username])
GO
ALTER TABLE [dbo].[UserFriend] CHECK CONSTRAINT [FK_USERFRIEND_USER1]
GO
ALTER TABLE [dbo].[UserFriend]  WITH CHECK ADD  CONSTRAINT [FK_USERFRIEND_USER2] FOREIGN KEY([friend])
REFERENCES [dbo].[UserInfo] ([username])
GO
ALTER TABLE [dbo].[UserFriend] CHECK CONSTRAINT [FK_USERFRIEND_USER2]
GO
ALTER TABLE [dbo].[UserIdentity]  WITH CHECK ADD  CONSTRAINT [FK_ID_USER] FOREIGN KEY([username])
REFERENCES [dbo].[UserInfo] ([username])
GO
ALTER TABLE [dbo].[UserIdentity] CHECK CONSTRAINT [FK_ID_USER]
GO
ALTER TABLE [dbo].[UserInfo]  WITH CHECK ADD  CONSTRAINT [FK_USERINFO_ACCOUNT] FOREIGN KEY([username])
REFERENCES [dbo].[Account] ([username])
GO
ALTER TABLE [dbo].[UserInfo] CHECK CONSTRAINT [FK_USERINFO_ACCOUNT]
GO
ALTER TABLE [dbo].[FriendChat]  WITH CHECK ADD  CONSTRAINT [FC_VALID] CHECK  (([sender]<>[friend]))
GO
ALTER TABLE [dbo].[FriendChat] CHECK CONSTRAINT [FC_VALID]
GO
ALTER TABLE [dbo].[FriendRequest]  WITH CHECK ADD  CONSTRAINT [FK_CHECK_VALID] CHECK  (([initiator]<>[target]))
GO
ALTER TABLE [dbo].[FriendRequest] CHECK CONSTRAINT [FK_CHECK_VALID]
GO
ALTER TABLE [dbo].[GroupChatMember]  WITH CHECK ADD  CONSTRAINT [FK_MSG_HISTORY_VALID] CHECK  (([last_read_msg]>=[start_history_msg]))
GO
ALTER TABLE [dbo].[GroupChatMember] CHECK CONSTRAINT [FK_MSG_HISTORY_VALID]
GO
ALTER TABLE [dbo].[SpamReport]  WITH CHECK ADD  CONSTRAINT [VALID] CHECK  (([target]<>[reporter]))
GO
ALTER TABLE [dbo].[SpamReport] CHECK CONSTRAINT [VALID]
GO
ALTER TABLE [dbo].[UserFriend]  WITH CHECK ADD  CONSTRAINT [UF_check_valid] CHECK  (([username]<>[friend]))
GO
ALTER TABLE [dbo].[UserFriend] CHECK CONSTRAINT [UF_check_valid]
GO
/****** Object:  StoredProcedure [dbo].[add_member_to_group]    Script Date: 1/6/2024 9:54:48 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[add_member_to_group] 
    @g_id GROUP_ID_TYPE,   
	@username USERNAME_TYPE,
	@joined_date datetime,
	@is_admin bit
AS   
   	insert into GroupChatMember(group_id, username, joined_date, is_admin, start_history_msg, last_read_msg)
   	values(@g_id, @username, @joined_date, @is_admin, 0, 0)
GO
/****** Object:  StoredProcedure [dbo].[add_msg_to_friend]    Script Date: 1/6/2024 9:54:48 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[add_msg_to_friend]
	@sender USERNAME_TYPE,
	@friend USERNAME_TYPE,
	@sent_date datetime,
	@cipher_msg varbinary(MAX)
AS
	declare @id int
	select @id = max(id) from FriendChat
	where (sender = @sender and friend = @friend) or (sender = @friend and friend = @sender)
	if @id is null
	begin
		set @id = 0;
	end
	set @id = @id + 1
   	insert into FriendChat VALUES(@id, @sender, @friend, @sent_date, @cipher_msg)
   	update UserFriend set last_read_msg = last_read_msg + 1 where username = @sender and friend = @friend
GO
/****** Object:  StoredProcedure [dbo].[add_msg_to_group]    Script Date: 1/6/2024 9:54:48 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[add_msg_to_group]
	@group_id GROUP_ID_TYPE,
	@sender USERNAME_TYPE,
	@sent_date datetime,
	@cipher_msg varbinary(MAX)
AS
	declare @id int
	select @id = max(id) from GroupChatMessage where group_id = @group_id
	if @id is null
	begin
		set @id = 0;
	end
	set @id = @id + 1
   	insert into GroupChatMessage VALUES(@id, @group_id, @sender, @sent_date, @cipher_msg)
   	update GroupChatMember set last_read_msg = last_read_msg + 1 where group_id = @group_id and username = @sender
GO
/****** Object:  StoredProcedure [dbo].[searchUser]    Script Date: 1/6/2024 9:54:48 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[searchUser] @username nvarchar(100)
AS
SELECT * FROM UserInfo WHERE username like '%' + @username + '%'
GO
/****** Object:  StoredProcedure [dbo].[update_friend_chat_last_read]    Script Date: 1/6/2024 9:54:48 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[update_friend_chat_last_read]
	@username USERNAME_TYPE,
	@friend USERNAME_TYPE
AS BEGIN
	declare @cur int = 0
	select @cur = max(id) from FriendChat
	where (sender = @username and friend = @friend) or (sender = @friend and friend = @username) 
	if @cur is null
	begin
		set @cur = 0
	end
	update UserFriend set last_read_msg = @cur where username = @username and friend = @friend
END
GO
/****** Object:  StoredProcedure [dbo].[update_group_chat_last_read]    Script Date: 1/6/2024 9:54:48 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[update_group_chat_last_read]
	@username USERNAME_TYPE,
	@group_id GROUP_ID_TYPE
AS BEGIN
	declare @cur int = 0
	select @cur = max(id) from GroupChatMessage where group_id = @group_id
	if @cur is null
	begin
		set @cur = 0
	end
	update GroupChatMember set last_read_msg = @cur where username = @username and group_id = @group_id
END
GO
/****** Object:  StoredProcedure [dbo].[update_identity]    Script Date: 1/6/2024 9:54:48 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[update_identity]
	@username USERNAME_TYPE,
	@public_key varbinary(300)
AS BEGIN
    if not exists(select * from UserIdentity where username = @username) begin
        insert into UserIdentity(username, public_key) values(@username, @public_key)
    end
END
GO
USE [master]
GO
ALTER DATABASE [Java] SET  READ_WRITE 
GO
