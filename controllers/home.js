// fetch data from database
// '/ endpoint'

var express = require('express');
var router = express.Router();

// fecthing all the events 
router.get('/events',function(req, res, next){

 req.getConnection(function(err, connection) {
   if (err) {
     console.error("Error " + err);
     return next(err);
   }
   else {
     console.log("CONNECTED")

     var get_all_events = "SELECT EventName,MainImage,EventDate FROM event ORDER BY EventId DESC";
     var query = connection.query( get_all_events, [] , function(err, results, fields){
       if(err){
        console.error("Sql error " + err);
        res.writeHead(500,"Internal error",{"content-type":"application/json"});
        res.end();

       }
       //res.writeHead(200,{"content-type":"application/json"});
       //res.end();
       var UserResults = [];
       for (var index in results){
           var Rowobj = results[index];
           UserResults.push(Rowobj);
       }
        res.json(UserResults);
      });
    }
   });
});
  // open specific event from home page.

router.get('/events/:eventId',function(req,res){
  var Event_Id = req.params.eventId;
  //connection to mysql database
  req.getConnection(function(err, connection) {
    if (err) {
      console.error("Sql error " + err);
      res.writeHead(500,"Internal error",{"content-type":"application/json"});
      res.end();
    }
    else {
      console.log("CONNECTED")
 
      var get_specific_event = "Select event.*,location.latitude,location.Longitude,location.LocationName FROM Event,location WHERE event.locationId = Location.locationId AND event.EventId = ?";
      var query = connection.query( get_specific_event, [Event_Id] , function(err, results, fields){
        if(err){
         console.error("Sql error " + err);
         res.writeHead(500,"Event does not exist",{"content-type":"application/json"});
         res.end();
 
        }
        //successfully sends.

        //res.writeHead(200,{"content-type":"application/json"});
        //res.end();
        var UserResults = [];
        for (var index in results){
            var Rowobj = results[index];
            UserResults.push(Rowobj);
            }
         res.json(UserResults);
         });
       }
  });

});

  // endpoint for user to like or be interested in an event  







 module.exports = router;
