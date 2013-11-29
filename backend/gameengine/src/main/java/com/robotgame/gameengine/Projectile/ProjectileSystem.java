package com.robotgame.gameengine.Projectile;

import com.robotgame.gameengine.Robot.Robot;
import com.robotgame.gameengine.Util.Vector2;

/**
 * Created with IntelliJ IDEA.
 * User: Oskar
 * Date: 2013-10-30
 * Time: 14:32
 * To change this template use File | Settings | File Templates.
 */
public class ProjectileSystem
{
    private int _maxProjectiles;
    private Projectile[] _projectiles;
    private int _nextProjectileIndex;

    public ProjectileSystem(int maxProjectiles)
    {
        _maxProjectiles = maxProjectiles;
        _projectiles = new Projectile[_maxProjectiles];
        _nextProjectileIndex = 0;
    }

    public void CreateProjectile(ProjectileType type, Vector2 startPos, Vector2 startVel, float height, float verticalVel, int creatorId)
    {
        for (int n = _nextProjectileIndex; n != (_nextProjectileIndex + _maxProjectiles - 1) % _maxProjectiles; n = (n+1) % _maxProjectiles )
            if (!_projectiles[n].alive)
            {
                switch (type)
                {
                    case Potato:
                        _projectiles[n] = new Potato(startPos, startVel, height, verticalVel, creatorId);

                }

            }
    }

    public void Update(Robot[] robots, int numRobots)
    {
        for (int n = 0; n < _maxProjectiles; n++)
            if (_projectiles[n].alive)
                _projectiles[n].Update(robots, numRobots);
    }


}
