package commonadapter.server.logic;


import adapter.Manager;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Identity;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Util;
import commonadapter.server.logic.models.ManagerImpl;
import commonadapter.logging.LogMessageType;
import commonadapter.logging.Logger;

public class Server implements Runnable {

    private Communicator communicator;
    private ObjectAdapter objectAdapter;
    private Manager manager;
    private String ontologyFilePath;

    public Server(String ontologyFilePath) {

        this.ontologyFilePath = ontologyFilePath;
    }

    public void run()
    {
        int status = 0;
        communicator = null;

        try
        {
            communicator = Util.initialize(new String[0]);

            objectAdapter = communicator.createObjectAdapterWithEndpoints(
                    "Adapter1",
                    "tcp -h localhost -p 10000:udp -h localhost -p 10000"
            );

            manager = new ManagerImpl(ontologyFilePath);

            objectAdapter.add(manager, new Identity("manager", "adapter"));

            objectAdapter.activate();

            Logger.printLogMessage("Server started", LogMessageType.INFO);

            communicator.waitForShutdown();
        }
        catch (Exception e)
        {
            System.err.println(e);
            status = 1;
        }
        if (communicator != null)
        {
            try
            {
                communicator.destroy();
            }
            catch (Exception e)
            {
                System.err.println(e);
                status = 1;
            }
        }

        System.exit(status);
    }

    public void shutdown() {

        communicator.shutdown();
    }
}
