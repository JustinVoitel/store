package org.eclipse.store.integrations.quarkus.deployment.test;




import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.store.storage.types.StorageManager;

@Path("/test")
@Produces(MediaType.TEXT_PLAIN)
public class StorageManagerController
{

    @Inject
    StorageManager storageManager;

    @GET
    public String testStorageManager()
    {
        return "storageManagerRoot=" + (storageManager.root() == null);
    }
}
