package dolpi.Finance_Data_Processing_and_Access_Control_Backend.Service;

import dolpi.Finance_Data_Processing_and_Access_Control_Backend.DTO.TransactionDTO;
import dolpi.Finance_Data_Processing_and_Access_Control_Backend.Entity.TransactionEntity;
import dolpi.Finance_Data_Processing_and_Access_Control_Backend.Exception.ResourcesNotFound;
import dolpi.Finance_Data_Processing_and_Access_Control_Backend.Repository.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.time.LocalDateTime;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepo transactionRepo;

    // Create Transaction
    public TransactionEntity create(TransactionDTO dto, String userId){

        if(!dto.getType().equals("income") &&
                !dto.getType().equals("expense")){
            throw new RuntimeException(
                    "Type must be income or expense"
            );
        }

        //Valid amount
        if(dto.getAmount() == null || dto.getAmount() <= 0){
            throw new RuntimeException(
                    "Amount must be greater than 0"
            );
        }

        //Transaction
        TransactionEntity transaction = new TransactionEntity();
        transaction.setAmount(dto.getAmount());
        transaction.setType(dto.getType());
        transaction.setCategory(dto.getCategory());
        transaction.setDate(dto.getDate());
        transaction.setNotes(dto.getNotes());
        transaction.setCreatedBy(userId);
        transaction.setDeleted(false);
        transaction.setCreatedAt(
                LocalDateTime.now().toString()
        );

        return transactionRepo.save(transaction);
    }


    // Get All Transactions
    public Page<TransactionEntity> getAll(
            String type,
            String category,
            int page,
            int limit
    ){
        Pageable pageable = PageRequest.of(page, limit);

        if(type != null && category != null){
            return transactionRepo
                    .findByTypeAndCategoryAndIsDeletedFalse(
                            type, category, pageable
                    );
        } else if(type != null){
            return transactionRepo
                    .findByTypeAndIsDeletedFalse(
                            type, pageable
                    );
        } else if(category != null){
            return transactionRepo
                    .findByCategoryAndIsDeletedFalse(
                            category, pageable
                    );
        } else {
            return transactionRepo
                    .findByIsDeletedFalse(pageable);
        }
    }

    //  Get By Id
    public TransactionEntity getById(String id){

        TransactionEntity transaction = transactionRepo
                .findById(id)
                .orElseThrow(() ->
                        new ResolutionException("Transaction not found")
                );

        // Deleted hai toh error
        if(transaction.isDeleted()){
            throw new ResourcesNotFound("Transaction not found");
        }

        return transaction;
    }

    //Update Transaction
    public TransactionEntity update(String id, TransactionDTO dto){

        // Transaction exist or not
        TransactionEntity transaction = transactionRepo
                .findById(id)
                .orElseThrow(() ->
                        new ResourcesNotFound("Transaction not found")
                );

        if(transaction.isDeleted()){
            throw new ResourcesNotFound("Transaction not found");
        }


        if(dto.getAmount() != null){
            transaction.setAmount(dto.getAmount());
        }
        if(dto.getType() != null){
            transaction.setType(dto.getType());
        }
        if(dto.getCategory() != null){
            transaction.setCategory(dto.getCategory());
        }
        if(dto.getDate() != null){
            transaction.setDate(dto.getDate());
        }
        if(dto.getNotes() != null){
            transaction.setNotes(dto.getNotes());
        }

        return transactionRepo.save(transaction);
    }

    //Soft Delete
    public String softDelete(String id){

        TransactionEntity transaction = transactionRepo
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Transaction not found")
                );


        if(transaction.isDeleted()){
            throw new RuntimeException(
                    "Transaction already deleted"
            );
        }

        transaction.setDeleted(true);
        transactionRepo.save(transaction);

        return "Transaction deleted successfully";
    }

}
