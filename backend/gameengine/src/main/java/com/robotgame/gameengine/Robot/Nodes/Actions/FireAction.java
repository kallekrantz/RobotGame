package com.robotgame.gameengine.Robot.Nodes.Actions;

import com.robotgame.gameengine.Projectile.ProjectileSystem;
import com.robotgame.gameengine.Projectile.ProjectileType;
import com.robotgame.gameengine.Robot.Nodes.NodeAction;
import com.robotgame.gameengine.Robot.Robot;
import com.robotgame.gameengine.Robot.RobotState;
import com.robotgame.gameengine.Util.Vector2;

/**
 * Created with IntelliJ IDEA.
 * User: Oskar
 * Date: 2013-10-25
 * Time: 22:44
 * To change this template use File | Settings | File Templates.
 */

/**
 * Adds an impulse to the robot.
 * @see com.robotgame.gameengine.Robot.Nodes.NodeAction
 */
public class FireAction extends NodeAction
{
    private int _source, _weaponId;

    /**
     * Fires a weapon.
     * @param source Index of the firing robot
     * @param weaponId Index of the firing weapon. Min: 0, Max: 2
     */
    public FireAction(int source, int weaponId)
    {
        _source = source;
        _weaponId = weaponId < 3 && weaponId >= 0 ? weaponId : 0;
    }



    public void PerformAction(Robot robot, ProjectileSystem projectileSystem)
    {
        RobotState state = robot.GetCurrentState();
        robot.GetCurrentState().fire = true;
        projectileSystem.CreateProjectile(ProjectileType.Potato, Vector2.Add(state.pos, Vector2.VectorFromPolarValues(0.4f, state.rot)) , Vector2.VectorFromPolarValues(8, state.rot), 0.4f, 0.2f, robot.GetIndex());
    }


}
