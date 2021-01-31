package org.bank.server.io;

import org.bank.atm.misc.Console;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.*;

/**
 * @author Motheen Baig
 */
public class Encryption {

    private Cipher rsaCipher;
    //
    private PrivateKey privateKey;
    //
    private KeyPairGenerator keyPairGen;

    public Encryption() {
        try {
            rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            privateKey = loadPrivateKey();
        } catch (final NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    public byte[] RSAEncrypt(final byte plainBytes[]) {
        byte cryptoBytes[] = null;
        try {
            rsaCipher.init(Cipher.ENCRYPT_MODE, privateKey);
            cryptoBytes = rsaCipher.doFinal(plainBytes);
        } catch (final InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return cryptoBytes;
    }

    public byte[] RSADecrypt(final byte cryptoBytes[]) {
        byte plainBytes[] = null;
        try {
            rsaCipher.init(Cipher.DECRYPT_MODE, privateKey);
            plainBytes = rsaCipher.doFinal(cryptoBytes);
        } catch (final InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return plainBytes;
    }

    public PrivateKey loadPrivateKey() {
        ObjectInputStream in;
        PrivateKey privateKey = null;
        try {
            in = new ObjectInputStream(new FileInputStream("C:\\Users\\mothe\\IdeaProjects\\ATM-BankServer3\\BankServer\\prv.key"));
            privateKey = (PrivateKey) in.readObject();
            Console.log("Server using private key: " + privateKey.toString());
            in.close();
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return privateKey;
    }

    public void generateAndStoreKeys() {
        KeyPair keyPair;
        if (keyPairGen == null) {
            try {
                keyPairGen = KeyPairGenerator.getInstance("RSA");
            } catch (final NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        keyPairGen.initialize(4096);
        keyPair = keyPairGen.generateKeyPair();
        saveKey("C:\\Users\\mothe\\IdeaProjects\\ATM-BankServer3\\BankServer\\prv.key", keyPair.getPrivate());
        saveKey("C:\\Users\\mothe\\IdeaProjects\\ATM-BankServer3\\ATM\\pub.key", keyPair.getPublic());
    }

    public void saveKey(final String fileName, final Object o) {
        ObjectOutputStream out;
        try {
            out = new ObjectOutputStream(new FileOutputStream(fileName));
            out.writeObject(o);
            out.close();
        } catch (final Exception e) {
            Console.log("Error writing 'Key' " + fileName);
        }
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }
}
