package dolpi.Finance_Data_Processing_and_Access_Control_Backend.Service;

import dolpi.Finance_Data_Processing_and_Access_Control_Backend.DTO.RoleUpdateDTO;
import dolpi.Finance_Data_Processing_and_Access_Control_Backend.DTO.StatusUpdateDTO;
import dolpi.Finance_Data_Processing_and_Access_Control_Backend.Entity.UserEntity;
import dolpi.Finance_Data_Processing_and_Access_Control_Backend.Exception.ResourcesNotFound;
import dolpi.Finance_Data_Processing_and_Access_Control_Backend.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//This Service User Update Role And Change Status And Fetch All Users
@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public String UpdateUser(String id, RoleUpdateDTO roleUpdateDTO){
        Optional<UserEntity> useropl=userRepo.findById(String.valueOf(id));

        UserEntity user=useropl.get();
        if(user==null) throw new ResourcesNotFound("NOT FOUND USER");

       user.setRoles(List.of(roleUpdateDTO.getRole()));

        userRepo.save(user);

        return "Successfully Updated User Role";
    }

    public String updateStatus(String id, StatusUpdateDTO statusUpdateDTO){
        Optional<UserEntity> useropl=userRepo.findById(String.valueOf(id));

        UserEntity user=useropl.get();
        if(user==null) throw new ResourcesNotFound("NOT FOUND USER");

        user.setActive(statusUpdateDTO.isActive());

        userRepo.save(user);

        return "Successfully Updated User Role";
    }

    public List<UserEntity> getAllUsers(){

        // Database Fetch user
        List<UserEntity> users = userRepo.findAll();


        users.forEach(user -> user.setPassword(null));

        return users;
    }
}
