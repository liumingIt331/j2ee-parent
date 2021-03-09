package com.liuming.mej2ee.luban.nio.reactor.many;

public class Main {
    public static void main(String[] args) {
        try {
            TCPReactor tcpReactor = new TCPReactor(1333);
            tcpReactor.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
