/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package portal;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import java.lang.String;
import java.util.Scanner;
import java.util.UUID;

import static java.lang.System.exit;


public class PortalAdm {
    public static void main(String[] args) {
        int opcao;             
	    Scanner s = new Scanner(System.in);

        try {

            //-----
            int servidor = 0;
            Cliente cliente;
            String id;
            boolean retorno;
            
            //-----
            int hash, porta;
            //-----
            
        
            do{	
            
                System.out.println("Menu:");
                System.out.println("[1]Cadastrar cliente");
                System.out.println("[2]Modificar cliente");
                System.out.println("[3]Apagar cliente");
                System.out.println("[4]Recuperar cliente");
                System.out.println("[5]Sair");

                opcao = s.nextInt();

                switch(opcao){
            
                    case 1:
                        id = criaId();
                        System.out.printf("Id do cliente: "+id);
                        System.out.println("\n--Digite os dados do cliente--\n");
                        System.out.println("Idade: ");
                        int idade = s.nextInt();
                        System.out.println("CPF: ");
                        int cpf = s.nextInt();

                        TTransport transport = new TSocket("localhost", 9090);
                        transport.open();
                        TProtocol protocol = new  TBinaryProtocol(transport);
                        Portal.Client client = new Portal.Client(protocol);

                        retorno = client.inserirCliente(id, idade, cpf);

                        if(retorno == false){
                            System.out.println("\nOperação inválida, cliente já existe!\n");
                        } 
                        else{
                            System.out.println("\nCliente adicionado com sucesso!\n");    
                        }   

                        transport.close();
                    break;
                    case 2:
                        System.out.println("\n--Digite os dados do cliente--\n");
                        System.out.println("Id: ");
                        id = s.next();
                        System.out.println("Idade: ");
                        idade = s.nextInt();
                        System.out.println("CPF: ");
                        cpf = s.nextInt();

                        transport = new TSocket("localhost", 9090);
                        transport.open();
                        protocol = new  TBinaryProtocol(transport);
                        client = new Portal.Client(protocol);

                        retorno = client.modificarCliente(id, idade, cpf);

                        if(retorno == true){
                            System.out.println("\nCliente modificado com sucesso!\n");
                        } 
                        else{
                            System.out.println("\nCliente não existe\n ");
                        }  

                       transport.close();
                    break;
                        
                    case 3: 
                        System.out.println("\nInforme o id do cliente a ser removido:");
                        id = s.next();

                        transport = new TSocket("localhost", 9090);
                        transport.open();
                        protocol = new  TBinaryProtocol(transport);
                        client = new Portal.Client(protocol);

                        retorno = client.apagarCliente(id);

                        if(retorno == true)
                            System.out.println("\nCliente removido com sucesso!\n");
                        else
                            System.out.println("\nCliente nao encontrado!\n");

                        transport.close();
                    break;
                    case 4:
                        System.out.println("\nInforme o id do cliente a ser recuperado:");
                        id = s.next();

                        transport = new TSocket("localhost", 9090);
                        transport.open();
                        protocol = new  TBinaryProtocol(transport);
                        client = new Portal.Client(protocol);

                        cliente = client.recuperarCliente(id);

                        if(cliente != null){
                            System.out.println("\nCliente ID: " + cliente.id);
                            System.out.println("Idade: " + cliente.idade);
                            System.out.println("CPF: " + cliente.cpf);
                            System.out.println("Tarefas: " + cliente.tarefas);
                            System.out.println("\n");
                        }
                        else{
                            System.out.println("Cliente não está na hash");
                            break;
                        }

                        transport.close();
                    break;
                    case 5:
                        exit(1);
                    default:
                        System.out.println("Esta opção nao é valida\n");
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

    public static String receberCliente(int idade, int cpf) throws TException { //invocado quando o cliente é autenticado (PortalClient)

        boolean retorno;
        String id = criaId();

        TTransport transport = new TSocket("localhost", 9090);
        transport.open();
        TProtocol protocol = new TBinaryProtocol(transport);
        Portal.Client client = new Portal.Client(protocol);

        retorno = client.inserirCliente(id, idade, cpf);

        if(retorno == false){
            System.out.println("\nCliente já existe!\n");
            transport.close();
            return null;
        }
        else{
            System.out.println("\nCliente adicionado com sucesso!\n");
            transport.close();
            return id;
        }

    }
}
