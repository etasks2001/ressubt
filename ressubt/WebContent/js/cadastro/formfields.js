("use strict");
export { FormFields };

function FormFields() {
    this.fields;
    this.operation;
    this.formName;
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

    getParameters() {
        var model = {};
        this.fields.forEach((x, i, f) => {
            model[f[i].name] = f[i].value;
        });

        return "action=Cadastro" + this.formName + "&model=" + JSON.stringify(model) + "&operation=" + this.operation;
    },

    clear: function () {
        this.loop(function (f) {
            if (f.tagName === "INPUT" && f.getAttribute("type") === "text") {
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
    setFormName(formName) {
        this.formName = formName;
    },
};
