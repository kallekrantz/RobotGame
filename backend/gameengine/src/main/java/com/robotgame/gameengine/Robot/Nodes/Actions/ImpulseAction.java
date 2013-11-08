package com.robotgame.gameengine.Robot.Nodes.Actions;

import com.robotgame.gameengine.Robot.Nodes.NodeAction;
import com.robotgame.gameengine.Robot.Robot;
import com.robotgame.gameengine.Util.Vector2;

/**
 * Created with IntelliJ IDEA.
 * User: Oskar
 * Date: 2013-10-25
 * Time: 22:44
 * To change this template use File | Settings | File Templates.
 */

public class ImpulseAction extends NodeAction
{
    private Vector2 _force, _pointOfAttack;

    public ImpulseAction(Vector2 force, Vector2 pointOfAttack)
    {

    }


    //Ska verka på roboten med en impuls. Just nu skapar den bara en statisk rotation.
    public void PerformAction(Robot robot)
    {
        robot.GetCurrentState().rot += 0.01f;
    }


}
