"use strict";

export { FormFields };

function FormFields() {
    this.fields;
    this.operation;
}

FormFields.prototype = {
    constructor: FormFields,
    disable: function (disable) {
        this.loop(function (f) {
            f.disabled = disable;
        });

        this.clear();
    },

    setFields: function (fields) {
        this.fields = fields;
    },

    getParameters: function () {
        let parameters = "";

        this.fields.forEach((x, i, f) => {
            let name = f[i].name;
            let value = f[i].value;
            console.log("value " + value);
            parameters += `${name}=${value}&`;
        });
        return parameters + `operation=${this.operation}`;
    },
    clear: function () {
        this.loop(function (f) {
            if (f.tagName === "INPUT") {
                f.value = "";
            }
        });
    },

    loop: function (func) {
        this.fields.forEach((x, i, f) => {
            func(f[i]);
        });
    },
    setInsert: function () {
        this.operation = "i";
    },
    setUpdate: function () {
        this.operation = "u";
    },
};
