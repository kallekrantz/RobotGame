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
 * Node that constantly outputs the value given to the majority of its inputs.
 * @see Node
 */
public class MajorityNode extends Node
{
    /*
    Members of parent class Node to be defined in constructor:
    _maxInputs = ?;
    _connectionToInput = new int[_maxInputs];  //If _maxInputs > 0
    _category = NodeCategory.?;
    _type = NodeType.?;
    _ownerIndex = ownerIndex;
    */

    public MajorityNode(int ownerIndex)
    {
        _maxInputs = 9;
        _connectionToInput = new int[_maxInputs];  //If _maxInputs > 0
        _category = NodeCategory.Logic;
        _type = NodeType.Majority;
        _ownerIndex = ownerIndex;
    }

    @Override
    public void Update(MatchContext context, LinkedList<NodeAction> actions,  boolean[] input)
    {
        if (input == null) _output = false;
        else
        {
            int sum = 0;
            for (int n = 0; n < _numInput; n++)
                if (input[n]) sum++;
            _output = sum > _numInput / 2;
        }
        _isUpdated = true;
    }
}
