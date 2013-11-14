package com.robotgame.gameengine.Robot;

import com.google.gson.Gson;
import com.robotgame.gameengine.Robot.Builder.RobotBlueprint;
import com.robotgame.gameengine.Robot.Nodes.NodeConnection;

public class testRobot {
	public String json;
	public testRobot(){
	RobotBlueprint blueprint = new RobotBlueprint();
    RobotBlueprint blueprint2 = new RobotBlueprint();
    //Todo: Fill blueprint with nodes

    /***** Implemented Nodes ********
     //Sensor nodes
     DistanceSensor,
     //Logic nodes
     And, Or, Not, And3, Or3, Delay, True, False, Default, TicTac, Clock, Majority,
     //Action nodes
     Debug,
     //Default node
     Default
     */

    blueprint.AddNode("Clock", 2, 0, 0);                 blueprint.AddNode("TicTac", 7, 0, 1);
//                        |                                /
    blueprint.AddNode("Delay", 30, 0, 2);//             /
//                        |                              /
                        blueprint.AddNode("Or", 0, 0, 3);
//                        |
                        blueprint.AddNode("Debug", 0, 0, 4);
                                         //From node, From channel
    blueprint.AddConnection(new NodeConnection(0, 0, 2, 0));
    blueprint.AddConnection(new NodeConnection(2, 0, 3, 0));
    blueprint.AddConnection(new NodeConnection(1, 0, 3, 1));
    blueprint.AddConnection(new NodeConnection(3, 0, 4, 0));





    Gson gson = new Gson();
    json = gson.toJson(blueprint);
	}
}
