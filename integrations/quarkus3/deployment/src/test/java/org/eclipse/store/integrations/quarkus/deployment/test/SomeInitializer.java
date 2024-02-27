package org.eclipse.store.integrations.quarkus.deployment.test;



import org.eclipse.store.integrations.quarkus.types.config.StorageManagerInitializer;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.store.storage.types.StorageManager;

@ApplicationScoped
public class SomeInitializer implements StorageManagerInitializer
{
    @Override
    public void initialize(StorageManager storageManager)
    {
        Object rootObject = storageManager.root();
        if (rootObject == null) {
            SomeRoot root = new SomeRoot();
            root.setData("Initial value");
            storageManager.setRoot(root);
        } else {
            throw new IllegalStateException("StorageManager should not have already a Root object assigned");
        }
    }
}
