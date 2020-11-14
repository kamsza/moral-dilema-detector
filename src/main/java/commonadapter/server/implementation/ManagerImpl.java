package commonadapter.server.implementation;

import adapter.*;
import com.zeroc.Ice.Current;
import com.zeroc.Ice.Identity;
import commonadapter.server.implementation.exceptions.OntologyItemCreationException;
import commonadapter.server.implementation.exceptions.OntologyItemLoadingException;
import commonadapter.server.implementation.logging.LogMessageType;
import commonadapter.server.implementation.logging.Logger;

import java.util.Set;
import java.util.TreeSet;

import static commonadapter.CommunicationUtils.GLOBAL_ICE_CATEGORY;


public class ManagerImpl implements Manager {

    OntologyLoader ontologyLoader;

    private Set<String> itemIds;

    public ManagerImpl() {

        itemIds = new TreeSet<>();
        ontologyLoader = new OntologyLoader("", "");
    }



    @Override
    public String load(String itemId, ItemType type, Current current) {

        if (checkIfLoaded(itemId))
            return itemId;

        // else

        String id = "";

        BaseItemImpl loadedItem = null;

        try {

            loadedItem = ontologyLoader.loadItem(itemId, type);
            id = loadedItem.getId();

            current.adapter.add(loadedItem, new Identity(id, GLOBAL_ICE_CATEGORY));

            itemIds.add(id);

        } catch (OntologyItemLoadingException ex) {

            Logger.printLogMessage(ex.getMessage(), LogMessageType.ERROR);
        }

        return id;
    }


    @Override
    public String create(ItemType type, Current current) {

        String id = "";

        BaseItemImpl createdItem = null;

        try {

            createdItem = ontologyLoader.createAndLoadItem(type);
            id = createdItem.getId();

            current.adapter.add(createdItem, new Identity(id, GLOBAL_ICE_CATEGORY));

            itemIds.add(id);

        } catch (OntologyItemCreationException ex) {

            Logger.printLogMessage(ex.getMessage(), LogMessageType.ERROR);
        }

        return id;
    }


    @Override
    public void persist(Current current) {

        ontologyLoader.persist();

        StringBuffer sb = new StringBuffer();

        itemIds.forEach(id -> {
            sb.append(id);
            sb.append("\n");
        });

        Logger.printLogMessage(sb.toString(), LogMessageType.INFO);

    }

    private boolean checkIfLoaded(String itemId) {

        return itemIds.contains(itemId);
    }
}
