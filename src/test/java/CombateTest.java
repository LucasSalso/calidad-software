import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class CombateTest {

    @Test
    public void testCombatePorTurnos() {
        // Crear campeones mockeados
        Campeon campeon1 = mock(Campeon.class);
        Campeon campeon2 = mock(Campeon.class);
        Random random = mock(Random.class);

        // Configurar mocks para alternar ataques y nombres de los campeones
        when(campeon1.estaVivo()).thenReturn(true, true, false); // Campeón 1 vive solo 2 turnos
        when(campeon2.estaVivo()).thenReturn(true); // Campeón 2 sigue vivo
        when(campeon1.getNombre()).thenReturn("Garen"); // Campeón 1 se llamará Garen
        when(campeon2.getNombre()).thenReturn("Dr Mundo"); // Campeón 2 se llamará Dr Mundo

        //Random para atacar
        when(random.nextBoolean()).thenReturn(true);

        Combate combate = new Combate(campeon1, campeon2, random);
        combate.iniciar();

        // Verificar que atacan alternadamente
        verify(campeon1, atLeastOnce()).atacar(campeon2);
        verify(campeon2, atLeastOnce()).atacar(campeon1);
    }

    @Test
    public void testCombatePorTurnosSinHabilidades() {
        // Crear campeones mockeados
        Campeon campeon1 = mock(Campeon.class);
        Campeon campeon2 = mock(Campeon.class);
        Random random = mock(Random.class);

        // Configurar mocks para alternar ataques y nombres de los campeones
        when(campeon1.estaVivo()).thenReturn(true, true, false); // Campeón 1 vive solo 2 turnos
        when(campeon2.estaVivo()).thenReturn(true); // Campeón 2 sigue vivo
        when(campeon1.getNombre()).thenReturn("Garen"); // Campeón 1 se llamará Garen
        when(campeon2.getNombre()).thenReturn("Dr Mundo"); // Campeón 2 se llamará Dr Mundo

        //Random para usar habilidades
        when(random.nextBoolean()).thenReturn(false);

        Combate combate = new Combate(campeon1, campeon2, random);
        combate.iniciar();

        // Verificar que atacan alternadamente
        verify(campeon1, never()).atacar(campeon2);
        verify(campeon2, never()).atacar(campeon1);
    }

    @Test
    public void testCombatePorTurnosConHabilidades() {
        // Crear campeones mockeados
        Campeon campeon1 = mock(Campeon.class);
        Campeon campeon2 = mock(Campeon.class);
        Random random = mock(Random.class);

        Habilidad habilidad1 = new Habilidad("Juicio", 50, "buff_ataque");
        Habilidad habilidad2 = new Habilidad("Regeneración", 25, "cura");

        // Configurar mocks para alternar ataques y nombres de los campeones
        when(campeon1.estaVivo()).thenReturn(true, true, false); // Campeón 1 vive solo 2 turnos
        when(campeon2.estaVivo()).thenReturn(true); // Campeón 2 sigue vivo
        when(campeon1.getNombre()).thenReturn("Garen"); // Campeón 1 se llamará Garen
        when(campeon2.getNombre()).thenReturn("Dr Mundo"); // Campeón 2 se llamará Dr Mundo
        when(campeon1.getHabilidades()).thenReturn(Arrays.asList(habilidad1)); // Habilidad 1 para Campeon 1
        when(campeon2.getHabilidades()).thenReturn(Arrays.asList(habilidad2)); // Habilidad 2 para Campeon 1

        //Random para usar habilidades
        when(random.nextBoolean()).thenReturn(false);

        Combate combate = new Combate(campeon1, campeon2, random);
        combate.iniciar();

        // Verificar que atacan alternadamente
        verify(campeon1, atLeastOnce()).usarHabilidad(anyString(), any());
        verify(campeon2, atLeastOnce()).usarHabilidad(anyString(), any());
    }

    @Test
    public void testCombateSeDetieneCuandoUnoEsDerrotado() {
        // Crear campeones mockeados
        Campeon campeon1 = mock(Campeon.class);
        Campeon campeon2 = mock(Campeon.class);
        Random random = mock(Random.class);

        // Configurar mocks para simular que campeon1 muere en el segundo turno
        when(campeon1.estaVivo()).thenReturn(true, false); // Vive solo 1 turno
        when(campeon2.estaVivo()).thenReturn(true); // Sigue vivo siempre

        //Random para atacar
        when(random.nextBoolean()).thenReturn(true);

        Combate combate = new Combate(campeon1, campeon2, random);
        combate.iniciar();

        // Verificar que el combate se detiene después de que campeon1 muere
        verify(campeon1, times(1)).atacar(campeon2);
        verify(campeon2, never()).atacar(campeon1); // Nunca llega a atacar el turno 2
    }

    @Test
    public void testMostrarGanador() {
        // Crear campeones con comportamiento realista
        Campeon campeon1 = new Campeon("Ahri", "Mago", 1500, 200, 50, List.of());
        Campeon campeon2 = new Campeon("Garen", "Tanque", 500, 300, 100, List.of());
        Random random = mock(Random.class);

        //Random para atacar
        when(random.nextBoolean()).thenReturn(true);

        // Combate configurado para que Garen pierda
        Combate combate = new Combate(campeon1, campeon2, random);
        combate.iniciar();

        // Verificar el ganador
        assertTrue(campeon1.estaVivo(), "Ahri debería estar viva.");
        assertFalse(campeon2.estaVivo(), "Garen debería estar derrotado.");
        combate.mostrarGanador(); // Confirmar el mensaje correcto
    }
}
