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

//OBSOLETE
public class And3Node extends Node
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

    public And3Node(int ownerIndex)
    {
        _isUpdated = false;
        _numInput = 3;
        _numOutput = 1;

        _connectionToInput = new int[_numInput];
        _category = NodeCategory.Logic;
        _type = NodeType.And;

        _ownerIndex = ownerIndex;
    }

    @Override
    public void Update(MatchContext context, LinkedList<NodeAction> actions, boolean[] input) {
        if (input == null) _output = false;
        else _output = input[0] && input[1] && input[2];
        _isUpdated = true;
    }
}
