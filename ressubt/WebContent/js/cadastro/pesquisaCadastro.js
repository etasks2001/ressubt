"use strict";
export { PesquisaCadastro, PageNumber };

function PageNumber(recordsLength) {
    this._currentPage = 1;
    this._nextPage = 1;
    this._previousPage = 1;
    if (recordsLength === 30) {
        this._nextPage = this._currentPage + 1;
    }

    console.log(`${this._previousPage}:${this._currentPage}:${this._nextPage}`);
}

PageNumber.prototype = {
    constructor: PageNumber,

    moveNextPage: function (recordsLength) {
        if (recordsLength === 30) {
            this._currentPage++;
            this._nextPage = this._currentPage + 1;
            this._previousPage = this._currentPage - 1;
        }
        console.log(`${this._previousPage}:${this._currentPage}:${this._nextPage}`);
    },
    movePreviousPage: function (recordsLength) {
        if (recordsLength === 30) {
            this._currentPage--;
            this._nextPage = this._currentPage + 1;
            this._previousPage = this._currentPage - 1;

            if (this._currentPage < 1) {
                this._currentPage = 1;
                this._nextPage = this._currentPage + 1;
                this._previousPage = this._currentPage;
            }
            if (this._previousPage === 0) {
                this._previousPage = 1;
            }
        }
        console.log(`${this._previousPage}:${this._currentPage}:${this._nextPage}`);
    },
    get nextPage() {
        return this._nextPage;
    },
    get previousPage() {
        return this._previousPage;
    },
};

function PesquisaCadastro(txtPesquisa, thead, tbody, selectOrdenar) {
    this.txtPesquisa = txtPesquisa;
    this.thead = thead;
    this.tbody = tbody;
    this.selectOrdenar = selectOrdenar;
    this.pageNumber = new PageNumber(30);
}

PesquisaCadastro.prototype = {
    constructor: PesquisaCadastro,

    pesquisar: function (formName) {
        let xhr = new XMLHttpRequest();

        var _this = this;
        xhr.onreadystatechange = function () {
            if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
                let json = JSON.parse(this.response);
                this.pageNumber = new PageNumber(30);
                _this.populateJson(json);
                if (json[0]) {
                    _this.pageNumber = new PageNumber(json.length);
                }
            }
        };

        let ordem = this.selectOrdenar.options[this.selectOrdenar.selectedIndex].value;
        xhr.open("POST", "/ressubt/control");
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        let link = `action=Cadastro${formName}Q&p=${this.txtPesquisa.value}&page=${this.pageNumber.previousPage}&o=${ordem}`;
        console.log(link);
        xhr.send(link);
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
                if (json[0]) {
                    _this.pageNumber.moveNextPage(json.length);
                }
            }
        };

        let ordem = this.selectOrdenar.options[this.selectOrdenar.selectedIndex].value;
        xhr.open("POST", "/ressubt/control");
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        const link = `action=Cadastro${formName}Q&p=${this.txtPesquisa.value}&page=${this.pageNumber.nextPage}&o=${ordem}`;
        console.log(link);
        xhr.send(link);
    },

    previousPage: function (formName) {
        let xhr = new XMLHttpRequest();
        let thead = this.thead;
        let tbody = this.tbody;

        var _this = this;
        xhr.onreadystatechange = function () {
            if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
                let json = JSON.parse(this.response);
                _this.populateJson(json);
                if (json[0]) {
                    _this.pageNumber.movePreviousPage(json.length);
                }
            }
        };

        let ordem = this.selectOrdenar.options[this.selectOrdenar.selectedIndex].value;
        xhr.open("POST", "/ressubt/control");
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        const link = `action=Cadastro${formName}Q&p=${this.txtPesquisa.value}&page=${this.pageNumber.previousPage}&o=${ordem}`;
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
            return json.length;
        }
        return 0;
    },
};
