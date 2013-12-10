matchMakerSocket = new WebSocket("ws://127.0.0.1:61989/");
userName = "bla";
/**
 * Called when the websocket has connected to the server. 
 * @method onopen
 * @return 
 */
matchMakerSocket.onopen = function() {
	window.frames[0].document.getElementById("messages").innerHTML+="Welcome!";
};

/**
 * Handler for incoming messages from the server. Writes the message in debug, and if a port is sent, starts a connection to a matchsocket
 * @method onmessage
 * @param {} evt
 * @return 
 */
matchMakerSocket.onmessage = function (evt) {
    window.frames[0].document.getElementById("debug").innerHTML+="<br>"+evt.data;
	if(evt.data.indexOf("port:") !== -1){
		start = evt.data.indexOf(":")+1;
		PORT=evt.data.substring(start,start+5);
		startNewMatch(PORT);
	}
};
/**
 * Starts a new matchSocket.
 * @method startNewMatch
 * @param {} port
 * @return 
 */
function startNewMatch (port){
	matchSocket = new WebSocket("ws://127.0.0.1:"+port);
	/**
	 * When the socket is connected, responds with a request to join a matchhandler
	 * @method onopen
	 * @return 
	 */
	matchSocket.onopen = function() {
		matchMakerSocket.close();
		matchSocket.send("0"+makeReq(port,userName));
		window.frames[0].document.getElementById("messages").innerHTML="Messages: Loading scene";
	}
	/**
	 * Handles messages from the matchsocket
	 * @method onmessage
	 * @param {} evt
	 * @return 
	 */
	matchSocket.onmessage = function (evt){
		if(evt.data.indexOf("NetworkError::")!=-1){
			
		}else if(evt.data.indexOf("StartingState::")!=-1){
			var tmp = evt.data.replace("StartingState::","");
			yourIndex = tmp[0];
			var startingState = JSON.parse(tmp.replace(tmp[0],""));
			currentMatchState = new NetworkMatch(startingState);
			nextMatchState = new NetworkMatch(startingState);
			console.log(window.frames[0].document);
			
			parent.changeFrame('battle.html');
			console.log(window.frames[0].document);
			
			//window.frames[0].document.getElementById('arena').innerHTML="<iframe src='../3d/arena.html' width='600px' height='500px'></iframe>";
		}else if(evt.data.indexOf("UpdateState::")!=-1){
			var newState = JSON.parse(evt.data.replace("UpdateState::",""));
			nextMatchState = new NetworkMatch(newState);
			window.frames[0].document.getElementById("debug").innerHTML+=" x: " + nextMatchState.robots[1].getX() +" dx: " + nextMatchState.robots[1].getdX() + " y: " + nextMatchState.robots[1].getZ() + " dY: " + nextMatchState.robots[1].getdZ()+"<br>"+" rot: "+nextMatchState.robots[1].getRotation()+" angVel: "+nextMatchState.robots[1].getAngularVelocity()+"<br>";
			window.frames[0].document.getElementById("messages").innerHTML="Messages: Game is on man!!!";
		}else if(evt.data.indexOf("MatchEnded::")!=-1){
			window.frames[0].document.getElementById("messages").innerHTML="Messages: Game over man, game over";
			window.frames[0].document.getElementById("debug").innerHTML+="</br>"+evt.data;
			matchSocket.close();
		}
		//document.getElementById("debug").innerHTML+="   "+evt.data;
	}
	/**
	 * Description
	 * @method onclose
	 * @return 
	 */
	matchSocket.onclose = function(){
	};
	/**
	 * Description
	 * @method onerror
	 * @return 
	 */
	matchSocket.onerror = function(){
	};
}
/**
 * Description
 * @method onclose
 * @return 
 */
matchMakerSocket.onclose = function() {
};

/**
 * Description
 * @method onerror
 * @param {} err
 * @return 
 */
matchMakerSocket.onerror = function(err) {
    alert("Error: " + err);
};

/**
 * Make a json-object containing username, matchtype and robot id as a request to join a match-lobby.
 * @method makeMatchMakerRequest
 * @return 
 */
function makeMatchMakerRequest(){
	userName = window.frames[0].document.getElementById("username").value;
	matchType  = window.frames[0].document.getElementById("matchtype").selectedIndex;
	var message=JSON.stringify(new request(userName,12,matchType));
	matchMakerSocket.send(message);
	window.frames[0].document.getElementById("messages").innerHTML="Messages: Entering "+window.frames[0].document.getElementById("matchtype").options[window.frames[0].document.getElementById("matchtype").selectedIndex].value+" lobby";
}
/**
 * Request to join the matchhandler associated with the connected port.
 * @method makeReq
 * @param {} port
 * @param {} username
 * @return message
 */
function makeReq(port,username){
	message=JSON.stringify(new joinRequest(username,port));
	return message;
}
/**
 * prototype for the json-object used to join a matchlobby
 * @method request
 * @param {} name
 * @param {} id
 * @param {} type
 * @return 
 */
function request(name, id, type){
	this.user=name;
	this.robotId=id;
	this.type=type;
}
/**
 * prototype for the json-object used to join a matchhandler
 * @method joinRequest
 * @param {} name
 * @param {} port
 * @return 
 */
function joinRequest(name, port){
	this.user=name;
	this.port=port;
}

/**
 * Send a keypress to the server
 * @method sendKey
 * @param {} key
 * @return 
 */
function sendKey(key){
	matchSocket.send("2"+key);
}
/**
 * Method to tell the server you are ready.
 * @method setReady
 * @return 
 */
function setReady(){
	window.frames[0].document.getElementById("messages").innerHTML="Messages: Waiting for other players";
	matchSocket.send("1");
}