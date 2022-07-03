package portal;

import portal.Portal;
import portal.PortalHandler;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

import portal.*;

public class PortalServidor {

    public static void main(String [] args) {
        try {
            PortalHandler handler = new PortalHandler();
            Portal.Processor processor = new Portal.Processor(handler);

            TServerTransport serverTransport = new TServerSocket(9090);
            TServer server = new TSimpleServer(new Args(serverTransport).processor(processor));

            System.out.println("Starting the simple server...");
            server.serve();
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

}