package com.stschool.ecommerce.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class IdGeneratorUtil {
    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    /*
        Generates Order ID
        Example:
        ORD-20260515123045-A1B2
     */

    public static String generateOrderId() {

        String timestamp =
                LocalDateTime.now()
                        .format(formatter);

        String randomPart =
                UUID.randomUUID()
                        .toString()
                        .substring(0, 4)
                        .toUpperCase();

        return "ORD-" +
                timestamp +
                "-" +
                randomPart;
    }

    /*
        Generates Transaction ID
        Example:
        TXN-20260515123045-X9Y8
     */

    public static String generateTransactionId() {

        String timestamp =
                LocalDateTime.now()
                        .format(formatter);

        String randomPart =
                UUID.randomUUID()
                        .toString()
                        .substring(0, 4)
                        .toUpperCase();

        return "TXN-" +
                timestamp +
                "-" +
                randomPart;
    }
}
