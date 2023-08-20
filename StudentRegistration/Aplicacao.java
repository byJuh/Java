import PEntrada.*;
import PPersistencia.*;
import PCadastro.PAluno.Aluno;
import PCadastro.CadastroAluno;
import PCadastro.PArmazenador.*;

/**
 * Menu que apresenta todos os tipos de entradas necessarias no decorrer do programa,
 * uma entrada de dados.
 * 
 * @author João Pedro Figols, Julia Schmidt, Igor Matos, Leonardo Grupioni 
 * @version v3.0 11/04/2023
 */
public class Aplicacao{
    int op = 0;     //variavel para a opcao selecionada no menu (inicializacao dele por zero)
    String aux = "";  //inicializacao do aux que armazena a resposta do usuario
    int aux1 = 0;   //variavel para verificar se já foi lido algum arquivo

    //Instanciacao de objetos:
    CadastroAluno cadastro = new CadastroAluno();  //instancia o objeto da classe CadastroAluno
    IEntrada ent = new EntradaGUI();      //Para entradas em modo grafico
    //IEntrada ent = new EntradaConsole();    //Para entradas em modo texto

    /**
     * Método Entrada, menu principal que possibilita ao usuario a manipulacao dos cadastros 
     *
     * @return op Int, a opcao de escolha do usuario do Menu:
     * op = 1, inserir
     * op = 2, remover
     * op = 3, listar
     * op = 4, alterar aluno
     * op = 5, salvar alunos
     * op = 6, ler arquivo
     * op = 7, sair
     * 
     */

    public void EntradaDados(){
        int sent = 1;
        int sentinela = 0;
        do{
            sent = 1;
            do{
                sentinela = 0;
                try{
                    //a variavel aux recebe uma entrada de dados
                    aux = ent.receberString(
                        "\n=======Menu=======\n" +
                        "   1. Inserir\n"+
                        "   2. Remover\n" +
                        "   3. Listar\n" +
                        "   4. Alterar aluno\n" +
                        "   5. Salvar aluno\n" +
                        "   6. Ler arquivo\n" +
                        "   7. Sair\n" +
                        "Escolha uma das opções anteriores: ");
                    op = Integer.parseInt(aux); //op recebe aux transformado de String para int
                }
                catch (NumberFormatException e){
                    //verifica se o usuario entrou com um valor invalido ou clicou em cancell
                    if(aux == null){//verifica se o usuario clicou para cancelar e sai do programa
                        ent.imprimirMsg("\nSaindo do programa...");
                        System.exit(0);
                    }
                    else{
                        ent.imprimirMsg("\nDigite numeros de 1 a 7!!");
                        sentinela = 1;
                    }
                }
            }while(sentinela != 0);

            switch (op){
                case 1:
                    int aux = inserir(); //auxiliar que armazena um valor retornado pela funcao (0 ou 1)
                    //se aux = 0 quer dizer que no modo grafico o usuario cancelou a entrada
                    if(aux != 0){
                        ent.imprimirMsg("\nO aluno foi salvo com sucesso!!!");
                    }
                    break;

                case 2:
                    if(CadastroAluno.count != 0){
                        String removerRA = RA();   //armazena o valor do RA selecionado
                        if(removerRA != null){
                            cadastro.removerAluno();    //chama a funcao removerAluno da classe CadastroAluno
                            ent.imprimirMsg("\nO aluno de RA: " + removerRA + "\nFoi removido com sucesso!!!");
                        }
                    }
                    else{
                        ent.imprimirMsg("Não há alunos cadastrados\n");
                    }
                    break;

                case 3:
                    //mostra a lista de todos os alunos ou de apenas um deles
                    if(CadastroAluno.count != 0){
                        mostrarLista();
                    }
                    else{
                        ent.imprimirMsg("Não há alunos cadastrados\n");
                    }
                    break;

                case 4:
                    if(CadastroAluno.count != 0){
                        String alterarRA = RA();   //pega o RA e o retorna ja verificado
                        if(alterarRA  != null){    
                            int qtd = cadastro.getQtdDisciplinasAluno(); //pega a quantidade de disciplinas atraves da funcao da classe CadastroAluno
                            int op1 = 0;    //inicializa a escolha do usuario
                            do{
                                op1 = setNotas(qtd); //funcao que retorna a opcao da disciplina escolhida, tendo como parametro a qtd de disciplinas
                                if(op1 > 0 && op1 < qtd+1){
                                    //chama a funcao alterarAluno da classe CadastroAluno
                                    cadastro.alterarAluno(op1, inserirNota(qtd, op1));  //altera os valores da nota do aluno escolhido pelo usuario 
                                }

                                if(op1 == -1){
                                    break;
                                }
                            }
                            while(op1 != qtd+1);
                        }
                    }
                    else{
                        ent.imprimirMsg("Não há alunos cadastrados\n");
                    }
                    break;

                case 5:
                    //salva os alunos cadastrados
                    salvarAlunos();
                    break;    

                case 6:
                    //le o arquivo desejado que apresenta os alunos cadastrados
                    if(aux1 == 0){
                        lerAlunos();
                    } else {
                        ent.imprimirMsg("Já foi lido um arquivo, não é possível ler outro. \nCaso deseje reinicie o programa!");
                    }
                    break;
                
                case 7:
                    //confirma se deseja ou nao sair do programa
                    confirmarSalvada();
                    confirmarSaida();
                    break;
                    
                default:
                    ent.imprimirMsg("Opção inválida!!");
                    sent = 1;
                    break;
            }
        }
        while(sent != 0);
    }

