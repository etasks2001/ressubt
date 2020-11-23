use rst 
go
/*@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*/
if object_id ( 'dbo.saldo_inserir_mensal_produto', 'P' ) is not null   
    drop procedure dbo.saldo_inserir_mensal_produto;
go

if object_id ( 'dbo.calcula_movimento', 'P' ) is not null   
    drop procedure dbo.calcula_movimento;  
go

if object_id ( 'dbo.saldo_atualiza_quantidade_entrada_saida', 'P' ) is not null   
    drop procedure dbo.saldo_atualiza_quantidade_entrada_saida;  
go

if object_id ( 'dbo.saldo_gravar_quantidade_final_inicial', 'P' ) is not null   
    drop procedure dbo.saldo_gravar_quantidade_final_inicial;  
go
/*@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*/
create procedure dbo.saldo_inserir_mensal_produto @codigoProduto int, @anoIni smallint, @anoFin smallint
as
/*------------------------------------------------------------------------------------------------------------------------------------*/
declare @produto int
	set @produto = @codigoProduto
/*------------------------------------------------------------------------------------------------------------------------------------*/
declare @anoInicial smallint
declare @anoFinal smallint

declare @ano int
declare @mes int
declare @produtoTotal int
	/*------------------------------------------------------------------------------------------------------------------------------------*/
	set @anoInicial = @anoIni
	set @anoFinal = @anoFin

	set @ano = @anoInicial
	/*------------------------------------------------------------------------------------------------------------------------------------*/
	begin transaction
		while(@ano <= @anoFinal)
		begin
			set @mes = 1
			while(@mes <= 12)
			begin
				/*------------------------------------------------------------------------------------------------------------------------------------*/
				select 
					@produtoTotal = count(dbo.saldo.produto) 
				from 
					dbo.saldo with(nolock) 
				where 
					dbo.saldo.produto = @produto and 
					dbo.saldo.mes = @mes and 
					dbo.saldo.ano = @ano
				/*------------------------------------------------------------------------------------------------------------------------------------*/
				if (@produtoTotal = 0)
					insert into dbo.saldo (dbo.saldo.produto, dbo.saldo.mes, dbo.saldo.ano) values (@produto, @mes, @ano)
				/*------------------------------------------------------------------------------------------------------------------------------------*/
				set @mes = @mes + 1
			end		
			set @ano = @ano + 1	
		end
		set @ano = @anoInicial
	commit transaction
go

