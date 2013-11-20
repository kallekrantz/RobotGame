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


/**
 * Abstract base class for all nodes.
 * @see com.robotgame.gameengine.Robot.Robot
 * @see NodeSystem
 */
public abstract class Node
{
    protected boolean _output;
    protected int _numInput;
    protected boolean _isUpdated;

    protected int _numOutput;    //Obsolete
    protected int[] _connectionToInput;
    protected int _maxInputs;
    protected NodeCategory _category;
    protected NodeType _type;
    protected int _ownerIndex;


    public Node()
    {
        _numInput = 0;
        _output = false;
        _isUpdated = false;
    }

    /**
     * This has to be called for every node in the beginning of every update cycle to reset the nodes before updating them.
     */
    public void Reset()
    {
        _isUpdated = false;
    }

    /**
     * Check if the node has been updated yet.
     * Used in NodeSystem.Update() and NodeSystem.GetOutputOfNode().
     * @return true if node has been updated this cycle.
     * @see NodeSystem
     */
    public boolean IsUpdated()
    {
        return _isUpdated;
    }

    /**
     * Gets the number of inputs of the node. Used by NodeSystem.GetOutputOfNode().
     * @return Number of connections connected to the node.
     */
    public int GetNumInput()
    {
        return _numInput;
    }

    public void SetInput(int channel, int connection) //Obsolete
    {
        if (channel >= 0 && channel < _numInput)
            _connectionToInput[channel] = connection;
    }


    /**
     * Adds an input to the node.
     * Used by RobotFactory.CreateRobot().
     * @param connection The index of the input connection.
     * @see com.robotgame.gameengine.Robot.Builder.RobotFactory
     */
    public void AddInput(int connection)
    {
        if (_numInput < _maxInputs)
            _connectionToInput[_numInput++] = connection;
    }


    /**
     * Gets the index of the connection to a specific channel
     * @param channel the channel number
     * @return the index of the input connection
     */
    public int GetInputConnection(int channel)
    {
        if (channel >= 0 && channel < _numInput && _connectionToInput != null)
            return _connectionToInput[channel];
        else return -1;
    }

    /**
     * Gets the output value of the node.
     * @return output value
     */
    public boolean GetOutput()
    {
        return _output;
    }

    /**
     * Must be implemented by all node sub classes! Part of the implementation must be to set _isUpdated = true.
     * Updates the output of the node using the relevant input (for logic and action nodes) and a MatchContext object (for sensor nodes).
     * If the node triggers any action (action nodes) a NodeAction object is added to actions.
     * Used in NodeSystem.Update() and NodeSystem.GetOutputOfNode().
     * @param context contains the position of all robots and various other info of possible use to sensor nodes.
     * @param actions a list of NodeAction objects ready to receive any actions triggered by action nodes.
     * @param input an array containing all input values to the node.
     * @see NodeSystem
     * @see NodeAction
     */
    public abstract void Update(MatchContext context, LinkedList<NodeAction> actions, boolean[] input);

}