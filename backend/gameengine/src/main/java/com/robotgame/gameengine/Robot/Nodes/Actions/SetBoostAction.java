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

public class SetBoostAction extends NodeAction
{
    private boolean _boost;

    public SetBoostAction(boolean boost)
    {
        _boost = boost;
    }



    public void PerformAction(Robot robot)
    {
        robot.SetBoost(_boost);
    }


}
