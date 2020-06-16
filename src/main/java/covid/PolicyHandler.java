package covid;

import covid.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
    
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReserved_Inspect(@Payload Reserved reserved){

        if(reserved.isMe()){
            System.out.println("##### listener Inspect : " + reserved.toJson());
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverCancelled_Inspect(@Payload Cancelled cancelled){

        if(cancelled.isMe()){
            System.out.println("##### listener Inspect : " + cancelled.toJson());
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverDelivered_Add(@Payload Delivered delivered){

        if(delivered.isMe()){
            System.out.println("##### listener Add : " + delivered.toJson());
        }
    }

}
