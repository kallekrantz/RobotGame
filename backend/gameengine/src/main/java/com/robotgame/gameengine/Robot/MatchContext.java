package com.robotgame.gameengine.Robot;

/**
 * Created with IntelliJ IDEA.
 * User: Oskar
 * Date: 2013-10-14
 * Time: 13:32
 * To change this template use File | Settings | File Templates.
 */


public class MatchContext
{
    public RobotState[] robotStates;
    private int numRobots;
    public float eastArenaBoundary, westArenaBoundary, northArenaBoundary, southArenaBoundary;

    public MatchContext(int numRobots)
    {
        this.numRobots = numRobots;
        robotStates = new RobotState[numRobots];
    }


    public int GetNumRobots()
    {
        return numRobots;
    }
}
