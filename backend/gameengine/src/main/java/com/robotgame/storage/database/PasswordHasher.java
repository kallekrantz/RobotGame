package com.robotgame.storage.database;


/**
 * Hashes a password according to md5.
 */
public class PasswordHasher {
    public static String hash(String str){
        return org.apache.commons.codec.digest.DigestUtils.md5Hex(str);
    }
}
