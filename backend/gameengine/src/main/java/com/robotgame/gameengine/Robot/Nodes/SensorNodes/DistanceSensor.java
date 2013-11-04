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
    protected boolean _isUpdated;
    protected boolean[] _output;
    protected int _numOutput;
    protected int[] _connectionToInput;
    protected int _numInput;
    protected NodeCategory _category;
    protected NodeType _type;
    protected int _ownerIndex;
    */

    private float _minDist, _maxDist;

    public DistanceSensor(int ownerIndex, float minDist, float maxDist)
    {
        _isUpdated = false;
        _numInput = 0;
        _numOutput = 1;
        _output = new boolean[_numOutput];
        _connectionToInput = new int[_numInput];
        _category = NodeCategory.Sensor;
        _type = NodeType.S_DistanceSensor;

        _ownerIndex = ownerIndex;
        _minDist = minDist;
        _maxDist = maxDist;
    }

    @Override
    public void Update(MatchContext context, LinkedList<NodeAction> actions,  boolean[] input)
    {
        if (context == null) _output[0] = false;
        else
        {
            Vector2 myPos = context.robotStates[_ownerIndex].pos;
            _output[0] = false;
            for (int n = 0; n < context.GetNumRobots(); n++)
            {
                if (n != _ownerIndex) //Only check distance to other robots, not to the owner.
                    if (myPos.DistanceToIsBetween(context.robotStates[n].pos, _minDist, _maxDist)) //Use Vector2.DistanceToIsBetween to check if robot n is within the specified distance.
                        _output[0] = true;
            }

        }
        _isUpdated = true;
    }
}
