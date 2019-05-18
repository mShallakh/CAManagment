package ps.edu.camanagment;

import org.springframework.stereotype.Service;

@Service
public class InsuranceService {


    public String checkUser(RequestWrapper request) {

        //decrypt data
        String uniqueId;
        try {
            uniqueId = decryptMessage(request.data, Constants.MY_PRIVATE_KEY);
        } catch (Exception e) {
            return "UniqueIdError";
        }
        //get public key from CA
        String publicKey = getPublicKey(uniqueId);
        //check sign
        String uniqueIdSignature;
        try {
            uniqueIdSignature = decryptMessage(request.signature, publicKey);
        } catch (Exception e) {
            return "UniqueIdSignatureError";
        }
        if (uniqueId.equals(uniqueIdSignature)) {
            //kolo tmm
            return backgroundCheck(uniqueId);
        } else {
            return "UniqueIdNotEqualUniqueIdSignature";
        }

    }

    public String backgroundCheck(String id) {

        return "All Good";

    }

    public String decryptMessage(String data, String key) throws Exception {

        return CryptographyUtil.decrypt(key.getBytes(), data.getBytes());
    }

    public String getPublicKey(String uniqueId) {

        String publicKey = "";
        return publicKey;
    }
}
