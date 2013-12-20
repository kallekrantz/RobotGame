/**
*File for database functionality, functions to call the db to
*get/set the robot and user.
*
*/

var storage = storageConnector("localhost");
//var storage = storageConnector("kallekrantz.com");

//Put
function updateRobot(user, robot)
{
	
	putRobotObj = {
		robotDesign: JSON.stringify(robot.robotDesign),
		robotName: 	 robot.robotName
	}
	console.log(putRobotObj);
	storage.putRobot(user.id, robot.id, putRobotObj, function(data){
		updateRobotList(data);
		console.log(data);
	});
}

//Get By Id
function loadRobotById(userId, robot)
{
	storage.getRobotById(user.id, robot.id, function(data){
		
	});
}

//Get First
function loadFirstRobot(userId)
{
	storage.getRobotList(userId, function(data){
		if(data.length != 0){
		
			robot.id = data[0].id;
			robot.robotName = data[0].robotName;
			robot.robotDesign = JSON.parse(data[0].robotDesign);
			updateRobotDesign(data[0].robotDesign);
			console.log(data);
			changeFrame('garage.html');
			
			for(var i=0; i<data.length; i++)
			{
				var pushObject = data[i];
				pushObject.robotDesign = JSON.parse(data[i].robotDesign);
				robotList.push(pushObject);
			}
			
			}
		else{
			robot.robotName = "DefaultBot";
			robot.robotDesign = robotDesign;
			createRobot(userId, robot);
			changeFrame('garage.html');
		}
	});
}

//Post
function createRobot(userId, robot)
{
	postRobotObj = {
		robotDesign: JSON.stringify(robot.robotDesign),
		robotName: 	 robot.robotName
	}
	
	storage.postRobot(user.id, postRobotObj, function(data){
		//Check if something actually did get posted
		var robObject = data;
		robObject.robotDesign = JSON.parse(data.robotDesign);
		updateRobotList(robObject);
		console.log('robot created!');
		console.log(robObject);
	});
}

function createUser(user)
{
	storage.postUser(user, function(data){
	});
}

function updateUser(user, obj)
{
	storage.putUser(user.id, obj, function(data){

	});
}

function loadUser(id)
{
	
	storage.getUserById(id, function(data){
	
		user.id = data.id;
		user.username = data.username;
		user.firstname = data.firstname;
		user.lastname = data.lastname;
		loadFirstRobot(user.id);
	});
}

function updateRobotDesign(JSONobject)
{
	JSONobject = JSON.parse(JSONobject); 
	robotDesign.connections = JSONobject.connections;
	robotDesign.nodes = JSONobject.nodes;
	robotDesign.components = JSONobject.components;	
	connections = JSONobject.connections;
	nodes = JSONobject.nodes;
	components = JSONobject.components;
}


