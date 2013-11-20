function NetworkMatch(robots){
this.robots = robots;
}
function NetworkRobot(parts,x,y,rotation){
this.parts = parts;
this.posX = x;
this.posY = y;
this.velX = 0;
this.velY = 0;
this.rotation = rotation;
this.angularVelocity = 0;
this.getX = function(){return this.posX;};
this.setX = function(x){this.posX=x;};
this.getY = function(){return this.posY;};
this.setY = function(y){this.posY=y;};
this.getdX = function(){return this.velX;};
this.setdX = function(dx){this.velX=dx;};
this.getdY = function(){return this.posX;};
this.setdY = function(dy){this.velY=dy;};
this.setRotation = function(rot){this.rotation = rot;};
this.getRotation = function(){return this.rotation;};
this.setAngularVelocity = function(angVel){this.angularVelocity = angVel;};
this.getAngularVelocity = function(){return this.angularVelocity;};
}
function NetworkPart(type){
//stub
}