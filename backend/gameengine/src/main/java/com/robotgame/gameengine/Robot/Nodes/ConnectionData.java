package com.robotgame.gameengine.Robot.Nodes;

/**
 * Created with IntelliJ IDEA.
 * User: Oskar
 * Date: 2013-11-14
 * Time: 17:34
 * To change this template use File | Settings | File Templates.
 */

/**
 * Represents a connection between two nodes. Matches variables used in frontend GUI.
 */
public class ConnectionData
{
    public int sourceId, targetId;

    public ConnectionData(int source, int target)
    {
        sourceId = source;
        targetId = target;
    }
}
