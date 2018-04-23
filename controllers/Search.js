  //  // // // // // // // // // // // // // //
  //  Searching for events in the database.  //
  // // // // // // // // // // // // // // //
var express = require('express');
var router = express.Router();

router.get('/search',function(req,res){

  var searchquery = req.query.search_query.toString();
  console.log(searchquery);
  //query to search the database.

    req.getConnection(function(err, connection) {
      if (err) {
        console.error("Error " + err);
        return next(err);
      } else {
        console.log("CONNECTED");

        var search_for_event =
          "SELECT eventname,eventdate,Mainimage from event WHERE Match(eventName,Description) Against (?)";
        var query = connection.query(search_for_event, [searchquery], function(
          err,
          results,
          fields
        ) {
          if (err) {
            console.error("Sql error " + err);
            res.writeHead(404, "Error event not found", {
              "content-type": "application/json"
            });
            res.end();
          }

          return res.json(results);
        });
      }
    })
});

module.exports = router;
