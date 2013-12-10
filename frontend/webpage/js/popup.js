//POP-UP
function openpopup(id){

      //Make the background divs tag visible
      var divobj = document.getElementById(id);
      divobj.style.visibility = "visible";
	  
	  var divbg = document.getElementById('bg');
      divbg.style.visibility = "visible";
} 

function closepopup(id){

      var divbg = document.getElementById('bg');
      divbg.style.visibility = "hidden";
	  
      var divobj = document.getElementById(id);
      divobj.style.visibility = "hidden";
	  
}

function changeFrame(id){
//temporär inloggning
/*
var iFrame = $("#frame").contents().find("#usrName").value;
console.log(iFrame);*/
//if(usrName == DBUSERNAME WRERSW>RG
	document.getElementById('frame').src = id;
	
	return false;
} 