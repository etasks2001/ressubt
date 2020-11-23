/*
use master
go
SET ANSI_NULLS on

if exists(select * from sysdatabases where name = 'rst')
 drop database rst

create database rst
go
*/
use rst
go
SET STATISTICS IO ON
go
/*@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*/
if exists (select 1 from sys.objects where name = 'fk_movimento_produto' and type='F')
	alter table movimento drop constraint  fk_movimento_produto
go

if exists (select * from sys.objects where name = 'fk_movimento_codigoDoRemetenteOuDestinatario' and type='F')
	alter table movimento drop constraint  fk_movimento_codigoDoRemetenteOuDestinatario
go

if exists (select 1 from sys.objects where name = 'fk_saldo_produto' and type='F')
	alter table saldo drop constraint  fk_saldo_produto
go

if exists (select * from sys.objects where name = 'fk_participante_contribuinte' and type='F')
	alter table participante drop constraint  fk_participante_contribuinte
go

if exists (select * from sys.objects where name = 'fk_produto_contribuinte' and type='F')
	alter table produto drop constraint  fk_produto_contribuinte
go
/*@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*/
if object_id('contribuinte', 'u') is not null drop table contribuinte;
create table contribuinte(
	sk smallint identity(1,1) not null,
	ano smallint not null,
	mes tinyint not null,
	nome varchar(100) not null,
	cnpj char(14) not null,
	ie varchar(14) not null,
	cod_mun char(7) not null,
	cod_ver char(2) not null,
	cod_fin char(2) not null,
	constraint pk_contribuinte_sk primary key (sk),
	constraint un_contribuinte_cnpj unique (cnpj),
	constraint un_contribuinte_ie unique (ie),
	constraint chk_contribuinte_cnpj check (cnpj like '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'),
	constraint chk_contribuinte_mes check (mes between 1 and 12),
	constraint chk_contribuinte_ano check (ano > 0)
)
go
/*@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*/
if object_id('participante', 'u') is not null drop table participante;
create table participante (
	sk int identity(1,1) not null,
	contribuinte smallint not null,
	nome varchar(100) not null,
	cod_pais char(5) not null,
	cnpj_cpf char(14) null,
	ie varchar(14) not null,
	cod_mun char(7) not null,
	constraint fk_participante_contribuinte foreign key (contribuinte) references contribuinte (sk),
	constraint pk_participante_sk primary key (sk),
)
go

create unique index un_participante_contribuinte_cnpj
	on dbo.participante(contribuinte, cnpj_cpf)
where cnpj_cpf is not null;
/*@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*/
if object_id('produto', 'u') is not null drop table produto;
create table produto(
	sk int identity(1,1) not null,
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
	constraint un_produto_contribuinte_cod_item unique clustered (contribuinte, cod_item),
	constraint chk_produto_aliq_icms check (aliq_icms > 0)
)
go
/*@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*/
if object_id('saldo', 'u') is not null drop table saldo;
create table saldo(
	produto int not null,
	ano smallint not null,
	mes tinyint not null,
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
	constraint idx_saldo unique clustered (produto,mes,ano)
)
go
/*@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*/
if object_id('movimento','u') is not null drop table movimento
create table movimento(
	numeroDeOrdem int identity(1,1) not null,
	produto int not null,
	data datetime not null,
	ano smallint not null,
	mes tinyint not null,
	chaveDoDocumentoFiscalEletronico char(44) not null,
	tipoDoDocumento char(1) null,
	serieDoDocumento tinyint not null,
	numeroDoDocumento int not null,
	codigoDoRemetenteOuDestinatario int not null,
	cfop int not null,
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
	constraint chk_movimento_chaveDoDocumentoFiscalEletronico check (chaveDoDocumentoFiscalEletronico like '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]' or chaveDoDocumentoFiscalEletronico like '[0]'),
	constraint chk_movimento_numeroDoItemNoDocumento check (numeroDoItemNoDocumento > 0),
	constraint chk_movimento_indicadorDoTipoDeOperacao check (indicadorDoTipoDeOperacao in ('0','1')),
	constraint chk_movimento_cfop check (cfop like '[0-9][0-9][0-9][0-9]'),
	constraint chk_movimento_quantidade check (quantidade > 0),
	constraint chk_movimento_saida_codigoDeEnquadramentoLegal check (saida_codigoDeEnquadramentoLegal in ('0','1','2','3','4')),
	constraint chk_movimento_mes check (mes between 1 and 12),
	constraint chk_movimento_ano check (ano > 0)
)
/*@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*/
bulk insert dbo.contribuinte from 'C:\rst\empresa.txt'
with (
	codepage = 'ACP',
	fieldterminator = ';',  
	keepidentity
);

bulk insert dbo.produto from 'c:\rst\produto.txt'
with (
	codepage = 'ACP',
	fieldterminator=';',
	keepidentity
);

bulk insert dbo.participante from 'c:\rst\participante.txt'
with (
	codepage = 'ACP',
	fieldterminator=';',
	keepidentity
);

bulk insert dbo.movimento from 'c:\rst\movimento.txt'
with(
	codepage ='ACP',
	fieldterminator =';',
	keepidentity
);

bulk insert dbo.saldo from 'c:\rst\saldo.txt'
with(
	codepage='ACP',
	fieldterminator=';',
	keepidentity
);

dbcc shrinkdatabase (rst);
go
