/**
 * Author: Karl Johan Krantz
 * This goal of this module is to give an easy way of reaching the storage module via rest.
 *
 *Requires: jQuery
 **/
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

$.putJSON = function(url, data, callback) {
    return jQuery.ajax({
        headers: { 
            'Accept': 'application/json',
            'Content-Type': 'application/json' 
            },
        'url': url,
        type:'PUT',
        'data': JSON.stringify(data),
        'dataType': 'json',
        'success': callback
    });
};

var storageConnector = function(uri){
    return {
        getUserById: function(id, callback){
            jQuery.getJSON("http://"+uri+":8080/user/"+id,callback);
        },
        getUserList: function(callback){
            jQuery.getJSON("http://"+uri+":8080/user", callback);
        },
        getRobotById: function(userId, robotId,callback){
            jQuery.getJSON("http://"+uri+":8080/user/"+userId+"/robot/"+robotId, callback);
        },
        getRobotList: function(userId, callback){
            jQuery.getJSON("http://"+uri+":8080/user/"+userId+"/robot/", callback);
        },
        postUser: function(postObject, callback){
            jQuery.postJSON("http://"+uri+":8080/user", postObject, callback);
        },
        postRobot: function(userId, postObject, callback){
            jQuery.postJSON("http://"+uri+":8080/user/"+userId+"/robot", postObject, callback);
        },
        putUser: function(userId, patchObject, callback){
            jQuery.putJSON("http://"+uri+":8080/user/"+userId, patchObject, callback);
        },
        putRobot: function(userId, robotId, patchObject, callback){
            jQuery.putJSON("http://"+uri+":8080/user/"+userId+"/robot/"+robotId, patchObject, callback);
        },
    };
}
