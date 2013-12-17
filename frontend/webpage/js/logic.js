/**
	*Authors: Mikael Pettersson & Christoffer Wern
	*
	*The intention of logic.js is to handle all functionality of the logic.html-file, this includes 
	*dragging and dropping logic nodes and handling input to global variables amongst others.
	*
**/
var logicInstance;
var selectedList = new Array();

var stringObject = new Array();

stringObject[0] =  {id:"And",			string:"<h3>And</h3><p>Information yay</p>"};
stringObject[1] =  {id:"Or",			string:"<h3>Or</h3><p>Information yay</p>"};
stringObject[2] =  {id:"True",			string:"<h3>True</h3><p>Information yay</p>"};
stringObject[3] =  {id:"False",			string:"<h3>False</h3><p>Information yay</p>"};
stringObject[4] =  {id:"Not",			string:"<h3>Not</h3><p>Information yay</p>"};
stringObject[5] =  {id:"Majority",		string:"<h3>Majority</h3><p>Information yay</p>"};
stringObject[6] =  {id:"Delay",			string:"<h3>Delay</h3><p>Information yay</p>"};
stringObject[7] =  {id:"Clock",			string:"<h3>Clock</h3><p>Information yay</p>"};
stringObject[8] =  {id:"TicTac",		string:"<h3>TicTac</h3><p>Information yay</p>"};
stringObject[9] =  {id:"SectorSensor",	string:"<h3>Sector Sensor</h3><p>Information yay</p>"};
stringObject[10] = {id:"DistanceSensor",string:"<h3>Distance Sensor</h3><p>Information yay</p>"};
stringObject[11] = {id:"Fire",			string:"<h3>Fire</h3><p>Information yay</p>"};
stringObject[12] = {id:"Boost",			string:"<h3>Boost</h3><p>Information yay</p>"};
stringObject[13] = {id:"MoveForward",	string:"<h3>Move Forward</h3><p>Information yay</p>"};
stringObject[14] = {id:"MoveBackwards",	string:"<h3>Move Backwards</h3><p>Information yay</p>"};
stringObject[15] = {id:"TurnLeft",		string:"<h3>Turn Left</h3><p>Information yay</p>"};
stringObject[16] = {id:"TurnRight",		string:"<h3>Turn Right</h3><p>Information yay</p>"};

