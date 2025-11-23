package readerwriter.monitor;

public class ReaderWriterMonitorImpl implements ReaderWriterMonitor {
    private int activeReaders = 0;
    private int waitingReaders = 0;
    private boolean writerActive = false;
    private int waitingWriters = 0;

    // Estadísticas
    private int totalReads = 0;
    private int totalWrites = 0;

    @Override
    public synchronized void startRead() throws InterruptedException {
        waitingReaders++;

        // Esperar mientras haya un escritor activo
        while (writerActive) {
            wait();
        }

        waitingReaders--;
        activeReaders++;
        totalReads++;

        System.out.println("[MONITOR] Lector entrando. Lectores activos: " + activeReaders);
    }


    @Override
    public synchronized void endRead() {
        activeReaders--;

        System.out.println("[MONITOR] Lector saliendo. Lectores activos: " + activeReaders);

        // Si no hay más lectores activos, notificar a todos (escritores pueden entrar)
        if (activeReaders == 0) {
            notifyAll();
        }
    }

    @Override
    public synchronized void startWrite() throws InterruptedException {
        waitingWriters++;

        while (activeReaders > 0 || writerActive || waitingReaders > 0) {
            wait();
        }

        waitingWriters--;
        writerActive = true;
        totalWrites++;

        System.out.println("[MONITOR] Escritor entrando.");
    }

    @Override
    public synchronized void endWrite() {
        writerActive = false;

        System.out.println("[MONITOR] Escritor saliendo.");

        // Notificar a todos (lectores tienen prioridad por el orden de la condición en startRead)
        notifyAll();
    }


    @Override
    public synchronized String getStatistics() {
        return String.format(
                "Estadísticas del Monitor:\n" +
                        "  Total de lecturas: %d\n" +
                        "  Total de escrituras: %d\n" +
                        "  Lectores activos: %d\n" +
                        "  Lectores esperando: %d\n" +
                        "  Escritores esperando: %d\n" +
                        "  Escritor activo: %s",
                totalReads, totalWrites, activeReaders, waitingReaders, waitingWriters, writerActive
        );
    }
}