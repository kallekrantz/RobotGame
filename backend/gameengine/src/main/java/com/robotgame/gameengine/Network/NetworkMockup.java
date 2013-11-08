package com.robotgame.gameengine.Network;

/**
 * Created with IntelliJ IDEA.
 * User: Oskar
 * Date: 2013-11-04
 * Time: 09:20
 * To change this template use File | Settings | File Templates.
 */
public class NetworkMockup implements NetworkInterface
{
    public NetworkMockup()
    {

    }


    @Override
    public void SendMatchState(MatchState matchState)
    {
        System.out.println("Sending states to players in match " + matchState.matchId);
    }




    @Override
    public boolean GetInputA(int playerNumber)
    {
        return false;
    }

    @Override
    public boolean GetInputB(int playerNumber)
    {
        return false;
    }
}
