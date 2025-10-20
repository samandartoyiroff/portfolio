package uz.tuit.portfolio.model;

public enum TransactionStatus {
    PENDING,   // Waiting for processing
    PASSED,    // Successfully completed
    FAILED,    // Failed during processing
    CANCELLED  // Manually or system cancelled
}
