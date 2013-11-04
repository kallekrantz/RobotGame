package com.robotgame.gameengine.Match;

import com.robotgame.gameengine.Match.PlayerStats;

/**
 * Created with IntelliJ IDEA.
 * User: Oskar
 * Date: 2013-10-30
 * Time: 12:31
 * To change this template use File | Settings | File Templates.
 */
public class MatchResult
{
    public PlayerStats[] playerStats;
    public int winningTeam;

    public MatchResult(int numRobots)
    {
        playerStats = new PlayerStats[numRobots];
    }
}
