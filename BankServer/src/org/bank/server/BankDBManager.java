package org.bank.server;

public class BankDBManager {

    public static String getCustomerInfo(final long cardNumber) {
        return "balance=432434.21:fname=michael:lname=sanders";
    }

    public static boolean clientCardExists(final long l) {
        return true;
    }

}
