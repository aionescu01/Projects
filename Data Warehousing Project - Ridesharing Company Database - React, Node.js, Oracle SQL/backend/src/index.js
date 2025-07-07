const express = require("express");
const cors = require('cors');

const fs = require('fs');
const path = require('path');

const angajatRoutes = require("./routes/oltp/angajat");
const clientRoutes = require("./routes/oltp/client");
const cursaRoutes = require("./routes/oltp/cursa");
const detaliiCursaRoutes = require("./routes/oltp/detaliiCursa");
const discountRoutes = require("./routes/oltp/discount");
const facturaRoutes = require("./routes/oltp/factura");
const istoricSoferiRoutes = require("./routes/oltp/istoricSoferi");
const locatiiRoutes = require("./routes/oltp/locatii");
const lucreazaInRoutes = require("./routes/oltp/lucreazaIn");
const masinaRoutes = require("./routes/oltp/masina");
const mesajRoutes = require("./routes/oltp/mesaj");

const dimAngajatRoutes = require("./routes/warehouse/dimAngajat");
const dimClientRoutes = require("./routes/warehouse/dimClient");
const dimFacturaRoutes = require("./routes/warehouse/dimFactura");
const dimLocatieRoutes = require("./routes/warehouse/dimLocatie");
const dimMasinaRoutes = require("./routes/warehouse/dimMasina");
const dimTimpRoutes = require("./routes/warehouse/dimTimp");
const FCursaRoutes = require("./routes/warehouse/FCursa");
const graphsRoutes = require("./routes/warehouse/graphs");

const { sequelizeOLTP, sequelizeWarehouse } = require("./config/database");
const DimAngajat = require('./models/warehouse/DimAngajat')
const DimClient = require('./models/warehouse/DimClient')
const DimFactura = require('./models/warehouse/DimFactura')
const DimLocatie = require('./models/warehouse/DimLocatie')
const DimMasina = require('./models/warehouse/DimMasina')
const DimTimp = require('./models/warehouse/DimTimp')
const FCursa = require('./models/warehouse/FCursa')


require("./models/oltp/associations");
require("./models/warehouse/associations");


const app = express();
const PORT = process.env.PORT || 3001;
const swaggerUi = require("swagger-ui-express");
const swaggerSpec = require("./swagger-config");


app.use(express.json());
app.use(cors());

// OLTP API
app.use("/api/oltp/angajat", angajatRoutes);
app.use("/api/oltp/client", clientRoutes);
app.use("/api/oltp/cursa", cursaRoutes);
app.use("/api/oltp/detaliiCursa", detaliiCursaRoutes);
app.use("/api/oltp/discount", discountRoutes);
app.use("/api/oltp/factura", facturaRoutes);
app.use("/api/oltp/istoricSoferi", istoricSoferiRoutes);
app.use("/api/oltp/locatii", locatiiRoutes);
app.use("/api/oltp/lucreazaIn", lucreazaInRoutes);
app.use("/api/oltp/masina", masinaRoutes);
app.use("/api/oltp/mesaj", mesajRoutes);


// Warehouse API
app.use("/api/warehouse/dimAngajat", dimAngajatRoutes);
app.use("/api/warehouse/dimClient", dimClientRoutes);
app.use("/api/warehouse/dimFactura", dimFacturaRoutes);
app.use("/api/warehouse/dimLocatie", dimLocatieRoutes);
app.use("/api/warehouse/dimMasina", dimMasinaRoutes);
app.use("/api/warehouse/dimTimp", dimTimpRoutes);
app.use("/api/warehouse/FCursa", FCursaRoutes);
app.use("/api/warehouse/", graphsRoutes);

app.use("/api-docs", swaggerUi.serve, swaggerUi.setup(swaggerSpec));


async function addMessageToDatabase(message, messageType, createdBy) {
  try {
    const validMessageTypes = ['E', 'W', 'I'];
    if (!validMessageTypes.includes(messageType)) {
      throw new Error(
        `Tipul mesajului este invalid: ${messageType}. Tipuri acceptate: ${validMessageTypes.join(", ")}`
      );
    }

    await sequelizeOLTP.models.Mesaj.create({
      MESSAGE: message,
      MESSAGE_TYPE: messageType,
      CREATED_BY: createdBy,
      CREATED_AT: new Date(),
    });

    console.log(`Mesajul a fost adăugat cu succes: ${messageType} - ${message}`);
  } catch (error) {
    console.error("Eroare la adăugarea mesajului în baza de date", error.message);
  }
}

async function runSQLScript(sequelize, filePath, message) {
  try {
    const sql = fs.readFileSync(path.resolve(filePath), 'utf8');
    
    await sequelize.query(sql);
    
    await addMessageToDatabase(`${message} - executat cu succes!`, "I", "Admin");
  } catch (err) {
    console.error("error: ", err);

    await addMessageToDatabase(`Eroare la executarea - ${message}`, "E", "Admin");
  }
}

