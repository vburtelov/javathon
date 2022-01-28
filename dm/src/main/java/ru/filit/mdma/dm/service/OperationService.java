package ru.filit.mdma.dm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.filit.mdma.dm.model.Operation;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OperationService {

    @Autowired
    private YamlService yamlService;

    private final URL operationsFileUrl;

    public OperationService(@Value("${datafiles.operations}") String operationsFileName){
        this.operationsFileUrl = getClass().getClassLoader().getResource(operationsFileName);
    }

    public List<Operation> getAllOperations(String accountNumber) throws IOException {
        List<Operation> operations = yamlService.readYaml(operationsFileUrl, Operation.class);
        return operations.stream().filter(o -> o.getAccountNumber().equals(accountNumber)).
                sorted((o1, o2) -> Long.compare(o2.getOperDate(), o1.getOperDate())).collect(Collectors.toList());
    }

    public List<Operation> getLastOperations(String accountNumber, int n) throws IOException {
        return getAllOperations(accountNumber).stream().limit(n).collect(Collectors.toList());
    }

    public List<Operation> getOperations(String accountNumber, Date from) throws IOException {
        return getAllOperations(accountNumber).stream().filter(o->new Date(o.getOperDate()*1000L).after(from)).collect(Collectors.toList());
    }

    public List<Operation> getOperations(String accountNumber, Date from, Date to) throws IOException {
        return getAllOperations(accountNumber).stream().filter(o->new Date(o.getOperDate()*1000L).after(from) && new Date(o.getOperDate()*1000L).before(to)).collect(Collectors.toList());
    }

}
