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

//Passes through the incoming signal with a delay defined in update steps
public class DelayNode extends Node
{
    /*
    Members of parent class Node to be defined in constructor:
    _maxInputs = ?;
    _connectionToInput = new int[_maxInputs];  //If _maxInputs > 0
    _category = NodeCategory.?;
    _type = NodeType.?;
    _ownerIndex = ownerIndex;
    */

    private int _timer;
    private boolean[] _delayBank;
    private int _delay;

    public DelayNode(int ownerIndex, int delay)
    {
        _maxInputs = 1;
        _connectionToInput = new int[_maxInputs];
        _category = NodeCategory.Logic;
        _type = NodeType.Delay;
        _ownerIndex = ownerIndex;

        _timer = 0;
        _delay = delay;
        _delayBank = new boolean[delay + 1];

    }

    @Override
    public void Update(MatchContext context, LinkedList<NodeAction> actions,  boolean[] input)
    {
        if (_isUpdated) return;

        if (input == null) _output = false;
        else
        {
            _delayBank[(_timer + _delay) % (_delay +1)] = input[0];
            _output = _delayBank[_timer];
            _timer++;
            _timer %= _delay + 1;
        }
        _isUpdated = true;
    }
}
