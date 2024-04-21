import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

class Proceso {
    String nombre;
    int rafagaCpu;
    int tiempoLlegada;
    int prioridad;

    public Proceso(String nombre, int rafagaCpu, int tiempoLlegada, int prioridad) {
        this.nombre = nombre;
        this.rafagaCpu = rafagaCpu;
        this.tiempoLlegada = tiempoLlegada;
        this.prioridad = prioridad;
    }
}

public class PlanificadorPrioridad {
    public static void main(String[] args) {
        // Datos de los procesos
        Proceso[] procesos = {
            new Proceso("A", 3, 2, 2),
            new Proceso("B", 1, 4, 3),
            new Proceso("C", 3, 0, 1),
            new Proceso("D", 4, 1, 3),
            new Proceso("E", 2, 3, 4)
        };

        // Ordenar los procesos por tiempo de llegada
        Arrays.sort(procesos, Comparator.comparingInt(p -> p.tiempoLlegada));

        // Planificador por prioridad
        PriorityQueue<Proceso> colaPrioridad = new PriorityQueue<>(Comparator.comparingInt(p -> p.prioridad));
        ArrayList<Proceso> ejecutados = new ArrayList<>();
        int tiempoTotal = 0;
        int procesoIndex = 0;

        while (!colaPrioridad.isEmpty() || procesoIndex < procesos.length) {
            while (procesoIndex < procesos.length && procesos[procesoIndex].tiempoLlegada <= tiempoTotal) {
                colaPrioridad.offer(procesos[procesoIndex]);
                procesoIndex++;
            }

            if (!colaPrioridad.isEmpty()) {
                Proceso procesoActual = colaPrioridad.poll();
                ejecutados.add(procesoActual);
                tiempoTotal += procesoActual.rafagaCpu;
            } else {
                tiempoTotal = procesos[procesoIndex].tiempoLlegada;
            }
        }

        // Imprimir el orden de ejecución
        System.out.println("Orden de ejecución:");
        for (Proceso proceso : ejecutados) {
            System.out.println(proceso.nombre);
        }
    }
}
