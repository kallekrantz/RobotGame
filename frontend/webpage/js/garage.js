/*
	*Author: Mikael Pettersson & Christoffer Wern
	*
	*
	*This file is intended to cover all functions needed for the garage.html-page aswell as some communication
	*with its' child page(the 3d-scene). It loads some needed variables as soon as the page is loaded, to keep
	*track of the users' robot parts.
	*
*/

$(document).ready(function() {

	addComponents();
	
	//TODO - fix so this works with correct variable names, maybe make a function with an array 
	//that contains {'chassi', 'wheels' ......} etc.
	$("#weapon").droppable({
		accept: ".weapon",
		drop: function (event, ui) {
            if (ui.draggable.is('.dropped')) return false;
			$("#weapon").text("");
			parent.components[2] = ui.draggable[0].id;
            $("#weapon").append("<img src='Style/"+ui.draggable[0].id+".jpg' height='100px' width='100px'>");
			document.getElementById("robot3D").contentWindow.hasChanged(ui.draggable[0].id);
		}
	});
	
	$("#chassi").droppable({
		accept: ".chassi",
		drop: function (event, ui) {
            if (ui.draggable.is('.dropped')) return false;
			$("#chassi").text("");
			parent.components[0] = ui.draggable[0].id;
            $("#chassi").append("<img src='Style/"+ui.draggable[0].id+".jpg' height='100px' width='100px'>");
			document.getElementById("robot3D").contentWindow.hasChanged(ui.draggable[0].id);
        }
	});
	
	$("#wheels").droppable({
		accept: ".wheels",
		drop: function (event, ui) {
            if (ui.draggable.is('.dropped')) return false;
			$("#wheels").text("");
			parent.components[1] = ui.draggable[0].id;
			parent.saveFile();
            $("#wheels").append("<img src='Style/"+ui.draggable[0].id+".jpg' height='100px' width='100px'>");
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
	/*******WEAPONS*******/
	$('#Laser2_2').hover(
		function(){
			$("#robotView").text("");
			$("#robotView").append("<h1>This is your standard futuristic awesome lazer. It shoots huge holes in the enemy armor and is awesome. </h1>");
	});
	$('#weapon2').hover(
		function(){
			$("#robotView").text("");
			$("#robotView").append("PLACEHOLDER");
	});
	
	/*******CHASSIS*******/
	$('#TankGroup2').hover(
		function(){
			$("#robotView").text("");
			$("#robotView").append("<h1>This is your standard robot chassi.</h1>");
	});
	$('#chassi2').hover(
		function(){
			$("#robotView").text("");
			$("#robotView").append("PLACEHOLDER");
	});
	
	/*******MOBILITY*******/
	$('#wheels1').hover(
		function(){
			$("#robotView").text("");
			$("#robotView").append("PLACEHOLDER");
	});
	
});

function addComponents(){
	if(parent.components[0] != null){
		$("#chassi").text("");
		$("#chassi").append("<img src='Style/"+parent.components[0]+".jpg' height='100px' width='100px'>");
	}
	if(parent.components[1] != null){
		$("#wheels").text("");	
		$("#wheels").append("<img src='Style/"+parent.components[1]+".jpg' height='100px' width='100px'>");
	}
	if(parent.components[2] != null){
		$("#weapon").text("");
		$("#weapon").append("<img src='Style/"+parent.components[2]+".jpg' height='100px' width='100px'>");
	}
	if(parent.components[3] != null){
		$("#weapon").text("");
		$("#weapon").append("<img src='Style/"+parent.components[3]+".jpg' height='100px' width='100px'>");
	}
	if(parent.components[4] != null){
		$("#weapon").text("");
		$("#weapon").append("<img src='Style/"+parent.components[3]+".jpg' height='100px' width='100px'>");
	}
}


