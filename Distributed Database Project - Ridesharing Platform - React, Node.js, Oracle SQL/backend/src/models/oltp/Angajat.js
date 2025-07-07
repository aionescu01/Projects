const { DataTypes } = require("sequelize");
const { sequelizeOLTP } = require("../../config/database");

const Angajat = sequelizeOLTP.define("Angajat", 
    {
        cod_angajat: {
            type: DataTypes.INTEGER,
            primaryKey: true,
            autoIncrement: true,
        },
        nume: {
            type: DataTypes.STRING(25),
            allowNull: false,
        },
        prenume: {
            type: DataTypes.STRING(25),
            allowNull: false,
        },
        nr_telefon: {
            type: DataTypes.STRING(12),
            allowNull: false,
        },
        tip_angajat: {
            type: DataTypes.STRING(25),
            allowNull: false,
        },
        data_nastere: {
            type: DataTypes.DATE,
            allowNull: false,
        },
        data_angajare: {
            type: DataTypes.DATE,
            allowNull: false,
        },
        salariu: {
            type: DataTypes.INTEGER,
            allowNull: false,
        },
        cod_masina: {
            type: DataTypes.INTEGER,
        },
    },
    {
        freezeTableName: true,
        tableName: "ANGAJAT",
        timestamps: false,
    }
);

module.exports = Angajat;