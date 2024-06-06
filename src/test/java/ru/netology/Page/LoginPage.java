package ru.netology.Page;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import ru.netology.Data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
   private final SelenideElement loginField =  $("[data-test-id='login'] input");
    private final SelenideElement passField =  $("[data-test-id='password'] input");
    private final SelenideElement loginButton = $("[data-test-id='action-login']");

    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        loginField.setValue(info.getLogin());
        passField.setValue(info.getPassword());
        loginButton.click();
       return new  VerificationPage();
    }
}
