window.onload = function() {
    fetch("qa.json").then(function(res) {
        res.text().then(function(text) {
            var intrebari = JSON.parse(text);
            afisare(intrebari);
        })
    })
}

function afisare(intrebari) {
    var buton1 = document.getElementById("b1");
    var nr;
    buton1.onclick = function() {
        nr = Math.floor(Math.random() * intrebari.test.length);
        document.getElementById("intrebare").innerHTML = intrebari.test[nr].question;
        document.getElementById("dif").innerHTML = intrebari.test[nr].difficulty;
        document.getElementById("raspuns").innerHTML = "";
    }
    document.getElementById("b2").onclick = function() {
        document.getElementById("raspuns").innerHTML = intrebari.test[nr].answer;
    }

}