var nodes = new Array();
var connections = new Array();
var components = new Array();
components.splice(0,0, null,null,null,null,null);
var createdNodes=0;

var oppenentComponents = new Array();

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
json = JSON.stringify(obj);

console.log(json);

connections.splice(0,connections.length);
nodes.splice(0,nodes.length);
components.splice(0,components.length);
createdNodes=0;
actionNodes.splice(0,actionNodes.length);
nrOfSensorNodes=0;
maxNrOfSensorNodes =0;

loadJSONfile(json);
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