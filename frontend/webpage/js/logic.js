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
				var thisIndex = indexOfObjectId(parent.divArray, this.id);
				/*
				Our object that the array/website keeps track of, also the object that should be stored
				within the database
				*/
				
				//TODO: �NDRA VARIABELNAMN P� DIVARRAY OCH ALLA DESS VARIABELNAMN SOM �VERRENSKOMMET
				var movedObject = {
					id:			parent.divArray[thisIndex].id,
					className: 	parent.divArray[thisIndex].className,
					name: 		parent.divArray[thisIndex].name,
					x:			this.style.left,
					y:			this.style.top,
					maxInput: 	parent.divArray[thisIndex].maxInput,
					maxOutput:	parent.divArray[thisIndex].maxOutput
					};
					parent.divArray.splice(thisIndex,1,movedObject);
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
			  
			parent.divArray.push(obj);	
			console.log(parent.divArray);
		},
	}
	
}());	
	

$(document).ready(function() {
	//These four lines reads the stored arrays from the global list, so files can be stored.
	//Should be exchanged when database is implemented(Or implement the DB in variables.js)
	
	//N�r en connection g�rs s� sparas den undan i arrayOfConnections, f�r att kunna spara till DB.
	logicInstance.bind("jsPlumbConnection", function(conn){
		parent.connectionArray.push(conn);
		}
	);
	
	//N�r en connection tas bort (t.ex. om man tar bort en nod) tas den �ven bort fr�n arrayen.
	logicInstance.bind("jsPlumbConnectionDetached", function(conn){
		parent.connectionArray.splice(parent.connectionArray.indexOf(conn),1);
		//typ logicInstance.connect(arrayOfConnections[0]);
		}
	);
	
	//Om man klickar p� en div ska den bli "selected"
	//Sparas undan i global variable "selected" och f�r r�d ram
	//Om man klickar p� den igen blir inte "selected" l�ngre
	
	$('#myCanvas').click(function(event){
		var tar = '#'+event.target.id;
		
		if(tar!='#myCanvas'){
			//Om diven redan �r selectad och man klickar p� den igen,
			//s� ska den inte bli selectad l�ngre
			if(arrayContains(selectedList,event.target.id)){
				$(tar).css("border", "1px solid black");
				selectedList.splice(selectedList.indexOf(event.target.id),1);
			}
			//Om den inte �r selectad s� ska den bli det
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
				//F�rst ta bort alla grafiska connections-delar
				logicInstance.removeAllEndpoints(selectedList[i]);
				logicInstance.detachAllConnections(selectedList[i]);
				
				//TA BORT SJ�LVA BOXEN
				$("#" + selectedList[i]).remove();
				
				remove

				//TA BORT FR�N PARENT-LISTAN
				parentIndex = indexOfObjectId(parent.divArray, selectedList[i]);
				parent.divArray.splice(parentIndex,1);
				
				//TA BORT FR�N SELECTED-LIST
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

	if(parent.connectionArray.length != 0){
		for(var i=0;i<parent.connectionArray.length;i++){
			logicInstance.connect({
			source: parent.connectionArray[i].sourceId,
			target: parent.connectionArray[i].targetId,
			anchors: ["BottomCenter","TopCenter"],
			endpointStyles:[{radius: 7, fillStyle:"green", outlineColor:"black", outlineWidth:1},
							{radius: 7, fillStyle:"red", outlineColor:"black", outlineWidth:1}],
			paintStyle:{ lineWidth:2, strokeStyle:'blue'},
			maxConnections: "1" 
			
			/*MAN KAN ALLTID L�GGA TILL EN TILL CONNECTION PGA ATT DET TYP SKAPA EN TILL ENDPOINT P�
			SAMMA POSITION..... BALLE... BORDE KUNNA L�SA GENOM ATT KOLLA NUVARANDE CONNECTIONS.*/
			
			});
		}
	}		
}

function addSavedDivs(){
	if(parent.divArray.length != 0){
		for(var i=0;i<parent.divArray.length;i++)
		{
			$('#myCanvas').append("<div id='"+parent.divArray[i].id+"'></div>");
			var elem = 			document.getElementById(parent.divArray[i].id);
			elem.className = 	parent.divArray[i].className;
			elem.innerHTML = 	"<p>"+parent.divArray[i].name+"</p>";
			elem.style.left = 	parent.divArray[i].x;
			elem.style.top = 	parent.divArray[i].y;
			
			logicInstance.draggable(parent.divArray[i].id, {
			containment:"parent",
			stop:function(event, ui){	
				var thisIndex = indexOfObjectId(parent.divArray, this.id);
				
				var movedObject = {
					id:			parent.divArray[thisIndex].id,
					className: 	parent.divArray[thisIndex].className,
					name: 		parent.divArray[thisIndex].name,
					x:			this.style.left,
					y:			this.style.top,
					maxInput: 	parent.divArray[thisIndex].maxInput,
					maxOutput:  parent.divArray[thisIndex].maxOutput
				};
				parent.divArray.splice(thisIndex,1,movedObject);
				}
			});
			
			//ADD ENDPOINTS
			if(parent.divArray[i].maxInput!= 0){
				var input = {
					anchor: ["TopCenter"],
					maxConnections: parent.divArray[i].maxInput,
					isSource:false, 
					isTarget:true,
					connector : "Bezier",
					paintStyle:{ radius: 7, fillStyle:"red", outlineColor:"black", outlineWidth:1 },
					connectorStyle: { lineWidth:2, strokeStyle:'blue' },
					scope:"blueline",
					dragAllowedWhenFull:false     
				};
				logicInstance.addEndpoint(parent.divArray[i].id, input);
			}
			
			if(parent.divArray[i].maxOutput!= 0){
				var output = {
					anchor: ["BottomCenter"],
					maxConnections:  parent.divArray[i].maxOutput,
					isSource:true, 
					isTarget:false,
					connector : "Bezier",
					paintStyle:{ radius: 7, fillStyle:"green", outlineColor:"black", outlineWidth:1},
					connectorStyle: { lineWidth:2, strokeStyle:'blue' },
					scope:"blueline",
					dragAllowedWhenFull:false     
				}; 
				logicInstance.addEndpoint(parent.divArray[i].id, output);
			}
			
		}
	parent.setCreatedNodes(parent.divArray[parent.divArray.length-1].id);	
	}
	
}

//When the user leaves the page
window.onbeforeunload = function (e) {
	parent.saveFile();
}

//Jsplumb.ready �r n�r sidan laddat klart, s� att jsplumb f�r k�ras med alla divvar
jsPlumb.ready(function() {
	logicInstance = jsPlumb.getInstance();
	addSavedDivs();
	addLatestConnections();
});