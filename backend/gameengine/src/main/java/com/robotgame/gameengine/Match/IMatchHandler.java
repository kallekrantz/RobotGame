package com.robotgame.gameengine.Match;

import com.robotgame.gameengine.Network.MatchState;

/**
 * Created with IntelliJ IDEA.
 * User: Oskar
 * Date: 2013-10-30
 * Time: 13:02
 * To change this template use File | Settings | File Templates.
 */

/**
 * This interface provides ways for a match to communicate with a match handler.
 */
public interface IMatchHandler
{
    /**
     * This method is called when the match is ended.
     * @param results Contains information about the outcome of the game.
     */
    public void MatchEnded(MatchResult results);


    /**
     * Sends updated info to server.
     * Make sure matchState is up to date.
     * @param matchState contains states of all robots (and other objects
     */
    public void SendMatchState(MatchState matchState);


	public void SendFirstMatchState(MatchState _matchState);

    //Implement these in MatchHandler
    //public boolean GetInputA(int playerNumber);
    //public boolean GetInputB(int playerNumber);

}
