import java.util.Scanner;

public class Aplicacao{
    public static void main(String args[]){
        String opnd1 = "";
        String opnd2 = "";
        String operador = "";
        String resp = "";
        double result = 0;
        int aux = 0;

        // armazena o tamanho do vetor de string (args) - qtde de parametros
        int tam = args.length; 

        // Verifica a qtde de parametros
        if (tam == 3) { // entrada pela linha de comando
            opnd1 = args[0];
            operador = args[1];
            opnd2 = args[2];
            // verifica operador
            if(!Utils.validarOperador(operador)) {
                System.out.println("Operador invalido!!");
                return;
            }
            // verifica operando 1
            if(!(Utils.validarOperando(opnd1))) {
                System.out.println("Operando 1 invalido!!");
                return;
            }

            if(!(Utils.validarOperando(opnd2))) {
                System.out.println("Operando 2 invalido!!");
                return;
            }
        } else if (tam == 0){ 

            // Entrada pelo teclado (console)
            Scanner scanner = new Scanner(System.in);
            System.out.print("Forneca operando1: ");
            opnd1 = scanner.next();

            // verifica operando 1, podendo ser um numero ou seno
            if(!Utils.validarOperando(opnd1) && !Utils.validarSeno(opnd1)) {
                System.out.println("Operando 1 invalido!!");
                return;
            }

            System.out.print("Forneca operador: ");
            operador = scanner.next();
            // verifica operador
            if(!Utils.validarOperador(operador) && !Utils.validarSeno(operador) && !Utils.validarOperando(operador) || (Utils.validarSeno(opnd1) && operador.equals("!"))) {
                System.out.println("Operador invalido!!");
                return;
            }

            //verifica se uma das contas eh da calculadora cientifica (sen ou fatorial)
            if (!Utils.validarSeno(opnd1) && !operador.equals("!") && !Utils.validarSeno(operador)){
                System.out.print("Forneca operando2: ");
                opnd2 = scanner.next();
                // verifica operando2           
                if(!(Utils.validarOperando(opnd2))) {
                    System.out.println("Operando 2 invalido!!");
                    return;
                }
            }
        } else if(tam==2 && !(args[0].equals("sen") && args[1].equals("!"))){
            if(args[0].equals("!")){
                opnd1 = args[1];
                operador = args[0]; 
            }
            else{
                opnd1 = args[0];
                operador = args[1]; 
            }
        }
        else{
            System.out.println("Parametros invalidos!!");
            return;
        }

        // Instancia (cria) objeto da classe CalculadoraCientifica
        CalcCien calcCient = new CalcCien();

        //verifica se o operando2 recebeu algum valor para representar a forma da saida
        if(opnd2 == ""){
            System.out.print("("+opnd1 + " " + operador + ") = ");
            System.out.println(calcCient.calcularCien(opnd1, operador, opnd2));
        }
        else{
            System.out.print("("+opnd1 + " " + operador + " " + opnd2 + ") = ");
            System.out.println(calcCient.calcularCien(opnd1, operador, opnd2));

        }

    }
}
