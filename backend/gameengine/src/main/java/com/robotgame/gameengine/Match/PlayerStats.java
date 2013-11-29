package com.robotgame.gameengine.Match;

/**
 * Created with IntelliJ IDEA.
 * User: Oskar
 * Date: 2013-10-30
 * Time: 12:21
 * To change this template use File | Settings | File Templates.
 */

/**
 * Statistics about the performance of a players robot in a game.
 * Not Implemented. Not yet used by Match.
 * @see  Match
 * @see  MatchResult
 */
public class PlayerStats
{
    public boolean isWinner;
    public float distanceTravelled;
    public int shotsFired, shotsHit, meleeAttacks;
    public float damageDealt, damageTaken;

    public PlayerStats()
    {

    }
}
