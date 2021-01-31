package org.bank.atm;

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
    private PublicKey publicKey;
    //
    private PrivateKey privateKey;
    private KeyPairGenerator keyPairGen;

    public Encryption() {
        try {
            rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            loadKey();
        } catch (final NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    public byte[] RSAEncrypt(final byte plainBytes[]) {
        byte cryptoBytes[] = null;
        try {
            rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey);
            cryptoBytes = rsaCipher.doFinal(plainBytes);
        } catch (final InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return cryptoBytes;
    }

    public byte[] RSADecrypt(final byte cryptoBytes[]) {
        byte plainBytes[] = null;
        try {
            rsaCipher.init(Cipher.DECRYPT_MODE, publicKey);
            plainBytes = rsaCipher.doFinal(cryptoBytes);
        } catch (final InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return plainBytes;
    }

    public void loadKey() {
        ObjectInputStream in;
        try {
            in = new ObjectInputStream(new FileInputStream("C:\\Users\\mothe\\IdeaProjects\\ATM-BankServer3\\ATM\\pub.key"));
            publicKey = (PublicKey) in.readObject();
            Console.log("Client using public key: " + getPublicKey().toString());
            in.close();
        } catch (final Exception e) {
            e.printStackTrace();
        }
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

    public Cipher getRSACipher() {
        return rsaCipher;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public KeyPairGenerator getKeyPairGen() {
        return keyPairGen;
    }
}
