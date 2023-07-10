package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper {
    private static Connection getConn() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app", "app", "pass"
        );
    }

    @SneakyThrows
    public static String getVerificationCodeFor() {
        var runner = new QueryRunner();
        var codeSQL = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1";
        try (
                var conn = getConn()

        ) {
            var code = runner.query(conn, codeSQL, new ScalarHandler<String>());
            return code;
        }
    }

    @SneakyThrows
    public static void cleanDB() {
        var runner = new QueryRunner();
        var cleanCards = "DELETE FROM cards";
        var cleanCardsTransaction = "DELETE FROM card_transactions";
        var cleanAuthCode = "DELETE FROM auth_codes";
        var cleanUser = "DELETE FROM users";
        try (
                var conn = getConn()

        ) {
            runner.execute(conn, cleanCardsTransaction);
            runner.execute(conn, cleanCards);
            runner.execute(conn, cleanAuthCode);
            runner.execute(conn, cleanUser);
        }

    }
}