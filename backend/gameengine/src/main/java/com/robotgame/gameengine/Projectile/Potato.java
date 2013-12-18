package com.robotgame.gameengine.Projectile;

import com.robotgame.gameengine.Match.Match;
import com.robotgame.gameengine.Robot.Robot;
import com.robotgame.gameengine.Util.Vector2;

/**
 * Created with IntelliJ IDEA.
 * User: Oskar
 * Date: 2013-11-29
 * Time: 12:34
 * To change this template use File | Settings | File Templates.
 */
public class Potato extends Projectile
{


    public Potato(Vector2 startPos, Vector2 startVel, float height, float verticalVel, int creatorId)
    {
        pos = startPos;
        vel = startVel;
        h = height;
        vVel = verticalVel;
        this.creatorId = creatorId;
        alive = true;
        type = ProjectileType.Potato;
    }

    @Override
    public void Update(Robot[] robots, int numRobots)
    {
        if (h < 0) alive = false;
        else
        {
            pos.Add(Vector2.Multiply(vel, Match.DT));
            vVel -= 0.5 * Match.DT;
            h += vVel * Match.DT;

            for (int n = 0; n < numRobots; n++)
                if (n != creatorId)
                    if (pos.DistanceToIsCloserThan(robots[n].GetCurrentState().pos, robots[n].GetRadius()))
                    {
                        robots[n].ApplyDamage(30);
                        alive = false;
                        break;
                    }
        }
    }
}
