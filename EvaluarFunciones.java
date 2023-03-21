import java.util.List;

/**
 * Manejo de evaluaciones
 */
public class EvaluarFunciones {
    
    /**
     * Metodo para verificar el atom del objeto pasado, según la sintaxis de LISP
     * @param value dato para evaluar si cumple con la condición de LISP
     * @return verdadero si cumple, falso si no.
     */
    public boolean isAtom(Object value){
        try {
            //entero -> int
            if((Integer)Integer.parseInt(value.toString()) instanceof Integer){
                return true;
            }
	    } 
        catch (NumberFormatException e) {
            try {
                //flotante -> float
                if((Float)Float.parseFloat(value.toString()) instanceof Float){
                    return true;
                }
            } 
            catch (NumberFormatException e2) {
                try {
                    //double -> decimal
                    if((Double)Double.parseDouble(value.toString()) instanceof Double){
                        return true;
                    }
		        } 
                catch (NumberFormatException e3) {
                    //no numero -> string
                    try{
                        String valor = value.toString();
                        if(valor instanceof String){
                            if(Character.toString(valor.charAt(0)).equals("'")){
                                return true;
                            }
                            //Si no cumple 
                            return false;
                        }
                    }
                    catch(Exception e4){
                        return false;
                    }
		        }
            }
	    }
        return false;
    }

    /**
     * Hacer una lista
     * @param lista que sera devuelta por el metodo
     */
    public List<Object> toList(List<Object> values){
        return values;
    }

    /**
     * Comparar dos objetos
     * @param a
     * @param b
     * @return
     */
    public boolean isEqual(Object a, Object b){
        return a.equals(b);
    }

    public boolean isGreaterThan (Object a, Object b){
        return (Double.parseDouble(a.toString()) > Double.parseDouble(b.toString()));
    }

    public boolean isLessThan(Object a, Object b){
        return (Double.parseDouble(a.toString()) < Double.parseDouble(b.toString()));
    }

    public Object cond(List instructions){
        List subList = instructions.subList(1, instructions.size());
        List subList2 = (List) subList.get(0);
        int i = 0;
        for (Object inst: subList2) {
            List instruccion = (List)inst;
            if (instruccion.contains("equal")){
                if (isEqual(instruccion.get(1), instruccion.get(2))){
                    return instruccion.get(3);
                }
            } else if (instruccion.contains("<")){
                if (isLessThan(instruccion.get(1), instruccion.get(2))){
                    return instruccion.get(3);
                }
            } else if (instruccion.contains(">")){
                if (isGreaterThan(instruccion.get(1), instruccion.get(2))){
                    return instruccion.get(3);
                }
            } else if (i == subList2.size()){
                return subList2.get(i);
            }
            i++;
        }
        return null;
    }
    
}
