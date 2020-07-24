USE [ecology]
GO
/****** Object:  Table [dbo].[authentication_token]    Script Date: 2020/3/16 10:22:09 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[authentication_token](
	[appName] [varchar](100) NULL,
	[appKey] [varchar](30) NULL,
	[appSecret] [varchar](100) NULL
) ON [PRIMARY]
GO
INSERT [dbo].[authentication_token] ([appName], [appKey], [appSecret]) VALUES (N'顺丰', N'26199203', N'zxxjz216rvrCicVgy2m9')
GO
INSERT [dbo].[authentication_token] ([appName], [appKey], [appSecret]) VALUES (N'ecology', N'21996072', N'SDxjzllwijf0nz5zxczx')
GO
INSERT [dbo].[authentication_token] ([appName], [appKey], [appSecret]) VALUES (N'filebim', N'26131499', N'SHxbjsob674jsadFsddf')
GO
INSERT [dbo].[authentication_token] ([appName], [appKey], [appSecret]) VALUES (N'4d', N'24893883', N'Fdxbjsafsadweq124ddf')
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'应用名' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'authentication_token', @level2type=N'COLUMN',@level2name=N'appName'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'应用id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'authentication_token', @level2type=N'COLUMN',@level2name=N'appKey'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'应用secret' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'authentication_token', @level2type=N'COLUMN',@level2name=N'appSecret'
GO
