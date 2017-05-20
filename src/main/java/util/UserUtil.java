package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserUtil {
    /**
     * Returns a String object that is the password, hashed from the original String that can be used to be stored.
     * If there is a problem while hashing the password, it will return a nuull String.
     * The input password cannot be an empty String object.
     * @param password the password that will be hashed as a String object
     * @return the hashed password
     */
    public static String hashPassword(String password) {
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(password.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }

        return generatedPassword;
    }
}
