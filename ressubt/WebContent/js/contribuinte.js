console.log("ola contribuinte");

var btnGravar = document.querySelector("button");
console.log(btnGravar);

let xhr = new XMLHttpRequest();
xhr.open("GET", "/ressubt/control?action=finalidade");

//xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
xhr.setRequestHeader("Content-type", "application/json; charset=UTF-8");
xhr.onreadystatechange = function () {
    // Chama a função quando o estado mudar.

    if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
        let resposta = xhr.response;

        console.log(resposta);
        let finalidadeJson = JSON.parse(resposta);
        finalidadeJson.forEach((finalidade) => {
            console.log(finalidade.codigo + " - " + finalidade.descricao);

            var selFinalidade = document.querySelector(".selFinalidade");
            let opt = document.createElement("option");
            opt.appendChild(document.createTextNode(finalidade.descricao));
            opt.setAttribute("value", finalidade.codigo);
            selFinalidade.appendChild(opt);
        });
    }
};

//xhr.send("action=finalidade");
xhr.send();
