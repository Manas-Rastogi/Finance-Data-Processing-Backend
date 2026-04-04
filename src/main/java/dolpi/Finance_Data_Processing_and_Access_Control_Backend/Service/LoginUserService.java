package dolpi.Finance_Data_Processing_and_Access_Control_Backend.Service;

import dolpi.Finance_Data_Processing_and_Access_Control_Backend.DTO.Logeduser;
import dolpi.Finance_Data_Processing_and_Access_Control_Backend.Entity.UserEntity;
import dolpi.Finance_Data_Processing_and_Access_Control_Backend.Exception.ResourcesNotFound;
import dolpi.Finance_Data_Processing_and_Access_Control_Backend.JwtToken.jwttoken;
import dolpi.Finance_Data_Processing_and_Access_Control_Backend.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

//This Service is Login Service
@Service
public class LoginUserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private jwttoken jwttoken;

    @Autowired
    private MyUserDetails myUserDetails;

    @Autowired
    private AuthenticationManager authenticationManager;

    public String GenerateToken(Logeduser logeduser){
        Optional<UserEntity> useropl=userRepo.findByEmail(logeduser.getEmail());
        UserEntity user=useropl.get();

        if(user.getRoles() == null) throw new ResourcesNotFound("Incorrect User");

        if(user==null) throw new ResourcesNotFound("Please Enter Valid Username ");
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(logeduser.getEmail(), logeduser.getPassword()));
        UserDetails userDetails = myUserDetails.loadUserByUsername(logeduser.getEmail());

        String jwt = jwttoken.generateToken(logeduser.getEmail());

        return jwt;

    }
}
