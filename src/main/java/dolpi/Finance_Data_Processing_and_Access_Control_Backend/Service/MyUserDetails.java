package dolpi.Finance_Data_Processing_and_Access_Control_Backend.Service;

import dolpi.Finance_Data_Processing_and_Access_Control_Backend.Entity.UserEntity;
import dolpi.Finance_Data_Processing_and_Access_Control_Backend.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetails implements UserDetailsService {
    @Autowired
    private UserRepo userRep;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<UserEntity> useropl = userRep.findByEmail(email);
        UserEntity user=useropl.get();
        if (user != null) {

            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getEmail())
                    .password(user.getPassword())
                    .roles(user.getRoles().toArray(new String[0]))
                    .build();
        }

        throw new UsernameNotFoundException("No user or hospital found with username: " + email);


    }
}
