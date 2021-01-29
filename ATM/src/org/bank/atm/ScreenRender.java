package org.bank.atm;

import java.awt.*;

public class ScreenRender {

    private Graphics2D g2d;
    private final ATM atm;

    public ScreenRender(final ATM atm) {
        this.atm = atm;
    }

    public void paint(final Graphics g) {
        g2d = (Graphics2D) g.create();
        g2d.fillRect(0, 0, 500, 500);
        switch (ATM.STATE) {
            case ConnectToServer -> {
                //g2d.setColor(Color.GREEN);
                //drawCenteredString("Connecting to server...", 500, 500, 0, g2d);
            }
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
