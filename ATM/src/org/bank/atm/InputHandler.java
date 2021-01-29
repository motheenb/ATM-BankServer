package org.bank.atm;

import java.awt.event.*;
import java.io.IOException;

public class InputHandler implements MouseListener, MouseMotionListener, KeyListener {

    private final ATM atm;

    public InputHandler(final ATM atm) {
        this.atm = atm;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        final int keyCode = e.getKeyCode();
        final char keyChar = e.getKeyChar();
        if (ATM.STATE.equals(State.EnterCard)) {
            if (keyCode > 47 && keyCode < 58) {
                if (atm.getCardNumber().length() < 16) {
                    atm.setCardNumber(atm.getCardNumber() + keyChar);
                } else
                    return;
            } else if (keyCode == 8) {
                atm.setCardNumber(removeLastChar(atm.getCardNumber()));
            } else if (keyCode == 10) {
                if (atm.getCardNumber().length() > 15) {
                    final String clientMessage = "1:type=ATM:id=" + atm.getClientID() + ":cardnumber=" + atm.getCardNumber();
                    atm.getATMConn().writeToServer(clientMessage);
                }
            }
        } else if (ATM.STATE.equals(State.EnterPIN)) {
            if (keyCode > 47 && keyCode < 58) {
                if (atm.getCardPIN().length() < 4) {
                    atm.setCardPIN(atm.getCardPIN() + keyChar);
                } else
                    return;
            } else if (keyCode == 8) {
                atm.setCardPIN(removeLastChar(atm.getCardPIN()));
            } else if (keyCode == 10) {
                if (atm.getCardPIN().length() > 3) {
                    final String clientMessage = "2:type=ATM:id=" + atm.getClientID() + ":cardnumber=" + atm.getCardNumber() + ":card_pin=" + atm.getCardPIN();
                    atm.getATMConn().writeToServer(clientMessage);
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    public String removeLastChar(String str) {
        if (str != null && str.length() > 0) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    public ATM getATM() {
        return atm;
    }

}
