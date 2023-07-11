package ru.netology.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DBHelper;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DBHelper.cleanDB;

public class LoginTest {
    @AfterAll
    static void clean() {
        cleanDB();
    }

    @Test
    @DisplayName("Should successfully login with exist login and password from SUT")
    void shouldSuccessLoginTest() {
        var loginpage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfoWithTestData();
        var verificationPage = loginpage.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisible();
        var verificationCode = DBHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode.getCode());
    }

    @Test
    @DisplayName("Should get error notification if user not exist in base")
    void shouldGetErrorIfLoginNotAddingToBaseTest(){
        var loginpage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.generateRandomUser();
        loginpage.validLogin(authInfo);
        loginpage.verifyErrorNotificationVisible();

    }

    @Test
    @DisplayName("Should get error if login with exist base and active user and random verification code")
    void shouldGetErrorIfLoginExistUserAndRandomVerificationCodeTest(){
        var loginpage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfoWithTestData();
        var verificationPage = loginpage.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisible();
        var verificationCode = DataHelper.generateRandomVerificationCode();
        verificationPage.verify(verificationCode.getCode());
        verificationPage.verifyErrorNotificationVisible();

    }

}
