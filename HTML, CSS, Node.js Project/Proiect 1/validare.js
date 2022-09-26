var input = document.getElementById("inp");
var buton = document.getElementById("validare");
var rezultat = 0;

buton.onclick = function validate() {
    var numarbilet = input.value;
    var biletcorect = /^[0-9]{3}[-\s\.]{0,1}[1-4]{3}[-\s\.]{0,1}[5-8]{4}$/;
    rezultat = biletcorect.test(numarbilet);
    if (rezultat == false)
        document.getElementById("validitate").innerHTML = 'Bilet invalid!';
    else document.getElementById("validitate").innerHTML = 'Bilet valid!';

}

input.onclick = function() {
    document.getElementById("validitate").innerHTML = '';
}