//Function to create nodes so that it is similar to a class.
function addNode(nodeType,name,maxInput,maxOutput,txtFieldLabel,txtFieldBool){
	Node.createNode(nodeType,name,maxInput,maxOutput,txtFieldLabel,txtFieldBool);
} 
	
	
//Class Node "typ"
var Node = (function(){
	
	//Private variables
	var divContext;
	
	var divId; 
	var divClassName;
	var divNodeType;
	var divMaxInputs;
	var divMaxOutputs;
	var divVal;
	var divValLabel;
	var divX;
	var divY;
	
	//Member Functions
	return{
		
		createNode:function(nodeType,name,maxInput,maxOutput, txtFieldLabel, txtFieldBool){
		
			//Sets all local parameters to its original values
			divId = parent.getCreatedNodes() + ""; 
			divClassName = nodeType;
			divNodeType = name;
			divMaxInputs = maxInput;
			divMaxOutputs = maxOutput;
			divValLabel = txtFieldLabel;
			divVal = 0;
			divX = 354;
			divY = 33;
			
			//Create the div
			divContext = "<div id='" + divId + "' class='"+divClassName+"'><p>"+divNodeType;
			if(txtFieldBool=='true'){
				divContext = divContext + "<br/>" + txtFieldLabel + ": <input id='val"+divId+"'class='textFieldClass' value='0' name='textfield' type='text' />";
			}
			divContext = divContext+"</p></div>";

			
			/*creates the draggable object inside myCanvas*/
			$('#myCanvas').append(divContext);
				logicInstance.draggable(divId, {
				containment:"parent",
				stop:function(event, ui){
				var thisIndex = indexOfObjectId(parent.nodes, this.id);
				/*
				Our object that the array/website keeps track of, also the object that should be stored
				within the database
				*/
				
				//TODO: ÄNDRA VARIABELNAMN PÅ nodes OCH ALLA DESS VARIABELNAMN SOM ÖVERRENSKOMMET
				var movedObject = {
					
					nodeType: 	parent.nodes[thisIndex].nodeType,
					x:			this.style.left,
					y:			this.style.top,
					id:			parent.nodes[thisIndex].id,
					maxInputs: 	parent.nodes[thisIndex].maxInputs,
					maxOutputs:	parent.nodes[thisIndex].maxOutputs,
					val: 		parent.nodes[thisIndex].val,
					valLabel:   parent.nodes[thisIndex].valLabel,
					className: 	parent.nodes[thisIndex].className
					};
					parent.nodes.splice(thisIndex,1,movedObject);
				}
				
			});
			
			//Creates the endpoints and its settings to the div.
			if(divMaxInputs != 0){
				var input = {
					anchor: ["TopCenter"],
					maxConnections: divMaxInputs,
					isSource:false, 
					isTarget:true,
					connector : "Flowchart",
					paintStyle:{ radius: 7, fillStyle:"red", outlineColor:"black", outlineWidth:1 },
					connectorStyle: { lineWidth:2, strokeStyle:'blue' },
					scope:"blueline",
					dragAllowedWhenFull:false     
				};
				logicInstance.addEndpoint(divId, input);
			}
			
			if(divMaxOutputs != 0){
				var output = {
					anchor: ["BottomCenter"],
					maxConnections:divMaxOutputs,
					isSource:true, 
					isTarget:false,
					connector : "Flowchart",
					paintStyle:{ radius: 7, fillStyle:"green", outlineColor:"black", outlineWidth:1},
					connectorStyle: { lineWidth:2, strokeStyle:'blue' },
					scope:"blueline",
					dragAllowedWhenFull:false     
				}; 	
				logicInstance.addEndpoint(divId, output);
			}
			
			
			
			var obj = {
				nodeType: 	divNodeType,
				x:			divX,
				y:			divY,
				id:			divId,
				maxInputs: 	divMaxInputs,
				maxOutputs:	divMaxOutputs,
				val: 		divVal,
				valLabel:   divValLabel,
				className: 	divClassName
			 };
			 
			if(divClassName == "sensorNode"){
				parent.nrOfSensorNodes = parent.nrOfSensorNodes+1;
				if(parent.nrOfSensorNodes == parent.maxNrOfSensorNodes){
					$(".sensorButton").hide();
				}
			}
			parent.nodes.push(obj);
		},
	}
	
}());	

function indexOfObjectId(array, obj){
	for(var i=0;i<array.length;i++){
		if(array[i].id==obj){
			return i;
		}
	}
	return -1;
}
	

