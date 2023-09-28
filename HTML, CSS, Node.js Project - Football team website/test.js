const express = require('express');
const formidable = require("formidable");
const fs = require("fs");
const app = express();
const session = require('express-session')

const port = 3000;

app.use(express.static("Proiect 1"));
app.use(express.static("poze"));
app.use(express.json());
app.set("view engine", "ejs");


app.get('/', (req, res) => {
    res.sendFile('/index.html');
});

app.use(session({
    secret: 'Keep it secret',
    name: 'uniqueSessionID',
    saveUninitialized: false
}))


app.get('/logout', (req, res) => {
    req.session.destroy((err) => {})
    res.writeHead(301, { Location: 'http://localhost:3000/' + 'acreditari_login.html' });
    res.end();

});


app.post("/adduser", function(req, res) {

    var form = new formidable.IncomingForm();
    var date;
    if (fs.existsSync("Proiect 1/login.json")) {
        var acreditari = fs.readFileSync("Proiect 1/login.json", "utf8");
        date = JSON.parse(acreditari);
    } else
        date = [];
    form.parse(req, function(err, fields) {
        var nume = fields.nume;
        var vcod = fields.cod;
        ob = { Username: nume, Password: vcod };
        date.push(ob);
        fs.writeFileSync("Proiect 1/login.json", JSON.stringify(date));
        res.send("S-a adaugat utilizatorul pe username " + nume + " cu parola " + vcod + ' <a href="acreditari_login.html ">La login</a>');
    })
})



app.post("/login", function(req, res) {

    var form = new formidable.IncomingForm();
    var date = [];
    form.parse(req, function(err, fields) {
        var nume = fields.nume;
        var vcod = fields.cod;
        ob = { Username: nume, Password: vcod };
        date.push(ob);
        var ok = 0;
        var login = fs.readFileSync("Proiect 1/login.json", "utf8");
        var datelogin = JSON.parse(login);

        for (let i = 0; i < datelogin.length; i++)
            if (datelogin[i].Username == ob.Username && datelogin[i].Password == ob.Password) {
                ok = 1;
                req.session.loggedIn = true;
                req.session.username = datelogin[i].Username;
                res.writeHead(301, { Location: 'http://localhost:3000/' + 'adaugareuser.html' });
                res.end();
            }
        if (ok == 0) {
            req.session.username = false;
            res.send('Date gresite! <a href="acreditari_login.html ">Inapoi la login</a>');
        }
    })
})

app.post("/salveaza", function(req, res) {
    var form = new formidable.IncomingForm();
    form.uploadDir = "poze";
    form.keepExtensions = true;
    var date;
    if (fs.existsSync("Proiect 1/acreditari.json")) {
        var acreditari = fs.readFileSync("Proiect 1/acreditari.json", "utf8");
        date = JSON.parse(acreditari);
    } else
        date = [];
    form.parse(req, function(err, fields, files) {
        var nume = fields.nume;
        var vcod = fields.cod;
        ob = { acreditare: nume, cod: vcod, poza: files.poza.path.substring(5) };
        date.push(ob);
        fs.writeFileSync("Proiect 1/acreditari.json", JSON.stringify(date));
        res.send("S-a adaugat acreditarea pe numele " + nume + " cu codul " + vcod);
    })
})

app.get("/acreditari", function(req, res) {
    var acreditari = fs.readFileSync("Proiect 1/acreditari.json", "utf8");
    var date = JSON.parse(acreditari);
    res.render("acreditari", { acreditari: date });
})


app.use(function(req, res, next) {
    res.status(404).send("404 Not Found")
})


app.listen(port, () => {
    console.log(`http://localhost:${port}`)
    var now = new Date();
    console.log('Repornit la ' + now.getHours() + ':' + now.getMinutes() + ':' + now.getSeconds());
})

/*

cd C:\Users\alexa\Desktop\Facultate\Anul I\Sem II\Tehnici WEB\Proiecte
nodemon test.js

*/