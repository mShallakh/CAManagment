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
            return "All Good";
        } else {
            return "UniqueIdNotEqualUniqueIdSignature";
        }

    }

    public String decryptMessage(String data, String key) throws Exception {

        return CryptographyUtil.decrypt(key.getBytes(), data.getBytes());
    }

    public String getPublicKey(String uniqueId) {

        ResponseEntity<User> responseEntity = restTemplate.exchange("URL" + uniqueId, HttpMethod.GET, null, new ParameterizedTypeReference<User>() {
        });

        User user = responseEntity.getBody();

        return user.getPublicKey();
    }
}
