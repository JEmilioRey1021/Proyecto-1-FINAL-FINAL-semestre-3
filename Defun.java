import javax.naming.NamingEnumeration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Defun {

    private String funName = "";
    private HashMap<String, Object> variables = new HashMap<>();
    private List<Object> instructions;

    /**
     * Constructor
     * @param funName = nombre de la funcion
     * @param vars Mapa con las variables de la funcion
     * @param inst Instrucciones de la funcion
     */
    public Defun(String funName, Object vars, Object inst){

        List<String> variables = new ArrayList<>();
        variables.add(vars.toString());
        List instructions = (List) inst;
        this.funName = funName;
        for (String item: variables){
            this.variables.put(item, null);
        }
        this.instructions = instructions;
    }

    /**
     * Ejecuta funcion
     * @param variables lista con las variables de la funcion
     * @return Devuelve la instruccion con las variables constantes
     */
    public List<Object> executeInstructions(List variables){
        List tempInstructions = this.instructions;
        HashMap<String, Object> tempVariables = this.variables;//Mapa temporal para almacenar el valor constante de cada variable
        if (variables.size() == tempVariables.size()){//verifica si la lista es del tamaño necesario para ejecutar la funcion
            int i = 0;
            for (String key: tempVariables.keySet()) {//Toma cada elemento del map y le asigna un Value
                tempVariables.replace(key, variables.get(i));
                i++;
            }
            i = 0;
            while (i < tempInstructions.size()){
                if ((tempInstructions.get(i) instanceof String)){//Toma cada elemento de las instrucciones
                    for (String key: tempVariables.keySet()) {//Toma cada elemento del map
                        if (tempInstructions.get(i).equals(key)){//Si el elemento de la instruccion es igual a algun Key del mapa lo reemplaza por su Value
                            tempInstructions.add(i, tempVariables.get(key));
                            tempInstructions.remove(i + 1);
                        }
                    }
                } else if (tempInstructions.get(i) instanceof ArrayList){
                    List subInstruction = (List)tempInstructions.get(i);
                    int j = 0;
                    while (j < subInstruction.size()){
                        if ((subInstruction.get(j) instanceof String)){//Toma cada elemento de las instrucciones
                            for (String key: tempVariables.keySet()) {//Toma cada elemento del map
                                if (subInstruction.get(j).equals(key)){//Si el elemento de la instruccion es igual a algun Key del mapa lo reemplaza por su Value
                                    subInstruction.add(j, tempVariables.get(key));
                                    subInstruction.remove(j + 1);
                                }
                            }
                        }
                        j++;
                    }
                    tempInstructions.add(i, subInstruction);
                    tempInstructions.remove(i + 1);
                }
                i++;
            }
            return tempInstructions;

        } else {//Si la cantidad de variables es menor no se ejecutara el codigo.

        }

        return tempInstructions;
    }

    public String getFunName(){
        return this.funName;
    }

}

