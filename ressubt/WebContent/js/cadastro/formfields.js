export { FormFields };

function FormFields() {
    this.fields;
}

FormFields.prototype = {
    constructor: FormFields,
    disable: function (disable) {
        for (let i = 0; i < this.fields.length; i++) {
            this.fields[i].disabled = disable;
        }
        this.clear();
    },
    setFields: function (fields) {
        this.fields = fields;
    },
    getParameters: function () {
        let parameters = "";
        for (let i = 0; i < this.fields.length; i++) {
            parameters += this.fields[i].name + "=" + this.fields[i].value + "&";
        }
        return parameters;
    },
    clear: function () {
        for (let i = 0; i < this.fields.length; i++) {
            if (this.fields[i].tagName === "INPUT") {
                this.fields[i].value = "";
            }
        }
    },
};
