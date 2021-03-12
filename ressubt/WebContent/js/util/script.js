export { populateHtmlSelect, GET, URL_FINALIDADE, URL_UF, C_DOT, URL_MUNICIPIO, populateSelectWithJson, requestJson };

const C_VALUE = "value";
const C_OPTION = "option";
const C_DOT = ".";
const GET = "GET";
const URL_FINALIDADE = "/ressubt/control?action=finalidade";
const URL_UF = "/ressubt/control?action=uf";
const URL_MUNICIPIO = "/ressubt/control?action=municipio&codigouf=";

var populateHtmlSelect = (codigo, descricao, select) => {
    let option = document.createElement(C_OPTION);

    option.appendChild(document.createTextNode(descricao));
    option.setAttribute(C_VALUE, codigo);

    select.appendChild(option);
};
const requestJson = (url, combo) => {
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
            populateSelectWithJson(this.response, combo);
        }
    };

    xhr.open(GET, url);
    xhr.setRequestHeader("Content-type", "application/json; charset=UTF-8");
    xhr.send();
};

const populateSelectWithJson = (resposta, combo) => {
    let json = JSON.parse(resposta);
    json.forEach((j) => {
        populateHtmlSelect(j.codigo, j.descricao, combo);
    });
    combo.dispatchEvent(new Event("change"));
};
