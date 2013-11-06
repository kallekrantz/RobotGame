package com.robotgame.storage.entities;




import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Timestamp;
@Entity
@Table(name="AuthTokens")
@XmlRootElement
public class AuthToken {


    private long lastUsed;
    private User user;
    private int authId;
    public AuthToken(User u,  long t){
        user = u;
        lastUsed = t;
    }
    public AuthToken(){}

    public AuthToken(User u){
        user = u;
    }

    public void touchTime(){
        lastUsed = System.currentTimeMillis()/1000;
    }

    public long getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(long lastUsed) {
        this.lastUsed = lastUsed;
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
    public int getAuthId() {
        return authId;
    }

    public void setAuthId(int authId) {
        this.authId = authId;
    }
}
