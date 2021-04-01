import { URL_FINALIDADE, URL_UF, URL_MUNICIPIO, requestJson } from "../../js/util/script.js";

const selectUf = document.querySelector("select[name=uf]");
const selectMunicipio = document.querySelector("select[name=cod_mun]");
const selectFinalidade = document.querySelector("select[name=cod_fin]");

window.onload = () => {
    requestJson(URL_FINALIDADE, selectFinalidade);
    requestJson(URL_UF, selectUf);

    //VMasker(document.getElementById("cnpj")).maskPattern("99.999.999/9999-99");
};

selectUf.addEventListener("change", (event) => {
    var option = event.target.options[event.target.selectedIndex];

    selectMunicipio.innerHTML = "";
    requestJson(URL_MUNICIPIO + option.value, selectMunicipio);
});
