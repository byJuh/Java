package PCadastro.PAluno;
import java.io.Serializable;
/**
 * Representa a disciplina que armazena disciplinas e notas do aluno
 * 
 * @author João Pedro Figols, Julia Schmidt, Igor Matos, Leonardo Grupioni 
 * @version v2.0 24/03/2023
 */
public class Disciplina implements Serializable
{
    //Atributos(valores de instancias)
    
    private Texto disciplina;
    public double nota;
    private static int i = 1;
    private String txt = " ";
    
    /**
     * Construtor: armazena as disciplinas
     *
     */
    public Disciplina(){
        setDisciplina("disciplina" + i);
        i++;
    }

    //getters e setters
    public void setDisciplina(String disciplina){
        this.disciplina = new Texto(disciplina);
    }

    public String getDisciplina(){
        return(this.disciplina.getTxt());
    }

    public double getNota(){
        return this.nota;
    }
}
