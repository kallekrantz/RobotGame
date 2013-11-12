package com.robotgame.gameengine.Match;

import com.robotgame.gameengine.Network.MatchState;
import com.robotgame.gameengine.Network.NetworkInterface;
import com.robotgame.gameengine.Network.matchHandler;
import com.robotgame.gameengine.Robot.Builder.RobotBlueprint;
import com.robotgame.gameengine.Robot.Builder.RobotFactory;
import com.robotgame.gameengine.Robot.MatchContext;
import com.robotgame.gameengine.Robot.Nodes.NodeAction;
import com.robotgame.gameengine.Robot.Robot;


import java.util.LinkedList;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: Oskar
 * Date: 2013-10-14
 * Time: 12:34
 * To change this template use File | Settings | File Templates.
 */

//Represents one game. Is run by a IMatchHandler that provides a function to call when the match is over.
public class Match implements Runnable
{
    private Robot[] _robots;
    private int _numRobots;
    private boolean _running;
    private MatchContext _context;
    private int _clock;
    private IMatchHandler _matchHandler;
    private MatchResult _matchResult;
    private MatchState _matchState;
    private int _matchId;
    //private ProjectileSystem _projectileSystem;



    //Public methods


    public Match(IMatchHandler matchHandler, int matchId)
    {
        _matchId = matchId;
        _clock = 0;
        _numRobots = 0;
        _running = false;
        _matchHandler = matchHandler;
    }

    public boolean BuildRobots(Vector<RobotBlueprint> blueprints)
    {
        _numRobots = blueprints.size();
        _robots = new Robot[_numRobots];

        for (int n = 0; n < _numRobots; n++)
        {
            _robots[n] = RobotFactory.CreateRobot(blueprints.get(n), n);
            if (_robots[n] == null) return false;
        }

        _context = new MatchContext(_numRobots);
        _matchState = new MatchState(_matchId, _numRobots);
        _matchResult = new MatchResult(_numRobots);
        return true;
    }


    public void SetRunning(boolean v)
    {
        _running = v;
    }


    public void start()
    {
        _running = true;
    }




    public void run()
    {
        _running = true;
        while(_running)
        {
            Update();

            try { Thread.sleep(33); // Max 30 updates per sec
            } catch(InterruptedException e) {};
        }
    }




    //Private methods

    private void Update()
    {
        CreateMatchContext();

        //Uppdatera noder utifrån nuvarande context
        for (int n = 0; n < _numRobots; n++)
        {
            _robots[n].UpdateNodes(_context);

        }

        for (int n = 0; n < _numRobots; n++)
        {
            LinkedList<NodeAction> actions = _robots[n].GetActions();
            for (NodeAction a : actions)
                a.PerformAction(_robots[n]);

            //System.out.println("Hot connections: " + _robots[n].GetHotConnections());
        }

        for (int n = 0; n < _numRobots; n++)
        {
            _robots[n].UpdateState();
        }

        CreateMatchState();

        _matchHandler.SendMatchState(_matchState);

        if (_clock > 120)
        {
            _matchResult.winningTeam = 2;
            _running = false;
            _matchHandler.MatchEnded(_matchResult);
        }
        _clock++;

    }

    //Creates en MatchContext with all the info for the sensor nodes.
    private void CreateMatchContext()
    {
        for (int n = 0; n < _numRobots; n++)
        {
            _context.robotStates[n] = _robots[n].GetCurrentState();
            //_context.A[n] = _networkInterface.GetInputA(n);
            //_context.B[n] = _networkInterface.GetInputB(n);
        }


        //ToDo: Lägga till lista över projektiler?
    }

    //Creates a MatchState object that contains the information that is to be sent to the players
    private void CreateMatchState()
    {
        for (int n = 0; n < _numRobots; n++)
            _matchState.robotStates[n] = _robots[n].GetCurrentState();
    }

}
