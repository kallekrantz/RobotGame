package com.robotgame.storage.entities;


import com.robotgame.storage.database.PasswordHasher;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * User Entity class contains everything that's needed for a single user.
 */

@Entity
@Table(name="User")
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
    public User(User u)
    {
        _username = u.getUsername();
        _firstname = u.getFirstname();
        _lastname = u.getLastname();
        _pwdHash = u.getPwdHash();
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
    @Column(unique = true)
    @GenericGenerator(name="increment", strategy = "increment")
    public int getId()
    {
        return _userId;
    }
    public void setId(int userId){
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


    public static User merge(User u,JSONObject obj) throws JSONException{
        User merge = new User(u);
        if(obj.has("username")){
            merge.setUsername(obj.getString("username"));
        }
        if(obj.has("firstname")){
            merge.setFirstname(obj.getString("firstname"));
        }
        if(obj.has("lastname")){
            merge.setLastname(obj.getString("lastname"));
        }
        if(obj.has("password")){
            merge.setPwdHash(PasswordHasher.hash(obj.getString("password")));
        }
        return merge;
    }
    public static User create(JSONObject jsonObj) throws JSONException{
        return new User(
                jsonObj.getString("username"),
                jsonObj.getString("firstname"),
                jsonObj.getString("lastname"),
                PasswordHasher.hash(
                        jsonObj.getString("password")
                )
        );
    }
}
