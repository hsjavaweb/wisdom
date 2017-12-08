package com.wisdom.modules.common.controller;

import com.wisdom.framework.core.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



/**
 * @author hyberbin on 2016/10/30.
 */
@Controller
@RequestMapping("/")
public class IndexController extends BaseController {

    @RequestMapping(value = "/index")
    public String index() {
        return "index";
    }

}