/*@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*/
create procedure dbo.calcula_movimento @codigoProduto int
as
/*------------------------------------------------------------------------------------------------------------------------------------*/
declare @produto int
set @produto = @codigoProduto
/*------------------------------------------------------------------------------------------------------------------------------------*/
declare @numeroDeOrdem int
declare @quantidade int
declare @indicadorDoTipoDeOperacao bit
declare @entrada_valorTotalDoICMSST decimal(18,6)
declare @entrada_valorTotalDoICMSProprio decimal(18,6)
declare @saida_codigoDeEnquadramentoLegal char(1)
/*------------------------------------------------------------------------------------------------------------------------------------*/
declare @saldoProprio_ValorTotal decimal(18,6)
declare @saldoProprio_ValorUnitario decimal(18,6)
declare @saldoST_ValorUnitario decimal(18,6)
declare @saldoST_ValorTotal decimal(18,6)
/*------------------------------------------------------------------------------------------------------------------------------------*/
declare @icms_tot_ini_st as decimal(18,6)
declare @icms_tot_ini_proprio as decimal(18,6)
/*------------------------------------------------------------------------------------------------------------------------------------*/
declare @saida_ComercializacaoSubsequente decimal(18,6)
declare @saida_SaidaAConsumidorOuUsuarioFinal decimal(18,6)
declare @saida_FatoGeradorNaoRealizado decimal(18,6)
declare @saida_SaidaOuSaidaSubsequenteComIsencaoOuNaoIncidencia decimal(18,6)
declare @saida_SaidaParaOutroEstado decimal(18,6)
/*------------------------------------------------------------------------------------------------------------------------------------*/
declare @apuracao_ValorRessarcimento decimal (18,6)
declare @apuracao_ICMSCreditoDaOperacaoPropria decimal (18,6)
declare @totalSTItem decimal (18,6)
declare @totalProprioItem decimal (18,6)
/*------------------------------------------------------------------------------------------------------------------------------------*/
declare @saida_Confronto_ICMSEfetivoDaEntrada decimal (18,6)
declare @aliquota decimal (6,2)
/*------------------------------------------------------------------------------------------------------------------------------------*/
declare @saldo_Quantidade int
/*------------------------------------------------------------------------------------------------------------------------------------*/
/*------------------------------------------------------------------------------------------------------------------------------------*/
	begin transaction
		/*------------------------------------------------------------------------------------------------------------------------------------*/
		select 
			@aliquota = dbo.produto.aliq_icms 
		from 
			dbo.produto 
		where 
			dbo.produto.sk = @produto
		/*------------------------------------------------------------------------------------------------------------------------------------*/
		declare 
			movimento_cursor
		cursor local forward_only static 
		for 
		select 
			dbo.movimento.numeroDeOrdem, 
			dbo.movimento.quantidade, 
			dbo.movimento.indicadorDoTipoDeOperacao, 
			dbo.movimento.entrada_valorTotalDoICMSST, 
			dbo.movimento.entrada_valorTotalDoICMSProprio,
			dbo.movimento.saida_codigoDeEnquadramentoLegal
		from 
			dbo.movimento 
		with(nolock) where 
			dbo.movimento.produto = @produto
		order by 
			dbo.movimento.data, 
			dbo.movimento.indicadorDoTipoDeOperacao, 
			dbo.movimento.numeroDoDocumento
		/*------------------------------------------------------------------------------------------------------------------------------------*/
		open movimento_cursor
		/*------------------------------------------------------------------------------------------------------------------------------------*/
		fetch next from 
			movimento_cursor
		into 
			@numeroDeOrdem, 
			@quantidade, 
			@indicadorDoTipoDeOperacao, 
			@entrada_valorTotalDoICMSST, 
			@entrada_valorTotalDoICMSProprio, 
			@saida_codigoDeEnquadramentoLegal
		/*------------------------------------------------------------------------------------------------------------------------------------*/
		select top 1 
			@saldo_Quantidade = dbo.saldo.qtd_ini, 
			@icms_tot_ini_st = dbo.saldo.icms_tot_ini_st, 
			@icms_tot_ini_proprio = dbo.saldo.icms_tot_ini_proprio 
		from 
			dbo.saldo
		with(nolock) where 
			dbo.saldo.produto = @produto 
		order by 
			dbo.saldo.ano, 
			dbo.saldo.mes
		/*------------------------------------------------------------------------------------------------------------------------------------*/
		if (@saldo_Quantidade > 0)
			set @saldoST_ValorUnitario = @icms_tot_ini_st / @saldo_Quantidade
		if (@saldo_Quantidade > 0)
			set @saldoProprio_ValorUnitario = @icms_tot_ini_proprio / @saldo_Quantidade
		while @@FETCH_STATUS = 0
		begin
			if @indicadorDoTipoDeOperacao = 0
				begin
					set @icms_tot_ini_st = @saldo_Quantidade * @saldoST_ValorUnitario
					set @icms_tot_ini_proprio = @saldo_Quantidade * @saldoProprio_ValorUnitario
				
					set @saldo_Quantidade = @saldo_Quantidade + @quantidade 

					if (@saldo_Quantidade > 0)
						set @saldoST_ValorUnitario = @icms_tot_ini_st / @saldo_Quantidade
					if (@saldo_Quantidade > 0)
						set @saldoProprio_ValorUnitario = @icms_tot_ini_proprio / @saldo_Quantidade
				end
			else if @indicadorDoTipoDeOperacao = 1
				begin
					set @saldo_Quantidade = @saldo_Quantidade - @quantidade 
				end
			/*-------------------------------------------------------------------------------------------------------*/
			set @saldoST_ValorTotal = @saldoST_ValorUnitario * @saldo_Quantidade
			set @saldoProprio_ValorTotal = @saldoProprio_ValorUnitario * @saldo_Quantidade
			/*------------------------------------------------------------------------------------------------------------------------------------*/
			set @saida_ComercializacaoSubsequente = 0
			set @saida_SaidaAConsumidorOuUsuarioFinal = 0
			set @saida_FatoGeradorNaoRealizado = 0
			set @saida_SaidaOuSaidaSubsequenteComIsencaoOuNaoIncidencia = 0
			set @saida_SaidaParaOutroEstado = 0
			set @saida_ComercializacaoSubsequente = 0
			set @saida_Confronto_ICMSEfetivoDaEntrada = 0
			set @apuracao_ValorRessarcimento = 0
			set @apuracao_ICMSCreditoDaOperacaoPropria = 0
			set @totalSTItem = @saldoST_ValorUnitario * @quantidade
			set @totalProprioItem = @saldoProprio_ValorUnitario * @quantidade
			/*------------------------------------------------------------------------------------------------------------------------------------*/
			if (@saida_codigoDeEnquadramentoLegal = '0')
				set @saida_ComercializacaoSubsequente = @totalSTItem
		
			if (@saida_codigoDeEnquadramentoLegal = '1')
				set @saida_SaidaAConsumidorOuUsuarioFinal = @totalSTItem

			if (@saida_codigoDeEnquadramentoLegal = '2')
				set @saida_FatoGeradorNaoRealizado = @totalSTItem

			if (@saida_codigoDeEnquadramentoLegal = '3')
				set @saida_SaidaOuSaidaSubsequenteComIsencaoOuNaoIncidencia = @totalSTItem

			if (@saida_codigoDeEnquadramentoLegal = '4')
				begin
					set @saida_SaidaParaOutroEstado = @totalSTItem
					set @saida_Confronto_ICMSEfetivoDaEntrada = @totalProprioItem

					set @apuracao_ValorRessarcimento = (@saida_SaidaParaOutroEstado * @aliquota / 100 ) - (@saida_Confronto_ICMSEfetivoDaEntrada * @aliquota / 100)
					set @apuracao_ICMSCreditoDaOperacaoPropria = (@saida_Confronto_ICMSEfetivoDaEntrada * @aliquota / 100)
				end
			/*-------------------------------------------------------------------------------------------------------*/
			
			set @saldoST_ValorUnitario = isnull(@saldoST_ValorUnitario, 0)
			set @saldoST_ValorTotal = isnull(@saldoST_ValorTotal, 0)
			set @saldoProprio_ValorUnitario = isnull(@saldoProprio_ValorUnitario, 0)
			set @saldoProprio_ValorTotal = isnull(@saldoProprio_ValorTotal, 0)
			set @saida_ComercializacaoSubsequente = isnull(@saida_ComercializacaoSubsequente, 0)

			/*-------------------------------------------------------------------------------------------------------*/
			update 
				dbo.movimento 
			set 
				dbo.movimento.saldoST_Quantidade = @saldo_Quantidade,
				dbo.movimento.saldoST_ValorUnitario = @saldoST_ValorUnitario,
				dbo.movimento.saldoST_ValorTotal = @saldoST_ValorTotal,
				dbo.movimento.saldoProprio_Quantidade = @saldo_Quantidade, 
				dbo.movimento.saldoProprio_ValorUnitario = @saldoProprio_ValorUnitario,
				dbo.movimento.saldoProprio_ValorTotal = @saldoProprio_ValorTotal,
				dbo.movimento.saida_ComercializacaoSubsequente = @saida_ComercializacaoSubsequente,
				dbo.movimento.saida_SaidaAConsumidorOuUsuarioFinal = @saida_SaidaAConsumidorOuUsuarioFinal,
				dbo.movimento.saida_FatoGeradorNaoRealizado = @saida_FatoGeradorNaoRealizado,
				dbo.movimento.saida_SaidaOuSaidaSubsequenteComIsencaoOuNaoIncidencia = @saida_SaidaOuSaidaSubsequenteComIsencaoOuNaoIncidencia,
				dbo.movimento.saida_SaidaParaOutroEstado = @saida_SaidaParaOutroEstado,
				dbo.movimento.saida_Confronto_ICMSEfetivoDaEntrada = @saida_Confronto_ICMSEfetivoDaEntrada,
				dbo.movimento.saida_ValorUnitarioDoICMSSuportado = @saldoST_ValorUnitario,
				dbo.movimento.apuracao_ValorRessarcimento = @apuracao_ValorRessarcimento,
				dbo.movimento.apuracao_ICMSCreditoDaOperacaoPropria = @apuracao_ICMSCreditoDaOperacaoPropria
			where 
				dbo.movimento.numeroDeOrdem = @numeroDeOrdem
			/*-------------------------------------------------------------------------------------------------------*/
			fetch next from 
				movimento_cursor
			into 
				@numeroDeOrdem, 
				@quantidade, 
				@indicadorDoTipoDeOperacao, 
				@entrada_valorTotalDoICMSST, 
				@entrada_valorTotalDoICMSProprio, 
				@saida_codigoDeEnquadramentoLegal
		end
		/*------------------------------------------------------------------------------------------------------------------------------------*/
		close movimento_cursor
		deallocate movimento_cursor
	commit transaction
