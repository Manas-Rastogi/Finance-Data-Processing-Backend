package dolpi.Finance_Data_Processing_and_Access_Control_Backend.Repository;

import dolpi.Finance_Data_Processing_and_Access_Control_Backend.Entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepo extends MongoRepository<UserEntity,String> {
    boolean existsByEmail(String email);

     //    UserEntity findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
}
