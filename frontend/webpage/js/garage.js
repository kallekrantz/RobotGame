$(document).ready(function() {

	$("#weapon").droppable({
		accept: ".weapon",
		drop: function (event, ui) {
            if (ui.draggable.is('.dropped')) return false;
			$("#weapon").text("");
            $("#weapon").append("<img src='Style/"+ui.draggable[0].id+".jpg' height='100px' width='100px'>");
		}
	});
	
	$("#chassi").droppable({
		accept: ".chassi",
		drop: function (event, ui) {
            if (ui.draggable.is('.dropped')) return false;
			$("#chassi").text("");
            $("#chassi").append("<img src='Style/"+ui.draggable[0].id+".jpg' height='100px' width='100px'>");
        }
	});
	
	$("#wheels").droppable({
		accept: ".wheels",
		drop: function (event, ui) {
            if (ui.draggable.is('.dropped')) return false;
			$("#wheels").text("");
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
	
});