import { populateHtmlSelect, GET, URL_FINALIDADE, URL_UF, C_DOT, URL_MUNICIPIO } from "../../js/contribuinte/function.js";

const selectUf = document.querySelector("select[name=uf]");
const selectMunicipio = document.querySelector("select[name=cod_mun]");
const selectFinalidade = document.querySelector("select[name=cod_fin]");

const btnCadastro = document.querySelector("button[name=cadastro]");
const btnPesquisa = document.querySelector("button[name=pesquisa]");
console.log(btnCadastro);

const tabContents = document.querySelectorAll(".tabContent");
const btnTabs = document.querySelectorAll(".tab");

const openTab = (event, nome) => {
    tabContents.forEach((tab) => {
        tab.style.display = "none";
    });

    btnTabs.forEach((btn) => {
        btn.className = btn.className.replace(" active", "");
    });

    document.querySelector("#" + nome).style.display = "block";
    event.currentTarget.className += " active";
};

btnCadastro.addEventListener("click", function (event) {
    openTab(event, "cadastro");
});
btnPesquisa.addEventListener("click", function (event) {
    openTab(event, "pesquisa");
});

window.onload = () => {
    requestJson(URL_FINALIDADE, selectFinalidade);
    requestJson(URL_UF, selectUf);
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

selectUf.addEventListener("change", (event) => {
    var option = event.target.options[event.target.selectedIndex];

    selectMunicipio.innerHTML = "";
    requestJson(URL_MUNICIPIO + option.value, selectMunicipio);
});

const populateSelectWithJson = (resposta, combo) => {
    let json = JSON.parse(resposta);
    json.forEach((j) => {
        populateHtmlSelect(j.codigo, j.descricao, combo);
    });
    combo.dispatchEvent(new Event("change"));
};
