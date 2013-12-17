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

var oppenentComponents = new Array();
//Symboliserar att vi läst in detta värden från databasen
oppenentComponents[0] = "";
oppenentComponents[1] = "";
oppenentComponents[2] = "Laser";

var nrOfSensorNodes=0;
var maxNrOfSensorNodes=0;

var actionNodes = new Array();

function getCreatedNodes(){
	createdNodes = createdNodes+1;
	return createdNodes;
}

function setCreatedNodes(object){
	createdNodes = parseInt(object);
}