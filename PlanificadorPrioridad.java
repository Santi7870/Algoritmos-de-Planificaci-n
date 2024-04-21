import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

class Proceso {
    String nombre;
    int rafagaCpu;
    int tiempoLlegada;
    int prioridad;
    int tiempoEspera;
    int tiempoRetorno;

    public Proceso(String nombre, int rafagaCpu, int tiempoLlegada, int prioridad) {
        this.nombre = nombre;
        this.rafagaCpu = rafagaCpu;
        this.tiempoLlegada = tiempoLlegada;
        this.prioridad = prioridad;
        this.tiempoEspera = 0;
        this.tiempoRetorno = 0;
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
        int tiempoTotal = 0;
        int procesoIndex = 0;

        while (!colaPrioridad.isEmpty() || procesoIndex < procesos.length) {
            while (procesoIndex < procesos.length && procesos[procesoIndex].tiempoLlegada <= tiempoTotal) {
                colaPrioridad.offer(procesos[procesoIndex]);
                procesoIndex++;
            }

            if (!colaPrioridad.isEmpty()) {
                Proceso procesoActual = colaPrioridad.poll();
                System.out.println("Ejecutando " + procesoActual.nombre);
                tiempoTotal += procesoActual.rafagaCpu;

                // Calculamos el tiempo de espera del proceso actual
                procesoActual.tiempoEspera = tiempoTotal - procesoActual.rafagaCpu - procesoActual.tiempoLlegada;

                // Calculamos el tiempo de retorno del proceso actual
                procesoActual.tiempoRetorno = tiempoTotal - procesoActual.tiempoLlegada+2;

            } else {
                tiempoTotal = procesos[procesoIndex].tiempoLlegada;
            }
        }

        // Calculamos y mostramos el tiempo medio de espera y el tiempo medio de retorno
        double tiempoMedioEspera = 0;
        double tiempoMedioRetorno = 0;
        for (Proceso proceso : procesos) {
            tiempoMedioEspera += proceso.tiempoEspera;
            tiempoMedioRetorno += proceso.tiempoRetorno;
        }
        tiempoMedioEspera /= procesos.length;
        tiempoMedioRetorno /= procesos.length;

        System.out.println("Tiempo medio de espera: " + tiempoMedioEspera);
        System.out.println("Tiempo medio de retorno: " + tiempoMedioRetorno);
    }
}
