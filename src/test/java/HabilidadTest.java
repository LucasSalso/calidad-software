import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HabilidadTest {

    @Test
    public void testHabilidadAplicaDanoAlOponente() {
        Habilidad habilidad = new Habilidad("Golpe Poderoso", 200, "daño");
        Campeon campeon1 = new Campeon("Garen", "Tanque", 1500, 100, 50, Arrays.asList(habilidad));
        Campeon campeon2 = new Campeon("Ahri", "Mago", 1200, 150, 0, Arrays.asList(new Habilidad("Orbe del Engaño", 250, "daño")));

        // Aplicar el daño de la habilidad de Garen a Ahri
        habilidad.aplicar(campeon1, campeon2);

        // Verificar que los puntos de vida de Ahri han disminuido correctamente
        assertEquals(1200 - 200, campeon2.getPuntosVida()); // Daño 200
    }

    @Test
    public void testHabilidadAplicaCuraASiMismo() {
        Habilidad habilidad = new Habilidad("Recuperacion", 200, "cura");
        Campeon campeon1 = new Campeon("Garen", "Tanque", 1500, 100, 50, Arrays.asList(habilidad));

        // Aplicar Recuperación a Garen
        habilidad.aplicar(campeon1, null);

        // Verificar que los puntos de vida de Garen han aumentado correctamente
        assertEquals(1500 + 200, campeon1.getPuntosVida());
    }

    @Test
    public void testHabilidadAplicaCuraASiMismoSuperandoElMaximoDePuntos() {
        Habilidad habilidad = new Habilidad("Recuperacion", 800, "cura");
        Campeon campeon1 = new Campeon("Garen", "Tanque", 1500, 100, 50, Arrays.asList(habilidad));

        // Aplicar Recuperación a Garen
        habilidad.aplicar(campeon1, null);

        // Verificar que los puntos de vida de Garen han aumentado correctamente
        assertEquals(2000, campeon1.getPuntosVida());
    }

    @Test
    public void testBuffAumentaDefensa() {

        // Crear una habilidad de tipo "buff" que aumente el daño de ataque
        Habilidad buffDefensa = new Habilidad("Fuerza Arcana", 50, "buff_defensa");

        // Crear un campeón con estadísticas base
        Campeon campeon = new Campeon("Ahri", "Mago", 1200, 150, 0, Arrays.asList(buffDefensa));

        // Estadísticas iniciales
        double defensa = campeon.getDefensa();

        // Aplicar la habilidad
        buffDefensa.aplicar(campeon, null);

        // Verificar que el daño de ataque ha aumentado
        assertEquals(defensa + 50, campeon.getDefensa(), "La defensa aumentó correctamente después de aplicar el buff.");
    }

    @Test
    public void testBuffAumentaAtaque() {

        // Crear una habilidad de tipo "buff" que aumente el daño de ataque
        Habilidad buffAtaque = new Habilidad("Fuerza Arcana", 50, "buff_ataque");

        // Crear un campeón con estadísticas base
        Campeon campeon = new Campeon("Ahri", "Mago", 1200, 150, 0, Arrays.asList(buffAtaque));

        // Estadísticas iniciales
        double ataqueInicial = campeon.getDanoAtaque();

        // Aplicar la habilidad
        buffAtaque.aplicar(campeon, null);

        // Verificar que el daño de ataque ha aumentado
        assertEquals(ataqueInicial + 50, campeon.getDanoAtaque(), "El daño de ataque aumentó correctamente después de aplicar el buff.");
    }

    @Test
    public void testBuffInventado() {

        // Crear una habilidad de tipo "buff" que aumente el daño de ataque
        Habilidad buffInventado = new Habilidad("Fuerza Arcana", 50, "buff_inventado");

        // Crear un campeón con estadísticas base
        Campeon campeon = new Campeon("Ahri", "Mago", 1200, 150, 0, Arrays.asList(buffInventado));

        // Estadísticas iniciales
        double ataqueInicial = campeon.getDanoAtaque();
        double defensaInicial = campeon.getDefensa();

        // Aplicar la habilidad
        buffInventado.aplicar(campeon, null);

        // Verificar que el daño de ataque ha aumentado
        assertEquals(ataqueInicial, campeon.getDanoAtaque(), "No ha aumentado el ataque.");
        assertEquals(defensaInicial, campeon.getDefensa(), "No ha aumentado la defensa.");
    }

    @Test
    public void testHabilidadNoExistente() {

        // Crear una habilidad de tipo "buff" que aumente el daño de ataque
        Habilidad habilidadTipoInventada = new Habilidad("Fuerza Arcana", 50, "inventada");

        // Crear un campeón con estadísticas base
        Campeon campeon = new Campeon("Ahri", "Mago", 1200, 150, 0, Arrays.asList(habilidadTipoInventada));

        // Estadísticas iniciales
        double ataqueInicial = campeon.getDanoAtaque();
        double defensaInicial = campeon.getDefensa();
        double vidaInicial = campeon.getPuntosVida();

        // Aplicar la habilidad
        habilidadTipoInventada.aplicar(campeon, null);

        // Verificar que el daño de ataque ha aumentado
        assertEquals(ataqueInicial, campeon.getDanoAtaque(), "No ha aumentado el ataque.");
        assertEquals(defensaInicial, campeon.getDefensa(), "No ha aumentado la defensa.");
        assertEquals(vidaInicial, campeon.getPuntosVida(), "No ha aumentado la vida.");
    }

    @Test
    public void testBuffEsTemporalAtaque() {

        // Crear una habilidad de tipo "buff" que aumente el daño de ataque
        Habilidad buffAtaque = new Habilidad("Fuerza Arcana", 50, "buff_ataque");

        // Crear un campeón con estadísticas base
        Campeon campeon = new Campeon("Ahri", "Mago", 1200, 150, 0, Arrays.asList(buffAtaque));

        // Estadísticas iniciales
        double ataqueInicial = campeon.getDanoAtaque();

        // Aplicar la habilidad
        buffAtaque.aplicar(campeon, null);

        // Simular 3 turnos
        for (int i = 0; i < 3; i++) {
            campeon.procesarTurno();
        }

        // Verificar que el buff ha expirado
        assertEquals(ataqueInicial, campeon.getDanoAtaque(), "El buff expiró correctamente después de 3 turnos.");
    }

    @Test
    public void testBuffEsTemporalDefensa() {

        // Crear una habilidad de tipo "buff" que aumente el daño de ataque
        Habilidad buffDefensa = new Habilidad("Fuerza Arcana", 50, "buff_defensa");

        // Crear un campeón con estadísticas base
        Campeon campeon = new Campeon("Ahri", "Mago", 1200, 150, 0, Arrays.asList(buffDefensa));

        // Estadísticas iniciales
        double defensaInicial = campeon.getDefensa();

        // Aplicar la habilidad
        buffDefensa.aplicar(campeon, null);

        // Simular 3 turnos
        for (int i = 0; i < 3; i++) {
            campeon.procesarTurno();
        }

        // Verificar que el buff ha expirado
        assertEquals(defensaInicial, campeon.getDefensa(), "El buff expiró correctamente después de 3 turnos.");
    }


}
