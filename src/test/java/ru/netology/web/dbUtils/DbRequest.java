package ru.netology.web.dbUtils;

import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DbRequest {
    public DbRequest() {
    }

    public static PaymentInfoModel getPaymentInfo() {
        String dbUrl = System.getProperty("database.url");
        String dbUser = System.getProperty("database.name");
        String dbPassword = System.getProperty("database.password");

        val getInfo = "SELECT * FROM payment_entity ORDER BY created DESC LIMIT 1;";
        val runner = new QueryRunner();

        try (
                val conn = DriverManager.getConnection(
                        dbUrl, dbUser, dbPassword)
        ) {
            return runner.query(conn, getInfo, new BeanHandler<>(PaymentInfoModel.class));

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    public static PaymentInfoModel getPaymentWithCreditInfo() {
        String dbUrl = System.getProperty("database.url");
        String dbUser = System.getProperty("database.name");
        String dbPassword = System.getProperty("database.password");

        val getInfo = "SELECT * FROM credit_request_entity ORDER BY created DESC LIMIT 1;";
        val runner = new QueryRunner();

        try (
                val conn = DriverManager.getConnection(
                        dbUrl, dbUser, dbPassword)
        ) {
            return runner.query(conn, getInfo, new BeanHandler<>(PaymentInfoModel.class));

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    public static void shouldDeleteAfterPayment() {
        String dbUrl = System.getProperty("database.url");
        String dbUser = System.getProperty("database.name");
        String dbPassword = System.getProperty("database.password");

        val clearPayment = "DELETE FROM payment_entity";
        val clearPaymentWithCredit = "DELETE FROM credit_request_entity";
        val clearOrder = "DELETE FROM order_entity";
        val runner = new QueryRunner();

        try (
                val conn = DriverManager.getConnection(
                        dbUrl, dbUser, dbPassword)
        ) {
            runner.update(conn, clearOrder);
            runner.update(conn, clearPayment);
            runner.update(conn, clearPaymentWithCredit);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