    /**
     * Método inserir, possibilita a entrada dos dados presente em um novo aluno (criacao)
     *
     * @return 0 ou 1, verificando se o usuario cancelou na entrada grafica
     */
    public int inserir(){
        String nome = entrarNome(); //string que armazena o nome inserido
        if(nome == null){ //if que verifica se foi clicado em cancelar
            return 0; //retorna 0, sai da funcao e volta ao menu   
        }
        int idade = entrarIdade(); //int que armazena a idade
        if(idade == -1){ //if que verifica se foi clicado em cancelar
            return 0; //retorna 0, sai da funcao e volta ao menu   
        }
        String RA = entrarRA(); //stirng que recebe o RA
        if(RA == null){ //if que verifica se foi clicado em cancelar
            return 0; //retorna 0, sai da funcao e volta ao menu   
        }
        String curso = entrarCurso(); //string que recebe o curso selecionado
        if(curso == null){ //if que verifica se foi clicado em cancelar
            return 0; //retorna 0, sai da funcao e volta ao menu   
        }
        int qtdDisciplinas = qtdDisciplinas(); //int que recebe a quantidade de disciplinas
        if(qtdDisciplinas == -1){ //if que verifica se foi clicado em cancelar
            return 0; //retorna 0, sai da funcao e volta ao menu   
        }
        int periodo = entrarPeriodo(); //int que recebe o periodo do aluno
        if(periodo == -1){ //if que verifica se foi clicado em cancelar
            return 0; //retorna 0, sai da funcao e volta ao menu
        }

        cadastro.inserirAluno(nome, idade, RA, curso, qtdDisciplinas, periodo); //acessa o cadastro de alunos e insere o aluno passando os parametros recebidos

        return 1; //retorna 1, indicando que o cadastro foi bem sucedido
    }

    /**
     * Método RA, entrada de RA
     *
     * @return RA String, o RA que o usuario digitou para o removerAluno ou alterarAluno
     */
    public String RA(){
        int sent = 0; //cria um sentinela para o loop
        String RA = ""; //String para receber os 8 digitos do RA
        do{
            try{
                sent = 0; //inicia como 0 para sair do loop se tudo ocorrer conforme o esperado
                RA = ent.receberString("Digite o RA: "); //recebe o RA inserido na entrada de dados
                //verifica se o ra foi inserido da maneira correta e envia um para o sentinela indicar o reinicio do loop
                if(RA.length() != 8){
                    ent.imprimirMsg("\nDigite 8 números para o RA!!!");
                    sent = 1;
                }
                else if(!RA.matches("\\d+")){
                    ent.imprimirMsg("\nDigite apenas números!!!");
                    sent = 1;
                }
                else if(!cadastro.verificarRA(RA)){
                    ent.imprimirMsg("\nEle não existe!!!");
                    sent = 1;
                }
            }
            // retorna ao menu e transforma o RA em null caso seja cancelado
            catch(NullPointerException e){
                ent.imprimirMsg("\nVoltando ao menu...");
                RA = null; 
            }
        }while(sent != 0);

        return RA;
    }

