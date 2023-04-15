package bolk_app.reg_login.security;

import org.springframework.context.annotation.Configuration;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Configuration class to hashcode users' passwords
 */
@Configuration
public class PasswordEncoder {

    public static String getPasswordEncoded(String raw) {
        String encoded = null;
        try
        {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(raw.getBytes());
            byte[] bytes = m.digest();
            StringBuilder s = new StringBuilder();
            for (byte aByte : bytes) {
                s.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            encoded = s.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return encoded;
    }
}
