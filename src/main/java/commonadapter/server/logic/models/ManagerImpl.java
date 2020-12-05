package commonadapter.server.logic.models;

import adapter.*;
import com.zeroc.Ice.Current;
import com.zeroc.Ice.Identity;
import commonadapter.server.logic.services.OntologyService;
import commonadapter.server.logic.exceptions.OntologyItemCreationException;
import commonadapter.server.logic.exceptions.OntologyItemLoadingException;
import commonadapter.logging.LogMessageType;
import commonadapter.logging.Logger;

import java.util.Set;
import java.util.TreeSet;

import static commonadapter.CommunicationUtils.GLOBAL_ICE_CATEGORY;


public class ManagerImpl implements Manager {

    private OntologyService ontologyService;

    public ManagerImpl(String ontologyfilePath) {

        ontologyService = new OntologyService(ontologyfilePath);
    }

    @Override
    public String load(String itemId, ItemType type, Current current) {

//        // else
//        String id = "";
//        BaseItemImpl loadedItem = null;
//
//
//        loadedItem = ontologyService.loadItem(itemId, type);
//        id = loadedItem.getId();
//        current.adapter.add(loadedItem, new Identity(id, GLOBAL_ICE_CATEGORY));




        return "";
    }


    @Override
    public String create(ItemType type, Current current) {

        String id = "";
        BaseItemImpl createdItem = null;

        createdItem = ontologyService.createAndLoadItem(type);
        id = createdItem.getId();
        current.adapter.add(createdItem, new Identity(id, GLOBAL_ICE_CATEGORY));

        return id;
    }


    @Override
    public void persist(Current current) {

        ontologyService.persist();

    }
}