go
/*@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*/

create procedure dbo.saldo_atualiza_quantidade_entrada_saida @codigoProduto int
as
/*------------------------------------------------------------------------------------------------------------------------------------*/
declare @produto int
	set @produto =  @codigoProduto

declare @ano smallint
declare @mes tinyint
declare @entrada int
declare @saida int

/*------------------------------------------------------------------------------------------------------------------------------------*/
	begin transaction
		update 
			dbo.saldo 
		set 
			dbo.saldo.entrada = 0, 
			dbo.saldo.saida = 0
		where 
			dbo.saldo.produto = @produto
		/*-------------------------------------------------------------------------------------------------------------------*/

		declare 
			movimento_cursor
		cursor local forward_only static 
		for 
		select 
			dbo.movimentototalentradasaida.ano,
			dbo.movimentototalentradasaida.mes,
			dbo.movimentototalentradasaida.entrada,
			dbo.movimentototalentradasaida.saida
		from 
			dbo.movimentototalentradasaida
		with(nolock) where 
			dbo.movimentototalentradasaida.produto = @produto
		/*------------------------------------------------------------------------------------------------------------------------------------*/
		open movimento_cursor
		/*------------------------------------------------------------------------------------------------------------------------------------*/
		fetch next from 
			movimento_cursor
		into 
			@ano, 
			@mes, 
			@entrada, 
			@saida

		while @@FETCH_STATUS = 0
		begin
			update 
				dbo.saldo 
			set
				dbo.saldo.saida = @saida,
				dbo.saldo.entrada = @entrada
			where
				dbo.saldo.produto = @produto and 
				dbo.saldo.mes = @mes and
				dbo.saldo.ano = @ano
				/*------------------------------------------------------------------------------------------------------------------------------------*/

			fetch next from 
				movimento_cursor
			into 
				@ano, 
				@mes, 
				@entrada, 
				@saida
		end
	/*------------------------------------------------------------------------------------------------------------------------------------*/
	close movimento_cursor
	deallocate movimento_cursor