    /**
     * Método mostrarLista, lista apenas um ou todos os alunos
     *
     * @return true ou false (boolean), para verificar se o usuario cancelou a acao, para assim retornar ao menu principal
     */
    public boolean mostrarLista(){
        //inicia como 0 para sair do loop se tudo ocorrer conforme o esperado
        int sent = 0; //cria um sentinela para o loop

        do{
            sent = 0; 
            try{
                //recebe a selecao de qual opcao quer ser mostrada
                aux = ent.receberString(
                    "  1. Mostrar todos os alunos\n" +
                    "  2. Apenas um aluno\n" +
                    "Escolha uma das duas opções: ");
                op = Integer.parseInt(aux);

                if(op < 1 || op > 2){
                    ent.imprimirMsg("Opção inválida!!! Apenas 1 ou 2!!!");
                    sent = 1;
                }
            }
            //verifica exeções
            catch(NumberFormatException e){
                if(aux == null){ //caso seja clicado cancell retorna ao menu
                    ent.imprimirMsg("\nVoltando ao Menu...");
                    return false;
                }
                else{ 
                    ent.imprimirMsg("\nDigite numeros de 1 ou 2!!");
                    sent = 1;
                }
            }
        }
        while(sent != 0);

        //entra em um switch para executar o que foi solicitado
        switch(op){
            case 1: //imprime todos os alunos 
                String s = "";
                int i = 0; 
                s= "==== Alunos Cadastrados ====\n";
                Aluno aluno1;
                while(i < CadastroAluno.count){
                    aluno1 = (Aluno) cadastro.armazenador.buscar(i);
                    s = s + aluno1 + "-----------------------------------\n";
                    i++;
                }
                ent.imprimirAlunos(s);
                break;
            case 2: //imprime o aluno especifico com seu boletim 
                String RA = RA();
                s = "==== Aluno Cadastrado ====\n";
                Aluno aluno2;
                for(int j = 0; j < CadastroAluno.count; j++){
                    aluno2 = (Aluno) cadastro.armazenador.buscar(j);
                    if(aluno2.getRA().equals(RA)){
                        s = "-----------------------------------" + aluno2 + aluno2.mostrarDisciplinasNotas();
                        ent.imprimirAlunos(s);
                        sent = 1;
                    }
                    else if (sent == 1){
                        break;
                    }
                }
                break;
        }

        return true;
    }

    /**
     * Método entrarNome, entrada do nome do aluno
     *
     * @return nome String, retorna o nome do aluno digitado ou null caso tenha cancelado na entrada grafica e nao tenha digitado nada
     */
    public String entrarNome(){
        int sent = 0; //cria uma sentinela para o loop
        String nome = "";
        do{
            try{
                //recebe o nome inserido
                nome = ent.receberString("\nDigite o Nome: ");
                sent = 0;
                if((nome.split(" ").length) < 2){ //verifica se o nome inserio contem ao menos dois nomes (nome + sobrenome)
                    ent.imprimirMsg("\nDigite o nome completo!!!");
                    sent = 1;
                }
                else if(!nome.matches("^[a-zA-Z ]+$")){ //verifica se o nome esta escrito apenas com letras de A a Z
                    ent.imprimirMsg("\nColoque apenas letras!!!");
                    sent = 1;
                }
            }
            catch(NullPointerException e){ 
                ent.imprimirMsg("\nVoltando ao menu...");
                nome = null; 
            }
        }
        while(sent != 0);

        return nome;
    }

    /**
     * Método entrarRA, entrada no inserir aluno de RA
     *
     * @return RA String, usado apenas no inserir aluno. Retornando o RA digitado para o cadastro de alunos ou null
     */
    public String entrarRA(){
        int sent = 0; //cria uma sentinela para o loop
        String RA = "";
        do{
            try{
                //recebe o RA digitado
                RA = ent.receberString("Digite o RA: ");
                sent = 0;
                if(RA.length() != 8){ //verifica se ele tem o tamanho correto de 8 algarismos
                    ent.imprimirMsg("\nDigite 8 números para o RA!!!");
                    sent = 1;
                }
                else if(!RA.matches("\\d+")){ //verifica se foram escritos apenas numeros de 0 a 9
                    ent.imprimirMsg("\nDigite apenas números!!!");
                    sent = 1;
                }
                else if(cadastro.verificarRA(RA)){ //verifica se foi clicado em cancelar
                    ent.imprimirMsg("\nEsse RA já foi cadastrado!!!");
                    RA = null;
                }
            }
            catch(NullPointerException e){
                ent.imprimirMsg("\nVoltando ao menu...");
                RA = null; 
            }
        }while(sent != 0);

        return RA;
    }

