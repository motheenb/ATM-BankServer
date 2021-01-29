package org.bank.atm;

import javax.swing.*;
import java.awt.*;

public class ATM extends JFrame {

    private Image img;
    private Graphics gfx;
    private final ScreenRender screenRender = new ScreenRender(this);
    //
    private final ATMConn atmConn = new ATMConn(this);
    //
    public static State STATE = State.ConnectToServer;

    public ATM() {
        setTitle("BaigBank ATM - Motheen Baig");
        setSize(500, 500);
        setResizable(false);
        setVisible(true);
        img = createImage(500, 500);
        gfx = img.getGraphics();
        atmConn.connectToServer();
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

}
