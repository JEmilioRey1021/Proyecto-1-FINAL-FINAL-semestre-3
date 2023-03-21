import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

public class ManejarArchivosTest {
    
    private ManejarArchivos manejador;
    
    @Before
    public void inicializar(){
        manejador = new ManejarArchivos();
        manejador.setPathFile("test.txt");
    }
    
    @Test
    public void testGetDataFile() {
        String data = manejador.getDataFile();
        Assert.assertEquals("Esto es un test.\n", data);
    }
    
    @Test
    public void testGetExists() {
        boolean exists = manejador.getExists();
        Assert.assertEquals(true, exists);
    }
    
    @Test
    public void testGetTokens() {
        List<String> tokens = manejador.getTokens(" ");
        Assert.assertEquals(4, tokens.size());
        Assert.assertEquals("Esto", tokens.get(0));
        Assert.assertEquals("un", tokens.get(2));
        Assert.assertEquals("test.", tokens.get(3));
    }
    
    @Test
    public void testGetInstruccion() throws Exception {
        List instruccion = manejador.getTokens(" ", "(+ 1 2)");
        Object resultado = manejador.getInstruccion(instruccion);
        Assert.assertEquals(List.class, resultado.getClass());
        List<Object> lista = (List<Object>) resultado;
        Assert.assertEquals(3, lista.size());
        Assert.assertEquals("+", lista.get(0));
        Assert.assertEquals(1, lista.get(1));
        Assert.assertEquals(2, lista.get(2));
    }
    
}
