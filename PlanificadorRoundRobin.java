import java.util.*;

class Proceso {
    String nombre;
    int rafagaCpu;
    int tiempoLlegada;
    int tiempoEspera;
    int tiempoRetorno;
    int prioridad;

    public Proceso(String nombre, int rafagaCpu, int tiempoLlegada, int prioridad) {
        this.nombre = nombre;
        this.rafagaCpu = rafagaCpu;
        this.tiempoLlegada = tiempoLlegada;
        this.tiempoEspera = 0;
        this.tiempoRetorno = 0;
        this.prioridad = prioridad;
    }
}

public class PlanificadorRoundRobin {
    public static void main(String[] args) {
        // Datos de los procesos
        Proceso[] procesos = {
                new Proceso("A", 3, 2, 2),
                new Proceso("B", 1, 4, 3),
                new Proceso("C", 3, 0, 1),
                new Proceso("D", 4, 1, 3),
                new Proceso("E", 2, 3, 4),
                new Proceso("D", 4, 5, 3)  // Proceso D repetido
        };

        // Ordenar los procesos por tiempo de llegada
        Arrays.sort(procesos, Comparator.comparingInt(p -> p.tiempoLlegada));

        Queue<Proceso> cola = new LinkedList<>();
        int tiempoTotal = 0;
        int quantum = 3;

        while (!Arrays.stream(procesos).allMatch(p -> p.rafagaCpu <= 0)) {
            for (Proceso proceso : procesos) {
                if (proceso.tiempoLlegada <= tiempoTotal && proceso.rafagaCpu > 0) {
                    cola.add(proceso);
                }
            }

            while (!cola.isEmpty()) {
                Proceso proceso = cola.poll();

                if (proceso.rafagaCpu > quantum) {
                    tiempoTotal += quantum;
                    proceso.rafagaCpu -= quantum;
                    proceso.tiempoEspera += tiempoTotal - proceso.tiempoLlegada - quantum;
                    cola.add(proceso);
                } else {
                    tiempoTotal += proceso.rafagaCpu;
                    proceso.tiempoEspera += tiempoTotal - proceso.tiempoLlegada - proceso.rafagaCpu;
                    proceso.rafagaCpu = 0;
                    proceso.tiempoRetorno = tiempoTotal - proceso.tiempoLlegada;
                }
            }
        }

        // Mostramos el orden de ejecución
        System.out.println("Orden de ejecución:");
        Arrays.stream(procesos).forEach(p -> System.out.print(p.nombre + " "));

        // Calculamos y mostramos el tiempo medio de espera y el tiempo medio de retorno
        double tiempoEsperaTotal = Arrays.stream(procesos).mapToInt(p -> p.tiempoEspera).sum();
        double tiempoRetornoTotal = Arrays.stream(procesos).mapToInt(p -> p.tiempoRetorno).sum();
        double tiempoMedioEspera = (double) tiempoEsperaTotal / procesos.length;
        double tiempoMedioRetorno = (double) tiempoRetornoTotal / procesos.length;
        System.out.println("\nTiempo medio de espera: " + tiempoMedioEspera);
        System.out.println("Tiempo medio de retorno: " + tiempoMedioRetorno);
    }
}
