package com.robotgame.gameengine.Robot.Builder;

import com.robotgame.gameengine.Robot.Robot;
import com.robotgame.gameengine.Robot.Nodes.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Oskar
 * Date: 2013-10-14
 * Time: 12:25
 * To change this template use File | Settings | File Templates.
 */


/**
 * Helper class for creating functioning Robot objects
*/
public class RobotFactory
{

    /**
     * @param  blueprint  contains all the necessary info about the robot
     * @param  robotIndex the index of this robot
     * @return      a functioning robot object ready to be used bu a Match
     * @see         RobotBlueprint, Robot
     */
    public static Robot CreateRobot(RobotBlueprint blueprint, int robotIndex)
    {
        //Comment this line unless CheckIfLegal is working correctly.
        //if (!blueprint.CheckIfLegal()) return null;

        PackIndices(blueprint);
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


    public static void PackIndices(RobotBlueprint blueprint)
    {

        Map<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
        Integer counter = 0;
        for (NodeData n : blueprint.nodes)
        {
            indexMap.put(Integer.valueOf(n.id), counter);
            n.id = counter.intValue();
            counter++;
        }

        for (ConnectionData c : blueprint.connections)
        {
            c.targetId = indexMap.get(c.targetId).intValue();
            c.sourceId = indexMap.get(c.sourceId).intValue();
        }


    }

    public static boolean CheckBlueprint(RobotBlueprint blueprint)
    {

        //Kolla så att inga connections leder till sig själva

        return true;
    }

}
