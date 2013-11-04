package com.robotgame.gameengine.Robot.Nodes;

import com.robotgame.gameengine.Robot.MatchContext;
import com.robotgame.gameengine.Robot.Robot.*;
import java.util.*;

/**
 * @(#)Robot.Nodes.Node.java
 *
 *
 * @author Oskar Krantz
 * @version 1.00 2013/10/10
 */


public abstract class Node
{
    protected boolean _isUpdated;
    protected boolean[] _output;
    protected int _numOutput;
    protected int[] _connectionToInput;
    protected int _numInput;
    protected NodeCategory _category;
    protected NodeType _type;
    protected int _ownerIndex;

    public Node()
    {

    }


    public void Reset()
    {
        _isUpdated = false;
    }

    public boolean IsUpdated()
    {
        return _isUpdated;
    }

    public int GetNumInput()
    {
        return _numInput;
    }

    public void SetInput(int channel, int connection)
    {
        if (channel >= 0 && channel < _numInput)
            _connectionToInput[channel] = connection;
    }

    public int GetInputConnection(int channel)
    {
        if (channel >= 0 && channel < _numInput && _connectionToInput != null)
            return _connectionToInput[channel];
        else return -1;
    }

    public boolean GetOutput(int channel)
    {
        if (channel >= 0 && channel < _numOutput && _output != null)
            return _output[channel];
        else return false;
    }

    //This method is overridden by the sensor subclasses
    public abstract void Update(MatchContext context, LinkedList<NodeAction> actions, boolean[] input);

}