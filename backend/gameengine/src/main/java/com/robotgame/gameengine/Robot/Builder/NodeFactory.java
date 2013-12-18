package com.robotgame.gameengine.Robot.Builder;

//import Robot.Nodes.PassiveNodes.*;

import com.robotgame.gameengine.Robot.Nodes.LogicNodes.*;
import com.robotgame.gameengine.Robot.Nodes.ActionNodes.*;
import com.robotgame.gameengine.Robot.Nodes.*;
import com.robotgame.gameengine.Robot.Nodes.NodeProperties;
import com.robotgame.gameengine.Robot.Nodes.SensorNodes.DistanceSensor;
import com.robotgame.gameengine.Robot.Nodes.SensorNodes.SectorSensor;

/**
 * Created with IntelliJ IDEA.
 * User: Oskar
 * Date: 2013-10-14
 * Time: 10:56
 * To change this template use File | Settings | File Templates.
 */


/**
 * Helper class for creating Node objects
 */
public class NodeFactory
{
    /**
     * A factory method for creating Node objects och various sub classes
     * @param properties The description of the node to be created
     * @param ownerIndex The index of the parent Robot
     * @return A node as described by properties
     * @see Node
     * @see NodeData
     *
     */
    public static Node Create(NodeData properties, int ownerIndex)
    {
        NodeType type;
        try { type = NodeType.valueOf(properties.nodeType); }
        catch (Exception e)
        {
            type = NodeType.Default;
        }


        switch (type)
        {

            //Sensor nodes
            case DistanceSensor:
                return new DistanceSensor(ownerIndex, properties.val);

            case SectorSensor:
                return new SectorSensor(ownerIndex, properties.val);


            //Logic Nodes
            case And:
                return new AndNode(ownerIndex);

            case Or:
                return new OrNode(ownerIndex);

            case True:
                return new TrueNode(ownerIndex);

            case False:
                return new FalseNode(ownerIndex);

            case Not:
                return new NotNode(ownerIndex);

            case Delay:
                return new DelayNode(ownerIndex, properties.val);

            case Default:
                return new DefaultNode(ownerIndex);

            case Clock:
                return new ClockNode(ownerIndex, properties.val);

            case TicTac:
                return new TicTacNode(ownerIndex, properties.val);

            case Majority:
                return new MajorityNode(ownerIndex);


            //Action nodes
            case Debug:
                return new DebugNode(ownerIndex);

            case MoveForward:
                return new MoveForwardNode(ownerIndex);

            case MoveBackwards:
                return new MoveBackwardsNode(ownerIndex);

            case TurnLeft:
                return new TurnLeftNode(ownerIndex);

            case TurnRight:
                return new TurnRightNode(ownerIndex);


            //Passive nodes

        }

        return null;
    }
}
