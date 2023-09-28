var random_images_array = ['red', 'green', 'blue', 'purple', 'orange', 'yellow', 'pink', 'black'];

window.onload = function() {

    num = Math.floor(Math.random() * random_images_array.length);
    var img = random_images_array[num];
    document.getElementById("salut").style.color = img;

    if (!localStorage.getItem("mesaj")) {
        localStorage.setItem("mesaj", "Go");
    } else {
        var string = localStorage.getItem("mesaj");
        localStorage.setItem("mesaj", string + "o");
        document.getElementById("salut").innerHTML = localStorage.getItem("mesaj") + "l!";

    }
    document.getElementById("reset").onclick = function() {
        //localStorage.clear();
        localStorage.removeItem("mesaj");
        location.reload();
    }

}

document.getElementById("refresh").onclick = function() {
    window.location.reload();
}
var div;
var but = document.getElementById("culoare");

but.onclick = function() {

    if (but.className == 'butonrosu') {
        div = document.querySelector('.butonrosu');
        div.classList.remove('butonrosu');
        div.classList.add('butonverde');
    } else {
        div = document.querySelector('.butonverde');
        div.classList.remove('butonverde');
        div.classList.add('butonrosu');
    }
}