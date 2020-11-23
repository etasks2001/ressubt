use rst 
go
SET STATISTICS IO ON
GO

declare @start datetime = getdate()
declare @produto int = 728

execute dbo.saldo_inserir_mensal_produto @produto, 2015, 2019
execute dbo.calcula_movimento @produto
execute dbo.saldo_atualiza_quantidade_entrada_saida @produto
execute dbo.saldo_gravar_quantidade_final_inicial @produto

select DATEDIFF(MILLISECOND, @start, GETDATE())

select * from saldo where produto = @produto order by ano, mes 
select 
	dbo.movimento.indicadorDoTipoDeOperacao,
	dbo.movimento.ano,
	dbo.movimento.mes,
	dbo.movimento.quantidade,
	dbo.movimento.saldoST_Quantidade,
	dbo.movimento.saldoST_ValorUnitario,
	dbo.movimento.saldoST_ValorTotal,
	dbo.movimento.saldoProprio_Quantidade,
	dbo.movimento.saldoProprio_ValorUnitario,
	dbo.movimento.saldoProprio_ValorTotal
from 
	dbo.movimento with(nolock)

where
	dbo.movimento.produto = @produto

order by 	
	dbo.movimento.data, 
	dbo.movimento.indicadorDoTipoDeOperacao, 
	dbo.movimento.numeroDoDocumento
