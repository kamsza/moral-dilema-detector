package commonadapter.server.logic.models;

import adapter.*;
import com.zeroc.Ice.Current;
import com.zeroc.Ice.Identity;
import commonadapter.server.logic.services.OntologyService;
import commonadapter.server.logic.exceptions.OntologyItemCreationException;
import commonadapter.server.logic.exceptions.OntologyItemLoadingException;
import commonadapter.server.logic.logging.LogMessageType;
import commonadapter.server.logic.logging.Logger;

import java.util.Set;
import java.util.TreeSet;

import static commonadapter.CommunicationUtils.GLOBAL_ICE_CATEGORY;


public class ManagerImpl implements Manager {

    OntologyService ontologyService;

    private Set<String> loadedItemIds;

    public ManagerImpl(String ontologyfilePath) {

        loadedItemIds = new TreeSet<>();
        ontologyService = new OntologyService(ontologyfilePath);
    }



    @Override
    public String load(String itemId, ItemType type, Current current) {

        if (checkIfLoaded(itemId))
            return itemId;

        // else

        String id = "";

        BaseItemImpl loadedItem = null;

        try {

            loadedItem = ontologyService.loadItem(itemId, type);
            id = loadedItem.getId();

            current.adapter.add(loadedItem, new Identity(id, GLOBAL_ICE_CATEGORY));

            loadedItemIds.add(id);

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

            createdItem = ontologyService.createAndLoadItem(type);
            id = createdItem.getId();

            current.adapter.add(createdItem, new Identity(id, GLOBAL_ICE_CATEGORY));

            loadedItemIds.add(id);

        } catch (OntologyItemCreationException ex) {

            Logger.printLogMessage(ex.getMessage(), LogMessageType.ERROR);
        }

        return id;
    }


    @Override
    public void persist(Current current) {

        ontologyService.persist();

        StringBuffer sb = new StringBuffer();

        loadedItemIds.forEach(id -> {
            sb.append(id);
            sb.append("\n");
        });

        Logger.printLogMessage(sb.toString(), LogMessageType.INFO);

    }

    private boolean checkIfLoaded(String itemId) {

        return loadedItemIds.contains(itemId);
    }
}
