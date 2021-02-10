package org.bank.server.database;

/**
 * @author Motheen Baig
 */
public final class BankDBManager {

    private static volatile BankDBManager instance;
    private static final Object lock = new Object();

    private BankDBManager() {

    }

    public static BankDBManager getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new BankDBManager();
                }
            }
        }
        return instance;
    }

    public String getCustomerInfo(final long cardNumber) {
        return "balance=432.15:fname=michael:lname=sanders";
    }

    public static boolean clientCardExists(final long l) {
        return true;
    }

}
