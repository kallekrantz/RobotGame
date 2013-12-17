function createNewUser(){

var user = {
	id 			: "",
	firstname	: document.getElementById("newFirstname").value,
	lastname	: document.getElementById("newLastname").value,
	pwdHash 	: document.getElementById("newPassword").value,
	username 	: document.getElementById("newUserName").value
};
console.log(user);

return parent.changeFrame('login.html');
}