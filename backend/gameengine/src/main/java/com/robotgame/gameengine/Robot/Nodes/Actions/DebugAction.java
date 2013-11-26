package com.robotgame.gameengine.Robot.Nodes.Actions;

import com.robotgame.gameengine.Robot.Nodes.NodeAction;
import com.robotgame.gameengine.Robot.Robot;

/**
 * Created with IntelliJ IDEA.
 * User: Oskar
 * Date: 2013-10-25
 * Time: 22:44
 * To change this template use File | Settings | File Templates.
 */

/**
 * Used for testing only.
 * This action prints a message string using System.out.println().
 * @see com.robotgame.gameengine.Robot.Nodes.ActionNodes.DebugNode
 * @see NodeAction
 */
public class DebugAction extends NodeAction
{
    private String _message;

    public DebugAction(String message)
    {
        _message = message;
    }



    public void PerformAction(Robot robot)
    {
        System.out.println(_message);
    }


}
