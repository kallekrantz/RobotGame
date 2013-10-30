package Robot.Nodes.Actions;

import Robot.Nodes.NodeAction;
import Robot.Robot;

/**
 * Created with IntelliJ IDEA.
 * User: Oskar
 * Date: 2013-10-25
 * Time: 22:44
 * To change this template use File | Settings | File Templates.
 */

public class DebugAction extends NodeAction
{
    private String _message;

    public DebugAction(String message)
    {
        _message = message;
    }



    public void PerformAction(Robot robot)
    {
        System.out.println(_message);
    }


}
