var nodes = new Array();
var connections = new Array();
var components = new Array();
components.splice(0,0, null,null,null,null,null);
var createdNodes=0;

var user = {
	id 			: "",
	/*
	firstname	: "",
	lastname:	: "",
	pwdHash 	: "",
	username 	: ""*/
}

/*

var robotDesign = {
	nodes		: nodes,
	connections	: connections,
	components	: components
}

var robot = {
	id 			: "",
	robotName 	: "",
	robotDesign	: JSON.stringify(robotObject)
	}	
*/


var oppenentComponents = new Array();
var storage = storageConnector("");

//Symboliserar att vi läst in detta värden från databasen
oppenentComponents[0] = "";
oppenentComponents[1] = "";
oppenentComponents[2] = "Laser";

var json;

var nrOfSensorNodes=0;
var maxNrOfSensorNodes=0;

var actionNodes = new Array();

function saveFile(){
	var obj = 	{	
		nodes : nodes,
		connections : connections,
		components : components
	};
	
	obj = JSON.stringify(obj);
	var tempRobotId = 2;
	
	var robotObject = {
		robotDesign: obj,
		robotName: "blä"
	};
	
	storage.putRobot(user.id,tempRobotId, robotObject, function(data){
		console.log(robotObject);
		console.log(data);
	});
	
}

var setUserName = function(name){
	user.id = name;
	//console.log(user.id);
	getRobotFromDB(user.id);
}

function getRobotFromDB(userId)
{

	var test = new Array;
	var tempRobotId = 2;
	//skicka in robot.id;
	storage.getRobotList(userId, function(data){
			console.log(data);
			//loadJSONfile(data.robotDesign);
	});
	
}


//Loads the JSON object into the global variables of the page, thus having your logic stored.
function loadJSONfile(jsonFile){
	//need to load createdNodes variable aswell, it should contain the number of nodes created by
	//the user, so that we can have an ID on all divs
	var obj = JSON.parse(jsonFile);
	connections = obj.connections;
	nodes = obj.nodes;
	components = obj.components;
}

function getCreatedNodes(){
	createdNodes = createdNodes+1;
	return createdNodes;
}

function setCreatedNodes(object){
	createdNodes = parseInt(object);
}