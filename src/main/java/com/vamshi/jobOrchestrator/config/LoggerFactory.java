package com.vamshi.jobOrchestrator.config;

import org.slf4j.Logger;

public class LoggerFactory {
    private LoggerFactory() {
        // Prevent instantiation
    }

    public static Logger getLogger(Class<?> clazz) {
        return org.slf4j.LoggerFactory.getLogger(clazz);
    }

    public static Logger getLogger(String name) {
        return org.slf4j.LoggerFactory.getLogger(name);
    }
}
