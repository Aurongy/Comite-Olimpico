package org.example;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;


public class Main {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        FabricaConexionMariaDB fabrica = new FabricaConexionMariaDB("localhost", 3306, "atletas_db", "root", "admin");
        MariaDBAtletaDAO atletaDAO = new MariaDBAtletaDAO(fabrica);
        MariaDBEntrenamientoDAO entrenamientoDAO = new MariaDBEntrenamientoDAO(fabrica);
        CalculadoraPlanilla calculadora = new CalculadoraPlanilla(10.0, 20.0, 15.0); // ejemplo

        while (true) {
            System.out.println("\n----Sistema de Monitoreo de Atletas----");
            System.out.println("1. Registrar atleta");
            System.out.println("2. Registrar entrenamiento");
            System.out.println("3. Ver historial de entrenamientos");
            System.out.println("4. Estadísticas de atleta");
            System.out.println("5. Exportar entrenamientos a CSV");
            System.out.println("6. Guardar respaldo JSON");
            System.out.println("7. Procesar pago de planilla (atleta)");
            System.out.println("8. Listar atletas");
            System.out.println("0. Salir");
            System.out.print("Seleccione opción: ");
            String opt = sc.nextLine().trim();
            try {
                switch (opt) {
                    case "1":
                        registrarAtleta(atletaDAO);
                        break;
                    case "2":
                        registrarEntrenamiento(entrenamientoDAO, atletaDAO);
                        break;
                    case "3":
                        verHistorial(entrenamientoDAO);
                        break;
                    case "4":
                        estadisticas(entrenamientoDAO, atletaDAO);
                        break;
                    case "5":
                        exportarCSV(entrenamientoDAO);
                        break;
                    case "6":
                        respaldoJSON(atletaDAO, entrenamientoDAO);
                        break;
                    case "7":
                        procesarPago(entrenamientoDAO, atletaDAO, calculadora);
                        break;
                    case "8":
                        listarAtletas(atletaDAO);
                        break;
                    case "0":
                        System.out.println("Hasta luego");
                        return;
                    default:
                        System.out.println("Opción inválida");
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    private static void registrarAtleta(MariaDBAtletaDAO dao) {
        try {
            Atleta a = new Atleta();
            System.out.print("Nombre completo: "); a.setNombre(sc.nextLine());
            System.out.print("Edad (entero, enter para omitir): "); String edadS = sc.nextLine().trim();
            if (!edadS.isEmpty()) a.setEdad(Integer.valueOf(edadS));
            System.out.print("Disciplina: "); a.setDisciplina(sc.nextLine());
            System.out.print("Departamento: "); a.setDepartamento(sc.nextLine());
            System.out.print("Nacionalidad: "); a.setNacionalidad(sc.nextLine());
            System.out.print("Fecha de ingreso (YYYY-MM-DD, enter para hoy): "); String f = sc.nextLine().trim();
            if (f.isEmpty()) a.setFechaIngreso(LocalDate.now()); else a.setFechaIngreso(LocalDate.parse(f));
            Atleta creado = dao.crear(a);
            System.out.println("Atleta creado con id=" + creado.getId());
        } catch (Exception e) { System.err.println("Error al registrar atleta: " + e.getMessage()); }
    }

    private static void registrarEntrenamiento(MariaDBEntrenamientoDAO tdao, MariaDBAtletaDAO adao) {
        try {
            System.out.print("ID atleta: "); long id = Long.parseLong(sc.nextLine());
            Optional<Atleta> opt = adao.buscarPorId(id);
            if (opt.isEmpty()) { System.out.println("Atleta no encontrado"); return; }
            SesionEntrenamiento t = new SesionEntrenamiento();
            t.setAtletaId(id);
            System.out.print("Fecha (YYYY-MM-DD): "); t.setFecha(LocalDate.parse(sc.nextLine()));
            System.out.print("Tipo (resistencia/tecnica/fuerza): "); t.setTipo(sc.nextLine());
            System.out.print("Valor (numero): "); t.setValor(Double.valueOf(sc.nextLine()));
            System.out.print("Unidad (segundos/metros/kg): "); t.setUnidad(sc.nextLine());
            System.out.print("Ubicacion (Nacional/Internacional): "); t.setUbicacion(sc.nextLine());
            if ("Internacional".equalsIgnoreCase(t.getUbicacion())) {
                System.out.print("Pais: "); t.setPais(sc.nextLine());
            }
            SesionEntrenamiento creado = tdao.crear(t);
            System.out.println("Entrenamiento registrado id=" + creado.getId());
        } catch (Exception e) { System.err.println("Error al registrar entrenamiento: " + e.getMessage()); }
    }

    private static void verHistorial(MariaDBEntrenamientoDAO tdao) {
        try {
            System.out.print("ID atleta: "); long id = Long.parseLong(sc.nextLine());
            List<SesionEntrenamiento> lista = tdao.buscarPorAtletaId(id);
            if (lista.isEmpty()) { System.out.println("Sin entrenamientos"); return; }
            lista.forEach(System.out::println);
        } catch (Exception e) { System.err.println("Error: " + e.getMessage()); }
    }

    private static void estadisticas(MariaDBEntrenamientoDAO tdao, MariaDBAtletaDAO adao) {
        try {
            System.out.print("ID atleta: "); long id = Long.parseLong(sc.nextLine());
            var opt = adao.buscarPorId(id);
            if (opt.isEmpty()) { System.out.println("Atleta no encontrado"); return; }
            List<SesionEntrenamiento> lista = tdao.buscarPorAtletaId(id);
            if (lista.isEmpty()) { System.out.println("Sin entrenamientos"); return; }
            System.out.println("Promedio: " + ServicioEstadisticas.promedio(lista));
            System.out.println("Mejor (max): " + ServicioEstadisticas.mejorMarca(lista, true));
            System.out.println("Evolución (ordenada por fecha):");
            ServicioEstadisticas.ordenarPorFecha(lista).forEach(s -> System.out.println(s.getFecha() + " -> " + s.getValor()));
            System.out.println("Promedio nacional: " + ServicioEstadisticas.promedioPorUbicacion(lista, "Nacional"));
            System.out.println("Promedio internacional: " + ServicioEstadisticas.promedioPorUbicacion(lista, "Internacional"));
        } catch (Exception e) { System.err.println("Error: " + e.getMessage()); }
    }

    private static void exportarCSV(MariaDBEntrenamientoDAO tdao) {
        try {
            List<SesionEntrenamiento> all = tdao.buscarTodos();
            System.out.print("Ruta CSV a guardar: "); String ruta = sc.nextLine();
            tdao.buscarPorAtletaId(all.get(0).getAtletaId());
            System.out.println("CSV exportado a: " + ruta);
        } catch (Exception e) { System.err.println("Error: " + e.getMessage()); }
    }

    private static void respaldoJSON(MariaDBAtletaDAO adao, MariaDBEntrenamientoDAO tdao) {
        try {
            System.out.print("Ruta archivo atletas JSON: "); String aPath = sc.nextLine();
            System.out.print("Ruta archivo entrenamientos JSON: "); String tPath = sc.nextLine();
            UtilidadesJson.guardarAtletas(adao.buscarTodos(), aPath);
            UtilidadesJson.guardarSesiones(tdao.buscarTodos(), tPath);
            System.out.println("Respaldo guardado.");
        } catch (Exception e) { System.err.println("Error: " + e.getMessage()); }
    }

    private static void procesarPago(MariaDBEntrenamientoDAO tdao, MariaDBAtletaDAO adao, CalculadoraPlanilla calculadora) {
        try {
            System.out.print("ID atleta: "); long id = Long.parseLong(sc.nextLine());
            var opt = adao.buscarPorId(id);
            if (opt.isEmpty()) { System.out.println("Atleta no encontrado"); return; }
            List<SesionEntrenamiento> lista = tdao.buscarPorAtletaId(id);
            double mejor = ServicioEstadisticas.mejorMarca(lista, true);
            double pago = calculadora.calcularPagoMensual(lista, mejor);
            System.out.println("Pago calculado: Q. " + pago);
        } catch (Exception e) { System.err.println("Error: " + e.getMessage()); }
    }

    private static void listarAtletas(MariaDBAtletaDAO dao) {
        try {
            List<Atleta> lista = dao.buscarTodos();
            if (lista.isEmpty()) { System.out.println("No hay atletas registrados."); return; }
            lista.forEach(System.out::println);
        } catch (Exception e) { System.err.println("Error: " + e.getMessage()); }
    }
}

