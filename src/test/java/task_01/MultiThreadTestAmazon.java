package task_01;

public class MultiThreadTestAmazon extends Thread {
    @Override
    public void run() {
        AmazonTest.amazonTest();
    }
}

class Main {
    public static void main(String[] args) {
        MultiThreadTestAmazon t1 = new MultiThreadTestAmazon();
        MultiThreadTestAmazon t2 = new MultiThreadTestAmazon();
        MultiThreadTestAmazon t3 = new MultiThreadTestAmazon();
        MultiThreadTestAmazon t4 = new MultiThreadTestAmazon();
        MultiThreadTestAmazon t5 = new MultiThreadTestAmazon();
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
    }
}