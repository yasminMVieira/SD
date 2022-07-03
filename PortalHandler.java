package portal;

import org.apache.thrift.TException;

import java.util.List;
import java.util.concurrent.Semaphore;
import java.lang.String;
import java.util.HashMap;

public class PortalHandler implements Portal.Iface {

    private HashMap<String, Cliente> cid = new HashMap<>();

    static Semaphore semaphore = new Semaphore(1);



    //cria um cliente e adiciona na hash
    @Override
    public boolean inserirCliente(String id, int idade, int cpf) throws TException {

        try {
            semaphore.acquire();
            Thread.sleep(3000);
        }
        catch (Exception e) {
            e.printStackTrace();
            InterruptedException ex;
        }

        Cliente cliente = new Cliente(id, idade, cpf, null);

        if (cid.containsValue(cliente.cpf)) { //verifica se ja existe
            semaphore.release();
            return false;
        }
        else{

            cid.put(id, cliente);
            semaphore.release();
            return true;
        }

    }


    //Função que modifica os dados de um cliente ja existente
    @Override
    public boolean modificarCliente(String id, int idade, int cpf) throws TException{

        try {
            semaphore.acquire();
            Thread.sleep(3000);
        }
        catch (Exception e) {
            e.printStackTrace();
            InterruptedException ex;
        }

        if (recuperarCliente(id) != null) { //verifica se ja existente
            recuperarCliente(id).setCpf(cpf);
            recuperarCliente(id).setIdade(idade);
            semaphore.release();
            return true;
        }
        else{
            semaphore.release();
            return false;
        }

    }


    @Override
    public Cliente recuperarCliente(String id) {
        if (cid.get(id) != null){
            return cid.get(id);
        }
        return null;
    }


    //Exclui o cliente
    @Override
    public boolean apagarCliente(String id) throws TException {

        try {
            semaphore.acquire();
            Thread.sleep(3000);
        }
        catch (Exception e) {
            e.printStackTrace();
            InterruptedException ex;
        }

        if (recuperarCliente(id) != null) { //verifica se ja existente
            cid.remove(id);
            semaphore.release();
            return true;
        }
        else{
            semaphore.release();
            return false;
        }

    }

    //cria uma tarefa e adiciona ao cliente
    @Override
    public boolean inserirTarefa(String id, String titulo, String descricao) throws TException {

        try {
            semaphore.acquire();
            Thread.sleep(3000);
        }
        catch (Exception e) {
            e.printStackTrace();
            InterruptedException ex;
        }

        int i;

        //verifica se ja existe tarefa de mesmo titulo
        if (recuperarCliente(id).tarefas != null) {
            for(i=0; i< recuperarCliente(id).tarefas.size(); i++){

                if(titulo.equals(recuperarCliente(id).tarefas.get(i).getTitulo())){
                    semaphore.release();
                    return false;
                }
            }
        }

        Tarefa tarefa = new Tarefa(titulo, descricao);

        //caso nao exista outra tarefa de mesmo titulo
        tarefa.setTitulo(titulo);
        tarefa.setDescricao(descricao);

        recuperarCliente(id).addToTarefas(tarefa);

        semaphore.release();
        return true;


    }


    //Função que modifica os dados de uma tarefa ja existente (compara pelo titulo).
    @Override
    public boolean modificarTarefa(String id, String titulo, String descricao) throws TException{

        try {
            semaphore.acquire();
            Thread.sleep(3000);
        }
        catch (Exception e) {
            e.printStackTrace();
            InterruptedException ex;
        }

        int i;
        boolean aux = false;

        if (recuperarCliente(id).tarefas != null) { //verifica se existe tarefas
            for (i = 0; i < recuperarCliente(id).tarefas.size(); i++) {
                if (recuperarCliente(id).tarefas.get(i).titulo.equals(titulo)) {
                    recuperarCliente(id).tarefas.get(i).setDescricao(descricao);
                    semaphore.release();
                    aux = true;
                }
            }
        }
        else{
            semaphore.release();
        }

        return aux;
    }


    @Override
    public List<Tarefa> listarTarefas(String id) throws TException {
        return recuperarCliente(id).tarefas;
    }


    //Exclui tarefas
    @Override
    public boolean apagarTarefas(String id) throws TException {

        try {
            semaphore.acquire();
            Thread.sleep(3000);
        }
        catch (Exception e) {
            e.printStackTrace();
            InterruptedException ex;
        }


        if (recuperarCliente(id).tarefas != null) {
            recuperarCliente(id).tarefas.clear();
            semaphore.release();
            return true;
        }
        else{
            semaphore.release();
            return false;
        }

    }

    //Exclui tarefa
    @Override
    public boolean apagarTarefa(String id, String titulo) throws TException {

        try {
            semaphore.acquire();
            Thread.sleep(3000);
        }
        catch (Exception e) {
            e.printStackTrace();
            InterruptedException ex;
        }

        int i;
        boolean aux = false;

        if (recuperarCliente(id).tarefas != null) { //verifica se ja existente
            for (i = 0; i < recuperarCliente(id).tarefas.size(); i++) {
                if (recuperarCliente(id).tarefas.get(i).titulo.equals(titulo)) {
                    recuperarCliente(id).tarefas.remove(i);
                    semaphore.release();
                    aux = true;
                }
            }
        }
        else{
            semaphore.release();
        }
        return aux;
    }


}
