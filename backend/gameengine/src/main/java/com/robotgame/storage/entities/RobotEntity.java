package com.robotgame.storage.entities;


import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * RobotEntity is the entity containing the RobotBlueprint, and robotname. Currently RobotEntity maps to user, and not the other way around.
 */

@Entity
@Table(name = "Robot")
@XmlRootElement
public class RobotEntity {
    private String robotName;
    private User user;
    private int id;
    private String robotDesign;
    public RobotEntity(String robotName, User user, String robotDesign){
        this.robotName = robotName;
        this.user = user;
        this.robotDesign = robotDesign;
    }

    public RobotEntity() {
    }

    public RobotEntity(RobotEntity robotEntity) {
        robotName = robotEntity.getRobotName();
        user = robotEntity.getUser();
        robotDesign = robotEntity.getRobotDesign();
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
    @Column(unique = true)
    @GenericGenerator(name="increment", strategy = "increment")
    public int getId() {
        return id;
    }

    public void setId(int robotId) {
        this.id = robotId;
    }

    public String getRobotDesign() {
        return robotDesign;
    }

    public void setRobotDesign(String robotDesign) {
        this.robotDesign = robotDesign;
    }

    @Override
    public String toString() {
        return "RobotEntity{" +
                "robotName='" + robotName + '\'' +
                ", user=" + user +
                ", robotId=" + id +
                ", robotDesign=" + robotDesign +
                '}';
    }

    public static RobotEntity create(JSONObject jsonObj) throws JSONException{
        return new RobotEntity(
                jsonObj.getString("robotName"),
                null,
                jsonObj.getString("robotDesign")
        );
    }

    public static RobotEntity merge(RobotEntity r, JSONObject obj) throws JSONException {
        RobotEntity merge = r;
        if(obj.has("robotDesign")){
            merge.setRobotDesign(obj.getString("robotDesign"));
        }
        if(obj.has("robotName")){
            merge.setRobotName(obj.getString("robotName"));
        }
        return merge;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
