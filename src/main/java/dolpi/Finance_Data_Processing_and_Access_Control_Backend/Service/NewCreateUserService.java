package dolpi.Finance_Data_Processing_and_Access_Control_Backend.Service;

import dolpi.Finance_Data_Processing_and_Access_Control_Backend.DTO.UserDTO;
import dolpi.Finance_Data_Processing_and_Access_Control_Backend.Entity.UserEntity;
import dolpi.Finance_Data_Processing_and_Access_Control_Backend.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

//This Service is Create new User
@Service
public class NewCreateUserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;

    public String newcreateuser(UserDTO userDTO){
        if(userRepo.existsByEmail(userDTO.getEmail())){
            throw new RuntimeException("Email already exists");
        }


        String encodepassword=passwordEncoder.encode(userDTO.getPassword());

        UserEntity user=new UserEntity();
        user.setName(userDTO.getName());
        user.setPassword(encodepassword);
        user.setEmail(userDTO.getEmail());
        user.setRoles(Arrays.asList("viewer"));

        userRepo.save(user);

        return "Successfully Created";
    }
}