    /**
     * Método entrarCurso, entrada do curso atraves de um menu 
     *
     * @return curso String, retorna o nome do curso escolhido pelo aluno: Ciencia da computacao, Engenharia Biomedica, design e Jogos.
     * 
     */
    public String entrarCurso(){
        int sent = 0; //cria uma sentinela para o loop
        int op = 0;
        String curso = "";
        String aux = "";

        do{
            sent = 0;
            try{
                //pede para o usuario e recebe o curso desejado
                aux = ent.receberString(
                    "\n============Cursos============\n" +
                    "   1. Ciência da Computação\n" +
                    "   2. Engenharia Biomédica\n" +
                    "   3. Design\n" +
                    "   4. Jogos\n" + 
                    "Escolha um dos cursos acima: ");
                op = Integer.parseInt(aux);

                if(op < 1 || op > 4){ //verifica se foi inserido uma das opcoes validas
                    ent.imprimirMsg("\nO número digitado é inválido!!!");
                    sent = 1;
                }
            }
            catch(NumberFormatException e){
                if(aux == null){ //verifica se foi clicado em cancelar
                    ent.imprimirMsg("\nVoltando ao menu...");
                    curso = null; 
                }
                else{ 
                    ent.imprimirMsg("\nDigite números de 1 a 4!!");
                    sent = 1;
                }
            }
        }
        while(sent != 0);

        //envia para a variavel String curso, qual a opcao desejada
        switch(op){
            case 1:
                curso = "Ciência da Computação";
                break;
            case 2:
                curso = "Engenharia Biomédica";
                break;
            case 3: 
                curso = "Design";
                break;
            case 4:
                curso = "Jogos";
                break;
            default:
                break;
        }

        return curso;
    }

    /**
     * Método entrarIdade, entrada da idade do aluno
     *
     * @return idade Int, retorna a idade digitada do aluno
     */
    public int entrarIdade(){
        int sent = 0; //cria uma sentinela para o loop
        int idade = 0;
        String aux = "";

        do{
            try{
                //recebe a idade
                sent = 0;
                aux = ent.receberString("Digite a idade: ");
                idade = Integer.parseInt(aux);

                if(idade < 16 || idade > 90){ //verifica se o aluno tem entre 16 e 90 anos (sendo essas as idades validas)
                    ent.imprimirMsg("\nIdade inválida");
                    sent = 1;
                }
            }
            catch(NumberFormatException e){ 
                if(aux == null){ //verifica se foi clicado em cancelar
                    ent.imprimirMsg("\nVoltando ao menu...");
                    idade = -1; 
                }
                else{ 
                    sent = 1;
                    ent.imprimirMsg("\nDigite uma idade válida");
                }
            }
        }
        while(sent != 0);

        return idade;
    }

    /**
     * Método entrarPeriodo, entrada do periodo 
     *
     * @return periodo Int, retorna o periodo que o aluno esta no curso
     */
    public int entrarPeriodo(){
        int sent = 0; //cria uma sentinela para o loop
        int periodo = 0;
        String aux = "";

        do{
            try{
                //recebe o periodo do aluno
                sent = 0;
                aux = ent.receberString("Digite o periodo: ");
                periodo = Integer.parseInt(aux);

                if(periodo < 1 || periodo > 14){ //verifica se o aluno esta em um periodo valido (do 0 ao 14)
                    ent.imprimirMsg("\nPeriodo inválido");
                    sent = 1;
                }
            }
            catch(NumberFormatException e){
                if(aux == null){ //verifica se foi clicado em cancelar
                    ent.imprimirMsg("\nVoltando ao menu...");
                    periodo = -1; 
                }
                else{
                    sent = 1;
                    ent.imprimirMsg("\nDigite um periodo válido");
                }
            }
        }
        while(sent != 0);

        return periodo;
    }

