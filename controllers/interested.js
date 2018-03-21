var express=require('express');
var router=express.Router();

//attending route
router.get('/myattending/:UserName', function(req,res){
  var UserName=req.params.UserName;
  req.getConnection(function(err,connection){
    if (err) {
        console.error("Error " + err);
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
//attending route
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


module.exports=router;