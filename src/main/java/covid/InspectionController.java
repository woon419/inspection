package covid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InspectionController {
   @Autowired
   private InspectionService inspectionService;

   @PostMapping("/inspections/update")
   public Inspection updateInspection(@RequestBody Inspection inspection){
     Inspection updateInspection = inspectionService.updateInspection(inspection);
     return updateInspection;
   }
}