async function syncDatabases() {
  try {
    await sequelizeOLTP.sync({ force: true });
    await addMessageToDatabase("Baza de date OLTP sincronizata cu succes!", "I", "Admin");

    await runSQLScript(sequelizeOLTP, './scripts/oltp-add-users.sql', 'adaugare utilizatori in OLTP');

    await insertInitialDataOLTP();

    await runSQLScript(sequelizeOLTP, './scripts/oltp-add-constraints.sql', 'adaugare constrangeri tabele OLTP');
    await runSQLScript(sequelizeOLTP, './scripts/oltp-grant-permissions-tables-users.sql', 'adaugare permisiuni tabele OLTP');
    
    //await sequelizeWarehouse.sync({ force: true });
    
    await DimAngajat.sync({ force: true });
    await DimClient.sync({ force: true });
    await DimFactura.sync({ force: true });
    await DimLocatie.sync({ force: true });
    await DimMasina.sync({ force: true });
    await DimTimp.sync({ force: true });
    await FCursa.sync({ force: true });

    await addMessageToDatabase("Baza de date Warehouse sincronizată cu succes!", "I", "Admin");

    await runSQLScript(sequelizeWarehouse, './scripts/warehouse-add-users.sql', 'adaugare utilizatori in Warehouse');

    await insertInitialDataWarehouse();

    await runSQLScript(sequelizeWarehouse, './scripts/warehouse-add-constraints.sql', 'adaugare constrangeri tabele Warehouse');
    await runSQLScript(sequelizeWarehouse, './scripts/warehouse-grant-permissions-tables-users.sql', 'adaugare permisiuni tabele Warehouse');

    await runSQLScript(sequelizeWarehouse, './scripts/warehouse-practical-exercises.sql', 'adaugare indecsi si view-uri');

    app.listen(PORT, () =>
      console.log(`DB ruleaza pe portul ${PORT}`)
    );
  } catch (err) {
    console.error("error: ", err);

    await addMessageToDatabase("Eroare la sincronizarea bazelor de date", "E", "Admin");
  }
}

syncDatabases();

