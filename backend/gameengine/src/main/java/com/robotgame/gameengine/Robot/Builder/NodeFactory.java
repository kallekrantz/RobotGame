package com.robotgame.gameengine.Robot.Builder;

//import Robot.Nodes.PassiveNodes.*;

import com.robotgame.gameengine.Robot.Nodes.LogicNodes.*;
import com.robotgame.gameengine.Robot.Nodes.ActionNodes.*;
import com.robotgame.gameengine.Robot.Nodes.*;
import com.robotgame.gameengine.Robot.Nodes.NodeProperties;
import com.robotgame.gameengine.Robot.Nodes.SensorNodes.DistanceSensor;

/**
 * Created with IntelliJ IDEA.
 * User: Oskar
 * Date: 2013-10-14
 * Time: 10:56
 * To change this template use File | Settings | File Templates.
 */



public class NodeFactory
{

    public static Node Create(NodeProperties properties, int ownerIndex)
    {
        switch (properties.get_type())
        {

            //Sensor nodes
            case S_DistanceSensor:
                return new DistanceSensor(properties.get_propertyValue1(), properties.get_propertyValue2(), ownerIndex);


            //Logic Nodes
            case L_And:
                return new AndNode(ownerIndex);

            case L_And3:
                return new And3Node(ownerIndex);

            case L_Or:
                return new OrNode(ownerIndex);

            case L_Or3:
                return new Or3Node(ownerIndex);

            case L_True:
                return new TrueNode(ownerIndex);

            case L_False:
                return new FalseNode(ownerIndex);

            case L_Not:
                return new NotNode(ownerIndex);

            case L_Delay:
                return new DelayNode(ownerIndex, properties.get_propertyValue1());

            case L_Default:
                return new DefaultNode(ownerIndex);

            case L_Clock:
                return new ClockNode(ownerIndex, properties.get_propertyValue1());

            case L_TicTac:
                return new TicTacNode(ownerIndex, properties.get_propertyValue1());

            case L_MajorityOf3:
                return new MajorityOf3Node(ownerIndex);


            //Action nodes
            case A_Debug:
                return new DebugNode(ownerIndex);

            case A_Movement:
                return new MovementNode(ownerIndex);



            //Passive nodes

        }

        return null;
    }
}
