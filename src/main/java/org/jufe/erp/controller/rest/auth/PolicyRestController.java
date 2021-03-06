package org.jufe.erp.controller.rest.auth;

import org.apache.log4j.Logger;
import org.jufe.erp.entity.Policy;
import org.jufe.erp.service.auth.PolicyService;
import org.jufe.erp.utils.anno.AuthRequest;
import org.jufe.erp.utils.enums.AuthLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * Created by raomengnan on 16-9-12.
 */
@RestController
@RequestMapping("/rest/auth/policy")
public class PolicyRestController {
    @Autowired
    private PolicyService policyService;

    private Logger logger = Logger.getLogger(PolicyRestController.class);

    @AuthRequest(level = AuthLevel.ADMIN)
    @RequestMapping("/policy-info/{policyId}")
    public Policy getPolicy(@PathVariable("policyId") String id){
        logger.debug("/policy-info: " + id);
        return policyService.getPolicy(id);
    }

    @AuthRequest(level = AuthLevel.ADMIN)
    @RequestMapping(value = "/save-policy", method = RequestMethod.PUT)
    public ResponseEntity<ModelMap> savePolicy(@RequestBody Policy policy){
        logger.debug("/save-policy:" + policy);
        ModelMap map = new ModelMap();
        boolean res = policyService.savePolicy(policy);
        map.put("result",  res);
        return new ResponseEntity<>(map, HttpStatus.OK);

    }

    @AuthRequest(level = AuthLevel.ADMIN)
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<ModelMap> deletePolicy(@RequestBody String id){
        logger.debug("/delete-policy:" + id);
        ModelMap map = new ModelMap();
        boolean res = policyService.deletePolicy(id);
        map.put("result", res);
        return new ResponseEntity<ModelMap>(map, HttpStatus.OK);
    }


}
