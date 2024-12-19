import java.util.Random;

public class Combate {

    private Campeon campeon_1;
    private Campeon campeon_2;
    private Random random; // Dependencia inyectada para números aleatorios

    // Constructor con inyección de Random
    public Combate(Campeon campeon_1, Campeon campeon_2, Random random) {
        this.campeon_1 = campeon_1;
        this.campeon_2 = campeon_2;
        this.random = random;
    }

    // Métodos
    public void iniciar() {
        int turno = 1;
        System.out.println("¡El combate entre " + campeon_1.getNombre() + " y " + campeon_2.getNombre() + " comienza!");

        while (campeon_1.estaVivo() && campeon_2.estaVivo()) {
            System.out.println("\nTurno " + turno);
            if (turno % 2 == 1) {
                // Turno del campeón 1
                if (random.nextBoolean()) {
                    campeon_1.atacar(campeon_2);
                } else {
                    usarHabilidadAleatoria(campeon_1, campeon_2);
                }
            } else {
                // Turno del campeón 2
                if (random.nextBoolean()) {
                    campeon_2.atacar(campeon_1);
                } else {
                    usarHabilidadAleatoria(campeon_2, campeon_1);
                }
            }

            // Procesar buffs temporales al final de cada turno
            campeon_1.procesarTurno();
            campeon_2.procesarTurno();

            turno++;
        }
        detener();
    }

    public void detener() {
        System.out.println("\nEl combate ha terminado.");
        mostrarGanador();
    }

    public void mostrarGanador() {
        if (campeon_1.estaVivo()) {
            System.out.println("¡El ganador es " + campeon_1.getNombre() + "!");
        } else {
            System.out.println("¡El ganador es " + campeon_2.getNombre() + "!");
        }
    }

    //Metodos auxiliares
    private void usarHabilidadAleatoria(Campeon atacante, Campeon objetivo) {
        if (!atacante.getHabilidades().isEmpty()) {
            Habilidad habilidad = atacante.getHabilidades().get(random.nextInt(atacante.getHabilidades().size()));
            atacante.usarHabilidad(habilidad.getNombre(), objetivo);
        } else {
            System.out.println(atacante.getNombre() + " no tiene habilidades disponibles.");
        }
    }
}
