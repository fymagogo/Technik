  //  // // // // // // // // // // // // // //
  //  Searching for events in the database.  //
  // // // // // // // // // // // // // // //
var express = require('express');
var router = express.Router();

router.get('/search',function(req,res){
    console.log(req.query);
    console.log(req.query.search_query);


    

});

module.exports = router;