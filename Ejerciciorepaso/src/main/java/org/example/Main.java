package org.example;

import org.example.DATASERVICE.CsvDataService;
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
// 1. Definir los nombres de los archivos
        String archivoEstudiantes = "estudiantes.csv";
        String archivoCalificaciones = "calificaciones.csv";
        String archivoSalida = "informe_final.csv";

        System.out.println("Iniciando proceso de generación de informe...");

        try {
            // 2. Crear las instancias de nuestras dos capas
            CsvDataService repositorio = new CsvDataService();
            ServicioInforme servicio = new ServicioInforme(repositorio); // Le pasamos el repositorio al servicio

            // 3. ¡Hacer UNA SOLA LLAMADA al servicio!
            servicio.generarInformeCompleto(archivoEstudiantes, archivoCalificaciones, archivoSalida);

            System.out.println("¡Informe '" + archivoSalida + "' generado exitosamente!");

        } catch (IOException e) {
            System.err.println("Ha ocurrido un error durante el proceso:");
            e.printStackTrace();
        }
    }
}
