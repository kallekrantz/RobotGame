package Robot;

import Robot.Nodes.*;
import Util.Vector2;

import java.util.LinkedList;


public class Robot
{
    private NodeSystem _nodeSystem;
    private int _numNodes;
    private  int _index;
    private RobotState _currentState, _futureState;
    private LinkedList<NodeAction> _actions;


    //Constructor for Robot class. Use Robot.Builder.RobotFactory to create Robot objects in a more practical way.
    public Robot(Node[] nodes,  NodeConnection[] connections, int index)
    {
        _nodeSystem = new NodeSystem(nodes, connections);
        _index = index;
        _currentState = new RobotState();
        _futureState = _currentState;
        _actions = new LinkedList<NodeAction>();

    }

    public void SetStartPos(Vector2 pos)
    {
        _futureState.pos = _currentState.pos = pos;
    }

    public void UpdateNodes(MatchContext context)
    {
        _actions.clear();
        _actions = _nodeSystem.Update(context);
    }

    public LinkedList<NodeAction> GetActions()
    {
        return _actions;
    }

    public int[] GetHotConnections()
    {
        return _nodeSystem.GetHotConnections();
    }





}