/**
 * A representation of the matchstate sent from the server
 * @method NetworkMatch
 * @param {} matchState
 * @return 
 */
function NetworkMatch(matchState){
	this.robots = new Array();
	for(robot=0;robot<matchState.robotStates.length;robot++){
		this.robots[robot] = new NetworkRobot(matchState.robotStates[robot]);
	}
}
/**
 * A representation of the robotstate sent from the server
 * @method NetworkRobot
 * @param {} robotState
 * @return 
 */
function NetworkRobot(robotState){
this.parts;
this.posX = robotState.pos.x;
this.posY = 0;
this.posZ = robotState.pos.y;
this.velX = robotState.vel.x;
this.velY = 0;
this.velZ = robotState.vel.y;
this.rotation = robotState.rot;
this.angularVelocity = robotState.w;
this.fire = robotState.fire;
/**
 * Returns the x coordinate of this robot.
 * @method getX
 * @return MemberExpression
 */
this.getX = function(){return this.posX;};
/**
 * Set the x coordinate of this robot.
 * @method setX
 * @param {} x
 * @return 
 */
this.setX = function(x){this.posX=x;};
/**
 * Returns the y coordinate of this robot.
 * @method getY
 * @return MemberExpression
 */
this.getY = function(){return this.posY;};
/**
 * Set the y coordinate of this robot.
 * @method setY
 * @param {} y
 * @return 
 */
this.setY = function(y){this.posY=y;};
/**
 * Returns the z coordinate of this robot.
 * @method getZ
 * @return MemberExpression
 */
this.getZ = function(){return this.posZ;};
/**
 * Set the z coordinate of this robot.
 * @method setZ
 * @param {} z
 * @return 
 */
this.setZ = function(z){this.posZ=z;};
/**
 * Returns the x coordinate velocity of this robot.
 * @method getdX
 * @return MemberExpression
 */
this.getdX = function(){return this.velX;};
/**
 * Set the x coordinate velocity of this robot
 * @method setdX
 * @param {} dx
 * @return 
 */
this.setdX = function(dx){this.velX=dx;};
/**
 * Returns the y coordinate velocity of this robot.
 * @method getdY
 * @return MemberExpression
 */
this.getdY = function(){return this.velY;};
/**
 * Set the y coordinate velocity of this robot
 * @method setdY
 * @param {} dy
 * @return 
 */
this.setdY = function(dy){this.velY=dy;};
/**
 * Returns the z coordinate velocity of this robot.
 * @method getdZ
 * @return MemberExpression
 */
this.getdZ = function(){return this.posZ;};
/**
 * Set the z coordinate velocity of this robot.
 * @method setdZ
 * @param {} dz
 * @return 
 */
this.setdZ = function(dz){this.velZ=dz;};
/**
 * Set the angle of rotation for this robot
 * @method setRotation
 * @param {} rot
 * @return 
 */
this.setRotation = function(rot){rotation = rot;};
/**
 * Returns the angle of rotation of this robot
 * @method getRotation
 * @return MemberExpression
 */
this.getRotation = function(){return this.rotation;};
/**
 * Set the angular velocity of this robot.
 * @method setAngularVelocity
 * @param {} angVel
 * @return 
 */
this.setAngularVelocity = function(angVel){angularVelocity = angVel;};
/**
 * Returns the angular velocity of this robot
 * @method getAngularVelocity
 * @return MemberExpression
 */
 
 /**
 * Set to true if the robot is firing
 * @method setFire
 * @param {} f
 */
this.setFire = function(f){this.fire = f;};

 /**
 * Set to true if the robot is firing
 * @method getFire
 * @return MemberExpression
 */
this.getFire = function(){return this.fire;};
 
this.getAngularVelocity = function(){return this.angularVelocity;};
}
/**
 * Still to be implemented
 * @method NetworkPart
 * @param {} type
 * @return 
 */
function NetworkPart(type){
//stub
}
