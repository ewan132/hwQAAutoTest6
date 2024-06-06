package ru.netology.Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.Data.DataHelper;
import ru.netology.Page.DashboardPage;
import ru.netology.Page.LoginPage;


import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.Data.DataHelper.*;


class MoneyTransferTest {

    DashboardPage dashboardPage;
    CardInfo firstCardInfo;
    CardInfo secondCardInfo;
    int firstCardBalance;
    int secondCardBalance;


    @BeforeEach
    void setup() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);
        firstCardInfo = getFirstCardInfo();
        secondCardInfo = getSecondCardInfo();
        firstCardBalance = dashboardPage.getCardBalance(0);
        secondCardBalance = dashboardPage.getCardBalance(1);
    }

    @Test
    void shouldTransferMoneyBetweenOwnCardsFS() {

        int sum = generateValidAmount(firstCardBalance);
        var expectedBalanceFirstCard = dashboardPage.getCardBalance(0) - sum;
        var expectedBalanceSecondCard = dashboardPage.getCardBalance(1) + sum;
        var transMoney = dashboardPage.selectCardForTransfer(secondCardInfo);
        dashboardPage = transMoney.toTransMoney(String.valueOf(sum), firstCardInfo);
        dashboardPage.reloadDashboardPage();
        var actualBalanceForFirstCard = dashboardPage.getCardBalance(0);
        var actualBalanceForSecondCard = dashboardPage.getCardBalance(1);
        assertAll(() -> assertEquals(expectedBalanceFirstCard,
                        actualBalanceForFirstCard),
                () -> assertEquals(expectedBalanceSecondCard, actualBalanceForSecondCard));
    }

    @Test
    void shouldTransferMoneyBetweenOwnCardsSF() {
        int sum = generateValidAmount(secondCardBalance);
        var expectedBalanceSecondCard = dashboardPage.getCardBalance(1) - sum;
        var expectedBalanceFirstCard = dashboardPage.getCardBalance(0) + sum;
        var transMoney = dashboardPage.selectCardForTransfer(firstCardInfo);
        dashboardPage = transMoney.toTransMoney(String.valueOf(sum), secondCardInfo);
        dashboardPage.reloadDashboardPage();
        var actualBalanceForSecondCard = dashboardPage.getCardBalance(1);
        var actualBalanceForFirstCard = dashboardPage.getCardBalance(0);
        assertAll(() -> assertEquals(expectedBalanceFirstCard,
                        actualBalanceForFirstCard),
                () -> assertEquals(expectedBalanceSecondCard, actualBalanceForSecondCard));
    }

    @Test
    void shouldErrorMassage() {
        int sum = generateValidAmount(secondCardBalance);
        var thirdCardInfo = getThirdCardInfo();
        var transMoney = dashboardPage.selectCardForTransfer(firstCardInfo);
        dashboardPage = transMoney.toTransMoney(String.valueOf(sum), thirdCardInfo);
        transMoney.findErrorMassage("Ошибка! Произошла ошибка");

    }


}