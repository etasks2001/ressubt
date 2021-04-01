package com.ressubt.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Movimento implements Model {
    private Integer numeroDeOrdem;
    private Integer produto;
    private LocalDateTime data;
    private Integer ano;
    private Integer mes;
    private String chaveDoDocumentoFiscalEletronico;
    private String tipoDoDocumento;
    private Integer serieDoDocumento;
    private Integer numeroDoDocumento;
    private Integer codigoDoRemetenteOuDestinatario;
    private String cfop;
    private Integer numeroDoItemNoDocumento;
    private String indicadorDoTipoDeOperacao;
    private Integer quantidade;

    private BigDecimal entrada_valorTotalDoICMSST;
    private BigDecimal entrada_valorTotalDoICMSProprio;

    private BigDecimal saida_ValorUnitarioDoICMSSuportado;
    private BigDecimal saida_SaidaAConsumidorOuUsuarioFinal;
    private BigDecimal saida_FatoGeradorNaoRealizado;

    private BigDecimal saida_SaidaOuSaidaSubsequenteComIsencaoOuNaoIncidencia;

    private BigDecimal saida_SaidaParaOutroEstado;

    private BigDecimal saida_ComercializacaoSubsequente;

    private BigDecimal saida_Confronto_ICMSEfetivoNaSaida;

    private BigDecimal saida_Confronto_ICMSEfetivoDaEntrada;

    private String saida_codigoDeEnquadramentoLegal;

    private Integer saldoST_Quantidade;

    private BigDecimal saldoST_ValorUnitario;

    private BigDecimal saldoST_ValorTotal;

    private Integer saldoProprio_Quantidade;

    private BigDecimal saldoProprio_ValorUnitario;

    private BigDecimal saldoProprio_ValorTotal;

    private BigDecimal apuracao_ValorRessarcimento;

    private BigDecimal apuracao_ValorComplemento;

    private BigDecimal apuracao_ICMSCreditoDaOperacaoPropria;

    public Movimento(Integer numeroDeOrdem, Integer produto, LocalDateTime data, Integer ano, Integer mes, String chaveDoDocumentoFiscalEletronico, String tipoDoDocumento, Integer serieDoDocumento,
	    Integer numeroDoDocumento, Integer codigoDoRemetenteOuDestinatario, String cfop, Integer numeroDoItemNoDocumento, String indicadorDoTipoDeOperacao, Integer quantidade,
	    BigDecimal entrada_valorTotalDoICMSST, BigDecimal entrada_valorTotalDoICMSProprio, BigDecimal saida_ValorUnitarioDoICMSSuportado, BigDecimal saida_SaidaAConsumidorOuUsuarioFinal,
	    BigDecimal saida_FatoGeradorNaoRealizado, BigDecimal saida_SaidaOuSaidaSubsequenteComIsencaoOuNaoIncidencia, BigDecimal saida_SaidaParaOutroEstado,
	    BigDecimal saida_ComercializacaoSubsequente, BigDecimal saida_Confronto_ICMSEfetivoNaSaida, BigDecimal saida_Confronto_ICMSEfetivoDaEntrada, String saida_codigoDeEnquadramentoLegal,
	    Integer saldoST_Quantidade, BigDecimal saldoST_ValorUnitario, BigDecimal saldoST_ValorTotal, Integer saldoProprio_Quantidade, BigDecimal saldoProprio_ValorUnitario,
	    BigDecimal saldoProprio_ValorTotal, BigDecimal apuracao_ValorRessarcimento, BigDecimal apuracao_ValorComplemento, BigDecimal apuracao_ICMSCreditoDaOperacaoPropria) {
	super();
	this.numeroDeOrdem = numeroDeOrdem;
	this.produto = produto;
	this.data = data;
	this.ano = ano;
	this.mes = mes;
	this.chaveDoDocumentoFiscalEletronico = chaveDoDocumentoFiscalEletronico;
	this.tipoDoDocumento = tipoDoDocumento;
	this.serieDoDocumento = serieDoDocumento;
	this.numeroDoDocumento = numeroDoDocumento;
	this.codigoDoRemetenteOuDestinatario = codigoDoRemetenteOuDestinatario;
	this.cfop = cfop;
	this.numeroDoItemNoDocumento = numeroDoItemNoDocumento;
	this.indicadorDoTipoDeOperacao = indicadorDoTipoDeOperacao;
	this.quantidade = quantidade;
	this.entrada_valorTotalDoICMSST = entrada_valorTotalDoICMSST;
	this.entrada_valorTotalDoICMSProprio = entrada_valorTotalDoICMSProprio;
	this.saida_ValorUnitarioDoICMSSuportado = saida_ValorUnitarioDoICMSSuportado;
	this.saida_SaidaAConsumidorOuUsuarioFinal = saida_SaidaAConsumidorOuUsuarioFinal;
	this.saida_FatoGeradorNaoRealizado = saida_FatoGeradorNaoRealizado;
	this.saida_SaidaOuSaidaSubsequenteComIsencaoOuNaoIncidencia = saida_SaidaOuSaidaSubsequenteComIsencaoOuNaoIncidencia;
	this.saida_SaidaParaOutroEstado = saida_SaidaParaOutroEstado;
	this.saida_ComercializacaoSubsequente = saida_ComercializacaoSubsequente;
	this.saida_Confronto_ICMSEfetivoNaSaida = saida_Confronto_ICMSEfetivoNaSaida;
	this.saida_Confronto_ICMSEfetivoDaEntrada = saida_Confronto_ICMSEfetivoDaEntrada;
	this.saida_codigoDeEnquadramentoLegal = saida_codigoDeEnquadramentoLegal;
	this.saldoST_Quantidade = saldoST_Quantidade;
	this.saldoST_ValorUnitario = saldoST_ValorUnitario;
	this.saldoST_ValorTotal = saldoST_ValorTotal;
	this.saldoProprio_Quantidade = saldoProprio_Quantidade;
	this.saldoProprio_ValorUnitario = saldoProprio_ValorUnitario;
	this.saldoProprio_ValorTotal = saldoProprio_ValorTotal;
	this.apuracao_ValorRessarcimento = apuracao_ValorRessarcimento;
	this.apuracao_ValorComplemento = apuracao_ValorComplemento;
	this.apuracao_ICMSCreditoDaOperacaoPropria = apuracao_ICMSCreditoDaOperacaoPropria;
    }

    public Integer getNumeroDeOrdem() {
	return numeroDeOrdem;
    }

    public Integer getProduto() {
	return produto;
    }

    public LocalDateTime getData() {
	return data;
    }

    public Integer getAno() {
	return ano;
    }

    public Integer getMes() {
	return mes;
    }

    public String getChaveDoDocumentoFiscalEletronico() {
	return chaveDoDocumentoFiscalEletronico;
    }

    public String getTipoDoDocumento() {
	return tipoDoDocumento;
    }

    public Integer getSerieDoDocumento() {
	return serieDoDocumento;
    }

    public Integer getNumeroDoDocumento() {
	return numeroDoDocumento;
    }

    public Integer getCodigoDoRemetenteOuDestinatario() {
	return codigoDoRemetenteOuDestinatario;
    }

    public String getCfop() {
	return cfop;
    }

    public Integer getNumeroDoItemNoDocumento() {
	return numeroDoItemNoDocumento;
    }

    public String getIndicadorDoTipoDeOperacao() {
	return indicadorDoTipoDeOperacao;
    }

    public Integer getQuantidade() {
	return quantidade;
    }

    public BigDecimal getEntrada_valorTotalDoICMSST() {
	return entrada_valorTotalDoICMSST;
    }

    public BigDecimal getEntrada_valorTotalDoICMSProprio() {
	return entrada_valorTotalDoICMSProprio;
    }

    public BigDecimal getSaida_ValorUnitarioDoICMSSuportado() {
	return saida_ValorUnitarioDoICMSSuportado;
    }

    public BigDecimal getSaida_SaidaAConsumidorOuUsuarioFinal() {
	return saida_SaidaAConsumidorOuUsuarioFinal;
    }

    public BigDecimal getSaida_FatoGeradorNaoRealizado() {
	return saida_FatoGeradorNaoRealizado;
    }

    public BigDecimal getSaida_SaidaOuSaidaSubsequenteComIsencaoOuNaoIncidencia() {
	return saida_SaidaOuSaidaSubsequenteComIsencaoOuNaoIncidencia;
    }

    public BigDecimal getSaida_SaidaParaOutroEstado() {
	return saida_SaidaParaOutroEstado;
    }

    public BigDecimal getSaida_ComercializacaoSubsequente() {
	return saida_ComercializacaoSubsequente;
    }

    public BigDecimal getSaida_Confronto_ICMSEfetivoNaSaida() {
	return saida_Confronto_ICMSEfetivoNaSaida;
    }

    public BigDecimal getSaida_Confronto_ICMSEfetivoDaEntrada() {
	return saida_Confronto_ICMSEfetivoDaEntrada;
    }

    public String getSaida_codigoDeEnquadramentoLegal() {
	return saida_codigoDeEnquadramentoLegal;
    }

    public Integer getSaldoST_Quantidade() {
	return saldoST_Quantidade;
    }

    public BigDecimal getSaldoST_ValorUnitario() {
	return saldoST_ValorUnitario;
    }

    public BigDecimal getSaldoST_ValorTotal() {
	return saldoST_ValorTotal;
    }

    public Integer getSaldoProprio_Quantidade() {
	return saldoProprio_Quantidade;
    }

    public BigDecimal getSaldoProprio_ValorUnitario() {
	return saldoProprio_ValorUnitario;
    }

    public BigDecimal getSaldoProprio_ValorTotal() {
	return saldoProprio_ValorTotal;
    }

    public BigDecimal getApuracao_ValorRessarcimento() {
	return apuracao_ValorRessarcimento;
    }

    public BigDecimal getApuracao_ValorComplemento() {
	return apuracao_ValorComplemento;
    }

    public BigDecimal getApuracao_ICMSCreditoDaOperacaoPropria() {
	return apuracao_ICMSCreditoDaOperacaoPropria;
    }

}
