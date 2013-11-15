package com.robotgame.gameengine.Robot.Builder;

/**
 * Created with IntelliJ IDEA.
 * User: Oskar Krantz
 * Date: 2013-10-10
 * Time: 08:15
 * To change this template use File | Settings | File Templates.
 */

import com.robotgame.gameengine.Robot.Nodes.*;
import java.util.*;
import com.google.gson.Gson;

public class RobotBlueprint
{

    public NodeData[] nodes;
    public ConnectionData[] connections;
    public String[] components;
    //private int _numNodes;


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


    //This method inspects the list of nodes and connections and returns whether all looks god or not.
    public boolean CheckIfLegal()
    {
        int numNodes = nodes.length;

        for (NodeData n : nodes)
        {
            //if (n.get_id() < 0 || n.get_id() >= numNodes) return false;

        }

        //Todo: Check for circular dependencies and illegal connections.



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

}
