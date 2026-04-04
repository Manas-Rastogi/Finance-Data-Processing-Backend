package dolpi.Finance_Data_Processing_and_Access_Control_Backend.Repository;

import dolpi.Finance_Data_Processing_and_Access_Control_Backend.Entity.TransactionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TransactionRepo extends MongoRepository<TransactionEntity, String> {

    Page<TransactionEntity> findByIsDeletedFalse(
            Pageable pageable
    );

    Page<TransactionEntity> findByTypeAndIsDeletedFalse(
            String type, Pageable pageable
    );

    Page<TransactionEntity> findByCategoryAndIsDeletedFalse(
            String category, Pageable pageable
    );

    Page<TransactionEntity> findByTypeAndCategoryAndIsDeletedFalse(
            String type, String category, Pageable pageable
    );

    List<TransactionEntity> findByIsDeletedFalse();
}