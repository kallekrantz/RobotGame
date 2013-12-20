var nodes = new Array();
var connections = new Array();
var components = new Array();
components.splice(0,0, null,null,null,null,null);
var robotList = new Array();
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


var nrOfSensorNodes=0;
var maxNrOfSensorNodes=0;

var actionNodes = new Array();

function loadOtherRobot(Index){
	robot = robotList[Index];
	var obj = robot.robotDesign;
	nodes = obj.nodes;
	connections = obj.connections;
	components = obj.components;
	robotDesign = obj;
	document.getElementById("frame").contentWindow.addComponents();
}

function updateRobotList(updatedRobot){
var robotExists = false;
	for(var i=0; i<robotList.length; i++){
		if(robotList[i].id == updatedRobot.id){
			robotExists = true;
			robotList[i].robotName = robot.robotName;
		}
	}
	if(!robotExists){
		robotList.push(updatedRobot);
		robot = updatedRobot;
		document.getElementById("frame").contentWindow.addComponents();
		var eSelect = window.frames[0].document.getElementById('robotList');
		eSelect.selectedIndex = robotList.length-1;
		window.frames[0].addComponents();
	}
}

function getCreatedNodes(){
	createdNodes = createdNodes+1;
	return createdNodes;
}

function setCreatedNodes(object){
	createdNodes = parseInt(object);
}

function clearAll(){
	user.id = "";			
	user.username = "";	
	user.firstname= "";	
	user.lastname = "";	
	user.password = "";

	robotDesign.nodes.length = 0;
	robotDesign.connections.length = 0;
	robotDesign.components.length = 0;

	robot = {
		id 			: "",
		robotName 	: "",
		robotDesign	: robotDesign
	}	
	robotList.length = 0;
    createdNodes=0;
}