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
 * Controls the boost variable of a robot.
 * @see NodeAction
 */
public class SetBoostAction extends NodeAction
{
    private boolean _boost;

    /**
     * Sets the boost variable to a desired value.
     * @param boost If true the boost of the robot is activated and all movement impulses is amplified.
     */
    public SetBoostAction(boolean boost)
    {
        _boost = boost;
    }



    public void PerformAction(Robot robot)
    {
        robot.SetBoost(_boost);
    }


}
