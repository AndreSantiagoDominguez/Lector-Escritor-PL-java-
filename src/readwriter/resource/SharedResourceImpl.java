package readerwriter.resource;

public class SharedResourceImpl implements SharedResource {
    private String content = "Contenido inicial";
    private int version = 0;

    @Override
    public String read() {
        return content;
    }

    @Override
    public void write(String content) {
        this.content = content;
        this.version++;
    }

    @Override
    public int getVersion() {
        return version;
    }
}