async function insertInitialDataOLTP() {
  try {
    const existingClients = await sequelizeOLTP.models.Client.findAll();

    if (existingClients.length === 0) {
      
      await sequelizeOLTP.models.Client.bulkCreate([
        { nume: 'Popescu', prenume: 'Ion', nr_telefon: '0712345678', apelativ: 'Dl.', data_nastere: '1985-06-15', nota: 9.5 },
        { nume: 'Ionescu', prenume: 'Maria', nr_telefon: '0712345679', apelativ: 'Dna.', data_nastere: '1990-03-22', nota: 8.7 },
        { nume: 'Georgescu', prenume: 'Andrei', nr_telefon: '0723456789', apelativ: 'Dl.', data_nastere: '1982-07-30', nota: 7.3 },
        { nume: 'Radu', prenume: 'Elena', nr_telefon: '0734567890', apelativ: 'Dna.', data_nastere: '1975-12-10', nota: 6.8 },
        { nume: 'Vasilescu', prenume: 'Mihai', nr_telefon: '0745678901', apelativ: 'Dl.', data_nastere: '1995-01-25', nota: 8.9 },
        { nume: 'Stan', prenume: 'Ioana', nr_telefon: '0756789012', apelativ: 'Dna.', data_nastere: '1988-09-14', nota: 7.5 },
        { nume: 'Dumitru', prenume: 'Gabriel', nr_telefon: '0767890123', apelativ: 'Dl.', data_nastere: '1978-11-03', nota: 9.2 },
        { nume: 'Bălan', prenume: 'Claudia', nr_telefon: '0778901234', apelativ: 'Dna.', data_nastere: '1992-04-28', nota: 6.5 },
        { nume: 'Toma', prenume: 'Radu', nr_telefon: '0789012345', apelativ: 'Dl.', data_nastere: '1980-01-12', nota: 7.0 },
        { nume: 'Păun', prenume: 'Oana', nr_telefon: '0790123456', apelativ: 'Dna.', data_nastere: '1983-02-19', nota: 8.0 },
        { nume: 'Neagu', prenume: 'Vasile', nr_telefon: '0701234567', apelativ: 'Dl.', data_nastere: '1993-06-22', nota: 9.0 },
        { nume: 'Munteanu', prenume: 'Anca', nr_telefon: '0702345678', apelativ: 'Dna.', data_nastere: '1981-08-13', nota: 7.8 },
        { nume: 'Marin', prenume: 'Florin', nr_telefon: '0703456789', apelativ: 'Dl.', data_nastere: '1986-03-05', nota: 8.3 },
        { nume: 'Ilie', prenume: 'Mihaela', nr_telefon: '0704567890', apelativ: 'Dna.', data_nastere: '1989-10-17', nota: 8.6 },
        { nume: 'Cristea', prenume: 'Ion', nr_telefon: '0705678901', apelativ: 'Dl.', data_nastere: '1991-01-30', nota: 6.9 },
        { nume: 'Iacob', prenume: 'Larisa', nr_telefon: '0706789012', apelativ: 'Dna.', data_nastere: '1994-05-08', nota: 7.2 },
        { nume: 'Călinescu', prenume: 'George', nr_telefon: '0707890123', apelativ: 'Dl.', data_nastere: '1987-12-02', nota: 7.4 },
        { nume: 'Stoica', prenume: 'Viorica', nr_telefon: '0708901234', apelativ: 'Dna.', data_nastere: '1976-11-25', nota: 8.1 },
        { nume: 'Nistor', prenume: 'Dan', nr_telefon: '0709012345', apelativ: 'Dl.', data_nastere: '1996-02-15', nota: 9.7 }
      ]);

    }

    const existingLocatii = await sequelizeOLTP.models.Locatii.findAll();

    if (existingLocatii.length === 0) {

      await sequelizeOLTP.models.Locatii.bulkCreate([
        { localitate: 'Bucuresti', judet: 'Bucuresti' },
        { localitate: 'Cluj-Napoca', judet: 'Cluj' },
        { localitate: 'Iasi', judet: 'Iasi' },
        { localitate: 'Timișoara', judet: 'Timiș' },
        { localitate: 'Constanța', judet: 'Constanța' },
        { localitate: 'Brașov', judet: 'Brașov' },
        { localitate: 'Ploiești', judet: 'Prahova' },
        { localitate: 'Arad', judet: 'Arad' },
        { localitate: 'Iași', judet: 'Iași' },
        { localitate: 'Sibiu', judet: 'Sibiu' }
      ]);

    }

    const existingMasini = await sequelizeOLTP.models.Masina.findAll();

    if (existingMasini.length === 0) {

      await sequelizeOLTP.models.Masina.bulkCreate([
        { numar_masina: 'IF123XYZ', data_achizitionare: '2020-05-01', data_revizie_urm: '2025-06-01', marca: 'Dacia', model: 'Logan' },
        { numar_masina: 'BR456ABC', data_achizitionare: '2021-07-15', data_revizie_urm: '2025-08-01', marca: 'BMW', model: 'X5' },
        { numar_masina: 'B124DEF', data_achizitionare: '2019-03-20', data_revizie_urm: '2025-09-01', marca: 'Audi', model: 'A4' },
        { numar_masina: 'VS56GHI', data_achizitionare: '2020-11-25', data_revizie_urm: '2025-11-15', marca: 'Volkswagen', model: 'Golf' },
        { numar_masina: 'BZ17JKL', data_achizitionare: '2021-08-09', data_revizie_urm: '2025-12-01', marca: 'Ford', model: 'Focus' },
        { numar_masina: 'GL24MNO', data_achizitionare: '2020-12-12', data_revizie_urm: '2025-12-15', marca: 'Mercedes', model: 'C-Class' },
        { numar_masina: 'BR234PQR', data_achizitionare: '2018-05-18', data_revizie_urm: '2025-06-10', marca: 'Toyota', model: 'Corolla' },
        { numar_masina: 'CS431STU', data_achizitionare: '2022-01-22', data_revizie_urm: '2025-07-01', marca: 'Skoda', model: 'Octavia' },
        { numar_masina: 'DB23VWX', data_achizitionare: '2019-06-10', data_revizie_urm: '2025-08-15', marca: 'Renault', model: 'Megane' },
        { numar_masina: 'CL54YZA', data_achizitionare: '2021-10-30', data_revizie_urm: '2025-09-20', marca: 'Peugeot', model: '3008' },
        { numar_masina: 'IF123BCD', data_achizitionare: '2020-02-14', data_revizie_urm: '2025-10-01', marca: 'Opel', model: 'Astra' },
        { numar_masina: 'IL999EFG', data_achizitionare: '2019-09-09', data_revizie_urm: '2025-11-30', marca: 'Hyundai', model: 'i30' },
        { numar_masina: 'MH888HIG', data_achizitionare: '2021-04-25', data_revizie_urm: '2025-12-01', marca: 'Honda', model: 'Civic' },
        { numar_masina: 'NT777JKL', data_achizitionare: '2022-06-12', data_revizie_urm: '2025-10-20', marca: 'Kia', model: 'Sportage' },
        { numar_masina: 'MS66LMN', data_achizitionare: '2021-09-17', data_revizie_urm: '2025-11-15', marca: 'Mazda', model: '3' },
        { numar_masina: 'PT555OPQ', data_achizitionare: '2019-11-30', data_revizie_urm: '2025-12-01', marca: 'Nissan', model: 'Qashqai' },
        { numar_masina: 'CT444RST', data_achizitionare: '2020-08-01', data_revizie_urm: '2025-06-30', marca: 'Citroen', model: 'C4' },
        { numar_masina: 'B33UVW', data_achizitionare: '2021-02-22', data_revizie_urm: '2025-07-01', marca: 'Seat', model: 'Leon' },
        { numar_masina: 'B222XYZ', data_achizitionare: '2018-01-10', data_revizie_urm: '2025-08-15', marca: 'Peugeot', model: '208' },
        { numar_masina: 'B11ABC', data_achizitionare: '2021-12-05', data_revizie_urm: '2025-09-20', marca: 'Fiat', model: 'Punto' }
      ]);

    }

    const existingAngajati = await sequelizeOLTP.models.Angajat.findAll();

    if (existingAngajati.length === 0) {

      await sequelizeOLTP.models.Angajat.bulkCreate([
        { nume: 'Popescu', prenume: 'Ion', nr_telefon: '0720123456', tip_angajat: 'Sofer', data_nastere: '1985-03-15', data_angajare: '2020-01-10', salariu: 3500, cod_masina: 2, dispecerat: null },
        { nume: 'Ionescu', prenume: 'Maria', nr_telefon: '0745123456', tip_angajat: 'Sofer', data_nastere: '1990-06-25', data_angajare: '2021-03-15', salariu: 3000, cod_masina: 3, dispecerat: null },
        { nume: 'Vasilescu', prenume: 'George', nr_telefon: '0734123456', tip_angajat: 'Sofer', data_nastere: '1982-11-05', data_angajare: '2019-05-22', salariu: 3800, cod_masina: 1, dispecerat: null },
        { nume: 'Georgescu', prenume: 'Ana', nr_telefon: '0765123456', tip_angajat: 'Dispecer', data_nastere: '1987-02-15', data_angajare: '2018-09-01', salariu: 4200, cod_masina: null, dispecerat: 'Titan' },
        { nume: 'Mihail', prenume: 'Adrian', nr_telefon: '0785123456', tip_angajat: 'Sofer', data_nastere: '1989-08-10', data_angajare: '2020-07-01', salariu: 3400, cod_masina: 7, dispecerat: null },
        { nume: 'Stan', prenume: 'Florin', nr_telefon: '0756123456', tip_angajat: 'Dispecer', data_nastere: '1983-12-20', data_angajare: '2017-04-10', salariu: 4300, cod_masina: null, dispecerat: 'Dristor' },
        { nume: 'Radu', prenume: 'Elena', nr_telefon: '0723123456', tip_angajat: 'Sofer', data_nastere: '1995-01-12', data_angajare: '2022-06-20', salariu: 3100, cod_masina: 8, dispecerat: null },
        { nume: 'Bucur', prenume: 'Marian', nr_telefon: '0741123456', tip_angajat: 'Sofer', data_nastere: '1992-07-30', data_angajare: '2021-05-12', salariu: 3200, cod_masina: 11, dispecerat: null },
        { nume: 'Popa', prenume: 'Vlad', nr_telefon: '0729123456', tip_angajat: 'Dispecer', data_nastere: '1986-04-28', data_angajare: '2019-11-15', salariu: 4000, cod_masina: null, dispecerat: 'Titan' },
        { nume: 'Dima', prenume: 'Ioana', nr_telefon: '0798123456', tip_angajat: 'Sofer', data_nastere: '1980-09-22', data_angajare: '2018-02-05', salariu: 3600, cod_masina: 12, dispecerat: null }
      ]);

    }

    const existingLucreazaIn = await sequelizeOLTP.models.LucreazaIn.findAll();

    if (existingLucreazaIn.length === 0) {

      await sequelizeOLTP.models.LucreazaIn.bulkCreate([
        { cod_angajat: 1, cod_locatie: 1 },
        { cod_angajat: 2, cod_locatie: 1 },
        { cod_angajat: 3, cod_locatie: 1 },
        { cod_angajat: 5, cod_locatie: 1 },
        { cod_angajat: 7, cod_locatie: 1 },
        { cod_angajat: 8, cod_locatie: 1 },
        { cod_angajat: 5, cod_locatie: 2 },
        { cod_angajat: 7, cod_locatie: 2 },
        { cod_angajat: 10, cod_locatie: 2 },
        { cod_angajat: 1, cod_locatie: 3 },
        { cod_angajat: 2, cod_locatie: 3 },
        { cod_angajat: 8, cod_locatie: 3 },
        { cod_angajat: 2, cod_locatie: 4 },
        { cod_angajat: 8, cod_locatie: 4 },
        { cod_angajat: 1, cod_locatie: 5 },
        { cod_angajat: 2, cod_locatie: 5 },
        { cod_angajat: 1, cod_locatie: 6 },
        { cod_angajat: 2, cod_locatie: 7 },
        { cod_angajat: 3, cod_locatie: 8 },
        { cod_angajat: 5, cod_locatie: 9 },
        { cod_angajat: 7, cod_locatie: 10 }
      ]);

    }

    const existingCurse = await sequelizeOLTP.models.Cursa.findAll();

    if (existingCurse.length === 0) {

      await sequelizeOLTP.models.Cursa.bulkCreate([
        { cod_masina: 1, cod_sofer: 1, cod_client: 1, adresa_client: 'Strada Victoriei 10', destinatie: 'Strada Bisericii 3', cod_locatie: 1 },
        { cod_masina: 2, cod_sofer: 2, cod_client: 2, adresa_client: 'Strada Ciresilor 7', destinatie: 'Strada Stefan cel Mare 12', cod_locatie: 1 },
        { cod_masina: 3, cod_sofer: 3, cod_client: 3, adresa_client: 'Strada Lalelelor 5', destinatie: 'Strada Principala 9', cod_locatie: 1 },
        { cod_masina: 4, cod_sofer: 5, cod_client: 4, adresa_client: 'Strada Teiului 6', destinatie: 'Strada Muresului 8', cod_locatie: 2 },
        { cod_masina: 5, cod_sofer: 7, cod_client: 5, adresa_client: 'Strada Moara de Vant 3', destinatie: 'Strada Alba Iulia 2', cod_locatie: 2 },
        { cod_masina: 6, cod_sofer: 8, cod_client: 6, adresa_client: 'Strada Nordului 1', destinatie: 'Strada Unirii 15', cod_locatie: 2 },
        { cod_masina: 7, cod_sofer: 1, cod_client: 7, adresa_client: 'Strada Stelea 11', destinatie: 'Strada Gheorgheni 4', cod_locatie: 3 },
        { cod_masina: 8, cod_sofer: 2, cod_client: 8, adresa_client: 'Strada Căpitanilor 7', destinatie: 'Strada Mureșului 13', cod_locatie: 3 },
        { cod_masina: 9, cod_sofer: 3, cod_client: 9, adresa_client: 'Strada Valea Lunga 8', destinatie: 'Strada Moșilor 10', cod_locatie: 3 },
        { cod_masina: 10, cod_sofer: 5, cod_client: 10, adresa_client: 'Strada Făgetului 9', destinatie: 'Strada Ferdinand 6', cod_locatie: 4 },
        { cod_masina: 12, cod_sofer: 7, cod_client: 11, adresa_client: 'Strada Carpați 5', destinatie: 'Strada Iancu de Hunedoara 7', cod_locatie: 4 },
        { cod_masina: 11, cod_sofer: 8, cod_client: 12, adresa_client: 'Strada Gheorgheni 10', destinatie: 'Strada Piatra Mare 3', cod_locatie: 4 },
        { cod_masina: 1, cod_sofer: 1, cod_client: 13, adresa_client: 'Strada Păcii 4', destinatie: 'Strada Andrei Mureșanu 2', cod_locatie: 5 },
        { cod_masina: 1, cod_sofer: 2, cod_client: 14, adresa_client: 'Strada Dacia 5', destinatie: 'Strada Ion Ionescu 7', cod_locatie: 5 },
        { cod_masina: 2, cod_sofer: 3, cod_client: 15, adresa_client: 'Strada Abatorului 8', destinatie: 'Strada Garajului 12', cod_locatie: 5 },
        { cod_masina: 2, cod_sofer: 5, cod_client: 16, adresa_client: 'Strada Pădurii 9', destinatie: 'Strada Revoluției 13, Hunedoara', cod_locatie: 6 },
        { cod_masina: 3, cod_sofer: 7, cod_client: 17, adresa_client: 'Strada Viitorului 12', destinatie: 'Strada Dumitru Ciuca 5', cod_locatie: 6 },
        { cod_masina: 7, cod_sofer: 8, cod_client: 18, adresa_client: 'Strada Adunați 15', destinatie: 'Strada Petru Rareș 6', cod_locatie: 6 },
        { cod_masina: 8, cod_sofer: 1, cod_client: 19, adresa_client: 'Strada Mărului 3', destinatie: 'Strada Gheorghe Doja 10', cod_locatie: 7 },
        { cod_masina: 11, cod_sofer: 2, cod_client: 1, adresa_client: 'Strada Arcului 8', destinatie: 'Strada Lăcrămioarei 12', cod_locatie: 7 },
        { cod_masina: 11, cod_sofer: 3, cod_client: 2, adresa_client: 'Strada Călărașilor 1', destinatie: 'Strada Gării 4', cod_locatie: 7 },
        { cod_masina: 12, cod_sofer: 5, cod_client: 5, adresa_client: 'Strada Perlei 10', destinatie: 'Strada Brătianu 6', cod_locatie: 8 },
        { cod_masina: 1, cod_sofer: 7, cod_client: 1, adresa_client: 'Strada Sălciilor 3', destinatie: 'Strada Alexandru Ioan Cuza 7', cod_locatie: 8 },
        { cod_masina: 7, cod_sofer: 8, cod_client: 2, adresa_client: 'Strada Albinelor 8', destinatie: 'Strada Căpitan Ignat 3', cod_locatie: 8 },
        { cod_masina: 8, cod_sofer: 1, cod_client: 1, adresa_client: 'Strada Găgești 4', destinatie: 'Strada Filimon 12', cod_locatie: 9 }
      ]);

    }

    const existingDetaliiCurse = await sequelizeOLTP.models.DetaliiCursa.findAll();

    if (existingDetaliiCurse.length === 0) {

      await sequelizeOLTP.models.DetaliiCursa.bulkCreate([
        { cod_cursa: 1, data_cursa: new Date('2025-01-01'), nota_sofer: 8, nota_client: 9 },
        { cod_cursa: 2, data_cursa: new Date('2025-02-02'), nota_sofer: 7, nota_client: 8 },
        { cod_cursa: 3, data_cursa: new Date('2025-01-03'), nota_sofer: 9, nota_client: 9 },
        { cod_cursa: 4, data_cursa: new Date('2025-02-04'), nota_sofer: 6, nota_client: 7 },
        { cod_cursa: 5, data_cursa: new Date('2025-01-05'), nota_sofer: 8, nota_client: 7 },
        { cod_cursa: 6, data_cursa: new Date('2025-02-06'), nota_sofer: 9, nota_client: 8 },
        { cod_cursa: 7, data_cursa: new Date('2025-01-07'), nota_sofer: 7, nota_client: 6 },
        { cod_cursa: 8, data_cursa: new Date('2025-02-08'), nota_sofer: 8, nota_client: 8 },
        { cod_cursa: 9, data_cursa: new Date('2025-01-09'), nota_sofer: 6, nota_client: 6 },
        { cod_cursa: 10, data_cursa: new Date('2025-02-10'), nota_sofer: 9, nota_client: 8 },
        { cod_cursa: 11, data_cursa: new Date('2025-01-11'), nota_sofer: 7, nota_client: 7 },
        { cod_cursa: 12, data_cursa: new Date('2025-01-12'), nota_sofer: 8, nota_client: 8 },
        { cod_cursa: 13, data_cursa: new Date('2025-01-13'), nota_sofer: 7, nota_client: 7 },
        { cod_cursa: 14, data_cursa: new Date('2025-02-14'), nota_sofer: 6, nota_client: 8 },
        { cod_cursa: 15, data_cursa: new Date('2025-02-15'), nota_sofer: 8, nota_client: 8 },
        { cod_cursa: 16, data_cursa: new Date('2025-02-16'), nota_sofer: 9, nota_client: 9 },
        { cod_cursa: 17, data_cursa: new Date('2025-01-17'), nota_sofer: 7, nota_client: 6 },
        { cod_cursa: 18, data_cursa: new Date('2025-02-18'), nota_sofer: 6, nota_client: 7 },
        { cod_cursa: 19, data_cursa: new Date('2025-02-19'), nota_sofer: 9, nota_client: 9 },
        { cod_cursa: 20, data_cursa: new Date('2025-02-20'), nota_sofer: 8, nota_client: 7 },
        { cod_cursa: 21, data_cursa: new Date('2025-01-21'), nota_sofer: 8, nota_client: 8 },
        { cod_cursa: 22, data_cursa: new Date('2025-01-22'), nota_sofer: 7, nota_client: 9 },
        { cod_cursa: 23, data_cursa: new Date('2025-01-23'), nota_sofer: 6, nota_client: 7 },
        { cod_cursa: 24, data_cursa: new Date('2025-01-24'), nota_sofer: 9, nota_client: 6 },
        { cod_cursa: 25, data_cursa: new Date('2025-02-25'), nota_sofer: 8, nota_client: 7 }
      ]);

    }

    const existingDiscounts = await sequelizeOLTP.models.Discount.findAll();

    if (existingDiscounts.length === 0) {

      await sequelizeOLTP.models.Discount.bulkCreate([
        { nota_discount: 11, cod_discount: 10 },
        { nota_discount: 10, cod_discount: 7 },
        { nota_discount: 9, cod_discount: 5 },
        { nota_discount: 8, cod_discount: 2 },
        { nota_discount: 7, cod_discount: 1 },
        { nota_discount: 6, cod_discount: 0 },
        { nota_discount: 5, cod_discount: 0 },
        { nota_discount: 4, cod_discount: 0 },
        { nota_discount: 3, cod_discount: 0 },
        { nota_discount: 2, cod_discount: 0 },
        { nota_discount: 1, cod_discount: 0 }
      ]);

    }

    const existingFacturi = await sequelizeOLTP.models.Factura.findAll();

    if (existingFacturi.length === 0) {

      await sequelizeOLTP.models.Factura.bulkCreate([
        { cod_dispecer: 4, cod_cursa: 1, pret: 55.20 },
        { cod_dispecer: 6, cod_cursa: 2, pret: 60.75 },
        { cod_dispecer: 9, cod_cursa: 3, pret: 45.30 },
        { cod_dispecer: 4, cod_cursa: 4, pret: 70.60 },
        { cod_dispecer: 6, cod_cursa: 5, pret: 50.90 },
        { cod_dispecer: 9, cod_cursa: 6, pret: 63.00 },
        { cod_dispecer: 4, cod_cursa: 7, pret: 38.80 },
        { cod_dispecer: 6, cod_cursa: 8, pret: 32.20 },
        { cod_dispecer: 9, cod_cursa: 9, pret: 72.40 },
        { cod_dispecer: 4, cod_cursa: 10, pret: 64.10 },
        { cod_dispecer: 6, cod_cursa: 11, pret: 55.50 },
        { cod_dispecer: 9, cod_cursa: 12, pret: 78.30 },
        { cod_dispecer: 4, cod_cursa: 13, pret: 48.50 },
        { cod_dispecer: 6, cod_cursa: 14, pret: 60.00 },
        { cod_dispecer: 9, cod_cursa: 15, pret: 45.30 },
        { cod_dispecer: 4, cod_cursa: 16, pret: 39.70 },
        { cod_dispecer: 6, cod_cursa: 17, pret: 28.80 },
        { cod_dispecer: 9, cod_cursa: 18, pret: 62.60 },
        { cod_dispecer: 4, cod_cursa: 19, pret: 51.90 },
        { cod_dispecer: 6, cod_cursa: 20, pret: 46.50 },
        { cod_dispecer: 9, cod_cursa: 21, pret: 38.40 },
        { cod_dispecer: 4, cod_cursa: 22, pret: 29.00 },
        { cod_dispecer: 6, cod_cursa: 23, pret: 25.10 },
        { cod_dispecer: 9, cod_cursa: 24, pret: 60.80 },
        { cod_dispecer: 4, cod_cursa: 25, pret: 53.20 }
      ]);

    }

    const existingIstoricSoferi = await sequelizeOLTP.models.IstoricSofer.findAll();

    if (existingIstoricSoferi.length === 0) {

      await sequelizeOLTP.models.IstoricSofer.bulkCreate([
        { cod_sofer: 1, nota: 7.8, numar_curse: 5 },
        { cod_sofer: 2, nota: 7.25, numar_curse: 4 },
        { cod_sofer: 3, nota: 7.75, numar_curse: 4 },
        { cod_sofer: 5, nota: 7.75, numar_curse: 4 },
        { cod_sofer: 7, nota: 7, numar_curse: 4 },
        { cod_sofer: 8, nota: 8, numar_curse: 4 }
      ]);

    }

    await addMessageToDatabase("Datele initiale au fost inserate cu succes in baza de date de tip OLTP!", "I", "Admin");
  } catch (err) {
    await addMessageToDatabase("Eroare la inserarea datelor initiale pentru OLTP", "E", "Admin");
  }
}