    /**
     * Método qtdDisciplinas, entrada de quantidade de disciplinas de um curso
     *
     * @return qtd Int, retorna a quantidade de disciplinas do curso que o aluno realiza
     */
    public int qtdDisciplinas(){
        int sent = 0;
        int qtd = 0;
        String aux = "";

        do{
            try{
                //recebe a quantidade de disciplinas do aluno 
                sent = 0;
                aux = ent.receberString("Digite a quantidade de disciplina que deseja: ");
                qtd = Integer.parseInt(aux);

                if(qtd < 1 || qtd > 15){ //verifica se a quantidade é valida (de 1 a 15)
                    ent.imprimirMsg("\nQuantidade de Disciplinas inválida");
                    sent = 1;
                }
            }
            catch(NumberFormatException e){
                if(aux == null){ //verifica se foi clicado em cancelar
                    ent.imprimirMsg("\nVoltando ao menu...");
                    qtd = -1; 
                }
                else{
                    sent = 1;
                    ent.imprimirMsg("\nDigite uma quantidade válida!!!");
                }
            }
        }
        while(sent != 0);

        return qtd;
    }

    /**
     * Método setNotas, menu de disciplinas
     *
     * @param qtd Int, quantidade de disciplinas que tem no curso
     * @return op Int, opcao de escolha do usuario no menu disciplinas ou -1, caso tenha cancelado na entrada grafica
     */
    protected int setNotas(int qtd){
        int op = 0;
        int sent = 0;
        String aux = "";

        do{
            try{
                //recebe qual nota o aluno deseja alterar
                sent = 0;
                aux = ent.receberString("\n" + cadastro.toStringDisciplinas() + "escolha uma das opções anteriores: ");
                op = Integer.parseInt(aux);
                if(op < 1 || op > cadastro.getQtdDisciplinasAluno()+1){ //verifica se foi digitado uma opcao valida (dentro da quantidade de discplinas)
                    ent.imprimirMsg("\nOpção inválida!!");
                    sent = 1;
                }
            }
            catch(NumberFormatException e){
                if(aux == null){ //verifica se foi clicado em cancelar
                    ent.imprimirMsg("\nVoltando ao menu...");
                    return -1;
                }
                else{
                    ent.imprimirMsg("\nEscolha apenas as opções mostradas!!");
                    sent = 1;
                }
            }
        }while(sent != 0);

        return op;
    }

    /**
     * Método inserirNota, inseri as notas de uma disciplina em especifico
     *
     * @param qtd Int, quantidade de disciplinas presentes no curso
     * @param op1 Int, a disciplina escolhida pelo usuario para alterar as notas
     * @return nota double, notas de 0 a 10
     */
    protected double inserirNota(int qtd, int op1){
        int sent = 0;
        String aux = "";
        double nota = 0;

        if(op1 > 0 && op1 <= qtd){
            do{
                try{
                    //recebe a nota digitada
                    sent = 0;
                    aux = ent.receberString("Notinha: ");
                    nota = Double.parseDouble(aux);

                    if(nota < 0 || nota > 10){ //verifica se a nota é valida (ou seja, de 0 a 10)
                        ent.imprimirMsg("\nNota inválida!!");
                        sent = 1;
                    }
                }
                catch(NumberFormatException e){
                    if(aux == null){ //verifica se foi clicado em cancelar
                        ent.imprimirMsg("\nVoltando a seleção de discplina...");
                        return -1;
                    }
                    else{
                        sent = 1;
                        ent.imprimirMsg("\nDigite uma nota coerente!!");
                    }
                }
            }while(sent != 0);
        }

        return nota;
    }

