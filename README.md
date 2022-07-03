# SD

Para executar (WINDOWS):

1 -> gerar o .java do arquivo thrift (thift --gen java portal.thrift)

2 -> javac  -cp jars\javax.annotation-api-1.3.2.jar;jars\libthrift-0.16.0.jar;jars\slf4j-api-1.7.36.jar;gen-java -d . *.java

3 -> java -cp jars\javax.annotation-api-1.3.2.jar;jars\libthrift-0.16.0.jar;jars\slf4j-api-1.7.36.jar;gen-java;. PortalServidor.java

4 -> demais portais (cliente, adm)
