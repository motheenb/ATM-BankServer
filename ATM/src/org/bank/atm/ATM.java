package org.bank.atm;

import javax.swing.*;
import java.awt.*;

/**
 * @author Motheen Baig
 */
public class ATM extends JFrame {

    private final InputHandler inputHandler = new InputHandler(this);
    //
    private Image img;
    private Graphics gfx;
    private final ScreenRender screenRender = new ScreenRender(this);
    //
    private ATMConn atmConn;
    //
    public static State STATE = State.ConnectToServer;
    //
    private int clientID;
    //
    private String cardNumber = "", cardPIN = "", fName = "", lName = "";
    private float balance;
    //
    private String errorMessage = "";

    public ATM() {
        setTitle("BaigBank ATM - Motheen Baig");
        setSize(500, 500);
        setResizable(false);
        setVisible(true);
        addKeyListener(inputHandler);
        addMouseListener(inputHandler);
        addMouseMotionListener(inputHandler);
        img = createImage(500, 500);
        gfx = img.getGraphics();
        if (STATE.equals(State.ConnectToServer)) {
            atmConn = new ATMConn(this);
        }
    }

    public void paint(final Graphics g) {
        if (img == null) {
            return;
        }
        gfx.clearRect(0, 0, 500, 500);
        screenRender.paint(gfx);
        g.drawImage(img, 0, 0, this);
        repaint();
    }

    public ATMConn getATMConn() {
        return atmConn;
    }

    public Graphics getGfx() {
        return gfx;
    }

    public Image getImg() {
        return img;
    }

    public ScreenRender getScreenRender() {
        return screenRender;
    }

    public static void main(final String...args) {
        new ATM();
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(final int clientID) {
        this.clientID = clientID;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(final String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardPIN() {
        return cardPIN;
    }

    public void setCardPIN(final String cardPIN) {
        this.cardPIN = cardPIN;
    }

    public InputHandler getInputHandler() {
        return inputHandler;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(final String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(final float balance) {
        this.balance = balance;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(final String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(final String lName) {
        this.lName = lName;
    }
}
