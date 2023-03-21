import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EvaluarFuncionesTest {

    private EvaluarFunciones evaluarFunciones = new EvaluarFunciones();

    @Test
    public void testIsAtom() {
        assertTrue(evaluarFunciones.isAtom(1));
        assertTrue(evaluarFunciones.isAtom(1.5f));
        assertTrue(evaluarFunciones.isAtom(1.5));
        assertTrue(evaluarFunciones.isAtom("hola"));
        assertTrue(evaluarFunciones.isAtom("'hola"));
        assertFalse(evaluarFunciones.isAtom(new ArrayList<>()));
        assertFalse(evaluarFunciones.isAtom(null));
    }

    @Test
    public void testToList() {
        List<Object> lista = Arrays.asList(1, "hola", 3.5f);
        List<Object> resultado = evaluarFunciones.toList(lista);
        assertEquals(lista, resultado);
    }

    @Test
    public void testIsEqual() {
        assertTrue(evaluarFunciones.isEqual(1, 1));
        assertTrue(evaluarFunciones.isEqual("hola", "hola"));
        assertFalse(evaluarFunciones.isEqual(1, 2));
        assertFalse(evaluarFunciones.isEqual("hola", "adios"));
    }

}
