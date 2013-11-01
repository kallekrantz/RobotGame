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
public class NodeProperties
{
    private NodeType _type;
    private int _propertyValue1, _propertyValue2;
    private int _id;
    private Vector2 pos;

    public NodeProperties(NodeType type, int value1, int value2, int id)
    {
        _type = type;
        _propertyValue1 = value1;
        _propertyValue2 = value2;
        _id = id;
    }


    public NodeType get_type() {
        return _type;
    }

    public int get_propertyValue1() {
        return _propertyValue1;
    }

    public int get_propertyValue2() {
        return _propertyValue2;
    }

    public int get_id() {
        return _id;
    }
}
