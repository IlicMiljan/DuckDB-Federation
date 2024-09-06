package com.miljanilic.catalog.loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Singleton;
import com.miljanilic.catalog.data.Schema;
import com.miljanilic.catalog.exception.FailedLoadingSchemaException;
import com.miljanilic.catalog.exception.FailedParsingSchemaException;

import java.io.*;
import java.util.*;
import java.net.URL;

@Singleton
public class SchemaLoader {
    private static final String SCHEMAS_FOLDER_PATH = "schemas";

    private final List<Schema> schemaList = new ArrayList<>();

    public SchemaLoader() {
        this.loadSchemasFromFolder(SCHEMAS_FOLDER_PATH);
    }

    public void loadSchemasFromFolder(String folderPath) {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            URL resource = classLoader.getResource(folderPath);

            if (resource == null) {
                throw new IllegalArgumentException("Folder not found: " + folderPath);
            }

            File folder = new File(resource.getFile());
            if (folder.isDirectory()) {
                for (File file : Objects.requireNonNull(folder.listFiles())) {
                    if (file.getName().endsWith(".json")) {
                        Schema schema = parseSchema(file);
                        if (schema != null) {
                            schemaList.add(schema);
                        }
                    }
                }
            } else {
                throw new IllegalArgumentException("The provided path is not a directory: " + folderPath);
            }
        } catch (Exception e) {
            throw new FailedLoadingSchemaException("Failed loading schema files.", e);
        }
    }

    private Schema parseSchema(File file) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(file, Schema.class);
        } catch (IOException e) {
           throw new FailedParsingSchemaException("Failed parsing schema from file: " + file.getName(), e);
        }
    }

    public List<Schema> getSchemaList() {
        return schemaList;
    }
}
