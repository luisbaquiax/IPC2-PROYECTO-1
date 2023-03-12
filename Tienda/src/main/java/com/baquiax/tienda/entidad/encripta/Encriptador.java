/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baquiax.tienda.entidad.encripta;

import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author luis
 */
public class Encriptador {

    /**
     * Se añade la clave de 32 dígitos para encriptar con AES 256
     */
    private static final String SECRET_KEY_AES = "nikocode1234nikocontraseñatienda";
    /**
     * la "sal"
     */
    private static final String SALT_AES = "luis1234";

    public Encriptador() {
    }

    /**
     * Encripa cualquier cadena
     *
     * @param password
     * @return
     */
    public String encriptar(String password) {
        try {
            byte[] iv = new byte[16];
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec keySpec = new PBEKeySpec(SECRET_KEY_AES.toCharArray(), SALT_AES.getBytes(), 65536, 256);
            SecretKey secretKeyTemp = secretKeyFactory.generateSecret(keySpec);
            SecretKeySpec secretKey = new SecretKeySpec(secretKeyTemp.getEncoded(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
            return Base64.getEncoder().encodeToString(cipher.doFinal(password.getBytes("UTF-8")));
        } catch (Exception e) {
            System.out.println("error al encriptar");
            System.out.println(e.getMessage());
        }
        return "";
    }

    /**
     * Desencripta cualquier cadena
     *
     * @param clave
     * @return
     */
    public String desencriptar(String clave) {
        byte[] iv = new byte[16];
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec keySpec = new PBEKeySpec(SECRET_KEY_AES.toCharArray(), SALT_AES.getBytes(), 65536, 256);
            SecretKey secretKeyTemp = secretKeyFactory.generateSecret(keySpec);
            SecretKeySpec secretKey = new SecretKeySpec(secretKeyTemp.getEncoded(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(clave)));
        } catch (Exception e) {
            System.out.println("error al desencriptar");
            System.out.println(e.getMessage());
        }
        return "";
    }
}
