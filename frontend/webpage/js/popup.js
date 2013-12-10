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
console.log("awal");
//temporär inloggning
/*
var username = window.frames[0].document.getElementById("username").value;
console.log(username);
//Code to get username from DB...

if(username.toLowerCase() == "lorb"){
	
}
else{
	alert("Wrong username or Password");
}*/
	document.getElementById('frame').src = id;
	return false;
} 