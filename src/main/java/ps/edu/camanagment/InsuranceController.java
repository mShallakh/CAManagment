package ps.edu.camanagment;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/insurance")
@RestController
public class InsuranceController {

    @Autowired
    InsuranceService insuranceService;

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseWrapper insureMe(@RequestBody RequestWrapper request){

        return new ResponseWrapper(insuranceService.checkUser(request));
    }



}
