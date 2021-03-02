import { populateHtmlSelect, GET, URL_FINALIDADE, URL_UF } from './function.js';


window.onload=()=>{
	requestJson(URL_FINALIDADE, "finalidade");
	requestJson(URL_UF,"uf");
};

const requestJson = (url, classe)=>{
	let xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function(){
	    if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
	        loadResposta(this.response, classe);
	    }
	};
	
	xhr.open(GET, url);
	xhr.setRequestHeader("Content-type", "application/json; charset=UTF-8");
	xhr.send();
}


const loadResposta=(resposta, classe)=>{
    let json = JSON.parse(resposta);
    json.forEach((j) => {
    	populateHtmlSelect(j.codigo, j.descricao, classe)
    });
}



