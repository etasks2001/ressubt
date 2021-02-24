select * from information_schema.table_constraints;

DO $$
declare r record;
begin
	raise info '%','----------------------------------------------------------------------------------';
	for r in (select  constraint_name, table_name from  information_schema.table_constraints where constraint_type = 'FOREIGN KEY') loop
	execute CONCAT('ALTER TABLE "' || r.table_name || '" DROP CONSTRAINT '||r.constraint_name);
	raise info '%','dropping '||r.constraint_name;
	end loop;
	raise info '%','----------------------------------------------------------------------------------';
end;
$$;
drop table if exists contribuinte;
drop table if exists participante;
drop table if exists produto;
drop table if exists saldo;
drop table if exists movimento;
/*@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*/
create table contribuinte(
	sk serial,
	ano smallint not null,
	mes smallint not null,
	nome varchar(100) not null,
	cnpj char(14) not null,
	ie varchar(14) not null,
	cod_mun char(7) not null,
	cod_ver char(2) not null,
	cod_fin char(2) not null,
	primary key (sk),
	unique (cnpj),
	unique (ie),
	constraint chk_contribuinte_cnpj check (cnpj ~ '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'),
	constraint chk_contribuinte_mes check (mes >=1 and mes <= 12),
	constraint chk_contribuinte_ano check (ano > 0)
);
/*@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*/

create table participante (
	sk serial,
	contribuinte smallint not null,
	nome varchar(100) not null,
	cod_pais char(5) not null,
	cnpj_cpf char(14) null,
	ie varchar(14) null,
	cod_mun char(7) not null,
	constraint fk_participante_contribuinte foreign key (contribuinte) references contribuinte (sk),
	constraint pk_participante_sk primary key (sk),
	unique (contribuinte, cnpj_cpf)
);
create unique index un_participante_contribuinte_cnpj on participante (contribuinte, cnpj_cpf) where cnpj_cpf is null;
/*@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*/
create table produto(
	sk serial,
	contribuinte smallint not null,
	cod_item varchar(60) not null,
	descr_item varchar(200) not null,
	cod_barra varchar(60),
	unid_inv varchar(6) not null,
	cod_ncm char(8) not null,
	aliq_icms decimal(6,2) not null,
	cest char(7),
	constraint fk_produto_contribuinte foreign key (contribuinte) references contribuinte (sk),
	constraint pk_produto_sk primary key (sk),
	unique (contribuinte, cod_item),
	constraint chk_produto_aliq_icms check (aliq_icms > 0)
);

/*@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*/
create table saldo(
	produto int not null,
	ano smallint not null,
	mes smallint not null,
	qtd_ini int default 0 not null,
	icms_tot_ini_st decimal(18,6) default 0 not null,
	icms_tot_ini_proprio decimal(18,6) default 0 not null,
	qtd_fim int default 0 not null, 
	icms_tot_fim_st decimal(18,6) default 0 not null,
	icms_tot_fim_proprio decimal(18,6) default 0 not null,
	fixo char(1) not null default 'N',
	entrada int not null default 0,
	saida int not null default 0,
	constraint pk_saldo_produto_periodo primary key (produto, mes, ano),
	constraint fk_saldo_produto foreign key (produto) references produto (sk),
	constraint chk_saldo_qtd_ini check (qtd_ini >= 0),
	constraint chk_saldo_icms_tot_ini_st check (icms_tot_ini_st >= 0),
	constraint chk_saldo_icms_tot_ini_proprio check (icms_tot_ini_proprio >= 0),
	constraint chk_saldo_qtd_fim check (qtd_fim >= 0),
	constraint chk_saldo_icms_tot_fim_st check (icms_tot_fim_st >= 0),
	constraint chk_saldo_icms_tot_fim_proprio check (icms_tot_fim_proprio >= 0),
	constraint chk_saldo_fixo check (fixo in ('S','N')),
	constraint chk_saldo_mes check (mes between 1 and 12),
	constraint chk_saldo_ano check (ano > 0),
	unique (produto,mes,ano)
);
/*@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*/
create table movimento(
	numeroDeOrdem serial,
	produto int not null,
	data timestamp not null,
	ano smallint not null,
	mes smallint not null,
	chaveDoDocumentoFiscalEletronico char(44) not null,
	tipoDoDocumento char(1) null,
	serieDoDocumento smallint not null,
	numeroDoDocumento int not null,
	codigoDoRemetenteOuDestinatario int not null,
	cfop varchar(4) not null,
	numeroDoItemNoDocumento int not null default 0,

	indicadorDoTipoDeOperacao char(1) not null,

	quantidade int not null default 0,

	entrada_valorTotalDoICMSST decimal(18,6) not null default 0,
	entrada_valorTotalDoICMSProprio decimal(18,6) not null default 0,

	saida_ValorUnitarioDoICMSSuportado decimal(18,6) not null default 0,
	saida_SaidaAConsumidorOuUsuarioFinal decimal(18,6) not null default 0,
	saida_FatoGeradorNaoRealizado decimal(18,6) not null default 0,
	saida_SaidaOuSaidaSubsequenteComIsencaoOuNaoIncidencia decimal(18,6) not null default 0,
	saida_SaidaParaOutroEstado decimal(18,6) not null default 0,
	saida_ComercializacaoSubsequente decimal(18,6) not null default 0,
	
	saida_Confronto_ICMSEfetivoNaSaida decimal(18,6) not null default 0,
	saida_Confronto_ICMSEfetivoDaEntrada decimal(18,6) not null default 0,
	
	saida_codigoDeEnquadramentoLegal char(1),

	saldoST_Quantidade int not null default 0,
	saldoST_ValorUnitario decimal(18,6) not null default 0,
	saldoST_ValorTotal decimal(18,6) not null default 0,

	saldoProprio_Quantidade int not null default 0,
	saldoProprio_ValorUnitario decimal(18,6) not null default 0,
	saldoProprio_ValorTotal decimal(18,6) not null default 0,

	apuracao_ValorRessarcimento decimal(18,6) not null default 0,
	apuracao_ValorComplemento decimal(18,6) not null default 0,
	apuracao_ICMSCreditoDaOperacaoPropria  decimal(18,6) not null default 0,
	constraint pk_movimento_numeroDeOrdem primary key (numeroDeOrdem),
	constraint fk_movimento_produto foreign key (produto) references produto(sk),
	constraint fk_movimento_codigoDoRemetenteOuDestinatario foreign key (codigoDoRemetenteOuDestinatario) references participante (sk),
	constraint chk_movimento_chaveDoDocumentoFiscalEletronico check (chaveDoDocumentoFiscalEletronico ~ '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]' or chaveDoDocumentoFiscalEletronico ~ '[0]'),
	constraint chk_movimento_numeroDoItemNoDocumento check (numeroDoItemNoDocumento > 0),
	constraint chk_movimento_indicadorDoTipoDeOperacao check (indicadorDoTipoDeOperacao in ('0','1')),
	constraint chk_movimento_cfop check (cfop ~ '[0-9][0-9][0-9][0-9]'),
	constraint chk_movimento_quantidade check (quantidade > 0),
	constraint chk_movimento_saida_codigoDeEnquadramentoLegal check (saida_codigoDeEnquadramentoLegal in ('0','1','2','3','4')),
	constraint chk_movimento_mes check (mes >= 1 and mes <= 12),
	constraint chk_movimento_ano check (ano > 0)
);
/*@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*/

