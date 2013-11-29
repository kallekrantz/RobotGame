package com.robotgame.gameengine.Network;

/**
 * Created with IntelliJ IDEA.
 * User: Oskar
 * Date: 2013-11-04
 * Time: 09:14
 * To change this template use File | Settings | File Templates.
 */


public interface NetworkInterface
{
    public void SendMatchState(MatchState matchState);
    public boolean GetInputA(int playerNumber);
    public boolean GetInputB(int playerNumber);
}
