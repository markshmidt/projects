package com.gomoku.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/ai")
    public String aiPage() {
        return "forward:/ai.html";
    }

    @GetMapping("/pvp")
    public String pvpPage() {
        return "forward:/pvp.html";
    }

    @GetMapping("/")
    public String indexPage() {
        return "forward:/index.html";
    }
}
