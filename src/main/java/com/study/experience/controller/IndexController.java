package com.study.experience.controller;

import com.study.experience.exception.IndexFileNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class IndexController {

    @GetMapping(value = "/")
    public String index() {
        log.info("goto index page");
        return "index";
    }

    @GetMapping(value = "/404")
    public String error404() {
        log.info("[error] 404 page");
        return "error/404";
    }

    @GetMapping(value = "/500")
    public String error500() {
        log.info("[error] 500 page");
        return "error/500";
    }

    @GetMapping(value = "/test/404")
    public String test404(ModelAndView modelAndView) {
        log.info("[Test] ExceptionHandler - 404");
        throw new IndexFileNotFoundException();
    }

}
