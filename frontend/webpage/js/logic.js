/*
	*Authors: Mikael Pettersson & Christoffer Wern
	*
	*The intention of logic.js is to handle all functionality of the logic.html-file, this includes 
	*dragging and dropping logic nodes and handling input to global variables amongst others.
	*
*/

var logicInstance;
var arrayOfConnections = new Array();
var selectedList = new Array();
	
function indexOfObjectId(array, obj){
	for(var i=0;i<array.length;i++){
		if(array[i].id==obj){
			return i;
		}
	}
	return -1;
}
	
function addNode(nodeType,name,maxInput,maxOutput){
	Node.createNode(nodeType,name,maxInput,maxOutput);
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
		createNode:function(nodeType,name,maxInput,maxOutput){
			divId = parent.getCreatedNodes() + "_ID_" + nodeType; 
			divClass = nodeType;
			divName = name;
			divText = "<div id='" + divId + "' class='"+divClass+"'><p>";
			divText = divText+divName+"</p></div>";
			divMaxInput = maxInput;
			divMaxOutput = maxOutput;
			
			/*
			if(nodeType=="Clock"){
				divText = divText+nodeType+"<br/><br/>Period: <input name='textfield'  class='clockNode' type='text' /></p></div>";
				maxInput = -1;
				maxOutput = 1;
			}
			*/
			
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
		arrayOfConnections.push(conn);
		}
	);
	
	//N�r en connection tas bort (t.ex. om man tar bort en nod) tas den �ven bort fr�n arrayen.
	logicInstance.bind("jsPlumbConnectionDetached", function(conn){
		arrayOfConnections.splice(arrayOfConnections.indexOf(conn),1);
		//typ logicInstance.connect(arrayOfConnections[0]);
		}
	);
	
	//Om man klickar p� en div ska den bli "selected"
	//Sparas undan i global variable "selected" och f�r r�d ram
	//Om man klickar p� den igen blir inte "selected" l�ngre
	/*
	$('#myCanvas').click(function(event){
		var tar = '#'+event.target.id;
		
		if(tar!='#myCanvas'){
			if(arrayContains(selectedList,event.target.id)){
				$(tar).css("border", "1px solid black");
				selectedList.splice(selectedList.indexOf(event.target.id),1);
			}
			else{
				//Nya selected f�r r�d ram
				$(tar).css("border", "2px solid red");
				selectedList.push(event.target.id);
			}
		}
	});
	
	$(document).keydown(function(e){
		if (e.keyCode == 46) { 
			var parentIndex;
			for(var i=0;i<selectedList.length;i++){
				logicInstance.removeAllEndpoints(selectedList[i]);
				logicInstance.detachAllConnections(selectedList[i]);
				$("#" + selectedList[i]).remove();
				parentIndex = indexOfObjectId(parent.divArray, selectedList[i]);
				parent.divArray.splice(parentIndex,1);
				alert(parentIndex);
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
			source:parent.connectionArray[i].sourceId,
			target:parent.connectionArray[i].targetId,
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
