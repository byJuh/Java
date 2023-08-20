package PPersistencia;
import java.io.*;
/**
 * @author julio
 * Classe para gravar e ler arquivo binario
 */
public class ArquivoBinario implements Persistencia{
    /**
     * Method gravarObj grava um objeto em arquivo
     *
     * @param objeto objeto a ser gravado
     * @param nomeArq nome do arquivo
     */
    public void gravarObj(Object objeto, String nomeArq){
        //grava no formato obj, consegue gravar qualquer coisa
        ObjectOutputStream output = null;   //para gravacao
        try {
            File file = new File(nomeArq);  //associa o arquivo q fornece a um nome qualquer
            output = new ObjectOutputStream(new FileOutputStream(file));//cria uma instacia para ter acesso ao write
            output.writeObject(objeto);  // escreve o objeto no arquivo
        } catch(Exception e){
            //System.out.println(e.toString());
        } finally {
            try {
                output.close();
            } catch(Exception ex) {
                // Nao faz nada!
            }
        }
    }

    /**
     * Method lerObj
     *
     * @param nomeArq nome do arquivo a ser lido
     * @return Object o objeto lido
     */
    public Object lerObj(String nomeArq){
        Object objeto = null;
        ObjectInputStream input = null;
        try {
            File file = new File(nomeArq);
            input = new ObjectInputStream(new FileInputStream(file));
            objeto = (Object)input.readObject();  //le o objeto do arquivo
        }
        catch(Exception e){
            //System.out.println(e.toString());
        } finally {
            try {
                input.close();
            } catch(Exception ex) {
                // Nao faz nada!
            }
        }
        return objeto;
    }
}
