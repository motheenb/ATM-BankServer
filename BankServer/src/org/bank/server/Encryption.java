package org.bank.server;

import org.bank.atm.Console;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.security.*;

/**
 * @author Motheen Baig
 */
public class Encryption {

    private Cipher rsaCipher;
    //
    private PrivateKey privateKey;

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

    public PrivateKey getPrivateKey() {
        return privateKey;
    }
}
