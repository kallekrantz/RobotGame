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


        //If the blueprint has issues it is replaced with a working but inactive dummy blueprint.
        if (blueprint == null)
            blueprint = RobotBlueprint.GetDummyBlueprint();
        else if (blueprint.nodes == null || blueprint.components == null)
            blueprint = RobotBlueprint.GetDummyBlueprint();
        else if (blueprint.GetNumNodes() < 2 || blueprint.GetNumConnections() < 1)
            blueprint = RobotBlueprint.GetDummyBlueprint();
        else
        {
            PackIndices(blueprint);
            if (!CheckIfLegal(blueprint))  //Comment this line unless CheckIfLegal is working correctly.
                blueprint = RobotBlueprint.GetDummyBlueprint();
        }

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


    private static boolean[] _visited, _ok;
    private static int _numConnections;
    private static boolean _loopFound;

    /**
     * Not implemented!
     * @return True if the blueprint is a legit working robot.
     */
    public static boolean CheckIfLegal(RobotBlueprint blueprint)
    {
        _numConnections = blueprint.GetNumConnections();
        _visited = new boolean[_numConnections];
        _ok = new boolean[blueprint.GetNumNodes()];
        _loopFound = false;



        /*
        for (int n : nodes)
        {
            if (!_ok)

        } */

        //Todo: Check for circular dependencies and illegal connections.
        //Since I'm not sure where to begin i started off writing some code
        //that I hope will make sure the test work. The idea is to let nodeTested
        //being the node that is tested and nextNode to go through all the targets
        //in all the connections and then return true/false depending on circularity or not.
        //However I'm not certain what will happen if a node have two or more connections, and how
        //to make it work once i > 0, since i = 1 and j = 0 should return false..



        return true;
    }

    private static boolean isLoop(int connectionIndex)
    {
        return false;
        //for (int n = 0; n < )
    }


}
