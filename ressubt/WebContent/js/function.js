export { criaOptionInSelect, GET, URL_FINALIDADE, URL_UF};
const C_VALUE = "value";
const C_OPTION = "option";
const C_DOT=".";
const GET = "GET";
const URL_FINALIDADE = "/ressubt/control?action=finalidade";
const URL_UF = "/ressubt/control?action=uf";

var criaOptionInSelect=(codigo, descricao, classe)=>{
    let select = document.querySelector(C_DOT+classe);
    
    let option = document.createElement(C_OPTION);

    option.appendChild(document.createTextNode(descricao));
    option.setAttribute(C_VALUE, codigo);
    
    select.appendChild(option);
}
