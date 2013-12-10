package com.robotgame.gameengine.Robot.Nodes;

import com.robotgame.gameengine.Robot.Robot;

/**
 * Created with IntelliJ IDEA.
 * User: Oskar
 * Date: 2013-10-14
 * Time: 10:22
 * To change this template use File | Settings | File Templates.
 */

public enum NodeType
{
    //Sensor nodes
    DistanceSensor, SectorSensor, //MirroredSectorSensor, CollisionSensor, FrontCollisionSensor, RearCollisionSensor, (TurretSensor)

    //Logic nodes
    And, Or, Not, Delay, True, False, Default, TicTac, Clock, Majority, //ProlongTrue, ProlongFalse

    //Action nodes
    Debug, MoveForward, MoveBackwards, TurnLeft, TurnRight, Boost  //, FireWeapon1, FireWeapon2, FireWeapon3, TurnTurretLeft, TurnTurretRight

}
