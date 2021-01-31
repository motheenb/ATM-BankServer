package org.bank.atm.handlers;

import org.bank.atm.ATM;

import java.awt.*;

/**
 * @author Motheen Baig
 */
public class ScreenHandler {

    private Graphics2D g2d;
    private final ATM atm;

    public ScreenHandler(final ATM atm) {
        this.atm = atm;
    }

    public void paint(final Graphics g) {
        g2d = (Graphics2D) g.create();
        g2d.fillRect(0, 0, 500, 500);
        handleErrorMessage(g2d);
        switch (ATM.STATE) {
            case ConnectToServer -> {
                g2d.setColor(Color.GREEN);
                drawCenteredString("Connecting to server...", 500, 500, 0, g2d);
            }
            case EnterCard -> {
                g2d.setColor(Color.GREEN);
                drawCenteredString("Enter your card number:", 500, 500, 0, g2d);
                g2d.setColor(Color.YELLOW);
                drawCenteredString(atm.getCardNumber() + "*", 500, 500, 25, g2d);
            }
            case EnterPIN -> {
                g2d.setColor(Color.GREEN);
                drawCenteredString("Enter your card PIN number:", 500, 500, 0, g2d);
                g2d.setColor(Color.YELLOW);
                drawCenteredString(getBlockedPIN() + "*", 500, 500, 25, g2d);
            }
            case MainMenu -> {
                g2d.setColor(Color.GREEN);
                drawCenteredString("Welcome " + atm.getfName() + " " + atm.getlName() + ", choose an option from below.", 500, 500, 0, g2d);
                g2d.setColor(Color.YELLOW);
                drawCenteredString("Account Balance: " + atm.getBalance(), 500, 500, 25, g2d);
                drawCenteredString("1. Deposit", 500, 500, 50, g2d);
                drawCenteredString("2. Withdraw", 500, 500, 75, g2d);
            }
        }
    }

    public String getBlockedPIN() {
        String asteriskPIN = "";
        for (int i = 0; i < atm.getCardPIN().length(); i++) {
            asteriskPIN += "*";
        }
        return asteriskPIN;
    }

    public void handleErrorMessage(final Graphics2D g2d) {
        if (atm.getErrorMessage() != "") {
            g2d.setColor(Color.RED);
            drawCenteredString(atm.getErrorMessage(), 500, 500, -25, g2d);
        }
    }

    public void drawCenteredString(final String s, final int w, final int h, final int yOffset, final Graphics g) {
        final FontMetrics fm = g.getFontMetrics();
        final int x = (w - fm.stringWidth(s)) / 2;
        final int y = (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2) + yOffset;
        g.drawString(s, x, y);
    }

    public Graphics2D getG2D() {
        return g2d;
    }

    public ATM getAtm() {
        return atm;
    }
}
