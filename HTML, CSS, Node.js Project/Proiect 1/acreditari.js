const express = require('express');
const app = express();
const port = 3000;

app.use(express.static("Proiect 1"));

app.use('/index', express.urlencoded({ extended: true }));

app.use('/', (req, res) => {

})

app.listen(port, () => {
    console.log(`Example app listening at http://localhost:${port}`)
})

/*

cd C:\Users\alexa\Desktop\Facultate\Anul I\Sem II\Tehnici WEB\Proiecte\Proiect 1
node acreditari.js

*/