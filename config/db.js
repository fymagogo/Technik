   // // // // // // // // // // // //
 // MYSQL DATABASE CONFIGURATION  //
// // // // // // // // // // // //
var mysql = require('mysql');

var myConnection = require('express-myconnection');

    dbOptions = {
      host: '127.0.0.1',
      user: 'root',
      password: 'Pedigree57',
      port: 3306,
      database: 'EventHub'
    };

module.exports = myConnection;
