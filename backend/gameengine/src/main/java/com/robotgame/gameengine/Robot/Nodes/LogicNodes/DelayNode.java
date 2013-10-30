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

//Passes through the incoming signal with a delay defined in update steps
public class DelayNode extends Node
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
    private int _timer;
    private boolean[] _delayBank;
    private int _delay;

    public DelayNode(int ownerIndex, int delay)
    {
        _timer = 0;
        _delay = delay;
        _delayBank = new boolean[delay + 1];
        _isUpdated = false;
        _numInput = 1;
        _numOutput = 1;
        _output = new boolean[_numOutput];
        _connectionToInput = new int[_numInput];
        _category = NodeCategory.Logic;
        _type = NodeType.L_Delay;
        _ownerIndex = ownerIndex;
    }

    @Override
    public void Update(MatchContext context, LinkedList<NodeAction> actions,  boolean[] input)
    {
        if (_isUpdated) return;

        if (input == null) _output[0] = false;
        else
        {
            _delayBank[(_timer + _delay) % (_delay +1)] = input[0];
            _output[0] = _delayBank[_timer];
            _timer++;
            _timer %= _delay + 1;
        }
        _isUpdated = true;
    }
}
