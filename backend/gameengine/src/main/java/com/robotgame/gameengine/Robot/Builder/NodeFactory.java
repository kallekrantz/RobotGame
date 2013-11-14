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
            case DistanceSensor:
                return new DistanceSensor(properties.get_propertyValue1(), properties.get_propertyValue2(), ownerIndex);


            //Logic Nodes
            case And:
                return new AndNode(ownerIndex);

            case And3:
                return new And3Node(ownerIndex);

            case Or:
                return new OrNode(ownerIndex);

            case Or3:
                return new Or3Node(ownerIndex);

            case True:
                return new TrueNode(ownerIndex);

            case False:
                return new FalseNode(ownerIndex);

            case Not:
                return new NotNode(ownerIndex);

            case Delay:
                return new DelayNode(ownerIndex, properties.get_propertyValue1());

            case Default:
                return new DefaultNode(ownerIndex);

            case Clock:
                return new ClockNode(ownerIndex, properties.get_propertyValue1());

            case TicTac:
                return new TicTacNode(ownerIndex, properties.get_propertyValue1());

            case Majority:
                return new MajorityOf3Node(ownerIndex);


            //Action nodes
            case Debug:
                return new DebugNode(ownerIndex);

            case MoveForward:
                return new MovementNode(ownerIndex);



            //Passive nodes

        }

        return null;
    }
}
