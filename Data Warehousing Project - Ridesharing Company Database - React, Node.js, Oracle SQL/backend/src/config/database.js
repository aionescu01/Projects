const { Sequelize } = require("sequelize");
require('dotenv').config();

const sequelizeOLTP = new Sequelize({
  dialect: process.env.DB_DIALECT,
  host: process.env.DB_HOST,
  port: process.env.DB_PORT,
  username: process.env.DB_USER,
  password: process.env.DB_PASS,
  database: process.env.DB_SID_OLTP,
  quoteIdentifiers: false,
  //logging: false,
});

const sequelizeWarehouse = new Sequelize({
  dialect: process.env.DB_DIALECT,
  host: process.env.DB_HOST,
  port: process.env.DB_PORT,
  username: process.env.DB_USER,
  password: process.env.DB_PASS,
  database: process.env.DB_SID_WAREHOUSE,
  quoteIdentifiers: false,
  //logging: false,
});

module.exports = { sequelizeOLTP, sequelizeWarehouse };