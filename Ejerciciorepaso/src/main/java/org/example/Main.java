package org.example;

import org.example.MODELO.Estudiante;
import org.example.MODELO.Resultado;
import org.example.SERVICIO.ServicioInforme;

import java.io.IOException;
import java.util.List;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
// 1. Crear una instancia de tu servicio (el que tiene todos los métodos)
        ServicioInforme servicio = new ServicioInforme();

        // 2. Definir los nombres de los archivos
        String archivoEstudiantes = "estudiantes.csv";
        String archivoCalificaciones = "calificaciones.csv";
        String archivoSalida = "informe_final.csv";

        System.out.println("Iniciando proceso de generación de informe...");

        // 3. ¡MUY IMPORTANTE! Envolver todo en un bloque try-catch
        //    (Porque los métodos de lectura/escritura lanzan 'IOException')
        try {

            // 4. LLAMAR A LOS MÉTODOS EN ORDEN

            // Paso A: Leer estudiantes
            System.out.println("Leyendo archivo de estudiantes...");
            Map<String, Estudiante> mapaEstudiantes = servicio.leerEstudiantes(archivoEstudiantes);

            // Paso B: Leer calificaciones
            System.out.println("Leyendo archivo de calificaciones...");
            Map<String, List<Double>> mapaNotas = servicio.leerCalificaciones(archivoCalificaciones);

            // Paso C: Procesar los datos (unir y calcular promedio)
            System.out.println("Procesando datos...");
            List<Resultado> listaResultados = servicio.procesarDatos(mapaEstudiantes, mapaNotas);

            // Paso D: Escribir el archivo final
            System.out.println("Escribiendo informe final...");
            servicio.escribirInforme(listaResultados, archivoSalida);

            System.out.println("¡Informe '" + archivoSalida + "' generado exitosamente!");

        } catch (IOException e) {
            // 5. Manejar el error si algo sale mal (ej: archivo no encontrado)
            System.err.println("Ha ocurrido un error durante el proceso:");
            e.printStackTrace(); // Imprime el error detallado en la consola
        }
    }
}
