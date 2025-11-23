package readerwriter.thread;

import readerwriter.monitor.ReaderWriterMonitor;
import readerwriter.resource.SharedResource;

import java.util.Random;

public class Reader implements Runnable {
    private final int id;
    private final SharedResource resource;
    private final ReaderWriterMonitor monitor;
    private final Random random;

    public Reader(int id, SharedResource resource, ReaderWriterMonitor monitor) {
        this.id = id;
        this.resource = resource;
        this.monitor = monitor;
        this.random = new Random();
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(random.nextInt(1000) + 500);

                monitor.startRead();

                try {
                    String content = resource.read();
                    int version = resource.getVersion();

                    System.out.println(String.format(
                            "  [LECTOR-%d] Leyendo: '%s' (versión %d)",
                            id, content, version
                    ));

                    Thread.sleep(random.nextInt(500) + 200);

                } finally {
                    monitor.endRead();
                }

                Thread.sleep(random.nextInt(500));
            }
        } catch (InterruptedException e) {
            System.out.println(String.format("[LECTOR-%d] Terminando...", id));
            Thread.currentThread().interrupt();
        }
    }
}