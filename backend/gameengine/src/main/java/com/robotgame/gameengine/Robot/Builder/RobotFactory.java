package com.robotgame.gameengine.Robot.Builder;

import com.robotgame.gameengine.Robot.Robot;
import com.robotgame.gameengine.Robot.Nodes.*;

/**
 * Created with IntelliJ IDEA.
 * User: Oskar
 * Date: 2013-10-14
 * Time: 12:25
 * To change this template use File | Settings | File Templates.
 */
public class RobotFactory
{
    public static Robot CreateRobot(RobotBlueprint blueprint, int robotIndex)
    {
        int numNodes = blueprint.GetNumNodes();
        Node[] nodes = new Node[numNodes];

        for (NodeData n : blueprint.nodes)
        {
            nodes[n.id] = NodeFactory.Create(n, robotIndex);
        }

        int numConnections = blueprint.GetNumConnections();
        NodeConnection[] connections = new NodeConnection[numConnections];
        int index = 0;

        for (ConnectionData c : blueprint.connections)
        {
            connections[index] = new NodeConnection(c.sourceId, c.targetId);
            nodes[c.targetId].AddInput(index);
            index++;
        }


        Robot robot = new Robot(nodes, connections, robotIndex);

        return robot;
    }

}
