 
package PCadastro.PAluno;
import java.io.Serializable;
/**
 * Represento uma pessoa. Utiliza a classe NomePessoa
 * 
 * @author Julio Arakaki, Julia Schmidt, João Pedro Figols, Igor Matos, Leonardo Grupioni 
 * @version v2.0 21/03/2023
 */
public class Pessoa implements Serializable{
    // variáveis de instância 
    private NomePessoa nome;
    private int idade;

    /**
     * Construtor para objetos da classe Pessoa
     */
    public Pessoa(String nome, int idade){
        setNomeP(nome);
        setIdade(idade);
    }
    
    //getters e setters
    private void setNomeP(String nome) {
        this.nome = new NomePessoa(nome);
    }

    private void setIdade(int idade) {
        this.idade = idade;
    }
    
    public String getNome(){
        return this.nome.getNome();
    }

    public int getIdade(){
        return(this.idade);
    }

}
