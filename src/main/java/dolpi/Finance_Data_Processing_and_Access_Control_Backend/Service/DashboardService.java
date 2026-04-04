package dolpi.Finance_Data_Processing_and_Access_Control_Backend.Service;

import dolpi.Finance_Data_Processing_and_Access_Control_Backend.Entity.TransactionEntity;
import dolpi.Finance_Data_Processing_and_Access_Control_Backend.Exception.ResourcesNotFound;
import dolpi.Finance_Data_Processing_and_Access_Control_Backend.Repository.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Autowired
    private TransactionRepo transactionRepo;

    // Summary
    public Map<String, Object> getSummary(){

        // ALL non-deleted transactions
        List<TransactionEntity> all = transactionRepo
                .findByIsDeletedFalse();

        //exception handle
        if(all.isEmpty()){
            throw new ResourcesNotFound("Transaction NULL");
        }

        // Total income calculate
        Double totalIncome = all.stream()
                .filter(t -> t.getType().equals("income"))
                .mapToDouble(TransactionEntity::getAmount)
                .sum();

        // Total expense calculate karo
        Double totalExpense = all.stream()
                .filter(t -> t.getType().equals("expense"))
                .mapToDouble(TransactionEntity::getAmount)
                .sum();

        // Net balance
        Double netBalance = totalIncome - totalExpense;

        //create Response
        Map<String, Object> summary = new HashMap<>();
        summary.put("totalIncome", totalIncome);
        summary.put("totalExpense", totalExpense);
        summary.put("netBalance", netBalance);

        return summary;
    }

    //Category Wise
    public Map<String, Object> getCategoryWise(){

        List<TransactionEntity> all = transactionRepo
                .findByIsDeletedFalse();

        if(all.isEmpty()){
            throw new ResourcesNotFound("NOT FOUND");
        }

        // Income category wise group karo
        Map<String, Double> incomeByCategory = all.stream()
                .filter(t -> t.getType().equals("income"))
                .collect(Collectors.groupingBy(
                        TransactionEntity::getCategory,
                        Collectors.summingDouble(TransactionEntity::getAmount)
                ));

        //Expense category wise group
        Map<String, Double> expenseByCategory = all.stream()
                .filter(t -> t.getType().equals("expense"))
                .collect(Collectors.groupingBy(
                        TransactionEntity::getCategory,
                        Collectors.summingDouble(TransactionEntity::getAmount)
                ));

        // Response banao
        Map<String, Object> result = new HashMap<>();
        result.put("income", incomeByCategory);
        result.put("expense", expenseByCategory);

        return result;
    }

    //Trends Month wise
    public List<Map<String, Object>> getTrends(){

        List<TransactionEntity> all = transactionRepo
                .findByIsDeletedFalse();

        if(all.isEmpty()){
            throw new ResourcesNotFound("NOT FOUND");
        }

        // Month wise group
        Map<String, List<TransactionEntity>> byMonth = all.stream()
                .collect(Collectors.groupingBy(
                        t -> t.getDate().substring(0, 7) // 2026-04
                ));

        if(byMonth.isEmpty()){
            throw new ResourcesNotFound("NOT FOUND");
        }

        // all month income and expense calculate
        List<Map<String, Object>> trends = new ArrayList<>();

        byMonth.forEach((month, transactions) -> {
            Double income = transactions.stream()
                    .filter(t -> t.getType().equals("income"))
                    .mapToDouble(TransactionEntity::getAmount)
                    .sum();

            Double expense = transactions.stream()
                    .filter(t -> t.getType().equals("expense"))
                    .mapToDouble(TransactionEntity::getAmount)
                    .sum();

            Map<String, Object> monthData = new HashMap<>();
            monthData.put("month", month);
            monthData.put("income", income);
            monthData.put("expense", expense);

            trends.add(monthData);
        });

        if(trends.isEmpty()){
            throw new ResourcesNotFound("NOT FOUND");
        }

        // Month wise sort
        trends.sort((a, b) ->
                a.get("month").toString()
                        .compareTo(b.get("month").toString())
        );

        return trends;
    }
}
