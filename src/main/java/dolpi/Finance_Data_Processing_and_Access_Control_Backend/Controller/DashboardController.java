package dolpi.Finance_Data_Processing_and_Access_Control_Backend.Controller;

import dolpi.Finance_Data_Processing_and_Access_Control_Backend.Service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    // Summary All access
    @GetMapping("/summary")
    public ResponseEntity<?> getSummary(){
        return new ResponseEntity(
                dashboardService.getSummary(),
                HttpStatus.OK
        );
    }

    //Category wise Admin and Analyst roles
    @GetMapping("/category-wise")
    public ResponseEntity<?> getCategoryWise(){
        return new ResponseEntity(
                dashboardService.getCategoryWise(),
                HttpStatus.OK
        );
    }

    // Trends Admin aur Analyst
    @GetMapping("/trends")
    public ResponseEntity<?> getTrends(){
        return new ResponseEntity(
                dashboardService.getTrends(),
                HttpStatus.OK
        );
    }
}