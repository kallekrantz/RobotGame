package com.robotgame.storage.entities;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: KarlJohan
 * Date: 10/24/13
 * Time: 4:54 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="User")
public class User{
    String username, firstname, lastname;
    int userId;
    String pwdHash;
    public User(String usrName, String frstName, String lstName, int id, String hash){
        username = usrName;
        firstname = frstName;
        lastname = lstName;
        userId = id;
        pwdHash = hash;
    }
    public String getPwdHash() {
        return pwdHash;
    }

    public void setPwdHash(String pwdHash) {
        this.pwdHash = pwdHash;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    @Id
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
