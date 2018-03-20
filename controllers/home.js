// fetch data from database
// '/ endpoint'

var express = require('express');
var router = express.Router();

// fecthing all the events 
router.get('/events', function (req, res, next) {

  req.getConnection(function (err, connection) {
    if (err) {
      console.error("Error " + err);
      return next(err);
    }
    else {
      console.log("CONNECTED")
      var get_all_events = "SELECT EventName,MainImage,EventDate FROM event ORDER BY EventId DESC";
      var query = connection.query(get_all_events, [], function (err, results, fields) {
        if (err) {
          console.error("Sql error " + err);
          // res.writeHead(500,"Internal error",{"content-type":"application/json"});
          // res.end();
        }
        // res.writeHead(200,{"content-type":"application/json"});
        //res.end();
        //   var UserResults = [];
        /*    for (var index in results){
                var Rowobj = results[index];
                UserResults.push(Rowobj);
            }
            */
        return res.json(results);
        //  res.json(results);
      });
    }
  });
});
// open specific event from home page.

router.get('/events/:eventId', function (req, res) {
  var Event_Id = req.params.eventId;
  //connection to mysql database
  req.getConnection(function (err, connection) {
    if (err) {
      console.error("Sql error " + err);
      //    res.writeHead(500,"Internal error",{"content-type":"application/json"});
      res.end();
    }
    else {
      console.log("CONNECTED")
      var get_specific_event = "SELECT event.*, location.latitude,location.longitude,location.locationName FROM event,location WHERE event.locationId=location.locationId AND event.EventId=?";
      var query = connection.query(get_specific_event, [Event_Id], function (err, results, fields) {
        if (err) {
          console.error("Sql error " + err);
          res.writeHead(500, "Event does not exist", { "content-type": "application/json" });
          res.end();

        }
        //successfully sends.
        //  res.writeHead(200,{"content-type":"application/json"});
        // res.end();

        var UserResults = [];
        for (var index in results) {
          var Rowobj = results[index];
          UserResults.push(Rowobj);
        }
        res.json(UserResults);
        // res.end();
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
