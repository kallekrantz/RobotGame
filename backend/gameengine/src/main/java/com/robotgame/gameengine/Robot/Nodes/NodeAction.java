package com.robotgame.gameengine.Robot.Nodes;

import com.robotgame.gameengine.Robot.Robot;

/**
 * Created with IntelliJ IDEA.
 * User: Oskar
 * Date: 2013-10-21
 * Time: 12:53
 * To change this template use File | Settings | File Templates.
 */

//Base class for actions performed
public abstract class NodeAction
{
    public NodeAction()
    {

    }


    public abstract void PerformAction(Robot robot);

}
