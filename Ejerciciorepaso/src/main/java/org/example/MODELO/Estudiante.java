package org.example.MODELO;

import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data

public class Estudiante {
    private int id;
    private String nombre;
    private String apellido;

    public Estudiante() {
    }
}
