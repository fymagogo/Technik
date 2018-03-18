var express = require('express');
var router = express.Router();

// endpoint to handle creation of events by a logged in user

router.post('/createEvent',function(req,res){
    var EventDetails = req.body;
    console.log(EventDetails);

    //connecting to database...again
    req.getConnection(function(err, connection) {
        if (err) {
          console.error("Error " + err);
          return next(err);
        }
        else {
          console.log("CONNECTED")
     
        var CreateEvent = "INSERT INTO Event (EventName,CategoryId,MainImage,EventDate,LocationId,UserName,Description) values (?,?,?,?,?,?,?) ";
         // inserting the event details into the table.
     
         var EventName =  EventDetails.EventName;
         var CategoryId = EventDetails.CategoryId;
          var MainImage = EventDetails.MainImage;
          var EventDate= EventDetails.EventDate;
          var LocationId = EventDetails.LocationId;
          var UserName = EventDetails.Username;
          var Description = EventDetails.Description;
          var locationName = EventDetails.LocationName;
          var latitude = EventDetails.Latitude;
          var longitude = EventDetails.Longitude;
        
        var query = connection.query( CreateEvent,[EventName,CategoryId,MainImage,EventDate,LocationId,UserName,Description] , function(err, results){
           if(err){
            console.error("Sql error " + err);
            res.writeHead(500,"Internal server error",{"content-type":"application/json"});
            res.end();
            } 
              connection.query("INSERT INTO location (latitude,longitude,locationName) VALUES ('?','?','?')",[latitude,longitude,locationName],function(err,results){
                if(err){
                    console.error("Sql error " + err);
                    res.writeHead(500,"Internal server error",{"content-type":"application/json"});
                    res.end();
                    } });

              res.writeHead(200,"event successfully created",{"content-type":"application/json"});
              res.end();
             console.log("event successfully created");
                  });
               
             }  
           });
});

module.exports = router;