matchMakerSocket = new WebSocket("ws://127.0.0.1:61989/");
userName = "bla";
matchMakerSocket.onopen = function() {
	document.getElementById("messages").innerHTML+="Welcome!";
};

matchMakerSocket.onmessage = function (evt) {
    document.getElementById("debug").innerHTML+="<br>"+evt.data;
	if(evt.data.indexOf("port:") !== -1){
		start = evt.data.indexOf(":")+1;
		PORT=evt.data.substring(start,start+5);
		startNewMatch(PORT);
	}
};
function startNewMatch (port){
	matchSocket = new WebSocket("ws://127.0.0.1:"+port);
	matchSocket.onopen = function() {
		matchMakerSocket.close();
		matchSocket.send("0"+makeReq(port,userName));
		document.getElementById("messages").innerHTML="Messages: Loading scene";
	}
	matchSocket.onmessage = function (evt){
		if(evt.data.indexOf("NetworkError::")!=-1){
			
		}else if(evt.data.indexOf("StartingState::")!=-1){
			var startingState = JSON.parse(evt.data.replace("StartingState::",""));
			currentMatchState = new NetworkMatch(startingState);
			nextMatchState = new NetworkMatch(startingState);
			document.getElementById('arena').innerHTML="<iframe src='arena.html' width='600px' height='500px'></iframe>";
		}else if(evt.data.indexOf("UpdateState::")!=-1){
			var newState = JSON.parse(evt.data.replace("UpdateState::",""));
			nextMatchState = new NetworkMatch(newState);
			document.getElementById("debug").innerHTML+=" x: " + nextMatchState.robots[0].getX() +" dx: " + nextMatchState.robots[0].getdX() + " y: " + nextMatchState.robots[0].getZ() + " dY: " + nextMatchState.robots[0].getdZ()+"<br>"+" rot: "+nextMatchState.robots[0].getRotation()+" angVel: "+nextMatchState.robots[0].getAngularVelocity()+"<br>";
		}else if(evt.data.indexOf("MatchEnded::")!=-1){
			document.getElementById("messages").innerHTML="Messages: Game over man, game over";
			document.getElementById("debug").innerHTML+="<br>"+evt.data;
			matchSocket.close();
		}
		//document.getElementById("debug").innerHTML+="   "+evt.data;
	}
	matchSocket.onclose = function(){
	};
	matchSocket.onerror = function(){
	};
}
matchMakerSocket.onclose = function() {
};

matchMakerSocket.onerror = function(err) {
    alert("Error: " + err);
};

function makeMatchMakerRequest(){
	userName = document.getElementById("username").value;
	matchType  = document.getElementById("matchtype").selectedIndex;
	var message=JSON.stringify(new request(userName,12,matchType));
	matchMakerSocket.send(message);
	document.getElementById("messages").innerHTML="Messages: Entering "+document.getElementById("matchtype").options[document.getElementById("matchtype").selectedIndex].value+" lobby";
}
function makeReq(port,username){
	message=JSON.stringify(new joinRequest(username,port));
	return message;
}
function request(name, id, model){
	this.user=name;
	this.robotId=id;
	this.type=model;
}
function joinRequest(name, port){
	this.user=name;
	this.port=port;
}
function ready(){
	matchSocket.send("1");
}
function sendKey(key){
	matchSocket.send("2"+key);
}
function setReady(){
	document.getElementById("messages").innerHTML="Messages: Waiting for other players";
	matchSocket.send("1");
}