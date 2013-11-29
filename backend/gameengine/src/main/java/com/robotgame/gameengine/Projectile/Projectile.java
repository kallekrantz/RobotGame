package com.robotgame.gameengine.Projectile;

import com.robotgame.gameengine.Robot.Robot;
import com.robotgame.gameengine.Util.Vector2;

/**
 * Created with IntelliJ IDEA.
 * User: Oskar
 * Date: 2013-11-29
 * Time: 12:25
 * To change this template use File | Settings | File Templates.
 */
public class Projectile
{
    public Vector2 pos, vel;
    public float h, vVel;
    public int creatorId;
    boolean alive;

    public Projectile()
    {
        alive = false;
    }

    public void Update(Robot[] robots, int numRobots)
    {

    }

}
