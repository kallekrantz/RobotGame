function NetworkMatch(matchState){
	this.robots = new Array();
	for(robot=0;robot<matchState.robotStates.length;robot++){
		this.robots[robot] = new NetworkRobot(matchState.robotStates[robot]);
	}
}
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
this.getX = function(){return this.posX;};
this.setX = function(x){this.posX=x;};
this.getY = function(){return this.posY;};
this.setY = function(y){this.posY=y;};
this.getZ = function(){return this.posZ;};
this.setZ = function(z){this.posZ=z;};
this.getdX = function(){return this.velX;};
this.setdX = function(dx){this.velX=dx;};
this.getdY = function(){return this.velY;};
this.setdY = function(dy){this.velY=dy;};
this.getdZ = function(){return this.posZ;};
this.setdZ = function(dz){this.velZ=dz;};
this.setRotation = function(rot){this.rotation = rot;};
this.getRotation = function(){return this.rotation;};
this.setAngularVelocity = function(angVel){this.angularVelocity = angVel;};
this.getAngularVelocity = function(){return this.angularVelocity;};
}
function NetworkPart(type){
//stub
}
