import { populateHtmlSelect, GET, URL_FINALIDADE, URL_UF,C_DOT,URL_MUNICIPIO } from './function.js';

const selectUf = document.querySelector("select[name=uf]");
const selectMunicipio = document.querySelector("select[name=cod_mun]");
const selectFinalidade = document.querySelector("select[name=cod_fin]");

window.onload=()=>{
	requestJson(URL_FINALIDADE, selectFinalidade);
	requestJson(URL_UF, selectUf);
};

const requestJson = (url, combo)=>{
	let xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function(){
	    if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
	    	populateSelectWithJson(this.response, combo);
	    }
	};
	
	xhr.open(GET, url);
	xhr.setRequestHeader("Content-type", "application/json; charset=UTF-8");
	xhr.send();
}



selectUf.addEventListener('change', (event) => {
	var option = event.target.options[event.target.selectedIndex];

	console.log(window.localStorage);
	console.log("change: " + option.value + " - Label: " + option.label);
	selectMunicipio.innerHTML="";
	requestJson(URL_MUNICIPIO+option.value, selectMunicipio);
});



const populateSelectWithJson=(resposta, combo)=>{
    let json = JSON.parse(resposta);
    
    
    if(window.localStorage.getItem(combo.name)!==null){
    	window.localStorage.setItem("cod_fin", json);
    	console.log("jfdkaslç:"+window.localStorage.getItem(combo.name));
    }
    
    
    
    json.forEach((j) => {
    	populateHtmlSelect(j.codigo, j.descricao, combo)
    });
    
    combo.dispatchEvent(new Event('change'));
}



