/**
	*Author: Mikael Pettersson & Christoffer Wern
	*
	*
	*This file is intended to cover all functions needed for the garage.html-page aswell as some communication
	*with its' child page(the 3d-scene). It loads some needed variables as soon as the page is loaded, to keep
	*track of the users' robot parts.
	*
*/

//stringObject handles the information about each component in the garage, the information is either here, or could be stored in a textfile/database.
var stringObject = new Array();
stringObject[0] = {id:"Laser",string:"<h3>Laser</h3><p>This is an awesome laser</p>"};
stringObject[1] = {id:"Chassi1",string:"<h3>Standard Chassi</h3><p>This is your standard Chassi, nothing special...</p>"}
stringObject[2] = {id:"Chassi2",string:"<h3>Awesome Chassi</h3><p>WOW THIS CHASSI IS AWESOME!!!</p>"}
stringObject[3] = {id:"Wheels",string:"<h3>Wheels</h3><p>Who care about wheels?</p>"}

//stringObject[] = {id:"",string:"<p></p>"}
$(document).ready(function() {

	addComponents();
	
	$("#weaponBox").droppable({
		accept: ".weapon",
		drop: function (event, ui) {
            if (ui.draggable.is('.dropped')) return false;
			$("#weaponBox").text("");
			parent.components[2] = ui.draggable[0].id;
			parent.actionNodes.push("weaponButton");
            $("#weaponBox").append("<img src='Style/"+ui.draggable[0].id+".jpg' height='100px' width='100px'>");
			document.getElementById("robot3D").contentWindow.hasChanged(ui.draggable[0].id);
		}
	});
	
	$("#chassiBox").droppable({
		accept: ".chassi",
		drop: function (event, ui) {
            if (ui.draggable.is('.dropped')) return false;
			$("#chassiBox").text("");
			parent.components[0] = ui.draggable[0].id;
			parent.maxNrOfSensorNodes = 3;
			//to show continuation with other chassis.
			//if(ui.draggable[0].id == "chassi2") parent.maxnrOfSensorNodes = 5;
            $("#chassiBox").append("<img src='Style/"+ui.draggable[0].id+".jpg' height='100px' width='100px'>");
			document.getElementById("robot3D").contentWindow.hasChanged(ui.draggable[0].id);
        }
	});
	
	$("#wheelsBox").droppable({
		accept: ".wheels",
		drop: function (event, ui) {
            if (ui.draggable.is('.dropped')) return false;
			$("#wheelsBox").text("");
			parent.actionNodes.push("wheelButton");
			parent.components[1] = ui.draggable[0].id;
            $("#wheelsBox").append("<img src='Style/"+ui.draggable[0].id+".jpg' height='100px' width='100px'>");
			document.getElementById("robot3D").contentWindow.hasChanged(ui.draggable[0].id);
		}
	});
	
	$( ".chassi").draggable({
        appendTo: "body",
        helper: "clone"
    });
	
	$( ".weapon").draggable({
        appendTo: "body",
        helper: "clone"
    });
	
	$( ".wheels").draggable({
        appendTo: "body",
        helper: "clone"
    });
	
	//Hover Functionality for part information
	/*******DIVS**********/
	$('#weaponBox').hover(
		function(){
		if(parent.components[2] != null){
			showThisInfo(parent.components[2]);
		}
		else{
			$("#robotView").text("");
			$("#robotView").append("<p>This box should contain the robot's main weapon</p>");
		}},
		function(){
			$("#robotView").text("");
		}
	);
	
	$('#chassiBox').hover(
		function(){
		if(parent.components[0] != null){
			showThisInfo(parent.components[0]);
		}
		else{
			$("#robotView").text("");
			$("#robotView").append("<p>This box should contain the robot's chassi.</p>");
		}},
		function(){
			$("#robotView").text("");
		}
	);
	
	$('#wheelsBox').hover(
		function(){
		if(parent.components[1] != null){
			showThisInfo(parent.components[1]);
		}
		else{
			$("#robotView").text("");
			$("#robotView").append("<p>This box should contain the robot's wheels</p>");
		}},
		function(){
			$("#robotView").text("");
		}
	);
	/*********************/
	
	/*******WEAPONS*******/
	$('.weapon').hover(
		function(e){
			showThisInfo(e.currentTarget.id);
		},
		function(){
			$("#robotView").text("");
		}
	);
	/*********************/
	
	/*******CHASSIS*******/
	$('.chassi').hover(
		function(e){
			showThisInfo(e.currentTarget.id);
		},
		function(){
			$("#robotView").text("");
		}
	);
	/**********************/
	
	/*******MOBILITY*******/
	$('.wheels').hover(
		function(e){
			showThisInfo(e.currentTarget.id);
		},
		function(){
			$("#robotView").text("");
		}
	);
	/**********************/
	
	
	document.getElementById("menuHeaderLeft").innerHTML="<p>"+parent.user.username+"</p>";	
	
	var eSelect = document.getElementById('robotList');
    eSelect.onchange = function() {
	
		parent.loadOtherRobot(eSelect.value);
		eSelect.selectedIndex = eSelect.value;
		
		document.getElementById("robot3D").contentWindow.hasChanged();
		setDefault();
		addComponents();
	}
	
});

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

function setDefault(){
	$("#chassiBox").text("");
	$("#wheelsBox").text("");
	$("#weaponBox").text("");
}

function addComponents(){
	console.log('addcomp');
	if(parent.components[0] != null){
		parent.maxNrOfSensorNodes = 3;
		//to show continuation with other chassis.
		//if(ui.draggable[0].id == "chassi2") parent.maxnrOfSensorNodes = 5;
		$("#chassiBox").text("");
		$("#chassiBox").append("<img src='Style/"+parent.components[0]+".jpg' height='100px' width='100px'>");
	}
	if(parent.components[1] != null){
		parent.actionNodes.push("wheelButton");
		$("#wheelsBox").text("");	
		$("#wheelsBox").append("<img src='Style/"+parent.components[1]+".jpg' height='100px' width='100px'>");
	}
	if(parent.components[2] != null){
		parent.actionNodes.push("weaponButton");
		$("#weaponBox").text("");
		$("#weaponBox").append("<img src='Style/"+parent.components[2]+".jpg' height='100px' width='100px'>");
	}
	if(parent.components[3] != null){
		//parent.actionNodes.push("weaponButton2"); tills vidare
		$("#weaponBox").text("");
		$("#weaponBox").append("<img src='Style/"+parent.components[3]+".jpg' height='100px' width='100px'>");
	}
	if(parent.components[4] != null){
		//parent.actionNodes.push("weaponButton3"); tills vidare
		$("#weaponBox").text("");
		$("#weaponBox").append("<img src='Style/"+parent.components[3]+".jpg' height='100px' width='100px'>");
	}
	if(parent.robotList.length != 0){
		var dropDownList = document.getElementById('robotList');
		
		if(dropDownList.length<parent.robotList.length){
			for(var i=0; i<parent.robotList.length; i++)
			{
				var option = document.createElement("option");
				option.text = parent.robotList[i].robotName;
				option.value = i;
				dropDownList.add(option, null);
			}
		}
	}
}

//When the user leaves the page
window.onbeforeunload = function (e) {

	parent.robot.robotDesign = parent.robotDesign;
	//Saves the robot, NOT FUNCTIONAL!!!
	parent.updateRobot(parent.user, parent.robot);
	
}


