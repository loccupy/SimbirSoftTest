package utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionDetails {
    
    DATE("date"),
    AMOUNT("amount"),
    TYPE("type");

    private final String description;
}
