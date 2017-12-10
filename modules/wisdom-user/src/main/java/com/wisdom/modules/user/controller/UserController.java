package com.wisdom.modules.user.controller;

import com.wisdom.framework.core.controller.BaseController;
import com.wisdom.framework.core.exception.BaseException;
import com.wisdom.modules.user.domain.User;
import com.wisdom.modules.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping(value="/find")
    public String find(int id){

        User user = userService.findById(id);

        return "login";

    }

    @RequestMapping("index")
    public String index(){

        return "index";
    }
}
