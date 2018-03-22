// file to handle settings for the app 

// user should be able to change or edit their categories  
//and edit their profile info
const bcrypt = require('bcrypt');
var express = require('express');
var router = express.Router();

// show the user's subscribed categories 
router.get('/settings/UserCategory/:UserName',function(req,res){
    var Username = req.params.UserName;
    req.getConnection(function(err, connection) {
        if (err) {
         console.error("Error " + err);
         return next(err);
    }
    else {
          console.log("CONNECTED")
         
         var get_users_categories = "SELECT eventcategory.CategoryName,usersubscriptions.CategoryId FROM eventcategory INNER JOIN usersubscriptions ON eventcategory.CategoryId = usersubscriptions.CategoryId AND usersubscriptions.Username = ?";
         var query = connection.query( get_users_categories, [Username] , function(err, results, fields){
             if(err){
             console.error("Sql error " + err);
             res.writeHead(500,"Internal error",{"content-type":"application/json"});
             res.end();
       
             }            
             return res.json(results);
         });
         }
     });    
});

//edit the User's subscribed categories.
//add or suscribe to a new category 
router.put('/settings/addCategory/:UserName/:categoryName', function(req,res){
    var username = req.params.UserName;
    var categoryname = req.params.categoryName;

  req.getConnection(function(err,connection){
    if(err){
        console.error("error connecting" + err);
    }
    else{
        var addCategory = "INSERT INTO usersubscriptions VALUES (?, (SELECT CategoryId from eventcategory WHERE CategoryName = ?))";	 	        	
        var query = connection.query(addCategory,[username,categoryname],function(err,results,fields){
            if(err){
             console.error("Sql error " + err);
             res.writeHead(500,"Internal error",{"content-type":"application/json"});
             res.end();
            }
            else{
                res.writeHead(200, "successful", {
                    "content-type": "application/json"
                  });
                  res.end();
            }
        });
    }
  });
});
  // Route to handle unsuscribing to an event category 
router.delete('/settings/unsuscribeCategory/:UserName/:categoryName', function(req,res){
    var username = req.params.UserName;
    var categoryname = req.params.categoryName;

  req.getConnection(function(err,connection){
    if(err){
        console.error("error connecting" + err);
    }
    else{
        var removecategorySuscription = "DELETE from usersubscriptions WHERE Username = ? AND CategoryId = (SELECT CategoryId FROM eventcategory WHERE CategoryName = ?)";	 	        	
        var query = connection.query(removecategorySuscription,[username,categoryname],function(err,results,fields){
            if(err){
             console.error("Sql error " + err);
             res.writeHead(500,"Internal error",{"content-type":"application/json"});
             res.end();
            }
            else{
                res.writeHead(200, "successful", {
                    "content-type": "application/json"
                  });
                  res.end();
            }
        });
    }
  });
});

 //Edit users profile.
 router.put('/settings/editProfile/:userName',function(req,res){
     var username = req.params.userName;
    req.getConnection(function(err, connection) {
        if (err) {
          console.error("Error " + err);
          return next(err);
        }
        else {
          console.log("CONNECTED");
        
        var Edit_user_profile = "Update users Set Firstname = ?,Lastname = ?, username = ?, Email = ?, PhoneNumber = ? WHERE Username = ?" ;
         
        var query = connection.query( Edit_user_profile,[req.body.FirstName,req.body.LastName,req.body.UserName,req.body.Email,req.body.PhoneNumber,username] , function(err, results){
           if(err){
            console.error("Sql error " + err);
            res.writeHead(500,"Internal error",{"content-type":"application/json"});
            res.end();
            }
            
              res.writeHead(200,"profile successfully editted",{"content-type":"application/json"});
              res.end();
             console.log("profile successfully editted");
                  })
               
             }
           });
 });
 // editPassword
 router.put('/settings/changePassword/:userName',function(req,res){

    const saltRounds = 10; 
     var old_password = req.body.oldPassword;
     var new_password = req.body.newPassword;
// connect to databse
     req.getConnection(function(err, connection) {
        if (err) {
          console.error(" Connection Error " + err);
          return next(err);
        }
        else {
          console.log("CONNECTED");
          // take user's old password and check if its in the database and matches the guys password 
      
            connection.query( "SELECT password from users WHERE username = ?",[req.params.userName], function(err, results, fields){
                if(err){
                 console.error("Sql error " + err);
                 }
        
                 if(results.length > 0){
                    if(results) {
                      console.log("the user exists in the database ");

                       const passwordhash = results[0].password.toString();
                       console.log(passwordhash);
                       bcrypt.compare(old_password, passwordhash ,function(err,response){
                         console.log(response);

                         if(response === true){
                            bcrypt.hash(new_password, saltRounds, function(err, hash) {
                                Insertnew_password = "UPDATE users SET password = ? WHERE userName = ?";
                                 var query = connection.query( Insertnew_password,[hash,req.params.userName] , function(err, results){
                                 if(err){
                                  console.error("Sql error " + err);
                                  res.writeHead(500,"Internal error",{"content-type":"application/json"});
                                  res.end();
                                  }
                                     console.log("password successfully changed");
                                     res.writeHead(200,"successful",{"content-type":"application/json"});
                                     res.end();
                            })
                        });

                          } else{
                            res.writeHead(400,"wrong password",{"content-type":"application/json"});
                            res.end();
                         }
                       });
                    }
                }else{ 
                    res.writeHead(500,"Internal error",{"content-type":"application/json"});
                    res.end();
                   }                    
               });
           }
       }); 
    });
    // route for a user to delete his profile
    router.delete('/settings/deleteAccount/:userName',function(req,res){
        req.getConnection(function(err, connection) {
            if (err) {
              console.error("Error " + err);
              return next(err);
            }
            else {
              console.log("CONNECTED");
            
            var Delete_profile = "DELETE FROM users WHERE username = ?";
             
            var query = connection.query( Delete_profile,[req.params.userName] , function(err, results){
               if(err){
                console.error("Sql error " + err);
                res.writeHead(500,"Internal error",{"content-type":"application/json"});
                res.end();
                }
                
                  res.writeHead(200,"profile successfully deleted",{"content-type":"application/json"});
                  res.end();
                      });                 
                 }
          });
     });
         
module.exports = router;