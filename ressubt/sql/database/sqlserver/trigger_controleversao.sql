use rst
go



CREATE TABLE [dbo].[Trace_Alteracao_Objeto] (
	[Id_Trace_Alteracao_Objeto] [INT] IDENTITY(1,1) NOT NULL,
	[Tp_Evento] [VARCHAR](30) NULL,
	[Dt_Alteracao] [DATETIME] NULL,
	[Nm_Servidor] [VARCHAR](100) NULL,
	[Nm_Login] [VARCHAR](50) NULL,
	[Nm_Database] [VARCHAR](20) NULL,
	[Nm_Objeto] [VARCHAR](50) NULL,
	[Ds_Evento] [XML] NULL
) ON [PRIMARY]








CREATE TRIGGER [trgTrace_Alteracao_Objeto]
ON DATABASE
FOR DDL_DATABASE_LEVEL_EVENTS
AS
BEGIN
    SET NOCOUNT ON
    SET ARITHABORT ON

    DECLARE @Evento XML
    SET @Evento = EVENTDATA()
    
    INSERT INTO [dbo].[Trace_Alteracao_Objeto] ( [Tp_Evento], [Dt_Alteracao], [Nm_Servidor], [Nm_Login], [Nm_Database], [Nm_Objeto], [Ds_Evento] )
    SELECT  @Evento.value('(/EVENT_INSTANCE/EventType/text())[1]','VARCHAR(50)') [Tipo_Evento],
            @Evento.value('(/EVENT_INSTANCE/PostTime/text())[1]','DATETIME') [PostTime],
            @Evento.value('(/EVENT_INSTANCE/ServerName/text())[1]','VARCHAR(50)') [ServerName],
            @Evento.value('(/EVENT_INSTANCE/LoginName/text())[1]','VARCHAR(50)') [LoginName],
            @Evento.value('(/EVENT_INSTANCE/DatabaseName/text())[1]','VARCHAR(50)') [DatabaseName],
            @Evento.value('(/EVENT_INSTANCE/ObjectName/text())[1]','VARCHAR(50)') [ObjectName], 
            @Evento
END









CREATE PROCEDURE [dbo].[stpControle_versao] AS SELECT 'Versão 1.0'

GO
ALTER PROCEDURE [dbo].[stpControle_versao] AS SELECT 'Versão 2.0'

GO
DROP PROCEDURE [dbo].[stpControle_versao]

GO
CREATE FUNCTION [dbo].[fncControle_versao]() RETURNS VARCHAR(10) AS  BEGIN RETURN 'Versão 1.0' END

GO
ALTER FUNCTION [dbo].[fncControle_versao]() RETURNS VARCHAR(10) AS  BEGIN RETURN 'Versão 2.0' END

GO
DROP FUNCTION [dbo].[fncControle_versao]












SELECT [Tp_Evento], [Dt_Alteracao], [Nm_Servidor], [Nm_Login], [Nm_Database], [Nm_Objeto], [Ds_Evento]
FROM [dbo].[Trace_Alteracao_Objeto] WITH(NOLOCK)
ORDER BY [Dt_Alteracao]








