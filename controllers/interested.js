//ROUTES FOR SHOWING THE EVENTS THE USER IS ATTENDING
//ROUTES TO SHOW THE EVENTS THE USER IS INTERESTED IN 
//ROUTES TO SHOW THE EVENTS BASED ON THE CATEGORIES THE USER HAS SUSCRIBED TO 

var express=require('express');
var router=express.Router();

//route to show events the user is attending
router.get('/myattending/:UserName', function(req,res){
  var UserName=req.params.UserName;
  req.getConnection(function(err,connection){
    if (err) {
        console.error("Error connecting to database" + err);
        return next(err);
    }
    else {
        console.log("CONNECTED");
        var input='SELECT Event.EventName,Event.mainimage,usersatteding.users_username FROM Event,usersatteding WHERE Event.EventId = usersatteding.event_EventId AND usersatteding.users_username =?';
        connection.query(input,[UserName], function(err,results){
            if (err) {
                console.error("Sql error " + err);
                res.writeHead(500, "Not Found", { "content-type": "application/json" });
                res.end();
            }
            else{
                return res.json(results);
            }
        });
    }
  });
});
//route to show all the events the user is interested in.
router.get('/myinterested/:UserName', function(req,res){
    var UserName=req.params.UserName;
    req.getConnection(function(err,connection){
      if (err) {
          console.error("Error " + err);
          return next(err);
      }
      else {
          console.log("CONNECTED");
          var input='SELECT Event.EventName,Event.mainimage,usersinterested.users_username FROM Event,usersinterested WHERE Event.EventId = usersinterested.event_EventId AND usersinterested.users_username =?';
          connection.query(input,[UserName], function(err,results){
              if (err) {
                  console.error("Sql error " + err);
                  res.writeHead(500, "Not Found", { "content-type": "application/json" });
                  res.end();
              }
              else{
                  return res.json(results);
              }
          });
  
      }  
    });
  });
// endpoint to show the events based on the categories the user has suscribed to. 
router.get('/myeventsuscriptions/:userName',function(req,res,next){
  var UserName=req.params.userName;
  req.getConnection(function(err,connection){
    if (err) 
    {
        console.error("Error " + err);
        return next(err);
    }
    else {
        console.log("CONNECTED");
        var input='SELECT event.*,usersubscriptions.Username from event,usersubscriptions where event.CategoryId = usersubscriptions.CategoryId AND usersubscriptions.username = ?';
        connection.query(input,[UserName], function(err,results){
            if (err) {
                console.error("Sql error " + err);
                res.writeHead(500, "Not Found", { "content-type": "application/json" });
                res.end();
            }
            else{
                console.log("successful");
                return res.json(results);
            }
        });
    }  
  });
});

module.exports=router;