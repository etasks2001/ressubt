var btnLogin = document.querySelector("button");

console.log(btnLogin);
/*
btnLogin.addEventListener("click", (event) => {
    event.preventDefault();
    console.log("botão pressionado.");

    let xhr = new XMLHttpRequest();
    xhr.open("POST", "/ressubt/control");

    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function () {
        // Chama a função quando o estado mudar.

        if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
            console.log("fdsfdasfd");
            console.log(xhr.responseURL);
            console.log(xhr.response);
        } else if (this.readyState === XMLHttpRequest.DONE && this.status === 302) {
            console.log("302");
            console.log(xhr.response);
        }
    };

    xhr.send("action=Login&email=mauro&senha=123");
});
*/
