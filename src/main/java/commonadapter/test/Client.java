package commonadapter.test;

import adapter.*;
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

            ManagerPrx managerPrx = ManagerPrx.checkedCast(base);
            if (managerPrx == null) throw new Error("Invalid proxy");

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
                        String scenarioId = managerPrx.create(ItemType.SCENARIO);
                        String laneId = managerPrx.create(ItemType.LANE);
                        String vehicleId = managerPrx.create(ItemType.VEHICLE);

                        ObjectPrx basePrx = communicator.stringToProxy(scenarioId + ":tcp -h localhost -p 10000");
                        ScenarioPrx scenarioPrx = ScenarioPrx.checkedCast(basePrx);

                        basePrx = communicator.stringToProxy(vehicleId + ":tcp -h localhost -p 10000");
                        VehiclePrx vehiclePrx = VehiclePrx.checkedCast(basePrx);

                        // onto creation
                        scenarioPrx.addLane(laneId);
                        scenarioPrx.addVehicle(vehicleId);
                        vehiclePrx.setLane(laneId);
                        vehiclePrx.setLength(457F);
                        vehiclePrx.setWidth(252F);

                        managerPrx.persist();

                        System.out.println("created scenario id = " + scenarioId);
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
