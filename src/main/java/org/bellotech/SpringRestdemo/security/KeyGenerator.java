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
            KeyPairGenerator keyPairGenerator = keyPairGenerator.getInstance("RSA");

            keyPairGenerator.initialize(2048);
            KeyPair = keyPairGenerator.generateRsaKeys();
            }catch (Exception ex){
                throw new IllegalStateException(ex);

    }
    return KeyPair;
}

    


}
