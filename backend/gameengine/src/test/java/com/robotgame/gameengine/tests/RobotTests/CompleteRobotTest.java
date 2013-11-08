package com.robotgame.gameengine.tests.RobotTests;

import com.google.gson.Gson;
import com.robotgame.gameengine.Match.IMatchHandler;
import com.robotgame.gameengine.Match.Match;
import com.robotgame.gameengine.Match.MatchResult;
import com.robotgame.gameengine.Network.MatchState;
import com.robotgame.gameengine.Network.NetworkMockup;
import com.robotgame.gameengine.Robot.Builder.RobotBlueprint;
import com.robotgame.gameengine.Robot.Nodes.NodeConnection;
import org.junit.Test;

import java.util.Vector;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: KarlJohan
 * Date: 10/31/13
 * Time: 3:57 PM
 * To change this template use File | Settings | File Templates.
 */

public class CompleteRobotTest implements IMatchHandler
{
    Match match;

    public CompleteRobotTest()
    {
        match = new Match(this, 1);
    }

    @Test
    public void RobotComplete()
    {
        CompleteRobotTest rt = new CompleteRobotTest();

        RobotBlueprint blueprint = new RobotBlueprint();
        RobotBlueprint blueprint2 = new RobotBlueprint();
        //Todo: Fill blueprint with nodes

        /***** Implemented Nodes ********
         //Sensor nodes
         S_DistanceSensor,
         //Logic nodes
         L_And, L_Or, L_Not, L_And3, L_Or3, L_Delay, L_True, L_False, L_Default, L_TicTac, L_Clock, L_MajorityOf3,
         //Action nodes
         A_Debug, A_Movement
         //Default node
         Default
         */

        blueprint.AddNode("L_Clock", 2, 0, 0);                 blueprint.AddNode("L_TicTac", 7, 0, 1);
//                            |                                /
        blueprint.AddNode("L_Delay", 30, 0, 2);//             /
//                            |                              /
                            blueprint.AddNode("L_Or", 0, 0, 3);
//                            |
                            blueprint.AddNode("A_Debug", 0, 0, 4);
                                             //From node, From channel
        blueprint.AddConnection(new NodeConnection(0, 0, 2, 0));
        blueprint.AddConnection(new NodeConnection(2, 0, 3, 0));
        blueprint.AddConnection(new NodeConnection(1, 0, 3, 1));
        blueprint.AddConnection(new NodeConnection(3, 0, 4, 0));






        Vector<RobotBlueprint> blueprints = new Vector<RobotBlueprint>();
        blueprints.add(blueprint);
        blueprints.add(blueprint2);
        Gson gson = new Gson();
        System.out.println(gson.toJson(blueprint));

        rt.match.BuildRobots(blueprints);



        rt.match.SetRunning(true);
        rt.match.run();
    }


    @Override
    public void MatchEnded(MatchResult results)
    {
        assertThat(results.winningTeam, is(2));
        assertThat(results.winningTeam, not(is(1)));
    }

    public void SendMatchState(MatchState matchState)
    {

    }
}