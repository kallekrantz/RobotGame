package com.robotgame.gameengine.Robot.Nodes.LogicNodes;


import com.robotgame.gameengine.Robot.MatchContext;
import com.robotgame.gameengine.Robot.Nodes.Node;
import com.robotgame.gameengine.Robot.Nodes.NodeAction;
import com.robotgame.gameengine.Robot.Nodes.NodeCategory;
import com.robotgame.gameengine.Robot.Nodes.NodeType;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: Oskar
 * Date: 2013-10-14
 * Time: 12:13
 * To change this template use File | Settings | File Templates.
 */

/**
 * Outputs a short periodic impulse with a set period.
 * @see Node
 */
public class ClockNode extends Node
{
    /*
    Members of parent class Node to be defined in constructor:
    _maxInputs = ?;
    _connectionToInput = new int[_maxInputs];
    _category = NodeCategory.?;
    _type = NodeType.?;
    _ownerIndex = ownerIndex;
    */
    private int _time;
    private int _period;

    /**
     * Creates a ClockNode
     * @param ownerIndex
     * @param period The approximate period in ms. Will not be very exact.
     */
    public ClockNode(int ownerIndex, int period)
    {
        _maxInputs = 0;
        _category = NodeCategory.Logic;
        _type = NodeType.Clock;
        _ownerIndex = ownerIndex;

        _time = 0;
        _period = period/33;
        if (_period <= 0) _period = 1;
    }

    @Override
    public void Update(MatchContext context, LinkedList<NodeAction> actions,  boolean[] input)
    {
        _time++;
        _time %= _period;
        if (_time == 0) _output = true;
        else _output = false;

        _isUpdated = true;
    }
}
