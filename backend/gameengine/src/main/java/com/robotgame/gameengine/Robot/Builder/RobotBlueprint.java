package com.robotgame.gameengine.Robot.Builder;

/**
 * Created with IntelliJ IDEA.
 * User: Oskar Krantz
 * Date: 2013-10-10
 * Time: 08:15
 * To change this template use File | Settings | File Templates.
 */

import com.robotgame.gameengine.Robot.Nodes.*;
import java.util.Vector;
import com.google.gson.Gson;

/**
 * Contains a complete specification of a robot.
 * Constructed using the JavaScript GUI.
 * Stored as json strings in database.
 * A vector<RobotBlueprint> should be provided to Match.BuildRobots() to create functional Robot objects.
 */
public class RobotBlueprint
{

    public NodeData[] nodes;
    public ConnectionData[] connections;
    public String[] components;
    //private int _numNodes;


    /**
     * Constructor is only used when creating blueprints for testing purposes.
     * Normally blueprints are created from json strings.
     * @param numNodes
     * @param numConnections
     */
    public RobotBlueprint(int numNodes, int numConnections)
    {
        components = new String[5];
        nodes = new NodeData[numNodes];
        connections = new ConnectionData[numConnections];

    }

    public boolean AddNode(NodeType type, int value, int ID)
    {
        nodes[ID] = new NodeData(type, value, ID);
        return true;
    }

    public boolean AddConnection(int from, int to, int index)
    {
        connections[index] = new ConnectionData(from, to);
        return true;
    }

    public int GetNumNodes()
    {
        return nodes.length;
    }

    public int GetNumConnections()
    {
        return connections.length;
    }


    /**
     * Not implemented!
     * @return True if the blueprint is a legit working robot.
     */
    public boolean CheckIfLegal()
    {
        int numNodes = nodes.length;

        for (NodeData n : nodes)
        {
            //if (n.get_id() < 0 || n.get_id() >= numNodes) return false;

        }

        //Todo: Check for circular dependencies and illegal connections.
        //Since I'm not sure where to begin i started off writing some code
        //that I hope will make sure the test work. The idea is to let nodeTested
        //being the node that is tested and nextNode to go through all the targets
        //in all the connections and then return true/false depending on circularity or not.
        //However I'm not certain what will happen if a node have two or more connections, and how
        //to make it work once i > 0, since i = 1 and j = 0 should return false..

        int nodeTested, nextNode;

        for(int i =0; i<connections.length; i++){

            nodeTested = connections[i].sourceId;

            for(int j=0; j<connections.length; j++)
            {
                nextNode = connections[j].targetId;

                if(nextNode == nodeTested)
                {
                    return false;
                }
            }


        }

        return true;
    }


    public static RobotBlueprint BlueprintFromJson(String json)
    {
        Gson gson = new Gson();
        return gson.fromJson(json, RobotBlueprint.class);
    }

    public String ToJson()
    {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static RobotBlueprint GetDummyBlueprint()
    {
        RobotBlueprint dummy = new RobotBlueprint(2, 1);

        dummy.AddNode(NodeType.True, 0, 0);
        dummy.AddNode(NodeType.TurnLeft, 0, 1);
        dummy.AddConnection(0, 1, 0);

        return dummy;
    }

    public static Vector<RobotBlueprint> GetDummyBlueprintVector()
    {
        Vector<RobotBlueprint> dummyVector = new Vector<RobotBlueprint>();
        dummyVector.add(GetDummyBlueprint());
        dummyVector.add(GetDummyBlueprint());
        return dummyVector;
    }

}
