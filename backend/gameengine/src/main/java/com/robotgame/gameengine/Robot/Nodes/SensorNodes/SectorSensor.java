package com.robotgame.gameengine.Robot.Nodes.SensorNodes;


import com.robotgame.gameengine.Robot.MatchContext;
import com.robotgame.gameengine.Robot.Nodes.Node;
import com.robotgame.gameengine.Robot.Nodes.NodeAction;
import com.robotgame.gameengine.Robot.Nodes.NodeCategory;
import com.robotgame.gameengine.Robot.Nodes.NodeType;
import com.robotgame.gameengine.Util.Vector2;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: Oskar
 * Date: 2013-10-14
 * Time: 12:13
 * To change this template use File | Settings | File Templates.
 */

/**
 * Detects whether any other robots are found within a certain distance.
 */
public class SectorSensor extends Node
{
    /*
    Members of parent class Node to be defined in constructor:
    _maxInputs = ?;
    _connectionToInput = new int[_maxInputs];  //If _maxInputs > 0
    _category = NodeCategory.?;
    _type = NodeType.?;
    _ownerIndex = ownerIndex;
    */

    private float _angle;

    /**
     * Creates a sector sensor.
     * @param ownerIndex
     * @param maxDist    The radius in cm within which the sensor checks for other robots.
     */
    public SectorSensor(int ownerIndex, float angle)
    {
        _maxInputs = 0;
        _category = NodeCategory.Sensor;
        _type = NodeType.SectorSensor;
        _ownerIndex = ownerIndex;
        
        _angle = angle;
    }

    @Override
    public void Update(MatchContext context, LinkedList<NodeAction> actions,  boolean[] input)
    {
        if (context == null) _output = false;
        
        else
        {
            Vector2 myPos = context.robotStates[_ownerIndex].pos;
            float myRot = (float) (context.robotStates[_ownerIndex].rot%(2*Math.PI)); //If rotated more than 2 pi
            float totRot = myRot+_angle; // robot rotation + sensor angle
            _output = false;
            
            
            
            for (int n = 0; n < context.GetNumRobots(); n++)
            {
            	
                if (n != _ownerIndex) //Only check distance to other robots, not to the owner.
                {
                	Vector2 diff =  Vector2.Diff(context.robotStates[n].pos, context.robotStates[0].pos);
                	float angleBetween = myPos.Angle(diff);
                	if(totRot >= (2*Math.PI)){
                		if( ( (0 <= angleBetween)&&(angleBetween <= (totRot%2*Math.PI)) ) || ( (myRot <=angleBetween)&&(angleBetween <= (2*Math.PI) ) ) )
                			_output = true;
                	}
                	else{
                		
                		if(totRot > myRot)
                		{
                			if((myRot <= angleBetween)&&(angleBetween <= totRot))
                				_output = true;
                		}
                		else
                		{
                			if((totRot <= angleBetween)&&(angleBetween <= myRot))
                				_output = true;
                		}
                	}	
                }
            }

        }
        _isUpdated = true;
    }
}
