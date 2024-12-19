import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class CampeonTest {

    @Test
    public void testCampeonCreadoConAtributosCorrectos() {
        Habilidad habilidad = new Habilidad("Golpe Poderoso", 100, "daño");
        Campeon campeon = new Campeon("Garen", "Tanque", 1500, 100, 50, Arrays.asList(habilidad));

        // Verificar que el campeón tiene los atributos correctos
        assertEquals("Garen", campeon.getNombre());
        assertEquals(1500, campeon.getPuntosVida());
        assertEquals(100, campeon.getDanoAtaque());
        assertEquals(50, campeon.getDefensa());
        assertEquals("Tanque", campeon.getRol());
        assertEquals(1, campeon.getHabilidades().size());
        Habilidad h = campeon.getHabilidades().get(0);
        assertEquals("Golpe Poderoso", h.getNombre());
        assertEquals(100, h.getDano());
        assertEquals("danio", h.getTipo());
    }

    @Test
    public void testAtacarReducePuntosDeVidaDelOponente() {
        Habilidad habilidad = new Habilidad("Golpe Poderoso", 100, "daño");
        Campeon campeon1 = new Campeon("Garen", "Tanque", 1500, 100, 50, Arrays.asList(habilidad));
        Campeon campeon2 = new Campeon("Ahri", "Mago", 1200, 150, 0, Arrays.asList(new Habilidad("Orbe del Engaño", 250, "daño")));

        campeon1.atacar(campeon2); // Garen ataca a Ahri

        // Verificar que los puntos de vida de Ahri han disminuido
        assertEquals(1200 - 100, campeon2.getPuntosVida());
    }

    @Test
    public void testRecibirDanoConsideraLaDefensa() {
        Habilidad habilidad = new Habilidad("Golpe Poderoso", 200, "daño");
        Campeon campeon1 = new Campeon("Garen", "Tanque", 1500, 100, 50, Arrays.asList(habilidad));
        Campeon campeon2 = new Campeon("Ahri", "Mago", 1200, 150, 30, Arrays.asList(new Habilidad("Orbe del Engaño", 250, "daño")));

        campeon2.recibirDano(campeon1.getDanoAtaque()); // Ahri recibe daño de Garen

        // Verificar que el daño recibido tiene en cuenta la defensa
        assertEquals(1200 - (100 - 30), campeon2.getPuntosVida()); // 100 de daño menos la defensa de 30
    }

    @Test
    public void testEstaVivoDevuelveFalseCuandoPuntosDeVidaSon0OMenos() {
        Habilidad habilidad = new Habilidad("Golpe Poderoso", 100, "daño");
        Campeon campeon = new Campeon("Garen", "Tanque", 100, 100, 0, Arrays.asList(habilidad));

        // Simular que Garen recibe suficiente daño para quedar con 0 puntos de vida
        campeon.recibirDano(100);

        // Verificar que el método estaVivo() devuelve false cuando los puntos de vida son 0 o menos
        assertFalse(campeon.estaVivo());
    }

    @Test
    public void testEstaVivoDevuelveTrueCuandoPuntosDeVidaSonMayoresQue0() {
        Habilidad habilidad = new Habilidad("Golpe Poderoso", 100, "daño");
        Campeon campeon = new Campeon("Garen", "Tanque", 100, 100, 0, Arrays.asList(habilidad));

        // Simular que Garen recibe daño pero aún tiene vida
        campeon.recibirDano(50);

        // Verificar que el método estaVivo() devuelve true cuando el campeón sigue con vida
        assertTrue(campeon.estaVivo());
    }

    @Test
    public void testUsarHabilidad() {
        Habilidad habilidad = new Habilidad("Golpe Poderoso", 100, "daño");
        Campeon campeon = new Campeon("Garen", "Tanque", 100, 100, 0, Arrays.asList(habilidad));
        Campeon campeon2 = new Campeon("Ahri", "Mago", 1200, 150, 0, Arrays.asList(new Habilidad("Orbe del Engaño", 250, "daño")));

        // Simular que Garen usa Golpe Poderoso contra Ahri
        campeon.usarHabilidad(habilidad.getNombre(), campeon2);

        assertTrue(campeon2.estaVivo());
        assertEquals(1200 - 100, campeon2.getPuntosVida());
    }

    @Test
    public void testUsarHabilidadQueNoTiene() {
        Habilidad habilidad = new Habilidad("Golpe Poderoso", 100, "daño");
        Campeon campeon = new Campeon("Garen", "Tanque", 100, 100, 0, Arrays.asList(habilidad));
        Campeon campeon2 = new Campeon("Ahri", "Mago", 1200, 150, 0, Arrays.asList(new Habilidad("Orbe del Engaño", 250, "daño")));

        // Simular que Garen usa Golpe Poderoso contra Ahri
        campeon.usarHabilidad("Regeneracion", campeon2);

        assertTrue(campeon2.estaVivo());
        assertEquals(1200, campeon2.getPuntosVida());
    }
}
