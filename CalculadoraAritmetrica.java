import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Calculos aritmetricos
 * realiza operaciones arimeticas
 */

public class CalculadoraAritmetrica {

    /**
     * Calculos
     * @param prefixList Lista con las instrucciones de list
     * @return valor de la evaluación
     */
    public Double calcular(List<Object> prefixList){
        
        Stack<Double> resultado = new Stack<>();
        
        String signo = String.valueOf(prefixList.get(0));
        
        for (int i = 1; i < prefixList.size(); i++){
            //Se Verificamos si el registro dentro de la lista es entero o double
            if(prefixList.get(i) instanceof Integer || prefixList.get(i) instanceof Double ){
                //el digito se va al stack
                resultado.push(Double.parseDouble(prefixList.get(i).toString()));
            }
            //evaluamos si el valor de la lista es un arraylist
            else if(prefixList.get(i) instanceof ArrayList){
                //calculo recursivo de la lista y se guarda el resultado en el stack
                resultado.push(calcular((List)prefixList.get(i)));
            }
        }
        
        if(signo.matches("[+]")){
            resultado.push(sumar(resultado));
        }
        if(signo.matches("[-]")){
            resultado.push(restar(resultado));
        }
        if(signo.matches("[*]")){
            resultado.push(multiplicar(resultado));
        }
        if(signo.matches("[/]")){
            resultado.push(dividir(resultado));
        }
        
        return resultado.peek();
    }
    
    /**
     * SUMA
     * @param value Stack para realizar calculo
     * @return calculador
     */
    public double sumar(Stack<Double> value){
        
        double a = 0.00;
        int lenstack = value.size();
        for(int control = 0; control<lenstack;control++){
            a += value.pop();
        }
        return a;
    }
    
    /**
     * RESTA
     * @param value Stack para realizar calculo
     * @return calculador
     */
    public double restar(Stack<Double> value){
        Stack<Double> temp_stack = revertirStack(value);

        double a = temp_stack.pop();
        int lenstack = temp_stack.size();
        
        for(int control = 0; control<lenstack;control++){
            a -= temp_stack.pop();
        }
        return a;
    }
    
    /**
     * PRODUCTO
     * @param value Stack para realizar calculo
     * @return calculador
     */
    public double multiplicar(Stack<Double> value){
        //colocamos el ultimo dato del stack como valor inicial
        double a = value.pop();
        int lenstack = value.size();
        for(int control = 0; control<lenstack;control++){
            a *= value.pop();
        }        
        return a;
    }
    
    /**
     * Operación de dividir
     * @param value Stack para realizar calculo
     * @return calculador
     */
    public double dividir(Stack<Double> value){
        Stack<Double> temp_stack = revertirStack(value);
        
        double a = temp_stack.pop();
        int lenstack = temp_stack.size();
        
        for(int control = 0; control<lenstack;control++){
            a /= temp_stack.pop();
        }
        
        return a;
    }
    
    /**
     * Revertir orden de la lista
     * @param value Stack<Double> a revertir
     * @return stack con nuevo orden
     */
    public Stack<Double> revertirStack(Stack<Double> value){
        Stack<Double> temp_stack = new Stack();
        while(!value.empty()){
            temp_stack.add((double)value.pop());
        }
        
        return temp_stack;
    } 
}
