function createNewUser(){

var user = {
	username 	: document.getElementById("newUserName").value,
	firstname	: document.getElementById("newFirstname").value,
	lastname	: document.getElementById("newLastname").value,
	password 	: document.getElementById("newPassword").value
};
console.log(user);
parent.createUser(user);

return parent.changeFrame('login.html');
}

function login()
{
var username =  document.getElementById("username").value;
var password =  document.getElementById("password").value;
	
	//Check if valid login-stuff
	
	//if(correctName&Password) ->
	parent.loadUser(username);

}