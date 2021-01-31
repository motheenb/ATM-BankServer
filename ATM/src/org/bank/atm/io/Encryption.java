package org.bank.atm.io;

import org.bank.atm.misc.Console;

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
    private PublicKey publicKey;

    public Encryption() {
        try {
            rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            publicKey = loadPublicKey();
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

    public PublicKey loadPublicKey() {
        ObjectInputStream in;
        PublicKey publicKey = null;
        try {
            in = new ObjectInputStream(new FileInputStream("C:\\Users\\mothe\\IdeaProjects\\ATM-BankServer3\\ATM\\pub.key"));
            publicKey = (PublicKey) in.readObject();
            Console.log("Client using public key: " + publicKey.toString());
            in.close();
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return publicKey;
    }

    public Cipher getRSACipher() {
        return rsaCipher;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

}
