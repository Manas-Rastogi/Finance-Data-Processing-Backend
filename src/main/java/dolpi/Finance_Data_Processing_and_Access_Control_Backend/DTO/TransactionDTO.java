package dolpi.Finance_Data_Processing_and_Access_Control_Backend.DTO;

import lombok.Data;
import lombok.NonNull;

@Data
public class TransactionDTO {
    @NonNull
    private Double amount;
    @NonNull
    private String type;
    @NonNull
    private String category;
    @NonNull
    private String date;
    @NonNull
    private String notes;
}
