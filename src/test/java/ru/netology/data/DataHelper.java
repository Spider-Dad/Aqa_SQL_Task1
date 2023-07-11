package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.util.Locale;

public class DataHelper {

    public static Faker faker = new Faker (new Locale("en"));

    //утилитный класс с приватным конструктором
    private DataHelper() {
    }

    // метод для валидного пользователя
    public static AuthInfo getAuthInfoWithTestData() {
        return new AuthInfo("vasya", "qwerty123");
    }

    //метод для генерации случайного логина
    private static String generateRandomLogin(){
        return faker.name().username();
    }

    //метод для генерации случайного пароля
    private static String generateRandomPassword(){
        return faker.internet().password();
    }

    //метод для пользователя со случайным логином и паролем
    public static AuthInfo generateRandomUser(){
        return new AuthInfo(generateRandomLogin(),generateRandomPassword());
    }

    //метод для генерации случайного проверочного кода
    public static VerificationCode generateRandomVerificationCode(){
        return new VerificationCode(faker.numerify("#####"));
    }

    //дата класс пользователя
    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }
    //дата класс верификации
    @Value
    public static class VerificationCode{
        private String code;
    }
}
