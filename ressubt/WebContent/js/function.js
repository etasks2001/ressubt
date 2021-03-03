export { populateHtmlSelect, GET, URL_FINALIDADE, URL_UF, C_DOT,URL_MUNICIPIO};

const C_VALUE = "value";
const C_OPTION = "option";
const C_DOT=".";
const GET = "GET";
const URL_FINALIDADE = "/ressubt/control?action=finalidade";
const URL_UF = "/ressubt/control?action=uf";
const URL_MUNICIPIO = "/ressubt/control?action=municipio&codigouf=";

var populateHtmlSelect=(codigo, descricao, select)=>{
    
    
    let option = document.createElement(C_OPTION);

    option.appendChild(document.createTextNode(descricao));
    option.setAttribute(C_VALUE, codigo);
    
    select.appendChild(option);
}