package com.robotgame.gameengine.tests.FactoryTests;

import com.robotgame.gameengine.Robot.Nodes.LogicNodes.And3Node;
import com.robotgame.gameengine.Robot.Nodes.LogicNodes.AndNode;
import com.robotgame.gameengine.Robot.Nodes.NodeProperties;
import com.robotgame.gameengine.Robot.Nodes.NodeType;
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
        NodeProperties properties = new NodeProperties(NodeType.And, 0,0,0);
        Class<AndNode> andNodeClass = (Class<AndNode>) Create(properties, 0).getClass();
        assertThat(andNodeClass, is(
                equalTo(AndNode.class)
        ));
    }




    @Test
    public void And3Node(){
        NodeProperties properties = new NodeProperties(NodeType.False, 0,0,0);
        Class<And3Node> and3NodeClass = (Class<And3Node>) Create(properties, 0).getClass();
        assertThat(and3NodeClass, is(
                equalTo(And3Node.class)
        ));
    }


}
