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

public class RobotBlueprint
{

    private LinkedList<NodeProperties> _nodeList;
    private LinkedList<NodeConnection> _connectionList;
    //private int _numNodes;


    public     RobotBlueprint()
    {
        _nodeList = new LinkedList<NodeProperties>();
        _connectionList = new LinkedList<NodeConnection>();
        //_numNodes = 0;
    }

    public boolean AddNode(String type, int propertyValue1, int propertyValue2, int ID)
    {
        NodeType typeEnum;
        try { typeEnum = NodeType.valueOf(type); }
        catch (IllegalArgumentException e) { return false; }

        _nodeList.add(new NodeProperties(typeEnum, propertyValue1, propertyValue2, ID));
        return true;
    }

    public boolean AddConnection(NodeConnection connection)
    {
        _connectionList.add(connection);
        return true;
    }

    public int GetNumNodes()
    {
        return _nodeList.size();
    }

    public LinkedList<NodeProperties> GetNodeList()
    {
        return _nodeList;
    }

    public int GetNumConnections()
    {
        return _connectionList.size();
    }

    public LinkedList<NodeConnection> GetConnectionList()
    {
        return _connectionList;
    }

    //This method inspects the list of nodes and connections and returns whether all looks god or not.
    public boolean CheckIfLegal()
    {
        int numNodes = _nodeList.size();

        for (NodeProperties n : _nodeList)
        {
            if (n.get_id() < 0 || n.get_id() >= numNodes) return false;

        }

        //Todo: Check for circular dependencies and illegal connections.



        return true;
    }


}
