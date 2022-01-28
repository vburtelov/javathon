package ru.filit.mdma.dms.auditor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.filit.mdma.dms.auditor.model.AuditMessage;

import java.util.Date;
import java.util.Random;

@Service
public class Auditor {


    @Autowired
    private KafkaTemplate<String, AuditMessage> kafkaTemplate;
    private final String topicName="dm-audit";

    public void sendMessage(AuditMessage message) {
        kafkaTemplate.send(topicName, message);
    }

    public static String createRequestKey(){
        Random random =new Random();
        return String.valueOf((random.nextInt(89999999)+10000000));
    }

    public void createAndSend(Object body, String requestKey, AuditMessage.TypeEnum type, String userName, String requestPath){
        AuditMessage message=new AuditMessage();
        message.setBody(body);
        message.setKey(requestKey);
        message.setType(type);
        message.setUserName(userName);
        message.setDate(new Date());
        message.setRequest(requestPath);
        sendMessage(message);
    }
}
