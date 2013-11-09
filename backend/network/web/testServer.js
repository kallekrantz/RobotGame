var ws = new WebSocket("ws://127.0.0.1:61989/");
var match;
var PORT;
ws.onopen = function() {
	
    ws.send(makejson());
};

ws.onmessage = function (evt) {
    document.getElementById("field").innerHTML+="   "+evt.data;
	if(evt.data.indexOf("port:") !== -1){
	start = evt.data.indexOf(":")+1;
	PORT=evt.data.substring(start,start+5);
	
	match = new WebSocket("ws://127.0.0.1:"+PORT);
		match.onopen = function() {
			ws.close();
			match.send("0"+makeReq(PORT));
		}
		match.onmessage = function (evt){
		document.getElementById("field").innerHTML+="   "+evt.data;
		}
		match.onclose = function(){
		};
		match.onerror = function(){
		};
	}
};

ws.onclose = function() {
};

ws.onerror = function(err) {
    alert("Error: " + err);
};

function makejson(){
	message=JSON.stringify(new request("me",12,0));
	return  message;
}
function makeReq(port){
	message=JSON.stringify(new joinRequest("me",port));
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
	match.send("1");
}
function sendKey(key){
	match.send("2"+key);
}