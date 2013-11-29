package com.robotgame.gameengine.Robot;

import com.google.gson.Gson;
import com.robotgame.gameengine.Robot.Builder.RobotBlueprint;
import com.robotgame.gameengine.Robot.Nodes.NodeConnection;
import com.robotgame.gameengine.Robot.Nodes.NodeType;

import java.util.Vector;

public class testRobot {
	public String json;
	public testRobot(){
	RobotBlueprint blueprint = new RobotBlueprint(5, 4);
    //RobotBlueprint blueprint2 = new RobotBlueprint();
    //Todo: Fill blueprint with nodes

        blueprint.AddNode(NodeType.Clock, 2, 0);                 blueprint.AddNode(NodeType.TicTac, 7, 1);
//                            |                                   /
        blueprint.AddNode(NodeType.Delay, 30, 2);//            /
//                               |                              /
        blueprint.AddNode(NodeType.Or, 0, 3);
//                                          |
        blueprint.AddNode(NodeType.Debug, 0, 4);


        //From node, From channel, index
        blueprint.AddConnection(0, 2, 0);
        blueprint.AddConnection(2, 3, 1);
        blueprint.AddConnection(1, 3, 2);
        blueprint.AddConnection(3, 4, 3);






        Vector<RobotBlueprint> blueprints = new Vector<RobotBlueprint>();
        blueprints.add(blueprint);





    Gson gson = new Gson();
    json = gson.toJson(blueprint);
	}
}
