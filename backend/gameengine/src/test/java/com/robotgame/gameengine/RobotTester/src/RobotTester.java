
import Robot.Builder.*;
import Robot.Nodes.NodeConnection;
import Robot.Robot;
import Match.*;

/**
 * Created with IntelliJ IDEA.
 * User: Oskar
 * Date: 2013-10-14
 * Time: 11:37
 * To change this template use File | Settings | File Templates.
**/

public class RobotTester implements IMatchHandler
{
    Match match;

    public RobotTester()
    {
        match = new Match(this);
    }

    public static void main(String[] args)
    {
        RobotTester rt = new RobotTester();

        RobotBlueprint blueprint = new RobotBlueprint();
        RobotBlueprint blueprint2 = new RobotBlueprint();
        //Todo: Fill blueprint with nodes

        /***** Implemented Nodes ********
        //Sensor nodes
        S_DistanceSensor,
        //Logic nodes
        L_And, L_Or, L_Not, L_And3, L_Or3, L_Delay, L_True, L_False, L_Default, L_TicTac, L_Clock, L_MajorityOf3,
        //Action nodes
        A_Debug,
        //Default node
        Default
        */

        blueprint.AddNode("L_Clock", 2, 0, 0);                 blueprint.AddNode("L_TicTac", 7, 0, 1);
//                            |                                /
        blueprint.AddNode("L_Delay", 30, 0, 2);//             /
//                            |                              /
                             blueprint.AddNode("L_Or", 0, 0, 3);
//                                             |
                             blueprint.AddNode("A_Debug", 0, 0, 4);

        blueprint.AddConnection(new NodeConnection(0, 0, 2, 0));
        blueprint.AddConnection(new NodeConnection(2, 0, 3, 0));
        blueprint.AddConnection(new NodeConnection(1, 0, 3, 1));
        blueprint.AddConnection(new NodeConnection(3, 0, 4, 0));






        RobotBlueprint[] blueprints = new RobotBlueprint[2];
        blueprints[0] = blueprint;
        blueprints[1] = blueprint2;


        rt.match.BuildRobots(blueprints);



        rt.match.SetRunning(true);
        new Thread(rt.match).start();
    }


    @Override
    public void MatchEnded(MatchResult results)
    {
        System.out.println();
        System.out.println("Match ended");
        System.out.println("Winner is " + results.winningTeam);


    }
}
