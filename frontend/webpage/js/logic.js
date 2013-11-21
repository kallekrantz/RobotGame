/*
	*Authors: Mikael Pettersson & Christoffer Wern
	*
	*The intention of logic.js is to handle all functionality of the logic.html-file, this includes 
	*dragging and dropping logic nodes and handling input to global variables amongst others.
	*
*/
var logicInstance;
var selectedList = new Array();
	
function indexOfObjectId(array, obj){
	for(var i=0;i<array.length;i++){
		if(array[i].id==obj){
			return i;
		}
	}
	return -1;
}
	
function addNode(nodeType,name,maxInput,maxOutput,txtFieldVal,txtFieldBool){
	Node.createNode(nodeType,name,maxInput,maxOutput,txtFieldVal,txtFieldBool);
} 
	
	
//Class Node "typ"
var Node = (function(){
	
	//Private variables
	var divId; 
	var divClass;
	var divName;
	var divText;
	var divMaxInput;
	var divMaxOutput;
	
	//Member Functions
	return{
		
		//Creates the div
		createNode:function(nodeType,name,maxInput,maxOutput, txtFieldVal, txtFieldBool){
			divId = parent.getCreatedNodes() + ""; 
			divClass = nodeType;
			divName = name;
			divText = "<div id='" + divId + "' class='"+divClass+"'><p>"+divName;
			if(txtFieldBool=='true'){
				divText = divText + "<br/>" + txtFieldVal + ": <input class='textFieldClass' name='textfield' type='text' />";
			}
			divText = divText+"</p></div>";
			divMaxInput = maxInput;
			divMaxOutput = maxOutput;
			
			/*creates the draggable object inside myCanvas*/
			$('#myCanvas').append(divText);
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
					id:			parent.nodes[thisIndex].id,
					className: 	parent.nodes[thisIndex].className,
					name: 		parent.nodes[thisIndex].name,
					x:			this.style.left,
					y:			this.style.top,
					maxInput: 	parent.nodes[thisIndex].maxInput,
					maxOutput:	parent.nodes[thisIndex].maxOutput
					};
					parent.nodes.splice(thisIndex,1,movedObject);
				}
				
			});
			
			//SET CONNECTIONS
			if(divMaxInput != 0){
				var input = {
					anchor: ["TopCenter"],
					maxConnections: divMaxInput,
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
			
			if(divMaxOutput != 0){
				var output = {
					anchor: ["BottomCenter"],
					maxConnections:divMaxOutput,
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
				id: 		divId,
				className: 	divClass,
				name: 		divName,
				x: 			1,
				y: 			1,
				maxInput: 	divMaxInput,
				maxOutput: 	divMaxOutput
			 };
			  
			parent.nodes.push(obj);	
			console.log(parent.nodes);
		},
	}
	
}());	
	

$(document).ready(function() {
	//These four lines reads the stored arrays from the global list, so files can be stored.
	//Should be exchanged when database is implemented(Or implement the DB in variables.js)
	
	//När en connection görs så sparas den undan i arrayOfConnections, för att kunna spara till DB.
	logicInstance.bind("jsPlumbConnection", function(conn){
		parent.connections.push(conn);
		}
	);
	
	//När en connection tas bort (t.ex. om man tar bort en nod) tas den även bort från arrayen.
	logicInstance.bind("jsPlumbConnectionDetached", function(conn){
		parent.connections.splice(parent.connections.indexOf(conn),1);
		//typ logicInstance.connect(arrayOfConnections[0]);
		}
	);
	
	//Om man klickar på en div ska den bli "selected"
	//Sparas undan i global variable "selected" och får röd ram
	//Om man klickar på den igen blir inte "selected" längre
	
	$('#myCanvas').click(function(event){
		var tar = '#'+event.target.id;
		
		if(tar!='#myCanvas'){
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
	
	/*
	$(document).keydown(function(e){
		if (e.keyCode == 46) { 
			var parentIndex;
			for(var i=0;i<selectedList.length;i++){
				//Först ta bort alla grafiska connections-delar
				logicInstance.removeAllEndpoints(selectedList[i]);
				logicInstance.detachAllConnections(selectedList[i]);
				
				//TA BORT SJÄLVA BOXEN
				$("#" + selectedList[i]).remove();
				
				remove

				//TA BORT FRÅN PARENT-LISTAN
				parentIndex = indexOfObjectId(parent.nodes, selectedList[i]);
				parent.nodes.splice(parentIndex,1);
				
				//TA BORT FRÅN SELECTED-LIST
				selectedList.splice(i,1);
			}
		   return false;
		}
	});
	*/
	
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
			logicInstance.connect({
			source: parent.connections[i].sourceId,
			target: parent.connections[i].targetId,
			anchors: ["BottomCenter","TopCenter"],
			endpointStyles:[{radius: 7, fillStyle:"green", outlineColor:"black", outlineWidth:1},
							{radius: 7, fillStyle:"red", outlineColor:"black", outlineWidth:1}],
			paintStyle:{ lineWidth:2, strokeStyle:'blue'},
			maxConnections: "1" 
			
			/*MAN KAN ALLTID LÄGGA TILL EN TILL CONNECTION PGA ATT DET TYP SKAPA EN TILL ENDPOINT PÅ
			SAMMA POSITION..... BALLE... BORDE KUNNA LÖSA GENOM ATT KOLLA NUVARANDE CONNECTIONS.*/
			
			});
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
			elem.innerHTML = 	"<p>"+parent.nodes[i].name+"</p>";
			elem.style.left = 	parent.nodes[i].x;
			elem.style.top = 	parent.nodes[i].y;
			
			logicInstance.draggable(parent.nodes[i].id, {
			containment:"parent",
			stop:function(event, ui){	
				var thisIndex = indexOfObjectId(parent.nodes, this.id);
				
				var movedObject = {
					id:			parent.nodes[thisIndex].id,
					className: 	parent.nodes[thisIndex].className,
					name: 		parent.nodes[thisIndex].name,
					x:			this.style.left,
					y:			this.style.top,
					maxInput: 	parent.nodes[thisIndex].maxInput,
					maxOutput:  parent.nodes[thisIndex].maxOutput
				};
				parent.nodes.splice(thisIndex,1,movedObject);
				}
			});
			
			//ADD ENDPOINTS
			if(parent.nodes[i].maxInput!= 0){
				var input = {
					anchor: ["TopCenter"],
					maxConnections: parent.nodes[i].maxInput,
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
			
			if(parent.nodes[i].maxOutput!= 0){
				var output = {
					anchor: ["BottomCenter"],
					maxConnections:  parent.nodes[i].maxOutput,
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
	parent.saveFile();
}

//Jsplumb.ready är när sidan laddat klart, så att jsplumb får köras med alla divvar
jsPlumb.ready(function() {
	logicInstance = jsPlumb.getInstance();
	addSavedDivs();
	addLatestConnections();
});
