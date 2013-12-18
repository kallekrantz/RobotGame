package com.robotgame.gameengine.tests.RobotTests;

import com.google.gson.Gson;
import com.robotgame.gameengine.Match.*;
import com.robotgame.gameengine.Network.*;
import com.robotgame.gameengine.Robot.Builder.RobotBlueprint;
import com.robotgame.gameengine.Robot.Builder.RobotFactory;
import com.robotgame.gameengine.Robot.Nodes.NodeConnection;
import com.robotgame.gameengine.Robot.Nodes.NodeType;
import com.robotgame.gameengine.Robot.Robot;
import org.junit.Test;

import java.util.Vector;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertNull;
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

       // match = new Match(this, new NetworkMockup(), 0);

        match = new Match(this, 1);

    }

    @Test
    public void RobotComplete()
    {
        CompleteRobotTest rt = new CompleteRobotTest();

        RobotBlueprint blueprint = new RobotBlueprint(6, 4);

        //Todo: Fill blueprint with nodes


/*
        blueprint.AddNode(NodeType.Clock, 200, 0);                 blueprint.AddNode(NodeType.TicTac, 700, 1);
//                            |                                   /
          blueprint.AddNode(NodeType.Delay, 30, 2);//            /
//                               |                              /
                            blueprint.AddNode(NodeType.Or, 0, 3);
//                                          |
                            blueprint.AddNode(NodeType.Debug, 0, 4);
*/
        //Test robot that drives in circles, alternating left and right.
                            blueprint.AddNode(NodeType.TicTac, 2000, 0);                            blueprint.AddNode(NodeType.True, 0, 1);
//                                 |                           |                                             |
        blueprint.AddNode(NodeType.Not, 0, 2);//               |                                             |
//                                 |                           |                                             |
        blueprint.AddNode(NodeType.TurnRight, 0, 4);   blueprint.AddNode(NodeType.TurnLeft, 0, 3);  blueprint.AddNode(NodeType.MoveForward, 0, 5);


                   //From node, From channel, index
        blueprint.AddConnection(0, 2, 0);
        blueprint.AddConnection(0, 3, 1);
        blueprint.AddConnection(2, 4, 2);
        blueprint.AddConnection(1, 5, 3);






        Vector<RobotBlueprint> blueprints = new Vector<RobotBlueprint>();
        blueprints.add(blueprint);
        blueprints.add(blueprint);

        Gson gson = new Gson();
        System.out.println(gson.toJson(blueprint));

        rt.match.BuildRobots(blueprints);
        rt.match.SetMatchLength(3);


        rt.match.SetRunning(true);
        rt.match.run();
    }


    public void MatchEnded(MatchResult results)
    {
        assertThat(results.winningTeam, is(0));
        assertThat(results.winningTeam, not(is(1)));
    }

    public void SendMatchState(MatchState matchState)
    {

    }

	public void SendFirstMatchState(MatchState _matchState) {
		// TODO Auto-generated method stub
		
	}

    public boolean GetInputA(int playerNumber) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean GetInputB(int playerNumber) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }


    @Test
    public void TestRobotFactory()
    {
        RobotBlueprint blueprint = new RobotBlueprint(3, 3);

        blueprint.AddNode(NodeType.Or, 0, 0);
        blueprint.AddNode(NodeType.Or, 0, 1);
        blueprint.AddNode(NodeType.Or, 0, 2);

        //From node, From channel, index
        blueprint.AddConnection(0, 1, 0);
        blueprint.AddConnection(1, 2, 1);
        blueprint.AddConnection(2, 0, 2);


        Robot robot = RobotFactory.CreateRobot(blueprint, 0);
        assertNull(robot);


        //Todo: Skriv test för indexpackaren.
    }
}