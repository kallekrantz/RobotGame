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
    S_DistanceSensor, // S_SectorSensor, S_MirroredSectorSensor,

    //Logic nodes
    L_And, L_Or, L_Not, L_And3, L_Or3, L_Delay, L_True, L_False, L_Default, L_TicTac, L_Clock, L_MajorityOf3,

    //Action nodes
    A_Debug,
    //Default node
    Default
}
