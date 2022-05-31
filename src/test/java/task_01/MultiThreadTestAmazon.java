package task_01;

public class MultiThreadTestAmazon implements Runnable {
    @Override
    public void run() {
        AmazonTest.amazonTest();
    }

    public static void main(String[] args) {
        MultiThreadTestAmazon t1 = new MultiThreadTestAmazon();
        MultiThreadTestAmazon t2 = new MultiThreadTestAmazon();
        //MultiThreadTestAmazon t3 = new MultiThreadTestAmazon();
        //MultiThreadTestAmazon t4 = new MultiThreadTestAmazon();
        //MultiThreadTestAmazon t5 = new MultiThreadTestAmazon();
        t1.run();
        t2.run();
    }
}
