"use strict";
export { PesquisaCadastro };

function PesquisaCadastro(txtPesquisa, thead, tbody, selectOrdenar) {
    this.txtPesquisa = txtPesquisa;
    this.thead = thead;
    this.tbody = tbody;
    this.selectOrdenar = selectOrdenar;
}

PesquisaCadastro.prototype = {
    constructor: PesquisaCadastro,
    pesquisar: function (formName) {
        let xhr = new XMLHttpRequest();
        let thead = this.thead;
        let tbody = this.tbody;

        xhr.onreadystatechange = function () {
            if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
                let json = JSON.parse(this.response);

                thead.innerHTML = "";
                let heads = Object.keys(json[0]);
                let tr = document.createElement("tr");
                thead.appendChild(tr);

                heads.forEach((head) => {
                    let th = document.createElement("th");
                    th.innerText = head;
                    tr.appendChild(th);
                });
                tbody.innerHTML = "";
                json.forEach((row) => {
                    let tr = document.createElement("tr");
                    heads.forEach((head) => {
                        let td = document.createElement("td");
                        td.innerText = row[head];
                        tr.appendChild(td);
                    });

                    tbody.appendChild(tr);
                });
            }
        };

        let ordem = this.selectOrdenar.options[this.selectOrdenar.selectedIndex].value;
        xhr.open("POST", "/ressubt/control");
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

        xhr.send(`action=Cadastro${formName}Q&p=${this.txtPesquisa.value}&page=1&o=${ordem}`);
    },
    clear: function () {
        this.txtPesquisa.value = "";
        this.thead.innerHTML = "";
        this.tbody.innerHTML = "";
    },
};