    /**
     * Método confirmarSaida, menu para confirmacao de saida
     *
     * @return true ou false, caso true sai do programa, se nao retorna ao menu principal
     */
    private boolean confirmarSaida(){
        int sent = 0;
        int escolha = 0;
        String aux = "";

        do{
            try{
                //recebe uma confirmacao se o usuario realmente quer sair do programa
                sent = 0;
                aux = ent.receberString(
                    "\nDeseja mesmo sair?\n" +
                    "   1. Sim\n" +
                    "   2. Não\n");
                escolha = Integer.parseInt(aux);

                if(escolha < 1 || escolha > 2){ //verifica se foi digitada uma opcao valida
                    ent.imprimirMsg("Opção inválida!!! Apenas 1 ou 2!!!");
                    sent = 1;
                }
            }
            catch(NumberFormatException e){
                if(aux == null){ //verifica se foi clicado em cancelar
                    ent.imprimirMsg("\nVoltando ao menu...");
                    return false; 
                }
                else{
                    sent = 1;
                    ent.imprimirMsg("\nDigite somente 1 ou 2!!!");
                }
            }
        }
        while(sent != 0);

        //se foi a opcao 1 sai do programa, agora se for a 2 volta ao menu
        switch(escolha){
            case 1: 
                System.exit(0);
                ent.imprimirMsg("\nSaindo do programa...");
                break;
            case 2: 
                ent.imprimirMsg("\nVoltando ao menu...");
                return false; 
        }

        return true;
    }
    
    /**
     * Método confirmarSalvada, menu para verificar se deseja salvar antes de sair
     *
     * @return true ou false, caso true salva os dados e sai do programa, se nao apenas sai do programa
     */
    private boolean confirmarSalvada(){
        int sent = 0;
        int escolha = 0;
        String aux = "";

        do{
            try{
                //recebe uma confirmacao se o usuario quer salvar antes de sair do programa
                sent = 0;
                aux = ent.receberString(
                    "\nDeseja salvar antes de sair?\n" +
                    "   1. Sim\n" +
                    "   2. Não\n");
                escolha = Integer.parseInt(aux);

                if(escolha < 1 || escolha > 2){ //verifica se foi digitada uma opcao valida
                    ent.imprimirMsg("Opção inválida!!! Apenas 1 ou 2!!!");
                    sent = 1;
                }
            }
            catch(NumberFormatException e){
                if(aux == null){ //verifica se foi clicado em cancelar
                    ent.imprimirMsg("\nVoltando ao menu...");
                    return false; 
                }
                else{
                    sent = 1;
                    ent.imprimirMsg("\nDigite somente 1 ou 2!!!");
                }
            }
        }
        while(sent != 0);

        //se foi a opcao 1 salva os alunos, agora se for a 2 sai do programa
        switch(escolha){
            case 1: 
                salvarAlunos();
                
                break;
            case 2: 
                break;
        }

        return true;
    }
    
    private String nomeArquivo = "";
    
    /**
     * Método salvarAlunos, salva em arquivo binario os alunos armazenados no cadastro alunos
     *
     */
    protected void salvarAlunos(){
        int sent = 0;
        aux1 = 0;
        
        do{
            try{
                //recebe o nome do arquivo
                nomeArquivo = ent.receberString("\nDigite o Nome do Arquivo: ");
                sent = 0;
                if((nomeArquivo.split(" ").length) > 1){ //verifica se o nome do arquivo apresenta apenas uma palavra
                    ent.imprimirMsg("\nNome de arquivo, não deve conter espaços!!!");
                    sent = 1;
                }
                else if(!(verificarNomeArquivo(nomeArquivo))){ //verifica se o nome do arquivo nao apresenta caracteres especiais
                    ent.imprimirMsg("\nNome de arquivo não pode conter as seguintes caracteres:\n \\ / : ? * \" < > |");
                    sent = 1;
                }
            }
            catch(NullPointerException e){ 
                ent.imprimirMsg("\nVoltando ao menu...");
                nomeArquivo = null; 
            }
        }
        while(sent != 0);
        
        //verifica se foi escrito um nome com caracteristicas validas
        if(nomeArquivo != null){
            nomeArquivo = nomeArquivo + ".bin";     //transforma a string em arquivo bin
            
            int resposta = 0;
            
            Persistencia arq = new ArquivoBinario();   //instancia um objeto 
            if( ((IArmazenador) arq.lerObj(nomeArquivo)) != null){
                int sen = 0;
                int escolha = 1;
                do{
                    try{
                        //recebe uma confirmacao se o usuario realmente quer sair do programa
                        sen = 0;
                        aux = ent.receberString(
                            "\nEste arquivo já existe, deseja subscreve-lo?\n" +
                            "   1. Sim\n" +
                            "   2. Não\n");
                        escolha = Integer.parseInt(aux);
        
                        if(escolha < 1 || escolha > 2){ //verifica se foi digitada uma opcao valida
                            ent.imprimirMsg("Opção inválida!!! Apenas 1 ou 2!!!");
                            sen = 1;
                        }
                    }
                    catch(NumberFormatException e){
                        if(aux == null){ //verifica se foi clicado em cancelar
                            escolha = 2; //considera que "não"
                        }
                        else{
                            sen = 1;
                            ent.imprimirMsg("\nDigite somente 1 ou 2!!!");
                        }
                    }
                }
                while(sen != 0);
        
                if(escolha == 1) resposta = 0;
                else resposta = 1;
            } 
            
            if(resposta == 0){
                arq.gravarObj(cadastro.armazenador, nomeArquivo);   //salva o armazenador do cadastro aluno
                ent.imprimirMsg("\nFoi salvo com sucesso!!");   
                aux1++; //acrescenta 1 na auxiliar para verificar se gravou ou nao o arquivo   
            } else ent.imprimirMsg("Não foi possível salvar!!!");
            
        }
    }
    
