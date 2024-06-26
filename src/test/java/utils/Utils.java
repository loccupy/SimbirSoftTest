package utils;

import org.apache.commons.lang3.time.CalendarUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static constants.Constants.PATH_TO_CSV;
import static constants.Constants.RU_LOCAL;
import static utils.TransactionDetails.DATE;
import static utils.TransactionDetails.AMOUNT;
import static utils.TransactionDetails.TYPE;

public class Utils {

    public static String formatterTransactionDate(String transactionDate) throws ParseException {
        DateFormat df = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss", Locale.ENGLISH);
        Date date = df.parse(transactionDate);
        SimpleDateFormat newFormatter = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss", Locale.forLanguageTag(RU_LOCAL));
        return newFormatter.format(date);
    }

    public static HashMap<String ,String> parseTable(String transactionDate, String transactionAmount, String transactionType) {
        HashMap<String, String> transaction = new HashMap<>();
        transaction.put(DATE.getDescription(), transactionDate);
        transaction.put(AMOUNT.getDescription(), transactionAmount);
        transaction.put(TYPE.getDescription(), transactionType);
        return transaction;
    }

    public static void writeFileCsv(List<HashMap<String, String>> allTransaction) throws IOException {
        try(FileWriter csvWriter = new FileWriter(PATH_TO_CSV)) {
            for (HashMap<String, String> rowData : allTransaction) {
                csvWriter.append(String.format("%s %s %s", rowData.get(DATE.getDescription()),
                        rowData.get(AMOUNT.getDescription()), rowData.get(TYPE.getDescription())));
                csvWriter.append("\n");
            }
        }
    }

    public static String getAmountWithFibonacci() {
        var index = CalendarUtils.INSTANCE.getDayOfMonth() + 1;
        var first = 0;
        var second = 1;
        for (var i = 2; i < index; i ++) {
            var n2 = first + second;
            first = second;
            second = n2;
        }
        return String.valueOf(second);
    }
}
