package com.robotgame.gameengine.Robot;

import com.robotgame.gameengine.Robot.Nodes.Node;
import com.robotgame.gameengine.Robot.Nodes.NodeAction;
import com.robotgame.gameengine.Robot.Nodes.NodeConnection;
import com.robotgame.gameengine.Robot.Nodes.NodeSystem;
import com.robotgame.gameengine.Util.Vector2;

import java.util.LinkedList;

/**
 * Represents a robot in a match. Important parts is the NodeSystem handling sensors and logic, and the RobotState describing the robots current state.
 * Created with the static factory RobotFactory.CreateRobot().
 * @see NodeSystem
 * @see RobotState
 * @see com.robotgame.gameengine.Robot.Builder.RobotFactory
 */
public class Robot
{
    private NodeSystem _nodeSystem;
    private int _numNodes;
    private  int _index;
    private RobotState _currentState; //, _futureState;
    private LinkedList<NodeAction> _actions;
    private float _boost;
    private float _mass;
    private float _maxThrust;
    private float _maxTurn;
    private Vector2 _impulse;


    /**
     * Constructor should not be used directly. Robot objects are best created by
     * @param nodes
     * @param connections
     * @param index
     */
    public Robot(Node[] nodes,  NodeConnection[] connections, int index)
    {
        _nodeSystem = new NodeSystem(nodes, connections);
        _index = index;
        _currentState = new RobotState();
        //_futureState = _currentState;
        _actions = new LinkedList<NodeAction>();
        _boost = 1;
        _mass = 5;
        _maxThrust = 12;
        _maxTurn = 1;
        _impulse = new Vector2(0, 0);
    }

    /**
     * Sets the starting position of the robot.
     * @param pos Two-dimensional vector.
     */
    public void SetStartPos(Vector2 pos)
    {
        //_futureState.pos =
        _currentState.pos = pos;
    }

    /**
     *
     * @param context
     */
    public void UpdateNodes(MatchContext context)
    {
        _actions.clear();
        _actions = _nodeSystem.Update(context);
    }


    /**
     * Updates the current state of the robot according to impulses
     */
    public void UpdateState()
    {
        _impulse.Multiply(_boost);
        _currentState.vel.Add(_impulse);

        //Apply dampening
        _currentState.w *= 0.9;
        _currentState.vel.x *= 0.9;
        _currentState.vel.y *= 0.9;

        //Add changes to pos and rot
        _currentState.rot += _currentState.w;
        _currentState.pos.Add(_currentState.vel);
    }

    public LinkedList<NodeAction> GetActions()
    {
        return _actions;
    }

    /**
     * Gets a list of the values sent through every connection.
     * To use this for real time visualization of the node system the connections need to have matching indices in front and backend. This may not be the case.
     * @return Array of boolean values, one for each connection.
     */
    public boolean[] GetHotConnections()
    {
        return _nodeSystem.GetHotConnections();  //Fungerar inte korrekt!!
    }

    public RobotState GetCurrentState()
    {
        return _currentState;
    }

    public void SetBoost(boolean isBoost)
    {
        if (isBoost) _boost = 2;
        else _boost = 1;
    }

    public float GetMass() {return _mass;}

    public float GetMaxThrust() {return _maxThrust;}

    public float GetMaxTurn() {return _maxTurn;}

    public void AddSpeed(float v, float relativeDirection)
    {
        _impulse.x += v * Math.cos(relativeDirection + _currentState.rot);
        _impulse.y += v * Math.sin(relativeDirection + _currentState.rot);
    }





}