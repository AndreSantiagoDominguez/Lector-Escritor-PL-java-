package readerwriter.config;

public class SimulationConfig {
    private final int numReaders;
    private final int numWriters;

    public SimulationConfig(int numReaders, int numWriters) {
        if (numReaders <= 0 || numWriters <= 0) {
            throw new IllegalArgumentException("Todos los parámetros deben ser positivos");
        }

        this.numReaders = numReaders;
        this.numWriters = numWriters;
    }

    public int getNumReaders() {
        return numReaders;
    }

    public int getNumWriters() {
        return numWriters;
    }
}