package healthcalc;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import healthcalc.exceptions.InvalidHealthDataException;

/**
 * Tests for the HealthCalc interface.
 * 
 * Use the AAA pattern (Arrange, Act, Assert) for the tests.
 * 
 */
@DisplayName("Tests para el cálculo de la Presión Arterial Media (MAP)")
public class MAPTest {

    private HealthCalc healthCalc;

    @BeforeEach 
    void setUp() {
        healthCalc = new HealthCalcImpl();
    }

    @Nested
    @DisplayName("MÉTRICA MAP")
    class MAPCalculationTests {

        @Test
        @DisplayName("Cálculo de MAP estándar (Valores saludables)")
        void testMapValido() throws InvalidHealthDataException {
            
            float pas = 120; //presión sistólica 
            float pad = 80;    //presión diastólica
            float expected = (pas + (2 * pad)) / 3; 

            float result = healthCalc.calculateMAP(pas, pad);

            assertEquals(expected, result, 0.01f); // Tolerancia de 0.01 para decimales
        }

        @ParameterizedTest(name = "PAS {0}, PAD {1} debe dar MAP > 60") //Pruebo con distintas entradas para validar que >60"
        @CsvSource({ //tabla de valores 
            "95, 60",
            "100, 65",
            "110, 70"
        })
        @DisplayName("Validación de perfusión adecuada (Límite mínimo)")
        void testMapPerfusionAdecuada(float pas, float pad) throws InvalidHealthDataException {
            float result = healthCalc.calculateMAP(pas, pad);
            assertTrue(result >= 60f, "La perfusión debería ser adecuada (>= 60)");
        }

        @Test
        @DisplayName("Validación de estado crítico (Perfusión < 60)")
        void testMapEstadoCritico() throws InvalidHealthDataException {
            float result = healthCalc.calculateMAP(80, 45);
            assertTrue(result < 60.0f, "Debe identificar valores de riesgo menores a 60");
        }
    }

    @Nested
    @DisplayName("Validación de errores y límites físicos/biológicos")
    class MAPValidationTests {

        @Test
        @DisplayName("Bloqueo de inconsistencia biológica (PAD >= PAS)")
        void testMapInconsistencia() {
            // la diastólica nunca puede ser mayor o igual a la sistólica
            assertThrows(InvalidHealthDataException.class, () -> healthCalc.calculateMAP(70, 110));
        }

        @ParameterizedTest(name = "Valor inválido: {0}")
        @ValueSource(floats = {-120, -1, 0})
        @DisplayName("Lanzar excepción ante valores negativos o cero")
        void testMapValoresNegativos(float valor) {
            assertAll(
                () -> assertThrows(InvalidHealthDataException.class, () -> healthCalc.calculateMAP(valor, 80)),
                () -> assertThrows(InvalidHealthDataException.class, () -> healthCalc.calculateMAP(120, valor))
            );
        }

        @ParameterizedTest(name = "Sistólica excesiva: {0} mmHg")
        @ValueSource(floats = {300.1f, 400, 500})
        @DisplayName("Bloqueo de límites físicos máximos (Sistólica > 300)")
        void testSistolicaMaxima(float pas) {
            assertThrows(InvalidHealthDataException.class, () -> healthCalc.calculateMAP(pas, 80));
        }

        @ParameterizedTest(name = "Diastólica excesiva: {0} mmHg")
        @ValueSource(floats = {200.1f, 250, 300})
        @DisplayName("Bloqueo de límites físicos máximos (Diastólica > 200)")
        void testDiastolicaMaxima(float pad) {
            assertThrows(InvalidHealthDataException.class, () -> healthCalc.calculateMAP(120, pad));
        }
    }
}