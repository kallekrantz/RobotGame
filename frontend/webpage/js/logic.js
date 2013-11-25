/*
	*Authors: Mikael Pettersson & Christoffer Wern
	*
	*The intention of logic.js is to handle all functionality of the logic.html-file, this includes 
	*dragging and dropping logic nodes and handling input to global variables amongst others.
	*
*/
var logicInstance;
var selectedList = new Array();
	

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
			divX = 10;
			divY = 1;
			
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
					connector : "Bezier",
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
					connector : "Bezier",
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
			  
			parent.nodes.push(obj);	
			console.log(parent.nodes);
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
	//These four lines reads the stored arrays from the global list, so files can be stored.
	//Should be exchanged when database is implemented(Or implement the DB in variables.js)
	
	//När en connection görs så sparas den undan i arrayOfConnections, för att kunna spara till DB.
	logicInstance.bind("jsPlumbConnection", function(conn){
		var myConn = {
			sourceId : conn.sourceId,
			targetId : conn.targetId
		}
		parent.connections.push(myConn);
		console.log(parent.connections);
	});
	
	//När en connection tas bort (t.ex. om man tar bort en nod) tas den även bort från arrayen.
	logicInstance.bind("jsPlumbConnectionDetached", function(conn){
		var idx = -1;
		for(var i=0;i<parent.connections.length;i++){
			if( (parent.connections[i].sourceId == conn.sourceId) && (parent.connections[i].targetId == conn.targetId)){
				idx = i;
			}
		}
		
		if(idx >=0){
			parent.connections.splice(idx,1);
			console.log(parent.connections);
		}
		else{
			alert("Error, something went wrong!");
		}
	});
	
	//Om man klickar på en div ska den bli "selected"
	//Sparas undan i global variable "selected" och får röd ram
	//Om man klickar på den igen blir inte "selected" längre
	
	$('#myCanvas').click(function(event){
		var tar = '#'+event.target.id;
		console.log(event);
		if(tar!='#myCanvas' && event.target.className!='textFieldClass' ){
			//Om diven redan är selectad och man klickar på den igen,
			//så ska den inte bli selectad längre
			if(arrayContains(selectedList,event.target.id)){
				$(tar).css("border", "1px solid black");
				selectedList.splice(selectedList.indexOf(event.target.id),1);
			}
			//Om den inte är selectad så ska den bli det
			else{
				$(tar).css("border", "2px solid red");
				selectedList.push(event.target.id);
			}
		}
	});
	
	
	$(document).keydown(function(e){
	if (e.keyCode == 46) { 
			var parentIndex;
			for(var i=0;i<selectedList.length;i++){
				//Först ta bort alla grafiska connections-delar
				logicInstance.removeAllEndpoints(selectedList[i]);
				logicInstance.detachAllConnections(selectedList[i]);
				
				//TA BORT SJÄLVA BOXEN
				$("#" + selectedList[i]).remove();

				//TA BORT FRÅN PARENT-LISTAN
				parentIndex = indexOfObjectId(parent.nodes, selectedList[i]);
				parent.nodes.splice(parentIndex,1);
				
				//TA BORT FRÅN SELECTED-LIST
				//selectedList.splice(i,1);
			}
			selectedList.splice(0,selectedList.length);
		   return false;
		}
	});
	
});

function arrayContains(array,obj){
	for(var i=0;i<array.length;i++){
		if(array[i]==obj){
			return true;
		}
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
					connector : "Bezier",
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
					connector : "Bezier",
					paintStyle:{ radius: 7, fillStyle:"green", outlineColor:"black", outlineWidth:1},
					connectorStyle: { lineWidth:2, strokeStyle:'blue' },
					scope:"blueline",
					dragAllowedWhenFull:false     
				}; 
				logicInstance.addEndpoint(parent.nodes[i].id, output);
			}
		}
	parent.setCreatedNodes(parent.nodes[parent.nodes.length-1].id);	
	}
	
}

//When the user leaves the page
window.onbeforeunload = function (e) {
	for(var i=0;i<parent.nodes.length;i++){
		parent.nodes[i].val = document.getElementById('val'+parent.nodes[i].id).value;
	}
	console.log(parent.nodes);
	parent.saveFile();
	
}

//Jsplumb.ready är när sidan laddat klart, så att jsplumb får köras med alla divvar
jsPlumb.ready(function() {
	logicInstance = jsPlumb.getInstance();
	addSavedDivs();
	addLatestConnections();
});
