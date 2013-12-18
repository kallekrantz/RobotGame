var nodes = new Array();
var connections = new Array();
var components = new Array();
components.splice(0,0, null,null,null,null,null);
var createdNodes=0;

var user = {
	id 			: "",
	username 	: "",	
	firstname	: "",
	lastname	: "",
	password 	: ""
}

var robotDesign = {
	nodes		: nodes,
	connections	: connections,
	components	: components
}

var robot = {
	id 			: "",
	robotName 	: "",
	robotDesign	: robotDesign
}	

var robotList = new Array();

var nrOfSensorNodes=0;
var maxNrOfSensorNodes=0;

var actionNodes = new Array();

function loadOtherRobot(Index){
	robot = robotList[Index];
	var obj = JSON.parse(robot.robotDesign);
	nodes = obj.nodes;
	connections = obj.connections;
	components = obj.components;
	robotDesign = obj;
	console.log(robotDesign);
	document.getElementById("frame").contentWindow.addComponents();
}

function getCreatedNodes(){
	createdNodes = createdNodes+1;
	return createdNodes;
}

function setCreatedNodes(object){
	createdNodes = parseInt(object);
}