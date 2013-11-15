package com.robotgame.gameengine.Util;

/**
 * Created with IntelliJ IDEA.
 * User: Oskar
 * Date: 2013-10-25
 * Time: 21:38
 * To change this template use File | Settings | File Templates.
 */
public class Vector2
{
    public float x, y;

    public Vector2(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public void Rotate(float angle)
    {

    }

    public boolean DistanceToIsCloserThan(Vector2 b, float distanceLimit)
    {
        float dx = x - b.x;
        float dy = y - b.y;
        return (dx * dx + dy * dy) < (distanceLimit * distanceLimit);
    }

    public boolean DistanceToIsFartherThan(Vector2 b, float distanceLimit)
    {
        float dx = x - b.x;
        float dy = y - b.y;
        return (dx * dx + dy * dy) > (distanceLimit * distanceLimit);
    }

    public boolean DistanceToIsBetween(Vector2 b, float minDist, float maxDist)
    {
        float dx = x - b.x;
        float dy = y - b.y;
        float distSq = dx * dx + dy * dy;
        return distSq > minDist * minDist && distSq < maxDist * maxDist;
    }


    public Vector2 Add(Vector2 a, Vector2 b)
    {
        Vector2 c = new Vector2(a.x + b.x, a.y + b.y);
        return c;
    }

    public void Add(Vector2 a)
    {
        x += a.x;
        y += a.y;
    }



    public void Multiply(float a)
    {
        x *= a;
        y *= a;
    }
}
