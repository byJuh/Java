package PCadastro;
import PCadastro.PAluno.*;
import PCadastro.PArmazenador.*;

import java.io.Serializable;
/**
 * CadastroAluno armazena os dados de todos os Alunos em um array,
 * remove, altera e verifica um aluno e o RA. Utilizando a classe Aluno.
 * 
 * @author João Pedro Figols, Julia Schmidt, Igor Matos, Leonardo Grupioni 
 * @version v3.0 11/04/2023
 */
public class CadastroAluno implements Serializable
{
    //Atributos (Variaveis de instancia)
    public IArmazenador armazenador;
    
    private static int i = 0;   //variavel que representa a última posicao que esta armazenado o aluno
    public static int count = 0; //contador de alunos presentes na array

    //construtor
    public CadastroAluno(){
        //armazenador = new ListaArray();
        //armazenador = new VetDin();
        armazenador = new ListaLigadaSimples();
    }

    /**
     * Método inserirAluno, cria um aluno
     *
     * @param nome String
     * @param idade Int
     * @param RA String
     * @param curso String
     * @param qtdDisciplinas Int
     * @param periodo Int
     */
    public void inserirAluno(String nome, int idade, String RA, String curso, int qtdDisciplinas, int periodo){
        
        //instancia o objeto da classe Aluno
        //chama o contrutor que recebe como parametros os dados necessarios para a criacao de um Aluno
        Aluno aluno = new Aluno(RA, nome, idade, curso, qtdDisciplinas, periodo); 
        
        if(!verificarAluno(aluno)){
            armazenador.adicionar(aluno);
            count++; //adicona um caso um aluno seja inserido
        }
    }
    
    public void inserirAlunoObjeto(Object objeto){
        armazenador.adicionar(objeto);
        count++;
    }

    /**
     * Método removerAluno, remove um aluno desejado
     *
     */
    public void removerAluno(){
        //remove um aluno deixando ele como null
        armazenador.remover(i-1);
        count--; //remove um caso um aluno seja removido
    }

    /**
     * Método alterarAluno, altera um aluno especifico
     *
     * @param op Int, a disciplina escolhida pelo usuario
     * @param nota double, a nota que o usuario deu como entrada
     */
    public void alterarAluno(int op, double nota){
        Aluno aluno = (Aluno) armazenador.buscar(i-1);
        //acrescenta no atributo nota da classe disciplinas, que esta sendo instancia em Aluno, a nota da disciplina especifica
        aluno.disciplinas[op-1].nota = nota;
    }

    /**
     * Método verificarAluno, verifica se o aluno ja existe no conjunto de array a partir do RA
     *
     * @param aluno, aluno ja criado, todos os seu dados
     * @return true ou false, true caso ja exista o RA, ou seja, o aluno
     */
    public boolean verificarAluno(Aluno aluno){
        int t = 0;
        i = 0;
        Aluno aluno1;
        
        if(count > 2){
            do{
                //verifica se tem algum aluno ja existe a partir do RA
                aluno1 = (Aluno) armazenador.buscar(i);
                if(aluno1 != null && aluno1.getRA().equals(aluno.getRA())){ 
                    t = 1;  //se for igual sai do loop
                }
                i++;
            }
            while(t == 0 && i < count);
            aluno1 = (Aluno) armazenador.buscar(i-1);
            if(aluno1 != null && aluno1.getRA().equals(aluno.getRA())){
                //existe um Aluno com o RA digitado
                return true;
            }
            else{
                //o Aluno ainda nao existe
                return false;
            }
        }

        return false;
    }

    /**
     * Método verificarRA, verifica se o RA existe ou nao
     *
     * @param RA String, RA do aluno escolhido pelo usuario
     * @return true ou false, true caso nao exista esse aluno
     */
    public boolean verificarRA(String RA){
        int t = 0;
        i = 0;
        Aluno aluno1;
        
        if(count > 0){
            do{
                aluno1 = (Aluno) armazenador.buscar(i);
                //verifica se o RA existe
                if(aluno1 != null && aluno1.getRA().equals(RA)){ 
                    t = 1;
                }
                i++;
            }
            while(t == 0 && i < count);
            aluno1 = (Aluno) armazenador.buscar(i-1);
            if(aluno1 != null && aluno1.getRA().equals(RA)){
                //se existe pode remover ou alterar o Aluno que pertence ao RA
                return true;
            }
            else{
                //se nao existe entao nao pode fazer fazer nada
                return false;
            }
        }

        return false;
    }

    /**
     * Método getQtdDisciplinasAluno, quantidade de disciplinas do aluno 
     *
     * retorna a quantidade disciplinas associada ao aluno especifico a partir da classe Aluno
     */
    public int getQtdDisciplinasAluno(){
        Aluno aluno1;
        aluno1 = (Aluno) armazenador.buscar(i-1);
        return aluno1.getQtdDisciplinas();
    }
    
    /**
     * Método toStringDisciplinas
     *
     * Retorna os atributos como String (Menu disciplinas - setNotas)
     */
    public String toStringDisciplinas(){
        String s ="";
        int aux = 0;
        Aluno aluno1;
        aluno1 = (Aluno) armazenador.buscar(i-1);
        for(int j = 0; j < aluno1.getQtdDisciplinas(); j++){
            aux = j + 1;
            s = s + aux + ".disciplina" + aux + "\n";
        }
        s = s + (aux+1) + ". Sair" + "\n";
        return s;
    }
}