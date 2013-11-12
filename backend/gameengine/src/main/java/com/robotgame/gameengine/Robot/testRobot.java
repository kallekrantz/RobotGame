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
     S_DistanceSensor,
     //Logic nodes
     L_And, L_Or, L_Not, L_And3, L_Or3, L_Delay, L_True, L_False, L_Default, L_TicTac, L_Clock, L_MajorityOf3,
     //Action nodes
     A_Debug,
     //Default node
     Default
     */

    blueprint.AddNode("L_Clock", 2, 0, 0);                 blueprint.AddNode("L_TicTac", 7, 0, 1);
//                        |                                /
    blueprint.AddNode("L_Delay", 30, 0, 2);//             /
//                        |                              /
                        blueprint.AddNode("L_Or", 0, 0, 3);
//                        |
                        blueprint.AddNode("A_Debug", 0, 0, 4);
                                         //From node, From channel
    blueprint.AddConnection(new NodeConnection(0, 0, 2, 0));
    blueprint.AddConnection(new NodeConnection(2, 0, 3, 0));
    blueprint.AddConnection(new NodeConnection(1, 0, 3, 1));
    blueprint.AddConnection(new NodeConnection(3, 0, 4, 0));





    Gson gson = new Gson();
    json = gson.toJson(blueprint);
	}
}
