package com.robotgame.gameengine.Match;

import com.robotgame.gameengine.Network.MatchState;

/**
 * Created with IntelliJ IDEA.
 * User: Oskar
 * Date: 2013-10-30
 * Time: 13:02
 * To change this template use File | Settings | File Templates.
 */
public interface IMatchHandler
{
    public void MatchEnded(MatchResult results);
    public void SendMatchState(MatchState matchState);

}
