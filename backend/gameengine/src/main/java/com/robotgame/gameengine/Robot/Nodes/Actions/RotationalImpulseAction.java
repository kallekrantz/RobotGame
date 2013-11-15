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

public class RotationalImpulseAction extends NodeAction
{
    private float _forceMoment;


    public RotationalImpulseAction(float relativeForceMoment)
    {
        _forceMoment = relativeForceMoment;
    }



    public void PerformAction(Robot robot)
    {
        robot.GetCurrentState().w += _forceMoment / robot.GetMass() * robot.GetMaxTurn();
    }


}
