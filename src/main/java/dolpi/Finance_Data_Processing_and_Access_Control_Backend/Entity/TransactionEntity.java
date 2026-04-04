package dolpi.Finance_Data_Processing_and_Access_Control_Backend.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "transactions")
public class TransactionEntity {
    @Id
    private String id;

    private Double amount;
    private String type;
    private String category;
    private String date;
    private String notes;
    private String createdBy;
    private boolean isDeleted;   //soft delete
    private String createdAt;
}
