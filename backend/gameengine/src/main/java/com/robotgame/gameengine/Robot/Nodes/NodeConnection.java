package Robot.Nodes;

import Robot.Robot;

/**
 * @(#)Robot.Nodes.NodeConnection.java
 *
 *
 * @author
 * @version 1.00 2013/10/10
 */


public class NodeConnection
{
	private int _fromNode, _fromNodeChannel, _toNode, _toNodeChannel;
    //private boolean _hot, _updated;


    public NodeConnection(int fromNode, int fromNodeChannel, int toNode, int toNodeChannel)
    {
    	_fromNode = fromNode;
    	_fromNodeChannel = fromNodeChannel;
        _toNode = toNode;
        _toNodeChannel = toNodeChannel;
    }


    public int FromNode()
    {
    	return _fromNode;
    }

    public int FromNodeChannel()
    {
    	return _fromNodeChannel;
    }

    public int ToNode()
    {
    	return _toNode;
    }

    public int ToNodeChannel()
    {
    	return _toNodeChannel;
    }
}