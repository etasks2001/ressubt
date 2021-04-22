import { validarDescricao } from "../../js/cadastro/validarDescricao.js";
import { validarCodigoFinalidade } from "../../js/cadastro/validarCodigoFinalidade.js";
//import { validarCPF } from "../../js/cadastro/validarCPF.js";
import { validarNome } from "../../js/cadastro/validarNome.js";
import { validarCNPJ } from "../../js/cadastro/validarCNPJ.js";
import { validarInscricaoEstadual } from "../../js/cadastro/validarInscricaoEstadual.js";
import { validarVersaoDoLeiaute } from "../../js/cadastro/validarVersaoDoLeiaute.js";
import { validarCNPJCPF } from "../../js/cadastro/validarCNPJCPF.js";
import { validarCodigoProduto } from "../../js/cadastro/validarCodigoProduto.js";
import { validarCodigoDeBarras } from "../../js/cadastro/validarCodigoDeBarras.js";
import { validarUnidade } from "../../js/cadastro/validarUnidade.js";
import { validarNCM } from "../../js/cadastro/validarNCM.js";
import { validarAliquotaICMS } from "../../js/cadastro/validarAliquotaICMS.js";
import { validarCest } from "../../js/cadastro/validarCest.js";

export const validadoresEspecificos = {
    descricao: (input) => validarDescricao(input),
    codigoFinalidade: (input) => validarCodigoFinalidade(input),
    //cpf: (input) => validarCPF(input),
    nome: (input) => validarNome(input),
    cnpj: (input) => validarCNPJ(input),
    inscricaoestadual: (input) => validarInscricaoEstadual(input),
    versaodoleiaute: (input) => validarVersaoDoLeiaute(input),
    cnpjcpf: (input) => validarCNPJCPF(input),
    codigoproduto: (input) => validarCodigoProduto(input),
    codigodebarras: (input) => validarCodigoDeBarras(input),
    unidade: (input) => validarUnidade(input),
    ncm: (input) => validarNCM(input),
    aliquotaICMS: (input) => validarAliquotaICMS(input),
    cest: (input) => validarCest(input),
};
