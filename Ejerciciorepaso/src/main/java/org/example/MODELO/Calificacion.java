package org.example.MODELO;

import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class Calificacion {
    private int estudiante_id;
    private String asignatura;
    private double nota;

    public Calificacion() {
    }
}
