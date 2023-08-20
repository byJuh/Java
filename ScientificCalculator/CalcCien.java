import java.lang.Math;
/**
 * Escreva uma descrição da classe Calccien aqui.
 * 
 * @author (seu nome) 
 * @version (um número da versão ou uma data)
 */
public class CalcCien{
    public double calcularCien(String opnd1, String oper, String opnd2){
        double operando1 = 0;
        double operador = 0;
        double result = 0;

        if (opnd1.equals("sen") || oper.equals("sen")){
            result = calcularSeno(opnd1, oper);
        }
        else if (oper.equals("!")){
            result = calcularFatorial(opnd1, oper);
        }
        else{
            Calculadora calc = new Calculadora();
            result = calc.calcular(opnd1, oper, opnd2); 
        }

        return result;
    }

    public double calcularFatorial(String opnd1, String oper){

        double operador1 = 0;
        double result = 0;
        operador1 = Double.parseDouble(opnd1);
        double fat = operador1;
        double i = fat - 1;
        

        while(i > 1){
            fat = fat * i;
            i--;
        }
        result = fat;

        return result;
    }

    public double calcularSeno(String opnd1, String oper){
        double grau = 0;
        double result = 0;
        double operando1 = 0;

        if(opnd1.equals("sen")){
            operando1 = Double.parseDouble(oper);
        }
        else{
            operando1 = Double.parseDouble(opnd1);
        }

        grau = Math.toRadians(operando1);
        result = Math.sin(grau);

        return result;
    }
}
