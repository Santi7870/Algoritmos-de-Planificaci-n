import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

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
        double tiempoEsperaTotal = 0;
        double tiempoRetornoTotal = 0;

        // Ordenar los procesos por tiempo de llegada
        Arrays.sort(procesos, Comparator.comparingInt(p -> p.tiempoLlegada));

        for (Proceso proceso : procesos) {
            // Simulamos el paso del tiempo hasta que llegue el proceso
            while (tiempoTotal < proceso.tiempoLlegada) {
                tiempoTotal++;
            }

            // Calculamos el tiempo de espera del proceso
            proceso.tiempoEspera = tiempoTotal - proceso.tiempoLlegada;

            // Ejecutamos el proceso actual
            tiempoTotal += proceso.rafagaCpu;

            // Calculamos el tiempo de retorno del proceso
            proceso.tiempoRetorno = tiempoTotal - proceso.tiempoLlegada+2;

            // Sumamos al tiempo total de espera y retorno
            tiempoEsperaTotal += proceso.tiempoEspera;
            tiempoRetornoTotal += proceso.tiempoRetorno;

            // Agregamos el proceso a la cola para mostrar el orden de ejecución
            cola.add(proceso);
        }

        // Mostramos el orden de ejecución
        System.out.println("Orden de ejecución:");
        while (!cola.isEmpty()) {
            System.out.print(cola.poll().nombre + " ");
        }

        // Calculamos y mostramos el tiempo medio de espera y el tiempo medio de retorno
        double tiempoMedioEspera = tiempoEsperaTotal / procesos.length;
        double tiempoMedioRetorno = tiempoRetornoTotal / procesos.length;
        System.out.println("\nTiempo medio de espera: " + tiempoMedioEspera);
        System.out.println("Tiempo medio de retorno: " + tiempoMedioRetorno);
    }
}
