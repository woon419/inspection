package covid;

import covid.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PolicyHandler{
    @Autowired
    private InspectionRepository inspectionRepository;

    @Autowired
    private KitRepository kitRepository;

    @Transactional
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReserved_Inspect(@Payload Reserved reserved){

        if(reserved.isMe()){    System.out.println("##### listener Inspect : " + reserved.toJson());
            Inspection newInspection = new Inspection(reserved);
            inspectionRepository.save(newInspection);

        }
    }

    @Transactional
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverCancelled_Inspect(@Payload Cancelled cancelled){

        if(cancelled.isMe()){
            System.out.println("##### listener Inspect : " + cancelled.toJson());
            Optional<Inspection> inspection = inspectionRepository.findById(cancelled.getId());
            if (inspection.isPresent()){
                Inspection cancelInspection = inspection.get();
                if ("RESERVED".equals(cancelInspection.getStatus()))
                    inspectionRepository.delete(cancelInspection);
            }
        }
    }

    @Transactional
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverDelivered_Add(@Payload Delivered delivered){

        if(delivered.isMe()){
            System.out.println("##### listener Add : " + delivered.toJson());
            Kit kit = kitRepository.findByType(delivered.getType());
            if (kit != null){
                kit.setQty(kit.getQty() + delivered.getQty());
            }
        }
    }

}
