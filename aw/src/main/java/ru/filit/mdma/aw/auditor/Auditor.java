package ru.filit.mdma.aw.auditor;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.filit.mdma.aw.auditor.model.AuditMessage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;

@Component
public class Auditor {

    private final String filePath="auditfiles/dm-audit.txt";


    @KafkaListener(topics = "dm-audit", groupId = "dm-aw")
    public void listenGroupFoo(AuditMessage message) throws IOException, URISyntaxException {
        System.out.println("done");
        URL url =getClass().getClassLoader().getResource(filePath);

        BufferedWriter bw = Files.newBufferedWriter(Paths.get(url.toURI()),  StandardCharsets.UTF_8, StandardOpenOption.APPEND);
        String messageStr =buildMsg(message);
        bw.write(messageStr);
        bw.newLine();
        bw.close();
    }

    private String buildMsg(AuditMessage message) {
        String formatStr ="%-19s %-8s %-15s %c%-34s %s";
        StringBuilder sb =new StringBuilder();
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        return String.format(formatStr, simpleDateFormat.format(message.getDate()), message.getKey(), message.getUserName(),
                message.getType()== AuditMessage.TypeEnum.REQUEST?'>':'<', message.getRequest(),message.getBody());
    }
}
