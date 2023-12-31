package org.bellotech.SpringRestdemo.security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

import org.springframework.stereotype.Component;

@Component
final  class KeyGeneratorUtils {

    private KeyGeneratorUtils(){}

    static KeyPair generateRsaKeys(){
        KeyPair KeyPair;
        try{
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");

            keyPairGenerator.initialize(2048);
            KeyPair = keyPairGenerator.generateKeyPair();
            }catch (Exception ex){
                throw new IllegalStateException(ex);

    }
    return KeyPair;
}

    


}
