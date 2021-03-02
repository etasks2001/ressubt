import { criaOptionInSelect, GET, URL_FINALIDADE, URL_UF } from './function.js';


window.onload=()=>{
	carregaSelect(URL_FINALIDADE, "finalidade");
	carregaSelect(URL_UF,"uf");
};

const carregaSelect = function(url, classe){
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
        criaOptionInSelect(j.codigo, j.descricao, classe)
    });
}