async function insertInitialDataWarehouse() {
  try {

    const existingDimAngajat = await sequelizeWarehouse.models.DimAngajat.findAll();

    if (existingDimAngajat.length === 0) {
      const angajatData = await sequelizeOLTP.models.Angajat.findAll({
        attributes: [
          ["cod_angajat", "cod_angajat"],
          [sequelizeOLTP.Sequelize.fn("TRIM", sequelizeOLTP.Sequelize.literal("nume" || ' ' || "prenume")), "nume_angajat"],
          ["tip_angajat", "tip_angajat"],
          ["data_angajare", "data_angajare"],
          ["salariu", "salariu"],
        ],
        where: {
          tip_angajat: ["Sofer", "Dispecer"],
        },
        raw: true,
      });
    
      const formattedData = angajatData.map(item => ({
        cod_angajat: item.cod_angajat,
        nume_angajat: item.nume_angajat,
        tip_angajat: item.tip_angajat,
        data_angajare: item.data_angajare,
        salariu: item.salariu
      }));

      try {
        await sequelizeWarehouse.models.DimAngajat.bulkCreate(formattedData);
      } catch (err) {
      
        try {
          await addMessageToDatabase('Eroare la inserarea datelor in DIM_ANGAJAT', "E", "Admin");
        } catch (logError) {
          console.error('Eroare la logarea mesajului în baza de date:', logError);
        }
      }
    }

    const existingDimClient = await sequelizeWarehouse.models.DimClient.findAll();
    
    if (existingDimClient.length === 0) {
      const clientData = await sequelizeOLTP.models.Client.findAll({
        attributes: [
          ["cod_client", "cod_client"],
          [
            sequelizeOLTP.Sequelize.fn(
              "TRIM",
              sequelizeOLTP.Sequelize.literal("nume" || ' ' || "prenume")
            ),
            "nume_client"
          ],
          ["nota", "nota_client"],
          [sequelizeOLTP.Sequelize.fn(
            "COALESCE",
            sequelizeOLTP.Sequelize.col("apelativ"),
            sequelizeOLTP.Sequelize.literal("CAST('N/A' AS NVARCHAR2(5))")
          ), "apelativ"],
          ["data_nastere", "data_nastere"],
        ],
        raw: true,
      });

      const formattedClientData = clientData.map(item => ({
        cod_client: item.cod_client,
        nume_client: item.nume_client,
        nota_client: item.nota_client,
        apelativ: item.apelativ,
        data_nastere: item.data_nastere,
      }));

      try {
        sequelizeWarehouse.models.DimClient.bulkCreate(formattedClientData);
      } catch (err) {
      
        try {
          await addMessageToDatabase('Eroare la inserarea datelor in DIM_CLIENT', "E", "Admin");
        } catch (logError) {
          console.error('Eroare la logarea mesajului în baza de date:', logError);
        }
      }
    }

    const existingDimFactura = await sequelizeWarehouse.models.DimFactura.findAll();
    
    if (existingDimFactura.length === 0) {
      const facturaData = await sequelizeOLTP.models.Factura.findAll({
        attributes: [
          ["cod_factura", "cod_factura"],
          ["cod_dispecer", "cod_dispecer"],
          ["cod_cursa", "cod_cursa"],
          [sequelizeOLTP.Sequelize.col("Cursa->DetaliiCursa.data_cursa"), "data_emitere"],
          ["pret", "pret"],
        ],
        include: [
          {
            model: sequelizeOLTP.models.Cursa,
            attributes: [],
            include: [
              {
                model: sequelizeOLTP.models.DetaliiCursa,
                attributes: [],
              },
            ],
          },
        ],
        where: {
          pret: {
            [sequelizeOLTP.Sequelize.Op.gt]: 0,
          },
        },
        raw: true,
      });

      try {
        for (const item of facturaData) {

          await sequelizeWarehouse.models.DimFactura.create(item);
        };
      } catch (err) {

        try {
          await addMessageToDatabase('Eroare la inserarea datelor in DIM_FACTURA', "E", "Admin");
        } catch (logError) {
          console.error('Eroare la logarea mesajului în baza de date:', logError);
        }
      }
    }

    const existingDimLocatie = await sequelizeWarehouse.models.DimLocatie.findAll();
    
    if (existingDimLocatie.length === 0) {
      const locatieData = await sequelizeOLTP.models.Locatii.findAll({
        attributes: [
          ["cod_locatie", "cod_locatie"],
          ["localitate", "localitate"],
          ["judet", "judet"],
        ],
        raw: true,
      });

      const formattedLocatieData = locatieData.map(item => ({
        cod_locatie: item.cod_locatie,
        localitate: item.localitate,
        judet: item.judet,
      }));

      try {
        await sequelizeWarehouse.models.DimLocatie.bulkCreate(formattedLocatieData);
      } catch (err) {
      
        try {
          await addMessageToDatabase('Eroare la inserarea datelor in DIM_LOCATIE', "E", "Admin");
        } catch (logError) {
          console.error('Eroare la logarea mesajului în baza de date:', logError);
        }
      }
    }

    const existingDimTimp = await sequelizeWarehouse.models.DimTimp.findAll();
    
    if (existingDimTimp.length === 0) {
      const generateDateRange = (start, years) => {
        const dates = [];
        const startDate = new Date(start);
        const endDate = new Date(startDate);
        endDate.setFullYear(endDate.getFullYear() + years);

        for (let d = new Date(startDate); d <= endDate; d.setDate(d.getDate() + 1)) {
          const isWeekend = [6, 0].includes(d.getDay());
          dates.push({
            data: new Date(d).toISOString(),
            anul: d.getFullYear(),
            luna: d.getMonth() + 1,
            nume_luna: d.toLocaleString("ro-RO", { month: "long" }),
            trimestru: Math.ceil((d.getMonth() + 1) / 3),
            ziua: d.getDate(),
            ziua_saptamanii: d.getDay() || 7,
            nume_zi: d.toLocaleString("ro-RO", { weekday: "long" }),
            este_weekend: isWeekend ? 1 : 0,
          });
        }

        return dates;
      };

      await sequelizeWarehouse.models.DimTimp.bulkCreate(generateDateRange("2021-01-01", 9));
    }

    const existingDimMasina = await sequelizeWarehouse.models.DimMasina.findAll();
    
    if (existingDimMasina.length === 0) {
      const masinaData = await sequelizeOLTP.models.Masina.findAll({
        attributes: [
          ["cod_masina", "cod_masina"],
          ["marca", "marca"],
          ["model", "model"],
          ["data_achizitionare", "data_achizitionare"],
          ["data_revizie_urm", "data_revizie_urm"],
        ],
        raw: true,
      });

      const formattedMasinaData = masinaData.map(item => ({
        cod_masina: item.cod_masina,
        marca: item.marca,
        model: item.model,
        data_achizitionare: item.data_achizitionare,
        data_revizie_urm: item.data_revizie_urm,
      }));

      try {
        await sequelizeWarehouse.models.DimMasina.bulkCreate(formattedMasinaData);
      } catch (err) {
      
        try {
          await addMessageToDatabase('Eroare la inserarea datelor in DIM_MASINA', "E", "Admin");
        } catch (logError) {
          console.error('Eroare la logarea mesajului în baza de date:', logError);
        }
      }
    }

    const existingFCursa = await sequelizeWarehouse.models.FCursa.findAll();

    if (existingFCursa.length === 0) {
      try {

        const cursaData = await sequelizeOLTP.models.Factura.findAll({
          attributes: [
            [sequelizeOLTP.Sequelize.col("Cursa.cod_cursa"), "cod_cursa"],
            [sequelizeOLTP.Sequelize.col("Cursa->DetaliiCursa.nota_sofer"), "nota_sofer"],
            [sequelizeOLTP.Sequelize.col("Cursa->DetaliiCursa.nota_client"), "nota_client"],
            [sequelizeOLTP.Sequelize.col("Factura.cod_factura"), "cod_factura"],
            [sequelizeOLTP.Sequelize.col("Cursa.cod_client"), "cod_client"],
            [sequelizeOLTP.Sequelize.col("Cursa.cod_sofer"), "cod_sofer"],
            [sequelizeOLTP.Sequelize.col("Cursa.cod_masina"), "cod_masina"],
            [sequelizeOLTP.Sequelize.col("Cursa.cod_locatie"), "cod_locatie"],
            [sequelizeOLTP.Sequelize.col("Cursa->Angajat.cod_angajat"), "cod_angajat"],
            [sequelizeOLTP.Sequelize.col("Cursa->DetaliiCursa.data_cursa"), "data_cursa"],
          ],
          include: [
            {
              model: sequelizeOLTP.models.Cursa,
              attributes: [],
              include: [
                {
                  model: sequelizeOLTP.models.DetaliiCursa,
                  attributes: [],
                },
                {
                  model: sequelizeOLTP.models.Angajat,
                  attributes: [],
                },
              ],
            },
          ],
          raw: true,
        });

        const dimTimpData = await sequelizeWarehouse.models.DimTimp.findAll({
          attributes: ["cod_timp", "data"],
          raw: true,
        });

        const dimTimpMap = dimTimpData.reduce((map, dim) => {
          map[dim.data] = dim.cod_timp;
          return map;
        }, {});

        const formattedCursaData = cursaData.map(item => {
          const codTimp = dimTimpMap[item.data_cursa] || null;

          if (!codTimp) {
            console.warn(`Nu s-a găsit cod_timp pentru data_cursa: ${item.data_cursa}`);
            return null;
          }

          return {
            cod_cursa: item.cod_cursa,
            nota_sofer: item.nota_sofer,
            nota_client: item.nota_client,
            cod_factura: item.cod_factura,
            cod_client: item.cod_client,
            cod_sofer: item.cod_sofer,
            cod_masina: item.cod_masina,
            cod_locatie: item.cod_locatie,
            cod_angajat: item.cod_angajat,
            cod_timp: codTimp,
          };
        }).filter(item => item !== null);

        await sequelizeWarehouse.models.FCursa.bulkCreate(formattedCursaData);

      } catch (error) {
        console.error("Eroare la inserarea datelor in F_Cursa:", error);
      
        try {
          await addMessageToDatabase('Eroare la inserarea datelor in F_Cursa', "E", "Admin");
        } catch (logError) {
          console.error('Eroare la logarea mesajului în baza de date:', logError);
        }
      }
    }

    try {
      await addMessageToDatabase('Datele initiale au fost inserate cu succes in baza de date de tip warehouse!', "I", "Admin");
    } catch (_) {
      console.error('Eroare la logarea mesajului în baza de date:', logError);
    }
  } catch (err) {

    try {
      await addMessageToDatabase('Eroare la inserarea datelor initiale pentru OLTP', "E", "Admin");
    } catch (_) {
      console.error('Eroare la logarea mesajului în baza de date:', logError);
    }
  }
}