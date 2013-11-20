package com.robotgame.gameengine.Robot.Nodes;

/**
 * @(#)Robot.Nodes.NodeConnection.java
 *
 *
 * @author
 * @version 1.00 2013/10/10
 */

/**
 * Class representing node connections in the game engine.
 */
public class NodeConnection
{
	private int _fromNode, _toNode;
    //private boolean _hot, _updated;


    public NodeConnection(int fromNode, int toNode)
    {
    	_fromNode = fromNode;
        //_fromNodeChannel = fromNodeChannel;
        _toNode = toNode;
        //_toNodeChannel = toNodeChannel;
    }


    public int FromNode()
    {
    	return _fromNode;
    }

    //public int FromNodeChannel()
    //{
    //	return _fromNodeChannel;
    //}

    public int ToNode()
    {
    	return _toNode;
    }

    //public int ToNodeChannel()
    //{
    //	return _toNodeChannel;
    //}
}