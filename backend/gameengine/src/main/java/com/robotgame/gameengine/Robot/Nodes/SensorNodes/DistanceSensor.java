package com.robotgame.gameengine.Robot.Nodes.SensorNodes;


import com.robotgame.gameengine.Robot.MatchContext;
import com.robotgame.gameengine.Robot.Nodes.Node;
import com.robotgame.gameengine.Robot.Nodes.NodeAction;
import com.robotgame.gameengine.Robot.Nodes.NodeCategory;
import com.robotgame.gameengine.Robot.Nodes.NodeType;
import com.robotgame.gameengine.Util.Vector2;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: Oskar
 * Date: 2013-10-14
 * Time: 12:13
 * To change this template use File | Settings | File Templates.
 */

//Detects whether any other robots are found between distances minDist and maxDist
public class DistanceSensor extends Node
{
    /*
    Members of parent class Node to be defined in constructor:
    _maxInputs = ?;
    _connectionToInput = new int[_maxInputs];  //If _maxInputs > 0
    _category = NodeCategory.?;
    _type = NodeType.?;
    _ownerIndex = ownerIndex;
    */

    private float _minDist, _maxDist;

    public DistanceSensor(int ownerIndex, float maxDist)
    {
        _maxInputs = 0;
        _category = NodeCategory.Sensor;
        _type = NodeType.DistanceSensor;
        _ownerIndex = ownerIndex;

        _minDist = 0;
        _maxDist = maxDist;
    }

    @Override
    public void Update(MatchContext context, LinkedList<NodeAction> actions,  boolean[] input)
    {
        if (context == null) _output = false;
        else
        {
            Vector2 myPos = context.robotStates[_ownerIndex].pos;
            _output = false;
            for (int n = 0; n < context.GetNumRobots(); n++)
            {
                if (n != _ownerIndex) //Only check distance to other robots, not to the owner.
                    if (myPos.DistanceToIsBetween(context.robotStates[n].pos, _minDist, _maxDist)) //Use Vector2.DistanceToIsBetween to check if robot n is within the specified distance.
                        _output = true;
            }

        }
        _isUpdated = true;
    }
}
