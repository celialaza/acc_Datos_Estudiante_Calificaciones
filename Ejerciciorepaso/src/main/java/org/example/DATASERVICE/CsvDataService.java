package org.example.DATASERVICE;

import org.example.MODELO.Estudiante;
import org.example.MODELO.Resultado;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvDataService {
    /**
     * Lee el archivo CSV de estudiantes y lo carga en un Mapa.
     *
     * @param rutaArchivo La ruta al archivo "estudiantes.csv".
     * @return Un Mapa con el ID del estudiante como clave (String) y el objeto Estudiante como valor.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public Map<String, Estudiante> leerEstudiantes(String rutaArchivo) throws IOException {

        // 1. Inicialización (esto estaba perfecto)
        Map<String, Estudiante> mapaEstudiantes = new HashMap<>();

        // 2. Usar try-with-resources (esto estaba perfecto)
        try (BufferedReader bfr = new BufferedReader(new FileReader(rutaArchivo))) {

            // 3. MEJORA: Saltar la línea de cabecera
            // Leemos la primera línea ("id,nombre,apellido") y la descartamos.
            bfr.readLine();

            String linea;
            while ((linea = bfr.readLine()) != null) {

                // 4. MEJORA: Añadir robustez para líneas vacías
                if (linea.trim().isEmpty()) {
                    continue; // Salta esta iteración si la línea está en blanco
                }

                String[] trozos = linea.split(",");

                // 5. MEJORA: Comprobar que la línea tiene los datos esperados
                if (trozos.length == 3) {
                    // 1. Convertimos el ID (texto) a un entero (int)
                    int idEstudiante = Integer.parseInt(trozos[0]);
                    // Creamos el estudiante (esto estaba bien)
                    Estudiante e = new Estudiante(idEstudiante, trozos[1], trozos[2]);

                    // 6. CORRECCIÓN: Añadir al *mapa*, no a la clase Estudiante
                    // Usamos el ID del estudiante (trozos[0]) como clave (key)
                    // y el objeto 'e' como valor (value).
                    mapaEstudiantes.put(trozos[0], e); // Asumiendo que Estudiante tiene un e.getId()
                    // Si no, puedes usar: mapaEstudiantes.put(trozos[0], e);

                } else {
                    // Opcional: Avisar que una línea está malformada
                    System.out.println("Advertencia: Línea malformada en estudiantes.csv: " + linea);
                }
            }
        }
        // 7. CORRECCIÓN: Se eliminan los bloques catch
        // Ya no son necesarios. Si ocurre una IOException (como FileNotFoundException),
        // la firma "throws IOException" la pasará al método 'main' para que él la maneje.
        // Esto es más limpio.

        // 8. Devolver el mapa poblado
        return mapaEstudiantes;
    }
    /**
     * Lee el archivo CSV de calificaciones y agrupa las notas por estudiante.
     *
     * @param rutaArchivo La ruta al archivo "calificaciones.csv".
     * @return Un Mapa con el ID del estudiante como clave (String) y una Lista de
     * sus notas (List<Double>) como valor.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public Map<String, List<Double>> leerCalificaciones(String rutaArchivo) throws IOException {

        // 1. Inicialización (clave String, valor es una LISTA de Doubles)
        Map<String, List<Double>> mapaNotas = new HashMap<>();

        // 2. Usar try-with-resources
        try (BufferedReader bfr = new BufferedReader(new FileReader(rutaArchivo))) {

            // 3. Saltar la línea de cabecera
            // Leemos "estudiante_id,asignatura,nota" y la descartamos
            bfr.readLine();

            String linea;
            while ((linea = bfr.readLine()) != null) {

                // 4. Robustez: Saltar líneas vacías
                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] trozos = linea.split(",");

                // 5. Comprobar que la línea tiene los 3 campos esperados
                if (trozos.length == 3) {

                    // --- INICIO DE LA LÓGICA CLAVE ---

                    // Obtenemos los datos. El ID sigue siendo String (nuestra clave)
                    String idEstudiante = trozos[0];

                    // Convertimos la nota (trozos[2]) a Double
                    double nota = Double.parseDouble(trozos[2]);

                    // 6. Lógica de Agregación:
                    // Comprobamos si ya hemos visto a este estudiante
                    if (!mapaNotas.containsKey(idEstudiante)) {
                        // SI ES LA PRIMERA VEZ que vemos este ID:
                        // Creamos una NUEVA lista para él
                        List<Double> listaDeNotas = new ArrayList<>();
                        // Añadimos su primera nota
                        listaDeNotas.add(nota);
                        // Guardamos la NUEVA lista en el mapa
                        mapaNotas.put(idEstudiante, listaDeNotas);
                    } else {
                        // SI YA EXISTE el ID en el mapa:
                        // Obtenemos la lista que ya existía
                        List<Double> listaExistente = mapaNotas.get(idEstudiante);
                        // Simplemente añadimos la nueva nota a esa lista
                        listaExistente.add(nota);
                    }
                    // --- FIN DE LA LÓGICA CLAVE ---

                } else {
                    // Opcional: Avisar que una línea está malformada
                    System.out.println("Advertencia: Línea malformada en calificaciones.csv: " + linea);
                }
            }
        }
        // 7. No se necesitan 'catch' por la misma razón que antes.
        // La 'IOException' se propagará al 'main'.

        // 8. Devolver el mapa con las notas agrupadas
        return mapaNotas;
    }
    public void escribirInforme(List<Resultado> resultados, String rutaArchivoSalida) throws IOException {

        // 1. Usamos try-with-resources con BufferedWriter para escribir de forma eficiente
        //    FileWriter se encarga de abrir el archivo en modo escritura (lo crea si no existe)
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivoSalida))) {

            // 2. Escribir la línea de cabecera (el título de las columnas)
            writer.write("nombre_completo,promedio");
            writer.newLine(); // Escribe un salto de línea

            // 3. Iterar sobre cada objeto 'Resultado' en tu lista
            for (Resultado res : resultados) {

                // 4. Formatear la línea de datos
                // Obtenemos los datos del objeto (¡asegúrate de tener estos getters en Resultado.java!)
                String linea = res.getNombreCompleto() + "," + res.getPromedio();

                // 5. Escribir la línea en el archivo
                writer.write(linea);
                writer.newLine(); // Escribe un salto de línea para la siguiente fila
            }
        }
        // 6. El 'try-with-resources' cierra el 'writer' automáticamente
        //    y ya hemos declarado 'throws IOException' en la firma.
    }
}
