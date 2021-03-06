/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package portal;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import java.lang.String;
import java.util.List;
import java.util.Scanner;


import static java.lang.System.exit;


public class PortalClient {
    public static void main(String[] args) {
        int opcao;
        Scanner s = new Scanner(System.in);

        try {

            //-----
            int servidor = 0;
            String id;
            boolean retorno;

            //-----
            int hash, porta;
            //-----

            System.out.println("Digite seu cpf: ");
            int cpf = s.nextInt();
            System.out.println("Digite sua idade: ");
            int idade = s.nextInt();

            id = PortalAdm.receberCliente(idade, cpf);
            System.out.println("Seu ID é: " + id + "\n");

            do{

                System.out.println("Menu:");
                System.out.println("[1]Inserir tarefa");
                System.out.println("[2]Modificar tarefa");
                System.out.println("[3]Listar Tarefas");
                System.out.println("[4]Apagar Tarefas");
                System.out.println("[5]Apagar Tarefa Especifica");
                System.out.println("[6]Sair");

                opcao = s.nextInt();

                switch(opcao){

                    case 1:
                        System.out.println("\n--Digite os dados da tarefa--\n");
                        System.out.println("Titulo: ");
                        String titulo = s.next();
                        System.out.println("Descrição: ");
                        String descricao = s.next();

                        TTransport transport = new TSocket("localhost", 9090);
                        transport.open();
                        TProtocol protocol = new  TBinaryProtocol(transport);
                        Portal.Client client = new Portal.Client(protocol);

                        retorno = client.inserirTarefa(id, titulo, descricao);

                        if(retorno == false){
                            System.out.println("\nOperação inválida, tarefa já existe!\n");
                        }
                        else{
                            System.out.println("\nTarefa adicionada com sucesso!\n");
                        }

                        transport.close();
                        //-----

                        break;
                    case 2:
                        System.out.println("--Digite os dados da tarefa--\n");
                        System.out.println("Titulo: ");
                        titulo = s.next();
                        System.out.println("Descrição: ");
                        descricao = s.next();

                        transport = new TSocket("localhost", 9090);
                        transport.open();
                        protocol = new  TBinaryProtocol(transport);
                        client = new Portal.Client(protocol);

                        retorno = client.modificarTarefa(id, titulo, descricao);

                        if(retorno == true){
                            System.out.println("\nTarefa modificada com sucesso!\n");
                        }
                        else{
                            System.out.println("\nTarefa não existe\n ");
                        }

                        transport.close();
                        break;
                    case 3:
                        transport = new TSocket("localhost", 9090);
                        transport.open();
                        protocol = new  TBinaryProtocol(transport);
                        client = new Portal.Client(protocol);

                        List<Tarefa> tar = client.listarTarefas(id);

                        int j;

                        if(tar != null){
                            for(j =0; j < tar.size(); j++){
                                System.out.println("\nTarefa: "+ tar.get(j).titulo + "\nDescrição: " + tar.get(j).descricao + "\n");
                            }
                        }
                        else
                            System.out.println("\nCliente não encontrado!\n");

                        transport.close();
                        break;

                    case 4:
                        transport = new TSocket("localhost", 9090);
                        transport.open();
                        protocol = new  TBinaryProtocol(transport);
                        client = new Portal.Client(protocol);

                        retorno = client.apagarTarefas(id);

                        if(retorno == true){
                            System.out.println("\nTarefas excluidas!\n");
                        }
                        else{
                            System.out.println("\nCliente sem tarefas!\n ");
                        }

                        transport.close();
                        break;
                    case 5:
                        System.out.println("\nInforme o titulo da tarefa:");
                        titulo = s.next();

                        transport = new TSocket("localhost", 9090);
                        transport.open();
                        protocol = new  TBinaryProtocol(transport);
                        client = new Portal.Client(protocol);

                        retorno = client.apagarTarefa(id, titulo);

                        if(retorno == true){
                            System.out.println("\nTarefa removida com sucesso!\n");
                        }
                        else{
                            System.out.println("\nTarefa não existe\n ");
                        }

                        transport.close();
                        break;
                    case 6:
                        exit(1);
                    default:
                        System.out.println("\nEsta opção não é valida\n");
                }
            }while(opcao != -1);
        }catch (IdNotFound i) {
            System.err.println("ID not found: " + i.getId());
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

}
