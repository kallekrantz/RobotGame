package com.robotgame.gameengine.tests.FactoryTests;


import com.robotgame.gameengine.Robot.Nodes.LogicNodes.AndNode;
import com.robotgame.gameengine.Robot.Nodes.*;
import org.junit.Test;


import static com.robotgame.gameengine.Robot.Builder.NodeFactory.Create;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: KarlJohan
 * Date: 10/31/13
 * Time: 4:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class NodeFactoryTest{
    @Test
    public void AndNode(){
        NodeData properties = new NodeData(NodeType.And, 0, 0);
        Class<AndNode> andNodeClass = (Class<AndNode>) Create(properties, 0).getClass();
        assertThat(andNodeClass, is(
                equalTo(AndNode.class)
        ));
    }




}
