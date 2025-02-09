package com.webservice.soap.service;

import org.springframework.stereotype.Service;

@Service
public class buildHealth {

    public String build (int hora) {

        if (hora < 12) {
            return "Buenos dias";
        } else if (hora < 18) {
            return "Buenas tardes";
        } else {
            return "Buenas noches";
        }
    }
}
