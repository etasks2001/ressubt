import { tiposDeInputs } from "../../js/cadastro/tiposDeInputs.js";

import { BAD_INPUT, CUSTOM_ERROR, PATTERN_MISMATCH, RANGE_OVERFLOW, RANGE_UNDERFLOW, STEP_MISMATCH, TOO_LONG, TOO_SHORT, TYPE_MISMATCH, VALID, VALUE_MISSING } from "../../js/cadastro/validityStates.js";

export const errorMessages = {
    [tiposDeInputs.DESCRICAO]: {
        [TOO_SHORT]: "Descrição está em branco",
        [VALUE_MISSING]: "Descrição está em branco",
    },

    [tiposDeInputs.CODIGO_FINALIDADE]: {
        [CUSTOM_ERROR]: "Quantidade de números tem que ser 2",
        [TOO_SHORT]: "Código da Finalidade está em branco",
        [VALUE_MISSING]: "Código da Finalidade está em branco",
        [PATTERN_MISMATCH]: "Somente números",
    },
    [tiposDeInputs.CPF]: {
        [TOO_SHORT]: "O CPF está em branco",
        [CUSTOM_ERROR]: "CPF é inválido",
    },
    [tiposDeInputs.NOME]: {
        [TOO_SHORT]: "Nome está em branco",
        [VALUE_MISSING]: "Nome está em branco",
    },
    [tiposDeInputs.CNPJ]: {
        [CUSTOM_ERROR]: "CNPJ tem que ter 14 números",
        [TOO_SHORT]: "CNPJ está em branco",
        [VALUE_MISSING]: "CNPJ está em branco",
    },
    [tiposDeInputs.INSCRICAO_ESTADUAL]: {
        [CUSTOM_ERROR]: "Quantidade de números de 5 a 14",
        [TOO_SHORT]: "Inscrição Estadual está em branco",
        [VALUE_MISSING]: "Inscrição Estadual está em branco",
    },
    [tiposDeInputs.VERSAO_DO_LEIAUTE]: {
        [CUSTOM_ERROR]: "Quantidade de números tem que ser 2",
        [TOO_SHORT]: "Versão do leiaute está em branco",
        [VALUE_MISSING]: "Versão do leiaute está em branco",
    },
    [tiposDeInputs.CNPJ_CPF]: {
        [CUSTOM_ERROR]: "Quantidade de números tem que ser 11 ou 14",
        [TOO_SHORT]: "CNPJ/CPF está em branco",
        [VALUE_MISSING]: "CNPJ/CPF está em branco",
    },
    [tiposDeInputs.CODIGO_PRODUTO]: {
        [TOO_SHORT]: "Código do Produto está em branco",
        [VALUE_MISSING]: "Código do Produto está em branco",
    },
    [tiposDeInputs.CODIGO_DE_BARRAS]: {
        [TOO_SHORT]: "Código de Barras está em branco",
        [VALUE_MISSING]: "Código de Barras está em branco",
    },
    [tiposDeInputs.UNIDADE]: {
        [TOO_SHORT]: "Unidade está em branco",
        [VALUE_MISSING]: "Unidade está em branco",
    },
    [tiposDeInputs.NCM]: {
        [CUSTOM_ERROR]: "NCM somente com 8 números",
        [TOO_SHORT]: "NCM está em branco",
        [VALUE_MISSING]: "NCM está em branco",
    },

    [tiposDeInputs.ALIQUOTA_ICMS]: {
        [CUSTOM_ERROR]: "Alíquota do ICMS não pode ser 0",
        [VALUE_MISSING]: "Alíquota do ICMS não pode ficar em branco",
    },
    [tiposDeInputs.CEST]: {
        [CUSTOM_ERROR]: "Quantidade de números tem que ser 7",
        [TOO_SHORT]: "CEST está em branco",
        [VALUE_MISSING]: "CEST está em branco",
    },
};
