package ru.netology.web.dbUtils;

import lombok.Value;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DbRequest {
    public DbRequest() {
    }

    @Value
    public static class PaymentInfo {
        String id;
        String amount;
        String created;
        String status;
        String transaction_id;
    }

    public static PaymentInfo getPaymentInfo() {
        val getInfo = "SELECT * FROM payment_entity ORDER BY created DESC LIMIT 1;";
        val runner = new QueryRunner();

        try (
                val conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass")
        ) { return runner.query(conn, getInfo, new BeanHandler<>(PaymentInfo.class));

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

 public static void shouldDeleteAfterPayment() {
        val clearPayment = "DELETE FROM payment_entity";
        val clearPaymentWithCredit = "DELETE FROM credit_request_entity";
        val clearOrder = "DELETE FROM order_entity";
        val runner = new QueryRunner();

        try (
                val conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                )
                ) {
            runner.update(conn, clearOrder);
            runner.update(conn, clearPayment);
            runner.update(conn, clearPaymentWithCredit);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
 }
}
