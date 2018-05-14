// fetch data from database
// '/ endpoint'

var express = require("express");
var router = express.Router();

// fecthing all the events
router.get("/events", function(req, res, next) {
  req.getConnection(function(err, connection) {
    if (err) {
      console.error("Error " + err);
      return next(err);
    } else {
      console.log("CONNECTED");

      var get_all_events =
        "SELECT EventName,MainImage,EventDate FROM event ORDER BY EventId DESC";
      var query = connection.query(get_all_events, [], function(
        err,
        results,
        fields
      ) {
        if (err) {
          console.error("Sql error " + err);
          res.writeHead(404, "Error not found", {
            "content-type": "application/json"
          });
          res.end();
        }

        var UserResults = [];
        for (var index in results) {
          var Rowobj = results[index];
          UserResults.push(Rowobj);
        }
        res.json(UserResults);
      });
    }
  });
});
//filter events by category
router.get("/events/filter/:Category", function(req,res){
  var category=req.params.Category;
  //connection to database
  req.getConnection(function(err,connection){
    if(err){
      console.log(err);
      res.writeHead(500, "Internal Error");
    }
    else{
      console.log("Connected");
      var input="SELECT event.* FROM event,eventcategory WHERE event.CategoryId=eventcategory.CategoryId AND CategoryName=?";
      connection.query(input, [category],function(err,results){
        if(err){
          res.writeHead(500, "Internal Error");
        }
        else{
          console.log("Success");
          return res.json(results);
        }
      });
    }

  });

});






// open specific event from home page.

router.get("/events/:eventId", function(req, res) {
  var Event_Id = req.params.eventId;
  //connection to mysql database
  req.getConnection(function(err, connection) {
    if (err) {
      console.error("Sql error " + err);
      res.writeHead(500, "Internal error", {
        "content-type": "application/json"
      });
      res.end();
    } else {
      console.log("CONNECTED");

      var get_specific_event =
        "Select event.*,location.latitude,location.Longitude,location.LocationName FROM Event,location WHERE event.locationId = Location.locationId AND event.EventId = ?";
      var query = connection.query(get_specific_event, [Event_Id], function(
        err,
        results,
        fields
      ) {
        if (err) {
          console.error("Sql error " + err);
          res.writeHead(404, "Event not found", {
            "content-type": "application/json"
          });
          res.end();
        }
        //res.writeHead(200,{"content-type":"application/json"});
        //res.end();
        var UserResults = [];
        for (var index in results) {
          var Rowobj = results[index];
          UserResults.push(Rowobj);
        }
        res.json(UserResults);
      });
    }
  });
});

// endpoint for user to attend  an event. //
// //                                 // //
router.put("/events/:eventId/attend", function(req, res, next) {
  var user_name = req.body.UserName;
  var EventId = req.params.eventId;
  req.getConnection(function(err, connection) {
    if (err) {
      console.error("Error " + err);
      return next(err);
    } else {
      var userattending = "INSERT INTO usersatteding VALUES (?,?)";
      connection.query(userattending, [user_name, EventId], function(
        err,
        results,
        fields
      ) {
        if (err) {
          console.error("Sql error " + err);
          res.writeHead(500, "Error inserting into database", {
            "content-type": "application/json"
          });
          res.end();
        }
        //successful insert and increase attending number in event table.
        var increaseAttending =
          "UPDATE event SET Attending = Attending + 1 WHERE EventId = ?";
        connection.query(increaseAttending, [EventId], function(err, results) {
          if (err) {
            console.error("Sql error " + err);
            res.writeHead(500, "Internal server error", {
              "content-type": "application/json"
            });
            res.end();
          }
        });
        res.writeHead(200, "successful", {
          "content-type": "application/json"
        });
        res.end();
      });
    }
  });
});
// for unattend an event. 

router.put("/events/:eventId/unattend", function(req, res, next) {  
  
  var user_name = req.body.UserName;
  var EventId = req.params.eventId;
  req.getConnection(function(err, connection) {
    if (err) {
      console.error("Error " + err);
      return next(err);
    } else {
       // check if user is attending the event first
      connection.query('SELECT * FROM usersatteding WHERE users_username = ? AND event_EventId = ?',[user_name,EventId],function(err,results){
        if(err){
          console.error("Sql error " + err);
          res.writeHead(500, "database error", {
            "content-type": "application/json"
          });
          res.end();
        }
        if(results.length >  0){
          if(results){
          console.log("user is already attending that event");
         
      var User_notattend = "DELETE FROM usersatteding WHERE users_username = ? AND event_EventId = ?";
      connection.query(User_notattend, [user_name, EventId], function(err,results,fields ) {
        
        if (err) {
          console.error("Sql error " + err);
          res.writeHead(500, "database error", {
            "content-type": "application/json"
          });
          res.end();
        }
        
        //Decrease number of users attending that event 
         var Decrease_Attending ="UPDATE event SET Attending = Attending - 1 WHERE EventId = ?";
        connection.query(Decrease_Attending, [EventId], function(err, results) {
          if (err) {
            console.error("Sql error " + err);
            res.writeHead(500, "Internal server error", {"content-type": "application/json" });
            res.end();
          }
          res.writeHead(200, "successful", {
            "content-type": "application/json"
          });
          res.end();
        });
            
      });
       }
    } else{
      res.writeHead(400, "forbidden", {
        "content-type": "application/json"
      });
      res.end();
    }
   });
 }
 });
});
// endpoint for user to like or be interested in an event
router.post('/events/:eventId/like', function (req, res) {

  var eventId = req.params.eventId;
  var UserName = req.body.UserName;
  req.getConnection(function (err, connection) {
    if (err) {
      console.error("Sql error " + err);
      res.writeHead(500, "Internal error", { "content-type": "application/json" });
      res.end();
    }
    else {

      var input = 'INSERT INTO usersinterested(users_username,event_EventId) VALUES (?,?)';
      connection.query(input, [UserName, eventId], function (err, results) {
        if (err) {
          console.log('SQL Error' + err);
        }
        //res.writeHead(200, "Event Liked", { "Content-type": "application/json" });
      connection.query('UPDATE event SET Interested=Interested+1 WHERE eventId='+eventId,[eventId], function(err,results){
        if(err){
          console.log('SQL Error' +err);
        }
      });
        console.log("Event Liked");
        console.log(res.json(results));
        res.end();
      });
    };
  });
});
  // handle user unliking an event
router.put('/events/:eventId/unlike', function(req,res){
  var eventId = req.params.eventId;
  var UserName = req.body.UserName;
  req.getConnection(function (err, connection) {
    if (err) {
      console.log("Sql error " + err);
      res.writeHead(500, "Internal error", { "content-type": "application/json" });
      res.end();
    }
    else {
      var input='DELETE FROM usersinterested WHERE users_username=? AND event_EventId=?';
      connection.query(input,[UserName,eventId], function(err,results){
        if (err){
          console.log('SQL Error'+err);
        }
        else{
          console.log('Successfully Deleted!');
          }
      connection.query('UPDATE event SET Interested=Interested-1 WHERE eventId='+eventId,[eventId], function(err,results){
        if(err){
          console.log('SQL Error'+err);
        }
        else{
          console.log('Decremented!');
        }
      });

      });
    }
    res.end();
});
});

module.exports = router;
