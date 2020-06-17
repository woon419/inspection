package covid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class InspectionService {
    @Autowired
    private InspectionRepository inspectionRepository;

    @Autowired
    private KitRepository kitRepository;

    @Transactional
    public Inspection updateInspection(Inspection inspection){
        Long inspectionId = inspection.getId();
        String kitType = inspection.getKitType();
        Optional<Inspection> optionalInspection = inspectionRepository.findById(inspectionId);
        Inspection updateInspection = null;

        if (optionalInspection.isPresent()){
            updateInspection = optionalInspection.get();
            String formatDate = LocalDateTime.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            updateInspection.setInspectionDate(formatDate);
            updateInspection.setStatus("INSPECTED");
            updateInspection.setKitType(inspection.getKitType());
        } else {
            throw new RuntimeException("예약 정보 없음");
        }

        Kit kit = kitRepository.findByType(kitType);
        if (kit == null || kit.getQty() < 1) throw new RuntimeException("재고 없음");

        kit.setQty(kit.getQty() - 1);
        return updateInspection;
    }
}
