package ru.filit.mdma.dm.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import org.springframework.stereotype.Service;
import ru.filit.mdma.dm.exception.WrongDataException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;

@Service
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
