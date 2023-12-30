package org.bellotech.SpringRestdemo.security;

import java.security.KeyPair;

public class Jwks {

    private Jwks(){}

    public static RSAKey generateRsa(){

        KeyPair keyPair = KeyGenerator.generateRsaKeys();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPrivate();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        return new RSAKey.Builder(publicKey)
        .privateKey(privateKey)
        .keyID(UUID.randomUUID().toString())
        .build();
    }
    }
    
}
