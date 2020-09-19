package commonadapter.test.client;

import adapter.Scenario;
import adapter.ScenarioFactoryPrx;
import adapter.ScenarioPrx;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.LocalException;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.Ice.Util;

public class Client {

    public static void main(String[] args)
    {
        int status = 0;
        Communicator communicator = null;

        try {

            communicator = Util.initialize(args);

            ObjectPrx base = communicator.stringToProxy("factory/factory1:tcp -h localhost -p 10000");

            ScenarioFactoryPrx obj = ScenarioFactoryPrx.checkedCast(base);
            if (obj == null) throw new Error("Invalid proxy");

            String line = null;
            java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));

            do
            {
                try
                {
                    System.out.print("==> ");
                    System.out.flush();
                    line = in.readLine();

                    if (line == null)
                    {
                        break;
                    }
                    if (line.equals("create"))
                    {
                        obj.createScenario("s1");
                        ObjectPrx basePrx = communicator.stringToProxy("scenario/s1:tcp -h localhost -p 10000");

                        ScenarioPrx scenario = ScenarioPrx.checkedCast(basePrx);
                        System.out.println("RESULT = " + scenario.getName());
                    }
                }
                catch (java.io.IOException ex)
                {
                    System.err.println(ex);
                }
            }
            while (!line.equals("x"));


        } catch (LocalException e) {
            e.printStackTrace();
            status = 1;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            status = 1;
        }
        if (communicator != null) {
            // Clean up
            //
            try {
                communicator.destroy();
            } catch (Exception e) {
                System.err.println(e.getMessage());
                status = 1;
            }
        }
        System.exit(status);
    }
}
