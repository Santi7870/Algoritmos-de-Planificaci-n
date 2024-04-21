import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

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

public class PlanificadorSJF {
    public static void main(String[] args) {
        // Datos de los procesos
        Proceso[] procesos = {
            new Proceso("A", 3, 2),
            new Proceso("B", 1, 4),
            new Proceso("C", 3, 0),
            new Proceso("D", 4, 1),
            new Proceso("E", 2, 3)
        };

        // Ordenar los procesos por tiempo de llegada
        Arrays.sort(procesos, Comparator.comparingInt(p -> p.tiempoLlegada));

        // Planificador SJF
        PriorityQueue<Proceso> colaSJF = new PriorityQueue<>(Comparator.comparingInt(p -> p.rafagaCpu));
        int tiempoTotal = 0;
        int procesoIndex = 0;

        while (!colaSJF.isEmpty() || procesoIndex < procesos.length) {
            while (procesoIndex < procesos.length && procesos[procesoIndex].tiempoLlegada <= tiempoTotal) {
                colaSJF.offer(procesos[procesoIndex]);
                procesoIndex++;
            }

            if (!colaSJF.isEmpty()) {
                Proceso procesoActual = colaSJF.poll();
                System.out.println("Ejecutando " + procesoActual.nombre);
                tiempoTotal += procesoActual.rafagaCpu;
            } else {
                tiempoTotal = procesos[procesoIndex].tiempoLlegada;
            }
        }
    }
}
