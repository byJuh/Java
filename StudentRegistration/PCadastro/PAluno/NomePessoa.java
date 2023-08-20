 
package PCadastro.PAluno;
import java.io.Serializable;
/**
 * Represento o nome de uma pessoa. Utiliza a classe Texto
 * 
 * @author Julio Arakaki, Julia Schmidt, João Pedro Figols, Igor Matos, Leonardo Grupioni 
 * @version v2.0 21/03/2023
 */
public class NomePessoa implements Serializable{
    // variáveis de instância 
    private Texto nome;

    /**
     * Construtor para objetos da classe NomePessoa
     */
    public NomePessoa(String nome) {
        setNome(nome);
    }

    //getters e setters
    private void setNome(String nome) {
        this.nome = new Texto(nome);
    }

    public String getNome(){
        return(this.nome.getTxt());
    }

    /**
     * Retorna quantidade de palavras do nome
     * @return qtd numero de palavras
     */
    public int getQtdePalavras(){
        return (this.nome.getQtdePalavras());
    } 

    /**
     * Retorna um texto invertido
     * @return txtInvertido String contendo o texto invertido
     */
    public String inverterTexto(){
        return (this.nome.inverterTexto());
    }

    /**
     * Método getNomeBibliografico
     *
     * @return nomeBibliografico String contendo o nome bibliografico
     */
    public String getNomeBibliografico(){
        String nomeBibliografico = " ";
        String s = getNome();
        String vet[] = s.split(" ");
        int qtde = vet.length;

        nomeBibliografico = vet[qtde-1] + ", ";
        int aux = 0;
        char ch = ' ';
        do{
            ch = vet[aux].charAt(0);
            if(ch < 91) nomeBibliografico = nomeBibliografico + vet[aux].substring(0,1) + ".";
            aux++;            
        }while(aux < (qtde-1));

        return nomeBibliografico;
    }
}
