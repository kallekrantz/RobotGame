package com.robotgame.gameengine.Robot.Nodes;


import com.robotgame.gameengine.Robot.MatchContext;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: Oskar
 * Date: 2013-10-14
 * Time: 12:13
 * To change this template use File | Settings | File Templates.
 */

//A default node, mostly for testing. It's a logic node that only passes on the single input channel to the output.
public class DefaultNode extends Node
{

    public DefaultNode(int ownerIndex)
    {
        _isUpdated = false;
        _numInput = 1;
        _numOutput = 1;
        _output = new boolean[_numOutput];
        _connectionToInput = new int[_numInput];
        _category = NodeCategory.Logic;
        _type = NodeType.L_Default;
        _ownerIndex = ownerIndex;
    }

    @Override
    public void Update(MatchContext context, LinkedList<NodeAction> actions,  boolean[] input)
    {
        if (input == null) _output[0] = false;
        else _output[0] = input[0];
        _isUpdated = true;
    }
}
