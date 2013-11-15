var divArray = new Array();
var connectionArray = new Array();
var createdNodes=0;


function saveFile(){
var json = JSON.stringify(divArray);
loadJSONfile(json);
}

//Loads the JSON object into the global variables of the page, thus having your logic stored.
function loadJSONfile(jsonFile){
	//need to load createdNodes variable aswell, it should contain the number of nodes created by
	//the user, so that we can have an ID on all divs
	divArray = JSON.parse(jsonFile);
}

function getCreatedNodes(){
	createdNodes = createdNodes+1;
	return createdNodes;
}

function setCreatedNodes(object){
	createdNodes = parseInt(object);
}