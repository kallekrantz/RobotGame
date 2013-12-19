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
import static org.junit.Assert.assertNotNull;
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
        String json = "{\"nodes\":[{\"nodeType\":\"And\",\"x\":\"573.203125px\",\"y\":\"283px\",\"id\":\"1\",\"maxInputs\":\"-1\",\"maxOutputs\":\"1\",\"val\":0,\"valLabel\":\"\",\"className\":\"logicNode\"},{\"nodeType\":\"True\",\"x\":\"474.203125px\",\"y\":\"72px\",\"id\":\"2\",\"maxInputs\":\"0\",\"maxOutputs\":\"1\",\"val\":0,\"valLabel\":\"\",\"className\":\"logicNode\"},{\"nodeType\":\"Clock\",\"x\":\"695.203125px\",\"y\":\"84px\",\"id\":\"3\",\"maxInputs\":\"1\",\"maxOutputs\":\"-1\",\"val\":\"0\",\"valLabel\":\"Time\",\"className\":\"logicNode\"},{\"nodeType\":\"TicTac\",\"x\":\"588.203125px\",\"y\":\"164px\",\"id\":\"4\",\"maxInputs\":\"1\",\"maxOutputs\":\"-1\",\"val\":\"0\",\"valLabel\":\"Time\",\"className\":\"logicNode\"},{\"nodeType\":\"Delay\",\"x\":\"464.203125px\",\"y\":\"168px\",\"id\":\"5\",\"maxInputs\":\"1\",\"maxOutputs\":\"-1\",\"val\":\"0\",\"valLabel\":\"Time\",\"className\":\"logicNode\"}],\"connections\":[{\"sourceId\":\"2\",\"targetId\":\"5\"},{\"sourceId\":\"5\",\"targetId\":\"1\"}],\"components\":[\"Chassi2\",\"Wheels\",\"Laser\",null,null]}";
        Gson gson = new Gson();
        blueprints.add(gson.fromJson(json, RobotBlueprint.class));


        System.out.println(gson.toJson(blueprint));

        rt.match.BuildRobots(blueprints);
        rt.match.SetMatchLength(3);


        rt.match.SetRunning(true);
        rt.match.run();
    }



    @Test
    public void InteractiveTest()
    {


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
    public void TestRobotFactoryIdentifyIllegal()
    {
        RobotBlueprint illegalBlueprint = new RobotBlueprint(3, 3);

        illegalBlueprint.AddNode(NodeType.Or, 0, 0);
        illegalBlueprint.AddNode(NodeType.Or, 0, 1);
        illegalBlueprint.AddNode(NodeType.Or, 0, 2);

        //From node, From channel, index
        illegalBlueprint.AddConnection(0, 1, 0);
        illegalBlueprint.AddConnection(1, 2, 1);
        illegalBlueprint.AddConnection(2, 0, 2);


        Robot robot = RobotFactory.CreateRobot(illegalBlueprint, 0);
        assertNull(robot);



        //Todo: Skriv test för indexpackaren.
    }

    @Test
    public void TestRobotFactoryPassLegal()
    {
        RobotBlueprint legalBlueprint = new RobotBlueprint(3, 2);

        legalBlueprint.AddNode(NodeType.Or, 0, 0);
        legalBlueprint.AddNode(NodeType.Or, 0, 1);
        legalBlueprint.AddNode(NodeType.Or, 0, 2);

        //From node, From channel, index
        legalBlueprint.AddConnection(0, 1, 0);
        legalBlueprint.AddConnection(1, 2, 1);

        Robot robot = RobotFactory.CreateRobot(legalBlueprint, 0);
        assertNotNull(robot);

        robot.UpdateNodes(null);

        //Todo: Skriv test för indexpackaren.
    }

    //@Test
    public void TestCollisions()
    {
        Match match = new Match(this, 2);
        match.SetMatchLength(1);
        match.BuildRobots(RobotBlueprint.GetDummyBlueprintVector());



    }
}