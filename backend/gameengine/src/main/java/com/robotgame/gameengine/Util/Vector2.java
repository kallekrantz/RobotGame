package com.robotgame.gameengine.Util;

/**
 * Created with IntelliJ IDEA.
 * User: Oskar
 * Date: 2013-10-25
 * Time: 21:38
 * To change this template use File | Settings | File Templates.
 */

/**
 * Represents a two-dimensional vector.
 */
public class Vector2
{
    public float x, y;

    public Vector2(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Rotates the vector counter clockwise around origin.
     * @param angle in radians
     */
    public void Rotate(float angle)
    {

    }
    //dela upp anglebetween i en funktion som returnar en
    //ny vektor typ static vector2 diff
    //sen använda det i atan2
    public float Angle(Vector2 a)
    {
    	//är det rätt x och y?
    	return (float) Math.atan2(a.x,a.y);
    }
    
    public static Vector2 Diff(Vector2 a, Vector2 b)
    {
        Vector2 c = new Vector2(a.x - b.x, a.y - b.y);
        return c;
    }
    /**
     * Checks if this vector is closer to the vector b than a certain limit.
     * @param b
     * @param distanceLimit
     * @return
     */
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


    public static Vector2 Add(Vector2 a, Vector2 b)
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
