import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

class Proceso {
    String nombre;
    int rafagaCpu;
    int tiempoLlegada;

    public Proceso(String nombre, int rafagaCpu, int tiempoLlegada) {
        this.nombre = nombre;
        this.rafagaCpu = rafagaCpu;
        this.tiempoLlegada = tiempoLlegada;
    }
}

public class PlanificadorFIFO {
    public static void main(String[] args) {
        // Datos de los procesos
        Proceso[] procesos = {
            new Proceso("A", 3, 2),
            new Proceso("B", 1, 4),
            new Proceso("C", 3, 0),
            new Proceso("D", 4, 1),
            new Proceso("E", 2, 3)
        };

        // Planificador FIFO
        Queue<Proceso> cola = new LinkedList<>();
        int tiempoTotal = 0;

        // Ordenar los procesos por tiempo de llegada
        Arrays.sort(procesos, Comparator.comparingInt(p -> p.tiempoLlegada));

        for (Proceso proceso : procesos) {
            // Simulamos el paso del tiempo hasta que llegue el proceso
            while (tiempoTotal < proceso.tiempoLlegada) {
                tiempoTotal++;
            }

            // Ejecutamos el proceso actual
            System.out.println("Ejecutando " + proceso.nombre);
            tiempoTotal += proceso.rafagaCpu;

            // Agregamos el proceso a la cola para mostrar el orden de ejecución
            cola.add(proceso);
        }

        // Mostramos el orden de ejecución
        System.out.println("\nOrden de ejecución:");
        while (!cola.isEmpty()) {
            System.out.print(cola.poll().nombre + " ");
        }
    }
}
