package com.miljanilic.module;

import com.google.inject.AbstractModule;
import com.miljanilic.catalog.InMemorySchemaRepository;
import com.miljanilic.catalog.SchemaRepository;

public class CatalogModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(SchemaRepository.class).to(InMemorySchemaRepository.class);
    }
}
