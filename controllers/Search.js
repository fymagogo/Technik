  //  // // // // // // // // // // // // // //
  //  Searching for events in the database.  //
  // // // // // // // // // // // // // // //
var express = require('express');
var router = express.Router();

router.get('/results',function(req,res){
    console.log(req.query);
    console.log(req.query.search_query);
    // pick search words from the query string
    var results = str.split("+");

    

});

module.exports = router;