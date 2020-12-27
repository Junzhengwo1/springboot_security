package com.kou.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author JIAJUN KOU
 */
@Controller

public class RouterController {

    @GetMapping({"/", "index", "index.html"})
    public String index(){

        return "index";
    }
    @GetMapping("/level1/index")
    public String level1() {
        return "levelOne/one";
    }

    @GetMapping("/level2/index")
    public String level2() {
        return "levelTwo/two";
    }

    @GetMapping("/level3/index")
    public String level3() {
        return "levelThree/three";
    }

    @GetMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

}
