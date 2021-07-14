"use strict";
export { PesquisaCadastro };

function PesquisaCadastro(txtPesquisa, thead, tbody, selectOrdenar) {
    this.txtPesquisa = txtPesquisa;
    this.thead = thead;
    this.tbody = tbody;
    this.selectOrdenar = selectOrdenar;
    this.pagePrevious = 1;
    this.pageNext = 1;
}

PesquisaCadastro.prototype = {
    constructor: PesquisaCadastro,

    pesquisar: function (formName) {
        let xhr = new XMLHttpRequest();

        var _this = this;
        xhr.onreadystatechange = function () {
            if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
                let json = JSON.parse(this.response);
                _this.populateJson(json);
            }
        };

        let ordem = this.selectOrdenar.options[this.selectOrdenar.selectedIndex].value;
        xhr.open("POST", "/ressubt/control");
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

        xhr.send(`action=Cadastro${formName}Q&p=${this.txtPesquisa.value}&page=${this.pagePrevious}&o=${ordem}`);
    },

    clear: function () {
        this.txtPesquisa.value = "";
        this.thead.innerHTML = "";
        this.tbody.innerHTML = "";
    },

    nextPage: function (formName) {
        let xhr = new XMLHttpRequest();
        let thead = this.thead;
        let tbody = this.tbody;

        var _this = this;
        xhr.onreadystatechange = function () {
            if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
                let json = JSON.parse(this.response);
                _this.populateJson(json);
            }
        };

        let ordem = this.selectOrdenar.options[this.selectOrdenar.selectedIndex].value;
        xhr.open("POST", "/ressubt/control");
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        const link = `action=Cadastro${formName}Q&p=${this.txtPesquisa.value}&page=${this.pageNext}&o=${ordem}`;
        console.log(link);
        xhr.send(link);
    },
    populateJson: function (json) {
        this.thead.innerHTML = "";
        if (json[0]) {
            let heads = Object.keys(json[0]);
            let tr = document.createElement("tr");
            this.thead.appendChild(tr);

            heads.forEach((head) => {
                let th = document.createElement("th");
                th.innerText = head;
                tr.appendChild(th);
            });
            this.tbody.innerHTML = "";
            json.forEach((row) => {
                let tr = document.createElement("tr");
                heads.forEach((head) => {
                    let td = document.createElement("td");
                    td.innerText = row[head];
                    tr.appendChild(td);
                });

                this.tbody.appendChild(tr);
            });
            this.pageNext++;
        }
    },
};
