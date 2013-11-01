package com.robotgame.gameengine.Match;

import com.robotgame.gameengine.Robot.Builder.RobotBlueprint;
import com.robotgame.gameengine.Robot.Builder.RobotFactory;
import com.robotgame.gameengine.Robot.MatchContext;
import com.robotgame.gameengine.Robot.Nodes.NodeAction;
import com.robotgame.gameengine.Robot.Robot;


import java.util.LinkedList;

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
    //private ProjectileSystem _projectileSystem;

    public Match(IMatchHandler matchHandler)
    {
        _clock = 0;
        _numRobots = 0;
        _running = false;
        _matchHandler = matchHandler;

    }

    public boolean BuildRobots(RobotBlueprint[] blueprints)
    {
        _numRobots = blueprints.length;
        _robots = new Robot[_numRobots];

        for (int n = 0; n < _numRobots; n++)
        {
            _robots[n] = RobotFactory.CreateRobot(blueprints[n], n);
            if (_robots[n] == null) return false;
        }

        _context = new MatchContext(_numRobots);
        _matchResult = new MatchResult(_numRobots);
        return true;
    }


    private void Update()
    {
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

        if (_clock > 120)
        {
            _matchResult.winningTeam = 2;
            _running = false;
            _matchHandler.MatchEnded(_matchResult);
        }
        _clock++;


    }

    public void SetRunning(boolean v)
    {
        _running = v;
    }

    public void run()
    {
        while(_running)
        {
            Update();



            try { Thread.sleep(33); // Max 30 updates per sec
            } catch(InterruptedException e) {};
        }
    }


}