commit transaction
	
go

/*@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*/

create procedure dbo.saldo_gravar_quantidade_final_inicial @codigoProduto int
as
declare @produto int
	set @produto = @codigoProduto
/*------------------------------------------------------------------------------------------------------------------------------------*/
declare @ano smallint
declare @mes tinyint
declare @qtd_ini int
declare @entrada int
declare @saida int
declare @qtd_fin int

declare @icms_tot_fim_st decimal (18,6)
declare @icms_tot_fim_proprio decimal (18,6)

declare @icms_tot_ini_st decimal (18,6)
declare @icms_tot_ini_proprio decimal (18,6)
/*------------------------------------------------------------------------------------------------------------------------------------*/
	begin transaction
		/*------------------------------------------------------------------------------------------------------*/
		declare 
			saldo_cursor
		cursor local forward_only static for
			select 
				dbo.saldo.ano,
				dbo.saldo.mes,
				dbo.saldo.qtd_ini,
				dbo.saldo.entrada,
				dbo.saldo.saida,
				dbo.saldo.icms_tot_ini_st,
				dbo.saldo.icms_tot_ini_proprio
			from 
				dbo.saldo 
			where 
				dbo.saldo.produto = @produto
			order by
				dbo.saldo.ano, 
				dbo.saldo.mes

		open saldo_cursor
		/*------------------------------------------------------------------------------------------------------*/
		fetch next from 
			saldo_cursor 
		into 
			@ano, 
			@mes, 
			@qtd_ini, 
			@entrada, 
			@saida, 
			@icms_tot_ini_st, 
			@icms_tot_ini_proprio	
		/*------------------------------------------------------------------------------------------------------*/
		while @@FETCH_STATUS = 0
			begin
				set @qtd_fin = @qtd_ini + @entrada - @saida

				/*-------------------------------------------------------------------------------------*/
				/*-------------------------------------------------------------------------------------*/
				select top 1 
					@icms_tot_fim_st = dbo.movimento.saldoST_ValorTotal,
					@icms_tot_fim_proprio = dbo.movimento.saldoProprio_ValorTotal
				from 
					dbo.movimento 
				where 
					dbo.movimento.produto = @produto and
					dbo.movimento.ano = @ano and 
					dbo.movimento.mes = @mes
				order by 
					dbo.movimento.data desc,
					dbo.movimento.indicadorDoTipoDeOperacao desc,
					dbo.movimento.numeroDoDocumento desc

				/*-------------------------------------------------------------------------------------*/
				/*-------------------------------------------------------------------------------------*/
				set @icms_tot_ini_st = isnull(@icms_tot_ini_st, 0)
				set @icms_tot_fim_st = isnull(@icms_tot_fim_st, 0)
				set @icms_tot_fim_proprio = isnull(@icms_tot_fim_proprio, 0)
				set @icms_tot_ini_proprio = isnull(@icms_tot_ini_proprio, 0)
				/*-------------------------------------------------------------------------------------*/
				update dbo.saldo set 					
					dbo.saldo.qtd_ini = @qtd_ini,
					dbo.saldo.qtd_fim = @qtd_fin,

					dbo.saldo.icms_tot_ini_st = @icms_tot_ini_st,
					dbo.saldo.icms_tot_ini_proprio = @icms_tot_ini_proprio, 

					dbo.saldo.icms_tot_fim_st = @icms_tot_fim_st,
					dbo.saldo.icms_tot_fim_proprio = @icms_tot_fim_proprio
				where 
					dbo.saldo.produto = @produto and
					dbo.saldo.ano = @ano and 
					dbo.saldo.mes = @mes
				/*-------------------------------------------------------------------------------------*/
				/*-------------------------------------------------------------------------------------*/
				fetch next from 
					saldo_cursor
				into 
					@ano, 
					@mes, 
					@qtd_ini, 
					@entrada, 
					@saida, 
					@icms_tot_ini_st, 
					@icms_tot_ini_proprio
				/*------------------------------------------------------------------------------------------------------*/
				set @qtd_ini = @qtd_fin
				set @icms_tot_ini_proprio = @icms_tot_fim_proprio
				set @icms_tot_ini_st = @icms_tot_fim_st
			end
		close saldo_cursor
		deallocate saldo_cursor
	commit transaction
go
/*@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*/



