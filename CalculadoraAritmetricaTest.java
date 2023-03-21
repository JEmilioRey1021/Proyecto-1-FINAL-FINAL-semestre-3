import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CalculadoraAritmetricaTest {

    private final CalculadoraAritmetrica calculadora = new CalculadoraAritmetrica();

    @Test
    void testSuma() {
        List<Object> prefixList = Arrays.asList("+", 2, 3);
        Double resultadoEsperado = 5.0;
        Double resultadoObtenido = calculadora.calcular(prefixList);
        Assertions.assertEquals(resultadoEsperado, resultadoObtenido);
    }

    @Test
    void testResta() {
        List<Object> prefixList = Arrays.asList("-", 5, 3);
        Double resultadoEsperado = 2.0;
        Double resultadoObtenido = calculadora.calcular(prefixList);
        Assertions.assertEquals(resultadoEsperado, resultadoObtenido);
    }

    @Test
    void testMultiplicacion() {
        List<Object> prefixList = Arrays.asList("*", 2, 3);
        Double resultadoEsperado = 6.0;
        Double resultadoObtenido = calculadora.calcular(prefixList);
        Assertions.assertEquals(resultadoEsperado, resultadoObtenido);
    }

    @Test
    void testDivision() {
        List<Object> prefixList = Arrays.asList("/", 6, 3);
        Double resultadoEsperado = 2.0;
        Double resultadoObtenido = calculadora.calcular(prefixList);
        Assertions.assertEquals(resultadoEsperado, resultadoObtenido);
    }

    @Test
    void testExpresionCompleja() {
        List<Object> prefixList = Arrays.asList("+", "*", 2, 3, "/", 6, 3);
        Double resultadoEsperado = 8.0;
        Double resultadoObtenido = calculadora.calcular(prefixList);
        Assertions.assertEquals(resultadoEsperado, resultadoObtenido);
    }

    @Test
    void testExpresionConSublista() {
        List<Object> prefixList = Arrays.asList("+", "*", 2, 3, Arrays.asList("-", 6, 3));
        Double resultadoEsperado = 11.0;
        Double resultadoObtenido = calculadora.calcular(prefixList);
        Assertions.assertEquals(resultadoEsperado, resultadoObtenido);
    }
}

