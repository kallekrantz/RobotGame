package com.robotgame.gameengine.Network;

import com.robotgame.gameengine.Robot.RobotState;

/**
 * Created with IntelliJ IDEA.
 * User: Oskar
 * Date: 2013-11-04
 * Time: 09:15
 * To change this template use File | Settings | File Templates.
 */
public class MatchState
{
    public int matchId;
    public int numRobots;
    public RobotState[] robotStates;

    public MatchState(int matchId, int numRobots)
    {
        this.matchId = matchId;
        this.numRobots = numRobots;
        robotStates = new RobotState[numRobots];
    }
}