$(document).ready(function() {
	//Calls function to show or hide node buttons depending on robot components.
	showHide();
	
	//När en connection görs så sparas den undan i arrayOfConnections, för att kunna spara till DB.
	//When a connection is added duplicately, it will instantly be removed, AS IF IT NEVER EXISTED.
	logicInstance.bind("jsPlumbConnection", function(conn){
		var idxbool = true;
		
		if(conn.sourceId!=conn.targetId){
			for(var i=0;i<parent.connections.length;i++){
				if( (parent.connections[i].sourceId == conn.sourceId) && (parent.connections[i].targetId == conn.targetId)){
					idxbool = false;
					logicInstance.detach(conn);
				}
			}
			if(idxbool==true){
				var myConn = {
					sourceId : conn.sourceId,
					targetId : conn.targetId
				}
				parent.connections.push(myConn);
			}
		}
		else{
			logicInstance.detach(conn);
		}
	});
	
	//Hi-jacks the function call to detach a connection to add functionality and remove it from the data model.
	logicInstance.bind("jsPlumbConnectionDetached", function(conn){
		var idx = -1;
		for(var i=0;i<parent.connections.length;i++){
			if( (parent.connections[i].sourceId == conn.sourceId) && (parent.connections[i].targetId == conn.targetId)){
				idx = i;
			}
		}
		
		if(idx >=0){
			parent.connections.splice(idx,1);
		}
		else{
			//alert("Error, something went wrong!");
		}
	});
	
	//Om man klickar på en div ska den bli "selected"
	//Sparas undan i global variable "selected" och får röd ram
	//Om man klickar på den igen blir inte "selected" längre
	
	$('#myCanvas').click(function(event){
		var tar = '#'+event.target.id;
	
		if(tar!='#myCanvas' && event.target.className!='textFieldClass' ){
			//Om diven redan är selectad och man klickar på den igen,
			//så ska den inte bli selectad längre
			if(arrayContains(selectedList,event.target.id)){
				$(tar).css("border", "1px solid black");
				selectedList.splice(selectedList.indexOf(event.target.id),1);
			}
			//Om den inte är selectad så ska den bli det
			else if(event.target.id != ""){
				$(tar).css("border", "2px solid red");
				selectedList.push(event.target.id);
			}
		}
	});
	
	//Deletes the selected nodes, key 46 = delete key on a windows pc.(not confirmed for other platforms)
	$(document).keydown(function(e){
	if (e.keyCode == 46) { 
			var parentIndex;
			for(var i=0;i<selectedList.length;i++){
				//Remove the graphical components of the GUI
				logicInstance.removeAllEndpoints(selectedList[i]);
				logicInstance.detachAllConnections(selectedList[i]);
				$("#" + selectedList[i]).remove();

				//TA BORT FRÅN PARENT-LISTAN
				parentIndex = indexOfObjectId(parent.nodes, selectedList[i]);
				if(parent.nodes[parentIndex].className == "sensorNode"){
					parent.nrOfSensorNodes = parent.nrOfSensorNodes-1;
					$(".sensorButton").show();
				}
				parent.nodes.splice(parentIndex,1);
			}
			//TA BORT FRÅN SELECTED-LIST
			selectedList.splice(0,selectedList.length);
		   return false;
		}
	});
	
	
	/**HOOVER STUFF**/
		
		/***NODER***/
		
		/**********/
		
		
		/*******BUTTONS*******/
		$('.logicButton').hover(
			function(e){
				showThisInfo(e.currentTarget.innerHTML);
			},
			function(){
				$("#robotView").text("");
			}
		);
		$('.sensorButton').hover(
			function(e){
				showThisInfo(e.currentTarget.innerHTML);
			},
			function(){
				$("#robotView").text("");
			}
		);
		$('.weaponButton').hover(
			function(e){
				showThisInfo(e.currentTarget.innerHTML);
			},
			function(){
				$("#robotView").text("");
			}
		);
		$('.wheelButton').hover(
			function(e){
				showThisInfo(e.currentTarget.innerHTML);
			},
			function(){
				$("#robotView").text("");
			}
		);
		/*********************/
		
	
	/****************/
	
	
	
});

function arrayContains(array,obj){
	for(var i=0;i<array.length;i++){
		if(array[i]==obj){
			return true;
		}
	}
	return false;
}

function showHide(){
	$(".wheelButton").hide();
	$(".weaponButton").hide();
	if(parent.maxNrOfSensorNodes<=0){
		$(".sensorButton").hide();
	}
	for(var i=0;i<parent.actionNodes.length;i++){
		$('.'+ parent.actionNodes[i]).show();
	}
	
	return false;
}

//DATABASE STUFF
//LOADS THE CONNECTIONS AND DIVS FROM THE GLOBAL VARIABLES

function addLatestConnections(){
	if(parent.connections.length != 0){
		for(var i=0;i<parent.connections.length;i++){
			if(logicInstance.getEndpoints(parent.connections[i].sourceId).length == 1){
				logicInstance.connect({
					source: logicInstance.getEndpoints(parent.connections[i].sourceId)[0],
					target: logicInstance.getEndpoints(parent.connections[i].targetId)[0],
				});
			}
			else{
				logicInstance.connect({
					source: logicInstance.getEndpoints(parent.connections[i].sourceId)[1],
					target: logicInstance.getEndpoints(parent.connections[i].targetId)[0],
				});
			}
		}
	}		
}

