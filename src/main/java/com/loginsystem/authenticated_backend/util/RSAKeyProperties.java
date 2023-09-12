package com.loginsystem.authenticated_backend.util;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.stereotype.Component;

@Component
public class RSAKeyProperties {
    
    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;


    public RSAKeyProperties() {
        KeyPair pair = KeyGeneratorUtility.generateRsaKey();
        this.publicKey = (RSAPublicKey) pair.getPublic();
        this.privateKey = (RSAPrivateKey) pair.getPrivate();
    }

    

    /**
     * @return RSAPublicKey return the publicKey
     */
    public RSAPublicKey getPublicKey() {
        return publicKey;
    }

    /**
     * @param publicKey the publicKey to set
     */
    public void setPublicKey(RSAPublicKey publicKey) {
        this.publicKey = publicKey;
    }

    /**
     * @return RSAPrivateKey return the privateKey
     */
    public RSAPrivateKey getPrivateKey() {
        return privateKey;
    }

    /**
     * @param privateKey the privateKey to set
     */
    public void setPrivateKey(RSAPrivateKey privateKey) {
        this.privateKey = privateKey;
    }

}
