package dolpi.Finance_Data_Processing_and_Access_Control_Backend.Controller;

import dolpi.Finance_Data_Processing_and_Access_Control_Backend.DTO.TransactionDTO;
import dolpi.Finance_Data_Processing_and_Access_Control_Backend.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    // Create transaction
    @PostMapping
    public ResponseEntity<?> create(@RequestBody TransactionDTO dto,@RequestParam("ID") String UserId){
        return new ResponseEntity(transactionService.create(dto,UserId), HttpStatus.CREATED);
    }

    // ALl transactions
    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(required = false) String type,
                                    @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ){
        return new ResponseEntity(transactionService.getAll(type, category, page, limit), HttpStatus.OK);
    }

    // One transaction
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id){
        return new ResponseEntity(transactionService.getById(id), HttpStatus.OK);
    }

    // Update transaction
    @PatchMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable String id,
            @RequestBody TransactionDTO dto
    ){
        return new ResponseEntity(transactionService.update(id, dto), HttpStatus.OK);
    }

    // Soft delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable String id
    ){
        return new ResponseEntity(transactionService.softDelete(id), HttpStatus.OK);
    }
}
