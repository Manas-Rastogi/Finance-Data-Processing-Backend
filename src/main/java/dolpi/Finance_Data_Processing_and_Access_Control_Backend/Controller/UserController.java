package dolpi.Finance_Data_Processing_and_Access_Control_Backend.Controller;

import dolpi.Finance_Data_Processing_and_Access_Control_Backend.DTO.RoleUpdateDTO;
import dolpi.Finance_Data_Processing_and_Access_Control_Backend.DTO.StatusUpdateDTO;
import dolpi.Finance_Data_Processing_and_Access_Control_Backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    //update role Admin only
    @PatchMapping("/{id}/role")
    public ResponseEntity<?> updateRole(@PathVariable String id, @RequestBody RoleUpdateDTO roleUpdateDTO){

        return new ResponseEntity(userService.UpdateUser(id, roleUpdateDTO), HttpStatus.OK);
    }

    // Status change Admin only
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable String id, @RequestBody StatusUpdateDTO statusUpdateDTO){
        return new ResponseEntity(userService.updateStatus(id, statusUpdateDTO), HttpStatus.OK);
    }

    // All users Get Admin only
    @GetMapping("/GetAllUser")
    public ResponseEntity<?> getAllUsers(){
        return new ResponseEntity(userService.getAllUsers(), HttpStatus.OK);
    }
}
