package com.robotgame.gameengine.Robot.Nodes;


import com.robotgame.gameengine.Util.Vector2;

/**
 * Created with IntelliJ IDEA.
 * User: Oskar
 * Date: 2013-10-14
 * Time: 10:24
 * To change this template use File | Settings | File Templates.
 */


//Class that represents a single node in a Robot.Builder.RobotBlueprint.
public class NodeData
{
    public String className, nodeType, valueTitle;
    public double x, y;
    public int id, maxInputs, maxOutputs, val;


    public NodeData(NodeType type, int value, int id)
    {
        nodeType = type.name();
        val = value;
        this.id = id;
    }

}
