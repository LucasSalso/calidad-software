public class Habilidad {

    private String nombre;
    private double dano;
    private String tipo; // Puede ser "daño", "cura", etc.

    private static final int DURACION_BUFF = 3; // Duración de los buffs (en turnos)

    // Constructor
    public Habilidad(String nombre, double dano, String tipo) {
        this.nombre = nombre;
        this.dano = dano;
        this.tipo = tipo;
    }

    // Métodos
    public void aplicar(Campeon campeon, Campeon otroCampeon) {
        if ("daño".equalsIgnoreCase(tipo)) {
            otroCampeon.recibirDano(dano);
            System.out.println("La habilidad " + nombre + " causó " + dano + " de daño a " + otroCampeon.getNombre());
        } else if ("cura".equalsIgnoreCase(tipo)) {
            dano = campeon.curar(dano);
            System.out.println("La habilidad " + nombre + " recupera " + dano + " de salud a " + campeon.getNombre());
        } else if (tipo.contains("buff")) {

            if (tipo.contains("defensa")) {
                campeon.aplicarBuff("defensa", dano, DURACION_BUFF);
            } else if (tipo.contains("ataque")) {
                campeon.aplicarBuff("ataque", dano, DURACION_BUFF);
            } else {
                System.out.println("La habilidad " + nombre + " no tuvo efectos en " + campeon.getNombre());
            }
        } else {
            System.out.println("El tipo de habilidad " + tipo + " no es válido.");
        }
    }

    //Getters
    public String getNombre() {
        return nombre;
    }

    public double getDano() {
        return dano;
    }

    public String getTipo() {
        return tipo;
    }

}
