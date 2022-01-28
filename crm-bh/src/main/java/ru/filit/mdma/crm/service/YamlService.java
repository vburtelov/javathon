package ru.filit.mdma.crm.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class YamlService {

    private final ObjectMapper objectMapper=new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)).findAndRegisterModules();

     public <T> List<T> readYaml(URL url, Class<T> clazz) throws IOException {
         CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);
         return objectMapper.readValue(url, listType);
     }

    public<T> void writeYaml(URL url, List<T> list) throws IOException {
         objectMapper.writeValue(new File(url.getPath()), list);
    }
}
