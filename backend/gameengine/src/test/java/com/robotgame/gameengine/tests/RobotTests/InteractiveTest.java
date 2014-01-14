package com.robotgame.gameengine.tests.RobotTests;

import com.google.gson.Gson;
import com.robotgame.gameengine.Match.IMatchHandler;
import com.robotgame.gameengine.Match.Match;
import com.robotgame.gameengine.Match.MatchResult;
import com.robotgame.gameengine.Network.MatchState;
import com.robotgame.gameengine.Robot.Builder.RobotBlueprint;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: Oskar
 * Date: 2013-12-19
 * Time: 12:28
 * To change this template use File | Settings | File Templates.
 */
public class InteractiveTest extends JFrame implements IMatchHandler
{
    private Match match;
    private Client client;

    public InteractiveTest()
    {
        match = new Match(this, 0);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon r1 = new ImageIcon("C:\\Users\\Oskar\\Documents\\GitHub\\RobotGame\\backend\\gameengine\\src\\resources\\robot.png");
        ImageIcon r2 = new ImageIcon("C:\\Users\\Oskar\\Documents\\GitHub\\RobotGame\\backend\\gameengine\\src\\resources\\robot2.png");


        client = new Client(r1.getImage(), r2.getImage());
        add(client);


        setSize(600, 500);
        setLocationRelativeTo(null);
        setTitle("LORB - the logical robot battle");
        setResizable(false);

        setVisible(true);
    }

    @Test
    public void RobotComplete()
    {
        InteractiveTest test = new InteractiveTest();
        Gson gson = new Gson();
        String s;
        RobotBlueprint blueprint1, blueprint2;

        s = (String)JOptionPane.showInputDialog("Paste json string for Robot 1: ", null);

        try
        {
            blueprint1 = gson.fromJson(s, RobotBlueprint.class);
        }
        catch (Exception e)
        {
            blueprint1 = RobotBlueprint.GetDummyBlueprint();
        }


        s = (String)JOptionPane.showInputDialog("Paste json string for Robot 2: ", null);

        try
        {
            blueprint2 = gson.fromJson(s, RobotBlueprint.class);
        }
        catch (Exception e)
        {
            blueprint2 = RobotBlueprint.GetDummyBlueprint();
        }




        Vector<RobotBlueprint> blueprints = new Vector<RobotBlueprint>();
        blueprints.add(blueprint1);
        blueprints.add(blueprint2);


        test.match.BuildRobots(blueprints);

        test.match.SetMatchLength(7);


        test.match.SetRunning(true);
        test.match.run();
    }


    @Override
    public void MatchEnded(MatchResult results) {

    }

    @Override
    public void SendMatchState(MatchState matchState)
    {
        client.SetState(matchState);

    }

    @Override
    public void SendFirstMatchState(MatchState _matchState) {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    private class Client extends JPanel
    {

        private Timer timer;
        private Image _robot1, _robot2;
        private MatchState _state;
        AffineTransform view;

        public Client(Image robot1, Image robot2)
        {
            setFocusable(true);
            setBackground(Color.BLACK);
            setDoubleBuffered(false);

            _robot1 = robot1;
            _robot2 = robot2;

            view = new AffineTransform();
            //view.setToScale(30, -30);
            view.translate(200, 150);

        }


        public void SetState(MatchState state)
        {
            _state = state;
            repaint();
        }


        public void paint(Graphics g)
        {
            if (_state == null) return;

            super.paint(g);

            Graphics2D g2d = (Graphics2D)g;
            //g2d.drawImage(_robot1, 100, 100, this);

            g2d.setTransform(view);

            for (int n = 0; n < _state.numRobots; n++)
            {
                AffineTransform t = AffineTransform.getScaleInstance(1, 1);
                t.rotate(_state.robotStates[n].rot, -30, 36);
                t.translate(_state.robotStates[n].pos.x * 50, _state.robotStates[n].pos.y * 50);
                g2d.drawImage(n % 2 == 0 ? _robot1 : _robot2, t, null);
            }


            Toolkit.getDefaultToolkit().sync();
            g.dispose();
        }

    }
}
