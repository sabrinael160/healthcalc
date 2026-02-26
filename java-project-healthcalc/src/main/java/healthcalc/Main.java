package healthcalc;

import java.util.Scanner;
import healthcalc.exceptions.InvalidHealthDataException;

public class Main {
    public static void main(String[] args) {
        // 1. Instanciamos tu calculadora
        HealthCalc calc = new HealthCalcImpl();
        
        // 2. Preparamos el scanner para leer del teclado
        Scanner scanner = new Scanner(System.in);

      
        System.out.println(" --- CALCULADORA DE PRESIÓN ARTERIAL MEDIA (MAP) ---");

        try {
            System.out.print("Introduce la Presión Sistólica (PAS): ");
            float pas = scanner.nextFloat();

            System.out.print("Introduce la Presión Diastólica (PAD): ");
            float pad = scanner.nextFloat();

            // 3. Usamos tus métodos para calcular y clasificar
            float map = calc.calculateMAP(pas, pad);
            String clasificacion = calc.mapClassification(map);

            System.out.println("\n--- RESULTADOS ---");
            System.out.printf("Valor MAP: %.2f mmHg\n", map);
            System.out.println("Clasificación: " + clasificacion);

        } catch (InvalidHealthDataException e) {
            // Este captura tus excepciones personalizadas
            System.err.println("\nERROR DE DATOS: " + e.getMessage());
        } catch (Exception e) {
            // Este captura si el usuario mete texto o algo que no sea un número
            System.err.println("\nERROR: Entrada no válida. Por favor, usa números.");
        } finally {
            scanner.close();
            System.out.println("Saliendo del programa...");
        }
    }
}