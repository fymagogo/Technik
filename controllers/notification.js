//NOTIFICATION ROUTE

var express = require("express");
var router = express.Router();
//connection stage
router.get('/notifications/:UserName',function(req,res){
    var Username = req.params.UserName;
    req.getConnection(function(err,connection){
      
        if (err) {
          console.error("Error " + err);
          return next(err);
        }
        else {
          console.log("CONNECTED");
          //query is supposed to give days left for events that the user has suscribed to and the events that the user is attending
          connection.query("SELECT Event.EventName,Event.mainimage,usersatteding.users_username,TIMESTAMPDIFF(DAY,CURDATE(),EventDate) AS Days_Left FROM Event,usersatteding WHERE Event.EventId = usersatteding.event_EventId AND usersatteding.users_username = ? UNION SELECT Event.EventName,Event.mainimage,usersinterested.users_username,TIMESTAMPDIFF(DAY,CURDATE(),EventDate) AS Days_Left FROM Event,usersinterested WHERE Event.EventId = usersinterested.event_EventId AND usersinterested.users_username = ?",[Username,req.params.UserName],function(err,results){
            if(err){
              console.error("Sql error " + err);
              res.writeHead(500,"Internal error",{"content-type":"application/json"});
              res.end();
              }
              return res.json(results);
          })
        }
    })
  });

  module.exports=router;
