package com.robotgame.gameengine.Robot.Nodes.ActionNodes;

import com.robotgame.gameengine.Robot.MatchContext;
import com.robotgame.gameengine.Robot.Nodes.Actions.DebugAction;
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
public class DebugNode extends Node
{
    /*
    protected boolean _isUpdated;
    protected boolean[] _output;
    protected int _numOutput;
    protected int[] _connectionToInput;
    protected int _numInput;
    protected NodeCategory _category;
    protected NodeType _type;
    */

    public DebugNode(int ownerIndex)
    {
        _isUpdated = false;
        _numInput = 1;
        _numOutput = 0;
        _output = new boolean[_numOutput];
        _connectionToInput = new int[_numInput];
        _category = NodeCategory.Logic;
        _type = NodeType.True;
        _ownerIndex = ownerIndex;

    }


    public void Update(MatchContext context, LinkedList<NodeAction> actions,  boolean[] input)
    {
        String out = String.valueOf(input[0]);
        actions.add(new DebugAction("Value of connection " + _connectionToInput[0] + ": " + out));
        _isUpdated = true;
    }
}
