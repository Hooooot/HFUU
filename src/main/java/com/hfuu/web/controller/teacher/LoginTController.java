package com.hfuu.web.controller.teacher;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("")
public class LoginTController {
    private static Logger logger = Logger.getLogger(LoginTController.class);

    @RequestMapping(value = {"/logint"}, method = RequestMethod.GET)
    public String toLoginT() {
        logger.debug("跳转到：\"teacher/login\"");
        return "teacher/login";
    }


}
