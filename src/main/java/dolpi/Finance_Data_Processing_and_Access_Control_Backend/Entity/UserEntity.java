package dolpi.Finance_Data_Processing_and_Access_Control_Backend.Entity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user")
public class UserEntity {
    @Id
    private String id;

    @NotNull
    private String Name;

    @NotNull
    private String email;

    @NotNull
    private String password;

    private List<String> roles;

    boolean isActive=true;

    private LocalDateTime createdAt;

}
