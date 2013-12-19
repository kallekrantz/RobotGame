/**
*File for database functionality, functions to call the db to
*get/set the robot and user.
*
*/

var storage = storageConnector("localhost");

//Put
function updateRobot(user, robot)
{
	
	putRobotObj = {
		robotDesign: JSON.stringify(robot.robotDesign),
		robotName: 	 robot.robotName
	}
	console.log(putRobotObj);
	storage.putRobot(user.id, robot.id, putRobotObj, function(data){
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
			robot.robotDesign = data[0].robotDesign;
			updateRobotDesign(robot.robotDesign);
			console.log(data.length);
			changeFrame('garage.html');
			
			for(var i=0; i<data.length; i++)
			{
				robotList.push(data[i]);
			}
			console.log(robotList);
			
			}
		else{
			robot.robotName = "DefaultBot";
			createRobot(userId, robot);
			changeFrame('garage.html');
		}
	});
}

//Post
function createRobot(userId, robot)
{
	postRobotObj = {
		robotDesign: robot.robotDesign,
		robotName: 	 robot.robotName
	}
	
	storage.postRobot(user.id, postRobotObj, function(data){
		//Check if something actually did get posted
		console.log('robot created!');
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
		console.log(user);
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


