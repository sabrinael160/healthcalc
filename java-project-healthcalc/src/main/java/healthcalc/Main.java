package healthcalc;

import java.util.Scanner;
import healthcalc.exceptions.InvalidHealthDataException;

public class Main {
    public static void main(String[] args) {

        HealthCalc calc = new HealthCalcImpl();
        
        Scanner scanner = new Scanner(System.in);

      
        System.out.println(" --- CALCULADORA DE PRESIÓN ARTERIAL MEDIA (MAP) ---");

        try {
            System.out.print("Introduce la Presión Sistólica (PAS): ");
            float pas = scanner.nextFloat();

            System.out.print("Introduce la Presión Diastólica (PAD): ");
            float pad = scanner.nextFloat();

            float map = calc.calculateMAP(pas, pad);
            String clasificacion = calc.mapClassification(map);

            System.out.println("\n--- RESULTADOS ---");
            System.out.printf("Valor MAP: %.2f mmHg\n", map);
            System.out.println("Clasificación: " + clasificacion);

        } catch (InvalidHealthDataException e) {

            System.err.println("\nERROR DE DATOS: " + e.getMessage());
        } catch (Exception e) {

            System.err.println("\nERROR: Entrada no válida. Por favor, usa números.");
        } finally {
            scanner.close();
            System.out.println("Saliendo del programa...");
        }
    }
}