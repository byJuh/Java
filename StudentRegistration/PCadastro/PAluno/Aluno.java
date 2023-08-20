package PCadastro.PAluno;   


import java.io.Serializable;
/**
 * Aluno armazena cada dados em seus atributos, utilizando a classe Disciplinas para 
 * armazenas as disciplinas e as notas.
 * Atributos: RA, nome, idade, curso, qtdDisciplinas e periodo
 * 
 * @author João Pedro Figols, Julia Schmidt, Igor Matos, Leonardo Grupioni 
 * @version v3.0 11/04/2023
 */

public class Aluno extends Pessoa implements Serializable{
    //Atributos (variaveis de instancia)
    private String RA, curso;
    private int periodo, qtd;
    public Disciplina disciplinas[]; //criacao de um array do tipo Disciplina

    /**
     * Aluno Construtor
     *
     * @param RA String
     * @param nome String
     * @param idade Int
     * @param curso String
     * @param qtdDisciplinas Int
     * @param periodo Int
     */
    public Aluno(String RA, String nome, int idade, String curso, int qtdDisciplinas, int periodo){
        super(nome, idade);     //chama o construtor da classe mae, nesse caso a classe Pessoa
        setRA(RA);              //altera o valor do RA
        setCurso(curso);        //altera o curso 
        setDisciplina(qtdDisciplinas);  //altera as disciplinas  
        setPeriodo(periodo);            //altera o valor do periodo
        setQtdDisciplinas(qtdDisciplinas);  //altera o valor da quantidade de disciplinas
    }

    //getters e setters
    private void setRA(String RA){
        this.RA = RA;
    }

    public String getRA(){
        return this.RA;
    }

    private void setPeriodo(int periodo){
        this.periodo = periodo;
    }

    public int getPeriodo(){
        return this.periodo;
    }

    /**
     * Método setDisciplina
     *
     * @param qtd Int, quantidade de disciplinas no curso
     */
    private void setDisciplina(int qtd){
        //instancia um array do tipo Disciplina
        disciplinas = new Disciplina[qtd];
        //variaveis de instancias
        
        //loop para instanciar cada posicao do Array
        for(int j = 0; j < qtd; j++){
            disciplinas[j] = new Disciplina(); //instancia o objeto em cada uma das posicoes do array
        }
    }

    private void setQtdDisciplinas(int qtd){
        this.qtd = qtd;
    }

    public int getQtdDisciplinas(){
        return this.qtd;
    }

    public Disciplina[] getDisciplina(){
        return disciplinas;
    }

    private void setCurso(String curso){
        this.curso = curso;
    }

    public String getCurso(){
        return this.curso;
    }
    
    /**
     * Método toString
     *
     * Retorna os atributos como String (RA, nome, idade, curso e periodo)
     */
    public String toString(){
        String s;
        s = "\nRA:" + getRA() + "\n" +
        "Nome: " + getNome() + "\n" + 
        "Idade: " + getIdade() + "\n" + 
        "Curso: " + getCurso() + "\n" +
        "Periodo: " + getPeriodo() + "\n";

        return s;
    }

    /**
     * Método mostrarDisciplinasNotas
     *
     * Retorna os atributos com String (Notas e disciplinas)
     */
    public String mostrarDisciplinasNotas(){
        String s ="";
        
        for (int i=0;i<disciplinas.length;i++){
            int aux = i + 1;
            s = s + "disciplina" + aux + ": " + disciplinas[i].getNota() + "\n";
            
        }
        return s;
    }
}
