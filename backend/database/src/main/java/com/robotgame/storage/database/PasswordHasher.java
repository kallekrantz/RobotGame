package com.robotgame.storage.database;

/**
 * Created with IntelliJ IDEA.
 * User: KarlJohan
 * Date: 11/6/13
 * Time: 2:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class PasswordHasher {
    public static String hash(String str){
        return org.apache.commons.codec.digest.DigestUtils.md5Hex(str);
    }
}
