export const inputUppercase = (input) => {
    input.addEventListener("keypress", (e) => {
        var charInput = e.keyCode;
        console.log(e.target.value);
        e.target.value = e.target.value.toUpperCase();

        if (charInput >= 97 && charInput <= 122) {
            if (!e.ctrlKey && !e.metaKey && !e.altKey) {
                var newChar = charInput - 32;

                var start = e.target.selectionStart;
                var end = e.target.selectionEnd;
                e.target.value = e.target.value.substring(0, start) + String.fromCharCode(newChar) + e.target.value.substring(end);
                e.target.setSelectionRange(start + 1, start + 1);
                e.preventDefault();
            }
        }
    });
};
