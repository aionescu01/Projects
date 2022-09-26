var buton1 = document.getElementById("again");
var buton2 = document.getElementById("ghicit");
var input = document.getElementById("inp");
var refr = document.getElementById("refresh");
var img;
var nume;
var myVar;

var vieti = 3;
var scor = 0;
var random_images_array = ['jucatori/achim.png', 'jucatori/rusescu.png', 'jucatori/cebotaru.png', 'jucatori/valceanu.png', 'jucatori/ventura.png', 'jucatori/cascini.png', 'jucatori/andronic.png', 'jucatori/bilali.png',
    'jucatori/chunchukov.png', 'jucatori/cordea.png', 'jucatori/gardos.png', 'jucatori/jutric.png', 'jucatori/markovic.png', 'jucatori/moulin.png', 'jucatori/pashov.png', 'jucatori/popa.png', 'jucatori/tanase.png',
    'jucatori/ureche.png'
];





var theNumbers = new Array();
theNumbers['jucatori/achim.png'] = 3;
theNumbers['jucatori/rusescu.png'] = 9;
theNumbers['jucatori/cebotaru.png'] = 37;
theNumbers['jucatori/valceanu.png'] = 1;
theNumbers['jucatori/ventura.png'] = 19;
theNumbers['jucatori/cascini.png'] = 24;
theNumbers['jucatori/andronic.png'] = 28;
theNumbers['jucatori/bilali.png'] = 4;
theNumbers['jucatori/chunchukov.png'] = 23;
theNumbers['jucatori/cordea.png'] = 25;
theNumbers['jucatori/gardos.png'] = 5;
theNumbers['jucatori/jutric.png'] = 15;
theNumbers['jucatori/markovic.png'] = 11;
theNumbers['jucatori/moulin.png'] = 7;
theNumbers['jucatori/pashov.png'] = 6;
theNumbers['jucatori/popa.png'] = 77;
theNumbers['jucatori/tanase.png'] = 10;
theNumbers['jucatori/ureche.png'] = 82;

refr.onclick = function refresh() {
    nume = prompt("Introduceti numele:");
    vieti = 3;
    scor = 0;
    random_images_array = ['jucatori/achim.png', 'jucatori/rusescu.png', 'jucatori/cebotaru.png', 'jucatori/valceanu.png', 'jucatori/ventura.png', 'jucatori/cascini.png', 'jucatori/andronic.png', 'jucatori/bilali.png',
        'jucatori/chunchukov.png', 'jucatori/cordea.png', 'jucatori/gardos.png', 'jucatori/jutric.png', 'jucatori/markovic.png', 'jucatori/moulin.png', 'jucatori/pashov.png', 'jucatori/popa.png', 'jucatori/tanase.png',
        'jucatori/ureche.png'
    ];
    buton1.style.visibility = "visible";
    document.getElementById("nr").innerHTML = ' ';
    var imgStr = '<img src="' + 'apasanou.png' + '" alt = "" style="height: auto; width: 200px;">';
    document.getElementById("imagine").innerHTML = imgStr;
    document.getElementById("vieti").innerHTML = '<span id="vieti">Vieti:' + vieti + '</span>';

}

