package com.robotgame.gameengine.Robot.Nodes.Actions;

import com.robotgame.gameengine.Projectile.ProjectileSystem;
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
 * Adds an impulse to the robot.
 * @see NodeAction
 */
public class ImpulseAction extends NodeAction
{
    private float _thrust, _relativeAngle;

    /**
     * Creates an impulse.
     * @param thrust Relative power of the impulse.
     *               Values between 0 and 1.
     *               1 represents the max power of the robot.
     * @param angle  The direction of the impulse.
     *               Defined in radians.
     *               0 is straight forward.
     */
    public ImpulseAction(float thrust, float angle)
    {
        _relativeAngle = angle;
        _thrust = thrust;
    }



    public void PerformAction(Robot robot, ProjectileSystem _projectileSystem)
    {
        robot.AddSpeed(_thrust / robot.GetMass() * robot.GetMaxThrust(), _relativeAngle);
    }


}
