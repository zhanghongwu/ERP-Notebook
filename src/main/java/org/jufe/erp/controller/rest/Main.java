package org.jufe.erp.controller.rest;

import org.jufe.erp.repository.QOSComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Raomengnan on 2016/11/14.
 */
@Controller
public class Main {
    @Autowired
    private QOSComponent qosComponent;

    @RequestMapping("/obj-store")
    public String objectStore(){
        return qosComponent.getHost();
    }
}
