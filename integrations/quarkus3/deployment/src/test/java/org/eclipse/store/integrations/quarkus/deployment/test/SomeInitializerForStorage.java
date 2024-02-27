package org.eclipse.store.integrations.quarkus.deployment.test;



import org.eclipse.store.integrations.quarkus.types.config.StorageManagerInitializer;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.store.storage.types.StorageManager;

@ApplicationScoped
public class SomeInitializerForStorage implements StorageManagerInitializer
{
    @Override
    public void initialize(StorageManager storageManager)
    {
        SomeRootWithStorage rootObject = (SomeRootWithStorage) storageManager.root();
        if (rootObject == null) {
            throw new IllegalStateException("StorageManager should have already a Root object assigned");
        } else {
            rootObject.setData("Initial value of Root");
        }
    }
}
