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
	message=JSON.stringify(new request("someone",12,0));
return  message;
}
function makeReq(port){
	message=JSON.stringify(new joinRequest("someone",port));
	return message;
}

// function dojson(){
// foo="function doStuff(){alert('stuff');}";
// jfoo=JSON.stringify(foo);
// unpackedfoo=JSON.parse(jfoo);
// document.getElementById("scriptbase").innerHTML=unpackedfoo;
// doStuff();
// }
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
// function poke(){
// ws.send("poke");
// }