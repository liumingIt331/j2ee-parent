package com.liuming.mej2ee.luban.nio.reactor.masterandslave;

public class Main {
    public static void main(String[] args) {
        try {
            TCPReactor tcpReactor = new TCPReactor(1333);
            new Thread(tcpReactor).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
