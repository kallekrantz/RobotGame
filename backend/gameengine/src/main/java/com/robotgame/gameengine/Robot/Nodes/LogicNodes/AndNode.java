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

//GetInputA default node, mostly for testing. It's a logic node that only passes on the single input channel to the output.
public class AndNode extends Node
{
    /*
    To be defined in constructor:
    _maxInputs = 0;
    _connectionToInput = new int[_maxInputs];
    _category = NodeCategory.Logic;
    _type = NodeType.And;
     _ownerIndex = ownerIndex;
    */

    public AndNode(int ownerIndex)
    {
        _maxInputs = 5;
        _connectionToInput = new int[_maxInputs];
        _category = NodeCategory.Logic;
        _type = NodeType.And;
        _ownerIndex = ownerIndex;
    }

    @Override
    public void Update(MatchContext context, LinkedList<NodeAction> actions,  boolean[] input)
    {
        if (input == null || _numInput == 0) _output = false;
        else
        {
            _output = true;
            for (int n = 0; n < _numInput; n++)
                _output = input[n] && _output;
        }
        _isUpdated = true;
    }
}
