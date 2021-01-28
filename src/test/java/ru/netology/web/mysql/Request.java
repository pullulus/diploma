package ru.netology.web.mysql;

import lombok.Value;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Request {
    public Request() {
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
                ) {
                    val info = runner.query(conn, getInfo, new BeanHandler<>(PaymentInfo.class));
                    return new PaymentInfo();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

 public static void shouldDeleteAfterPayment() {
        val clearPayment = "DELETE FROM payment_entity";
        val clearOrder = "DELETE FROM order_entity";
        val runner = new QueryRunner();

        try (
                val conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                )
                ) {
            runner.update(conn, clearOrder);
            runner.update(conn, clearPayment);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
 }
}