    /**
     * Método verificarNomeArquivo, verifica se o nome digiado apresenta caracteres especiais: " : ? / \ * > < |
     *
     * @param _nomeArquivo String, nome do arquivo digitado 
     * @return true ou false, retorna true se nao haver nenhum caractere especial, se nao retorna false
     */
    private boolean verificarNomeArquivo(String _nomeArquivo){
        boolean bool = true;
        char ch;
        //loop que percorrera todos os caracteres do nome dado ao arquivo
        for(int i = 0; i < _nomeArquivo.length(); i++){
            ch = _nomeArquivo.charAt(i);    //variavel ch recebe cada um dos caracteres para serem verificados
            if(ch == 34 || ch == 47 || ch == 92 || ch == 42 || ch == 124 || ch == 58 || ch == 60 || ch == 62 || ch == 63) { //usando tabela ascii
                bool = false;
                break;
            }
        }
        
        return bool;
    }

    /**
     * Método lerAlunos, le o arquivo binario desejado pelo usuario
     *
     */
    protected void lerAlunos(){
        int sent = 0;
        
        do{
            try{
                //recebe o nome do arquivo
                nomeArquivo = ent.receberString("\nDigite o Nome do Arquivo: ");
                sent = 0;
                if((nomeArquivo.split(" ").length) > 1){ //verifica se o nome do arquivo inserido apresenta apenas uma palavra
                    ent.imprimirMsg("\nNome de arquivo, não deve conter espaços!!!");
                    sent = 1;
                }//0123456789_-
                else if(!(verificarNomeArquivo(nomeArquivo))){ //verifica se o nome do arquivo nao apresenta caracteres especiais
                    ent.imprimirMsg("\nNome de arquivo não pode conter as seguintes caracteres:\n \\ / : ? * \" < > |");
                    sent = 1;
                }
            }
            catch(NullPointerException e){ 
                ent.imprimirMsg("\nVoltando ao menu...");
                nomeArquivo = null; 
            }
        }
        while(sent != 0);
        
        //verifica se foi escrito um nome com caracteristicas validas e se apresenta algo salvo
        if(nomeArquivo != null ){
            nomeArquivo = nomeArquivo + ".bin";     //transforma a string em arquivo bin
            Persistencia arq = new ArquivoBinario();    //instancia um objeto
            if( ((IArmazenador) arq.lerObj(nomeArquivo)) != null){ //abre o console quando entra nessa funcao exception
                IArmazenador arr = (IArmazenador) arq.lerObj(nomeArquivo);      //le o aquivo do tipo IArmazenador
                Aluno aluno;
                for(int i = 0; i < arr.getQtd(); i++){
                    aluno = (Aluno) arr.buscar(i);  //variavel aluno recebe cada um dos alunos lidos atraves da busca
                    cadastro.inserirAlunoObjeto(aluno); //armazenando cada aluno lido no cadastroAluno
                }
                ent.imprimirMsg("\nFoi lido com sucesso!!");
                aux1++; //variavel de controle que indica que um arquivo foi lido com sucesso
            }else {
                ent.imprimirMsg("\nNão existe este arquivo ou o mesmo esta vazio, tente novamente!");
            }
        }
    }

}