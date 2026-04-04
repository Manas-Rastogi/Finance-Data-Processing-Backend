package dolpi.Finance_Data_Processing_and_Access_Control_Backend.Controller;

import dolpi.Finance_Data_Processing_and_Access_Control_Backend.DTO.Logeduser;
import dolpi.Finance_Data_Processing_and_Access_Control_Backend.DTO.UserDTO;
import dolpi.Finance_Data_Processing_and_Access_Control_Backend.Exception.ResourcesNotFound;
import dolpi.Finance_Data_Processing_and_Access_Control_Backend.Service.LoginUserService;
import dolpi.Finance_Data_Processing_and_Access_Control_Backend.Service.NewCreateUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Pattern;

@RestController
@RequestMapping("/Auth")
public class AuthController {

    @Autowired
    private LoginUserService loginUserService;

    @Autowired
    private NewCreateUserService newCreateUserService;

    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);

    //New User Create
    @PostMapping("/craete/user")
    public ResponseEntity<?> createnewuser(@RequestBody UserDTO userDTO){

        boolean check=pattern.matcher(userDTO.getEmail()).matches();

        if(!check)  throw new ResourcesNotFound("User Email Incorrect Please Enter Valid Email");

        return new ResponseEntity(newCreateUserService.newcreateuser(userDTO), HttpStatus.CREATED);
    }

    //Login User
    @PostMapping("/login/user")
    public ResponseEntity<?> loginUser(@RequestBody Logeduser logeduser){
        return new ResponseEntity<>(loginUserService.GenerateToken(logeduser),HttpStatus.CREATED);
    }
}
