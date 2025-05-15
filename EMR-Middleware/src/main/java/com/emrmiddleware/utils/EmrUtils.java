package com.emrmiddleware.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmrUtils {

  public static Timestamp getFormatDate(String date) {

    return Timestamp.valueOf(date);
  }

  public static String getCurrentTime() {

    Date date = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return dateFormat.format(date);
  }

  public static String get_SHA_512_SecurePassword(String passwordToHash, String salt) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-512");
      String input = passwordToHash + salt;
      byte[] messageDigest = md.digest(input.getBytes());

      BigInteger no = new BigInteger(1, messageDigest);
      String hashtext = no.toString(16);

      while (hashtext.length() < 32) {
        hashtext = "0" + hashtext;
      }
      return hashtext;
    }

    // For specifying wrong message digest algorithms
    catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }
}