buton1.onclick = function getRandomImage() {
    clearTimeout(myVar);

    num = Math.floor(Math.random() * random_images_array.length);
    if (random_images_array.length > 0) {
        img = random_images_array[num];
        if (vieti > 0)
            myVar = setTimeout(function() {

                document.getElementById("nr").visibility = "visible";
                document.getElementById("nr").innerHTML = 'Gresit!';
                vieti = vieti - 1;
                buton2.style.visibility = "hidden";
                input.style.visibility = "hidden";
                document.getElementById("vieti").innerHTML = '<span id="vieti">Vieti:' + vieti + '</span>';

                if (vieti == 0)
                    aipierdut().call();

            }, 7000);
    } else {
        img = 'placeholder.png';
        vieti = -1;
    }
    document.getElementById("nr").innerHTML = ' ';

    if (vieti == 0) {
        aipierdut.call();
        clearTimeout(myVar);
    } else if (vieti == -1) {
        buton2.style.visibility = "hidden";
        input.style.visibility = "hidden";
        document.getElementById("nr").innerHTML = 'Joc terminat! Scor: ';
        document.getElementById("nr").innerHTML += scor;
        document.getElementById("vieti").style.visibility = "hidden";

        var paragraf = document.createElement("p");
        paragraf.innerHTML = nume + " a terminat jocul cu scorul " + scor;
        document.getElementById("scoruri").appendChild(paragraf);

        clearTimeout(myVar);

    } else if (vieti >= 1) {
        buton1.style.visibility = "visible";
        buton2.style.visibility = "visible";
        input.style.visibility = "visible";
        document.getElementById("vieti").style.visibility = "visible";
    }

    document.getElementById("vieti").innerHTML = '<span id="vieti">Vieti:' + vieti + '</span>';
    var imgStr = '<img src="' + img + '" alt = "" style="height: auto; width: 200px;">';
    document.getElementById("imagine").innerHTML = imgStr;
    random_images_array.splice(num, 1);

}

function aipierdut() {

    document.getElementById("nr").innerHTML = 'Ai pierdut! Scor: ';
    document.getElementById("nr").innerHTML += scor;
    var imgStr = '<img src="' + 'aipierdut.png' + '" alt = "" style="height: auto; width: 200px;">';
    document.getElementById("imagine").innerHTML = imgStr;
    buton1.style.visibility = "hidden";
    document.getElementById("vieti").style.visibility = "hidden";

    var paragraf = document.createElement("p");
    paragraf.innerHTML = nume + " a terminat jocul cu scorul " + scor;
    document.getElementById("scoruri").appendChild(paragraf);


    clearTimeout(myVar);


}


buton2.onclick = function afisare() {
    var nr = input.value;
    if (theNumbers[img] == nr) {
        clearTimeout(myVar);
        document.getElementById("nr").innerHTML = 'Corect!';
        buton2.style.visibility = "hidden";
        input.style.visibility = "hidden";
        scor = scor + 1;
    } else {
        document.getElementById("nr").innerHTML = 'Gresit!';
        vieti = vieti - 1;
    }
    input.value = '';
    document.getElementById("vieti").innerHTML = '<span id="vieti">Vieti:' + vieti + '</span>';

    if (vieti == 0) {
        var imgStr = '<img src="' + 'aipierdut.png' + '" alt = "" style="height: auto; width: 200px;">';
        document.getElementById("imagine").innerHTML = imgStr;

        buton1.style.visibility = "hidden";
        buton2.style.visibility = "hidden";
        input.style.visibility = "hidden";
        document.getElementById("nr").innerHTML = 'Ai pierdut! Scor: ';
        document.getElementById("nr").innerHTML += scor;
        document.getElementById("vieti").style.visibility = "hidden";

        clearTimeout(myVar);


        var paragraf = document.createElement("p");
        paragraf.innerHTML = nume + " a terminat jocul cu scorul " + scor;
        document.getElementById("scoruri").appendChild(paragraf);
    }
}


function whichElement(e) {
    var targ;
    if (!e) {
        var e = window.event;
    }
    if (e.target) {
        targ = e.target;
    } else if (e.srcElement) {
        targ = e.srcElement;
    }
    var tname;
    tname = targ.getAttribute("href");
    alert("Redirectionam la " + tname);
    window.location.href = tname;
}

function hide(e) {
    e.currentTarget.style.visibility = 'hidden';
}

function func1(event) {
    alert("Zona imagine");
    if (document.getElementById("check").checked) {
        event.stopPropagation();
    }
}

function func2() {
    alert("Zona joc");
}

function myFunction() {
    var elem = document.querySelector(".buton");
    var theCSSprop = window.getComputedStyle(elem, null).getPropertyValue("background-color");
    document.getElementById("demo").innerHTML = "Culoarea butoanelor este " + theCSSprop;
}