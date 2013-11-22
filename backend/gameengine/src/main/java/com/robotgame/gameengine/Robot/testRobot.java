package com.robotgame.gameengine.Robot;

import com.google.gson.Gson;
import com.robotgame.gameengine.Robot.Builder.RobotBlueprint;
import com.robotgame.gameengine.Robot.Nodes.NodeConnection;
import com.robotgame.gameengine.Robot.Nodes.NodeType;

import java.util.Vector;

public class testRobot {
	public String json;
	public testRobot(){
		 RobotBlueprint blueprint = new RobotBlueprint(6, 4);

	        //Todo: Fill blueprint with nodes


	/*
	        blueprint.AddNode(NodeType.Clock, 200, 0);                 blueprint.AddNode(NodeType.TicTac, 700, 1);
//	                            |                                   /
	          blueprint.AddNode(NodeType.Delay, 30, 2);//            /
//	                               |                              /
	                            blueprint.AddNode(NodeType.Or, 0, 3);
//	                                          |
	                            blueprint.AddNode(NodeType.Debug, 0, 4);
	*/
	        //Test robot that drives in circles, alternating left and right.
	                            blueprint.AddNode(NodeType.TicTac, 2000, 0);                            blueprint.AddNode(NodeType.True, 0, 1);
//	                                 |                           |                                             |
	        blueprint.AddNode(NodeType.Not, 0, 2);//               |                                             |
//	                                 |                           |                                             |
	        blueprint.AddNode(NodeType.TurnRight, 0, 4);   blueprint.AddNode(NodeType.TurnLeft, 0, 3);  blueprint.AddNode(NodeType.MoveForward, 0, 5);


	                   //From node, From channel, index
	        blueprint.AddConnection(0, 2, 0);
	        blueprint.AddConnection(0, 3, 1);
	        blueprint.AddConnection(2, 4, 2);
	        blueprint.AddConnection(1, 5, 3);







        Vector<RobotBlueprint> blueprints = new Vector<RobotBlueprint>();
        blueprints.add(blueprint);





    Gson gson = new Gson();
    json = gson.toJson(blueprint);
    
	}
}
