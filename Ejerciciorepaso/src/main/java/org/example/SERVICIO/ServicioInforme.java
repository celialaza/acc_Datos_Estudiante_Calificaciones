package org.example.SERVICIO;

import org.example.MODELO.Estudiante;
import org.example.MODELO.Resultado;
import org.example.DATASERVICE.CsvDataService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServicioInforme {
    // 1. El servicio ahora "depende de" un repositorio
    private final CsvDataService repositorio;

    // 2. Creamos un constructor para "inyectar" esa dependencia
    public ServicioInforme(CsvDataService repositorio) {
        this.repositorio = repositorio;
    }


    // Necesitarás importar Estudiante, Resultado, List, Map, ArrayList
    private List<Resultado> procesarDatos(Map<String, Estudiante> mapaEstudiantes, Map<String, List<Double>> mapaNotas) {
        List<Resultado> listaResultados = new ArrayList<>();

        // 2. Iterar sobre el mapa de notas.
        // Usamos .entrySet() porque necesitamos tanto la clave (ID) como el valor (lista de notas)
        for (Map.Entry<String, List<Double>> entrada : mapaNotas.entrySet()) {

            // 3. Obtener los datos de cada entrada del bucle
            String idEstudiante = entrada.getKey(); // Ej: "101"
            List<Double> notas = entrada.getValue(); // Ej: [8.5, 9.0]

            // 4. Calcular el promedio de la lista de notas
            double sumaTotal = 0.0;
            for (double nota : notas) {
                sumaTotal += nota; // Suma todas las notas de la lista
            }

            // Comprobamos que la lista no esté vacía para evitar una división por cero
            double promedio = 0.0;
            if (!notas.isEmpty()) {
                promedio = sumaTotal / notas.size(); // Ej: 17.5 / 2 = 8.75
            }

            // 5. Buscar el nombre del estudiante usando el ID
            // Usamos el 'idEstudiante' (clave) para buscar en el 'mapaEstudiantes'
            Estudiante estudiante = mapaEstudiantes.get(idEstudiante);

            // 6. Crear el objeto Resultado (si encontramos al estudiante)
            if (estudiante != null) {
                // Combinamos nombre y apellido
                String nombreCompleto = estudiante.getNombre() + " " + estudiante.getApellido();

                // Creamos el objeto final para esta fila
                Resultado resultadoFila = new Resultado(nombreCompleto, promedio);

                // 7. Añadir el resultado a nuestra lista final
                listaResultados.add(resultadoFila);
            } else {
                // Opcional: Avisar si hay notas de un ID que no existe en estudiantes.csv
                System.out.println("Advertencia: Se encontraron notas para el ID " + idEstudiante + " pero no existe en el archivo de estudiantes.");
            }
        }

        // 8. Devolver la lista completa de resultados
        return listaResultados;
    }
    // 4. Creamos un método "maestro" que orquesta todo
    public void generarInformeCompleto(String rutaEstudiantes, String rutaCalificaciones, String rutaSalida) throws IOException {

        // Pide los datos al repositorio
        Map<String, Estudiante> estudiantes = repositorio.leerEstudiantes(rutaEstudiantes);
        Map<String, List<Double>> notas = repositorio.leerCalificaciones(rutaCalificaciones);

        // Procesa los datos (usando su propio método)
        List<Resultado> resultados = this.procesarDatos(estudiantes, notas);

        // Pide al repositorio que escriba los resultados
        repositorio.escribirInforme(resultados, rutaSalida);
    }


    }



