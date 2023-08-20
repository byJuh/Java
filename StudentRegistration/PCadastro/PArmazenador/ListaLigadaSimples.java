package PCadastro.PArmazenador; 

import java.io.Serializable;
/**
 * ListaLigadaSimples. Ed para Lista Ligada simples
 * 
 * @author Julio Arakaki 
 * @version 1.0 2023/05/15
 */
public class ListaLigadaSimples implements IArmazenador, Serializable{
    No inicio, fim;
    int qtdNos;

    /**
     * ListaLigadaSimples Construtor
     *
     */
    public ListaLigadaSimples(){
        setInicio(null);
        setFim(null);
        setQtd(0);
    }

    /**
     * setInicio
     *
     * @param inicio No inicio da lista ligada
     */
    private void setInicio(No inicio){
        this.inicio = inicio;
    }

    /**
     * getInicio
     *
     * @return No de inicio da lista ligada
     */
    public No getInicio(){
        return(this.inicio);
    }

    /**
     * getFim
     *
     * @return No do final da lista ligada
     */
    public No getFim(){
        return(this.fim);
    }

    /**
     * setFim
     *
     * @param fim No fim da lista ligada
     */
    private void setFim(No fim){
        this.fim = fim;
    }

    /**
     * getQtdNos
     *
     * @return int, qtde de nos na lista ligada
     */
    public int getQtd(){
        return this.qtdNos;
    }

    /**
     * setQtdNos
     *
     * @param qtdNos Um parâmetro
     */
    private void setQtd(int qtdNos){
        this.qtdNos = qtdNos;
    }

    /**
     * estaVazia
     *
     * @return boolean, true se estiver vazia e false caso contrario
     */
    public boolean estaVazia(){
        boolean vazia = false; 
        if (getInicio() == null){
            vazia = true;
        }
        return vazia;
    }

    /**
     * inserirInicio
     *
     * @param elem Objeto a ser inserido no inicio da lista ligada
     */
    public void inserirInicio(Object elem) {
        No novo = new No(elem); //1

        if (estaVazia()){
            setInicio(novo);
            setFim(novo);
        }
        else{
            novo.setProximo(inicio);
            setInicio(novo);
        }
        setQtd(getQtd() + 1);
    }

    /**
     * inserirFim
     *
     * @param elem Objeto a ser inserido no fim da lista ligada
     */
    public void inserirFim(Object elem){
        No novo = new No(elem);
        if (estaVazia()){
            setInicio(novo);
            setFim(novo);
        }
        else{
            getFim().setProximo(novo);
            setFim(novo);
        }
        setQtd(getQtd() + 1);
    }

    /**
     * removerInicio
     *
     * @return Objeto removido
     */
    public Object removerInicio(){
        No aux = null;
        Object obj = null; 
        if(!estaVazia()){
            if ((getInicio() == getFim())){
                aux = getInicio();
                setInicio(null);
                setFim(null);
            } else {
                aux = getInicio();
                setInicio(aux.getProximo());
                aux.setProximo(null);
            }
            setQtd(getQtd() - 1);
            obj = aux.getConteudo();
        }

        return(obj);
    }

    /**
     * removerFim
     *
     * @return Objeto removido
     */
    public Object removerFim(){
        No ant = getInicio();
        No aux = null;
        Object obj = null; 
        if (!estaVazia()){
            if ((getInicio() == getFim())){
                aux = getInicio();
                setInicio(null);
                setFim(null);
            } else {            
                // percorre ate achar o no antes do fim
                while(ant.getProximo() != fim){
                    ant = ant.getProximo();
                }
                ant.setProximo(null);
                aux = fim;
                setFim(ant);
            }
            setQtd(getQtd() - 1);
            obj = aux.getConteudo();           
        }
        return obj;
    }

    /**
     * removerFim
     *
     * @return Objeto removido
     */
    public Object removerPos(int i){
        No ant = getInicio();
        No aux = null;
        Object obj = null;

        if (!estaVazia()){
            No f = getInicio();
            int count = 0;
            while(f.getProximo() != null && count != i){
                ant = f;
                f = f.getProximo();
                count++;
            }

            if(count == i){
                if ((getInicio() == getFim())){ //so tem um item na lista
                    aux = getInicio();
                    setInicio(null);
                    setFim(null);
                } else if(getInicio() == f){   //remove no inicio se tem mais um elemento 
                    aux = f.getProximo();
                    setInicio(aux);
                }else{
                    ant.setProximo(f.getProximo());
                    aux = fim;
                    if(f == getFim()){
                        setFim(ant);
                    }
                }
            }
            setQtd(getQtd() - 1);
            obj = aux.getConteudo();           
        }
        return obj;
    }

    public void adicionar(Object a){
        inserirFim(a);
    }

    public Object remover(int i){
        Object obj = null;
        obj = removerPos(i);
        
        return obj;
    }

    public Object buscar (int i){
        Object obj = null;
        
        if (!estaVazia()){
            No f = getInicio();
            int count = 0;
            while(f.getProximo() != null && count != i){
                f = f.getProximo();
                count++;
            }
            
            if(count == i){
                obj = f.getConteudo(); 
            }
        }
        return obj;
    }
}