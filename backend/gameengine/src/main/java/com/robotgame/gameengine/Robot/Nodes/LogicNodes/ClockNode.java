package Robot.Nodes.LogicNodes;

import Robot.MatchContext;
import Robot.Nodes.Node;
import Robot.Nodes.NodeAction;
import Robot.Nodes.NodeCategory;
import Robot.Nodes.NodeType;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: Oskar
 * Date: 2013-10-14
 * Time: 12:13
 * To change this template use File | Settings | File Templates.
 */

//A default node, mostly for testing. It's a logic node that only passes on the single input channel to the output.
public class ClockNode extends Node
{
    /*
    protected boolean _isUpdated;
    protected boolean[] _output;
    protected int _numOutput;
    protected int[] _connectionToInput;
    protected int _numInput;
    protected NodeCategory _category;
    protected NodeType _type;
    protected int _ownerIndex;
    */
    private int _time;
    private int _period;

    public ClockNode(int ownerIndex, int period)
    {
        _isUpdated = false;
        _numInput = 0;
        _numOutput = 1;
        _output = new boolean[_numOutput];
        // _connectionToInput =    //N/A because of 0 inputs
        _category = NodeCategory.Logic;
        _type = NodeType.L_Clock;
        _ownerIndex = ownerIndex;

        _time = 0;
        _period = period;
    }

    @Override
    public void Update(MatchContext context, LinkedList<NodeAction> actions,  boolean[] input)
    {
        _time++;
        _time %= _period;
        if (_time == 0) _output[0] = true;
        else _output[0] = false;

        _isUpdated = true;
    }
}
