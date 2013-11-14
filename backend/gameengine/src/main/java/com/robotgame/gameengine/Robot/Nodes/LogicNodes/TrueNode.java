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
public class TrueNode extends Node
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

    public TrueNode(int ownerIndex)
    {
        _isUpdated = false;
        _numInput = 0;
        _numOutput = 1;
        _output = new boolean[_numOutput];
        _category = NodeCategory.Logic;
        _type = NodeType.True;
        _output[0] = true;
        _ownerIndex = ownerIndex;
    }

    @Override
    public void Update(MatchContext context, LinkedList<NodeAction> actions,  boolean[] input)
    {
        _isUpdated = true;
    }
}
