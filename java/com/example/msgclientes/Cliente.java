package com.example.msgclientes;

/**
 * Created by Luis on 09-05-2017.
 */

public class Cliente {
    private String nombre;
    private String telefono;
    private String mensaje;

    public String getNombre() { return nombre; }

    public String getTelefono() {
        return telefono;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setNombre(String n) {
        nombre = n;
    }

    public void setTelefono(String t) {
        telefono = t;
    }

    public void setMensaje(String m) {
        mensaje = m;
    }

}
