package org.eclipse.store.integrations.quarkus.deployment.test;



import org.eclipse.store.integrations.quarkus.types.config.EmbeddedStorageFoundationCustomizer;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageFoundation;

@ApplicationScoped
public class SomeCustomizer implements EmbeddedStorageFoundationCustomizer
{
    @Override
    public void customize(EmbeddedStorageFoundation<?> embeddedStorageFoundation)
    {
        embeddedStorageFoundation.setDataBaseName("JUnit");
    }
}
