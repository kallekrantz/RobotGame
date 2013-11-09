package com.robotgame.storage.entities;




import com.robotgame.storage.database.PasswordHasher;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.sql.Timestamp;
@Entity
@Table(name="AuthTokens")
@XmlRootElement
public class AuthToken {


    private long lastUsed;
    private User user;
    private int authId;
    private String token;
    public AuthToken(User u,  long t, String token){
        user = u;
        lastUsed = t;
        this.token = token;
    }
    public AuthToken(){}

    public AuthToken(User u){
        user = u;
        touchTime();
        generateToken();
    }


    @XmlTransient
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

    @XmlTransient
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    public int getAuthId() {
        return authId;
    }

    public void setAuthId(int authId) {
        this.authId = authId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    public void touchTime(){
        lastUsed = System.currentTimeMillis()/1000;
    }
    public void generateToken(){
        token = PasswordHasher.hash(Long.toString(System.currentTimeMillis())+ "This is a authtoken");
    }

}