function addSavedDivs(){
	if(parent.nodes.length != 0){
		for(var i=0;i<parent.nodes.length;i++)
		{
			$('#myCanvas').append("<div id='"+parent.nodes[i].id+"'></div>");
			var elem = 			document.getElementById(parent.nodes[i].id);
			elem.className = 	parent.nodes[i].className;
				var thisInnerHtml = "<p>"+parent.nodes[i].nodeType;
				if(parent.nodes[i].valLabel != ""){
					thisInnerHtml = thisInnerHtml+"<br/>"+parent.nodes[i].valLabel+": <input id='val"+parent.nodes[i].id+"'class='textFieldClass' name='textfield' type='text' value='"+parent.nodes[i].val+"'/>";
				}
				thisInnerHtml = thisInnerHtml+"</p>";
			elem.innerHTML = thisInnerHtml;
			elem.style.left = 	parent.nodes[i].x;
			elem.style.top = 	parent.nodes[i].y;
			
			logicInstance.draggable(parent.nodes[i].id, {
			containment:"parent",
			stop:function(event, ui){	
				var thisIndex = indexOfObjectId(parent.nodes, this.id);
				
					var movedObject = {
						
						nodeType: 	parent.nodes[thisIndex].nodeType,
						x:			this.style.left,
						y:			this.style.top,
						id:			parent.nodes[thisIndex].id,
						maxInputs: 	parent.nodes[thisIndex].maxInputs,
						maxOutputs:	parent.nodes[thisIndex].maxOutputs,
						val: 		parent.nodes[thisIndex].val,
						valLabel:   parent.nodes[thisIndex].valLabel,
						className: 	parent.nodes[thisIndex].className
					};			
				parent.nodes.splice(thisIndex,1,movedObject);
				}
		});
			
			//ADD ENDPOINTS
			if(parent.nodes[i].maxInputs!= 0){
				var input = {
					anchor: ["TopCenter"],
					maxConnections: parent.nodes[i].maxInputs,
					isSource:false, 
					isTarget:true,
					connector : "Flowchart",
					paintStyle:{ radius: 7, fillStyle:"red", outlineColor:"black", outlineWidth:1 },
					connectorStyle: { lineWidth:2, strokeStyle:'blue' },
					scope:"blueline",
					dragAllowedWhenFull:false     
				};
				logicInstance.addEndpoint(parent.nodes[i].id, input);
			}
			
			if(parent.nodes[i].maxOutputs!= 0){
				var output = {
					anchor: ["BottomCenter"],
					maxConnections:  parent.nodes[i].maxOutputs,
					isSource:true, 
					isTarget:false,
					connector : "Flowchart",
					paintStyle:{ radius: 7, fillStyle:"green", outlineColor:"black", outlineWidth:1},
					connectorStyle: { lineWidth:2, strokeStyle:'blue' },
					scope:"blueline",
					dragAllowedWhenFull:false     
				}; 
				logicInstance.addEndpoint(parent.nodes[i].id, output);
			}
			console.log(parent.nodes[i].className);
			if(parent.nodes[i].className == "sensorNode"){
				parent.nrOfSensorNodes = parent.nrOfSensorNodes+1;
				if(parent.nrOfSensorNodes == parent.maxNrOfSensorNodes){
					$(".sensorButton").hide();
				}
			}
		}
	parent.setCreatedNodes(parent.nodes[parent.nodes.length-1].id);	
	}
	
}

//Change information in "hoover-div", bottom-left
function showThisInfo(comp){
	for(var i=0;i<stringObject.length;i++){
		if(stringObject[i].id == comp){
			$("#robotView").text("");
			$("#robotView").append(stringObject[i].string);
			return true;
		}
	}
	return false;
}

//When the user leaves the page
window.onbeforeunload = function (e) {
	for(var i=0;i<parent.nodes.length;i++){
		if(parent.nodes[i].valLabel != ""){
			parent.nodes[i].val = document.getElementById('val'+parent.nodes[i].id).value;
		}
	}
	parent.robot.robotDesign = parent.robotDesign;
	//Saves the robot, NOT FUNCTIONAL!!!
	//parent.updateRobot(parent.user, parent.robot);
	
}

//Jsplumb.ready är när sidan laddat klart, så att jsplumb får köras med alla divvar
jsPlumb.ready(function() {
	
	logicInstance = jsPlumb.getInstance();
	addSavedDivs();
	addLatestConnections();
});
