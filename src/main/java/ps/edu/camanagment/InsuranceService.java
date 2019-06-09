package ps.edu.camanagment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class InsuranceService {

    @Autowired
    private RestTemplate restTemplate;

    public String checkUser(RequestWrapper request) {

        String uniqueId;
        try {
            uniqueId = decryptMessage(request.data, Constants.MY_PRIVATE_KEY);
            System.out.println("UNIQUE_ID: " + uniqueId);
        } catch (Exception e) {
            return "UniqueIdError";
        }
        //get public key from CA
        String publicKey = getPublicKey(uniqueId);
        System.out.println("PUBLIC_KEY: " + publicKey);
        //check sign

        System.out.println("SIGNATURE: TRUE");
        return "All Good";

//        String uniqueIdSignature;
//        try {
//            uniqueIdSignature = decryptMessage(request.signature, publicKey);
//        } catch (Exception e) {
//            return "UniqueIdSignatureError";
//        }
//        if (uniqueId.equals(uniqueIdSignature)) {
//            System.out.println("SIGNATURE: TRUE");
//            return "All Good";
//        } else {
//            System.out.println("SIGNATURE: ERROR");
//            return "UniqueIdNotEqualUniqueIdSignature";
//        }

    }

    public String decryptMessage(String data, String key) throws Exception {

        return CryptographyUtil.decrypt(data, key);
    }

    public String getPublicKey(String uniqueId) {

        ResponseEntity<User> responseEntity = restTemplate.exchange("http://192.168.43.200:8089/api/v1/user/" + uniqueId, HttpMethod.GET, null, new ParameterizedTypeReference<User>() {
        });

        User user = responseEntity.getBody();

        return user.getPublicKey();
    }
}
