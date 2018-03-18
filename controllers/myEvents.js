 // // // // // // // // // // // // // // // // //
// Endpoint for the events the user has created  //
// // // // // // // // // // // // // // // // //

var express = require('express');
var router = express.Router();

// to show all the events the user has created 
router.get('/myEvents/:UserName',function(req,res,next){
    var Username = req.params.UserName;
    // connect to database 
    req.getConnection(function(err, connection) {
        if (err) {
          console.error("Error " + err);
          return next(err);
        }
        else {
            console.log("CONNECTED")
         
            var get_users_events = "SELECT * FROM event WHERE UserName = ?";
            var query = connection.query( get_users_events, [Username] , function(err, results, fields){
              if(err){
               console.error("Sql error " + err);
               res.writeHead(500,"This Username does not exist or has no events",{"content-type":"application/json"});
               res.end();
       
              }
              
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
         //Delete the user's event 

router.delete('/myEvents/:UserName/:EventId/delete', function(req,res,next){
    req.getConnection(function(err,connection){
        if(err){
            console.error("Error "+err);
            return next(err);
         }else{
            console.log("connected");
            connection.query('DELETE FROM event WHERE UserName = ? AND EventId = ?',[req.params.UserName,req.params.EventId],function(err,results){
                if(err){
                    console.error("Sql error " + err);
                    res.writeHead(500,"This Username has no event with this Id",{"content-type":"application/json"});
                    res.end();
            
                   }
                 else{
                     console.log("event has successfully been deleted");
                     res.writeHead(200,"event successfully deleted",{"content-type":"application/json"});
                     res.end();
                 }

            });
         }
    })
});

// Edit an event. 
router.put('/myEvents/:UserName/:EventId/edit',function(req,res,next){
    //connecting to database.
    req.getConnection(function(err, connection) {
        if (err) {
          console.error("Error " + err);
          return next(err);
        }
        else {
          console.log("CONNECTED");
        
        var EditEvent = "Update Event Set EventName = ?,CategoryId = ?, MainImage = ?, EventDate = ?, Description = ? WHERE Username = ? AND EventId = ? " ;
         
        var query = connection.query( EditEvent,[req.body.EventName,req.body.CategoryId,req.body.MainImage,req.body.EventDate,req.body.Description,req.params.UserName,req.params.EventId] , function(err, results){
           if(err){
            console.error("Sql error " + err);
            res.writeHead(500,"Internal error",{"content-type":"application/json"});
            res.end();
            }
            connection.query('UPDATE Location Set LocationName = ?,Latitude = ?,longitude = ? WHERE LocationId = ?',[req.body.LocationName,req.body.latitude,req.body.longitude,req.body.locationId ], function(err,results){
                if(err){
                    console.error("Sql error " + err);
                    res.writeHead(500,"Internal error",{"content-type":"application/json"});
                    res.end();
                }
           });
              res.writeHead(200,"event successfully editted",{"content-type":"application/json"});
              res.end();
             console.log("event successfully editted");
                  })
               
             }
           });


});
module.exports = router;