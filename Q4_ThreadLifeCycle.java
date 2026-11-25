class ScannerStation extends Thread {
    private final int scripts;

    ScannerStation(String name, int scripts) {
        super(name);
        this.scripts = scripts;
    }

    public void run() {
        try {
            for (int i = 1; i <= scripts; i++) {
                Thread.sleep(200);
            }
            System.out.println(getName() + " scanned " + scripts + " scripts");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class RunnableStation implements Runnable {
    private final String name;
    private final int scripts;

    RunnableStation(String name, int scripts) {
        this.name = name;
        this.scripts = scripts;
    }

    public void run() {
        try {
            for (int i = 1; i <= scripts; i++) {
                Thread.sleep(200);
            }
            System.out.println(name + " scanned " + scripts + " scripts");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class Q4_ThreadLifeCycle {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new ScannerStation("Station 1", 5);
        Thread t2 = new Thread(new RunnableStation("Station 2", 6));
        Thread t3 = new Thread(new RunnableStation("Station 3", 4));
        Thread t4 = new Thread(new RunnableStation("Station 4", 7));

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        System.out.println("Station 1 alive: " + t1.isAlive());

        t1.join();
        t2.join();
        t3.join();
        t4.join();

        System.out.println("All stations completed");
    }
}
