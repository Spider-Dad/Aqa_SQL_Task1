package ru.netology.data;

import lombok.Data;
import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper {

    private static QueryRunner runner = new QueryRunner();

    private DBHelper(){

    }

// метод для подключения к базе данных
    private static Connection getConn() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app", "app", "pass"
        );
    }

    // метод возвращающий последний сгенерированный код верификации
    @SneakyThrows
    public static DataHelper.VerificationCode getVerificationCode() {
        var codeSQL = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1";
        var conn = getConn(); // подключение  к базе данных из метода
        var code = runner.query(conn, codeSQL, new ScalarHandler<String>()); // с помощью runner и метода query выполняем запрос codeSQL через подключение conn получаем значение одног поля (ScalarHandler)
        return new DataHelper.VerificationCode(code); // создаем экземпляр VerificationCode передавая строку с кодом code
    }

   // метод для очистки базы данных
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