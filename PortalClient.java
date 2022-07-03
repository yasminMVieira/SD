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
import java.util.UUID;


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


            do{

                System.out.println("Menu:");
                System.out.println("[1]Inserir tarefa");
                System.out.println("[2]Modificar tarefa");
                System.out.println("[3]Listar Tarefas");
                System.out.println("[4]Apagar Tarefas");
                System.out.println("[5]Apagar Tarefa Especifica");
                System.out.println("[6]Sair\n");

                opcao = s.nextInt();

                switch(opcao){

                    case 1:
                        System.out.printf("Id do cliente: "+ id);
                        System.out.println("\nDigite os dados da tarefa:");
                        System.out.println("Titulo: ");
                        String titulo = s.next();
                        System.out.println("Descrição: ");
                        String descricao = s.next();

                        TTransport transport = new TSocket("localhost", 9090);
                        transport.open();
                        TProtocol protocol = new  TBinaryProtocol(transport);
                        Portal.Client client = new Portal.Client(protocol);

                        //servidor = retornaServidorV(id);

                        retorno = client.inserirTarefa(id, titulo, descricao);
                        if(retorno == false){
                            System.out.println("\nOperação inválida, tarefa ja existe!\n");
                        }
                        else{
                            System.out.println("\nTarefa adicionada com sucesso!\n");
                        }
                        System.out.println(retorno);
                        transport.close() ;
                        //-----

                        break;
                    case 2:
                        System.out.println("Digite os dados da tarefa:\n");
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
                            System.out.println("\nTarefa nao existe\n ");
                        }
                        System.out.println(retorno);
                        transport.close() ;
                        break;

                    case 3:
                        System.out.println("Informe o id do cliente");
                        id = s.next();

                        transport = new TSocket("localhost", 9090);
                        transport.open();
                        protocol = new  TBinaryProtocol(transport);
                        client = new Portal.Client(protocol);

                        List<Tarefa> tar = client.listarTarefas(id);

                        int j;

                        if(tar != null){
                            for(j =0; j < tar.size(); j++){
                                System.out.println("Tarefa: "+ tar.get(j).titulo + "\n Descrição: " + tar.get(j).descricao);
                            }
                        }
                        else
                            System.out.println("\nCliente nao encontrado!\n");

                        transport.close();
                        break;

                    case 4:
                        System.out.println("Informe o id do cliente");
                        id = s.next();

                        transport = new TSocket("localhost", 9090);
                        transport.open();
                        protocol = new  TBinaryProtocol(transport);
                        client = new Portal.Client(protocol);


                        retorno = client.apagarTarefas(id);

                        if(retorno == true){
                            System.out.println("\nTarefa modificada com sucesso!\n");
                        }
                        else{
                            System.out.println("\nTarefa nao existe\n ");
                        }
                        System.out.println(retorno);
                        transport.close() ;
                        break;
                    case 5:
                        System.out.println("Informe o id do cliente");
                        id = s.next();
                        System.out.println("Informe o titulo da tarefa");
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
                            System.out.println("\nTarefa nao existe\n ");
                        }
                        System.out.println(retorno);
                        transport.close() ;
                        break;
                    case 6:


                    default:
                        System.out.println("Esta opçao nao e valida\n");
                }
            }while(opcao != -1);
        }catch (IdNotFound i) {
            System.err.println("ID not found: " + i.getId());
        } catch (Exception x) {
            x.printStackTrace();
        }
    }


    //cria Id unico
    public static String criaId() {
        String uniqueID = UUID.randomUUID().toString();
        return uniqueID;
    }
}





