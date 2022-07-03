# SD

 👩🏻‍💻 Para executar (WINDOWS):

1️⃣ -> gerar o ``.java`` do arquivo thrift: ``thift --gen java portal.thrift``

2️⃣ -> ```javac  -cp jars\slf4j-nop-1.7.25.jar;jars\javax.annotation-api-1.3.2.jar;jars\libthrift-0.16.0.jar;jars\slf4j-api-1.7.36.jar;gen-java -d . *.java```

3️⃣ -> ```java -cp jars\slf4j-nop-1.7.25.jar;jars\javax.annotation-api-1.3.2.jar;jars\libthrift-0.16.0.jar;jars\slf4j-api-1.7.36.jar;gen-java;. PortalServidor.java```

4️⃣ -> realizar o mesmo para os demais portais (cliente, adm)


Notes

Ao realizar o acesso em PortalClient é pedido o cpf e idade desse usuário, e através desses dados é enviado ao PortalAdm uma requisição de inserir esse cliente no banco de dados. Também há a possibilidade de inserir um cliente pelo próprio portal do administrador.
