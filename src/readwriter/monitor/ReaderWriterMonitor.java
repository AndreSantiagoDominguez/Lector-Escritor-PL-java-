package readerwriter.monitor;


public interface ReaderWriterMonitor {
    void startRead() throws InterruptedException;

    void endRead();

    void startWrite() throws InterruptedException;

    void endWrite();

    String getStatistics();
}