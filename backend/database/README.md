Example of usage in jQuery

    $.postJSON = function(url, data, callback) {
        return jQuery.ajax({
            headers: { 
               'Accept': 'application/json',
               'Content-Type': 'application/json' 
            },
            'url': url,
            type:'POST',
            'data': JSON.stringify(data),
            'dataType': 'json',
            'success': callback
        });
    };


    jQuery.postJSON("http://localhost:8080/user", {username:"kalle", firstname:'krantz',    lastname:'krantz',password:'wiie'}, function(data){
        jQuery.getJSON("http://localhost:8080/user", function(data){
             console.log("Got data from http://localhost:8080/user via GET");
             console.log(data); 
             jQuery.postJSON("http://localhost:8080/user/0/robot", {robotName:'wiie', robotDesign:'{}'}, function(data){
                 console.log("Got data from http://localhost:8080/user/0/robot via POST");
                 console.log(data); 
                 jQuery.getJSON("http://localhost:8080/user/0/robot", function(data){
                     console.log("Got data from http://localhost:8080/user/0/robot via GET");
                     console.log(data); 
                 });
             });
        });
    console.log("Got data from http://localhost:8080/user via POST");
    console.log(data); 

    });
