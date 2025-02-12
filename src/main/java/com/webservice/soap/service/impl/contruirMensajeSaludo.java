package com.webservice.soap.service.impl;

import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class contruirMensajeSaludo {

    private static final Map<String, Integer> SALUDOS = new LinkedHashMap<>();

    static {
        SALUDOS.put("Buenos días", 12);
        SALUDOS.put("Buenas tardes", 18);
        SALUDOS.put("Buenas noches", 24);
    }

    public String obtenerSaludo(int hora) {
        if (hora < 0 || hora > 23) {
            throw new IllegalArgumentException("La hora debe estar entre 0 y 23");
        }

        return SALUDOS.entrySet().stream()
                .filter(entry -> hora < entry.getValue())
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse("Buenas noches");
    }
}