SET client_encoding = 'UTF8';

/*first line must have fields with no content*/
COPY contribuinte (sk,ano,mes,nome,cnpj,ie,cod_mun,cod_ver,cod_fin) 
	FROM 'C:/rst/database/data/empresa.txt' WITH (FORMAT csv, HEADER true, DELIMITER ';');

COPY produto (sk,contribuinte,cod_item,descr_item,cod_barra,unid_inv,cod_ncm,aliq_icms,cest) 
	FROM 'C:/rst/database/data/produto.txt' WITH (FORMAT csv, HEADER true, DELIMITER ';');

COPY participante (sk,contribuinte,nome,cod_pais,cnpj_cpf,ie,cod_mun) 
	FROM 'C:/rst/database/data/participante.txt' WITH (FORMAT csv, HEADER true, DELIMITER ';');

COPY movimento (numeroDeOrdem,produto,data,ano,mes,chaveDoDocumentoFiscalEletronico,tipoDoDocumento,serieDoDocumento,numeroDoDocumento,codigoDoRemetenteOuDestinatario,cfop,numeroDoItemNoDocumento,indicadorDoTipoDeOperacao,quantidade,entrada_valorTotalDoICMSST,entrada_valorTotalDoICMSProprio,saida_ValorUnitarioDoICMSSuportado,saida_SaidaAConsumidorOuUsuarioFinal,saida_FatoGeradorNaoRealizado,saida_SaidaOuSaidaSubsequenteComIsencaoOuNaoIncidencia,saida_SaidaParaOutroEstado,saida_ComercializacaoSubsequente,saida_Confronto_ICMSEfetivoNaSaida,saida_Confronto_ICMSEfetivoDaEntrada,saida_codigoDeEnquadramentoLegal,saldoST_Quantidade,saldoST_ValorUnitario,saldoST_ValorTotal,saldoProprio_Quantidade,saldoProprio_ValorUnitario,saldoProprio_ValorTotal,apuracao_ValorRessarcimento,apuracao_ValorComplemento,apuracao_ICMSCreditoDaOperacaoPropria) 
	FROM 'C:/rst/database/data/movimento.txt' WITH (FORMAT csv, HEADER true, DELIMITER ';');

COPY saldo (produto,ano,mes,qtd_ini,icms_tot_ini_st,icms_tot_ini_proprio,qtd_fim, icms_tot_fim_st,icms_tot_fim_proprio,fixo,entrada,saida) 
	FROM 'C:/rst/database/data/saldo.txt' WITH (FORMAT csv, HEADER true, DELIMITER ';');




select * from contribuinte;
select * from produto;
select * from participante;
select * from movimento;
select * from saldo;