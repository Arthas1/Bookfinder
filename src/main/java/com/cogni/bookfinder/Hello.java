package com.cogni.bookfinder;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController // potrzebne do web


public class Hello {

    @GetMapping("/index")

    public String ranking()    {
        return "Hello Books";
    }
}


//Taskkill /F /IM 12704