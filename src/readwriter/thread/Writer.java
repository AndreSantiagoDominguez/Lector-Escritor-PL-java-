package readerwriter.thread;

import readerwriter.monitor.ReaderWriterMonitor;
import readerwriter.resource.SharedResource;

import java.util.Random;

public class Writer implements Runnable {
    private final int id;
    private final SharedResource resource;
    private final ReaderWriterMonitor monitor;
    private final Random random;
    private int writeCount = 0;

    public Writer(int id, SharedResource resource, ReaderWriterMonitor monitor) {
        this.id = id;
        this.resource = resource;
        this.monitor = monitor;
        this.random = new Random();
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(random.nextInt(2000) + 1000);

                monitor.startWrite();

                try {
                    writeCount++;
                    String newContent = String.format(
                            "Contenido del Escritor-%d (escritura #%d)",
                            id, writeCount
                    );

                    resource.write(newContent);

                    System.out.println(String.format(
                            "    [ESCRITOR-%d] Escribiendo: '%s' (versión %d)",
                            id, newContent, resource.getVersion()
                    ));

                    Thread.sleep(random.nextInt(700) + 300);

                } finally {
                    monitor.endWrite();
                }

                Thread.sleep(random.nextInt(1000));
            }
        } catch (InterruptedException e) {
            System.out.println(String.format("[ESCRITOR-%d] Terminando...", id));
            Thread.currentThread().interrupt();
        }
    }
}