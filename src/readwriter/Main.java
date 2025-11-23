package readerwriter;

import readerwriter.config.SimulationConfig;
import readerwriter.monitor.ReaderWriterMonitor;
import readerwriter.monitor.ReaderWriterMonitorImpl;
import readerwriter.resource.SharedResource;
import readerwriter.resource.SharedResourceImpl;
import readerwriter.thread.Reader;
import readerwriter.thread.Writer;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Sistema Lector-Escritor con Prioridad a Lectores ===");
        System.out.println("Presiona ENTER para detener la simulación...\n");

        SimulationConfig config = new SimulationConfig(5, 3);
        SharedResource sharedResource = new SharedResourceImpl();
        ReaderWriterMonitor monitor = new ReaderWriterMonitorImpl();
        List<Thread> threads = new ArrayList<>();

        // Crear hilos lectores
        for (int i = 0; i < config.getNumReaders(); i++) {
            Reader reader = new Reader(i + 1, sharedResource, monitor);
            Thread thread = new Thread(reader);
            threads.add(thread);
            thread.start();
        }

        // Crear hilos escritores
        for (int i = 0; i < config.getNumWriters(); i++) {
            Writer writer = new Writer(i + 1, sharedResource, monitor);
            Thread thread = new Thread(writer);
            threads.add(thread);
            thread.start();
        }

        // Crear hilo para mostrar estadísticas periódicamente
        Thread statsThread = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    Thread.sleep(5000);
                    System.out.println("\n" + monitor.getStatistics() + "\n");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        statsThread.start();
        threads.add(statsThread);

        try {
            System.in.read();

            System.out.println("\n=== Finalizando simulación ===");
            System.out.println(monitor.getStatistics());

            for (Thread thread : threads)


            {
                thread.interrupt();
            }

            for (Thread thread : threads) {
                thread.join(2000); // Timeout de 2 segundos por hilo
            }

            System.out.println("\n=== Simulación finalizada ===");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Error en la simulación: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error al leer entrada: " + e.getMessage());
        }
    }
}