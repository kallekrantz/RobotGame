package com.robotgame.storage.entities;


import org.codehaus.jettison.json.JSONObject;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Created with IntelliJ IDEA.
 * User: KarlJohan
 * Date: 10/24/13
 * Time: 4:54 PM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name="Users")
@XmlRootElement
public class User{
    private String _username, _firstname, _lastname;
    private int _userId;
    private String _pwdHash;

    public User()
    {
        _username = "";
        _firstname = "";
        _lastname = "";
        _userId = -1;
        _pwdHash = "";
    }
    public User(String usrName, String frstName, String lstName, String hash)
    {
        _username = usrName;
        _firstname = frstName;
        _lastname = lstName;
        _pwdHash = hash;
    }

    @XmlTransient
    public String getPwdHash()
    {
        return _pwdHash;
    }

    public void setPwdHash(String pwdHash)
    {
        this._pwdHash = pwdHash;
    }
    @Column(unique = true)
    public String getUsername()
    {
        return _username;
    }

    public void setUsername(String username)
    {
        this._username = username;
    }

    public String getFirstname()
    {
        return _firstname;
    }

    public void setFirstname(String firstname)
    {
        this._firstname = firstname;
    }

    public String getLastname()
    {
        return _lastname;
    }

    public void setLastname(String lastname)
    {
        this._lastname = lastname;
    }

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    public int getuserId()
    {
        return _userId;
    }
    public void setuserId(int userId){
        this._userId = userId;
    }

    @Override
    public String toString() {
        return "User{" +
                "_username='" + _username + '\'' +
                ", _firstname='" + _firstname + '\'' +
                ", _lastname='" + _lastname + '\'' +
                ", _userId=" + _userId +
                ", _pwdHash='" + _pwdHash + '\'' +
                '}';
    }
}
