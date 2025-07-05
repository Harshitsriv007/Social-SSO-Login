package com.example.social.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import java.util.Collections;
import java.util.Map;


@RestController
public class SocialApplicationController{

    @GetMapping("/user")
    public Map<String, Object> user(OAuth2AuthenticationToken principal) {
        String name = principal.getPrincipal().getAttribute("name");
        if (name == null) {
            name = principal.getPrincipal().getAttribute("login"); // fallback for GitHub
        }
        return Collections.singletonMap("name", name);
//        return principal.getPrincipal().getAttributes();
    }
}