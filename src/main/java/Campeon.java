import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Campeon {

    private String nombre;
    private String rol;
    private double puntosVida;
    private double danoAtaque;
    private double defensa;
    private List<Habilidad> habilidades;

    private double danoAtaqueInicial;
    private double defensaInicial;

    private static final double MAX_PUNTOS_VIDA = 2000;

    // Buffs temporales: tipo -> duración restante
    private Map<String, Integer> buffsTemporales = new HashMap<>();

    // Constructor
    public Campeon(String nombre, String rol, double puntosVida, double danoAtaque, double defensa, List<Habilidad> habilidades) {
        this.nombre = nombre;
        this.rol = rol;
        this.puntosVida = puntosVida;
        this.danoAtaque = danoAtaque;
        this.defensa = defensa;
        this.habilidades = habilidades;
        this.danoAtaqueInicial = danoAtaque;
        this.defensaInicial = defensa;
    }

    // Métodos
    public void atacar(Campeon otroCampeon) {
        double danoInfligido = Math.max(0, danoAtaque);
        otroCampeon.recibirDano(danoInfligido);
        System.out.println(nombre + " atacó a " + otroCampeon.nombre + " causando " + danoInfligido + " de daño.");
    }

    public void usarHabilidad(String habilidad, Campeon otroCampeon) {
        for (Habilidad h : habilidades) {
            if (h.getNombre().equalsIgnoreCase(habilidad)) {
                h.aplicar(this, otroCampeon);
                return;
            }
        }
        System.out.println(nombre + " no tiene la habilidad " + habilidad + ".");
    }

    public void recibirDano(double dano) {
        double danoReducido = Math.max(0, dano - defensa);
        puntosVida -= danoReducido;
        System.out.println(nombre + " recibió " + danoReducido + " de daño. Puntos de vida restantes: " + puntosVida);
    }

    public boolean estaVivo() {
        return puntosVida > 0;
    }

    //Métodos auxiliares
    public double curar(double puntos) {
        double simulacionPuntosVida = puntosVida + puntos;
        if(simulacionPuntosVida <= MAX_PUNTOS_VIDA) {
            puntosVida = simulacionPuntosVida;
            System.out.println(nombre + " recuperó " + puntos + " puntos de vida. Total: " + puntosVida);
        }else {
            puntos = MAX_PUNTOS_VIDA - puntosVida;
            puntosVida = MAX_PUNTOS_VIDA;
            System.out.println(nombre + " recuperó " + puntos + " puntos de vida. Total: " + puntosVida);
        }
        return puntos;
    }

    public void aplicarBuff(String tipo, double valor, int duracion) {
        if ("defensa".equalsIgnoreCase(tipo)) {
            defensa += valor;
            System.out.println(nombre + " ganó un buff de defensa de " + valor + " puntos por " + duracion + " turnos.");
        } else if ("ataque".equalsIgnoreCase(tipo)) {
            danoAtaque += valor;
            System.out.println(nombre + " ganó un buff de ataque de " + valor + " puntos por " + duracion + " turnos.");
        }
        buffsTemporales.put(tipo, duracion); // Registrar duración del buff
    }

    public void procesarTurno() {
        // Itera sobre los buffs temporales, reduce la duración y elimina los que expiran
        buffsTemporales.entrySet().removeIf(entry -> {
            String tipo = entry.getKey();
            int duracionRestante = entry.getValue();

            if (duracionRestante > 1) {
                buffsTemporales.put(tipo, duracionRestante - 1);
                return false; // Buff sigue activo
            } else {
                // El buff expira, revertimos su efecto
                if ("defensa".equalsIgnoreCase(tipo)) {
                    defensa = defensaInicial; // Revertir mejora de defensa
                    System.out.println(nombre + " perdió el buff de defensa.");
                } else if ("ataque".equalsIgnoreCase(tipo)) {
                    danoAtaque = danoAtaqueInicial; // Revertir mejora de ataque
                    System.out.println(nombre + " perdió el buff de ataque.");
                }
                return true; // Buff eliminado
            }
        });
    }

    //Getters
    public String getRol() {
        return rol;
    }

    public double getPuntosVida() {
        return puntosVida;
    }

    public double getDanoAtaque() {
        return danoAtaque;
    }

    public double getDefensa() {
        return defensa;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Habilidad> getHabilidades() {
        return habilidades;
    }
}
