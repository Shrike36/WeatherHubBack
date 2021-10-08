package com.weatherhub.app.utils;

import java.security.SecureRandom;

public class CodeGenerator {
    public static int generateCode(){
        SecureRandom secureRandom = new SecureRandom();
        return secureRandom.nextInt(899999)+100000;
    }
}
