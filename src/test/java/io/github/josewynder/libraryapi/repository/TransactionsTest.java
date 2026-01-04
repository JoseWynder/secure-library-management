package io.github.josewynder.libraryapi.repository;

import io.github.josewynder.libraryapi.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class TransactionsTest {

    @Autowired
    TransactionService transactionService;

    /**
     *  Commit - confirm the changes
     *  Rollback - Undo the changes
     */
    @Test
    void simpleTransactionTest() {
        transactionService.execute();
    }

    @Test
    void transactionManagedStateTest() {
        transactionService.updateWithoutUpdating();
    }

}
