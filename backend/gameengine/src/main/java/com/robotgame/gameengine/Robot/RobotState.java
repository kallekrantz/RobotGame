package com.robotgame.gameengine.Robot;


import com.robotgame.gameengine.Util.Vector2;

/**
 * Created with IntelliJ IDEA.
 * User: Oskar
 * Date: 2013-10-14
 * Time: 12:23
 * To change this template use File | Settings | File Templates.
 */
public class RobotState
{
    public Vector2 pos;
    public Vector2 vel;

    public float rot;
    public float w;

    public RobotState()
    {
        pos = new Vector2(0, 0);
        vel = new Vector2(0, 0);

        rot = 0;
        w = 0;
    }





}
