use rst
go
/*@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*/
if object_id ('dbo.movimentototalentradasaida', 'view') is not null
   drop view dbo.movimentototalentradasaida ;
go
/*@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*/
create view dbo.movimentototalentradasaida
with schemabinding
as  
			select 
				dbo.movimento.produto, 
				dbo.movimento.mes, 
				dbo.movimento.ano,
				sum(iif(dbo.movimento.indicadorDoTipoDeOperacao = 0, dbo.movimento.quantidade,0)) entrada,
				sum(iif(dbo.movimento.indicadorDoTipoDeOperacao = 1, dbo.movimento.quantidade,0)) saida,
				count_big(*) as count
			from 
				dbo.movimento
			--where
			--	dbo.movimento.produto = 5			
			group by
				dbo.movimento.produto, 
				dbo.movimento.mes,
				dbo.movimento.ano

go
create unique clustered index idx_movimentototalentradasaida
   on dbo.movimentototalentradasaida (produto, mes, ano);
go
/*@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*/

SET STATISTICS IO ON
GO


declare @start datetime

	set @start = getdate()

select * from dbo.movimentototalentradasaida where dbo.movimentototalentradasaida.produto = 5


select DATEDIFF(MILLISECOND, @start,GETDATE())


