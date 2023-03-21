import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * INTERPRETE LISP. PROGRAMA MAIN.
 */

public class InterpreteLisp {
    
    final static String DELIMITADOR = " \t\n\r\f";
    public static void main(String[] args) { 
        int opcion = 0;
        try{
            while(true){
                System.out.println("\n\t\tMenú");
                System.out.println("1) Ejecutar LISP");
                System.out.println("2) Salir");

                System.out.print("Ingrese la opción: ");
                //lectura con clase teclado.
                opcion = Teclado.readInt();
                switch(opcion){
                    case 1:
                        String path = "";
                        System.out.print("Ingrese el Path del archivo: ");
                        
                        path = Teclado.readString();
                        
                        //crear un objeto del manejador de datos
                        ManejarArchivos archivo = new ManejarArchivos();
                        
                        //Seteamos el path del archivo
                        archivo.setPathFile(path);
                        
                        //verifica existencia
                        if(archivo.getExists()){
                            System.out.println(String.format( "\n\n\t\tExpresión a Evaluar: \n\n%s\n",archivo.getDataFile()));
                            runLisp(archivo.getListInstruccion());
                        }
                        else{
                            System.out.println(String.format("\n\t\tEl archivo de la ruta %s no fue encontrado", path));
                        }
                        break;

                    case 2:
                        //Salir
                        System.exit(0);
                }
            
            }
        }
        catch(Exception e){
            System.out.println(String.format("\n\n\t\tOcurrio el problema: %s",e.toString()));
        }
        
    }

    public static int factorial(int n) {
        if (n == 0) {
            return 1;
        } else {
            return n * factorial(n-1);
        }
    }
    
    
    /**
     * Ejecutar la instrucción de LISP
     *  @param value objeto con las instrucciones de LISP
     */
    public static void runLisp(Object value) throws Exception{
        try{
            //Se castea el objeto a tipo List y se asigna a una variable List
            List instruccions = (List)value;
            List tempIns = new ArrayList();
            
            //se instancia objeto de manejador de datos
            ManejarArchivos archivo = new ManejarArchivos();
            
            for(int control=0;control<instruccions.size();control++){
                tempIns.add(archivo.getInstruccion(archivo.getTokens(DELIMITADOR, instruccions.get(control).toString() )));
                
            }
            List<Defun> deFun = new ArrayList<>();
            for (Object i: tempIns) {
                List instruccion = null;
                if(i instanceof ArrayList){
                    instruccion = (List) i;
                }
                else if(i instanceof String){
                    instruccion = Arrays.asList(tempIns.toString().split(" "));
                }
                else{
                     instruccion = tempIns;
                }


                if(instruccion.contains("atom")){

                    //Si el tamaño es de 2, la sintaxis de LISP para atom es correcta
                    if(instruccion.size() == 2 ){
                        if( (new EvaluarFunciones()).isAtom(instruccion.get(1))){
                            System.out.print("\n\t\tResultado: True\n\n");
                        }
                        else{
                            System.out.print("\n\t\tResultado: NIL\n\n");
                        }
                    }
                    else{
                        System.out.println("La función de atom tiene erroes de sintaxis");
                    }
                }
                else if(instruccion.contains("defun")){//Crea una funcion
                    List subList = (List) instruccion.get(2);
                    Defun newFunc = new Defun(instruccion.get(1).toString(), subList.get(0), subList.get(1));
                    deFun.add(newFunc);//Agrega la funcion al array de funciones
                } else if (instruccion.contains("list")){//Devuelve una lista con los valores ingresados
                    List<Object> list = new EvaluarFunciones().toList(instruccion.subList(1, instruccion.size()));
                    System.out.println(list);
                } else if (instruccion.contains("equal")){//Compara si son iguales
                    if( (new EvaluarFunciones()).isEqual(instruccion.get(1), instruccion.get(2))){
                        System.out.print("\n\t\tResultado: True\n\n");
                    }
                    else{
                        System.out.print("\n\t\tResultado: NIL\n\n");
                    }
                } else if (instruccion.contains(">")){//Verifica si es mayor 
                    if( (new EvaluarFunciones()).isGreaterThan(instruccion.get(1), instruccion.get(2))){
                        System.out.print("\n\t\tResultado: True\n\n");
                    }
                    else{
                        System.out.print("\n\t\tResultado: NIL\n\n");
                    }
                } else if (instruccion.contains("<")){//Verifica si es menor
                    if( (new EvaluarFunciones()).isLessThan(instruccion.get(1), instruccion.get(2))){
                        System.out.print("\n\t\tResultado: True\n\n");
                    }
                    else{
                        System.out.print("\n\t\tResultado: NIL\n\n");
                    }
                }
                else if (instruccion.contains("+") || instruccion.contains("-") || instruccion.contains("*") || instruccion.contains("/")){
                    CalculadoraAritmetrica calculator = new CalculadoraAritmetrica();
                    System.out.println("\n\t\tResultado: " + calculator.calcular(instruccion));
                    break;
                    //Despliegue temporal del parseo de las instrucciones
                } else if (instruccion.contains("cond")){

                    runLisp((new EvaluarFunciones()).cond(instruccion));
                
                }else if (instruccion.contains("factorial")) {
                    int n = Integer.parseInt(instruccion.get(1).toString());
                    int result = factorial(n);
                    System.out.println("\n\t\tResultado: " + result + "\n\n");
                
                } else {//Si no es ninguno revisa dentro de un array que contiene todas las definiciones de funciones
                    for (Defun fun: deFun) {
                        if (instruccion.contains(fun.getFunName())){
                            List<Object> tempSubIns = fun.executeInstructions(instruccion.subList(1, instruccion.size()));
                            
                            String sub_instruccion = String.format("(%s)", listToString(tempSubIns));
                            
                            runLisp( archivo.getInstruccion(archivo.getTokens( DELIMITADOR , sub_instruccion )) );
                        }
                    }
                }

            }

        }
        catch(Exception e){
            System.out.println("\n\n\tOcurrio un problema al evaluar la expreción. \n\tError: " + e.toString());
        }
    }
    
    /**
     * Convertir la instrucción LISP de una lista a String
     * @param value Lista de la instrucccion que se desea convertir
     * @return La instrucción en String
     */
    public static String listToString(List value){
        String tempSIns ="";
        List tempList = (List)value;
        for(int control = 0;control<tempList.size(); control++){
            if(tempList.get(control) instanceof List){
                tempSIns += "(";
                tempSIns += listToString((List)tempList.get(control)) + "\t";
                tempSIns += ")";
            }
            else{
                tempSIns += tempList.get(control) + "\t";
            }
            
        }
        
        return tempSIns;
    }
}
