const express = require("express");
const router = express.Router();
const { createAngajat, getAllAngajati, getAngajatById, updateAngajat, deleteAngajat } = require("../../controllers/oltp/angajatController");

/**
 * @swagger
 * tags:
 *   name: OLTP - Angajati
 *   description: API pentru gestionarea angajaților
 */

/**
 * @swagger
 * /api/oltp/angajat:
 *   post:
 *     summary: Creează un angajat
 *     description: Adaugă un nou angajat în baza de date.
 *     tags: [OLTP - Angajati]
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             properties:
 *               nume:
 *                 type: string
 *                 example: Ion
 *               prenume:
 *                 type: string
 *                 example: Popescu
 *               nr_telefon:
 *                 type: string
 *                 example: 0712345678
 *               tip_angajat:
 *                 type: string
 *                 example: Permanent
 *               data_nastere:
 *                 type: string
 *                 format: date
 *                 example: "1985-06-15"
 *               data_angajare:
 *                 type: string
 *                 format: date
 *                 example: "2020-01-10"
 *               salariu:
 *                 type: integer
 *                 example: 4500
 *               cod_masina:
 *                 type: integer
 *                 example: 12345
 *               dispecerat:
 *                 type: string
 *                 example: "Da"
 *     responses:
 *       201:
 *         description: Angajat creat cu succes.
 *       500:
 *         description: Eroare la crearea angajatului.
 */
router.post("/", createAngajat);

/**
 * @swagger
 * /api/oltp/angajat:
 *   get:
 *     summary: Preia toți angajații
 *     description: Returnează lista completă a angajaților din baza de date.
 *     tags: [OLTP - Angajati]
 *     responses:
 *       200:
 *         description: Lista angajaților.
 *         content:
 *           application/json:
 *             schema:
 *               type: array
 *               items:
 *                 type: object
 *                 properties:
 *                   cod_angajat:
 *                     type: integer
 *                     example: 1
 *                   nume:
 *                     type: string
 *                     example: Ion
 *                   prenume:
 *                     type: string
 *                     example: Popescu
 *                   nr_telefon:
 *                     type: string
 *                     example: 0712345678
 *                   tip_angajat:
 *                     type: string
 *                     example: Permanent
 *                   data_nastere:
 *                     type: string
 *                     format: date
 *                     example: "1985-06-15"
 *                   data_angajare:
 *                     type: string
 *                     format: date
 *                     example: "2020-01-10"
 *                   salariu:
 *                     type: integer
 *                     example: 4500
 *                   cod_masina:
 *                     type: integer
 *                     example: 12345
 *                   dispecerat:
 *                     type: string
 *                     example: "Da"
 *       500:
 *         description: Eroare la preluarea angajaților.
 */
router.get("/", getAllAngajati);

/**
 * @swagger
 * /api/oltp/angajat/{id}:
 *   get:
 *     summary: Preia un angajat după ID
 *     description: Returnează datele unui angajat specificat prin ID.
 *     tags: [OLTP - Angajati]
 *     parameters:
 *       - name: id
 *         in: path
 *         required: true
 *         description: ID-ul angajatului.
 *         schema:
 *           type: integer
 *           example: 1
 *     responses:
 *       200:
 *         description: Angajat găsit.
 *       404:
 *         description: Angajatul nu a fost găsit.
 *       500:
 *         description: Eroare la preluarea angajatului.
 */
router.get("/:id", getAngajatById);

/**
 * @swagger
 * /api/oltp/angajat/{id}:
 *   put:
 *     summary: Actualizează un angajat
 *     description: Actualizează datele unui angajat specificat prin ID.
 *     tags: [OLTP - Angajati]
 *     parameters:
 *       - name: id
 *         in: path
 *         required: true
 *         description: ID-ul angajatului.
 *         schema:
 *           type: integer
 *           example: 1
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             properties:
 *               nume:
 *                 type: string
 *                 example: Ion
 *               prenume:
 *                 type: string
 *                 example: Popescu
 *               nr_telefon:
 *                 type: string
 *                 example: 0712345678
 *               tip_angajat:
 *                 type: string
 *                 example: Permanent
 *               data_nastere:
 *                 type: string
 *                 format: date
 *                 example: "1985-06-15"
 *               data_angajare:
 *                 type: string
 *                 format: date
 *                 example: "2020-01-10"
 *               salariu:
 *                 type: integer
 *                 example: 5000
 *               cod_masina:
 *                 type: integer
 *                 example: 12345
 *               dispecerat:
 *                 type: string
 *                 example: "Da"
 *     responses:
 *       200:
 *         description: Angajat actualizat cu succes.
 *       404:
 *         description: Angajatul nu a fost găsit.
 *       500:
 *         description: Eroare la actualizarea angajatului.
 */
router.put("/:id", updateAngajat);

/**
 * @swagger
 * /api/oltp/angajat/{id}:
 *   delete:
 *     summary: Șterge un angajat
 *     description: Șterge un angajat specificat prin ID din baza de date.
 *     tags: [OLTP - Angajati]
 *     parameters:
 *       - name: id
 *         in: path
 *         required: true
 *         description: ID-ul angajatului.
 *         schema:
 *           type: integer
 *           example: 1
 *     responses:
 *       200:
 *         description: Angajat șters cu succes.
 *       404:
 *         description: Angajatul nu a fost găsit.
 *       500:
 *         description: Eroare la ștergerea angajatului.
 */
router.delete("/:id", deleteAngajat);

module.exports = router;