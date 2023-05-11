package utilities;

import org.apache.commons.codec.binary.Base64;

public class AuthenticationHelper {
    public static String getEncodedCred(String email, String apiToken){
        if (email.isEmpty() || apiToken.isEmpty()){
            throw new IllegalArgumentException("[ERR] Email or Api Token can not be null");
        }

        byte[] encodedCred = Base64.encodeBase64((email + ":" + apiToken).getBytes());
        return new String(encodedCred);
    }
}
