/**
*File for database functionality, functions to call the db to
*get/set the robot and user.
*
*/

var storage = storageConnector("");
//Put
function saveRobot(user, robot)
{

	storage.putRobot(user.id, robot.id, {robot.robotDesign, robot.robotName}, function(data){
		if(!data){
		alert('Robot was not saved');
		}
	});
	
}

//Get
function loadRobot(userId, robotId)
{

}

//Post
function createRobot(userId, robot)
{

}



