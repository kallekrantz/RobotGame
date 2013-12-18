package com.robotgame.gameengine.Robot.Nodes.ActionNodes;

import com.robotgame.gameengine.Robot.MatchContext;
import com.robotgame.gameengine.Robot.Nodes.Actions.FireAction;
import com.robotgame.gameengine.Robot.Nodes.Actions.SetBoostAction;
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
 * When activated this node amplifies the movement of the robot.
 * Uses the Action SetBoostAction.
 * This node accepts 1 input.
 * @see com.robotgame.gameengine.Robot.Nodes.Actions.SetBoostAction
 * @see com.robotgame.gameengine.Robot.Nodes.Node
 */
public class Fire1Node extends Node
{
    /*
    Members of parent class Node to be defined in constructor:
    _maxInputs = ?;
    _connectionToInput = new int[_maxInputs];  //If _maxInputs > 0
    _category = NodeCategory.?;
    _type = NodeType.?;
    _ownerIndex = ownerIndex;
    */

    private int _reloadTimer;

    public Fire1Node(int ownerIndex)
    {
        _maxInputs = 1;
        _connectionToInput = new int[_maxInputs];  //If _maxInputs > 0
        _category = NodeCategory.Action;
        _type = NodeType.FireWeapon1;
        _ownerIndex = ownerIndex;

        _reloadTimer = 0;
    }


    public void Update(MatchContext context, LinkedList<NodeAction> actions,  boolean[] input)
    {
        if (_reloadTimer > 0) _reloadTimer--;
        else if (input[0])
        {
            _reloadTimer = 30;
            actions.add(new FireAction(_ownerIndex, 1));
        }
        _isUpdated = true;
    }
}
