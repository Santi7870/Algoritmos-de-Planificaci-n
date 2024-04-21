import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

class Proceso {
    String nombre;
    int rafagaCpu;
    int tiempoLlegada;
    int tiempoEspera;
    int tiempoRetorno;

    public Proceso(String nombre, int rafagaCpu, int tiempoLlegada) {
        this.nombre = nombre;
        this.rafagaCpu = rafagaCpu;
        this.tiempoLlegada = tiempoLlegada;
        this.tiempoEspera = 0;
        this.tiempoRetorno = 0;
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

        // Planificador SJF
        PriorityQueue<Proceso> colaSJF = new PriorityQueue<>(Comparator.comparingInt(p -> p.rafagaCpu));
        int tiempoTotal = 0;
        double tiempoEsperaTotal = 0;
        double tiempoRetornoTotal = 0;
        int procesoIndex = 0;

        // Ordenar los procesos por tiempo de llegada
        Arrays.sort(procesos, Comparator.comparingInt(p -> p.tiempoLlegada));

        while (!colaSJF.isEmpty() || procesoIndex < procesos.length) {
            while (procesoIndex < procesos.length && procesos[procesoIndex].tiempoLlegada <= tiempoTotal) {
                colaSJF.offer(procesos[procesoIndex]);
                procesoIndex++;
            }

            if (!colaSJF.isEmpty()) {
                Proceso procesoActual = colaSJF.poll();
                System.out.println("Ejecutando " + procesoActual.nombre);

                // Calculamos el tiempo de espera del proceso actual
                procesoActual.tiempoEspera = tiempoTotal - procesoActual.tiempoLlegada;
                tiempoEsperaTotal += procesoActual.tiempoEspera;

                // Ejecutamos el proceso actual y actualizamos el tiempo total
                tiempoTotal += procesoActual.rafagaCpu;

                // Calculamos el tiempo de retorno del proceso actual
                procesoActual.tiempoRetorno = tiempoTotal - procesoActual.tiempoLlegada;
                tiempoRetornoTotal += procesoActual.tiempoRetorno+2;
            } else {
                tiempoTotal = procesos[procesoIndex].tiempoLlegada;
            }
        }


        // Calculamos y mostramos el tiempo medio de espera y el tiempo medio de retorno
        double tiempoMedioEspera = tiempoEsperaTotal / procesos.length;
        double tiempoMedioRetorno = tiempoRetornoTotal / procesos.length;
        System.out.println("\nTiempo medio de espera: " + tiempoMedioEspera);
        System.out.println("Tiempo medio de retorno: " + tiempoMedioRetorno);
    }
}
