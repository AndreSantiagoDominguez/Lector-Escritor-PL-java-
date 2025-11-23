
package readerwriter.resource;

public interface SharedResource {
    String read();
    void write(String content);
    int getVersion();
}