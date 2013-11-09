package com.robotgame.storage.entities;


import org.codehaus.jettison.json.JSONObject;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created with IntelliJ IDEA.
 * User: KarlJohan
 * Date: 10/24/13
 * Time: 5:19 PM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "Robots")
@XmlRootElement
public class Robot{
    private String robotName;
    private User user;
    private int robotId;
    private String robotDesign;
    public Robot(String robotName, User user, String robotDesign){
        this.robotName = robotName;
        this.user = user;
        this.robotDesign = robotDesign;
    }

    public Robot() {
    }

    public String getRobotName() {
        return robotName;
    }

    public void setRobotName(String robotName) {
        this.robotName = robotName;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    public int getRobotId() {
        return robotId;
    }

    public void setRobotId(int robotId) {
        this.robotId = robotId;
    }

    public String getRobotDesign() {
        return robotDesign;
    }

    public void setRobotDesign(String robotDesign) {
        this.robotDesign = robotDesign;
    }

    @Override
    public String toString() {
        return "Robot{" +
                "robotName='" + robotName + '\'' +
                ", user=" + user +
                ", robotId=" + robotId +
                ", robotDesign=" + robotDesign +
                '}';
    }
}
