package dolpi.Finance_Data_Processing_and_Access_Control_Backend.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Logeduser {
    @NotNull
    private String email;

    @NotNull
    private String password;
}
