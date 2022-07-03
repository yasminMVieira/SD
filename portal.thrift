namespace java portal

exception IdNotFound
{
   1:i64 time,
   2:string id
}

struct Tarefa{
    2: string titulo
    3: string descricao
}

struct Cliente{
    1: string id
   	2: i32 idade
	3: i32 cpf
    4: list <Tarefa> tarefas
}


service Portal
{
    bool inserirCliente(1: string id,2: i32 idade,3: i32 cpf),
    bool modificarCliente(1: string id,2: i32 idade,3: i32 cpf),
    Cliente recuperarCliente(1: string id),
    bool apagarCliente(1: string id),
    bool inserirTarefa(1: string id,2: string titulo,3: string descricao),
    bool modificarTarefa(1: string id,2: string titulo,3: string descricao),
    list<Tarefa> listarTarefas(1: string id),
    bool apagarTarefas(1: string id),
    bool apagarTarefa(1: string id,2: string titulo),

}
