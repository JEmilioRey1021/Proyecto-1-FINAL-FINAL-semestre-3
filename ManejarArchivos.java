
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

/**
 * Manejo de archivos
 */
public class ManejarArchivos {
    
    String PATH_FILE = "";

    public ManejarArchivos(){
        
    }
    
    /***
     * Constructor para el path del archivo
     * @param path de la ruta del archivo
     */
    public void setPathFile(String path){
        PATH_FILE = path;
    }
    
    /***
     * Obtener contenido del archivo
     *  @param ruta archivo
     * @return string con los datos del archivo
     */
    public String getDataFile(){
        BufferedReader reader;
        String linea,datos = "";
        
        try{
            reader = new BufferedReader(new FileReader(PATH_FILE));
                
            while((linea = reader.readLine()) != null){
                //concatenamos con un tabular y se eliminara al separar las expresiones.
                datos += linea + "\n";
            }
            
            // Cerramos la conexion
            reader.close();
            
        }
        //error
        catch(Exception e){
            e.printStackTrace();
        }
        
        return datos;
    }
    
    /***
     * Verificar que el archivo EXISTA
     * @return true si el archivo existe, false si el archivo no existe
     */
    public boolean getExists(){
        return (new File(PATH_FILE)).exists();
    }
    
    
    /**
     * Regresar el ARRAYLIST con TOKENS
     * @return Array con los tokens crados, según el delimitador
     */
    public List getTokens(String delimitador){
        
        return Collections.list(new StringTokenizer(getDataFile().replaceAll("[", "").replaceAll("]", "").replaceAll("\\(", " ( ").replaceAll("\\)", " ) ").trim(), delimitador)).stream()
        .map(token -> (String) token)
        .collect(Collectors.toList());
        
    }
    
    /**
     * Parseo de la instrucción enviada por medio del delimitador enviado
     * @return lista de la instrucción parseada
     */
    public List getTokens(String delimitador,String value){
        String tempValue = value.replaceAll("\\,", " ").replaceAll("\\[", "(").replaceAll("\\]", ")");
        List tempList = Collections.list(new StringTokenizer(tempValue.replaceAll("\\(", " ( ").replaceAll("\\)", " ) ").trim(), delimitador)).stream()
            .map(token -> (String) token)
            .collect(Collectors.toList());
        return tempList;
        
    }
    
    /**
     * Conversión de String al valor en su tipo de dato.
     * @return Objeto con el tipo nativo del valor
     */
    private Object getAtom(String token) {
        try {
            //entero -> int
            return Integer.parseInt(token);
	} 
        catch (NumberFormatException e) {
            try {
                //flotante -> float
                return Float.parseFloat(token);
            } 
            catch (NumberFormatException e2) {
                try {
                    //double --> decimal
                    return Double.parseDouble(token);
		} 
                catch (NumberFormatException e3) {
                    //no numerico -> string
                    return token;
		}
            }
	}
    }
    
    /**
     * Parseo en forma de arreglos PERO DEL LENGUAJE LISP
     * @return Objeto que contiene una lista con el arreglo del lenguaje LISP
     * @throws Exception 
     */
    public Object getInstruccion(List instruccion) throws Exception {
        
        
        
        //ver si esta vacia la lista
        if (instruccion.isEmpty()) {
            throw new IllegalArgumentException("La instrucción no es valida.");
	}
	
        //primer valor se almacena en la variable
        String token = instruccion.remove(0).toString();
        
        //verificar si es ( para un subarreglo
	if (token.equals("(")) {
            
            //Creamos un ArrayList de tipo objeto con el tamaño de la lista 
            List<Object> tempList = new ArrayList<Object>(instruccion.size() - 1);
            try{
                

                //Loop hasta encontrar )
                while (!instruccion.get(0).equals(")")){
                    tempList.add(getInstruccion(instruccion));
                }

                //remover el primer valor 
                instruccion.remove(0);


                if(instruccion.get(0).equals("(") && instruccion.size() > 1){
                    tempList.add(getInstruccion(instruccion));
                }
            
                //subarreglo para almacenarlo en la lista.
                return tempList;
            }
            catch(Exception e){
                return tempList;
            }
            
	} 
        else if (token.equals(")")) {
            throw new Exception("No se encontro ).");
	} 
        else {
            //retornamos el valor para almacenarlo en el arreglo
            return getAtom(token);
	}
        
    }
 
    public List<Object> getListInstruccion(){
        String tempInstruction = "";
        char[] caracteres = (getDataFile().replace("\n","")+"\n").toCharArray();
        
        List<Object> listas = new ArrayList<Object>();
        
        for(int control = 0; control < caracteres.length;control++){
            
            if(caracteres[control] == ')'){
                tempInstruction += caracteres[control];
                
                if(control < (caracteres.length - 2)){
                   if(caracteres[control + 1] == '('){
                       listas.add(tempInstruction);
                        tempInstruction = "";
                   }
                }
                
            }
            else if(caracteres[control] == '\n'){
                listas.add(tempInstruction);
                tempInstruction = "";
            }
            else{
                tempInstruction += caracteres[control];
            }
             
            
        }
        
        return listas;
    }
    
}